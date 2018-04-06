package cn.imaq.trainingcollege.service;

import cn.imaq.autumn.core.annotation.Autumnwired;
import cn.imaq.autumn.core.annotation.Component;
import cn.imaq.trainingcollege.domain.dto.ClassInfoDto;
import cn.imaq.trainingcollege.domain.dto.NewOrderDto;
import cn.imaq.trainingcollege.domain.dto.OrderListDto;
import cn.imaq.trainingcollege.domain.entity.*;
import cn.imaq.trainingcollege.mapper.*;
import cn.imaq.trainingcollege.support.exception.ServiceException;
import cn.imaq.trainingcollege.util.HashUtil;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderService {
    @Autumnwired
    private OrderMapper orderMapper;

    @Autumnwired
    private ParticipantMapper participantMapper;

    @Autumnwired
    private ClassMapper classMapper;

    @Autumnwired
    private CourseMapper courseMapper;

    @Autumnwired
    private StudentMapper studentMapper;

    @Autumnwired
    private PayService payService;

    @Autumnwired
    private Jedis jedis;

    public ClassInfoDto getClassInfo(Integer classId) {
        CourseClass courseClass = classMapper.getById(classId);
        // get paid count
        int paidCount = participantMapper.countByClassId(courseClass.getId());
        // get lock count
        String redisScript = "local sum = 0 " +
                "for k, v in ipairs(redis.call('keys', 'class_' .. KEYS[1] .. '_*')) do " +
                "sum = sum + redis.call('get', v) end " +
                "return sum";
        int lockCount = ((Long) jedis.eval(redisScript, 1, String.valueOf(classId))).intValue();
        return new ClassInfoDto(courseClass, courseClass.getLimit() - paidCount - lockCount);
    }

    public void newOrder(Integer studentId, NewOrderDto dto) {
        if (dto.getNames() == null || dto.getNames().isEmpty()) {
            throw new ServiceException("学员数量不能为 0");
        }
        if (StringUtils.isAnyBlank(dto.getNames().toArray(new String[0]))) {
            throw new ServiceException("请填写所有学员姓名");
        }
        CourseClass courseClass = classMapper.getById(dto.getClassId());
        if (courseClass == null) {
            throw new ServiceException("班级不存在");
        }
        int count = dto.getNames().size();
        // insert order
        Course course = courseMapper.getById(courseClass.getCourseId());
        Order order = Order.builder()
                .studentId(studentId)
                .collegeId(course.getCollegeId())
                .courseId(courseClass.getCourseId())
                .classId(courseClass.getId())
                .count(count)
                .origPrice(count * courseClass.getPrice())
                .status(Order.Status.CLOSED)
                .created((int) (System.currentTimeMillis() / 1000))
                .build();
        orderMapper.insert(order);
        // get paid count
        int paidCount = participantMapper.countByClassId(courseClass.getId());
        // lock redis
        String lockScript = "local remain = KEYS[3] + 0 " +
                "for k, v in ipairs(redis.call('keys', 'class_' .. KEYS[1] .. '_*')) do " +
                "remain = remain - redis.call('get', v) end " +
                "if (remain >= KEYS[4] + 0) then " +
                "redis.call('setex', 'class_' .. KEYS[1] .. '_order_' .. KEYS[2], KEYS[5] + 0, KEYS[4]) return 1 " +
                "else return 0 end";
        int redisResult = ((Long) jedis.eval(lockScript, 5, String.valueOf(courseClass.getId()), String.valueOf(order.getId()), String.valueOf(courseClass.getLimit() - paidCount), String.valueOf(count), String.valueOf(15 * 60))).intValue();
        if (redisResult != 1) {
            throw new ServiceException("销售火爆，名额不足，请重新尝试报名");
        }
        orderMapper.updateStatus(order.getId(), Order.Status.NOT_PAID);
        // insert participants
        List<Participant> participants = dto.getNames().stream().map(x -> {
            return Participant.builder()
                    .studentId(studentId)
                    .courseId(courseClass.getCourseId())
                    .classId(courseClass.getId())
                    .orderId(order.getId())
                    .name(x)
                    .status(Participant.Status.INVALID)
                    .build();
        }).collect(Collectors.toList());
        for (Participant participant : participants) {
            participantMapper.insert(participant);
        }
    }

    public List<OrderListDto> getStudentOrderList(Integer studentId) {
        List<Order> orders = orderMapper.getByStudentId(studentId);
        return orders.stream().map(order -> {
            checkExpire(order);
            Course course = courseMapper.getById(order.getCourseId());
            return OrderListDto.builder()
                    .id(order.getId())
                    .courseName(course.getTitle())
                    .count(order.getCount())
                    .origPrice(order.getOrigPrice())
                    .status(order.getStatus())
                    .build();
        }).collect(Collectors.toList());
    }

    public void payOrder(Integer studentId, Integer orderId, String password) {
        Order order = orderMapper.getById(orderId);
        if (order == null || !order.getStudentId().equals(studentId)) {
            throw new ServiceException("订单不存在");
        }
        checkExpire(order);
        if (order.getStatus() != Order.Status.NOT_PAID) {
            throw new ServiceException("订单状态不正确");
        }
        Student student = studentMapper.getById(order.getStudentId());
        if (!student.getPwdHash().equals(HashUtil.hash(password))) {
            throw new ServiceException("账户密码错误，支付失败");
        }
        payService.pay(order.getStudentId(), order.getOrigPrice());
        // paid
        orderMapper.updateStatus(order.getId(), Order.Status.PAID);
        orderMapper.updatePayPrice(order.getId(), order.getOrigPrice());
        participantMapper.makeValid(order.getId(), 1);
        jedis.del("class_" + order.getClassId() + "_order_" + order.getId());
    }

    public void cancelOrder(Integer studentId, Integer orderId) {
        Order order = orderMapper.getById(orderId);
        if (order == null || !order.getStudentId().equals(studentId)) {
            throw new ServiceException("订单不存在");
        }
        checkExpire(order);
        if (order.getStatus() != Order.Status.PAID && order.getStatus() != Order.Status.NOT_PAID) {
            throw new ServiceException("订单状态不正确");
        }
        if (order.getStatus() == Order.Status.PAID) {
            // refund
            Course course = courseMapper.getById(order.getCourseId());
            long delta = course.getStartTime() - System.currentTimeMillis() / 1000;
            if (delta > 0) {
                int refund;
                if (delta <= 14 * 24 * 3600) {
                    refund = (int) (order.getPayPrice() * 0.5);
                } else {
                    refund = (int) (order.getPayPrice() * 0.8);
                }
                payService.refund(order.getStudentId(), refund);
            }
        }
        // paid
        orderMapper.updateStatus(order.getId(), Order.Status.CANCELLED);
        participantMapper.makeValid(order.getId(), 0);
    }

    private void checkExpire(Order order) {
        if (order.getStatus() == Order.Status.NOT_PAID) {
            if (!jedis.exists("class_" + order.getClassId() + "_order_" + order.getId())) {
                order.setStatus(Order.Status.CLOSED);
                orderMapper.updateStatus(order.getId(), Order.Status.CLOSED);
            }
        }
    }
}
