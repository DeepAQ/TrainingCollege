package cn.imaq.trainingcollege.service;

import cn.imaq.autumn.core.annotation.Autumnwired;
import cn.imaq.autumn.core.annotation.Component;
import cn.imaq.trainingcollege.domain.anal.*;
import cn.imaq.trainingcollege.domain.entity.*;
import cn.imaq.trainingcollege.mapper.*;
import cn.imaq.trainingcollege.util.DateUtil;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("ALL")
@Component
public class AnalService {
    @Autumnwired
    private OrderMapper orderMapper;

    @Autumnwired
    private StudentMapper studentMapper;

    @Autumnwired
    private CollegeMapper collegeMapper;

    @Autumnwired
    private CollegeProfileMapper collegeProfileMapper;

    @Autumnwired
    private CourseMapper courseMapper;

    @Autumnwired
    private ClassMapper classMapper;

    @Autumnwired
    private ParticipantMapper participantMapper;

    @Autumnwired
    private RecordMapper recordMapper;

    public StudentConsumptionAnal getStudentConsumptionAnal(Integer studentId, Integer startTime, Integer endTime) {
        StudentConsumptionAnal anal = new StudentConsumptionAnal();
        for (int i = startTime; i <= endTime; i += 15 * 24 * 3600) {
            anal.getMonthly().put(DateUtil.toYearMonth(i), 0);
        }

        List<Order> orders = orderMapper.getByStudentId(studentId).stream()
                .filter(o -> o.getCreated() > startTime && o.getCreated() < endTime)
                .collect(Collectors.toList());

        orders.stream().filter(o -> o.getStatus() == Order.Status.PAID).forEach(o -> {
            String ym = DateUtil.toYearMonth(o.getCreated());
            anal.getMonthly().put(ym, anal.getMonthly().getOrDefault(ym, 0) + o.getPayPrice());

            String collegeName = collegeProfileMapper.getById(collegeMapper.getById(o.getCollegeId()).getProfileId()).getName();
            anal.getByCollege().put(collegeName, anal.getByCollege().getOrDefault(collegeName, 0) + o.getPayPrice());

            String tagName = courseMapper.getById(o.getCourseId()).getTags();
            anal.getByTags().put(tagName, anal.getByTags().getOrDefault(tagName, 0) + o.getPayPrice());
        });

        anal.setAvgPrice((int) orders.stream().filter(o -> o.getStatus() == Order.Status.PAID)
                .mapToInt(Order::getPayPrice).average().getAsDouble());
        anal.setFinishRate(orders.stream().filter(o -> o.getStatus() == Order.Status.PAID).count() * 1.0 / orders.size());
        return anal;
    }

    public StudentStudyAnal getStudentStudyAnal(Integer studentId, Integer startTime, Integer endTime) {
        StudentStudyAnal anal = new StudentStudyAnal();
        for (int i = startTime; i <= endTime; i += 15 * 24 * 3600) {
            anal.getMonthly().put(DateUtil.toYearMonth(i), 0);
        }

        List<Order> orders = orderMapper.getByStudentId(studentId).stream()
                .filter(o -> o.getCreated() > startTime && o.getCreated() < endTime)
                .collect(Collectors.toList());

        final int[] totalPeriod = {0};
        orders.stream().filter(o -> o.getStatus() == Order.Status.PAID).forEach(o -> {
            Course c = courseMapper.getById(o.getCourseId());
            String ym = DateUtil.toYearMonth(o.getCreated());
            int period = c.getPeriod() * c.getWeeks();
            totalPeriod[0] += period;
            anal.getMonthly().put(ym, anal.getMonthly().getOrDefault(ym, 0) + period);
            anal.getByCourse().put(c.getTitle(), anal.getByCourse().getOrDefault(c.getTitle(), 0) + period);
            anal.getByTags().put(c.getTags(), anal.getByTags().getOrDefault(c.getTags(), 0) + period);
        });

        anal.setTotal(totalPeriod[0]);
        anal.setCancelRate(orders.stream().filter(o -> o.getStatus() == Order.Status.CANCELLED).count() * 1.0 / orders.size());
        return anal;
    }

