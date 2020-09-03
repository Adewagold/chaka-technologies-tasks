package com.chaka.tech.chakatakehometask.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * *  Created by Adewale Adeleye on 2020-09-02
 **/
public class Statistics {
    private BigDecimal sum = new BigDecimal("0");
    private BigDecimal avg = new BigDecimal("0");
    private BigDecimal max = null;
    private BigDecimal min = null;
    private Long count = 0L;

    public void add(BigDecimal occurrence) {
        if (this.min == null || occurrence.compareTo(this.min) < 0)
            this.min = occurrence;
        if (this.max == null || occurrence.compareTo(this.max) > 0)
            this.max = occurrence;
        this.count++;
        this.sum = this.sum.add(occurrence);
        this.avg = this.sum.divide(new BigDecimal(this.count), 2, RoundingMode.HALF_EVEN);
    }

    public void add(Statistics statistics) {
        if (statistics.getMin() != null) {
            if (this.getMin() == null || statistics.getMin().compareTo(this.getMin()) < 0)
                this.setMin(statistics.getMin());
        }

        if (statistics.getMax() != null) {
            if (this.getMax() == null || statistics.getMax().compareTo(this.getMax()) > 0)
                this.setMax(statistics.getMax());
        }

        this.setSum(this.getSum().add(statistics.getSum()));
        this.setCount(this.getCount() + statistics.getCount());
        if (this.getCount() != 0)
            this.setAvg(this.getSum().divide(new BigDecimal(this.getCount()), 2, RoundingMode.HALF_EVEN));

    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public BigDecimal getAvg() {
        return avg;
    }

    public void setAvg(BigDecimal avg) {
        this.avg = avg;
    }

    public BigDecimal getMax() {
        return max;
    }

    public void setMax(BigDecimal max) {
        this.max = max;
    }

    public BigDecimal getMin() {
        return min;
    }

    public void setMin(BigDecimal min) {
        this.min = min;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
