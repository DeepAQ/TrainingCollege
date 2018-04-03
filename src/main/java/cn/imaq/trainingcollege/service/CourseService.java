package cn.imaq.trainingcollege.service;

import cn.imaq.autumn.core.annotation.Autumnwired;
import cn.imaq.autumn.core.annotation.Component;
import cn.imaq.trainingcollege.domain.entity.Course;
import cn.imaq.trainingcollege.mapper.CourseMapper;

import java.util.List;

@Component
public class CourseService {
    @Autumnwired
    private CourseMapper courseMapper;

    public List<Course> getCoursesOfCollege(Integer collegeId) {
        return courseMapper.getByCollegeId(collegeId);
    }

    public void addCourse(Integer collegeId, Course course) {
        course.setCollegeId(collegeId);
        courseMapper.insert(course);
    }
}
