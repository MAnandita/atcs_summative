import java.awt.*;
import java.util.Map;
import java.util.Stack;

/**
 * L-System Artwork
 * Generates a fractal branching tree or geometric pattern using an L-System
 * grammar and turtle graphics
 * Supports variable angle and any user-provided axiom and rules
 */
public class LSystemArt extends ArtWork {
    private static final long serialVersionUID = 1L;

    private String axiom;
    private Map<Character, String> rules;
    private int depth;
    private double angleDegrees;
    private String result;

    /**
     * Constructor for LSystemArt
     * 
     * @param title        The title of the artwork
     * @param artist       The artist's name
     * @param axiom        The initial axiom string
     * @param rules        The map of rewrite rules
     * @param depth        The number of iterations (recursion depth)
     * @param angleDegrees The angle used for turning
     */
    public LSystemArt(String title, String artist, String axiom, Map<Character, String> rules, int depth,
            double angleDegrees) {
        super(title, artist);
        this.axiom = axiom;
        this.rules = rules;
        this.depth = depth;
        this.angleDegrees = angleDegrees;
        this.description = "L-System at depth " + depth + " with angle " + angleDegrees + "°";
        this.result = generateLSystem();
    }

    /**
     * Generate the expanded L-System string by applying the rules
     * 
     * @return The final expanded string after all iterations
     */
    private String generateLSystem() {
        String current = axiom;
        for (int i = 0; i < depth; i++) {
            StringBuilder next = new StringBuilder();
            for (char c : current.toCharArray()) {
                next.append(rules.getOrDefault(c, String.valueOf(c)));
            }
            current = next.toString();
        }
        return current;
    }

    @Override
    public void display() {
        GraphicsDisplay.showArtwork(this);
    }

    @Override
    public int calculateComplexity() {
        int numF = 0;
        for (char c : result.toCharArray()) {
            if (c == 'F') {
                numF++;
            }
        }
        return depth * numF;
    }

    @Override
    public String getArtType() {
        return "L-System";
    }

    @Override
    public String getDescription() {
        return description;
    }

    /*
     * Draw the L-System using turtle graphics with stack-based branching
     */
    @Override
    public void drawGraphics(Graphics2D g2d) {
        // First pass -> compute bounding box
        double angle = -90;
        double initialStep = 6.0;
        double step = initialStep;

        Stack<Double> angleStack = new Stack<>();
        Stack<Point.Double> posStack = new Stack<>();
        Stack<Double> stepStack = new Stack<>();

        double x = 0;
        double y = 0;

        double minX = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;

        // First pass: simulate movement and compute bounding box
        for (char c : result.toCharArray()) {
            switch (c) {
                case 'F': {
                    double newX = x + step * Math.cos(Math.toRadians(angle));
                    double newY = y + step * Math.sin(Math.toRadians(angle));

                    // Update bounding box
                    minX = Math.min(minX, Math.min(x, newX));
                    maxX = Math.max(maxX, Math.max(x, newX));
                    minY = Math.min(minY, Math.min(y, newY));
                    maxY = Math.max(maxY, Math.max(y, newY));

                    x = newX;
                    y = newY;
                    break;
                }
                case '+':
                    angle += angleDegrees;
                    break;
                case '-':
                    angle -= angleDegrees;
                    break;
                case '[':
                    posStack.push(new Point.Double(x, y));
                    angleStack.push(angle);
                    stepStack.push(step);

                    if (angleDegrees <= 40) {
                        step *= 0.75;
                    }
                    break;
                case ']':
                    Point.Double pos = posStack.pop();
                    x = pos.x;
                    y = pos.y;
                    angle = angleStack.pop();
                    step = stepStack.pop();
                    break;
                default:
                    break;
            }
        }

        // Compute offset to center
        double boundingWidth = maxX - minX;
        double boundingHeight = maxY - minY;

        double offsetX = 400 - (boundingWidth / 2 + minX);
        double offsetY = 300 - (boundingHeight / 2 + minY);

        // Second pass -> actual drawing with offset
        x = offsetX;
        y = offsetY;
        angle = -90;
        step = initialStep;

        angleStack.clear();
        posStack.clear();
        stepStack.clear();

        g2d.fillRect(0, 0, 800, 600);

        g2d.setColor(new Color(0, 124, 119));
        if (angleDegrees <= 40) {
            g2d.setColor(new Color(34, 139, 34)); // forest green for plants
        }

        g2d.setStroke(new BasicStroke(1));

        // Second pass: draw
        for (char c : result.toCharArray()) {
            switch (c) {
                case 'F': {
                    double newX = x + step * Math.cos(Math.toRadians(angle));
                    double newY = y + step * Math.sin(Math.toRadians(angle));
                    g2d.drawLine((int) x, (int) y, (int) newX, (int) newY);
                    x = newX;
                    y = newY;
                    break;
                }
                case '+':
                    angle += angleDegrees;
                    break;
                case '-':
                    angle -= angleDegrees;
                    break;
                case '[':
                    posStack.push(new Point.Double(x, y));
                    angleStack.push(angle);
                    stepStack.push(step);

                    if (angleDegrees <= 40) {
                        step *= 0.75;
                    }
                    break;
                case ']':
                    Point.Double pos = posStack.pop();
                    x = pos.x;
                    y = pos.y;
                    angle = angleStack.pop();
                    step = stepStack.pop();
                    break;
                default:
                    break;
            }
        }
    }

}
