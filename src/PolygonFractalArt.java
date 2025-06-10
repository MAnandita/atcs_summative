import java.awt.*;

public class PolygonFractalArt extends ArtWork {
    private static final long serialVersionUID = 1L;

    private int sides; // number of sides of polygon
    private int depth; // recursion depth
    private double scaleFactor; // scaling factor per recursion

    public PolygonFractalArt(String title, String artist, int sides, int depth, double scaleFactor) {
        super(title, artist);
        this.sides = sides;
        this.depth = depth;
        this.scaleFactor = scaleFactor;
        this.description = "Recursive Polygon Fractal with " + sides + " sides, depth " + depth;
    }

    @Override
    public void display() {
        GraphicsDisplay.showArtwork(this);
    }

    @Override
    public int calculateComplexity() {
        if (sides <= 1)
            return 0;
        return (int) ((Math.pow(sides, depth + 1) - 1) / (sides - 1));
    }

    @Override
    public String getArtType() {
        return "Polygon Fractal";
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void drawGraphics(Graphics2D g2d) {
        g2d.fillRect(0, 0, 800, 600);

        g2d.setStroke(new BasicStroke(1));

        // Start with a big polygon in the center
        drawPolygonFractal(g2d, 400, 300, 150, depth);
    }

    /*
     * Recursive method to draw polygon fractal
     */
    private void drawPolygonFractal(Graphics2D g2d, double x, double y, double radius, int currentDepth) {
        if (currentDepth == 0 || radius < 2)
            return;

        Polygon poly = createPolygon(x, y, radius, sides);
        g2d.setColor(randomColor(currentDepth));
        g2d.draw(poly);

        // For each vertex -> recurse
        double angleStep = 2 * Math.PI / sides;
        for (int i = 0; i < sides; i++) {
            double angle = i * angleStep;
            double vx = x + radius * Math.cos(angle);
            double vy = y + radius * Math.sin(angle);

            drawPolygonFractal(g2d, vx, vy, radius * scaleFactor, currentDepth - 1);
        }
    }

    /**
     * Create a regular polygon as a Polygon object
     */
    private Polygon createPolygon(double x, double y, double radius, int sides) {
        Polygon poly = new Polygon();
        double angleStep = 2 * Math.PI / sides;
        double startAngle = -Math.PI / 2; // Start from the top

        for (int i = 0; i < sides; i++) {
            double angle = startAngle + i * angleStep;
            int px = (int) (x + radius * Math.cos(angle));
            int py = (int) (y + radius * Math.sin(angle));
            poly.addPoint(px, py);
        }

        return poly;
    }

    /**
     * Generate a color based on depth
     */
    private Color randomColor(int depthLevel) {
        float hue = (float) depthLevel / depth;
        return Color.getHSBColor(hue, 0.6f, 1.0f);
    }
}