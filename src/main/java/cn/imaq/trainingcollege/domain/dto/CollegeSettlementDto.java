package cn.imaq.trainingcollege.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CollegeSettlementDto {
    private Integer collegeId;

    private String collegeName;

    private Integer totalIncome;

    private Integer settledIncome;
}
