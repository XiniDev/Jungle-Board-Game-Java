package jungui;

import java.awt.Image;

import java.net.URL;

import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

/**
 * The ResourceLoader class is used for loading images resources for a game.
 */
public class ResourceLoader {
    private static final int PIECE_DIAMETER = 60;

    private int cellSize;
    private Map<String, ImageIcon> iconCache = new HashMap<>();

    /**
     * Constructs a new ResourceLoader with a fixed cell size for image scaling.
     * 
     * @param cellSize the fixed cell size in pixels.
     */
    public ResourceLoader(int cellSize) {
        this.cellSize = cellSize;
    }

    /**
     * Loads an icon with the specific filename.
     * 
     * @param fileName  the name of the file to load.
     * @return          the image icon of the loaded image, or null if failure.
     */
    public ImageIcon loadIcon(String fileName) {
        return iconCache.computeIfAbsent(fileName, key -> {
            try {
                URL imgURL = getClass().getResource("/resources/" + key);
                Image image = new ImageIcon(imgURL).getImage();
                return new ImageIcon(image.getScaledInstance(cellSize,
                                                             cellSize,
                                                             Image.SCALE_SMOOTH));
            } catch (NullPointerException e) {
                System.err.println("Error loading image: " + key);
                return null;
            }
        });
    }

    /**
     * Loads a piece icon with the specific filename.
     * Uses PieceIcon to load the image as it loads a specific circular icon.
     * 
     * @param fileName  the name of the file to load.
     * @return          the image icon of the loaded image, or null if failure.
     */
    public ImageIcon loadPieceIcon(String fileName, int playerNumber) {
        String key = "pieces/" + fileName;
        try {
            URL imgURL = getClass().getResource("/resources/" + key);
            Image image = new ImageIcon(imgURL).getImage();
            return new PieceIcon(image, PIECE_DIAMETER, playerNumber);
        } catch (NullPointerException e) {
            System.out.println("Error loading image");
            return null;
        }
    }
}
