package cn.imaq.trainingcollege.controller;

import cn.imaq.autumn.core.annotation.Autumnwired;
import cn.imaq.autumn.rest.annotation.RequestMapping;
import cn.imaq.autumn.rest.annotation.RestController;
import cn.imaq.autumn.rest.annotation.param.JSONBody;
import cn.imaq.autumn.rest.core.RequestMethod;
import cn.imaq.trainingcollege.domain.anal.StudentConsumptionAnal;
import cn.imaq.trainingcollege.domain.dto.LoginClaim;
import cn.imaq.trainingcollege.domain.dto.Response;
import cn.imaq.trainingcollege.domain.enumeration.UserType;
import cn.imaq.trainingcollege.service.AnalService;
import cn.imaq.trainingcollege.support.annotation.JWTClaim;

@RestController
@RequestMapping(value = "/anal", method = RequestMethod.POST)
public class AnalController {
    @Autumnwired
    private AnalService analService;

    @RequestMapping(value = "/student/consumption")
    public Response<StudentConsumptionAnal> getStudentConsumptionAnal(@JWTClaim LoginClaim claim, @JSONBody("start") Integer start, @JSONBody("end") Integer end) {
        if (claim == null || claim.getUserType() != UserType.Student) {
            return Response.ofFailure("登录信息无效，请重新登录");
        }
        return Response.ofSuccess(analService.getStudentConsumptionAnal(claim.getUserId(), start, end));
    }
}
