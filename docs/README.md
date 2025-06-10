# Recursive Art Generator - AP CS Unit 7 Final Project

## Project Overview

This project is a Java application that generates recursive artwork using object-oriented programming principles. It demonstrates mastery of inheritance, recursion, method overloading, ArrayLists, and other advanced Java concepts.

## Features

### Visual Graphics Display
- **Graphical Window**: Each artwork opens in a separate window (800x600 pixels)
- **Real-time Rendering**: See the recursive patterns drawn visually
- **Color-coded Patterns**: Different colors for different artwork types and variations
- **Anti-aliased Graphics**: Smooth, high-quality visual output

### Art Types (with Visual Graphics)
1. **Fractal Art** - Generates three types of fractals:
   - Tree fractals with branching patterns (brown to green gradient)
   - Sierpinski triangles (blue filled triangles)
   - Koch snowflakes (cornflower blue lines)

2. **Recursive Trees** - Creates customizable tree patterns:
   - Adjustable depth and branch count
   - Optional randomization for natural appearance
   - Seasonal variations with appropriate colors:
     - Spring: Light greens
     - Summer: Balanced greens
     - Fall: Oranges and reds
     - Winter: Grays
   - Green leaves at branch ends

3. **Spiral Art** - Produces mathematical spirals:
   - Archimedean spirals (dark green)
   - Logarithmic spirals (blue violet)
   - Golden spirals (gold color)
   - Bidirectional spirals with contrasting colors
   - Decorative star elements along the spiral
   - Galaxy spiral with multiple arms (special feature)

4. **L-System Art** - Recursive grammar-based art:
   - Fractal plant (sticks)
   - Square Sierpinski
   - Von Koch snowflake
   - Supports variable angle and depth
   - Turtle graphics rendering

5. **Polygon Fractal Art**:
   - Recursive fractal patterns based on polygons (triangles, squares, pentagons, etc.)
   - Adjustable recursion depth and polygon sides
   - Color-coded by recursion level

6. **Cellular Automata Art**:
   - Dynamic grid-based patterns generated over multiple generations
   - Customizable grid size and number of generations
   - Visual evolution of cellular patterns
   - Fading colors and age-based visualization

### Portfolio Management
- Add artworks using multiple overloaded methods
- Sort artworks by complexity using recursive bubble sort
- Search for artworks by title recursively
- Generate comprehensive portfolio reports:
  - Total complexity
  - Average complexity
  - Most complex artwork
  - Art type distribution
- **AI-based Art Critique Engine**:
  - Automatically generates a written art critique for each artwork type (FractalArt, RecursiveTree, SpiralArt, LSystemArt, PolygonFractalArt, CellularAutomataArt)
  - Critiques are specific to the type of artwork and its complexity
  - Final comment is drawn from a set of curated phrases
- Export artwork descriptions to text files
- Save/load entire portfolios to/from files
- Generate unique Artwork IDs based on metadata

### Special Features
- Seasonal tree generation
- Fibonacci spiral generator
- Galaxy spiral generator
- Artwork ID generator
- Global statistics across all portfolios

## Technical Implementation

### Object-Oriented Design
- **Abstract Base Class**: `ArtWork` defines common properties and abstract methods
- **Inheritance**: Six concrete subclasses extend `ArtWork`:
  - `FractalArt`
  - `RecursiveTree`
  - `SpiralArt`
  - `LSystemArt`
  - `PolygonFractalArt`
  - `CellularAutomataArt`
- **Polymorphism**: Artworks stored in `ArrayList<ArtWork>` and accessed polymorphically
- **Encapsulation**: Private helper methods and protected fields

### Recursion
- All art generation uses recursive algorithms:
  - Recursive tree drawing
  - Spiral segment generation
  - Fractal generation
  - L-System expansion and drawing
  - Polygon fractal recursion
  - Cellular automata generations
- Portfolio operations (sorting, searching, calculating) use recursion
- Complexity calculations are recursive

