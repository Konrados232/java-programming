package uj.java.w7.insurance;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InsuranceFileManager extends FileManager {

    private InsuranceEntry convertToInsuranceEntry(String[] arr) {
        return new InsuranceEntry(new BigDecimal(arr[0]), arr[1], arr[2],
                new BigDecimal(arr[3]), new BigDecimal(arr[4]), new BigDecimal(arr[5]),
                new BigDecimal(arr[6]), new BigDecimal(arr[7]), new BigDecimal(arr[8]),
                new BigDecimal(arr[9]), new BigDecimal(arr[10]), new BigDecimal(arr[11]),
                new BigDecimal(arr[12]), new BigDecimal(arr[13]), new BigDecimal(arr[14]),
                arr[15], arr[16], new BigDecimal(arr[17]));
    }

    public List<InsuranceEntry> convertFileFromZipToListOfInsuranceEntries(String zipDirectory, String fileDirectory) throws IOException {
        BufferedReader bufferedReader = createBufferedReaderOfZipFile(zipDirectory, fileDirectory);

        return bufferedReader.lines().skip(1)
                .map(s -> convertToInsuranceEntry(s.split(",")))
                .collect(Collectors.toList());
    }

    public void writeListOfPairsToFile(String directory, List<Pair> list) throws IOException {
        List<String> listToWrite = new ArrayList<>();

        for (var x : list)
            listToWrite.add(x.toString());

        writeListToFile(directory, listToWrite);
    }

}
