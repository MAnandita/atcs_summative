import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;

/**
 * ArtPortfolio class to manage collections of ArtWork objects.
 * Demonstrates ArrayLists, method overloading, static variables, and recursion.
 */
public class ArtPortfolio implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<ArtWork> artworks;
    private String portfolioName;
    private String curator;
    private Date creationDate;

    // Static variable to track total portfolios created
    private static int totalPortfolios = 0;
    private static int totalArtworksAcrossAllPortfolios = 0;

    /**
     * Constructor for ArtPortfolio
     * 
     * @param portfolioName Name of the portfolio
     * @param curator       Name of the curator/owner
     */
    public ArtPortfolio(String portfolioName, String curator) {
        this.portfolioName = portfolioName;
        this.curator = curator;
        this.artworks = new ArrayList<>();
        this.creationDate = new Date();
        totalPortfolios++;
    }

    // Method overloading for adding artworks

    /**
     * Add a single artwork to the portfolio
     * 
     * @param art The artwork to add
     */
    public void addArtwork(ArtWork art) {
        if (art != null) {
            artworks.add(art);
            totalArtworksAcrossAllPortfolios++;
            System.out.println("Added: " + art.getTitle() + " to portfolio");
        }
    }

    /**
     * Add artwork by creating it (method overloading)
     * 
     * @param title  Title of the artwork
     * @param artist Artist name
     * @param type   Type of artwork ("fractal", "tree", "spiral")
     */
    public void addArtwork(String title, String artist, String type) {
        ArtWork newArt = null;

        switch (type.toLowerCase()) {
            case "fractal":
                newArt = new FractalArt(title, artist);
                break;
            case "tree":
                newArt = new RecursiveTree(title, artist);
                break;
            case "spiral":
                newArt = new SpiralArt(title, artist);
                break;
            default:
                System.out.println("Unknown artwork type: " + type);
                return;
        }

        addArtwork(newArt);
    }

    /**
     * Add multiple artworks at once (method overloading)
     * 
     * @param artArray Array of artworks to add
     */
    public void addArtwork(ArtWork[] artArray) {
        for (ArtWork art : artArray) {
            addArtwork(art);
        }
        System.out.println("Added " + artArray.length + " artworks to portfolio");
    }

    /**
     * Display all artworks in the portfolio
     */
    public void displayPortfolio() {
        System.out.println("\n========================================");
        System.out.println("Portfolio: " + portfolioName);
        System.out.println("Curator: " + curator);
        System.out.println("Created: " + creationDate);
        System.out.println("Total Artworks: " + artworks.size());
        System.out.println("========================================\n");

        for (int i = 0; i < artworks.size(); i++) {
            System.out.println((i + 1) + ". " + artworks.get(i));
        }
    }

    /**
     * Sort artworks by complexity (uses object references)
     * Uses bubble sort with recursion
     * 
     * @param artList The list to sort (modifies the actual list)
     */
    public void sortByComplexity(ArrayList<ArtWork> artList) {
        if (artList == null || artList.size() <= 1) {
            return;
        }

        // Recursive bubble sort
        recursiveBubbleSort(artList, artList.size());
        System.out.println("Portfolio sorted by complexity!");
    }

    /**
     * Private recursive helper for bubble sort
     * 
     * @param list The list to sort
     * @param n    Current size to consider
     */
    private void recursiveBubbleSort(ArrayList<ArtWork> list, int n) {
        // Base case: if size is 1, return
        if (n == 1) {
            return;
        }

        // One pass of bubble sort
        for (int i = 0; i < n - 1; i++) {
            if (list.get(i).getComplexityLevel() > list.get(i + 1).getComplexityLevel()) {
                // Swap
                ArtWork temp = list.get(i);
                list.set(i, list.get(i + 1));
                list.set(i + 1, temp);
            }
        }

        // Recursive call for remaining elements
        recursiveBubbleSort(list, n - 1);
    }

    /**
     * Find the most complex artwork using recursion
     * 
     * @param artList The list to search
     * @return The artwork with highest complexity
     */
    public ArtWork findMostComplex(ArrayList<ArtWork> artList) {
        if (artList == null || artList.isEmpty()) {
            return null;
        }

        return recursiveFindMostComplex(artList, 0, null);
    }

    /**
     * Private recursive helper to find most complex artwork
     * 
     * @param list       The list to search
     * @param index      Current index
     * @param currentMax Current maximum complexity artwork
     * @return Artwork with highest complexity
     */
    private ArtWork recursiveFindMostComplex(ArrayList<ArtWork> list, int index, ArtWork currentMax) {
        // Base case: reached end of list
        if (index >= list.size()) {
            return currentMax;
        }

        ArtWork current = list.get(index);

        // Update max if current is more complex
        if (currentMax == null || current.getComplexityLevel() > currentMax.getComplexityLevel()) {
            currentMax = current;
        }

        // Recursive call for next element
        return recursiveFindMostComplex(list, index + 1, currentMax);
    }

    /**
     * Find artwork by title using recursive search
     * 
     * @param title Title to search for
     * @return The artwork if found, null otherwise
     */
    public ArtWork findArtworkByTitle(String title) {
        return recursiveFindByTitle(artworks, title, 0);
    }

    /**
     * Private recursive helper for title search
     * 
     * @param list  The list to search
     * @param title Title to find
     * @param index Current index
     * @return The artwork if found
     */
    private ArtWork recursiveFindByTitle(ArrayList<ArtWork> list, String title, int index) {
        // Base case: not found
        if (index >= list.size()) {
            return null;
        }

        // Check current artwork
        if (list.get(index).getTitle().equalsIgnoreCase(title)) {
            return list.get(index);
        }

        // Recursive call for next element
        return recursiveFindByTitle(list, title, index + 1);
    }

    /**
     * Calculate total complexity of all artworks recursively
     * 
     * @return Total complexity value
     */
    public int calculateTotalComplexity() {
        return recursiveTotalComplexity(artworks, 0);
    }

    /**
     * Private recursive helper for total complexity
     * 
     * @param list  The list of artworks
     * @param index Current index
     * @return Sum of complexity values
     */
    private int recursiveTotalComplexity(ArrayList<ArtWork> list, int index) {
        // Base case
        if (index >= list.size()) {
            return 0;
        }

        // Add current complexity to recursive sum
        return list.get(index).getComplexityLevel() +
                recursiveTotalComplexity(list, index + 1);
    }

    /**
     * Generate a report of the portfolio
     */
    public void generateReport() {
        System.out.println("\n=== PORTFOLIO REPORT ===");
        System.out.println("Portfolio: " + portfolioName);
        System.out.println("Curator: " + curator);
        System.out.println("Created: " + creationDate);
        System.out.println("\n--- Statistics ---");
        System.out.println("Total Artworks: " + artworks.size());
        System.out.println("Average Complexity: " + calculateAverageComplexity());
        System.out.println("Total Complexity: " + calculateTotalComplexity());

        ArtWork mostComplex = findMostComplex(artworks);
        if (mostComplex != null) {
            System.out.println("Most Complex: " + mostComplex.getTitle() +
                    " (Complexity: " + mostComplex.getComplexityLevel() + ")");
        }

        System.out.println("\n--- Art Type Distribution ---");
        Map<String, Integer> distribution = getArtTypeDistribution();
        for (Map.Entry<String, Integer> entry : distribution.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        System.out.println("\n--- AI Art Critiques ---");
        for (ArtWork art : artworks) {
            System.out.println("\nArtwork: " + art.getTitle() + " (" + art.getArtType() + ")");
            System.out.println(AIArtCritiqueEngine.generateCritique(art));
        }

        System.out.println("\n--- Global Statistics ---");
        System.out.println("Total Portfolios Created: " + getTotalPortfolios());
        System.out.println("Total Artworks Across All Portfolios: " + getTotalArtworksGlobal());
        System.out.println("========================\n");
    }

    /**
     * Private helper to calculate average complexity
     * 
     * @return Average complexity of artworks
     */
    private double calculateAverageComplexity() {
        if (artworks.isEmpty()) {
            return 0.0;
        }
        return (double) calculateTotalComplexity() / artworks.size();
    }

    /**
     * Private helper to get distribution of art types
     * 
     * @return Map of art type to count
     */
    private Map<String, Integer> getArtTypeDistribution() {
        Map<String, Integer> distribution = new HashMap<>();

        for (ArtWork art : artworks) {
            String type = art.getArtType();
            distribution.put(type, distribution.getOrDefault(type, 0) + 1);
        }

        return distribution;
    }

    /**
     * Generate a unique ID for an artwork
     * 
     * @param artwork The artwork to generate ID for
     * @return Unique identifier string
     */
    public String generateArtworkID(ArtWork artwork) {
        String type = artwork.getArtType().replaceAll(" ", "").substring(0, 3).toUpperCase();
        String artistInitials = getInitials(artwork.getArtist());
        int complexity = artwork.getComplexityLevel();
        long timestamp = artwork.getCreationDate().getTime() % 10000;

        return String.format("%s-%s-%d-%04d", type, artistInitials, complexity, timestamp);
    }

    /**
     * Private helper to get initials from a name
     * 
     * @param name The full name
     * @return Initials as uppercase string
     */
    private String getInitials(String name) {
        String[] parts = name.split(" ");
        StringBuilder initials = new StringBuilder();

        for (String part : parts) {
            if (!part.isEmpty()) {
                initials.append(part.charAt(0));
            }
        }

        return initials.toString().toUpperCase();
    }

    /**
     * Remove artwork by title
     * 
     * @param title Title of artwork to remove
     * @return true if removed, false if not found
     */
    public boolean removeArtwork(String title) {
        ArtWork toRemove = findArtworkByTitle(title);
        if (toRemove != null) {
            artworks.remove(toRemove);
            totalArtworksAcrossAllPortfolios--;
            System.out.println("Removed: " + title);
            return true;
        }
        System.out.println("Artwork not found: " + title);
        return false;
    }

    /**
     * Get artworks by type
     * 
     * @param type The type to filter by
     * @return List of artworks of that type
     */
    public ArrayList<ArtWork> getArtworksByType(String type) {
        ArrayList<ArtWork> filtered = new ArrayList<>();

        for (ArtWork art : artworks) {
            if (art.getArtType().equalsIgnoreCase(type)) {
                filtered.add(art);
            }
        }

        return filtered;
    }

    public void saveToFile(String filename) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(this);
        }
    }

    public static ArtPortfolio loadFromFile(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return (ArtPortfolio) in.readObject();
        }
    }

    // Static methods

    /**
     * Get total number of portfolios created
     * 
     * @return Total portfolio count
     */
    public static int getTotalPortfolios() {
        return totalPortfolios;
    }

    /**
     * Get total artworks across all portfolios
     * 
     * @return Total artwork count globally
     */
    public static int getTotalArtworksGlobal() {
        return totalArtworksAcrossAllPortfolios;
    }

    /**
     * Reset global statistics (static method)
     */
    public static void resetGlobalStats() {
        totalPortfolios = 0;
        totalArtworksAcrossAllPortfolios = 0;
        System.out.println("Global statistics reset.");
    }

    // Getters
    public ArrayList<ArtWork> getArtworks() {
        return artworks;
    }

    public String getPortfolioName() {
        return portfolioName;
    }

    public String getCurator() {
        return curator;
    }

    public int size() {
        return artworks.size();
    }
}
