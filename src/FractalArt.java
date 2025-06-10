import java.awt.*;

/**
 * FractalArt class that extends ArtWork to create recursive fractal patterns.
 * Demonstrates method overloading, recursion, and inheritance.
 */
public class FractalArt extends ArtWork {
    private static final long serialVersionUID = 1L;
    private int iterations;
    private double scaleFactor;
    private String fractalType; // "tree", "sierpinski", "koch", etc.

    /**
     * Constructor with title and artist only
     * 
     * @param title  The title of the fractal artwork
     * @param artist The artist who created it
     */
    public FractalArt(String title, String artist) {
        super(title, artist);
        this.iterations = 5; // Default iterations
        this.scaleFactor = 0.7; // Default scale factor
        this.fractalType = "tree"; // Default type
        this.complexityLevel = calculateComplexity();
    }

    /**
     * Constructor with iterations (method overloading)
     * 
     * @param title      The title of the fractal artwork
     * @param artist     The artist who created it
     * @param iterations Number of recursive iterations
     */
    public FractalArt(String title, String artist, int iterations) {
        super(title, artist);
        this.iterations = iterations;
        this.scaleFactor = 0.7;
        this.fractalType = "tree";
        this.complexityLevel = calculateComplexity();
    }

    /**
     * Constructor with all parameters (method overloading)
     * 
     * @param title      The title of the fractal artwork
     * @param artist     The artist who created it
     * @param iterations Number of recursive iterations
     * @param type       Type of fractal ("tree", "sierpinski", "koch")
     */
    public FractalArt(String title, String artist, int iterations, String type) {
        super(title, artist);
        this.iterations = iterations;
        this.scaleFactor = 0.7;
        this.fractalType = type.toLowerCase();
        this.complexityLevel = calculateComplexity();
    }

    @Override
    public void display() {
        System.out.println("\n=== Displaying Fractal Art: " + title + " ===");
        System.out.println("Type: " + fractalType);
        System.out.println("Iterations: " + iterations);
        System.out.println("Generating fractal pattern...\n");

        // Clear previous description
        description = "";

        // Generate based on fractal type
        switch (fractalType) {
            case "tree":
                generateFractalTree(400, 600, 100, -Math.PI / 2, iterations);
                break;
            case "sierpinski":
                generateSierpinski(200, 100, 600, 100, 400, 600, iterations);
                break;
            case "koch":
                generateKochSnowflake(400, 300, 200, iterations);
                break;
            default:
                generateFractalTree(400, 600, 100, -Math.PI / 2, iterations);
        }

        System.out.println("\nFractal generation complete!");

        // Show graphical display
        GraphicsDisplay.showArtwork(this);
    }

    @Override
    public int calculateComplexity() {
        return calculateFractalComplexity(iterations);
    }

    @Override
    public String getArtType() {
        return "Fractal Art";
    }

    @Override
    public String getDescription() {
        if (description.isEmpty()) {
            return "A " + fractalType + " fractal with " + iterations + " iterations";
        }
        return description;
    }

    @Override
    public void drawGraphics(Graphics2D g2d) {
        // Set color and stroke
        g2d.setStroke(new BasicStroke(2.0f));

        // Draw based on fractal type
        switch (fractalType) {
            case "tree":
                g2d.setColor(new Color(101, 67, 33)); // Brown for tree
                drawFractalTree(g2d, 400, 550, 100, -Math.PI / 2, iterations);
                break;
            case "sierpinski":
                g2d.setColor(Color.BLUE);
                drawSierpinski(g2d, 200, 500, 600, 500, 400, 100, iterations);
                break;
            case "koch":
                g2d.setColor(new Color(100, 149, 237)); // Cornflower blue
                drawKochSnowflake(g2d, 400, 300, 200, iterations);
                break;
            default:
                g2d.setColor(new Color(101, 67, 33));
                drawFractalTree(g2d, 400, 550, 100, -Math.PI / 2, iterations);
        }
    }

    /**
     * Private recursive helper method to calculate complexity
     * 
     * @param depth Current recursion depth
     * @return Complexity value
     */
    private int calculateFractalComplexity(int depth) {
        if (depth <= 0) {
            return 1;
        }
        // Recursive calculation: each level adds exponentially more complexity
        return depth + 2 * calculateFractalComplexity(depth - 1);
    }

