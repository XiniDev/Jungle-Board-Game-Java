package jungle;

import java.util.ArrayList;
import java.util.List;

import jungle.pieces.Lion;
import jungle.pieces.Piece;
import jungle.pieces.Rat;
import jungle.pieces.Tiger;

import jungle.squares.Den;
import jungle.squares.PlainSquare;
import jungle.squares.Square;
import jungle.squares.Trap;
import jungle.squares.WaterSquare;

/**
 * The Game class is the main game logic for the Jungle game.
 */
public class Game {

    // board configs

    /** Height of the board. */
    public static final int HEIGHT = 9;
    /** Width of the board. */
    public static final int WIDTH = 7;
    /** Location of the water in rows. */
    public static final int[] WATER_ROWS = {3, 4, 5};
    /** Location of the water in columns. */
    public static final int[] WATER_COLS = {1, 2, 4, 5};
    /** Location of the den column. */
    public static final int DEN_COL = 3;

    // piece ranks

    /** The rank of an elephant. */
    public static final int ELEPHANT_RANK = 8;
    /** The rank of a lion. */
    public static final int LION_RANK = 7;
    /** The rank of a tiger. */
    public static final int TIGER_RANK = 6;
    /** The rank of a leopard. */
    public static final int LEOPARD_RANK = 5;
    /** The rank of a wolf. */
    public static final int WOLF_RANK = 4;
    /** The rank of a dog. */
    public static final int DOG_RANK = 3;
    /** The rank of a cat. */
    public static final int CAT_RANK = 2;
    /** The rank of a rat. */
    public static final int RAT_RANK = 1;

    /**
     * Possible move distances and direction offsets.
     * Numbers are made this way to optimise calculation of legal moves.
     */
    private static final int[] STEP_SIZES = {1, 3, 4};
    private static final int[] DIRECTION_OFFSETS = {-2, -1, 1, 2};

    private Player p0;
    private Player p1;
    private Player turn;

    private Square[][] squareBoard;
    private Piece[][] pieceBoard;

    /**
     * Constructs a new Game containing two players.
     * 
     * @param p0 the first player.
     * @param p1 the second player.
     */
    public Game(Player p0, Player p1) {
        if (p0.getPlayerNumber() != 0 || p1.getPlayerNumber() != 1) {
            throw new IllegalArgumentException("Player numbers must be 0 and 1.");
        }

        this.p0 = p0;
        this.p1 = p1;
        this.turn = p0;

        this.squareBoard = new Square[HEIGHT][WIDTH];
        this.pieceBoard = new Piece[HEIGHT][WIDTH];

        initGame();
    }

    /**
     * Adds the starting pieces to the board for both players.
     * Follows the rules of a typical Jungle Game.
     */
    public void addStartingPieces() {
        // player 1 pieces
        addPiece(0, 0, LION_RANK, 0);
        addPiece(0, 6, TIGER_RANK, 0);
        addPiece(1, 1, DOG_RANK, 0);
        addPiece(1, 5, CAT_RANK, 0);
        addPiece(2, 0, RAT_RANK, 0);
        addPiece(2, 2, LEOPARD_RANK, 0);
        addPiece(2, 4, WOLF_RANK, 0);
        addPiece(2, 6, ELEPHANT_RANK, 0);

        // player 2 pieces
        addPiece(8, 6, LION_RANK, 1);
        addPiece(8, 0, TIGER_RANK, 1);
        addPiece(7, 5, DOG_RANK, 1);
        addPiece(7, 1, CAT_RANK, 1);
        addPiece(6, 6, RAT_RANK, 1);
        addPiece(6, 4, LEOPARD_RANK, 1);
        addPiece(6, 2, WOLF_RANK, 1);
        addPiece(6, 0, ELEPHANT_RANK, 1);
    }

    /**
     * Adds one piece to the board at a specific row and column.
     * Overrides spaces that are already occupied by pieces by replacing it.
     * 
     * @param row           the row to add the piece.
     * @param col           the column to add the piece.
     * @param rank          the rank of the piece.
     * @param playerNumber  the player number that owns the piece.
     */
    public void addPiece(int row, int col, int rank, int playerNumber) {
        Player player = getPlayer(playerNumber);
        Piece piece;

        switch (rank) {
            case RAT_RANK:
                piece = new Rat(player, getSquare(row, col));
                break;
            case TIGER_RANK:
                piece = new Tiger(player, getSquare(row, col));
                break;
            case LION_RANK:
                piece = new Lion(player, getSquare(row, col));
                break;
            default:
                piece = new Piece(player, getSquare(row, col), rank);
                break;
        }

        setPiece(piece, row, col);
    }

    /**
     * Returns the piece at the specific row and column.
     * 
     * @param row   the row of the piece.
     * @param col   the column of the piece.
     * @return      the piece at the location, may be null if empty.
     */
    public Piece getPiece(int row, int col) {
        return pieceBoard[row][col];
    }

    /**
     * Returns the piece at the specific coordinate.
     * 
     * @param coord the coordinate of the piece.
     * @return      the piece at the location, may be null if empty.
     */
    public Piece getPiece(Coordinate coord) {
        return getPiece(coord.row(), coord.col());
    }

