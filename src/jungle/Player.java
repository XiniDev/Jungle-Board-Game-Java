package jungle;

/**
 * The Player class stores the player information in the game.
 * It tracks important information unique to the player,
 * and also information to track win conditions.
 */
public class Player {

    private String name;
    private int playerNumber;
    private boolean capturedDen;
    private int pieces;

    /**
     * Constructs a new Player with the name and player number.
     * Initially, opponent's Den is not captured and player has no pieces.
     * 
     * @param name          the name of the player.
     * @param playerNumber  the id of the player.
     */
    public Player(String name, int playerNumber) {
        this.name = name;
        this.playerNumber = playerNumber;
        this.capturedDen = false;
        this.pieces = 0;
    }

    /**
     * Marks the capturing of the opponent's den.
     * Called when the player moves into the opponent's den.
     */
    public void captureDen() {
        capturedDen = true;
    }

    /**
     * Checks if the player has captured the opponent's den.
     * 
     * @return true if the player captured it.
     */
    public boolean hasCapturedDen() {
        return capturedDen;
    }

    /**
     * Checks if the player still has any pieces left for win condition.
     * 
     * @return true if the player still has pieces left.
     */
    public boolean hasPieces() {
        return pieces > 0;
    }

    /**
     * Increases the player piece count by one.
     * Called when the player piece a piece (e.g. from the start).
     */
    public void gainOnePiece() {
        pieces++;
    }

    /**
     * Decreases the player piece count by one.
     * Called when the player loses a piece (e.g. from being captured).
     */
    public void loseOnePiece() {
        pieces--;
    }

    // getters

    /**
     * Getter for the name of the player.
     * 
     * @return the name of the player.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the player number of the player.
     * 
     * @return the player number of the player.
     */
    public int getPlayerNumber() {
        return playerNumber;
    }


    // overrides

    /**
     * Checks if the player is equal to another object.
     * Two players are equal if their player numbers are equal.
     * 
     * @param obj the object to compare with this player.
     * @return true if the object is equal to this player.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        final Player other = (Player) obj;
        return other.getPlayerNumber() == getPlayerNumber();
    }


    /**
     * Returns the hash code for the player.
     * The hash code is calculated based on the player number.
     * 
     * @return the hash code for the player.
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(getPlayerNumber());
    }
}
