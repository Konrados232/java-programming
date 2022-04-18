package uj.java.gvt;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GvtFileManager extends FileManager {

    final String SLASH = "/";

    final String GVT_CATALOG = ".gvt";
    final String VERSION_CATALOG = "versions";
    final String FILES_CATALOG = "files";

    final String GVT_DIR = ".gvt/";
    final String VERSION_DIR = ".gvt/versions/";

    final String VERSION_FILENAME = "versionInfo";

    GvtFileManager() {

    }

    public boolean isGvtInitialized() {
        Path gvtDir = Paths.get(GVT_CATALOG);
        return Files.exists(gvtDir);
    }

    public void initializeDirectories() throws IOException {
        createDirectory(GVT_CATALOG);
        createDirectory(VERSION_DIR);
        createDirectory(VERSION_DIR + "0");
        createDirectory(VERSION_DIR + "0" + SLASH + FILES_CATALOG);
    }

    public void copyAllTrackedMainFilesToVersionFolder(VersionInfo versionInfo) throws IOException {
        for (var x : versionInfo.fileList()) {
            copyFileBetweenDirectories(x, VERSION_DIR + versionInfo.versionIndex() + SLASH + FILES_CATALOG + SLASH + x);
        }
    }

    public void copyAllTrackedVersionFilesToMainFolder(VersionInfo versionInfo) throws IOException {
        for (var x : versionInfo.fileList()) {
            copyFileBetweenDirectories(VERSION_DIR + versionInfo.versionIndex() + SLASH + FILES_CATALOG + SLASH + x, x);
        }
    }

    public void createVersionFolderAndAllSubfolders(long version) throws IOException {
        createDirectory(VERSION_DIR + version);
        createDirectory(VERSION_DIR + version + SLASH + FILES_CATALOG);
    }

    public void createVersionsFileInVersionLocation(VersionInfo versionInfo) throws IOException {
        serializeClassToFile(VERSION_DIR + versionInfo.versionIndex() + SLASH + VERSION_FILENAME, versionInfo);
    }

    public void createVersionsFileInMainLocation(VersionInfo versionInfo) throws IOException {
        serializeClassToFile(GVT_DIR + VERSION_FILENAME, versionInfo);
    }

    public void createVersionsFileInVersionAndMainLocations(VersionInfo versionInfo) throws IOException {
        createVersionsFileInVersionLocation(versionInfo);
        createVersionsFileInMainLocation(versionInfo);
    }

    public VersionInfo readVersionInfoFromGvtLocation() throws IOException, ClassNotFoundException {
        return (VersionInfo) readSerializedFileToClass(GVT_DIR + VERSION_FILENAME);
    }

    public VersionInfo readVersionInfoFromVersionLocation(long version) throws IOException, ClassNotFoundException {
        return (VersionInfo) readSerializedFileToClass(VERSION_DIR + version + SLASH + VERSION_FILENAME);
    }
}