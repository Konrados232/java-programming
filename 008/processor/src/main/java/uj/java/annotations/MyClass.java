package uj.java.annotations;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeKind;
import java.io.IOException;
import java.util.*;

@SupportedSourceVersion(SourceVersion.RELEASE_17)
@SupportedAnnotationTypes({"uj.java.annotations.MyComparable", "uj.java.annotations.ComparePriority"})
public class MyClass extends AbstractProcessor {

    SourceFileCreator sourceFileCreator = new SourceFileCreator();
    SourceCreatorFileManager sourceCreatorFileManager = new SourceCreatorFileManager();

    Comparator<Element> elementComparator = new Comparator<Element>() {
        @Override
        public int compare(Element o1, Element o2) {
            var firstElement = o1.getAnnotation(ComparePriority.class);
            var secondElement = o2.getAnnotation(ComparePriority.class);

            if (firstElement == null && secondElement == null)
                return 0;
            else if (firstElement == null)
                return 1;
            else if (secondElement == null)
                return -1;
            else
                return firstElement.value() - secondElement.value();
        }
    };

    private void createPackageAndClassOfElement(TypeElement classElement) throws SourceFileCreator.IncorrectSourceCodeException {
        String packageName = processingEnv.getElementUtils().getPackageOf(classElement).toString();
        String comparatorClassName = classElement.getSimpleName().toString() + "Comparator";

        sourceFileCreator.writePackage(packageName);
        sourceFileCreator.writeClass(comparatorClassName, AccessModifier.PUBLIC);
    }

    private void createHeaderOfCompareMethod(String className, String comparatorClassName) throws SourceFileCreator.IncorrectSourceCodeException {
        List<MethodArgument> methodArguments = new ArrayList<>();
        methodArguments.add(new MethodArgument(className, "first"));
        methodArguments.add(new MethodArgument(className, "second"));

        sourceFileCreator.writeMethodInClass("compare", "int", AccessModifier.PUBLIC, comparatorClassName, methodArguments);
    }

    private void createBodyOfCompareMethod(List<VariableElement> elementsToCompare) throws SourceFileCreator.IncorrectSourceCodeException {
        List<String> codeBlock = new ArrayList<>();
        codeBlock.add("int result = 0;");

        for (var variableElement : elementsToCompare) {
            String variableName = variableElement.getSimpleName().toString();

            TypeKind variableType = variableElement.asType().getKind();
            String variableTypeName = convertToVariableTypeName(variableType);

            codeBlock.add("result = " + variableTypeName + ".compare(first." + variableName + ", second." + variableName + ");");
            codeBlock.add("if (result != 0) return result;");
        }

        codeBlock.add("return result;");

        sourceFileCreator.writeCodeBlockInMethod("compare", codeBlock);
    }

    private void createCompareMethod(TypeElement classElement, List<VariableElement> elementsToCompare) throws SourceFileCreator.IncorrectSourceCodeException, IOException {
        String className = classElement.getSimpleName().toString();
        String comparatorClassName = className + "Comparator";

        createHeaderOfCompareMethod(className, comparatorClassName);
        createBodyOfCompareMethod(elementsToCompare);
    }

    private String convertToVariableTypeName(TypeKind variableType) {
        return switch (variableType) {
            case INT -> "Integer";
            case CHAR -> "Character";
            default -> defaultConversionToVariableTypeName(variableType);
        };
    }

    private String defaultConversionToVariableTypeName(TypeKind variableType) {
        String toConvert = variableType.name();
        return toConvert.charAt(0) + toConvert.substring(1).toLowerCase();
    }

    private List<VariableElement> filterEnclosedElements(TypeElement classElement) {
        List<VariableElement> variableElements = new ArrayList<>();

        List<? extends Element> enclosedElements = classElement.getEnclosedElements();

        for (var x : enclosedElements) {
            var listOfModifiers = x.getModifiers();
            var elementType = x.asType();

            if (!listOfModifiers.contains(Modifier.PRIVATE) && elementType.getKind().isPrimitive()) {
                variableElements.add((VariableElement) x);
            }
        }

        variableElements.sort(elementComparator);

        return variableElements;
    }

    private void processAnnotation(Element element) throws SourceFileCreator.IncorrectSourceCodeException, IOException {
        if (!element.getKind().isClass()) {
            return;
        }

        TypeElement classElement = (TypeElement) element;
        createPackageAndClassOfElement(classElement);

        var elementsToCompare = filterEnclosedElements(classElement);
        createCompareMethod(classElement, elementsToCompare);

        sourceCreatorFileManager.createSourceFile(classElement.getSimpleName().toString() + "Comparator", sourceFileCreator.mainCodeBody(), processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            Set<? extends Element> annotatedElements
                    = roundEnv.getElementsAnnotatedWith(annotation);

            for (var x : annotatedElements) {
                try {
                    processAnnotation(x);
                } catch (SourceFileCreator.IncorrectSourceCodeException | IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return true;
    }

}