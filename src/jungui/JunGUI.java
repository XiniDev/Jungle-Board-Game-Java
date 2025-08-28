package jungui;

import jungle.Coordinate;
import jungle.Game;
import jungle.Player;
import jungle.pieces.Piece;
import jungle.squares.Square;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * JunGUI uses as the Model-View Paradigm to display the Jungle Game.
 * Separated into "View Methods" and "Controller Methods" for neatness.
 * JunGUI is created to be independent of the game.
 * This is so that the game is a standalone, without changing the game,
 * the view can be swapped out with a different one if wanted.
 */
public class JunGUI extends JFrame {
    private static final int CELL_SIZE = 100;
    private static final String FONT_TYPE = "Arial";

    private static final Font TEXT_FONT = new Font(FONT_TYPE, Font.PLAIN, 20);
    private static final Font TITLE_FONT = new Font(FONT_TYPE, Font.PLAIN, 48);

    private static final String TITLE_TEXT = "Jungle Game by JunGUI";

    private static final int TITLE_MARGIN = 100;
    private static final int BOTTOM_SPACING = 400;

    private static final int PLAYER_TEXT_FIELD_COLS = 20;
    private static final Dimension START_BUTTON_SIZE = new Dimension(150, 40);

    private static final double PIECE_SCALE_FACTOR = 0.8;
    private static final double HIGHLIGHT_SCALE_FACTOR = 0.4;

    private Game game;
    private JPanel menuPanel;
    private JPanel boardPanel;
    private JLabel[][] boardLabels;
    private int selectedRow;
    private int selectedCol;
    private List<Coordinate> highlightedCells;

    private ImageIcon denBackground;
    private ImageIcon trapBackground;
    private ImageIcon waterBackground;
    private ImageIcon plainBackground;

    private ImageIcon[][] pieceIcons;

    private ImageIcon highlightIcon;

    /**
     * Constructs a new JunGUI which performs initialisation.
     * It initialises the menu screen before starting the game.
     */
    public JunGUI() {
        this.selectedRow = -1;
        this.selectedCol = -1;
        this.highlightedCells = new ArrayList<Coordinate>();

        initWindow();

        initMenu();
    }

    // getters

    /**
     * Getter for the row of the selected coordinate.
     * 
     * @return the row of the selected coordinate.
     */
    public int getSelectedRow() {
        return selectedRow;
    }

    /**
     * Getter for the column of the selected coordinate.
     * 
     * @return the column of the selected coordinate.
     */
    public int getSelectedCol() {
        return selectedCol;
    }

    // ============================
    // View Methods
    // ============================

    // initialisation before game starts methods

