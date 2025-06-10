import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
 * Main class for the Art Generator program.
 * Provides user interface and demonstrates all features of the artwork system.
 */
public class ArtGenerator {
    private static Scanner scanner = new Scanner(System.in);
    private static ArtPortfolio currentPortfolio = null;
    private static ArrayList<ArtWork> tempArtworks = new ArrayList<>();
    // Global to store last used depth
    final double BASE_STEP_FACTOR = 600.0;

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║       WELCOME TO THE RECURSIVE ART GENERATOR             ║");
        System.out.println("║              AP CS Unit 7 Final Project                  ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");

        // Create default portfolio
        System.out.print("\nEnter your name: ");
        String userName = scanner.nextLine();
        currentPortfolio = new ArtPortfolio("My Creative Portfolio", userName);

        // Main program loop
        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    createArtworkMenu();
                    break;
                case 2:
                    displayArtworkMenu();
                    break;
                case 3:
                    portfolioManagementMenu();
                    break;
                case 4:
                    specialFeaturesMenu();
                    break;
                case 5:
                    demonstrateAllFeatures();
                    break;
                case 6:
                    running = false;
                    System.out.println("\nThank you for using the Art Generator!");
                    System.out.println("Total artworks created: " + ArtPortfolio.getTotalArtworksGlobal());
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    /**
     * Display the main menu
     */
    private static void displayMainMenu() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║              MAIN MENU                 ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║  1. Create New Artwork                 ║");
        System.out.println("║  2. Display Artwork                    ║");
        System.out.println("║  3. Portfolio Management               ║");
        System.out.println("║  4. Special Features                   ║");
        System.out.println("║  5. Demo All Features (Auto)           ║");
        System.out.println("║  6. Exit                               ║");
        System.out.println("╚════════════════════════════════════════╝");
    }

    /**
     * Menu for creating new artwork
     */
    private static void createArtworkMenu() {
        System.out.println("\n--- CREATE NEW ARTWORK ---");
        System.out.println("1. Fractal Art");
        System.out.println("2. Recursive Tree");
        System.out.println("3. Spiral Art");
        System.out.println("4. Cellular Automata");
        System.out.println("5. L-System");
        System.out.println("6. Polygon Fractal");
        System.out.println("7. Back to Main Menu");

        int choice = getIntInput("Select artwork type: ");

        if (choice == 7)
            return;

        System.out.print("Enter artwork title: ");
        String title = scanner.nextLine();
        System.out.print("Enter artist name: ");
        String artist = scanner.nextLine();

        ArtWork newArt = null;

        switch (choice) {
            case 1:
                newArt = createFractalArt(title, artist);
                break;
            case 2:
                newArt = createRecursiveTree(title, artist);
                break;
            case 3:
                newArt = createSpiralArt(title, artist);
                break;
            case 4:
                newArt = createCellularAutomataArt(title, artist);
                break;
            case 5:
                newArt = createLSystemArt(title, artist);
                break;
            case 6:
                newArt = createPolygonFractalArt(title, artist);
                break;
            default:
                System.out.println("Invalid choice.");
                return;
        }

        if (newArt != null) {
            tempArtworks.add(newArt);
            System.out.print("\nAdd to portfolio? (y/n): ");
            if (scanner.nextLine().toLowerCase().startsWith("y")) {
                currentPortfolio.addArtwork(newArt);
            }

            System.out.print("Display artwork now? (y/n): ");
            if (scanner.nextLine().toLowerCase().startsWith("y")) {
                newArt.display();
            }
        }
    }

    /**
     * Create a fractal artwork with user input
     */
    private static ArtWork createFractalArt(String title, String artist) {
        System.out.println("\n--- Fractal Art Options ---");
        System.out.println("1. Tree Fractal");
        System.out.println("2. Sierpinski Triangle");
        System.out.println("3. Koch Snowflake");

        int typeChoice = getIntInput("Select fractal type: ");
        String fractalType = "tree";

        switch (typeChoice) {
            case 1:
                fractalType = "tree";
                break;
            case 2:
                fractalType = "sierpinski";
                break;
            case 3:
                fractalType = "koch";
                break;
        }

        int iterations = getIntInput("Enter number of iterations (3-10): ");
        iterations = Math.max(3, Math.min(10, iterations));

        return new FractalArt(title, artist, iterations, fractalType);
    }

    /**
     * Create a recursive tree with user input
     */
    private static ArtWork createRecursiveTree(String title, String artist) {
        System.out.println("\n--- Recursive Tree Options ---");

        int depth = getIntInput("Enter tree depth (3-12): ");
        depth = Math.max(3, Math.min(12, depth));

        System.out.print("Add randomness? (y/n): ");
        boolean randomized = scanner.nextLine().toLowerCase().startsWith("y");

        int branches = 2;
        if (randomized) {
            branches = getIntInput("Number of branches per node (2-5): ");
            branches = Math.max(2, Math.min(5, branches));
        }

        return new RecursiveTree(title, artist, depth, randomized, branches);
    }

    /**
     * Create a spiral artwork with user input
     */
    private static ArtWork createSpiralArt(String title, String artist) {
        System.out.println("\n--- Spiral Art Options ---");
        System.out.println("1. Archimedean Spiral");
        System.out.println("2. Logarithmic Spiral");
        System.out.println("3. Golden Spiral");

        int typeChoice = getIntInput("Select spiral type: ");
        String spiralType = "archimedean";

        switch (typeChoice) {
            case 1:
                spiralType = "archimedean";
                break;
            case 2:
                spiralType = "logarithmic";
                break;
            case 3:
                spiralType = "golden";
                break;
        }

        int segments = getIntInput("Enter number of segments (50-200): ");
        segments = Math.max(50, Math.min(200, segments));

        System.out.print("Create bidirectional spiral? (y/n): ");
        boolean bidirectional = scanner.nextLine().toLowerCase().startsWith("y");

        return new SpiralArt(title, artist, segments, spiralType, bidirectional);
    }

    /**
     * Create a Cellular Automata artwork with user input
     */
    private static ArtWork createCellularAutomataArt(String title, String artist) {
        int size = getIntInput("Enter grid size (10-100): ");
        size = Math.max(10, Math.min(100, size));
        int generations = getIntInput("Enter number of generations (1-50): ");
        generations = Math.max(1, Math.min(50, generations));

        return new CellularAutomataArt(title, artist, size, generations);
    }

    /*
     * Create an L-System artwork with user input
     */

    private static ArtWork createLSystemArt(String title, String artist) {
        System.out.println("\n--- L-System Art ---");
        System.out.println("Select L-System type:");
        System.out.println("1. L-System Sticks (Fractal Plant)");
        System.out.println("2. Square Sierpinski");
        System.out.println("3. Von Koch Snowflake");

        int choice = getIntInput("Select type: ");

        String axiom = "";
        Map<Character, String> rules = new HashMap<>();
        double angle = 20.0; // default
        int depth = 4; // default, declare here!

        switch (choice) {
            case 1:
                // L-System Sticks
                System.out.println("\nUsing L-System Sticks:");
                System.out.println("Axiom: X");
                System.out.println("F -> FF");
                System.out.println("X -> F[+X]F[-X]+X");
                System.out.println("Angle: 20 degrees");

                axiom = "X";
                rules.put('F', "FF");
                rules.put('X', "F[+X]F[-X]+X");
                angle = 20.0;
                depth = depthPrompt(3, 6); // recommend 3-6
                break;

            case 2:
                // Square Sierpinski
                System.out.println("\nUsing Square Sierpinski:");
                System.out.println("Axiom: F+XF+F+XF");
                System.out.println("X -> XF-F+F-XF+F+XF-F+F-X");
                System.out.println("Angle: 90 degrees");

                axiom = "F+XF+F+XF";
                rules.put('X', "XF-F+F-XF+F+XF-F+F-X");
                angle = 90.0;
                depth = depthPrompt(3, 5); // recommend 3-5
                break;

            case 3:
                // Von Koch Snowflake
                System.out.println("\nUsing Von Koch Snowflake:");
                System.out.println("Axiom: F++F++F");
                System.out.println("F -> F-F++F-F");
                System.out.println("Angle: 60 degrees");

                axiom = "F++F++F";
                rules.put('F', "F-F++F-F");
                angle = 60.0;
                depth = depthPrompt(1, 4); // recommend 1-4
                break;

            default:
                // Fallback to Sticks if invalid choice
                System.out.println("Invalid choice. Using L-System Sticks by default.");
                axiom = "X";
                rules.put('F', "FF");
                rules.put('X', "F[+X]F[-X]+X");
                angle = 20.0;
                depth = depthPrompt(3, 6);
                break;
        }

        return new LSystemArt(title, artist, axiom, rules, depth, angle);
    }

    /*
     * Create a Polygon Fractal artwork
     */
    private static ArtWork createPolygonFractalArt(String title, String artist) {
        int sides = getIntInput("Enter number of sides (3-8): ");
        sides = Math.max(3, Math.min(8, sides));

        int depth = getIntInput("Enter recursion depth (2-6): ");
        depth = Math.max(2, Math.min(6, depth));

        double scaleFactor = 0.5; // good default
        System.out.println("Using default scale factor 0.5");

        return new PolygonFractalArt(title, artist, sides, depth, scaleFactor);
    }

    /**
     * Menu for displaying artwork
     */
    private static void displayArtworkMenu() {
        System.out.println("\n--- DISPLAY ARTWORK ---");
        System.out.println("1. Display from Portfolio");
        System.out.println("2. Display Recent (Temporary)");
        System.out.println("3. Back to Main Menu");

        int choice = getIntInput("Select option: ");

        switch (choice) {
            case 1:
                if (currentPortfolio.size() == 0) {
                    System.out.println("Portfolio is empty!");
                } else {
                    currentPortfolio.displayPortfolio();
                    System.out.print("\nEnter artwork number to display (0 to cancel): ");
                    int artNum = getIntInput("");
                    if (artNum > 0 && artNum <= currentPortfolio.size()) {
                        currentPortfolio.getArtworks().get(artNum - 1).display();
                    }
                }
                break;
            case 2:
                if (tempArtworks.isEmpty()) {
                    System.out.println("No temporary artworks!");
                } else {
                    System.out.println("\n--- Temporary Artworks ---");
                    for (int i = 0; i < tempArtworks.size(); i++) {
                        System.out.println((i + 1) + ". " + tempArtworks.get(i));
                    }
                    System.out.print("\nEnter artwork number to display (0 to cancel): ");
                    int artNum = getIntInput("");
                    if (artNum > 0 && artNum <= tempArtworks.size()) {
                        tempArtworks.get(artNum - 1).display();
                    }
                }
                break;
        }
    }

    /**
     * Portfolio management menu
     */
    private static void portfolioManagementMenu() {
        System.out.println("\n--- PORTFOLIO MANAGEMENT ---");
        System.out.println("1. View Portfolio");
        System.out.println("2. Sort by Complexity");
        System.out.println("3. Find Most Complex");
        System.out.println("4. Search by Title");
        System.out.println("5. Generate Report");
        System.out.println("6. Remove Artwork");
        System.out.println("7. Create New Portfolio");
        System.out.println("8. Export Artwork Description to File");
        System.out.println("9. Save Portfolio to File");
        System.out.println("10. Load Portfolio from File");
        System.out.println("11. Run AI Art Critique on Artwork");
        System.out.println("12. Back to Main Menu");

        int choice = getIntInput("Select option: ");

        switch (choice) {
            case 1:
                currentPortfolio.displayPortfolio();
                break;
            case 2:
                currentPortfolio.sortByComplexity(currentPortfolio.getArtworks());
                currentPortfolio.displayPortfolio();
                break;
            case 3:
                ArtWork mostComplex = currentPortfolio.findMostComplex(currentPortfolio.getArtworks());
                if (mostComplex != null) {
                    System.out.println("\nMost Complex Artwork:");
                    System.out.println(mostComplex);
                    System.out.println("Complexity Level: " + mostComplex.getComplexityLevel());
                }
                break;
            case 4:
                System.out.print("Enter title to search: ");
                String searchTitle = scanner.nextLine();
                ArtWork found = currentPortfolio.findArtworkByTitle(searchTitle);
                if (found != null) {
                    System.out.println("\nFound: " + found);
                    System.out.print("Display it? (y/n): ");
                    if (scanner.nextLine().toLowerCase().startsWith("y")) {
                        found.display();
                    }
                } else {
                    System.out.println("Artwork not found.");
                }
                break;
            case 5:
                currentPortfolio.generateReport();
                break;
            case 6:
                System.out.print("Enter title to remove: ");
                String removeTitle = scanner.nextLine();
                currentPortfolio.removeArtwork(removeTitle);
                break;
            case 7:
                System.out.print("Enter new portfolio name: ");
                String portfolioName = scanner.nextLine();
                System.out.print("Enter curator name: ");
                String curatorName = scanner.nextLine();
                currentPortfolio = new ArtPortfolio(portfolioName, curatorName);
                System.out.println("New portfolio created!");
                break;
            case 8:
                exportArtworkDescription();
                break;
            case 9:
                savePortfolioToFile();
                break;
            case 10:
                loadPortfolioFromFile();
                break;
            case 11:
                runAICritique();
                break;

            case 12:
                return;

        }
    }

    /**
     * Special features menu
     */
    private static void specialFeaturesMenu() {
        System.out.println("\n--- SPECIAL FEATURES ---");
        System.out.println("1. Seasonal Tree");
        System.out.println("2. Fibonacci Spiral");
        System.out.println("3. Galaxy Spiral");
        System.out.println("4. Generate Artwork ID");
        System.out.println("5. View Global Statistics");
        System.out.println("6. Back to Main Menu");

        int choice = getIntInput("Select feature: ");

        switch (choice) {
            case 1:
                createSeasonalTree();
                break;
            case 2:
                createFibonacciSpiral();
                break;
            case 3:
                createGalaxySpiral();
                break;
            case 4:
                if (currentPortfolio.size() > 0) {
                    ArtWork art = currentPortfolio.getArtworks().get(0);
                    String id = currentPortfolio.generateArtworkID(art);
                    System.out.println("\nGenerated ID for " + art.getTitle() + ": " + id);
                } else {
                    System.out.println("No artworks in portfolio!");
                }
                break;
            case 5:
                System.out.println("\n--- GLOBAL STATISTICS ---");
                System.out.println("Total Portfolios: " + ArtPortfolio.getTotalPortfolios());
                System.out.println("Total Artworks: " + ArtPortfolio.getTotalArtworksGlobal());
                break;
        }
    }

    private static void runAICritique() {
        if (currentPortfolio.size() == 0) {
            System.out.println("Portfolio is empty!");
            return;
        }

        currentPortfolio.displayPortfolio();
        System.out.print("\nEnter artwork number to critique (0 to cancel): ");
        int artNum = getIntInput("");

        if (artNum > 0 && artNum <= currentPortfolio.size()) {
            ArtWork art = currentPortfolio.getArtworks().get(artNum - 1);
            System.out.println("\n=== AI Art Critique ===");
            System.out.println(AIArtCritiqueEngine.generateCritique(art));
            System.out.println("=======================");
        }
    }

    /**
     * Create a seasonal tree
     */
    private static void createSeasonalTree() {
        System.out.println("\n--- Create Seasonal Tree ---");
        System.out.println("1. Spring");
        System.out.println("2. Summer");
        System.out.println("3. Fall");
        System.out.println("4. Winter");

        int season = getIntInput("Select season: ");
        String[] seasons = { "spring", "summer", "fall", "winter" };

        if (season >= 1 && season <= 4) {
            RecursiveTree seasonalTree = new RecursiveTree("Seasonal Tree", "Nature", 8);
            seasonalTree.generateSeasonalTree(seasons[season - 1]);

            System.out.print("\nAdd to portfolio? (y/n): ");
            if (scanner.nextLine().toLowerCase().startsWith("y")) {
                currentPortfolio.addArtwork(seasonalTree);
            }
        }
    }

    /**
     * Create a Fibonacci spiral
     */
    private static void createFibonacciSpiral() {
        SpiralArt fibSpiral = new SpiralArt("Fibonacci Spiral", "Mathematics");
        fibSpiral.generateFibonacciSpiral();

        System.out.print("\nAdd to portfolio? (y/n): ");
        if (scanner.nextLine().toLowerCase().startsWith("y")) {
            currentPortfolio.addArtwork(fibSpiral);
        }
    }

    /**
     * Create a galaxy spiral
     */
    private static void createGalaxySpiral() {
        int arms = getIntInput("Enter number of galaxy arms (2-6): ");
        arms = Math.max(2, Math.min(6, arms));

        SpiralArt galaxy = new SpiralArt("Galaxy", "Cosmos", 120);
        galaxy.generateGalaxySpiral(arms);

        System.out.print("\nAdd to portfolio? (y/n): ");
        if (scanner.nextLine().toLowerCase().startsWith("y")) {
            currentPortfolio.addArtwork(galaxy);
        }
    }

    /**
     * Demonstrate all features automatically
     */
    private static void demonstrateAllFeatures() {
        System.out.println("\n=== DEMONSTRATING ALL FEATURES ===");
        System.out.println("This will create sample artworks and show all functionality.\n");

        // Create sample artworks
        System.out.println("1. Creating diverse artworks...");

        // Fractals
        FractalArt fractal1 = new FractalArt("Mystic Tree", "Demo Artist", 6, "tree");
        FractalArt fractal2 = new FractalArt("Triangle Dream", "Demo Artist", 5, "sierpinski");
        FractalArt fractal3 = new FractalArt("Snow Crystal", "Demo Artist", 4, "koch");

        // Trees
        RecursiveTree tree1 = new RecursiveTree("Simple Oak", "Demo Artist", 7);
        RecursiveTree tree2 = new RecursiveTree("Wild Forest", "Demo Artist", 8, true, 3);

        // Spirals
        SpiralArt spiral1 = new SpiralArt("Golden Ratio", "Demo Artist", 100, "golden", false);
        SpiralArt spiral2 = new SpiralArt("Dual Spiral", "Demo Artist", 80, "logarithmic", true);

        // L-Systems
        Map<Character, String> rulesSticks = new HashMap<>();
        rulesSticks.put('F', "FF");
        rulesSticks.put('X', "F[+X]F[-X]+X");
        LSystemArt lsystem1 = new LSystemArt("Fractal Plant", "Demo Artist", "X", rulesSticks, 4, 20.0);

        Map<Character, String> rulesSierpinski = new HashMap<>();
        rulesSierpinski.put('X', "XF-F+F-XF+F+XF-F+F-X");
        LSystemArt lsystem2 = new LSystemArt("Square Sierpinski", "Demo Artist", "F+XF+F+XF", rulesSierpinski, 3, 90.0);

        Map<Character, String> rulesKoch = new HashMap<>();
        rulesKoch.put('F', "F-F++F-F");
        LSystemArt lsystem3 = new LSystemArt("Von Koch Snowflake", "Demo Artist", "F++F++F", rulesKoch, 3, 60.0);

        // Polygon Fractal
        PolygonFractalArt polygon = new PolygonFractalArt("Polygon Star", "Demo Artist", 5, 4, 0.5);

        // Cellular Automata
        CellularAutomataArt automata = new CellularAutomataArt("Cellular Glow", "Demo Artist", 50, 20);

        // Add to portfolio using different overloaded methods
        System.out.println("\n2. Adding to portfolio (demonstrating method overloading)...");
        currentPortfolio.addArtwork(fractal1);
        currentPortfolio.addArtwork("Quick Tree", "Demo Artist", "tree");

        ArtWork[] artArray = { fractal2, tree1, spiral1, lsystem1, polygon, automata };
        currentPortfolio.addArtwork(artArray);

        currentPortfolio.addArtwork(fractal3);
        currentPortfolio.addArtwork(tree2);
        currentPortfolio.addArtwork(spiral2);
        currentPortfolio.addArtwork(lsystem2);
        currentPortfolio.addArtwork(lsystem3);

        // Display portfolio
        System.out.println("\n3. Displaying portfolio...");
        currentPortfolio.displayPortfolio();

        // Demonstrate recursion features
        System.out.println("\n4. Demonstrating recursive features...");
        System.out.println("Total Complexity: " + currentPortfolio.calculateTotalComplexity());

        ArtWork mostComplex = currentPortfolio.findMostComplex(currentPortfolio.getArtworks());
        System.out.println("Most Complex: " + mostComplex.getTitle() +
                " (Complexity: " + mostComplex.getComplexityLevel() + ")");

        // Sort by complexity
        System.out.println("\n5. Sorting by complexity...");
        currentPortfolio.sortByComplexity(currentPortfolio.getArtworks());
        currentPortfolio.displayPortfolio();

        // Generate report
        System.out.println("\n6. Generating comprehensive report...");
        currentPortfolio.generateReport();

        // Display one artwork
        System.out.println("\n7. Displaying a sample artwork (Tree)...");
        tree1.display();

        // Special features
        System.out.println("\n8. Demonstrating special features...");

        // Seasonal tree
        RecursiveTree winterTree = new RecursiveTree("Winter Solitude", "Nature", 7);
        winterTree.generateSeasonalTree("winter");

        // Fibonacci spiral
        SpiralArt fibSpiral = new SpiralArt("Mathematical Beauty", "Fibonacci");
        fibSpiral.generateFibonacciSpiral();

        // Galaxy spiral
        SpiralArt galaxySpiral = new SpiralArt("Galaxy Spiral", "Cosmos", 120);
        galaxySpiral.generateGalaxySpiral(4);

        System.out.println("\n=== DEMONSTRATION COMPLETE ===");
        System.out.println("All features have been demonstrated!");
        System.out.println("You can now explore the features individually from the main menu.");

        pauseForUser();
    }

    /**
     * Export artwork description to a text file
     */
    private static void exportArtworkDescription() {
        if (currentPortfolio.size() == 0) {
            System.out.println("Portfolio is empty!");
            return;
        }

        currentPortfolio.displayPortfolio();
        System.out.print("\nEnter artwork number to export: ");
        int artNum = getIntInput("");

        if (artNum > 0 && artNum <= currentPortfolio.size()) {
            ArtWork art = currentPortfolio.getArtworks().get(artNum - 1);

            System.out.print("Enter filename to save (e.g., art_description.txt): ");
            String filename = scanner.nextLine();

            try {
                art.exportDescription(filename);
                System.out.println("Artwork description saved to " + filename);
            } catch (Exception e) {
                System.out.println("Error saving description: " + e.getMessage());
            }
        }
    }

    /**
     * Save portfolio to file
     */
    private static void savePortfolioToFile() {
        System.out.print("Enter filename to save portfolio (e.g., portfolio.ser): ");
        String filename = scanner.nextLine();

        try {
            currentPortfolio.saveToFile(filename);
            System.out.println("Portfolio saved to " + filename);
        } catch (Exception e) {
            System.out.println("Error saving portfolio: " + e.getMessage());
        }
    }

    /**
     * Load portfolio from file
     */
    private static void loadPortfolioFromFile() {
        System.out.print("Enter filename to load portfolio (e.g., portfolio.ser): ");
        String filename = scanner.nextLine();

        try {
            currentPortfolio = ArtPortfolio.loadFromFile(filename);
            System.out.println("Portfolio loaded from " + filename);
        } catch (Exception e) {
            System.out.println("Error loading portfolio: " + e.getMessage());
        }
    }

    /**
     * Prompt user for depth and store it for stepScalingFactor calculation.
     */
    private static int depthPrompt(int minRecommended, int maxRecommended) {
        int depth = getIntInput("Enter depth (recommended " + minRecommended + "-" + maxRecommended + "): ");
        depth = Math.max(minRecommended, Math.min(maxRecommended, depth)); // clamp to range
        return depth;
    }

    /**
     * Helper method to get integer input
     */
    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
            System.out.print("Please enter a number: ");
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        return value;
    }

    /**
     * Pause for user to read output
     */
    private static void pauseForUser() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }
}