    public CollegeIncomeAnal getCollegeIncomeAnal(Integer collegeId, Integer startTime, Integer endTime) {
        CollegeIncomeAnal anal = new CollegeIncomeAnal();
        for (int i = startTime; i <= endTime; i += 15 * 24 * 3600) {
            anal.getMonthly().put(DateUtil.toYearMonth(i), 0);
        }

        List<Order> orders = orderMapper.getByCollegeId(collegeId).stream()
                .filter(o -> o.getCreated() > startTime && o.getCreated() < endTime)
                .collect(Collectors.toList());

        orders.stream().filter(o -> o.getStatus() == Order.Status.PAID).forEach(o -> {
            String ym = DateUtil.toYearMonth(o.getCreated());
            anal.getMonthly().put(ym, anal.getMonthly().getOrDefault(ym, 0) + o.getPayPrice());

            String courseName = courseMapper.getById(o.getCourseId()).getTitle();
            anal.getByCourse().put(courseName, anal.getByCourse().getOrDefault(courseName, 0) + o.getPayPrice());

            String teacherName = classMapper.getById(o.getClassId()).getTeacher();
            anal.getByTeacher().put(teacherName, anal.getByTeacher().getOrDefault(teacherName, 0) + o.getPayPrice());
        });

        anal.setTotal(orders.stream().filter(o -> o.getStatus() == Order.Status.PAID).mapToInt(o -> o.getPayPrice()).sum());
        anal.setOnlineTotal(orders.stream().filter(o -> o.getStatus() == Order.Status.PAID && o.getStudentId() > 0).mapToInt(o -> o.getPayPrice()).sum());
        anal.setOfflineTotal(anal.getTotal() - anal.getOnlineTotal());
        anal.setAvgPrice((int) orders.stream().filter(o -> o.getStatus() == Order.Status.PAID).mapToInt(o -> o.getPayPrice()).average().getAsDouble());
        anal.setFinishRate(orders.stream().filter(o -> o.getStatus() == Order.Status.PAID).count() * 1.0 / orders.size());
        return anal;
    }

    public CollegeTeachingAnal getCollegeTeachingAnal(Integer collegeId, Integer startTime, Integer endTime) {
        CollegeTeachingAnal anal = new CollegeTeachingAnal();
        for (int i = startTime; i <= endTime; i += 15 * 24 * 3600) {
            anal.getMonthly().put(DateUtil.toYearMonth(i), 0);
        }

        List<Order> orders = orderMapper.getByCollegeId(collegeId).stream()
                .filter(o -> o.getCreated() > startTime && o.getCreated() < endTime)
                .collect(Collectors.toList());
        List<Course> courses = courseMapper.getByCollegeId(collegeId).stream()
                .filter(c -> c.getStartTime() > startTime && c.getStartTime() < endTime)
                .collect(Collectors.toList());

        int totalPeriod = 0;
        for (Course c : courses) {
            String ym = DateUtil.toYearMonth(c.getStartTime());
            int period = c.getPeriod() * c.getWeeks();
            totalPeriod += period;
            anal.getMonthly().put(ym, anal.getMonthly().getOrDefault(ym, 0) + period);
            anal.getByCourse().put(c.getTitle(), anal.getByCourse().getOrDefault(c.getTitle(), 0) + period);
            for (CourseClass cc : classMapper.getByCourseId(c.getId())) {
                anal.getByTeacher().put(cc.getTeacher(), anal.getByTeacher().getOrDefault(cc.getTeacher(), 0) + period);
            }
        }

        anal.setTotal(totalPeriod);
        anal.setCancelRate(orders.stream().filter(o -> o.getStatus() == Order.Status.CANCELLED).count() * 1.0 / orders.size());
        return anal;
    }

