package jungle.squares;

import jungle.Player;

/**
 * The Den class is a type of square in the game.
 * This square is the winning objective of the game.
 */
public class Den extends Square {

    /**
     * Constructs a new Den with an owner.
     * 
     * @param owner the owner of the square.
     */
    public Den(Player owner) {
        super(owner);
    }

    /**
     * Checks if this den square is a water square.
     * 
     * @return false always for den square.
     */
    @Override
    public boolean isWater() {
        return false;
    }

    /**
     * Checks if this den square is a den square.
     * 
     * @return true always for den square.
     */
    @Override
    public boolean isDen() {
        return true;
    }

    /**
     * Checks if this den square is a trap square.
     * 
     * @return false always for den square.
     */
    @Override
    public boolean isTrap() {
        return false;
    }
}
