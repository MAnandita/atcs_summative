#!/bin/bash

# Compile the project
echo "Compiling Java files..."
javac -d bin src/*.java

# Check if compilation was successful
if [ $? -eq 0 ]; then
    echo "Compilation successful!"
    echo "Running Art Generator..."
    echo ""
    # Run the program
    java -cp bin ArtGenerator
else
    echo "Compilation failed. Please check for errors."
fi
