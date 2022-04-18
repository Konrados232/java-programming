package uj.java.gvt;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

record CommandArgs(String commandName, String mainCommand, String optionalCommand) {

}

public class Gvt {

    static GvtFileManager gvtFileManager;
    static List<String> listOfViableCommands = List.of("init", "add", "detach", "checkout", "commit", "history", "version");

    public static void init() {
        try {
            if (gvtFileManager.isGvtInitialized()) {
                System.out.println("Current directory is already initialized.");
                System.exit(10);
            }

            gvtFileManager.initializeDirectories();

            VersionInfo currentVersionInfo = new VersionInfo(0, "GVT initialized.", "");
            gvtFileManager.createVersionsFileInVersionAndMainLocations(currentVersionInfo);

            System.out.println("Current directory initialized successfully.");

        } catch (Exception e) {
            System.out.println("Underlying system problem. See ERR for details.");
            e.printStackTrace();
            System.exit(-3);
        }
    }

    public static void add(String file, String optionalMessage) {
        try {
            if (file == null || file.equals("")) {
                System.out.println("Please specify file to add.");
                System.exit(20);
            }

            Path fileToAdd = Paths.get(file);

            if (Files.notExists(fileToAdd)) {
                System.out.println("File " + file + " not found.");
                System.exit(21);
            }

            VersionInfo currentVersionInfo = gvtFileManager.readVersionInfoFromGvtLocation();

            if (currentVersionInfo.isFileAlreadyTraced(file)) {
                System.out.println("File " + file + " already added.");
                return;
            }

            currentVersionInfo.updateMessages("Added file: ", file, optionalMessage);
            currentVersionInfo.incrementVersion();
            currentVersionInfo.addFileToTrackedList(file);

            gvtFileManager.createVersionFolderAndAllSubfolders(currentVersionInfo.versionIndex());
            gvtFileManager.copyAllTrackedMainFilesToVersionFolder(currentVersionInfo);
            gvtFileManager.createVersionsFileInVersionAndMainLocations(currentVersionInfo);

            System.out.println("File " + file + " added successfully.");

        } catch (Exception e) {
            System.out.println("Underlying system problem. See ERR for details.");
            e.printStackTrace();
            System.exit(22);
        }
    }

    public static void detach(String file, String optionalMessage) {
        try {
            if (file == null || file.equals("")) {
                System.out.println("Please specify file to detach.");
                System.exit(30);
            }

            VersionInfo currentVersionInfo = gvtFileManager.readVersionInfoFromGvtLocation();

            assert currentVersionInfo != null;
            if (!currentVersionInfo.isFileAlreadyTraced(file)) {
                System.out.println("File " + file + " is not added to gvt.");
                return;
            }

            currentVersionInfo.updateMessages("Detached file: ", file, optionalMessage);
            currentVersionInfo.incrementVersion();
            currentVersionInfo.removeFileFromTrackedList(file);

            gvtFileManager.createVersionFolderAndAllSubfolders(currentVersionInfo.versionIndex());
            gvtFileManager.copyAllTrackedMainFilesToVersionFolder(currentVersionInfo);
            gvtFileManager.createVersionsFileInVersionAndMainLocations(currentVersionInfo);

            System.out.println("File " + file + " detached successfully.");

        } catch (Exception e) {
            System.out.println("Underlying system problem. See ERR for details.");
            e.printStackTrace();
            System.exit(31);
        }

    }

    public static void checkout(String version) {
        try {
            VersionInfo currentVersionInfo = gvtFileManager.readVersionInfoFromGvtLocation();

            if (version == null || !version.matches("\\d+") || Long.parseLong(version) > currentVersionInfo.versionIndex()) {
                System.out.println("Invalid version number: " + version + ".");
                System.exit(40);
            }

            VersionInfo versionInfo = gvtFileManager.readVersionInfoFromVersionLocation(Long.parseLong((version)));

            gvtFileManager.copyAllTrackedVersionFilesToMainFolder(versionInfo);

            System.out.println("Version" + version + " checked out successfully.");

        } catch (Exception e) {
            System.out.println("Underlying system problem. See ERR for details.");
            e.printStackTrace();
            System.exit(-3);
        }
    }

    public static void commit(String file, String optionalMessage) {
        try {
            if (file == null || file.equals("")) {
                System.out.println("Please specify file to commit.");
                System.exit(50);
            }

            Path fileToCommit = Paths.get(file);

            if (Files.notExists(fileToCommit)) {
                System.out.println("File " + file + " does not exist.");
                System.exit(51);
            }

            VersionInfo currentVersionInfo = gvtFileManager.readVersionInfoFromGvtLocation();

            if (!currentVersionInfo.isFileAlreadyTraced(file)) {
                System.out.println("File " + file + " is not added to gvt.");
                return;
            }

            currentVersionInfo.updateMessages("Committed file: ", file, optionalMessage);
            currentVersionInfo.incrementVersion();
            currentVersionInfo.addFileToTrackedList(file);

            gvtFileManager.createVersionFolderAndAllSubfolders(currentVersionInfo.versionIndex());
            gvtFileManager.copyAllTrackedMainFilesToVersionFolder(currentVersionInfo);
            gvtFileManager.createVersionsFileInVersionAndMainLocations(currentVersionInfo);

            System.out.println("File " + file + " committed successfully.");

        } catch (Exception e) {
            System.out.println("Underlying system problem. See ERR for details.");
            e.printStackTrace();
            System.exit(-52);
        }
    }

