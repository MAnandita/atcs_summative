import java.awt.*;

/**
 * SpiralArt class that extends ArtWork to create recursive spiral patterns.
 * Demonstrates mathematical recursion with various spiral types.
 */
public class SpiralArt extends ArtWork {
    private static final long serialVersionUID = 1L;
    private double initialRadius;
    private double radiusDecay;
    private double angleIncrement;
    private int segments;
    private String spiralType; // "archimedean", "logarithmic", "golden"
    private boolean bidirectional;

    /**
     * Basic constructor
     * 
     * @param title  The title of the spiral artwork
     * @param artist The artist who created it
     */
    public SpiralArt(String title, String artist) {
        super(title, artist);
        this.initialRadius = 200;
        this.radiusDecay = 0.95;
        this.angleIncrement = Math.PI / 8; // 22.5 degrees
        this.segments = 100;
        this.spiralType = "archimedean";
        this.bidirectional = false;
        this.complexityLevel = calculateComplexity();
    }

    /**
     * Constructor with segments parameter (method overloading)
     * 
     * @param title    The title of the spiral artwork
     * @param artist   The artist who created it
     * @param segments Number of segments in the spiral
     */
    public SpiralArt(String title, String artist, int segments) {
        super(title, artist);
        this.initialRadius = 200;
        this.radiusDecay = 0.95;
        this.angleIncrement = Math.PI / 8;
        this.segments = segments;
        this.spiralType = "archimedean";
        this.bidirectional = false;
        this.complexityLevel = calculateComplexity();
    }

    /**
     * Constructor with full customization (method overloading)
     * 
     * @param title         The title of the spiral artwork
     * @param artist        The artist who created it
     * @param segments      Number of segments
     * @param spiralType    Type of spiral
     * @param bidirectional Whether to create spiral in both directions
     */
    public SpiralArt(String title, String artist, int segments, String spiralType, boolean bidirectional) {
        super(title, artist);
        this.initialRadius = 200;
        this.radiusDecay = 0.95;
        this.angleIncrement = Math.PI / 8;
        this.segments = segments;
        this.spiralType = spiralType.toLowerCase();
        this.bidirectional = bidirectional;

        // Adjust parameters based on spiral type
        switch (this.spiralType) {
            case "logarithmic":
                this.radiusDecay = 0.98;
                this.angleIncrement = Math.PI / 16;
                break;
            case "golden":
                this.radiusDecay = 0.618; // Golden ratio
                this.angleIncrement = Math.PI / 10;
                break;
        }

        this.complexityLevel = calculateComplexity();
    }

    @Override
    public void display() {
        System.out.println("\n=== Displaying Spiral Art: " + title + " ===");
        System.out.println("Type: " + spiralType);
        System.out.println("Segments: " + segments);
        System.out.println("Bidirectional: " + bidirectional);
        System.out.println("Generating spiral pattern...\n");

        // Clear previous description
        description = "";

        // Start spiral generation from center
        int centerX = 400;
        int centerY = 300;

        description += "=== Spiral Pattern ===\n";
        description += "Center: (" + centerX + ", " + centerY + ")\n";
        description += "Type: " + spiralType + " spiral\n\n";

        // Generate the spiral
        if (bidirectional) {
            // Generate outward spiral
            generateSpiral(centerX, centerY, 5, 0, segments / 2, 1, "Outward");
            // Generate inward spiral
            generateSpiral(centerX, centerY, initialRadius, Math.PI, segments / 2, -1, "Inward");
        } else {
            // Generate single spiral
            generateSpiral(centerX, centerY, 5, 0, segments, 1, "Main");
        }

        System.out.println("\nSpiral generation complete!");
        System.out.println("Total segments drawn: " + segments);

        // Show graphical display
        GraphicsDisplay.showArtwork(this);
    }

    @Override
    public int calculateComplexity() {
        // Use recursive method to calculate based on spiral segments
        return calculateSpiralComplexity(segments);
    }

    @Override
    public String getArtType() {
        return "Spiral Art";
    }

    @Override
    public String getDescription() {
        if (description.isEmpty()) {
            return "A " + spiralType + " spiral with " + segments + " segments";
        }
        return description;
    }

