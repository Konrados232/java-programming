package uj.java.kindergarten;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

record ChildInfo(String childName, int hungerSpeed) {
}

public class Kindergarten {

    private static int readNumberOfChildrenFromFile(String fileName) {
        try {
            var fileInputStream = new FileInputStream(fileName);
            var bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

            String firstLine = bufferedReader.readLine();

            if (firstLine != null)
                return Integer.parseInt(firstLine);

            fileInputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private static ChildInfo convertLineToChildInfo(String line) {
        String[] splittedArray = line.split(" ");

        String name = splittedArray[0];
        int hungerSpeed = Integer.parseInt(splittedArray[1]);

        return new ChildInfo(name, hungerSpeed);
    }

    private static List<ChildInfo> readChildInfoFromFile(String fileName, int numberOfChildren) {
        try {
            var fileInputStream = new FileInputStream(fileName);
            var bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

            List<ChildInfo> childInfos = new ArrayList<>();

            String currentLine = bufferedReader.readLine(); // ignore first line

            for (int i = 0; i < numberOfChildren; i++) {
                currentLine = bufferedReader.readLine();

                ChildInfo currentChild = convertLineToChildInfo(currentLine);
                childInfos.add(currentChild);
            }

            return childInfos;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static void initializeForks(ReentrantLock[] forks) {
        for (int i = 0; i < forks.length; i++) {
            forks[i] = new ReentrantLock();
        }
    }

    private static void initializeChildrenAndAssignForksForChildren(ChildImpl[] children, List<ChildInfo> childInfos, ReentrantLock[] forks) {
        ReentrantLock leftFork;
        ReentrantLock rightFork;

        for (int i = 0; i < children.length; i++) {
            String name = childInfos.get(i).childName();
            int hungerSpeed = childInfos.get(i).hungerSpeed();

            // inverted for first child
            if (i == 0) {
                rightFork = forks[i];
                leftFork = forks[(i + 1) % children.length];
            } else {
                leftFork = forks[i];
                rightFork = forks[(i + 1) % children.length];
            }

            children[i] = new ChildImpl(name, hungerSpeed, leftFork, rightFork);
        }
    }

    private static void addNeighboursToChildren(ChildImpl[] children) {
        for (int i = 0; i < children.length; i++) {
            children[i].addNearChildren(children[trueModuloFor32BitInt(i - 1, children.length)],
                    children[trueModuloFor32BitInt(i + 1, children.length)]);
        }
    }

    private static int trueModuloFor32BitInt(int number, int modulo) {
        int remainder = number % modulo;
        return (remainder >> 31 & modulo) + remainder;
    }

    public static void main(String[] args) throws IOException {
        init();

        final var fileName = args[0];
        System.out.println("File name: " + fileName);
        //TODO: read children file, and keep children NOT hungry!

        int numberOfChildren = readNumberOfChildrenFromFile(fileName);
        List<ChildInfo> childInfos = readChildInfoFromFile(fileName, numberOfChildren);

        if (numberOfChildren == 0 || childInfos == null) {
            return;
        }

        ChildImpl[] children = new ChildImpl[numberOfChildren];
        ReentrantLock[] forks = new ReentrantLock[numberOfChildren];

        initializeForks(forks);
        initializeChildrenAndAssignForksForChildren(children, childInfos, forks);
        addNeighboursToChildren(children);

        Thread[] threads = new Thread[numberOfChildren];
        for (int i = 0; i < numberOfChildren; i++) {
            threads[i] = new Thread(children[i], children[i].name());
            threads[i].start();
        }
    }

    private static void init() throws IOException {
        Files.deleteIfExists(Path.of("out.txt"));
        System.setErr(new PrintStream(new FileOutputStream("out.txt")));
        new Thread(Kindergarten::runKindergarden).start();
    }

    private static void runKindergarden() {
        try {
            Thread.sleep(10100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            List<String> errLines = Files.readAllLines(Path.of("out.txt"));
            System.out.println("Children cries count: " + errLines.size());
            errLines.forEach(System.out::println);
            System.exit(errLines.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
