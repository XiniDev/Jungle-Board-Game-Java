package jungle.pieces;

import jungle.Game;
import jungle.Player;
import jungle.squares.Square;

/**
 * The Lion class is a special piece in the game.
 * It can leap horizontally and vertically.
 */
public class Lion extends Piece {

    /**
     * Constructs a new Lion with an owner and a square.
     * Rank is automatically set based on lion's rank.
     * 
     * @param owner     the owner of the lion.
     * @param square    the square that the lion is standing on.
     */
    public Lion(Player owner, Square square) {
        super(owner, square, Game.LION_RANK);
    }

    /**
     * Overrides the piece's always false horizontal leap.
     * 
     * @return true always for lion.
     */
    @Override
    public boolean canLeapHorizontally() {
        return true;
    }

    /**
     * Overrides the piece's always false vertical leap.
     * 
     * @return true always for lion.
     */
    @Override
    public boolean canLeapVertically() {
        return true;
    }
}
