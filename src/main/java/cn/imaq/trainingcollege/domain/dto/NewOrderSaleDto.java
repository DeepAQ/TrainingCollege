package cn.imaq.trainingcollege.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class NewOrderSaleDto {
    private Integer classId;

    private String email;

    private List<String> names;
}
