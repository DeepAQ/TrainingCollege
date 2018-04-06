package cn.imaq.trainingcollege.domain.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Order {
    private Integer id;

    private Integer studentId;

    private Integer collegeId;

    private Integer courseId;

    private Integer classId;

    private Integer count;

    private Integer orig_price;

    private Integer pay_price;

    private Status status;

    private Integer created;

    public enum Status {
        NOT_PAID, PAID, CANCELLED, INVALID
    }
}
