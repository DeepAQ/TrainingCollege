package cn.imaq.trainingcollege.service;

import cn.imaq.autumn.core.annotation.Autumnwired;
import cn.imaq.autumn.core.annotation.Component;
import cn.imaq.trainingcollege.domain.dto.CollegeProfileDto;
import cn.imaq.trainingcollege.domain.dto.CourseDetailDto;
import cn.imaq.trainingcollege.domain.dto.CourseListDto;
import cn.imaq.trainingcollege.domain.entity.CollegeProfile;
import cn.imaq.trainingcollege.domain.entity.Course;
import cn.imaq.trainingcollege.domain.entity.CourseClass;
import cn.imaq.trainingcollege.mapper.ClassMapper;
import cn.imaq.trainingcollege.mapper.CourseMapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class CourseService {
    @Autumnwired
    private CourseMapper courseMapper;

    @Autumnwired
    private ClassMapper classMapper;

    @Autumnwired
    private CollegeService collegeService;

    public List<CourseListDto> getCourseList() {
        return courseMapper.getAll().stream().map(x -> {
            try {
                CollegeProfileDto profile = collegeService.getProfiles(x.getCollegeId());
                return CourseListDto.builder()
                        .id(x.getId())
                        .title(x.getTitle())
                        .collegeName(profile.getCurrent().getName())
                        .tags(x.getTags())
                        .startTime(x.getStartTime())
                        .period(x.getPeriod())
                        .weeks(x.getWeeks())
                        .build();
            } catch (Exception e) {
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public List<Course> getCoursesOfCollege(Integer collegeId) {
        return courseMapper.getByCollegeId(collegeId);
    }

    public Course getCourse(Integer courseId) {
        return courseMapper.getById(courseId);
    }

    public CourseDetailDto getCourseDetail(Integer courseId) {
        Course course = courseMapper.getById(courseId);
        CollegeProfile collegeProfile = collegeService.getProfiles(course.getCollegeId()).getCurrent();
        List<CourseClass> classes = classMapper.getByCourseId(courseId);
        return CourseDetailDto.builder()
                .id(course.getId())
                .college(collegeProfile)
                .title(course.getTitle())
                .description(course.getDescription())
                .tags(course.getTags())
                .startTime(course.getStartTime())
                .period(course.getPeriod())
                .weeks(course.getWeeks())
                .classes(classes)
                .build();
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
