package uj.java.w7.insurance;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

// Enhanced FileManager from gvt

public class FileManager {

    public void createDirectory(String directory) throws IOException {
        Path newDirectory = Paths.get(directory);
        Files.createDirectories(newDirectory);
    }

    public void createOrReplaceFileIfExists(String fileDirectory) throws IOException {
        Path newDirectory = Paths.get(fileDirectory);
        Files.deleteIfExists(newDirectory);
        Files.createFile(newDirectory);
    }

    // public List<String> readFile(String fileDirectory)

    public void writeSingleLineToFile(String directory, String text) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(directory, true));

        bufferedWriter.append(text);
        bufferedWriter.newLine();

        bufferedWriter.close();
    }

    public void writeListToFile(String directory, List<String> list) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(directory, true));

        for (var x : list) {
            bufferedWriter.append(x);
            bufferedWriter.newLine();
        }

        bufferedWriter.close();
    }

    public BufferedReader createBufferedReaderOfZipFile(String zipDirectory, String fileDirectory) throws IOException {
        ZipFile zipFile = new ZipFile(zipDirectory);
        ZipEntry insuranceEntry = zipFile.getEntry(fileDirectory);

        InputStream inputStream = zipFile.getInputStream(insuranceEntry);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

        return new BufferedReader(inputStreamReader);
    }

    public void copyFileBetweenDirectories(String firstDirectory, String secondDirectory) throws IOException {
        Path basicDirectory = Paths.get(firstDirectory);
        Path copiedFileDir = Paths.get(secondDirectory);
        Files.copy(basicDirectory, copiedFileDir, StandardCopyOption.REPLACE_EXISTING);
    }

    public void serializeClassToFile(String dir, Object object) throws IOException {
        FileOutputStream fos = new FileOutputStream(dir);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(object);

        oos.close();
    }

    public Object readSerializedFileToClass(String directory) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(directory);
        ObjectInputStream ois = new ObjectInputStream(fis);

        Object versionInfo = (Object) ois.readObject();

        ois.close();

        return versionInfo;
    }
}
