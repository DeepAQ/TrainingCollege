package cn.imaq.trainingcollege.controller;

import cn.imaq.autumn.core.annotation.Autumnwired;
import cn.imaq.autumn.rest.annotation.RequestMapping;
import cn.imaq.autumn.rest.annotation.RestController;
import cn.imaq.autumn.rest.annotation.param.JSONBody;
import cn.imaq.autumn.rest.core.RequestMethod;
import cn.imaq.trainingcollege.domain.dto.ClassInfoDto;
import cn.imaq.trainingcollege.domain.dto.LoginClaim;
import cn.imaq.trainingcollege.domain.dto.NewOrderDto;
import cn.imaq.trainingcollege.domain.dto.Response;
import cn.imaq.trainingcollege.domain.enumeration.UserType;
import cn.imaq.trainingcollege.service.OrderService;
import cn.imaq.trainingcollege.support.annotation.JWTClaim;

@RestController
@RequestMapping(value = "/order", method = RequestMethod.POST)
public class OrderController {
    @Autumnwired
    private OrderService orderService;

    @RequestMapping("/classinfo")
    public Response<ClassInfoDto> classInfo(@JSONBody("classId") Integer classId) {
        return Response.ofSuccess(orderService.getClassInfo(classId));
    }

    @RequestMapping("/new")
    public Response newOrder(@JWTClaim LoginClaim claim, @JSONBody NewOrderDto dto) {
        if (claim == null || claim.getUserType() != UserType.Student) {
            return Response.ofFailure("无权限");
        }
        orderService.newOrder(claim.getUserId(), dto);
        return Response.ofSuccess();
    }
}
