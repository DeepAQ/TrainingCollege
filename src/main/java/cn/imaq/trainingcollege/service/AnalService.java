package cn.imaq.trainingcollege.service;

import cn.imaq.autumn.core.annotation.Autumnwired;
import cn.imaq.autumn.core.annotation.Component;
import cn.imaq.trainingcollege.domain.anal.CollegeIncomeAnal;
import cn.imaq.trainingcollege.domain.anal.CollegeTeachingAnal;
import cn.imaq.trainingcollege.domain.anal.StudentConsumptionAnal;
import cn.imaq.trainingcollege.domain.anal.StudentStudyAnal;
import cn.imaq.trainingcollege.domain.entity.Course;
import cn.imaq.trainingcollege.domain.entity.Order;
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
        List<Order> orders = orderMapper.getByStudentId(studentId).stream()
                .filter(o -> o.getCreated() > startTime && o.getCreated() < endTime)
                .collect(Collectors.toList());

        orders.stream().filter(o -> o.getStatus() == Order.Status.PAID).forEach(o -> {
            String ym = DateUtil.toYearMonth(o.getCreated());
            anal.getMonthly().put(ym, anal.getMonthly().getOrDefault(ym, 0) + o.getPayPrice());

            String collegeName = collegeProfileMapper.getById(collegeMapper.getById(o.getCollegeId()).getProfileId()).getName();
            anal.getByColleges().put(collegeName, anal.getByColleges().getOrDefault(collegeName, 0) + o.getPayPrice());

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
        List<Order> orders = orderMapper.getByCollegeId(collegeId).stream()
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

            String teacherName = classMapper.getById(o.getClassId()).getTeacher();
            anal.getByTeacher().put(teacherName, anal.getByTeacher().getOrDefault(teacherName, 0) + period);
        });

        anal.setTotal(totalPeriod[0]);
        anal.setCancelRate(orders.stream().filter(o -> o.getStatus() == Order.Status.CANCELLED).count() * 1.0 / orders.size());
        return anal;
    }
}