    /**
     * Moves a piece from one specific row and column location to another.
     * 
     * @param fromRow   the row the piece is moving from.
     * @param fromCol   the column the piece is moving from.
     * @param toRow     the row the piece is moving to.
     * @param toCol     the column the piece is moving to.
     */
    public void move(int fromRow, int fromCol, int toRow, int toCol) {
        Coordinate fromCoord = new Coordinate(fromRow, fromCol);
        Coordinate toCoord = new Coordinate(toRow, toCol);

        Piece piece = getMovingPiece(fromCoord);
        validateMove(piece, fromCoord, toCoord);

        handleCapture(toCoord);
        movePiece(piece, fromCoord, toCoord);
        changeTurn();
    }

    /**
     * Returns the current player that has a turn.
     * 
     * @return the turn of the current player.
     */
    public Player getTurn() {
        return turn;
    }

    /**
     * Retrieves a player based on their player number.
     * 
     * @param   playerNumber the player's number
     * @return  the corresponding Player object
     */
    public Player getPlayer(int playerNumber) throws IllegalArgumentException {
        if (playerNumber < 0 || playerNumber > 1) {
            throw new IllegalArgumentException("Invalid player number: "
                                                + playerNumber + ".");
        }

        return p0.getPlayerNumber() == playerNumber ? p0 : p1;
    }

    /**
     * Returns the winner of the game.
     * The winner is obtained either from capturing all opponent's pieces,
     * or from capturing opponent's den.
     * 
     * @return the winning player unless none.
     */
    public Player getWinner() {
        if (!p0.hasPieces() || p1.hasCapturedDen()) {
            return p1;
        } else if (!p1.hasPieces() || p0.hasCapturedDen()) {
            return p0;
        } else {
            return null;
        }
    }

    /**
     * Checks if the game is over.
     * 
     * @return true if game is over, false otherwise.
     */
    public boolean isGameOver() {
        return getWinner() != null;
    }

    /**
     * Returns the square at the specific row and column.
     * 
     * @param row   the row of the square.
     * @param col   the column of the square.
     * @return      the square at the location, may be null if empty.
     */
    public Square getSquare(int row, int col) {
        return squareBoard[row][col];
    }

    /**
     * Returns the square at the specific coordinate.
     * 
     * @param coord the coordinate of the square.
     * @return      the square at the location, may be null if empty.
     */
    public Square getSquare(Coordinate coord) {
        return getSquare(coord.row(), coord.col());
    }

    /**
     * Returns a list of legal moves for the piece at the specific row and column.
     * When the game is over, or when no pieces are selected, return an empty list.
     * 
     * @param row   the row of the piece.
     * @param col   the column of the piece.
     * @return      a list of coordinates containing all legal moves.
     */
    public List<Coordinate> getLegalMoves(int row, int col)
                                throws IndexOutOfBoundsException {
        Coordinate fromCoord = new Coordinate(row, col);
        validateCoordinate(fromCoord);

        List<Coordinate> legalMoves = new ArrayList<>();
        Piece piece = getPiece(fromCoord);

        if (isGameOver() || piece == null) {
            return legalMoves;
        }

        legalMoves = generateLegalMoves(piece, fromCoord);

        return legalMoves;
    }

    /**
     * Determines whether a move is legal by checking multiple conditions.
     * Ensures piece's turn.
     * Ensures coordinates are in board.
     * Ensures moving vertically or horizontally.
     * Ensures can defeat if moving into opponent piece.
     * Ensures moving into plain or can swim when moving one square.
     * Ensures can leap over water when moving more than one square.
     * Overloading method for the private method for ease of access.
     * 
     * @param fromRow   the row the piece is moving from.
     * @param fromCol   the column the piece is moving from.
     * @param toRow     the row the piece is moving to.
     * @param toCol     the column the piece is moving to.
     * @return          true if the move is legal, false if otherwise.
     */
    public boolean isLegalMove(int fromRow, int fromCol, int toRow, int toCol) {
        return isLegalMove(getPiece(fromRow, fromCol),
                           new Coordinate(fromRow, fromCol),
                           new Coordinate(toRow, toCol));
    }

    /**
     * Checks if the turn allows this piece to be used.
     */
    public boolean isPieceTurn(Piece piece) {
        return piece.getOwner() == getTurn();
    }

    // initialisation methods

