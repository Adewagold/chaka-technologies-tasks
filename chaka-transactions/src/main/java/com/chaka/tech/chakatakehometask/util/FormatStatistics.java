package com.chaka.tech.chakatakehometask.util;

import com.chaka.tech.chakatakehometask.model.Statistics;
import com.chaka.tech.chakatakehometask.model.StatisticsResponse;
import org.springframework.stereotype.Component;

/**
 * *  Created by Adewale Adeleye on 2020-09-02
 **/
@Component
public class FormatStatistics {
    private static String initialValue="0.00";
    public static StatisticsResponse formStatisticsResponse(Statistics statistics) {
        StatisticsResponse res = new StatisticsResponse();

        String avg = String.valueOf(statistics.getAvg());
        String max = null;
        if (statistics.getMax() != null)
            max = String.valueOf(statistics.getMax());
        String min = null;
        if (statistics.getMin() != null)
            min = String.valueOf(statistics.getMin());
        String sum = String.valueOf(statistics.getSum());
        if (max == null)
            max = "0";
        if (min == null)
            min = "0";
        String[] arr = avg.split("\\.");
        if (arr.length < 2) {
            if (avg != null)
                avg = avg.concat(".00");
            else
                avg = initialValue;

        }
        arr = sum.split("\\.");
        if (arr.length < 2) {
            if (sum != null)
                sum = sum.concat(".00");
            else
                sum = initialValue;

        }
        arr = min.split("\\.");
        if (arr.length < 2) {
            if (min != null)
                min = min.concat(".00");
            else
                min = initialValue;

        }
        arr = max.split("\\.");
        if (arr.length < 2) {
            if (max != null)
                max = max.concat(".00");
            else
                max = initialValue;
        }

        res.setAvg(avg);
        res.setSum(sum);
        res.setMax(max);
        res.setMin(min);
        res.setCount(statistics.getCount());
        return res;
    }
}
