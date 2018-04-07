package cn.imaq.trainingcollege.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CourseParticipantDto {
    private Integer id;

    private Integer courseId;

    private String courseName;

    private String teacher;

    private String name;
}
