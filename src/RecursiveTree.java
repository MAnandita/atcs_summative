import java.util.Random;
import java.awt.*;

/*
 * RecursiveTree class that extends ArtWork to create recursive tree patterns.
 * Demonstrates advanced recursion with customizable parameters.
 */
public class RecursiveTree extends ArtWork {
    private static final long serialVersionUID = 1L;
    private int maxDepth;
    private double branchAngle;
    private double lengthReduction;
    private boolean randomized;
    private int branchCount;
    private Random random;
    private String season = "default"; // Track current season for coloring

    /**
     * Basic constructor
     * 
     * @param title  The title of the tree artwork
     * @param artist The artist who created it
     */
    public RecursiveTree(String title, String artist) {
        super(title, artist);
        this.maxDepth = 8;
        this.branchAngle = Math.PI / 6; // 30 degrees
        this.lengthReduction = 0.75;
        this.randomized = false;
        this.branchCount = 2;
        this.random = new Random();
        this.complexityLevel = calculateComplexity();
    }

    /**
     * Constructor with depth parameter (method overloading)
     * 
     * @param title    The title of the tree artwork
     * @param artist   The artist who created it
     * @param maxDepth Maximum recursion depth
     */
    public RecursiveTree(String title, String artist, int maxDepth) {
        super(title, artist);
        this.maxDepth = maxDepth;
        this.branchAngle = Math.PI / 6;
        this.lengthReduction = 0.75;
        this.randomized = false;
        this.branchCount = 2;
        this.random = new Random();
        this.complexityLevel = calculateComplexity();
    }

    /**
     * Constructor with full customization (method overloading)
     * 
     * @param title       The title of the tree artwork
     * @param artist      The artist who created it
     * @param maxDepth    Maximum recursion depth
     * @param randomized  Whether to add randomness to the tree
     * @param branchCount Number of branches per node
     */
    public RecursiveTree(String title, String artist, int maxDepth, boolean randomized, int branchCount) {
        super(title, artist);
        this.maxDepth = maxDepth;
        this.branchAngle = Math.PI / 6;
        this.lengthReduction = 0.75;
        this.randomized = randomized;
        this.branchCount = Math.max(2, Math.min(5, branchCount)); // Limit branches 2-5
        this.random = new Random();
        this.complexityLevel = calculateComplexity();
    }

    @Override
    public void display() {
        System.out.println("\n=== Displaying Recursive Tree: " + title + " ===");
        System.out.println("Max Depth: " + maxDepth);
        System.out.println("Branch Count: " + branchCount);
        System.out.println("Randomized: " + randomized);
        System.out.println("Generating tree structure...\n");

        // Clear previous description
        description = "";

        // Start tree generation from base
        int startX = 400;
        int startY = 600;
        double initialLength = 150;
        double initialAngle = -Math.PI / 2; // Pointing up

        // Draw trunk
        description += "=== Tree Structure ===\n";
        description += "Trunk: Base at (" + startX + ", " + startY + ")\n\n";

        // Generate recursive tree
        drawBranch(startX, startY, initialLength, initialAngle, maxDepth, "Root");

        System.out.println("\nTree generation complete!");
        System.out.println("Total branches created: " + countBranches(maxDepth));

        // Show graphical display
        GraphicsDisplay.showArtwork(this);
    }

    @Override
    public int calculateComplexity() {
        // Complexity based on total number of branches
        return calculateTreeComplexity(maxDepth);
    }

    @Override
    public String getArtType() {
        return "Recursive Tree";
    }

    @Override
    public String getDescription() {
        if (description.isEmpty()) {
            return "A recursive tree with depth " + maxDepth + " and " + branchCount + " branches per node";
        }
        return description;
    }

