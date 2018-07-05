package cn.imaq.trainingcollege.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static String toYearMonth(long timestamp) {
        return new SimpleDateFormat("yyyy/M").format(new Date(timestamp * 1000));
    }
}
