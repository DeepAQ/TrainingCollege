package cn.imaq.trainingcollege.controller;

import cn.imaq.autumn.core.annotation.Autumnwired;
import cn.imaq.autumn.rest.annotation.RequestMapping;
import cn.imaq.autumn.rest.annotation.RestController;
import cn.imaq.autumn.rest.annotation.param.JSONBody;
import cn.imaq.autumn.rest.annotation.param.RequestParam;
import cn.imaq.autumn.rest.core.RequestMethod;
import cn.imaq.trainingcollege.domain.dto.*;
import cn.imaq.trainingcollege.domain.entity.Course;
import cn.imaq.trainingcollege.domain.entity.CourseClass;
import cn.imaq.trainingcollege.domain.enumeration.UserType;
import cn.imaq.trainingcollege.service.CourseService;
import cn.imaq.trainingcollege.support.annotation.JWTClaim;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/course", method = RequestMethod.POST)
public class CourseController {
    @Autumnwired
    private CourseService courseService;

    @RequestMapping("/list")
    public Response<List<CourseListDto>> courseList() {
        return Response.ofSuccess(courseService.getCourseList());
    }

    @RequestMapping("/detail")
    public Response<CourseDetailDto> courseDetail(@JSONBody("courseId") Integer courseId) {
        return Response.ofSuccess(courseService.getCourseDetail(courseId));
    }

    @RequestMapping("/my")
    public Response<List<Course>> myCourses(@JWTClaim LoginClaim claim) {
        if (claim == null || claim.getUserType() != UserType.College) {
            return Response.ofFailure("无权限");
        }
        return Response.ofSuccess(courseService.getCoursesOfCollege(claim.getUserId()));
    }

    @RequestMapping("/add")
    public Response addCourse(@JWTClaim LoginClaim claim, @JSONBody Course course) {
        if (claim == null || claim.getUserType() != UserType.College) {
            return Response.ofFailure("无权限");
        }
        if (StringUtils.isAnyBlank(course.getTitle(), course.getTags(), course.getDescription())) {
            return Response.ofFailure("请填写完整信息");
        }
        if (course.getStartTime() <= System.currentTimeMillis() / 1000) {
            return Response.ofFailure("课程开始时间不可早于今天");
        }
        courseService.addCourse(claim.getUserId(), course);
        return Response.ofSuccess();
    }

    @RequestMapping("/classes")
    public Response<List<ClassManageDto>> classes(@JWTClaim LoginClaim claim, @JSONBody("courseId") Integer courseId) {
        if (claim == null || claim.getUserType() != UserType.College) {
            return Response.ofFailure("无权限");
        }
        Course course = courseService.getCourse(courseId);
        if (!Objects.equals(course.getCollegeId(), claim.getUserId())) {
            return Response.ofFailure("无权限");
        }
        return Response.ofSuccess(courseService.getClassesOfCourse(courseId));
    }

    @RequestMapping("/addclass")
    public Response addClass(@JWTClaim LoginClaim claim, @RequestParam("courseId") Integer courseId, @JSONBody CourseClass courseClass) {
        if (claim == null || claim.getUserType() != UserType.College) {
            return Response.ofFailure("无权限");
        }
        if (StringUtils.isAnyBlank(courseClass.getTeacher()) || courseClass.getPrice() <= 0 || courseClass.getLimit() <= 0) {
            return Response.ofFailure("请填写完整信息");
        }
        Course course = courseService.getCourse(courseId);
        if (!Objects.equals(course.getCollegeId(), claim.getUserId())) {
            return Response.ofFailure("无权限");
        }
        courseService.addClass(courseId, courseClass);
        return Response.ofSuccess();
    }

    @RequestMapping("/participants")
    public Response<List<ParticipantDto>> participants(@JWTClaim LoginClaim claim, @JSONBody("classId") Integer classId) {
        if (claim == null || claim.getUserType() != UserType.College) {
            return Response.ofFailure("无权限");
        }
        return Response.ofSuccess(courseService.getParticipantsOfClass(classId));
    }

    @RequestMapping("/participated")
    public Response<List<CourseParticipantDto>> participated(@JWTClaim LoginClaim claim) {
        if (claim == null || claim.getUserType() != UserType.Student) {
            return Response.ofFailure("无权限");
        }
        return Response.ofSuccess(courseService.getParticipantsOfStudent(claim.getUserId()));
    }
}
