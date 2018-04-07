package cn.imaq.trainingcollege.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Settlement {
    private Integer id;

    private Integer collegeId;

    private Integer time;

    private Integer origAmount;

    private Integer realAmount;
}
