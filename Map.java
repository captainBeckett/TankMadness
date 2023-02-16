import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Represents a game map of blocks in Tank Madness game
 * 
 * @author captainBeckett
 */
public class Map {
    // instance variables
    private ArrayList<Integer> xCoordinates;
    private ArrayList<Integer> yCoordinates;
    private ArrayList<Block> blocks;

    /**
     * Creates a new map according to a text file with block coordinates
     * 
     * @param filename textfile with block coordinates
     */
    public Map(String filename) {
        // initialize instance variables
        this.xCoordinates = new ArrayList<>();
        this.yCoordinates = new ArrayList<>();
        this.blocks = new ArrayList<>();
        // read in from the file
        Scanner input = null;
        try {
            input = new Scanner(new File(filename));
        } catch (Exception e) {
            // print the error
            e.printStackTrace();
        }
        // file is ready to be scanned
        while (input.hasNext()) {
            // skip over every third line
            input.nextLine();
            // grab each x coordinate
            String[] x = input.nextLine().split(", ");
            // insert each x coordinate into array of x coordinates
            for (int i = 0; i < x.length; i++) {
                this.xCoordinates.add(Integer.parseInt(x[i]));
            }
            // grab each y coordinate
            String[] y = input.nextLine().split(", ");
            // insert each y coordinate into array of y coordinates
            for (int j = 0; j < y.length; j++) {
                this.yCoordinates.add(Integer.parseInt(y[j]));
            }
        }

        // fill block array with new blocks based on each position in x and y
        // coordinates array
        for (int i = 0; i < this.xCoordinates.size(); i++) {
            this.blocks.add(new Block(this.xCoordinates.get(i), this.yCoordinates.get(i)));
        }

    }

    /**
     * Returns the number of blocks in map
     * 
     * @return number of blocks
     */
    public int numBlocks() {
        return this.blocks.size();
    }

    /**
     * Returns the block at a given index
     * 
     * @param index index
     * @return block at index
     */
    public Block getBlock(int index) {
        return this.blocks.get(index);
    }

}
