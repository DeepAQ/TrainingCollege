package cn.imaq.trainingcollege.domain.dto;

import lombok.Data;

@Data
public class ModifyPasswordDto {
    private String oldPassword;

    private String newPassword;
}
