package cn.imaq.trainingcollege.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Integer id;

    private Integer studentId;

    private Integer collegeId;

    private Integer courseId;

    private Integer classId;

    private Integer count;

    private Integer origPrice;

    private Integer payPrice;

    private Status status;

    private Integer created;

    public enum Status {
        NOT_PAID, PAID, CANCELLED, CLOSED
    }
}
