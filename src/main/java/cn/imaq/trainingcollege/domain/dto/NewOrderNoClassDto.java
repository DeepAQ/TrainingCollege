package cn.imaq.trainingcollege.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class NewOrderNoClassDto {
    private Integer courseId;

    private List<String> names;
}
