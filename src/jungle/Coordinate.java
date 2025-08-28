package jungle;

/**
 * The Coordinate class is a position in a grid with row and column values.
 * The equals and hashcode methods are created for comparison in case needed.
 */
public class Coordinate {

    private static final int HASH_PRIME = 31;
    private int row;
    private int col;

    /**
     * Constructs a new Coordinate with a specific row and column value.
     * 
     * @param row the row of the coordinate.
     * @param col the col of the coordinate.
     */
    public Coordinate(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Finds the Manhattan distance between this coordinate and another coordinate.
     * Distance is calculated by summing the distances from both row and column
     * 
     * @param other the coordinate to find distance from.
     * @return      the Manhattan distance between two coordinates.
     */
    public int manDistTo(Coordinate other) {
        return Math.abs(row() - other.row()) + Math.abs(col() - other.col());
    }

    /**
     * Checks if this coordinate is diagonal to another coordinate.
     * Two coordinates are diagonal if not vertical or horizontal.
     * 
     * @param other the coordinate to check against.
     * @return      true if diagonal, false if not.
     */
    public boolean isDiagonalTo(Coordinate other) {
        return Math.abs(row() - other.row()) > 0
                && Math.abs(col() - other.col()) > 0;
    }

    /**
     * Checks if this coordinate is within a rectangular area.
     * The area is bound by minimum and maximum row and column values.
     * 
     * @param minRow    the minimum row value of the area.
     * @param maxRow    the maximum row value of the area.
     * @param minCol    the minimum column value of the area.
     * @param maxCol    the maximum column value of the area.
     * @return          true if this coordinate is within the bounds, false otherwise.
     */
    public boolean isWithinBounds(int minRow, int maxRow, int minCol, int maxCol) {
        return row() >= minRow && row() <= maxRow
                && col() >= minCol && col() <= maxCol;
    }

    /**
     * Moves this coordinate by change in row and column values.
     * Returns a new coordinate of new position without changing this one.
     * 
     * @param rowChange the change in the row value.
     * @param colChange the change in the column value.
     * @return          a new coordinate after changes.
     */
    public Coordinate moveBy(int rowChange, int colChange) {
        return new Coordinate(this.row + rowChange, this.col + colChange);
    }

    // getters

    /**
     * Getter for the row of the coordinate.
     * 
     * @return the row of the coordinate.
     */
    public int row() {
        return row;
    }


    /**
     * Getter for the column of the coordinate.
     * 
     * @return the column of the coordinate.
     */
    public int col() {
        return col;
    }

    // overrides

    /**
     * Checks if the coordinate is equal to another object.
     * Two coordinates are equal if both their row and column values are equal.
     * 
     * @param obj the object to compare with this coordinate.
     * @return true if the object is equal to this coordinate.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        final Coordinate other = (Coordinate) obj;
        return other.row() == row() && other.col == col();
    }

    /**
     * Returns the hash code for the coordinate.
     * The hash code is calculated based on the row and column values.
     * 31 is chosen for prime number hashing to have less likely collisions.
     * 
     * @return the hash code for the coordinate.
     */
    @Override
    public int hashCode() {
        int result = row();
        result = HASH_PRIME * result + col();
        return result;
    }

    /**
     * Returns a string representation of the coordinate.
     * The string contains both the row and column values.
     * 
     * @return the string representation of the coordinate.
     */
    @Override
    public String toString() {
        return "[" + row() + ", " + col() + "]";
    }
}
