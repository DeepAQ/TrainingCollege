package cn.imaq.trainingcollege.domain.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Participant {
    private Integer id;

    private Integer studentId;

    private Integer courseId;

    private Integer classId;

    private Integer orderId;

    private String name;

    private Status status;

    public enum Status {
        INVALID, VALID
    }
}
