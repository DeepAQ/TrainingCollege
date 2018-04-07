package cn.imaq.trainingcollege.domain.dto;

import cn.imaq.trainingcollege.domain.entity.Settlement;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CollegeStatsDto {
    private Integer settledIncome;

    private List<Settlement> settlements;

    private List<CourseStatsDto> courseStats;
}
