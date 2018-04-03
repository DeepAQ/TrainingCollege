package cn.imaq.trainingcollege.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    private Integer id;

    private Integer collegeId;

    private String title;

    private String description;

    private String tags;

    private Integer startTime;

    private Integer period;

    private Integer weeks;
}
