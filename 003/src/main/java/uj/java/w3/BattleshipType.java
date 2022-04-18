package uj.java.w3;

import java.util.Objects;

public class BattleshipType implements Comparable<BattleshipType> {
    private int howManyMasts;
    private int howManyShips;

    public BattleshipType(int howManyMasts, int howManyShips) {
        this.howManyMasts = howManyMasts;
        this.howManyShips = howManyShips;
    }

    public int getHowManyMasts() {
        return howManyMasts;
    }

    public int getHowManyShips() {
        return howManyShips;
    }

    @Override
    public int compareTo(BattleshipType o) {
        if (this.howManyMasts < o.howManyMasts) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BattleshipType that = (BattleshipType) o;
        return howManyMasts == that.howManyMasts;
    }

    @Override
    public int hashCode() {
        return Objects.hash(howManyMasts);
    }

}
