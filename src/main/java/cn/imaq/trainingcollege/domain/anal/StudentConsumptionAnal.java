package cn.imaq.trainingcollege.domain.anal;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class StudentConsumptionAnal {
    private Map<String, Integer> monthly = new HashMap<>();

    private Integer avgPrice;

    private Double finishRate;

    private Map<String, Integer> byTags = new HashMap<>();

    private Map<String, Integer> byColleges = new HashMap<>();
}
