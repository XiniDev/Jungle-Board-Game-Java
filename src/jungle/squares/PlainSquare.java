package jungle.squares;

/**
 * The PlainSquare class is a type of square in the game.
 * This square is a basic square in the game.
 */
public class PlainSquare extends Square {

    /**
     * Constructs a new PlainSquare without an owner.
     * Owner is set to null as there is no owner for plain squares.
     */
    public PlainSquare() {
        super(null);
    }

    /**
     * Checks if this plain square is a water square.
     * 
     * @return false always for plain square.
     */
    @Override
    public boolean isWater() {
        return false;
    }

    /**
     * Checks if this plain square is a den square.
     * 
     * @return false always for plain square.
     */
    @Override
    public boolean isDen() {
        return false;
    }

    /**
     * Checks if this plain square is a trap square.
     * 
     * @return false always for plain square.
     */
    @Override
    public boolean isTrap() {
        return false;
    }
}
