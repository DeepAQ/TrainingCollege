package cn.imaq.trainingcollege.service;

import cn.imaq.autumn.core.annotation.Autumnwired;
import cn.imaq.autumn.core.annotation.Component;
import cn.imaq.trainingcollege.domain.entity.CourseClass;
import cn.imaq.trainingcollege.domain.entity.Course;
import cn.imaq.trainingcollege.mapper.ClassMapper;
import cn.imaq.trainingcollege.mapper.CourseMapper;

import java.util.List;

@Component
public class CourseService {
    @Autumnwired
    private CourseMapper courseMapper;

    @Autumnwired
    private ClassMapper classMapper;

    public List<Course> getCoursesOfCollege(Integer collegeId) {
        return courseMapper.getByCollegeId(collegeId);
    }

    public Course getCourse(Integer courseId) {
        return courseMapper.getById(courseId);
    }

    public void addCourse(Integer collegeId, Course course) {
        course.setCollegeId(collegeId);
        courseMapper.insert(course);
    }

    public List<CourseClass> getClassesOfCourse(Integer courseId) {
        return classMapper.getByCourseId(courseId);
    }

    public void addClass(Integer courseId, CourseClass courseClass) {
        courseClass.setCourseId(courseId);
        classMapper.insert(courseClass);
    }
}
