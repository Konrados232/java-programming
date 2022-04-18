package uj.java.w3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BattleshipManager {
    List<BattleshipType> battleshipTypes;

    public BattleshipManager() {
        battleshipTypes = new ArrayList<BattleshipType>();
    }

    public void addNewBattleShipType(int howManyMasts, int howManyShips) {
        BattleshipType newBattleshipType = new BattleshipType(howManyMasts, howManyShips);

        if (battleshipTypes.contains(newBattleshipType)) {
            System.out.println("Already contains");
        } else {
            battleshipTypes.add(newBattleshipType);
        }
    }

    public void sortBeforeGeneratingBoard() {
        Collections.sort(battleshipTypes);
    }
}
