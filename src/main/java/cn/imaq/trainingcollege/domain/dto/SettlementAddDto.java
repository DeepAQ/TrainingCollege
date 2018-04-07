package cn.imaq.trainingcollege.domain.dto;

import lombok.Data;

@Data
public class SettlementAddDto {
    private Integer collegeId;

    private Integer origAmount;

    private Integer ratio;
}
