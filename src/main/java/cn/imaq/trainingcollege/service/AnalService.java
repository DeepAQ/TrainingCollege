package cn.imaq.trainingcollege.service;

import cn.imaq.autumn.core.annotation.Autumnwired;
import cn.imaq.autumn.core.annotation.Component;
import cn.imaq.trainingcollege.domain.anal.StudentConsumptionAnal;
import cn.imaq.trainingcollege.domain.entity.Order;
import cn.imaq.trainingcollege.mapper.CollegeMapper;
import cn.imaq.trainingcollege.mapper.CollegeProfileMapper;
import cn.imaq.trainingcollege.mapper.CourseMapper;
import cn.imaq.trainingcollege.mapper.OrderMapper;
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
}
