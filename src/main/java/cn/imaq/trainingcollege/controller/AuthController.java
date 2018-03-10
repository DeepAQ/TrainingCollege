package cn.imaq.trainingcollege.controller;

import cn.imaq.autumn.core.annotation.Autumnwired;
import cn.imaq.autumn.rest.annotation.RequestMapping;
import cn.imaq.autumn.rest.annotation.RestController;
import cn.imaq.autumn.rest.annotation.param.JSONBody;
import cn.imaq.autumn.rest.core.RequestMethod;
import cn.imaq.trainingcollege.domain.dto.Response;
import cn.imaq.trainingcollege.domain.dto.StudentRegisterDto;
import cn.imaq.trainingcollege.service.StudentService;
import org.apache.commons.lang3.StringUtils;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autumnwired
    private StudentService studentService;

    @RequestMapping(value = "/student/register", method = RequestMethod.POST)
    public Response studentReg(@JSONBody StudentRegisterDto dto) {
        if (StringUtils.isAnyBlank(dto.getEmail(), dto.getPassword(), dto.getName())) {
            return Response.ofFailure("请填写完整注册信息");
        }
        studentService.register(dto);
        return Response.ofSuccess();
    }
}
