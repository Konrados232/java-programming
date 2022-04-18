package uj.java.w7.insurance;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;


public class InsuranceValueCollector implements Collector<InsuranceEntry, List<Pair>, List<Pair>> {

    private Pair getCountyFromTheList(List<Pair> currentList, InsuranceEntry currentInsurance) {
        for (var x : currentList) {
            if (x.county().equals(currentInsurance.county()))
                return x;
        }

        return null;
    }

    private void addNewCounty(List<Pair> currentList, InsuranceEntry currentInsurance) {
        Pair foundCounty = getCountyFromTheList(currentList, currentInsurance);

        BigDecimal result = currentInsurance.tiv2012().subtract(currentInsurance.tiv2011());

        if (foundCounty == null) {
            currentList.add(new Pair(currentInsurance.county(), result));
        } else {
            foundCounty.addToValue(result);
        }
    }

    @Override
    public Supplier<List<Pair>> supplier() {
        return ArrayList::new;
    }

    @Override
    public BiConsumer<List<Pair>, InsuranceEntry> accumulator() {
        return this::addNewCounty;
    }

    @Override
    public BinaryOperator<List<Pair>> combiner() {
        return (firstList, secondList) -> {
            firstList.addAll(secondList);
            return firstList;
        };
    }

    @Override
    public Function<List<Pair>, List<Pair>> finisher() {
        return (list) -> {
            Collections.sort(list);
            return list.subList(0, 10);
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of(Characteristics.UNORDERED);
    }
}
