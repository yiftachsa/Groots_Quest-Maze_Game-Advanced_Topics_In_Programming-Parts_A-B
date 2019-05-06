package algorithms.mazeGenerators;

import java.io.Serializable;
import java.util.Objects;

public class Position implements Serializable {


    private int x;
    private int y;

    /**
     * Constructor
     * @param x - int
     * @param y - int
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Default no parameters constructor
     */
    public Position() {
        this.x = 0;
        this.y = 0;
    }

    /**
     * Returns x
     * @return - int
     */
    public int getRowIndex() {
        return x;
    }

    /**
     * Sets x
     * @param x - int
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Returns y
     * @return - int
     */
    public int getColumnIndex() {
        return y;
    }

    /**
     * Sets y
     * @param y - int
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Returns the position's values
     * @return - String
     */
    @Override
    public String toString() {
        return "{"+ x + "," + y + '}';
    }

    /**
     * Returns true if both Objects are Positions and the values of both positions are equal.
     * @param o - Object
     * @return - boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x &&
                y == position.y;
    }

    /**
     * Uses Object hashing
     * @return - int
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }


}