    /**
     * Generate a fractal tree pattern recursively
     * 
     * @param x      Starting x coordinate
     * @param y      Starting y coordinate
     * @param length Branch length
     * @param angle  Branch angle
     * @param depth  Recursion depth
     */
    private void generateFractalTree(int x, int y, double length, double angle, int depth) {
        if (depth == 0)
            return; // Base case

        // Calculate end point of current branch
        int endX = x + (int) (length * Math.cos(angle));
        int endY = y + (int) (length * Math.sin(angle));

        // Add to description (simulating drawing)
        description += String.format("Branch[%d]: (%d,%d) to (%d,%d) - Length: %.1f\n",
                depth, x, y, endX, endY, length);

        // Recursive calls for sub-branches
        double newLength = length * scaleFactor;
        generateFractalTree(endX, endY, newLength, angle - 0.4, depth - 1); // Left branch
        generateFractalTree(endX, endY, newLength, angle + 0.4, depth - 1); // Right branch

        // Optional: Add middle branch for more complex trees
        if (depth > 3) {
            generateFractalTree(endX, endY, newLength * 0.8, angle, depth - 1);
        }
    }

    /**
     * Draw fractal tree recursively with graphics
     */
    private void drawFractalTree(Graphics2D g2d, int x, int y, double length, double angle, int depth) {
        if (depth == 0)
            return;

        int endX = x + (int) (length * Math.cos(angle));
        int endY = y + (int) (length * Math.sin(angle));

        // Set color based on depth for nice effect
        float hue = 0.1f + (depth * 0.02f); // Brown to green gradient
        g2d.setColor(Color.getHSBColor(hue, 0.8f, 0.5f));
        g2d.setStroke(new BasicStroke(Math.max(1, depth / 2)));

        // Draw branch
        g2d.drawLine(x, y, endX, endY);

        // Recursive calls for sub-branches
        double newLength = length * scaleFactor;
        drawFractalTree(g2d, endX, endY, newLength, angle - 0.4, depth - 1);
        drawFractalTree(g2d, endX, endY, newLength, angle + 0.4, depth - 1);

        // Optional middle branch for more complex trees
        if (depth > 3) {
            drawFractalTree(g2d, endX, endY, newLength * 0.8, angle, depth - 1);
        }
    }

    /**
     * Generate Sierpinski triangle recursively
     * 
     * @param x1,   y1, x2, y2, x3, y3 Triangle vertices
     * @param depth Recursion depth
     */
    private void generateSierpinski(int x1, int y1, int x2, int y2, int x3, int y3, int depth) {
        if (depth == 0) {
            // Base case: draw triangle
            description += String.format("Triangle: (%d,%d)-(%d,%d)-(%d,%d)\n",
                    x1, y1, x2, y2, x3, y3);
            return;
        }

        // Calculate midpoints
        int midX1 = (x1 + x2) / 2;
        int midY1 = (y1 + y2) / 2;
        int midX2 = (x2 + x3) / 2;
        int midY2 = (y2 + y3) / 2;
        int midX3 = (x3 + x1) / 2;
        int midY3 = (y3 + y1) / 2;

        // Recursive calls for three sub-triangles
        generateSierpinski(x1, y1, midX1, midY1, midX3, midY3, depth - 1);
        generateSierpinski(midX1, midY1, x2, y2, midX2, midY2, depth - 1);
        generateSierpinski(midX3, midY3, midX2, midY2, x3, y3, depth - 1);
    }

    /**
     * Draw Sierpinski triangle recursively with graphics
     */
    private void drawSierpinski(Graphics2D g2d, int x1, int y1, int x2, int y2, int x3, int y3, int depth) {
        if (depth == 0) {
            // Draw filled triangle
            int[] xPoints = { x1, x2, x3 };
            int[] yPoints = { y1, y2, y3 };
            g2d.fillPolygon(xPoints, yPoints, 3);
            return;
        }

        // Calculate midpoints
        int midX1 = (x1 + x2) / 2;
        int midY1 = (y1 + y2) / 2;
        int midX2 = (x2 + x3) / 2;
        int midY2 = (y2 + y3) / 2;
        int midX3 = (x3 + x1) / 2;
        int midY3 = (y3 + y1) / 2;

        // Recursive calls for three sub-triangles
        drawSierpinski(g2d, x1, y1, midX1, midY1, midX3, midY3, depth - 1);
        drawSierpinski(g2d, midX1, midY1, x2, y2, midX2, midY2, depth - 1);
        drawSierpinski(g2d, midX3, midY3, midX2, midY2, x3, y3, depth - 1);
    }