    /**
     * Recursive method to generate spiral
     * 
     * @param centerX           Center x coordinate
     * @param centerY           Center y coordinate
     * @param radius            Current radius
     * @param angle             Current angle
     * @param remainingSegments Segments left to draw
     * @param direction         1 for outward, -1 for inward
     * @param spiralName        Name of the spiral (for description)
     */
    private void generateSpiral(int centerX, int centerY, double radius, double angle,
            int remainingSegments, int direction, String spiralName) {
        // Base case
        if (remainingSegments <= 0 || radius <= 1 || radius > 300) {
            description += String.format("  %s spiral ended at radius %.1f\n", spiralName, radius);
            return;
        }

        // Calculate current point
        int x = centerX + (int) (radius * Math.cos(angle));
        int y = centerY + (int) (radius * Math.sin(angle));

        // Calculate next point for line segment
        double nextRadius = calculateNextRadius(radius, angle, direction);
        double nextAngle = angle + angleIncrement;
        int nextX = centerX + (int) (nextRadius * Math.cos(nextAngle));
        int nextY = centerY + (int) (nextRadius * Math.sin(nextAngle));

        // Add segment to description (only log every 10th segment to avoid clutter)
        if (remainingSegments % 10 == 0) {
            description += String.format("  Segment %d: (%d,%d) to (%d,%d) - Radius: %.1f\n",
                    segments - remainingSegments + 1, x, y, nextX, nextY, radius);
        }

        // Recursive call
        generateSpiral(centerX, centerY, nextRadius, nextAngle,
                remainingSegments - 1, direction, spiralName);

        // Add decorative elements at certain intervals
        if (remainingSegments % 20 == 0) {
            generateDecorativeElement(nextX, nextY, nextRadius / 10, remainingSegments / 20);
        }
    }

    /**
     * Calculate next radius based on spiral type
     * 
     * @param currentRadius Current radius
     * @param angle         Current angle
     * @param direction     Direction of spiral
     * @return Next radius value
     */
    private double calculateNextRadius(double currentRadius, double angle, int direction) {
        switch (spiralType) {
            case "archimedean":
                // Linear growth: r = a + b*theta
                return currentRadius + (direction * 2);

            case "logarithmic":
                // Exponential growth: r = a * e^(b*theta)
                return currentRadius * (direction > 0 ? 1.02 : 0.98);

            case "golden":
                // Golden ratio growth
                return currentRadius * (direction > 0 ? 1.05 : 0.95);

            default:
                return currentRadius + (direction * 2);
        }
    }

    /**
     * Recursive method to calculate spiral complexity
     * 
     * @param segments Number of segments
     * @return Complexity value
     */
    private int calculateSpiralComplexity(int segments) {
        if (segments <= 1) {
            return 1;
        }
        // Complexity increases with segments, but not linearly
        return 1 + calculateSpiralComplexity(segments / 2) + calculateSpiralComplexity(segments / 2);
    }

    /**
     * Generate decorative elements along the spiral recursively
     * 
     * @param x     Center x of decoration
     * @param y     Center y of decoration
     * @param size  Size of decoration
     * @param depth Recursion depth for decoration
     */
    private void generateDecorativeElement(int x, int y, double size, int depth) {
        if (depth <= 0 || size < 2) {
            return; // Base case
        }

        // Add small spiral or star pattern
        description += String.format("    * Decoration at (%d,%d) - Size: %.1f\n", x, y, size);

        // Recursive decorations in cardinal directions
        int offset = (int) (size * 2);
        generateDecorativeElement(x + offset, y, size * 0.5, depth - 1);
        generateDecorativeElement(x - offset, y, size * 0.5, depth - 1);
        generateDecorativeElement(x, y + offset, size * 0.5, depth - 1);
        generateDecorativeElement(x, y - offset, size * 0.5, depth - 1);
    }

    /**
     * Generate a Fibonacci spiral (special case of golden spiral)
     */
    public void generateFibonacciSpiral() {
        System.out.println("\nGenerating Fibonacci Spiral...");
        this.spiralType = "golden";
        this.segments = 144; // Fibonacci number
        this.angleIncrement = Math.PI / 12;

        description += "\n[Fibonacci Spiral: Based on Fibonacci sequence]\n";
        description += "Using golden ratio for growth\n";

        // Generate Fibonacci rectangles
        int a = 1, b = 1;
        description += "Fibonacci sequence: ";
        for (int i = 0; i < 10; i++) {
            description += a + " ";
            int temp = a + b;
            a = b;
            b = temp;
        }
        description += "...\n\n";

        display();
    }

