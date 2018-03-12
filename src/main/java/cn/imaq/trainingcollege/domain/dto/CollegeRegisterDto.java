package cn.imaq.trainingcollege.domain.dto;

import lombok.Data;

@Data
public class CollegeRegisterDto {
    private String password;

    private String name;

    private String location;

    private String description;
}
