package uj.java.annotations;

import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

public class SourceCreatorFileManager extends FileManager {

    public void createSourceFile(String fileName, List<SourceCodeLine> code, ProcessingEnvironment processingEnv) throws IOException {
        JavaFileObject file =  processingEnv.getFiler().createSourceFile(fileName);

        var stringList = convertFromSourceCodeLineToString(code);

        PrintWriter printWriter = new PrintWriter(file.openWriter());

        for (var x : stringList) {
            printWriter.println(x);
        }

        printWriter.close();
    }

    private List<String> convertFromSourceCodeLineToString(List <SourceCodeLine> code) {
        List<String> convertedList = new LinkedList<>();

        for (var x : code) {
            String convertedString = "\t".repeat(Math.max(0, x.tabCount())) + x.text();
            convertedList.add(convertedString);
        }

        return convertedList;
    }

}