### Method Overloading
- Multiple constructors in each artwork class
- Three versions of `addArtwork()` in `ArtPortfolio`
- Flexible parameter options for customization

### Static Features
- Global portfolio and artwork counters
- Static methods for accessing global statistics

## How to Run

1. **Compile all Java files**:
   ```bash
   javac -d bin src/*.java
   ```

2. **Run the program**:
   ```bash
   java -cp bin ArtGenerator
   ```

3. **Follow the menu options**:
   - Option 5 provides an automatic demonstration of all features
   - Create custom artworks with various parameters
   - Manage portfolios and view statistics

## Project Structure

```
ArtworkProject/
├── src/
│   ├── ArtWork.java               # Abstract base class
│   ├── FractalArt.java            # Fractal artwork implementation
│   ├── RecursiveTree.java         # Tree artwork implementation
│   ├── SpiralArt.java             # Spiral artwork implementation
│   ├── LSystemArt.java            # L-System artwork implementation
│   ├── PolygonFractalArt.java     # Polygon fractal artwork
│   ├── CellularAutomataArt.java   # Cellular automata artwork
│   ├── AIArtCritiqueEngine.java   # AI-based art critique system
│   ├── ArtPortfolio.java          # Portfolio management
│   ├── ArtGenerator.java          # Main program with UI
├── bin/                           # Compiled class files
├── plan/
│   └── ProjectPlan.txt            # Project plan & initial documentation
├── docs/
│   ├── README.md                  # Final project README (this document)
│   └── SampleOutput.txt           # Example program output
└── reflection/
    └── ProjectReflection.txt      # Final project reflection document
```

## Key Concepts Demonstrated

1. **Inheritance & Polymorphism**: Base class with multiple subclasses
2. **Recursion**: Used throughout for art generation and data processing
3. **ArrayLists**: Dynamic collections for portfolio management
4. **Method Overloading**: Multiple versions of methods with different parameters
5. **Static Variables/Methods**: Global state tracking
6. **Object References**: Passing and modifying objects
7. **Private Helper Methods**: Encapsulation of complex logic
8. **Proper equals() and toString()**: Overridden Object methods
9. **AI Critique System**:
    - Provides dynamic text commentary on artworks when generating reports or exporting descriptions
    - Commentary is customized to the artwork type and its complexity

## Sample Usage

1. **Create a Fractal**:
   - Select "Create New Artwork" → "Fractal Art"
   - Choose fractal type and iterations
   - View the generated pattern

2. **Generate Seasonal Tree**:
   - Select "Special Features" → "Seasonal Tree"
   - Choose a season for unique characteristics

3. **Create L-System Art**:
   - Select "Create New Artwork" → "L-System"
   - Choose L-System type (Fractal Plant, Sierpinski Square, Von Koch Snowflake)
   - Choose recursion depth
   - View the generated L-System pattern

4. **Create Polygon Fractal**:
   - Select "Create New Artwork" → "Polygon Fractal"
   - Choose sides and recursion depth
   - View recursive polygon pattern

5. **Create Cellular Automata Art**:
   - Select "Create New Artwork" → "Cellular Automata"
   - Choose grid size and number of generations
   - View dynamic cellular grid evolution

6. **Portfolio Analysis**:
   - Add multiple artworks
   - Sort by complexity
   - Generate comprehensive report (includes AI art critique)

7. **Export / Save / Load**:
   - Export artwork descriptions to text files
   - Save entire portfolio to file
   - Load portfolio back from file

8. **Special Features**:
   - Generate seasonal trees
   - Generate Fibonacci spiral
   - Generate Galaxy spiral
   - Generate Artwork ID

## Educational Value

This project reinforces:
- Object-oriented design principles
- Recursive problem-solving
- Data structure usage (ArrayLists)
- User interface design
- Code organization and documentation
- The beauty of mathematical patterns through recursion
- How to design and integrate an AI-based commentary system to enhance user interaction