package cn.imaq.trainingcollege.controller;

import cn.imaq.autumn.core.annotation.Autumnwired;
import cn.imaq.autumn.rest.annotation.RequestMapping;
import cn.imaq.autumn.rest.annotation.RestController;
import cn.imaq.autumn.rest.annotation.param.JSONBody;
import cn.imaq.autumn.rest.core.RequestMethod;
import cn.imaq.trainingcollege.domain.dto.LoginResultDto;
import cn.imaq.trainingcollege.domain.dto.Response;
import cn.imaq.trainingcollege.domain.dto.StudentLoginDto;
import cn.imaq.trainingcollege.domain.dto.StudentRegisterDto;
import cn.imaq.trainingcollege.service.StudentService;
import org.apache.commons.lang3.StringUtils;

@RestController
@RequestMapping(value = "/auth", method = RequestMethod.POST)
public class AuthController {
    @Autumnwired
    private StudentService studentService;

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
