package cn.imaq.trainingcollege.controller;

import cn.imaq.autumn.core.annotation.Autumnwired;
import cn.imaq.autumn.rest.annotation.RequestMapping;
import cn.imaq.autumn.rest.annotation.RestController;
import cn.imaq.autumn.rest.annotation.param.JSONBody;
import cn.imaq.trainingcollege.domain.dto.LoginClaim;
import cn.imaq.trainingcollege.domain.dto.Response;
import cn.imaq.trainingcollege.domain.entity.Grade;
import cn.imaq.trainingcollege.domain.entity.Record;
import cn.imaq.trainingcollege.domain.enumeration.UserType;
import cn.imaq.trainingcollege.service.RecordService;
import cn.imaq.trainingcollege.support.annotation.JWTClaim;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@RestController
@RequestMapping("/record")
public class RecordController {
    @Autumnwired
    private RecordService recordService;

    @RequestMapping("/records")
    public Response<List<Record>> records(@JSONBody("participantId") Integer participantId) {
        return Response.ofSuccess(recordService.getRecords(participantId));
    }

    @RequestMapping("/grades")
    public Response<List<Grade>> grades(@JSONBody("participantId") Integer participantId) {
        return Response.ofSuccess(recordService.getGrades(participantId));
    }

    @RequestMapping("/addrecord")
    public Response addRecord(@JWTClaim LoginClaim claim, @JSONBody Record record) {
        if (claim == null || claim.getUserType() != UserType.College) {
            return Response.ofFailure("无权限");
        }
        recordService.addRecord(record);
        return Response.ofSuccess();
    }

    @RequestMapping("/addgrade")
    public Response addGrade(@JWTClaim LoginClaim claim, @JSONBody Grade grade) {
        if (StringUtils.isBlank(grade.getGrade())) {
            return Response.ofFailure("请输入成绩");
        }
        if (claim == null || claim.getUserType() != UserType.College) {
            return Response.ofFailure("无权限");
        }
        recordService.addGrade(grade);
        return Response.ofSuccess();
    }
}
