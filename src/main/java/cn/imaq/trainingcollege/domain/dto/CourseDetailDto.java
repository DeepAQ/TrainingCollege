package cn.imaq.trainingcollege.domain.dto;

import cn.imaq.trainingcollege.domain.entity.CollegeProfile;
import cn.imaq.trainingcollege.domain.entity.CourseClass;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CourseDetailDto {
    private Integer id;

    private String title;

    private String description;

    private CollegeProfile college;

    private String tags;

    private Integer startTime;

    private Integer period;

    private Integer weeks;

    private Integer avgPrice;

    private List<CourseClass> classes;
}
