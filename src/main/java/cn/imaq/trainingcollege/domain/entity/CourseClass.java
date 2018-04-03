package cn.imaq.trainingcollege.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseClass {
    private Integer id;

    private Integer courseId;

    private String teacher;

    private Integer price;

    private Integer limit;
}
