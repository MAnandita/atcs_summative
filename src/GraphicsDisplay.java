import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * GraphicsDisplay class to handle visual rendering of artwork.
 * Creates a window to display the recursive patterns.
 */
public class GraphicsDisplay extends JPanel {
    private ArtWork artwork;
    private BufferedImage image;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    /**
     * Constructor for GraphicsDisplay
     * 
     * @param artwork The artwork to display
     */
    public GraphicsDisplay(ArtWork artwork) {
        this.artwork = artwork;
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.WHITE);

        // Create buffered image for drawing
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, WIDTH, HEIGHT);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the artwork
        artwork.drawGraphics(g2d);
        g2d.dispose();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, null);
        }
    }

    /**
     * Display the artwork in a new window
     * 
     * @param artwork The artwork to display
     */
    public static void showArtwork(ArtWork artwork) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame(artwork.getArtType() + ": " + artwork.getTitle());
            GraphicsDisplay display = new GraphicsDisplay(artwork);

            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.add(display);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
