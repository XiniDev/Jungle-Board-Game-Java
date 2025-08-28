package jungle.squares;

/**
 * The WaterSquare class is a type of square in the game.
 * This square introduces swimming and leaping into the game.
 */
public class WaterSquare extends Square {

    /**
     * Constructs a new WaterSquare without an owner.
     * Owner is set to null as there is no owner for water squares.
     */
    public WaterSquare() {
        super(null);
    }

    /**
     * Checks if this water square is a water square.
     * 
     * @return true always for water square.
     */
    @Override
    public boolean isWater() {
        return true;
    }

    /**
     * Checks if this water square is a den square.
     * 
     * @return false always for water square.
     */
    @Override
    public boolean isDen() {
        return false;
    }

    /**
     * Checks if this water square is a trap square.
     * 
     * @return false always for water square.
     */
    @Override
    public boolean isTrap() {
        return false;
    }
}
