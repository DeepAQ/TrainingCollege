package cn.imaq.trainingcollege.controller;

import cn.imaq.autumn.core.annotation.Autumnwired;
import cn.imaq.autumn.rest.annotation.RequestMapping;
import cn.imaq.autumn.rest.annotation.RestController;
import cn.imaq.autumn.rest.annotation.param.JSONBody;
import cn.imaq.autumn.rest.core.RequestMethod;
import cn.imaq.trainingcollege.domain.dto.CollegeProfileDto;
import cn.imaq.trainingcollege.domain.dto.LoginClaim;
import cn.imaq.trainingcollege.domain.dto.Response;
import cn.imaq.trainingcollege.domain.entity.CollegeProfile;
import cn.imaq.trainingcollege.domain.enumeration.UserType;
import cn.imaq.trainingcollege.service.CollegeService;
import cn.imaq.trainingcollege.support.annotation.JWTClaim;

@RestController
@RequestMapping(value = "/college", method = RequestMethod.POST)
public class CollegeController {
    @Autumnwired
    private CollegeService collegeService;

    @RequestMapping("/profile")
    public Response<CollegeProfileDto> profile(@JWTClaim LoginClaim claim) {
        if (claim == null || claim.getUserType() != UserType.College) {
            return Response.ofFailure("无权限");
        }
        return Response.ofSuccess(collegeService.getProfiles(claim.getUserId()));
    }

    @RequestMapping("/profile/edit")
    public Response editProfile(@JWTClaim LoginClaim claim, @JSONBody CollegeProfile profile) {
        if (claim == null || claim.getUserType() != UserType.College) {
            return Response.ofFailure("无权限");
        }
        collegeService.editProfile(claim.getUserId(), profile);
        return Response.ofSuccess();
    }
}
