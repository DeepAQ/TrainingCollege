package cn.imaq.trainingcollege.domain.dto;

import cn.imaq.trainingcollege.domain.entity.CourseClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassInfoDto {
    private CourseClass courseClass;

    private Integer available;
}
