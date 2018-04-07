package cn.imaq.trainingcollege.controller;

import cn.imaq.autumn.core.annotation.Autumnwired;
import cn.imaq.autumn.rest.annotation.RequestMapping;
import cn.imaq.autumn.rest.annotation.RestController;
import cn.imaq.autumn.rest.annotation.param.JSONBody;
import cn.imaq.autumn.rest.core.RequestMethod;
import cn.imaq.trainingcollege.domain.dto.*;
import cn.imaq.trainingcollege.domain.enumeration.UserType;
import cn.imaq.trainingcollege.service.StudentService;
import cn.imaq.trainingcollege.support.annotation.JWTClaim;
import org.apache.commons.lang3.StringUtils;

@RestController
@RequestMapping(value = "/student", method = RequestMethod.POST)
public class StudentController {
    @Autumnwired
    private StudentService studentService;

    @RequestMapping("/modifypassword")
    public Response modifyPassword(@JWTClaim LoginClaim claim, @JSONBody ModifyPasswordDto dto) {
        if (StringUtils.isAnyBlank(dto.getOldPassword(), dto.getNewPassword())) {
            return Response.ofFailure("请输入旧密码和新密码");
        }
        if (claim == null || claim.getUserType() != UserType.Student) {
            return Response.ofFailure("无权限");
        }
        studentService.modifyPassword(claim.getUserId(), dto.getOldPassword(), dto.getNewPassword());
        return Response.ofSuccess();
    }

    @RequestMapping("/profile")
    public Response<StudentProfileDto> profile(@JWTClaim LoginClaim claim) {
        if (claim == null || claim.getUserType() != UserType.Student) {
            return Response.ofFailure("无权限");
        }
        return Response.ofSuccess(studentService.getProfile(claim.getUserId()));
    }

    @RequestMapping("/terminate")
    public Response terminate(@JWTClaim LoginClaim claim) {
        if (claim == null || claim.getUserType() != UserType.Student) {
            return Response.ofFailure("无权限");
        }
        studentService.terminate(claim.getUserId());
        return Response.ofSuccess();
    }

    @RequestMapping("/wallet")
    public Response<StudentWalletDto> wallet(@JWTClaim LoginClaim claim) {
        if (claim == null || claim.getUserType() != UserType.Student) {
            return Response.ofFailure("无权限");
        }
        return Response.ofSuccess(studentService.getWallet(claim.getUserId()));
    }

    @RequestMapping("/wallet/exchange")
    public Response exchange(@JWTClaim LoginClaim claim, @JSONBody("count") Integer count) {
        if (claim == null || claim.getUserType() != UserType.Student) {
            return Response.ofFailure("无权限");
        }
        studentService.exchangePoints(claim.getUserId(), count);
        return Response.ofSuccess();
    }
}
