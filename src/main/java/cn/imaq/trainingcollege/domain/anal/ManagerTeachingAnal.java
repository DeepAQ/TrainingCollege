package cn.imaq.trainingcollege.domain.anal;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@Data
public class ManagerTeachingAnal {
    private Map<String, Integer> monthly = new TreeMap<>();

    private Integer total;

    private Double cancelRate;

    private Map<String, Integer> byCollege = new HashMap<>();

    private Map<String, Integer> byTags = new HashMap<>();
}
