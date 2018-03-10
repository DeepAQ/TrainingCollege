package cn.imaq.trainingcollege.domain.dto;

import lombok.Data;

@Data
public class StudentRegisterDto {
    private String email;

    private String password;

    private String name;
}
