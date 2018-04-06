package cn.imaq.trainingcollege.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class NewOrderDto {
    private Integer classId;

    private List<String> names;
}
