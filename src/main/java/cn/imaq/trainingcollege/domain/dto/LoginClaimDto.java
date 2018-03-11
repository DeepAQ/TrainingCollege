package cn.imaq.trainingcollege.domain.dto;

import cn.imaq.trainingcollege.domain.enumeration.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginClaimDto {
    private Integer userId;

    private UserType userType;
}
