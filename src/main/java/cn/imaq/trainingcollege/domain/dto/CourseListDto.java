package cn.imaq.trainingcollege.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CourseListDto {
    private Integer id;

    private String title;

    private String collegeName;

    private String tags;

    private Integer startTime;

    private Integer period;

    private Integer weeks;
}