    private void initGame() {
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                squareBoard[row][col] = createSquare(row, col);
            }
        }
    }

    private Square createSquare(int row, int col) {
        if (isDenSquare(row, col)) {
            return new Den(getPlayerByRow(row));
        } else if (isTrapSquare(row, col)) {
            return new Trap(getPlayerByRow(row));
        } else if (isWaterSquare(row, col)) {
            return new WaterSquare();
        } else {
            return new PlainSquare();
        }
    }

    private Player getPlayerByRow(int row) {
        return row < HEIGHT / 2 ? p0 : p1;
    }

    private boolean isDenSquare(int row, int col) {
        return col == DEN_COL && (row == 0 || row == HEIGHT - 1);
    }

    private boolean isTrapSquare(int row, int col) {
        return (col == DEN_COL - 1 || col == DEN_COL + 1)
                && (row == 0 || row == HEIGHT - 1)
                || col == DEN_COL
                && (row == 1 || row == HEIGHT - 2);
    }

    private boolean isWaterSquare(int row, int col) {
        for (int r : WATER_ROWS) {
            for (int c : WATER_COLS) {
                if (r == row && c == col) {
                    return true;
                }
            }
        }
        return false;
    }

    // turn management

    private void changeTurn() {
        turn = (turn == p0) ? p1 : p0;
    }

    // coordinate validation

    private boolean isCoordWithinBoard(Coordinate coord) {
        return coord.isWithinBounds(0, HEIGHT - 1, 0, WIDTH - 1);
    }

    private void validateCoordinate(Coordinate coord) {
        if (!isCoordWithinBoard(coord)) {
            throw new IndexOutOfBoundsException("Square "
                                                + coord.toString()
                                                + " is outside of board.");
        }
    }

    // piece management

    private void setPiece(Piece piece, int row, int col) {
        pieceBoard[row][col] = piece;
    }

    private void setPiece(Piece piece, Coordinate coord) {
        setPiece(piece, coord.row(), coord.col());
    }

    private Piece getMovingPiece(Coordinate fromCoord) {
        Piece piece = getPiece(fromCoord);
        if (piece == null) {
            throw new IllegalArgumentException("No piece at "
                                               + fromCoord.toString()
                                               + ".");
        }
        return piece;
    }

    private void handleCapture(Coordinate toCoord) {
        Piece targetPiece = getPiece(toCoord);
        if (targetPiece != null) {
            targetPiece.beCaptured();
        }
    }

    private void movePiece(Piece piece, Coordinate fromCoord, Coordinate toCoord) {
        setPiece(piece, toCoord);
        setPiece(null, fromCoord);
        piece.move(getSquare(toCoord));
    }

    // move validation

    private void validateMove(Piece piece, Coordinate fromCoord, Coordinate toCoord) {
        if (!isLegalMove(piece, fromCoord, toCoord)) {
            throw new IllegalMoveException("Illegal move from "
                                            + fromCoord.toString() + " to "
                                            + toCoord.toString() + ".");
        }
    }

    private boolean isLegalMove(Piece piece, Coordinate fromCoord, Coordinate toCoord) {
        if (!isPieceTurn(piece)
            || !isCoordWithinBoard(fromCoord)
            || !isCoordWithinBoard(toCoord)
            || fromCoord.isDiagonalTo(toCoord)) {
            return false;
        }

        // cannot move into own den (wiki rule)
        Square targetSquare = getSquare(toCoord);
        if (targetSquare.isDen() && targetSquare.isOwnedBy(piece.getOwner())) {
            return false; // cannot move into own den
        }

        Piece targetPiece = getPiece(toCoord);
        if (targetPiece != null
            && (piece.getOwner() == targetPiece.getOwner()
            || !piece.canDefeat(targetPiece))) {
            return false;
        }

        int distance = fromCoord.manDistTo(toCoord);
        // Square targetSquare = getSquare(toCoord);

        if (distance == 1) {
            return !targetSquare.isWater() || piece.canSwim();
        } else if (distance > 1) {
            return isValidLeap(piece, fromCoord, toCoord, distance);
        }

        return false;
    }

    private boolean isValidLeap(Piece piece,
                                Coordinate fromCoord,
                                Coordinate toCoord,
                                int distance) {
        return (piece.canLeapHorizontally() && distance == 3
                || piece.canLeapVertically() && distance == 4)
                && isLeapingOverWater(fromCoord, toCoord);
    }

    private boolean isLeapingOverWater(Coordinate fromCoord, Coordinate toCoord) {
        int deltaRow = toCoord.row() - fromCoord.row();
        int deltaCol = toCoord.col() - fromCoord.col();

        int rowStep = Integer.signum(deltaRow);
        int colStep = Integer.signum(deltaCol);
        int steps = Math.max(Math.abs(deltaRow), Math.abs(deltaCol));

        for (int i = 1; i < steps; i++) {
            Coordinate coordStep = fromCoord.moveBy(i * rowStep, i * colStep);

            if (!getSquare(coordStep).isWater()
                || getPiece(coordStep) != null) {
                return false;
            }
        }

        return !getSquare(toCoord).isWater();
    }

    // legal move generation

    private List<Coordinate> generateLegalMoves(Piece piece, Coordinate fromCoord) {
        List<Coordinate> legalMoves = new ArrayList<>();

        for (int step : STEP_SIZES) {
            for (int direction : DIRECTION_OFFSETS) {
                int deltaRow = (direction / 2) * step;
                int deltaCol = (direction % 2) * step;
                Coordinate toCoord = fromCoord.moveBy(deltaRow, deltaCol);
                if (isLegalMove(piece, fromCoord, toCoord)) {
                    legalMoves.add(toCoord);
                }
            }
        }
        return legalMoves;
    }
}