    /**
     * Generate Koch snowflake recursively
     * 
     * @param centerX Center x coordinate
     * @param centerY Center y coordinate
     * @param size    Size of the snowflake
     * @param depth   Recursion depth
     */
    private void generateKochSnowflake(int centerX, int centerY, int size, int depth) {
        // Calculate vertices of equilateral triangle
        int x1 = centerX;
        int y1 = centerY - size;
        int x2 = centerX - (int) (size * Math.cos(Math.PI / 6));
        int y2 = centerY + (int) (size * Math.sin(Math.PI / 6));
        int x3 = centerX + (int) (size * Math.cos(Math.PI / 6));
        int y3 = centerY + (int) (size * Math.sin(Math.PI / 6));

        // Generate Koch curve for each side
        generateKochCurve(x1, y1, x2, y2, depth);
        generateKochCurve(x2, y2, x3, y3, depth);
        generateKochCurve(x3, y3, x1, y1, depth);
    }

    /**
     * Draw Koch snowflake recursively with graphics
     */
    private void drawKochSnowflake(Graphics2D g2d, int centerX, int centerY, int size, int depth) {
        // Calculate vertices of equilateral triangle
        int x1 = centerX;
        int y1 = centerY - size;
        int x2 = centerX - (int) (size * Math.cos(Math.PI / 6));
        int y2 = centerY + (int) (size * Math.sin(Math.PI / 6));
        int x3 = centerX + (int) (size * Math.cos(Math.PI / 6));
        int y3 = centerY + (int) (size * Math.sin(Math.PI / 6));

        // Draw Koch curve for each side
        drawKochCurve(g2d, x1, y1, x2, y2, depth);
        drawKochCurve(g2d, x2, y2, x3, y3, depth);
        drawKochCurve(g2d, x3, y3, x1, y1, depth);
    }

    /**
     * Generate Koch curve segment recursively
     * 
     * @param x1,   y1 Starting point
     * @param x2,   y2 Ending point
     * @param depth Recursion depth
     */
    private void generateKochCurve(int x1, int y1, int x2, int y2, int depth) {
        if (depth == 0) {
            description += String.format("Line: (%d,%d) to (%d,%d)\n", x1, y1, x2, y2);
            return;
        }

        // Calculate points for Koch curve
        int deltaX = x2 - x1;
        int deltaY = y2 - y1;

        // First third point
        int xa = x1 + deltaX / 3;
        int ya = y1 + deltaY / 3;

        // Second third point
        int xb = x1 + 2 * deltaX / 3;
        int yb = y1 + 2 * deltaY / 3;

        // Peak point (equilateral triangle)
        double angle = Math.atan2(deltaY, deltaX) - Math.PI / 3;
        int xc = xa + (int) ((xb - xa) * Math.cos(angle) - (yb - ya) * Math.sin(angle));
        int yc = ya + (int) ((xb - xa) * Math.sin(angle) + (yb - ya) * Math.cos(angle));

        // Recursive calls
        generateKochCurve(x1, y1, xa, ya, depth - 1);
        generateKochCurve(xa, ya, xc, yc, depth - 1);
        generateKochCurve(xc, yc, xb, yb, depth - 1);
        generateKochCurve(xb, yb, x2, y2, depth - 1);
    }

    /**
     * Draw Koch curve segment recursively with graphics
     */
    private void drawKochCurve(Graphics2D g2d, int x1, int y1, int x2, int y2, int depth) {
        if (depth == 0) {
            g2d.drawLine(x1, y1, x2, y2);
            return;
        }

        // Calculate points for Koch curve
        int deltaX = x2 - x1;
        int deltaY = y2 - y1;

        // First third point
        int xa = x1 + deltaX / 3;
        int ya = y1 + deltaY / 3;

        // Second third point
        int xb = x1 + 2 * deltaX / 3;
        int yb = y1 + 2 * deltaY / 3;

        // Peak point (equilateral triangle)
        double angle = Math.atan2(deltaY, deltaX) - Math.PI / 3;
        int xc = xa + (int) ((xb - xa) * Math.cos(angle) - (yb - ya) * Math.sin(angle));
        int yc = ya + (int) ((xb - xa) * Math.sin(angle) + (yb - ya) * Math.cos(angle));

        // Recursive calls
        drawKochCurve(g2d, x1, y1, xa, ya, depth - 1);
        drawKochCurve(g2d, xa, ya, xc, yc, depth - 1);
        drawKochCurve(g2d, xc, yc, xb, yb, depth - 1);
        drawKochCurve(g2d, xb, yb, x2, y2, depth - 1);
    }

    // Getters
    public int getIterations() {
        return iterations;
    }

    public String getFractalType() {
        return fractalType;
    }
}
