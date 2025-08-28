package jungle.squares;

import jungle.Player;

/**
 * The Trap class is a type of square in the game.
 * This square is causes opponent pieces inside to have 0 strength.
 */
public class Trap extends Square {

    /**
     * Constructs a new Trap with an owner.
     * 
     * @param owner the owner of the square.
     */
    public Trap(Player owner) {
        super(owner);
    }

    /**
     * Checks if this trap square is a water square.
     * 
     * @return false always for trap square.
     */
    @Override
    public boolean isWater() {
        return false;
    }

    /**
     * Checks if this trap square is a den square.
     * 
     * @return false always for trap square.
     */
    @Override
    public boolean isDen() {
        return false;
    }

    /**
     * Checks if this trap square is a trap square.
     * 
     * @return true always for trap square.
     */
    @Override
    public boolean isTrap() {
        return true;
    }
}
