package uj.java.w3;

import java.util.*;

record Position(int x, int y) {
}

public class BattleshipBoardGenerator implements BattleshipGenerator {

    public enum FieldType {
        WATER,
        SHIP,
        WALL
    }

    private enum DirectionType {
        FOUR_WAY(4),
        EIGHT_WAY(8);

        private int value;

        DirectionType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    final int boardWidth = 10;
    final int boardHeight = 10;
    final DirectionType typeOfShipDirection = DirectionType.FOUR_WAY;

    FieldType[][] board = new FieldType[boardHeight][boardWidth];

    private boolean checkIfPositionIsViable(Position pos) {
        return (pos.x() >= 0 && pos.y() >= 0 && pos.x() < boardHeight && pos.y() < boardWidth && board[pos.y()][pos.x()] == FieldType.WATER);
    }

    private Position getNextPositionFromDirection(int direction, Position currentPosition, DirectionType directionType) {
        if (directionType == DirectionType.FOUR_WAY) {
            return switch ((direction % directionType.getValue())) {
                case 0 -> new Position(currentPosition.x(), currentPosition.y() - 1); // north
                case 1 -> new Position(currentPosition.x() + 1, currentPosition.y()); // east
                case 2 -> new Position(currentPosition.x(), currentPosition.y() + 1); // south
                case 3 -> new Position(currentPosition.x() - 1, currentPosition.y()); // west
                default -> new Position(-100, -100);
            };
        } else if (directionType == DirectionType.EIGHT_WAY) {
            return switch ((direction % directionType.getValue())) {
                case 0 -> new Position(currentPosition.x(), currentPosition.y() - 1); // north
                case 1 -> new Position(currentPosition.x() + 1, currentPosition.y() - 1);
                case 2 -> new Position(currentPosition.x() + 1, currentPosition.y()); // east
                case 3 -> new Position(currentPosition.x() + 1, currentPosition.y() + 1);
                case 4 -> new Position(currentPosition.x(), currentPosition.y() + 1); // south
                case 5 -> new Position(currentPosition.x() - 1, currentPosition.y() + 1);
                case 6 -> new Position(currentPosition.x() - 1, currentPosition.y()); // west
                case 7 -> new Position(currentPosition.x() - 1, currentPosition.y() - 1);
                default -> new Position(-100, -100);
            };
        } else {
            //return new Position(-100,-100);
            return null;
        }
    }

    private Position getNextMastAtRandomNearPosition(Position pos) {
        Random rand = new Random();
        int nextDirection = rand.nextInt(typeOfShipDirection.getValue());
        Position nextPosition;

        for (int i = 0; i < typeOfShipDirection.getValue(); i++) {
            nextPosition = getNextPositionFromDirection(nextDirection + i, pos, typeOfShipDirection);
            if (checkIfPositionIsViable(nextPosition)) return nextPosition;
        }

        return null;
    }

    private Position getRandomOriginPosition() {
        Random rand = new Random();
        Position originPosition;

        do {
            originPosition = new Position(rand.nextInt(boardHeight), rand.nextInt(boardWidth));
        } while (!checkIfPositionIsViable(originPosition));

        return originPosition;
    }

    private void resetShipValuesAtPositions(Stack<Position> positionStack) {
        while (!positionStack.empty()) {
            Position positionToChange = positionStack.pop();
            board[positionToChange.y()][positionToChange.x()] = FieldType.WATER;
        }
    }

    private void buildWallsAroundShip(Stack<Position> positionStack) {
        while (!positionStack.empty()) {
            Position shipPosition = positionStack.pop();
            Position positionToChange;

            for (int i = 0; i < DirectionType.EIGHT_WAY.getValue(); i++) {
                positionToChange = getNextPositionFromDirection(i, shipPosition, DirectionType.EIGHT_WAY);

                if (checkIfPositionIsViable(positionToChange))
                    board[positionToChange.y()][positionToChange.x()] = FieldType.WALL;
            }
        }
    }

    private void generateShipAtRandomPosition(int howManyMasts) {
        boolean acceptedPosition = false;

        do {
            Stack<Position> positionsStack = new Stack<Position>();

            Position originPosition = getRandomOriginPosition();
            board[originPosition.y()][originPosition.x()] = FieldType.SHIP;
            positionsStack.add(originPosition);

            // generate next masts if possible
            for (int i = 0; i < (howManyMasts - 1); i++) {
                Position nextPosition = getNextMastAtRandomNearPosition(positionsStack.peek());

                if (nextPosition != null) {
                    board[nextPosition.y()][nextPosition.x()] = FieldType.SHIP;
                    positionsStack.add(nextPosition);
                } else {
                    resetShipValuesAtPositions(positionsStack);
                    break;
                }
            }

            if (!positionsStack.empty()) {
                buildWallsAroundShip(positionsStack);
                acceptedPosition = true;
            }

        } while (!acceptedPosition);

    }

    private void fillWholeBoardInWater() {
        for (int i = 0; i < boardWidth; i++) {
            for (int j = 0; j < boardHeight; j++) {
                board[j][i] = FieldType.WATER;
            }
        }
    }

    private void generateBoard() {
        BattleshipManager battleshipManager = new BattleshipManager();
        //battleshipManager.addNewBattleShipType(50, 1);
        battleshipManager.addNewBattleShipType(4, 1);
        battleshipManager.addNewBattleShipType(3, 2);
        battleshipManager.addNewBattleShipType(2, 3);
        battleshipManager.addNewBattleShipType(1, 4);
        battleshipManager.sortBeforeGeneratingBoard();

        fillWholeBoardInWater();

        for (var x : battleshipManager.battleshipTypes) {
            for (int i = 0; i < x.getHowManyShips(); i++) {
                generateShipAtRandomPosition(x.getHowManyMasts());
            }
        }
    }

    private String convertBoardToString() {
        StringBuilder convertedBoard = new StringBuilder();

        for (int i = 0; i < boardWidth; i++) {
            for (int j = 0; j < boardHeight; j++) {
                if (board[j][i] == FieldType.SHIP) convertedBoard.append("#");
                else convertedBoard.append(".");
            }
        }

        return convertedBoard.toString();
    }

    @Override
    public String generateMap() {
        generateBoard();

        return convertBoardToString();
    }
}