    public ManagerIncomeAnal getManagerIncomeAnal(Integer startTime, Integer endTime) {
        ManagerIncomeAnal anal = new ManagerIncomeAnal();
        for (int i = startTime; i <= endTime; i += 15 * 24 * 3600) {
            anal.getMonthlyOrders().put(DateUtil.toYearMonth(i), 0);
            anal.getMonthlyTotal().put(DateUtil.toYearMonth(i), 0);
        }

        List<Order> orders = orderMapper.getByTime(startTime, endTime);

        orders.stream().filter(o -> o.getStatus() == Order.Status.PAID).forEach(o -> {
            String ym = DateUtil.toYearMonth(o.getCreated());
            anal.getMonthlyTotal().put(ym, anal.getMonthlyTotal().getOrDefault(ym, 0) + o.getPayPrice());
            anal.getMonthlyOrders().put(ym, anal.getMonthlyOrders().getOrDefault(ym, 0) + 1);

            String collegeName = collegeProfileMapper.getById(collegeMapper.getById(o.getCollegeId()).getProfileId()).getName();
            anal.getByCollege().put(collegeName, anal.getByCollege().getOrDefault(collegeName, 0) + o.getPayPrice());
            anal.getOrdersByCollege().put(collegeName, anal.getOrdersByCollege().getOrDefault(collegeName, 0) + 1);

            String tagName = courseMapper.getById(o.getCourseId()).getTags();
            anal.getByTags().put(tagName, anal.getByTags().getOrDefault(tagName, 0) + o.getPayPrice());
            anal.getOrdersByTags().put(tagName, anal.getOrdersByTags().getOrDefault(tagName, 0) + 1);
        });

        anal.setTotal(orders.stream().filter(o -> o.getStatus() == Order.Status.PAID).mapToInt(o -> o.getPayPrice()).sum());
        anal.setOrders(orders.size());
        anal.setOnlineTotal(orders.stream().filter(o -> o.getStatus() == Order.Status.PAID && o.getStudentId() > 0).mapToInt(o -> o.getPayPrice()).sum());
        anal.setOfflineTotal(anal.getTotal() - anal.getOnlineTotal());
        anal.setAvgPrice((int) orders.stream().filter(o -> o.getStatus() == Order.Status.PAID).mapToInt(o -> o.getPayPrice()).average().getAsDouble());
        anal.setFinishRate(orders.stream().filter(o -> o.getStatus() == Order.Status.PAID).count() * 1.0 / orders.size());
        return anal;
    }

    public ManagerTeachingAnal getManagerTeachingAnal(Integer startTime, Integer endTime) {
        ManagerTeachingAnal anal = new ManagerTeachingAnal();
        for (int i = startTime; i <= endTime; i += 15 * 24 * 3600) {
            anal.getMonthly().put(DateUtil.toYearMonth(i), 0);
        }

        List<Order> orders = orderMapper.getByTime(startTime, endTime);
        List<Course> courses = courseMapper.getByTime(startTime, endTime);

        int totalPeriod = 0;
        for (Course c : courses) {
            String ym = DateUtil.toYearMonth(c.getStartTime());
            int period = c.getPeriod() * c.getWeeks();
            totalPeriod += period;
            anal.getMonthly().put(ym, anal.getMonthly().getOrDefault(ym, 0) + period);

            String collegeName = collegeProfileMapper.getById(collegeMapper.getById(c.getCollegeId()).getProfileId()).getName();
            anal.getByCollege().put(collegeName, anal.getByCollege().getOrDefault(collegeName, 0) + period);
            anal.getByTags().put(c.getTags(), anal.getByTags().getOrDefault(c.getTags(), 0) + period);
        }
        anal.setTotal(totalPeriod);
        anal.setCancelRate(orders.stream().filter(o -> o.getStatus() == Order.Status.CANCELLED).count() * 1.0 / orders.size());
        return anal;
    }

    public ManagerPlatformAnal getManagerPlatformAnal(Integer startTime, Integer endTime) {
        ManagerPlatformAnal anal = new ManagerPlatformAnal();
        for (int i = startTime; i <= endTime; i += 15 * 24 * 3600) {
            anal.getMonthlyStudent().put(DateUtil.toYearMonth(i), 0);
            anal.getMonthlyCollege().put(DateUtil.toYearMonth(i), 0);
        }

        List<Student> students = studentMapper.getAll();
        List<College> colleges = collegeMapper.getAll();

        for (Student s : students) {
            if (s.getCreated() > startTime && s.getCreated() < endTime) {
                String ym = DateUtil.toYearMonth(s.getCreated());
                anal.getMonthlyStudent().put(ym, anal.getMonthlyStudent().getOrDefault(ym, 0) + 1);
            }
        }
        for (College c : colleges) {
            if (c.getCreated() > startTime && c.getCreated() < endTime) {
                String ym = DateUtil.toYearMonth(c.getCreated());
                anal.getMonthlyCollege().put(ym, anal.getMonthlyCollege().getOrDefault(ym, 0) + 1);
            }
        }

        anal.setTotalStudent(students.size());
        anal.setTotalCollege(colleges.size());
        return anal;
    }
}
