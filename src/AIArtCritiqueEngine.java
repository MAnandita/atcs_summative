import java.util.Random;

public class AIArtCritiqueEngine {

    private static Random rand = new Random();

    public static String generateCritique(ArtWork art) {
        StringBuilder critique = new StringBuilder();

        // Common intro
        critique.append("Analyzing artwork: \"" + art.getTitle() + "\" by " + art.getArtist() + ".\n");

        // Type-based critique
        String type = art.getArtType();
        switch (type) {
            case "Fractal":
            case "Fractal Art":
                critique.append(fractalCritique(art));
                break;
            case "Recursive Tree":
                critique.append(treeCritique(art));
                break;
            case "Spiral":
            case "Spiral Art":
                critique.append(spiralCritique(art));
                break;
            case "Polygon Fractal":
                critique.append(polygonCritique(art));
                break;
            case "L-System":
                critique.append(lsystemCritique(art));
                break;
            case "Cellular Automata":
                critique.append(cellularAutomataCritique(art));
                break;
            default:
                critique.append("This artwork type offers a unique recursive structure.\n");
        }

        // Final random comment
        critique.append(randomFinalComment());

        return critique.toString();
    }

    // Example critiques
    private static String fractalCritique(ArtWork art) {
        return "The fractal composition showcases strong recursive layering. " +
                "The scaling between iterations is consistent, and the balance between detail and overall structure is well maintained.\n"
                +
                "Complexity score: " + art.calculateComplexity() + ".\n";
    }

    private static String treeCritique(ArtWork art) {
        return "The recursive tree demonstrates effective control of branch angles and depth. " +
                "The tapering of branches feels natural, and the variation across levels adds interest without overwhelming the form.\n";
    }

    private static String spiralCritique(ArtWork art) {
        return "The spiral achieves a clear visual flow. " +
                "The progression of segment lengths and curvature is well executed, resulting in a composition that draws the eye steadily inward or outward.\n";
    }

    private static String polygonCritique(ArtWork art) {
        return "The recursive polygon pattern exhibits precise control of geometric relationships. " +
                "Each layer adds complexity while preserving the underlying symmetry, creating a coherent and visually engaging structure.\n";
    }

    private static String lsystemCritique(ArtWork art) {
        return "This L-System rendering demonstrates thoughtful parameter tuning. " +
                "The balance between density and readability is well handled, and the recursive branching structure fills the space in a controlled way.\n";
    }

    private static String cellularAutomataCritique(ArtWork art) {
        return "The cellular automata grid shows an engaging evolution of patterns. " +
                "The choice of rule set and grid size produces interesting variations across generations, with a good balance between order and complexity.\n";
    }

    private static String randomFinalComment() {
        String[] phrases = {
                "Overall, this is a well-crafted example of recursive visual art.",
                "The piece reflects a strong understanding of both algorithmic structure and visual impact.",
                "The work demonstrates effective use of recursion to build visual complexity.",
                "This is a thoughtfully designed piece with clear attention to visual form.",
                "The result balances algorithmic rigor with aesthetic clarity."
        };
        return phrases[rand.nextInt(phrases.length)];
    }

}
