package cn.imaq.trainingcollege.domain.dto;

import cn.imaq.trainingcollege.domain.enumeration.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResultDto {
    private String token;

    private String displayName;

    private UserType userType;

    private boolean needVerify;
}
