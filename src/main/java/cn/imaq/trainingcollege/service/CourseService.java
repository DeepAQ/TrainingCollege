package cn.imaq.trainingcollege.service;

import cn.imaq.autumn.core.annotation.Autumnwired;
import cn.imaq.autumn.core.annotation.Component;
import cn.imaq.trainingcollege.domain.dto.*;
import cn.imaq.trainingcollege.domain.entity.CollegeProfile;
import cn.imaq.trainingcollege.domain.entity.Course;
import cn.imaq.trainingcollege.domain.entity.CourseClass;
import cn.imaq.trainingcollege.mapper.ClassMapper;
import cn.imaq.trainingcollege.mapper.CourseMapper;
import cn.imaq.trainingcollege.mapper.ParticipantMapper;
import org.apache.commons.lang3.StringUtils;

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
    private ParticipantMapper participantMapper;

    @Autumnwired
    private CollegeService collegeService;

    @Autumnwired
    private OrderService orderService;

    public List<CourseListDto> getCourseList(String kw) {
        List<Course> courses;
        if (StringUtils.isBlank(kw)) {
            courses = courseMapper.getAll();
        } else {
            courses = courseMapper.getByKeyword(kw);
        }
        return courses.stream().map(x -> {
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
        int totalLimit = classes.stream().mapToInt(CourseClass::getLimit).sum();
        int avgPrice;
        if (totalLimit > 0) {
            avgPrice = classes.stream().mapToInt(x -> x.getPrice() * x.getLimit()).sum() / totalLimit;
        } else {
            avgPrice = 0;
        }
        return CourseDetailDto.builder()
                .id(course.getId())
                .college(collegeProfile)
                .title(course.getTitle())
                .description(course.getDescription())
                .tags(course.getTags())
                .startTime(course.getStartTime())
                .period(course.getPeriod())
                .weeks(course.getWeeks())
                .avgPrice(avgPrice)
                .classes(classes)
                .build();
    }

    public void addCourse(Integer collegeId, Course course) {
        course.setCollegeId(collegeId);
        courseMapper.insert(course);
    }

    public List<ClassManageDto> getClassesOfCourse(Integer courseId) {
        return classMapper.getByCourseId(courseId).stream().map(c -> {
            int paid = participantMapper.countByClassId(c.getId());
            return ClassManageDto.builder()
                    .id(c.getId())
                    .courseId(c.getCourseId())
                    .teacher(c.getTeacher())
                    .price(c.getPrice())
                    .limit(c.getLimit())
                    .paid(paid)
                    .build();
        }).collect(Collectors.toList());
    }

    public void addClass(Integer courseId, CourseClass courseClass) {
        courseClass.setCourseId(courseId);
        classMapper.insert(courseClass);
    }

    public List<ParticipantDto> getParticipantsOfClass(Integer classId) {
        return participantMapper.getByClassId(classId).stream().map(p -> {
            return ParticipantDto.builder()
                    .id(p.getId())
                    .name(p.getName())
                    .build();
        }).collect(Collectors.toList());
    }

    public List<CourseParticipantDto> getParticipantsOfStudent(Integer studentId) {
        return participantMapper.getByStudentId(studentId).stream().map(p -> {
            String teacher = "未分配班级";
            if (p.getClassId() > 0) {
                CourseClass courseClass = classMapper.getById(p.getClassId());
                teacher = courseClass.getTeacher();
            }
            Course course = courseMapper.getById(p.getCourseId());
            return CourseParticipantDto.builder()
                    .id(p.getId())
                    .courseId(p.getCourseId())
                    .courseName(course.getTitle())
                    .teacher(teacher)
                    .name(p.getName())
                    .build();
        }).collect(Collectors.toList());
    }
}
