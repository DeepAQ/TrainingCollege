package cn.imaq.trainingcollege.domain.anal;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@Data
public class CollegeIncomeAnal {
    private Map<String, Integer> monthly = new TreeMap<>();

    private Integer total;

    private Integer onlineTotal;

    private Integer offlineTotal;

    private Integer avgPrice;

    private Double finishRate;

    private Map<String, Integer> byCourse = new HashMap<>();

    private Map<String, Integer> byTeacher = new HashMap<>();
}
