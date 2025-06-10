import java.util.Date;
import java.util.Objects;
import java.awt.Graphics2D;
import java.io.Serializable;

/**
 * Abstract base class for all types of artwork in the portfolio.
 * This class demonstrates inheritance, abstraction, and proper OOP design.
 */
public abstract class ArtWork implements Serializable {
    private static final long serialVersionUID = 1L;
    // Protected instance variables (accessible by subclasses)
    protected String title;
    protected String artist;
    protected Date creationDate;
    protected int complexityLevel;
    protected String description;

    /**
     * Constructor for ArtWork
     * 
     * @param title  The title of the artwork
     * @param artist The artist who created the artwork
     */
    public ArtWork(String title, String artist) {
        this.title = title;
        this.artist = artist;
        this.creationDate = new Date(); // Current date
        this.complexityLevel = 0; // Will be calculated by subclass
        this.description = "";
    }

    /**
     * Constructor with all parameters
     * 
     * @param title       The title of the artwork
     * @param artist      The artist who created the artwork
     * @param description Description of the artwork
     */
    public ArtWork(String title, String artist, String description) {
        this(title, artist);
        this.description = description;
    }

    // Abstract methods to be implemented by subclasses

    /**
     * Display the artwork (generate and show the recursive pattern)
     */
    public abstract void display();

    /**
     * Calculate the complexity level of the artwork
     * 
     * @return The complexity level as an integer
     */
    public abstract int calculateComplexity();

    /**
     * Get the type of artwork
     * 
     * @return String representation of the artwork type
     */
    public abstract String getArtType();

    /**
     * Get description of the artwork pattern
     * 
     * @return String description of the generated pattern
     */
    public abstract String getDescription();

    /**
     * Draw the artwork using graphics
     * 
     * @param g2d The Graphics2D object to draw with
     */
    public abstract void drawGraphics(Graphics2D g2d);

    // Concrete methods

    /**
     * Override equals method for proper comparison
     * 
     * @param other The object to compare with
     * @return true if objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (other == null || getClass() != other.getClass())
            return false;

        ArtWork artwork = (ArtWork) other;
        return Objects.equals(title, artwork.title) &&
                Objects.equals(artist, artwork.artist) &&
                Objects.equals(getArtType(), artwork.getArtType());
    }

    /**
     * Override toString method for string representation
     * 
     * @return String representation of the artwork
     */
    @Override
    public String toString() {
        return String.format("%s - \"%s\" by %s (Created: %tF, Complexity: %d)",
                getArtType(), title, artist, creationDate, complexityLevel);
    }

    /**
     * Export artwork description to a text file
     * 
     * @param filename The name of the file to write to
     */
    public void exportDescription(String filename) throws java.io.IOException {
        try (java.io.PrintWriter out = new java.io.PrintWriter(new java.io.FileWriter(filename))) {
            out.println("Artwork Title: " + getTitle());
            out.println("Artist: " + getArtist());
            out.println("Type: " + getArtType());
            out.println("Description: " + getDescription());
            out.println("Complexity Level: " + getComplexityLevel());
            out.println("Creation Date: " + getCreationDate());
        }
    }

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public int getComplexityLevel() {
        return complexityLevel;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