    /**
     * Recursive method to draw a branch and its sub-branches
     * 
     * @param x      Starting x coordinate
     * @param y      Starting y coordinate
     * @param length Branch length
     * @param angle  Branch angle
     * @param depth  Current recursion depth
     * @param path   Path string for tracking position in tree
     */
    private void drawBranch(int x, int y, double length, double angle, int depth, String path) {
        if (depth == 0) {
            description += String.format("  Leaf at %s: (%d, %d)\n", path, x, y);
            return; // Base case
        }

        // Calculate end point of current branch
        int endX = x + (int) (length * Math.cos(angle));
        int endY = y + (int) (length * Math.sin(angle));

        // Add branch to description
        String depthIndent = "  ".repeat(maxDepth - depth);
        description += String.format("%sBranch %s [Depth %d]: (%d,%d) to (%d,%d) - Length: %.1f\n",
                depthIndent, path, depth, x, y, endX, endY, length);

        // Calculate parameters for sub-branches
        double newLength = length * lengthReduction;
        if (randomized) {
            newLength *= (0.8 + random.nextDouble() * 0.4); // Random factor 0.8-1.2
        }

        // Generate sub-branches based on branchCount
        if (branchCount == 2) {
            // Classic binary tree
            double leftAngle = angle - branchAngle;
            double rightAngle = angle + branchAngle;

            if (randomized) {
                leftAngle += (random.nextDouble() - 0.5) * 0.3;
                rightAngle += (random.nextDouble() - 0.5) * 0.3;
            }

            drawBranch(endX, endY, newLength, leftAngle, depth - 1, path + "-L");
            drawBranch(endX, endY, newLength, rightAngle, depth - 1, path + "-R");
        } else {
            // Multi-branch tree
            double angleStep = (2 * branchAngle) / (branchCount - 1);
            double startAngle = angle - branchAngle;

            for (int i = 0; i < branchCount; i++) {
                double branchAngle = startAngle + (i * angleStep);
                if (randomized) {
                    branchAngle += (random.nextDouble() - 0.5) * 0.3;
                }
                drawBranch(endX, endY, newLength, branchAngle, depth - 1, path + "-" + i);
            }
        }

        // Occasionally add an extra branch for variety
        if (randomized && random.nextDouble() > 0.7 && depth > 2) {
            double extraAngle = angle + (random.nextDouble() - 0.5) * Math.PI / 4;
            drawBranch(endX, endY, newLength * 0.6, extraAngle, depth - 2, path + "-X");
        }
    }

    /**
     * Calculate the complexity of the tree based on branch count
     * 
     * @param depth Current depth
     * @return Total complexity value
     */
    private int calculateTreeComplexity(int depth) {
        if (depth <= 0) {
            return 1;
        }
        // Each level multiplies by branch count
        return 1 + branchCount * calculateTreeComplexity(depth - 1);
    }

    /**
     * Count total branches in the tree recursively
     * 
     * @param depth Current depth
     * @return Total number of branches
     */
    private int countBranches(int depth) {
        if (depth <= 0) {
            return 0;
        }
        // Recursive calculation: current level + all sub-branches
        return (int) Math.pow(branchCount, maxDepth - depth + 1) - 1;
    }

    /**
     * Generate a nature-inspired tree with seasonal variations
     * 
     * @param season The season ("spring", "summer", "fall", "winter")
     */
    public void generateSeasonalTree(String season) {
        System.out.println("\nGenerating " + season + " tree...");

        this.season = season.toLowerCase(); // Store the season

        switch (this.season) {
            case "spring":
                this.lengthReduction = 0.8; // Vigorous growth
                this.branchCount = 3;
                description += "\n[Spring Tree: New growth, many small branches]\n";
                break;
            case "summer":
                this.lengthReduction = 0.75;
                this.branchCount = 2;
                description += "\n[Summer Tree: Full foliage, balanced structure]\n";
                break;
            case "fall":
                this.lengthReduction = 0.7;
                this.branchCount = 2;
                this.randomized = true;
                description += "\n[Fall Tree: Some branches bare, irregular pattern]\n";
                break;
            case "winter":
                this.lengthReduction = 0.65;
                this.branchCount = 2;
                this.maxDepth = Math.max(5, maxDepth - 2); // Fewer small branches
                description += "\n[Winter Tree: Bare branches, stark structure]\n";
                break;
        }

        display();
    }