    /**
     * Create a galaxy-inspired spiral pattern
     * 
     * @param arms Number of spiral arms
     */
    public void generateGalaxySpiral(int arms) {
        System.out.println("\nGenerating Galaxy Spiral with " + arms + " arms...");

        int centerX = 400;
        int centerY = 300;

        description = "=== Galaxy Spiral Pattern ===\n";
        description += "Arms: " + arms + "\n\n";

        // Generate multiple spiral arms
        for (int i = 0; i < arms; i++) {
            double startAngle = (2 * Math.PI * i) / arms;
            description += "Arm " + (i + 1) + " starting at angle " +
                    String.format("%.1f", Math.toDegrees(startAngle)) + " degrees\n";

            generateSpiral(centerX, centerY, 10, startAngle, segments / arms, 1, "Arm" + (i + 1));
        }
    }

    // Getters and setters
    public int getSegments() {
        return segments;
    }

    public String getSpiralType() {
        return spiralType;
    }

    public boolean isBidirectional() {
        return bidirectional;
    }

    @Override
    public void drawGraphics(Graphics2D g2d) {
        // Set rendering hints for smooth curves
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(new BasicStroke(2.0f));

        // Center of spiral
        int centerX = 400;
        int centerY = 300;

        // Set color based on spiral type
        switch (spiralType) {
            case "golden":
                g2d.setColor(new Color(255, 215, 0)); // Gold
                break;
            case "logarithmic":
                g2d.setColor(new Color(138, 43, 226)); // Blue violet
                break;
            default:
                g2d.setColor(new Color(0, 100, 0)); // Dark green
        }

        // Draw the spiral
        if (bidirectional) {
            // Draw outward spiral
            drawSpiral(g2d, centerX, centerY, 5, 0, segments / 2, 1);
            // Draw inward spiral in different color
            g2d.setColor(new Color(220, 20, 60)); // Crimson
            drawSpiral(g2d, centerX, centerY, initialRadius, Math.PI, segments / 2, -1);
        } else {
            // Draw single spiral
            drawSpiral(g2d, centerX, centerY, 5, 0, segments, 1);
        }

        // Draw center point
        g2d.setColor(Color.BLACK);
        g2d.fillOval(centerX - 3, centerY - 3, 6, 6);
    }

    /**
     * Draw spiral recursively with graphics
     */
    private void drawSpiral(Graphics2D g2d, int centerX, int centerY, double radius,
            double angle, int remainingSegments, int direction) {
        if (remainingSegments <= 0 || radius <= 1 || radius > 300) {
            return;
        }

        // Calculate current and next points
        int x1 = centerX + (int) (radius * Math.cos(angle));
        int y1 = centerY + (int) (radius * Math.sin(angle));

        double nextRadius = calculateNextRadius(radius, angle, direction);
        double nextAngle = angle + angleIncrement;
        int x2 = centerX + (int) (nextRadius * Math.cos(nextAngle));
        int y2 = centerY + (int) (nextRadius * Math.sin(nextAngle));

        // Draw line segment
        g2d.drawLine(x1, y1, x2, y2);

        // Add decorative elements at intervals
        if (remainingSegments % 20 == 0 && remainingSegments > 20) {
            drawDecorativeElement(g2d, x2, y2, nextRadius / 15);
        }

        // Recursive call
        drawSpiral(g2d, centerX, centerY, nextRadius, nextAngle,
                remainingSegments - 1, direction);
    }

    /**
     * Draw decorative elements along the spiral
     */
    private void drawDecorativeElement(Graphics2D g2d, int x, int y, double size) {
        // Save current color
        Color currentColor = g2d.getColor();

        // Draw small star pattern
        g2d.setColor(currentColor.brighter());
        int intSize = (int) size;

        // Draw star
        g2d.drawLine(x - intSize, y, x + intSize, y);
        g2d.drawLine(x, y - intSize, x, y + intSize);
        g2d.drawLine(x - intSize / 2, y - intSize / 2, x + intSize / 2, y + intSize / 2);
        g2d.drawLine(x - intSize / 2, y + intSize / 2, x + intSize / 2, y - intSize / 2);

        // Restore color
        g2d.setColor(currentColor);
    }
}
