package cn.imaq.trainingcollege.domain.anal;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@Data
public class StudentStudyAnal {
    private Integer total;

    private Map<String, Integer> monthly = new TreeMap<>();

    private Double cancelRate;

    private Map<String, Integer> byCourse = new HashMap<>();

    private Map<String, Integer> byTags = new HashMap<>();
}
