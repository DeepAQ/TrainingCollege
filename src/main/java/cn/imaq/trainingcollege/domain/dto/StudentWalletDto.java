package cn.imaq.trainingcollege.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentWalletDto {
    private Integer balance;

    private Integer points;

    private Integer consumption;

    private Integer discount;
}
