package cn.imaq.trainingcollege.controller;

import cn.imaq.autumn.core.annotation.Autumnwired;
import cn.imaq.autumn.rest.annotation.RequestMapping;
import cn.imaq.autumn.rest.annotation.RestController;
import cn.imaq.autumn.rest.annotation.param.JSONBody;
import cn.imaq.autumn.rest.core.RequestMethod;
import cn.imaq.trainingcollege.domain.dto.*;
import cn.imaq.trainingcollege.domain.enumeration.UserType;
import cn.imaq.trainingcollege.service.CollegeService;
import cn.imaq.trainingcollege.service.ManagerService;
import cn.imaq.trainingcollege.service.StudentService;
import cn.imaq.trainingcollege.support.annotation.JWTClaim;
import org.apache.commons.lang3.StringUtils;

@RestController
@RequestMapping(value = "/auth", method = RequestMethod.POST)
public class AuthController {
    @Autumnwired
    private StudentService studentService;

    @Autumnwired
    private CollegeService collegeService;

    @Autumnwired
    private ManagerService managerService;

    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public Response<LoginClaim> getStatus(@JWTClaim LoginClaim claim) {
        return Response.ofSuccess(claim);
    }

    @RequestMapping("/student/register")
    public Response studentReg(@JSONBody StudentRegisterDto dto) {
        if (StringUtils.isAnyBlank(dto.getEmail(), dto.getPassword(), dto.getName())) {
            return Response.ofFailure("请填写完整注册信息");
        }
        studentService.register(dto);
        return Response.ofSuccess();
    }

    @RequestMapping("/student/login")
    public Response<LoginResultDto> studentLogin(@JSONBody StudentLoginDto dto) {
        if (StringUtils.isAnyBlank(dto.getEmail(), dto.getPassword())) {
            return Response.ofFailure("请填写 E-mail 和密码");
        }
        return Response.ofSuccess(studentService.login(dto));
    }

    @RequestMapping("/student/resend")
    public Response resendEmail(@JWTClaim LoginClaim claim) {
        if (claim == null || claim.getUserType() != UserType.Student) {
            return Response.ofFailure("登录信息无效，请重新登录");
        }
        studentService.sendActivicationEmail(claim.getUserId());
        return Response.ofSuccess();
    }

    @RequestMapping("/college/register")
    public Response<Integer> collegeReg(@JSONBody CollegeRegisterDto dto) {
        if (StringUtils.isAnyBlank(dto.getName(), dto.getPassword(), dto.getLocation())) {
            return Response.ofFailure("请填写完整注册信息");
        }
        return Response.ofSuccess(collegeService.register(dto));
    }

    @RequestMapping("/college/login")
    public Response<LoginResultDto> collegeLogin(@JSONBody CollegeLoginDto dto) {
        if (StringUtils.isAnyBlank(dto.getId(), dto.getPassword())) {
            return Response.ofFailure("请填写机构编号和密码");
        }
        return Response.ofSuccess(collegeService.login(dto));
    }

    @RequestMapping("/manager/login")
    public Response<LoginResultDto> collegeLogin(@JSONBody ManagerLoginDto dto) {
        if (StringUtils.isAnyBlank(dto.getUsername(), dto.getPassword())) {
            return Response.ofFailure("请填写用户名和密码");
        }
        return Response.ofSuccess(managerService.login(dto));
    }
}
