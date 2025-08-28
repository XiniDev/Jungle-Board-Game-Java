package jungle.pieces;

import jungle.squares.Square;
import jungle.Player;

/**
 * The Piece class represents a piece in the game.
 * It stores information to interact with other pieces and terrain.
 */
public class Piece {

    private Player owner;
    private Square square;
    private int rank;

    /**
     * Constructs a new Piece with an owner, a square and a rank.
     * The rank determines its strength except on opponent traps.
     * The square gives terrain information to determine legal movement.
     * Owner gains one piece whenever a piece is instantiated.
     * 
     * @param owner     the owner of the piece.
     * @param square    the square that the piece is standing on.
     * @param rank      the rank of the piece.
     */
    public Piece(Player owner, Square square, int rank) {
        this.owner = owner;
        this.square = square;
        this.rank = rank;
        owner.gainOnePiece();
    }

    /**
     * Checks if the piece's owner equals a specified player.
     * 
     * @return true if the piece's owner is the specified player.
     */
    public boolean isOwnedBy(Player player) {
        return owner.equals(player);
    }

    /**
     * Returns the strength of the piece by its rank.
     * Returns 0 strength if the piece is on opponent's trap.
     * 
     * @return the current strength of the piece.
     */
    public int getStrength() {
        return isOnOpponentTrap() ? 0 : getRank();
    }

    /**
     * Checks if this piece can swim.
     * 
     * @return false always for any arbitrary piece.
     */
    public boolean canSwim() {
        return false;
    }

    /**
     * Checks if this piece can leap horizontally.
     * 
     * @return false always for any arbitrary piece.
     */
    public boolean canLeapHorizontally() {
        return false;
    }

    /**
     * Checks if this piece can leap vertically.
     * 
     * @return false always for any arbitrary piece.
     */
    public boolean canLeapVertically() {
        return false;
    }

    /**
     * Changes the square of the piece to a different square.
     * Capture the den if the piece is on opponent's den.
     */
    public void move(Square toSquare) {
        square = toSquare;
        if (isOnOpponentDen()) {
            owner.captureDen();
        }
    }

    /**
     * Checks if the target piece can be defeated by this piece.
     * 
     * @return true if this piece can defeat the target.
     */
    public boolean canDefeat(Piece target) {
        return getStrength() >= target.getStrength();
    }

    /**
     * Causes the piece to be captured by the owner losing one piece.
     * Called when the piece is captured during the game.
     */
    public void beCaptured() {
        owner.loseOnePiece();
    }

    /**
     * Checks if the player is on the opponent's trap.
     * 
     * @return true if the player is on the opponent's trap
     */
    public boolean isOnOpponentTrap() {
        return getSquare().isTrap() && !getSquare().isOwnedBy(owner);
    }

    /**
     * Checks if the player is on the opponent's den.
     * 
     * @return true if the player is on the opponent's den
     */
    private boolean isOnOpponentDen() {
        return getSquare().isDen() && !getSquare().isOwnedBy(owner);
    }

    // getters

    /**
     * Getter for the owner of the piece.
     * 
     * @return the owner of the piece.
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * Getter for the rank of the piece.
     * 
     * @return the rank of the piece.
     */
    public int getRank() {
        return rank;
    }

    /**
     * Getter for the square that the piece is on.
     * 
     * @return the square of the piece.
     */
    public Square getSquare() {
        return square;
    }
}
