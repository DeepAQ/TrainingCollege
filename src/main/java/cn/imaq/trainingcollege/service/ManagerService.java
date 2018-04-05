package cn.imaq.trainingcollege.service;

import cn.imaq.autumn.core.annotation.Autumnwired;
import cn.imaq.autumn.core.annotation.Component;
import cn.imaq.trainingcollege.domain.dto.LoginClaim;
import cn.imaq.trainingcollege.domain.dto.LoginResultDto;
import cn.imaq.trainingcollege.domain.dto.ManagerLoginDto;
import cn.imaq.trainingcollege.domain.entity.Manager;
import cn.imaq.trainingcollege.domain.enumeration.UserType;
import cn.imaq.trainingcollege.mapper.ManagerMapper;
import cn.imaq.trainingcollege.support.exception.ServiceException;
import cn.imaq.trainingcollege.util.HashUtil;
import cn.imaq.trainingcollege.util.JWTUtil;

@Component
public class ManagerService {
    @Autumnwired
    private ManagerMapper managerMapper;

    public LoginResultDto login(ManagerLoginDto dto) {
        Manager manager = managerMapper.getByUsername(dto.getUsername());
        if (manager == null || !manager.getPwdHash().equals(HashUtil.hash(dto.getPassword()))) {
            throw new ServiceException("用户名或密码错误");
        }
        LoginClaim claim = new LoginClaim(manager.getId(), UserType.Manager);
        String token = JWTUtil.sign(claim);
        return new LoginResultDto(token, manager.getUsername(), UserType.Manager, false);
    }
}
