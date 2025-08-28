package jungui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

/**
 * The class PieceIcon is a subclass of ImageIcon.
 * It creates a circular image icon for each piece in JunGUI.
 * The colour of the piece depends on the player number
 */
public class PieceIcon extends ImageIcon {

    private static final int BORDER_THICKNESS = 10;

    /**
     * Constructs a new PieceIcon with an image, diameter, and player number.
     * The border colour is determined based on the player number.
     *
     * @param image         the source image of the piece.
     * @param diameter      the diameter of the circular icon.
     * @param playerNumber  the player number for border colour.
     */
    public PieceIcon(Image image, int diameter, int playerNumber) {
        super(getCircularImage(image, diameter, playerNumber));
    }

    private static Image getCircularImage(Image image, int diameter, int playerNumber) {
        int totalDiameter = diameter + (BORDER_THICKNESS * 2);
        BufferedImage circularImage = new BufferedImage(totalDiameter,
                                                        totalDiameter,
                                                        BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = circularImage.createGraphics();

        try {
            // anti aliasing for smooth edges
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);
            drawBackgroundCircle(g2, totalDiameter);
            drawBorder(g2, diameter, getBorderColor(playerNumber));
            createCircularClip(g2, diameter);
            drawSprite(g2, image, diameter);
        } finally {
            // ensure disposal of g2
            g2.dispose();
        }

        return circularImage;
    }

    private static void drawBackgroundCircle(Graphics2D g2, int diameter) {
        // black background circle for the external border
        g2.setColor(Color.BLACK);
        g2.fill(new Ellipse2D.Float(0, 0, diameter, diameter));
    }

    private static Color getBorderColor(int playerNumber) {
        return (playerNumber == 0) ? Color.BLUE.brighter() : Color.RED.brighter();
    }

    private static void drawBorder(Graphics2D g2, int diameter, Color color) {
        // border that is based on player (p0: blue, p1: red)
        g2.setColor(color);
        g2.setStroke(new BasicStroke(BORDER_THICKNESS));
        g2.fill(new Ellipse2D.Float(BORDER_THICKNESS / 2.0f,
                                    BORDER_THICKNESS / 2.0f,
                                    diameter + BORDER_THICKNESS,
                                    diameter + BORDER_THICKNESS));
    }

    private static void createCircularClip(Graphics2D g2, int diameter) {
        g2.setClip(new Ellipse2D.Float(BORDER_THICKNESS,
                                       BORDER_THICKNESS,
                                       diameter,
                                       diameter));
    }

    private static void drawSprite(Graphics2D g2, Image image, int diameter) {
        // sprite inside the piece
        g2.drawImage(image,
                     BORDER_THICKNESS,
                     BORDER_THICKNESS,
                     diameter,
                     diameter,
                     null);
    }
}
