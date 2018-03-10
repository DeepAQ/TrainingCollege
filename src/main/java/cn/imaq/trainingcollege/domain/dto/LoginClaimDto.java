package cn.imaq.trainingcollege.domain.dto;

import cn.imaq.trainingcollege.domain.enumeration.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginClaimDto {
    private Integer userId;

    private UserType userType;
}
