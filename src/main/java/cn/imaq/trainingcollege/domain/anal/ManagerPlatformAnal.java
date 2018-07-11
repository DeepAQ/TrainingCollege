package cn.imaq.trainingcollege.domain.anal;

import lombok.Data;

import java.util.Map;
import java.util.TreeMap;

@Data
public class ManagerPlatformAnal {
    private Integer totalStudent;

    private Integer totalCollege;

    private Map<String, Integer> monthlyStudent = new TreeMap<>();

    private Map<String, Integer> monthlyCollege = new TreeMap<>();
}
