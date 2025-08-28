package jungle.pieces;

import jungle.Game;
import jungle.Player;
import jungle.squares.Square;

/**
 * The Lion class is a special piece in the game.
 * It can leap vertically.
 */
public class Tiger extends Piece {

    /**
     * Constructs a new Tiger with an owner and a square.
     * Rank is automatically set based on tiger's rank.
     * 
     * @param owner     the owner of the tiger.
     * @param square    the square that the tiger is standing on.
     */
    public Tiger(Player owner, Square square) {
        super(owner, square, Game.TIGER_RANK);
    }

    /**
     * Overrides the piece's always false horizontal leap.
     * 
     * @return true always for tiger.
     */
    @Override
    public boolean canLeapHorizontally() {
        return true;
    }
}
