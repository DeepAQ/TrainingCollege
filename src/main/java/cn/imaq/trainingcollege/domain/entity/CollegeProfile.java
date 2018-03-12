package cn.imaq.trainingcollege.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CollegeProfile {
    private Integer id;

    private String name;

    private String location;

    private String description;
}
