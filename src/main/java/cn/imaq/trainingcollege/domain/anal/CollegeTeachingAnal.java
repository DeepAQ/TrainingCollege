package cn.imaq.trainingcollege.domain.anal;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@Data
public class CollegeTeachingAnal {
    private Map<String, Integer> monthly = new TreeMap<>();

    private Integer total;

    private Integer avgPrice;

    private Double cancelRate;

    private Map<String, Integer> byCourse = new HashMap<>();

    private Map<String, Integer> byTeacher = new HashMap<>();
}
