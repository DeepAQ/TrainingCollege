package cn.imaq.trainingcollege.domain.dto;

import cn.imaq.trainingcollege.domain.entity.Student;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentProfileDto {
    private String email;

    private String name;

    private Student.Status status;
}
