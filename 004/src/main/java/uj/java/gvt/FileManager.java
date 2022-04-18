package uj.java.gvt;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileManager {

    public void createDirectory(String directory) throws IOException {
        Path newVersionDir = Paths.get(directory);
        Files.createDirectories(newVersionDir);
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

        VersionInfo versionInfo = (VersionInfo) ois.readObject();

        ois.close();

        return versionInfo;
    }
}