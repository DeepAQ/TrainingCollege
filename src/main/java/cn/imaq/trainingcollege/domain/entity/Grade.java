package cn.imaq.trainingcollege.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Grade {
    private Integer id;

    private Integer participantId;

    private Integer date;

    private String grade;

    private String note;
}
