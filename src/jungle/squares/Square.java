package jungle.squares;

import jungle.Player;

/**
 * The Square abstract class stores a square template for the game.
 * It stores information of its own terrain type.
 */
public abstract class Square {

    private Player owner;

    /**
     * Constructs a new Square with an owner.
     * 
     * @param owner the owner of the square.
     */
    public Square(Player owner) {
        this.owner = owner;
    }

    /**
     * Returns the player that owns the square.
     * 
     * @param   player the player that is being checked against.
     * @return  true if the player owns square or false if not.
     */
    public boolean isOwnedBy(Player player) {
        if (owner == null || owner.equals(null)) {
            return false;
        }
        return owner.equals(player);
    }

    /**
     * Checks if this square is a water square.
     * 
     * @return true if it is a water square.
     */
    public abstract boolean isWater();

    /**
     * Checks if this square is a den square.
     * 
     * @return true if it is a den square.
     */
    public abstract boolean isDen();

    /**
     * Checks if this square is a trap square.
     * 
     * @return true if it is a trap square.
     */
    public abstract boolean isTrap();
}
