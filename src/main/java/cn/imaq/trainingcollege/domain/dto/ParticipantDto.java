package cn.imaq.trainingcollege.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParticipantDto {
    private Integer id;

    private String name;
}
