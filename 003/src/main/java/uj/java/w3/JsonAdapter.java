package uj.java.w3;

import java.util.*;
import java.util.List;

public class JsonAdapter implements JsonMapper {

    public enum VariableTypes {
        STRING,
        NUMERIC,
        BOOLEAN,
        MAP,
        LIST
    }

    private StringBuilder convertedJson = new StringBuilder();

    private void deleteLastComma() {
        convertedJson.setLength(convertedJson.length() - 1);
    }

    private VariableTypes getVariableType(String type) {
        if (type.contains("String")) return VariableTypes.STRING;
        else if (type.contains("Integer") || type.contains("Short") || type.contains("Long")
                || type.contains("Byte") || type.contains("Float") || type.contains("Double"))
            return VariableTypes.NUMERIC;
        else if (type.contains("Boolean")) return VariableTypes.BOOLEAN;
        else if (type.contains("List")) return VariableTypes.LIST;
        else if (type.contains("Map")) return VariableTypes.MAP;
        else return null;
    }

    private void convertString(String stringElement) {
        convertedJson.append("\"");
        convertedJson.append((stringElement).replaceAll("\"", "\\\\\""));
        convertedJson.append("\"");
    }

    private void convertNumeric(String numericElement) {
        convertedJson.append(numericElement);
    }

    private void convertBoolean(boolean booleanElement) {
        convertedJson.append(booleanElement ? "true" : "false");
    }

    private void decideNextConversionBasedOnType(VariableTypes type, Object element) {
        switch (type) {
            case STRING -> convertString((String) element);
            case NUMERIC -> convertNumeric(element.toString());
            case BOOLEAN -> convertBoolean((boolean) element);
            case LIST -> convertList((List<?>) element);
            case MAP -> convertMap((Map<String, ?>) element);
            default -> System.out.println("Unknown behaviour");
        }
    }

    private void convertList(List<?> list) {
        if (list == null || list.size() == 0) {
            convertedJson.append("[]");
            return;
        }

        convertedJson.append("[");

        for (var currentElement : list) {
            VariableTypes type = getVariableType(currentElement.getClass().getTypeName());

            decideNextConversionBasedOnType(type, currentElement);

            convertedJson.append(",");
        }

        deleteLastComma();

        convertedJson.append("]");
    }

    private void convertMap(Map<String, ?> map) {
        if (map == null || map.size() == 0) {
            convertedJson.append("{}");
            return;
        }

        convertedJson.append("{");

        for (String key : map.keySet()) {

            //add key value to string
            convertString(key);
            convertedJson.append(":");

            var currentElement = map.get(key);
            VariableTypes type = getVariableType(map.get(key).getClass().getTypeName());

            decideNextConversionBasedOnType(type, currentElement);

            convertedJson.append(",");

        }

        deleteLastComma();

        convertedJson.append("}");
    }

    @Override
    public String toJson(Map<String, ?> map) {
        convertMap(map);

        return convertedJson.toString();
    }
}