    // Getters and setters
    public int getMaxDepth() {
        return maxDepth;
    }

    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
        this.complexityLevel = calculateComplexity();
    }

    public boolean isRandomized() {
        return randomized;
    }

    public void setRandomized(boolean randomized) {
        this.randomized = randomized;
    }

    @Override
    public void drawGraphics(Graphics2D g2d) {
        // Set rendering hints for smooth lines
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Start tree from base
        int startX = 400;
        int startY = 550;
        double initialLength = 120;
        double initialAngle = -Math.PI / 2;

        // Set base color
        Color baseColor = new Color(101, 67, 33); // Brown

        // Draw the tree recursively
        drawGraphicalBranch(g2d, startX, startY, initialLength, initialAngle, maxDepth, "Root", baseColor);
    }

    /**
     * Draw branch with graphics recursively
     */
    private void drawGraphicalBranch(Graphics2D g2d, int x, int y, double length, double angle,
            int depth, String path, Color baseColor) {
        if (depth == 0) {
            // Draw leaves/flowers at the end based on season
            if (season.equals("spring")) {
                g2d.setColor(new Color(255, 182, 193)); // Pink flowers
                g2d.fillOval(x - 4, y - 4, 8, 8);
            } else if (season.equals("summer")) {
                g2d.setColor(new Color(34, 139, 34)); // Forest green leaves
                g2d.fillOval(x - 3, y - 3, 6, 6);
            } else if (season.equals("fall")) {
                // Fall leaves in various colors
                Color[] fallColors = { new Color(255, 69, 0), new Color(255, 140, 0), new Color(255, 215, 0) };
                g2d.setColor(fallColors[(int) (Math.random() * fallColors.length)]);
                g2d.fillOval(x - 3, y - 3, 6, 6);
            } else if (season.equals("winter")) {
                // No leaves in winter, just show branch tips
                g2d.setColor(new Color(200, 200, 200));
                g2d.fillOval(x - 2, y - 2, 4, 4);
            } else {
                g2d.setColor(new Color(34, 139, 34)); // Default green
                g2d.fillOval(x - 3, y - 3, 6, 6);
            }
            return;
        }

        // Calculate end point
        int endX = x + (int) (length * Math.cos(angle));
        int endY = y + (int) (length * Math.sin(angle));

        // Set color and stroke based on depth and season
        float colorFactor = (float) depth / maxDepth;
        if (season.equals("spring")) {
            // Spring colors - light greens and pinks
            if (depth <= 2) {
                // Flowers/buds at tips
                g2d.setColor(new Color(255, 182, 193)); // Light pink
            } else {
                g2d.setColor(new Color((int) (100 * colorFactor), (int) (200 * colorFactor + 55),
                        (int) (100 * colorFactor)));
            }
        } else if (season.equals("summer")) {
            // Summer - deep greens
            g2d.setColor(new Color((int) (34 * colorFactor), (int) (139 * colorFactor), (int) (34 * colorFactor)));
        } else if (season.equals("fall")) {
            // Fall colors - oranges and reds
            if (depth <= 3) {
                // More colorful at the tips
                g2d.setColor(new Color(255, (int) (140 - depth * 20), 0)); // Orange to red
            } else {
                g2d.setColor(new Color((int) (139 * colorFactor), (int) (69 * colorFactor), (int) (19 * colorFactor))); // Saddle
                                                                                                                        // brown
            }
        } else if (season.equals("winter")) {
            // Winter - grays and whites
            int gray = (int) (150 * colorFactor + 50);
            g2d.setColor(new Color(gray, gray, gray));
        } else {
            // Default brown to green gradient
            g2d.setColor(new Color((int) (101 * colorFactor), (int) (67 * colorFactor + 100 * (1 - colorFactor)),
                    (int) (33 * colorFactor)));
        }

        g2d.setStroke(new BasicStroke(Math.max(1, depth / 2.0f)));

        // Draw branch
        g2d.drawLine(x, y, endX, endY);

        // Calculate parameters for sub-branches
        double newLength = length * lengthReduction;
        if (randomized) {
            newLength *= (0.8 + random.nextDouble() * 0.4);
        }

        // Generate sub-branches
        if (branchCount == 2) {
            // Binary tree
            double leftAngle = angle - branchAngle;
            double rightAngle = angle + branchAngle;

            if (randomized) {
                leftAngle += (random.nextDouble() - 0.5) * 0.3;
                rightAngle += (random.nextDouble() - 0.5) * 0.3;
            }

            drawGraphicalBranch(g2d, endX, endY, newLength, leftAngle, depth - 1, path + "-L", baseColor);
            drawGraphicalBranch(g2d, endX, endY, newLength, rightAngle, depth - 1, path + "-R", baseColor);
        } else {
            // Multi-branch tree
            double angleStep = (2 * branchAngle) / (branchCount - 1);
            double startAngle = angle - branchAngle;

            for (int i = 0; i < branchCount; i++) {
                double branchAngle = startAngle + (i * angleStep);
                if (randomized) {
                    branchAngle += (random.nextDouble() - 0.5) * 0.3;
                }
                drawGraphicalBranch(g2d, endX, endY, newLength, branchAngle, depth - 1, path + "-" + i, baseColor);
            }
        }

        // Occasionally add extra branch
        if (randomized && random.nextDouble() > 0.7 && depth > 2) {
            double extraAngle = angle + (random.nextDouble() - 0.5) * Math.PI / 4;
            drawGraphicalBranch(g2d, endX, endY, newLength * 0.6, extraAngle, depth - 2, path + "-X", baseColor);
        }
    }
}
