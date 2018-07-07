package cn.imaq.trainingcollege.domain.anal;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@Data
public class ManagerIncomeAnal {
    private Map<String, Integer> monthlyTotal = new TreeMap<>();

    private Map<String, Integer> monthlyOrders = new TreeMap<>();

    private Integer total;

    private Integer orders;

    private Integer onlineTotal;

    private Integer offlineTotal;

    private Integer avgPrice;

    private Double finishRate;

    private Map<String, Integer> byCollege = new HashMap<>();

    private Map<String, Integer> byTags = new HashMap<>();

    private Map<String, Integer> ordersByCollege = new HashMap<>();

    private Map<String, Integer> ordersByTags = new HashMap<>();
}
