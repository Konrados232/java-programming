package uj.java.w7.insurance;

import java.math.BigDecimal;

public class Pair implements Comparable<Pair> {

    private String county;
    private BigDecimal value;

    String county() {
        return county;
    }

    BigDecimal value() {
        return value;
    }

    public void addToValue(BigDecimal newVal) {
        value = value.add(newVal);
    }

    public Pair(String county, BigDecimal value) {
        this.county = county;
        this.value = value;
    }

    @Override
    public int compareTo(Pair o) {
        return Integer.compare(0, this.value.compareTo(o.value));
    }

    @Override
    public String toString() {
        return county + "," + value;
    }
}