    public static void history(String lastMessage, String howMany) {
        try {
            VersionInfo currentVersionInfo = gvtFileManager.readVersionInfoFromGvtLocation();

            if (lastMessage == null || howMany == null || lastMessage.equals("") || howMany.equals("") || !lastMessage.equals("-last") || !howMany.matches("\\d+")) {

                for (long i = 0; i <= currentVersionInfo.versionIndex(); i++) {
                    VersionInfo versionInfo = gvtFileManager.readVersionInfoFromVersionLocation(i);
                    versionInfo.printHistoryVersionInfo();
                }

                return;
            }

            long firstVersion = currentVersionInfo.versionIndex() - Long.parseLong(howMany) + 1;

            for (long i = firstVersion; i <= currentVersionInfo.versionIndex(); i++) {
                VersionInfo versionInfo = gvtFileManager.readVersionInfoFromVersionLocation(i);
                versionInfo.printHistoryVersionInfo();
            }

        } catch (Exception e) {
            System.out.println("Underlying system problem. See ERR for details.");
            e.printStackTrace();
            System.exit(-3);
        }
    }

    public static void version(String version) {
        try {
            VersionInfo currentVersionInfo = gvtFileManager.readVersionInfoFromGvtLocation();

            if (version == null || version.equals("")) {
                currentVersionInfo.printVersionInfo();
                return;
            }

            if(!version.contains("[0-9]*")) {
                System.out.println("Invalid version number: " + version + ".");
                System.exit(60);
            }

            if(Long.parseLong(version) < 0 || Long.parseLong(version) > currentVersionInfo.versionIndex()) {
                System.out.println("Invalid version number: " + version + ".");
                System.exit(60);
            }

            VersionInfo versionInfo = gvtFileManager.readVersionInfoFromVersionLocation(Long.parseLong(version));
            versionInfo.printVersionInfo();

        } catch (Exception e) {
            System.out.println("Underlying system problem. See ERR for details.");
            e.printStackTrace();
            System.exit(-3);
        }
    }

    private static boolean isCommandNameCorrect(String mainCommand) {
        return listOfViableCommands.contains(mainCommand);
    }

    private static CommandArgs parseCommandsWithOptionalMessage(String[] args) {
        return switch (args.length) {
            case 4 -> new CommandArgs(args[0], args[1], args[3]);
            case 2 -> new CommandArgs(args[0], args[1], null);
            default -> new CommandArgs(args[0], null, null);
        };
    }

    private static CommandArgs parseCommandsWithLastParameter(String[] args) {
        if (args.length >= 3) return new CommandArgs(args[0], args[1], args[2]);
        else return new CommandArgs(args[0], null, null);
    }

    private static CommandArgs parseCommandsWithOneNumericalParameter(String[] args) {
        if (args.length >= 2) return new CommandArgs(args[0], args[1], null);
        else return new CommandArgs(args[0], null, null);
    }

    private static CommandArgs parseCommands(String[] args) {
        String command = args[0];

        return switch (command) {
            case "init" -> new CommandArgs(args[0], null, null);
            case "checkout", "version" -> parseCommandsWithOneNumericalParameter(args);
            case "add", "detach", "commit" -> parseCommandsWithOptionalMessage(args);
            case "history" -> parseCommandsWithLastParameter(args);
            default -> null;
        };
    }

    public static void executeCommand(String[] args) {
        CommandArgs commandArgs = parseCommands(args);

        switch (commandArgs.commandName()) {
            case "init" -> init();
            case "add" -> add(commandArgs.mainCommand(), commandArgs.optionalCommand());
            case "detach" -> detach(commandArgs.mainCommand(), commandArgs.optionalCommand());
            case "checkout" -> checkout(commandArgs.mainCommand());
            case "commit" -> commit(commandArgs.mainCommand(), commandArgs.optionalCommand());
            case "history" -> history(commandArgs.mainCommand(), commandArgs.optionalCommand());
            case "version" -> version(commandArgs.mainCommand());
        }
    }

    private static void handleInputExceptions(String... args) {
        if (args == null || args.length == 0) {
            System.out.println("Please specify command.");
            System.exit(1);
        }

        if (!isCommandNameCorrect(args[0])) {
            System.out.println("Unknown command " + args[0] + ".");
            System.exit(1);
        }

        if (!args[0].equals("init") && !gvtFileManager.isGvtInitialized()) {
            System.out.println("Current directory is not initialized. Please use \"init\" command to initialize.");
            System.exit(-2);
        }
    }

    public static void main(String... args) {
        gvtFileManager = new GvtFileManager();
        handleInputExceptions(args);

        executeCommand(args);
    }
}