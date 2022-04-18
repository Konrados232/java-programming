package uj.java.w7.insurance;

import java.io.*;
import java.math.BigDecimal;
import java.util.List;

public class FloridaInsurance {

    private static List<InsuranceEntry> insuranceEntries;
    private static InsuranceFileManager insuranceFileManager;

    private static long countNumberOfCounties() {
        return insuranceEntries
                .stream()
                .map(InsuranceEntry::county)
                .distinct()
                .count();
    }

    private static BigDecimal countSumOfAllInsurancesIn2012() {
        return insuranceEntries
                .stream()
                .map(InsuranceEntry::tiv2012)
                .reduce(new BigDecimal(0), BigDecimal::add);
    }

    private static List<Pair> countTop10CountiesWithBiggestInsuranceIncreaseBetween2011And2012() {
        return insuranceEntries
                .stream()
                .collect(new InsuranceValueCollector());
    }

    public static void main(String[] args)  {
        try {
            insuranceFileManager = new InsuranceFileManager();
            insuranceEntries = insuranceFileManager.convertFileFromZipToListOfInsuranceEntries("FL_insurance.csv.zip", "FL_insurance.csv");

            var count = countNumberOfCounties();
            insuranceFileManager.createOrReplaceFileIfExists("count.txt");
            insuranceFileManager.writeSingleLineToFile("count.txt", String.valueOf(count));

            var sum = countSumOfAllInsurancesIn2012();
            insuranceFileManager.createOrReplaceFileIfExists("tiv2012.txt");
            insuranceFileManager.writeSingleLineToFile("tiv2012.txt", sum.toPlainString());

            var listOfPairs = countTop10CountiesWithBiggestInsuranceIncreaseBetween2011And2012();
            insuranceFileManager.createOrReplaceFileIfExists("most_valuable.txt");
            insuranceFileManager.writeSingleLineToFile("most_valuable.txt", "country,value");
            insuranceFileManager.writeListOfPairsToFile("most_valuable.txt", listOfPairs);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
