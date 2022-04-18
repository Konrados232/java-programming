package uj.java.pwj2020.spreadsheet;

import java.util.ArrayList;

record Position(int row, int column) {
}

public class Spreadsheet {

    enum CellType {
        VALUE,
        REFERENCE,
        FORMULA
    }

    enum FormulaType {
        ADDITION,
        SUBTRACTION,
        MULTIPLICATION,
        DIVISION,
        MODULO
    }

    private String[][] cells;

    private String getStringBetweenCharacters(String input, String first, String second) {
        return input.substring(input.indexOf(first) + 1, input.indexOf(second));
    }

    private String removeWhitespaces(String input) {
        return input.replaceAll("\\s+", "");
    }

    private CellType cellType(String cellString) {
        if (cellString.matches("[-0-9]+"))
            return CellType.VALUE;
        else if (cellString.matches("^[$].*"))
            return CellType.REFERENCE;
        else if (cellString.matches("^[=].*"))
            return CellType.FORMULA;
        else return null;
    }

    private FormulaType formulaType(String formula) {
        String extractedFormula = getStringBetweenCharacters(formula, "=", "(");
        extractedFormula = removeWhitespaces(extractedFormula);

        return switch (extractedFormula) {
            case "ADD" -> FormulaType.ADDITION;
            case "SUB" -> FormulaType.SUBTRACTION;
            case "MUL" -> FormulaType.MULTIPLICATION;
            case "DIV" -> FormulaType.DIVISION;
            case "MOD" -> FormulaType.MODULO;
            default -> null;
        };
    }

    private void changeValueInArrayPosition(Position position, String value) {
        cells[position.row()][position.column()] = value;
    }

    private String convertArrayPositionToValue(Position position) {
        return cells[position.row()][position.column()];
    }

    private Position convertReferenceToArrayPosition(String reference) {
        String positionString = reference.substring(reference.indexOf("$") + 1);
        positionString = removeWhitespaces(positionString);

        int row = Integer.parseInt(positionString.substring(1)) - 1; // we assume row position is positive number
        int column = (int) positionString.charAt(0) - 'A'; // we assume column position is A-Z

        return new Position(row, column);
    }

    private void changeAllChainOfReferences(Position startingPosition) {
        var positions = new ArrayList<Position>();
        positions.add(startingPosition);

        String currentPositionString = convertArrayPositionToValue(startingPosition);

        while (cellType(currentPositionString) != CellType.VALUE) {
            Position nextPosition = convertReferenceToArrayPosition(currentPositionString);
            positions.add(nextPosition);

            currentPositionString = convertArrayPositionToValue(nextPosition);
        }

        // our found value at the end of references is currentPositionString

        for (Position p : positions) {
            changeValueInArrayPosition(p, currentPositionString);
        }
    }

    private int calculateResultBasedOnFormulaType(int first, int second, FormulaType formulaType) {
        return switch (formulaType) {
            case ADDITION -> first + second;
            case SUBTRACTION -> first - second;
            case MULTIPLICATION -> first * second;
            case DIVISION -> first / second;
            case MODULO -> first % second;
        };
    }

    private String[] getValuesFromFormula(Position formulaPosition) {
        String croppedValues = getStringBetweenCharacters(convertArrayPositionToValue(formulaPosition), "(", ")");
        croppedValues = removeWhitespaces(croppedValues);

        return croppedValues.split(",");
    }

    private void changeFormulaAndItsChainOfReferences(Position formulaPosition) {
        String[] formulaValues = getValuesFromFormula(formulaPosition);
        int[] formulaIntValues = new int[formulaValues.length];

        for (int i = 0; i < formulaValues.length; i++) {
            if (cellType(formulaValues[i]) == CellType.REFERENCE) {
                Position firstReferencePosition = convertReferenceToArrayPosition(formulaValues[i]);
                changeAllChainOfReferences(firstReferencePosition);
                formulaValues[i] = convertArrayPositionToValue(firstReferencePosition);
            }

            formulaIntValues[i] = Integer.parseInt(formulaValues[i]);
        }

        int result = calculateResultBasedOnFormulaType(formulaIntValues[0], formulaIntValues[1], formulaType(convertArrayPositionToValue(formulaPosition)));
        changeValueInArrayPosition(formulaPosition, String.valueOf(result));
    }

    private void decideOperationInCell(Position currentPosition) {
        CellType currentCellType = cellType(convertArrayPositionToValue(currentPosition));

        switch (currentCellType) {
            case REFERENCE -> changeAllChainOfReferences(currentPosition);
            case FORMULA -> changeFormulaAndItsChainOfReferences(currentPosition);
            // in other case nothing happens - expected behaviour
        }
    }

    public String[][] calculate(String[][] input) {

        cells = input.clone();

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                decideOperationInCell(new Position(i,j));
            }
        }

        return cells;
    }
}