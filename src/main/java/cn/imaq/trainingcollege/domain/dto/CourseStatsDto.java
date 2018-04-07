package cn.imaq.trainingcollege.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CourseStatsDto {
    private Integer id;

    private String name;

    private Integer totalCount;

    private Integer totalAmount;

    private Integer cancelCount;
}
