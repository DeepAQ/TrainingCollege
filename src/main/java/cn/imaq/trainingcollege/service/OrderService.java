package cn.imaq.trainingcollege.service;

import cn.imaq.autumn.core.annotation.Autumnwired;
import cn.imaq.autumn.core.annotation.Component;
import cn.imaq.trainingcollege.domain.dto.ClassInfoDto;
import cn.imaq.trainingcollege.domain.dto.NewOrderDto;
import cn.imaq.trainingcollege.domain.entity.Course;
import cn.imaq.trainingcollege.domain.entity.CourseClass;
import cn.imaq.trainingcollege.domain.entity.Order;
import cn.imaq.trainingcollege.domain.entity.Participant;
import cn.imaq.trainingcollege.mapper.ClassMapper;
import cn.imaq.trainingcollege.mapper.CourseMapper;
import cn.imaq.trainingcollege.mapper.OrderMapper;
import cn.imaq.trainingcollege.mapper.ParticipantMapper;
import cn.imaq.trainingcollege.support.exception.ServiceException;
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
                .orig_price(count * courseClass.getPrice())
                .status(Order.Status.INVALID)
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
}
