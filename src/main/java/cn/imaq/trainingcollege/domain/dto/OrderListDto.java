package cn.imaq.trainingcollege.domain.dto;

import cn.imaq.trainingcollege.domain.entity.Order;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderListDto {
    private Integer id;

    private String courseName;

    private Integer count;

    private Integer origPrice;

    private Order.Status status;
}
