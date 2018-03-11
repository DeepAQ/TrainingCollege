package cn.imaq.trainingcollege.controller;

import cn.imaq.autumn.core.annotation.Autumnwired;
import cn.imaq.autumn.rest.annotation.RequestMapping;
import cn.imaq.autumn.rest.annotation.RestController;
import cn.imaq.autumn.rest.annotation.param.JSONBody;
import cn.imaq.autumn.rest.core.RequestMethod;
import cn.imaq.trainingcollege.domain.dto.*;
import cn.imaq.trainingcollege.service.StudentService;
import cn.imaq.trainingcollege.support.annotation.JWTClaim;
import org.apache.commons.lang3.StringUtils;

@RestController
@RequestMapping(value = "/auth", method = RequestMethod.POST)
public class AuthController {
    @Autumnwired
    private StudentService studentService;

    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public Response<LoginClaimDto> getStatus(@JWTClaim LoginClaimDto claim) {
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
}
