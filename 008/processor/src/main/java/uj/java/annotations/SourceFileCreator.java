package uj.java.annotations;

import java.util.LinkedList;
import java.util.List;

record Pair(int index, int tabCount) {}

public class SourceFileCreator {

    private final List<SourceCodeLine> mainCodeBody;

    private int packageCount;
    private int classCount;
    private int fieldCount;
    private int methodCount;

    private String extensionName;

    private final boolean hasPackageLimit;
    private final boolean hasClassLimit;
    private int packageLimit;
    private int classLimit;

    public SourceFileCreator() {
        mainCodeBody = new LinkedList<>();
        this.packageCount = 0;
        this.classCount = 0;
        this.fieldCount = 0;
        this.methodCount = 0;
        this.hasPackageLimit = false;
        this.hasClassLimit = false;
    }

    public SourceFileCreator(int classLimit, int packageLimit) {
        mainCodeBody = new LinkedList<>();
        this.packageCount = 0;
        this.classCount = 0;
        this.fieldCount = 0;
        this.methodCount = 0;
        this.hasPackageLimit = true;
        this.hasClassLimit = true;
        this.classLimit = classLimit;
        this.packageLimit = packageLimit;
    }

    public void writePackage(String packageName) throws IncorrectSourceCodeException {
        if (hasPackageLimit && packageCount + 1 == packageLimit) {
            throw new IncorrectSourceCodeException("Can't add more packages over limit.");
        }

        mainCodeBody.add(packageCount, new SourceCodeLine("package " + packageName + ";", 0));
        packageCount++;
    }

    public void writeClass(String className, AccessModifier accessModifier) throws IncorrectSourceCodeException {
        if(hasClassLimit && classCount + 1 == classLimit) {
            throw new IncorrectSourceCodeException("Can't add more classes over limit.");
        }

        mainCodeBody.add(packageCount,
                new SourceCodeLine(accessModifier.toString().toLowerCase() + " class " + className + " {", 0));
        mainCodeBody.add(packageCount + 1, new SourceCodeLine("}", 0));
        classCount++;
    }

    public void writeMethodInClass(String methodName, String methodType, AccessModifier accessModifier, String className, List<MethodArgument> methodArguments) throws IncorrectSourceCodeException {
        if (!areArgumentsCorrect(methodArguments)) {
            throw new IncorrectSourceCodeException("Method arguments can't be empty.");
        }

        if (classCount == 0) {
            throw new IncorrectSourceCodeException("Can't add method when there are no classes.");
        }

        Pair foundClassPair = classIndexAndTabCount(className, true);
        if (foundClassPair.index() == -1) {
            throw new IncorrectSourceCodeException("Can't add method to non-existent class.");
        }

        String methodText = convertToMethodNameCode(methodName, methodType, accessModifier, methodArguments);
        mainCodeBody.add(foundClassPair.index() + 1,
                new SourceCodeLine(methodText, foundClassPair.tabCount() + 1));
        mainCodeBody.add(foundClassPair.index() + 2,
                new SourceCodeLine("}", foundClassPair.tabCount() + 1));
        methodCount++;
    }

    public void writeCodeBlockInMethod(String methodName, List<String> codeList) throws IncorrectSourceCodeException {
        if (classCount == 0) {
            throw new IncorrectSourceCodeException("Can't add method when there are no classes.");
        }

        if (methodCount == 0) {
            throw new IncorrectSourceCodeException("Can't add method block code when there are no methods.");
        }

        Pair foundMethodPair = classIndexAndTabCount(methodName, false);
        if (foundMethodPair.index() == -1) {
            throw new IncorrectSourceCodeException("Can't add block code to non-existent method.");
        }

        String codeBlock = convertToMethodBlockCode(codeList, foundMethodPair.tabCount() + 1);
        mainCodeBody.add(foundMethodPair.index() + 1,
                new SourceCodeLine(codeBlock, foundMethodPair.tabCount() + 1));

    }

    public void writeFieldInClass(String fieldName, String type, AccessModifier accessModifier, String className) throws IncorrectSourceCodeException {
        if (classCount == 0) {
            throw new IncorrectSourceCodeException("Can't add method when there are no classes.");
        }

        Pair foundClassPair = classIndexAndTabCount(className, true);
        if (foundClassPair.index() == -1) {
            throw new IncorrectSourceCodeException("Can't add method to non-existent class.");
        }

        mainCodeBody.add(foundClassPair.index() + 1,
                new SourceCodeLine(accessModifier.toString().toLowerCase() + " " + type + " " + fieldName + ";", foundClassPair.tabCount() + 1));
        fieldCount++;
    }

    private Pair classIndexAndTabCount(String name, boolean isClass) {
        for (int i = 0; i < mainCodeBody.size(); i++) {
            var x = mainCodeBody.get(i);
            if (x.text().contains(name)) {
                if (isClass && x.text().contains("class")) {
                    return new Pair(i, x.tabCount());
                } else {
                    return new Pair(i, x.tabCount());
                }
            }
        }

        return new Pair(-1, 0);
    }

    private boolean areArgumentsCorrect(List<MethodArgument> methodArguments) {
        for (var x : methodArguments) {
            if (!x.type().matches("[a-zA-Z<>]+") || !x.name().matches("[a-zA-Z]+")) {
                return false;
            }
        }

        return true;
    }

    private String deleteLastComma(String text) {
        return text.substring(0, text.length() - 1);
    }

    private String convertToMethodNameCode(String methodName, String methodType, AccessModifier accessModifier, List<MethodArgument> methodArguments) {
        StringBuilder methodText = new StringBuilder(accessModifier.toString().toLowerCase() + " " + methodType + " " + methodName + "(");

        for (var x : methodArguments) {
            methodText.append(x.type()).append(" ").append(x.name()).append(",");
        }
        methodText = new StringBuilder(deleteLastComma(methodText.toString()));
        methodText.append(") {");

        return methodText.toString();
    }

    private String convertToMethodBlockCode(List<String> codeList, int tabCount) {
        StringBuilder codeBlock = new StringBuilder();
        for (var x : codeList) {
            codeBlock.append(x).append("\n");
            codeBlock.append("\t".repeat(Math.max(0, tabCount)));
        }
        return codeBlock.toString();
    }

    public List<SourceCodeLine> mainCodeBody() {
        return mainCodeBody;
    }

    static class IncorrectSourceCodeException extends Exception {
        public IncorrectSourceCodeException(String exceptionMessage) {
            super(exceptionMessage);
        }
    }

}
