package cn.imaq.trainingcollege.controller;

import cn.imaq.autumn.core.annotation.Autumnwired;
import cn.imaq.autumn.rest.annotation.RequestMapping;
import cn.imaq.autumn.rest.annotation.RestController;
import cn.imaq.autumn.rest.annotation.param.JSONBody;
import cn.imaq.autumn.rest.core.RequestMethod;
import cn.imaq.trainingcollege.domain.dto.CollegeSettlementDto;
import cn.imaq.trainingcollege.domain.dto.LoginClaim;
import cn.imaq.trainingcollege.domain.dto.Response;
import cn.imaq.trainingcollege.domain.dto.SettlementAddDto;
import cn.imaq.trainingcollege.domain.enumeration.UserType;
import cn.imaq.trainingcollege.service.SettlementService;
import cn.imaq.trainingcollege.support.annotation.JWTClaim;

import java.util.List;

@RestController
@RequestMapping(value = "/settlement", method = RequestMethod.POST)
public class SettlementController {
    @Autumnwired
    private SettlementService settlementService;

    @RequestMapping("/colleges")
    public Response<List<CollegeSettlementDto>> colleges(@JWTClaim LoginClaim claim) {
        if (claim == null || claim.getUserType() != UserType.Manager) {
            return Response.ofFailure("无权限");
        }
        return Response.ofSuccess(settlementService.getCollegeSettlements());
    }

    @RequestMapping("/add")
    public Response add(@JWTClaim LoginClaim claim, @JSONBody SettlementAddDto dto) {
        if (claim == null || claim.getUserType() != UserType.Manager) {
            return Response.ofFailure("无权限");
        }
        settlementService.addSettlement(dto.getCollegeId(), dto.getOrigAmount(), dto.getRatio());
        return Response.ofSuccess();
    }
}
