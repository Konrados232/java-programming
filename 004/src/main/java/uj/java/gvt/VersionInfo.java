package uj.java.gvt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VersionInfo implements Serializable {
    private long versionIndex;
    private String neededMessage;
    private String optionalMessage;
    private List<String> fileList;

    VersionInfo() {

    }

    VersionInfo(int versionName, String neededMessage, String optionalMessage) {
        this.versionIndex = versionName;
        this.neededMessage = neededMessage;
        this.optionalMessage = optionalMessage;
        this.fileList = new ArrayList<String>();
    }

    public List<String> fileList() {
        return fileList;
    }

    public long versionIndex() {
        return versionIndex;
    }

    public void printVersionInfo() {
        System.out.println("Version: " + versionIndex);
        System.out.println(neededMessage);
        System.out.print(optionalMessage);
    }

    public void printHistoryVersionInfo() {
        System.out.println(versionIndex + ": " + neededMessage);
    }

    public void addFileToTrackedList(String file) {
        fileList.add(file);
    }

    public void removeFileFromTrackedList(String file) {
        fileList.remove(file);
    }

    public void incrementVersion() {
        versionIndex++;
    }

    public void changeVersion(long version) {
        versionIndex = version;
    }

    public void updateMessages(String defaultMessage, String fileName, String optional) {
        neededMessage = defaultMessage + fileName;
        if (optional == null || optional.equals("")) optionalMessage = "";
        else optionalMessage = optional;
    }

    public boolean isFileAlreadyTraced(String file) {
        if (fileList == null || file.length() == 0) {
            return false;
        }

        return fileList.contains(file);
    }

}
