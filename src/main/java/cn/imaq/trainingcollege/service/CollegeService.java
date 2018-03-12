package cn.imaq.trainingcollege.service;

import cn.imaq.autumn.core.annotation.Autumnwired;
import cn.imaq.autumn.core.annotation.Component;
import cn.imaq.trainingcollege.domain.dto.CollegeLoginDto;
import cn.imaq.trainingcollege.domain.dto.CollegeRegisterDto;
import cn.imaq.trainingcollege.domain.dto.LoginClaimDto;
import cn.imaq.trainingcollege.domain.dto.LoginResultDto;
import cn.imaq.trainingcollege.domain.entity.College;
import cn.imaq.trainingcollege.domain.entity.CollegeProfile;
import cn.imaq.trainingcollege.domain.enumeration.UserType;
import cn.imaq.trainingcollege.mapper.CollegeMapper;
import cn.imaq.trainingcollege.mapper.CollegeProfileMapper;
import cn.imaq.trainingcollege.support.exception.ServiceException;
import cn.imaq.trainingcollege.util.HashUtil;
import cn.imaq.trainingcollege.util.JWTUtil;

@Component
public class CollegeService {
    @Autumnwired
    private CollegeMapper collegeMapper;

    @Autumnwired
    private CollegeProfileMapper collegeProfileMapper;

    public LoginResultDto login(CollegeLoginDto dto) {
        Integer id = Integer.valueOf(dto.getId());
        College college = collegeMapper.getById(id);
        if (college == null || !college.getPwdHash().equals(HashUtil.hash(dto.getPassword()))) {
            throw new ServiceException("编号或密码错误");
        }
        if (college.getProfileId() == null) {
            throw new ServiceException("该账号尚未被审批，暂不能登录");
        }
        LoginClaimDto claim = new LoginClaimDto(college.getId(), UserType.College);
        String token = JWTUtil.sign(claim);
        return new LoginResultDto(token, UserType.College, false);
    }

    public Integer register(CollegeRegisterDto dto) {
        CollegeProfile profile = CollegeProfile.builder()
                .name(dto.getName())
                .location(dto.getLocation())
                .description(dto.getDescription())
                .build();
        collegeProfileMapper.insert(profile);
        College college = College.builder()
                .pwdHash(HashUtil.hash(dto.getPassword()))
                .pendingProfileId(profile.getId())
                .build();
        collegeMapper.insert(college);
        return college.getId();
    }
}
