import java.awt.*;
import java.util.*;

/**
 * Cellular Automata Artwork
 * - Supports flexible B/S rule (e.g. B36/S23 -> HighLife)
 * - Tracks age of cells -> color gradients based on cell age
 * - Fade out for dead cells
 */
public class CellularAutomataArt extends ArtWork {
    private static final long serialVersionUID = 1L;

    private boolean[][] grid;
    private int[][] ageGrid;
    private int size;
    private int generations;

    // Rule components
    private Set<Integer> birthSet;
    private Set<Integer> survivalSet;

    public CellularAutomataArt(String title, String artist, int size, int generations) {
        super(title, artist);
        this.size = size;
        this.generations = generations;
        this.description = "Advanced Cellular Automata pattern with aging (HighLife B36/S23)";

        // Initialize rule -> HighLife (B36/S23)
        birthSet = parseRule("36");
        survivalSet = parseRule("23");

        // Initialize grids
        grid = new boolean[size][size];
        ageGrid = new int[size][size];

        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = rand.nextDouble() < 0.2; // 20% alive
                ageGrid[i][j] = grid[i][j] ? 1 : 0;
            }
        }
    }

    @Override
    public void display() {
        GraphicsDisplay.showArtwork(this);
    }

    @Override
    public int calculateComplexity() {
        return generations * size * size;
    }

    @Override
    public String getArtType() {
        return "Cellular Automata";
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void drawGraphics(Graphics2D g2d) {
        int cellSize = 600 / size;

        // Run generations
        for (int gen = 0; gen < generations; gen++) {
            boolean[][] newGrid = new boolean[size][size];
            int[][] newAgeGrid = new int[size][size];

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    int neighbors = countAliveNeighbors(i, j);

                    if (grid[i][j]) {
                        // Alive → survives if in survivalSet
                        newGrid[i][j] = survivalSet.contains(neighbors);
                    } else {
                        // Dead → born if in birthSet
                        newGrid[i][j] = birthSet.contains(neighbors);
                    }

                    // Update age grid
                    if (newGrid[i][j]) {
                        newAgeGrid[i][j] = ageGrid[i][j] + 1;
                    } else {
                        newAgeGrid[i][j] = 0;
                    }
                }
            }

            grid = newGrid;
            ageGrid = newAgeGrid;
        }

        // Draw final grid
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j]) {
                    // Alive cell -> color based on age using hue gradient
                    int age = Math.min(ageGrid[i][j], 60); // allow wider gradient
                    float hue = 1.0f - (float) age / 60;
                    Color cellColor = Color.getHSBColor(hue, 1.0f, 1.0f);
                    g2d.setColor(cellColor);
                } else {
                    // Dead cell
                    g2d.setColor(new Color(25, 30, 35));
                }
                g2d.fillRect(i * cellSize, j * cellSize, cellSize, cellSize);
            }
        }

    }

    /*
     * Count number of live neighbors for cell (x, y)
     */
    private int countAliveNeighbors(int x, int y) {
        int count = 0;

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0)
                    continue; // Skip self

                int nx = x + dx;
                int ny = y + dy;

                if (nx >= 0 && nx < size && ny >= 0 && ny < size) {
                    if (grid[nx][ny])
                        count++;
                }
            }
        }

        return count;
    }

    /*
     * Parse B/S rule component (e.g. "36" -> {3,6})
     */
    private Set<Integer> parseRule(String rule) {
        Set<Integer> set = new HashSet<>();
        for (char c : rule.toCharArray()) {
            if (Character.isDigit(c)) {
                set.add(c - '0');
            }
        }
        return set;
    }
}