    private void initWindow() {
        setTitle("JunGUI");
        setSize(CELL_SIZE * Game.WIDTH, CELL_SIZE * Game.HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    private void initMenu() {
        menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

        // menu title with margins on each end
        menuPanel.add(Box.createVerticalStrut(TITLE_MARGIN));
        menuPanel.add(generateMenuTitle());
        menuPanel.add(Box.createVerticalStrut(TITLE_MARGIN));

        // text fields for player names entry
        JTextField[] playerTextFields = new JTextField[2];
        for (int i = 0; i <= 1; i++) {
            JPanel nameField = generateNameField(i);
            menuPanel.add(nameField);
            playerTextFields[i] = (JTextField) nameField.getComponent(1);
        }

        // start button
        menuPanel.add(generateStartButton(playerTextFields));
        menuPanel.add(Box.createVerticalStrut(BOTTOM_SPACING));

        getContentPane().add(menuPanel);
    }

    private JLabel generateMenuTitle() {
        JLabel titleLabel = new JLabel(TITLE_TEXT, SwingConstants.CENTER);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return titleLabel;
    }

    private JPanel generateNameField(int playerNumber) {
        int textNumber = playerNumber + 1;

        JPanel playerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel playerLabel = new JLabel("Player " + textNumber + ": ");
        playerLabel.setFont(TEXT_FONT);
        JTextField playerTextField = new JTextField("Player "
                                                    + textNumber, PLAYER_TEXT_FIELD_COLS);
        playerTextField.setFont(TEXT_FONT);
        playerPanel.add(playerLabel);
        playerPanel.add(playerTextField);
        return playerPanel;
    }

    private JPanel generateStartButton(JTextField[] playerTextFields) {
        JButton startButton = new JButton("Start Game");
        startButton.setFont(TEXT_FONT);
        startButton.setPreferredSize(START_BUTTON_SIZE);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(playerTextFields[0].getText().trim(),
                          playerTextFields[1].getText().trim());
            }
        });
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(startButton);
        return buttonPanel;
    }

    // initialisation after menu screen methods

    private void startGame(String playerName0, String playerName1) {
        // cannot start game if names are empty
        if (playerName0.isEmpty() || playerName1.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                                          "Player names cannot be empty.",
                                          "Error",
                                          JOptionPane.ERROR_MESSAGE);
            return;
        }

        getContentPane().remove(menuPanel);

        Player player1 = new Player(playerName0, 0);
        Player player2 = new Player(playerName1, 1);
        game = new Game(player1, player2);
        game.addStartingPieces();

        loadResources();
        initBoard();

        revalidate();
        repaint();
    }

    private void loadResources() {
        String[] pieceNames = {"piece",
                               "rat",
                               "cat",
                               "dog",
                               "wolf",
                               "leopard",
                               "tiger",
                               "lion",
                               "elephant"};
        ResourceLoader loader = new ResourceLoader(CELL_SIZE);
        denBackground = loader.loadIcon("den.png");
        trapBackground = loader.loadIcon("trap.png");
        waterBackground = loader.loadIcon("water.png");
        plainBackground = loader.loadIcon("plain.png");
        highlightIcon = loader.loadIcon("highlight.png");
        pieceIcons = loadPieceIcons(loader, pieceNames);
    }

    private ImageIcon[][] loadPieceIcons(ResourceLoader loader, String[] pieceNames) {
        ImageIcon[][] pieceIcons = new ImageIcon[2][pieceNames.length];

        for (int pNum = 0; pNum < 2; pNum++) {
            for (int rank = 0; rank < pieceNames.length; rank++) {
                String fileName = pieceNames[rank] + ".png";
                pieceIcons[pNum][rank] = loader.loadPieceIcon(fileName, pNum);
            }
        }
        return pieceIcons;
    }

    private void initBoard() {
        boardPanel = new JPanel(new GridLayout(Game.HEIGHT, Game.WIDTH));
        boardLabels = new JLabel[Game.HEIGHT][Game.WIDTH];

        for (int row = 0; row < Game.HEIGHT; row++) {
            for (int col = 0; col < Game.WIDTH; col++) {
                JLabel cellLabel = createCellLabel(row, col);
                boardLabels[row][col] = cellLabel;
                boardPanel.add(cellLabel);
            }
        }

        add(boardPanel, BorderLayout.CENTER);
    }

    private JLabel createCellLabel(int row, int col) {
        JLabel label = new JLabel();
        label.setOpaque(true);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        updateCellLabel(label, row, col);

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleSquareClick(row, col);
            }
        });

        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return label;
    }

    // UI update methods

    private void updateCellLabel(JLabel label, int row, int col) {
        Square square = game.getSquare(row, col);
        Piece piece = game.getPiece(row, col);

        ImageIcon backgroundIcon;
        if (square.isDen()) {
            backgroundIcon = denBackground;
        } else if (square.isTrap()) {
            backgroundIcon = trapBackground;
        } else if (square.isWater()) {
            backgroundIcon = waterBackground;
        } else {
            backgroundIcon = plainBackground;
        }

        ImageIcon highlightOption = isCellHighlighted(row, col) ? highlightIcon : null;

        if (piece != null) {
            ImageIcon pieceIcon = getPieceIcon(piece, piece.getOwner().getPlayerNumber());
            label.setIcon(stackIcons(backgroundIcon,
                                     pieceIcon,
                                     highlightOption,
                                     CELL_SIZE,
                                     CELL_SIZE));
        } else {
            label.setIcon(stackIcons(backgroundIcon,
                                     null,
                                     highlightOption,
                                     CELL_SIZE,
                                     CELL_SIZE));
        }
    }

    private ImageIcon getPieceIcon(Piece piece, int playerNumber) {
        switch (piece.getRank()) {
            case Game.RAT_RANK:
                return pieceIcons[playerNumber][Game.RAT_RANK];
            case Game.CAT_RANK:
                return pieceIcons[playerNumber][Game.CAT_RANK];
            case Game.DOG_RANK:
                return pieceIcons[playerNumber][Game.DOG_RANK];
            case Game.WOLF_RANK:
                return pieceIcons[playerNumber][Game.WOLF_RANK];
            case Game.LEOPARD_RANK:
                return pieceIcons[playerNumber][Game.LEOPARD_RANK];
            case Game.TIGER_RANK:
                return pieceIcons[playerNumber][Game.TIGER_RANK];
            case Game.LION_RANK:
                return pieceIcons[playerNumber][Game.LION_RANK];
            case Game.ELEPHANT_RANK:
                return pieceIcons[playerNumber][Game.ELEPHANT_RANK];
            default:
                return pieceIcons[playerNumber][0];
        }
    }

    private boolean isCellHighlighted(int row, int col) {
        return highlightedCells.contains(new Coordinate(row, col));
    }

    // drawing methods for updating game

    private ImageIcon stackIcons(ImageIcon background,
                                 ImageIcon piece,
                                 ImageIcon highlight,
                                 int width,
                                 int height) {
        BufferedImage stackedImage = new BufferedImage(width,
                                                       height,
                                                       BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = stackedImage.createGraphics();

        // anti aliasing for smooth edges
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);

        // draw square
        g2.drawImage(background.getImage(), 0, 0, width, height, null);

        // draw piece on top of square if need
        if (piece != null) {
            drawCenteredImage(g2, piece, width, height, PIECE_SCALE_FACTOR);
        }

        // draw highlight on top of piece if need
        if (highlight != null) {
            drawCenteredImage(g2, highlight, width, height, HIGHLIGHT_SCALE_FACTOR);
        }

        g2.dispose();
        return new ImageIcon(stackedImage);
    }

    private void drawCenteredImage(Graphics2D g2,
                                   ImageIcon icon,
                                   int width,
                                   int height,
                                   double scaleFactor) {
        int iconSize = (int) (height * scaleFactor);
        int iconX = (width - iconSize) / 2;
        int iconY = (height - iconSize) / 2;
        g2.drawImage(icon.getImage(), iconX, iconY, iconSize, iconSize, null);
    }

    // ============================
    // Controller Methods
    // ============================

    // handling clicking on squares method

    private void handleSquareClick(int row, int col) {
        if (selectedRow == -1 && selectedCol == -1) {
            selectPiece(row, col);
        } else {
            makeMove(row, col);
        }
    }

    // actions of player (select and move) on click

    private void selectPiece(int row, int col) {
        Piece piece = game.getPiece(row, col);
        if (piece == null || !game.isPieceTurn(piece)) {
            return;
        }

        setSelected(row, col);
        highlightedCells.clear();
        highlightedCells.addAll(game.getLegalMoves(row, col));

        updateBoard();
    }

    private void makeMove(int row, int col) {
        if (game.isLegalMove(selectedRow, selectedCol, row, col)) {
            game.move(selectedRow, selectedCol, row, col);
        }

        highlightedCells.clear();
        updateBoard();
        setSelected(-1, -1);

        if (game.isGameOver()) {
            String winMessage = game.getWinner().getName() + " has won the game!";
            JOptionPane.showMessageDialog(this, winMessage);
            System.exit(0);
        }
    }

    // game update method on user action

    private void updateBoard() {
        for (int row = 0; row < Game.HEIGHT; row++) {
            for (int col = 0; col < Game.WIDTH; col++) {
                updateCellLabel(boardLabels[row][col], row, col);
            }
        }
    }

    // private setters

    private void setSelected(int row, int col) {
        selectedRow = row;
        selectedCol = col;
    }
}
