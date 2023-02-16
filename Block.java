import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.io.File;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 * Represents a block in the Tank Madness game map
 * 
 * @author captainBeckett
 */
public class Block {
    // instance variables
    private int x;
    private int y;
    public final int WIDTH = 22;
    public final int HEIGHT = 22;
    private boolean active = false;

    private BufferedImage inactiveBlock;
    private BufferedImage activeBlock;

    private double timer;
    private int desiredFPS = 60;

    /**
     * Creates a new block with the x coordinate and y coordinate
     * 
     * @param x x coordinate
     * @param y y coordinate
     */
    public Block(int x, int y) {
        this.x = x;
        this.y = y;
        //load in block images
        try {
            this.inactiveBlock = ImageIO.read(new File("Images/InactiveBlockImage.png"));
            this.activeBlock = ImageIO.read(new File("Images/activeBlock.png"));
        } catch (Exception e) {
            //print our error
            e.printStackTrace();
        }
        //set block activated timer to 5 seconds
        this.timer = 5 * desiredFPS;
    }

    /**
     * Draws the block on Interface using regular Graphics
     * 
     * @param g Graphics
     */
    public void drawBlock(Graphics g) {
        //draw block
        if (this.active == false) {
            //draw inactivated block
            g.drawImage(this.inactiveBlock, this.x, this.y, WIDTH, HEIGHT, null);
        } else {
            //draw activated block
            g.drawImage(this.activeBlock, this.x, this.y, WIDTH, HEIGHT, null);
            //countdown activation timer
            this.timer--;
        }

        //timer expired
        if (this.timer < 0) {
            //reset timer to default value
            this.timer = 5 * desiredFPS;
            //set block to inactive
            this.active = false;
        }
    }

    /**
     * Returns the x coordinate of the block
     * 
     * @return x coordinate
     */
    public int getX() {
        return this.x;
    }

    /**
     * Returns the y coordinate of the block
     * 
     * @return y coordinate
     */
    public int getY() {
        return this.y;
    }

    /**
     * Activates block based on collision in Interface
     * 
     * @param activated whether ammo collided with block or not
     */
    public void setActive(boolean activated) {
        this.active = activated;
    }

    /**
     * Returns the rectangle coordinates of block
     * 
     * @return block rectangle
     */
    public Rectangle getRectangle() {
        return new Rectangle(this.x, this.y, WIDTH, HEIGHT);
    }

}