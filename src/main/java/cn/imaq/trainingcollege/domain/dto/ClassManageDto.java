package cn.imaq.trainingcollege.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClassManageDto {
    private Integer id;

    private Integer courseId;

    private String teacher;

    private Integer price;

    private Integer limit;

    private Integer paid;
}
