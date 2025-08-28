package jungle.pieces;

import jungle.Game;
import jungle.Player;
import jungle.squares.Square;

/**
 * The Rat class is a special piece in the game.
 * It can swim and deafeat the elephant piece.
 */
public class Rat extends Piece {

    /**
     * Constructs a new Rat with an owner and a square.
     * Rank is automatically set based on rat's rank.
     * 
     * @param owner     the owner of the rat.
     * @param square    the square that the rat is standing on.
     */
    public Rat(Player owner, Square square) {
        super(owner, square, Game.RAT_RANK);
    }

    /**
     * Overrides the piece's always false can swim.
     * 
     * @return true always for rat.
     */
    @Override
    public boolean canSwim() {
        return true;
    }


    /**
     * Overrides the piece's default can defeat.
     * Unless the rat's target is an elephant, it will call super.
     * 
     * @return true always for rat.
     */
    @Override
    public boolean canDefeat(Piece target) {
        if (target.getRank() == Game.ELEPHANT_RANK) {
            return true;
        }
        return super.canDefeat(target);
    }
}
