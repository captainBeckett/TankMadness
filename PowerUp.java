import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * Represents a powerup block in the Tank Madness Game
 * 
 * @author captainBeckett
 */
public class PowerUp {
    //instance variables
    private int x;
    private int y;
    public final int WIDTH = 40;
    public final int HEIGHT = 40;
    private BufferedImage powerup;

    /**
     * Creates a new powerup block based on x coordinate, y coordinate and game map
     * 
     * @param x x coordinate
     * @param y y coordinate
     * @param m game map
     */
    public PowerUp(int x, int y, Map m){
        //initialize instance variables
        this.x = x;
        this.y = y;
        //load in image
        try{
            this.powerup = ImageIO.read(new File("Images/PowerUp.png"));
        }catch(Exception e){
            //print our error
            e.printStackTrace();
        }
        //adjust position of powerup if collides with map
        adjustCollision(m);
    }

    /**
     * Draws the powerup icon in Interface using regular Graphics
     * 
     * @param g Graphics
     */
    public void drawPowerUp(Graphics g){
        g.drawImage(this.powerup, this.x, this.y, WIDTH, HEIGHT, null);
    }

    /**
     * PowerUp collision detection with blocks in map
     * 
     * @param m map
     */
    public void adjustCollision(Map m) {
        //create temporary rectangle to compare collisions
        Rectangle guide = new Rectangle(this.x, this.y, WIDTH, HEIGHT);
        //check if powerup intersects with each block in the map
        for (int i = 0; i < m.numBlocks(); i++) {
            //powerup intersects block
            if (guide.intersects(m.getBlock(i).getRectangle())) {
                //get the collision rectangle
                Rectangle collide = guide.intersection(m.getBlock(i).getRectangle());
                //adjust powerup coordinates according to collision rectangle height and width
                if (collide.height < collide.width) {
                    // fix up or down
                    if (this.y < m.getBlock(i).getY()) {
                        //on top
                        this.y -= collide.height;
                    } else {
                        //underneath
                        this.y += collide.height;
                    }
                } else {
                    // fix left or right
                    if (this.x < m.getBlock(i).getX()) {
                        //left
                        this.x -= collide.width;
                    } else {
                        //right
                        this.x += collide.width;
                    }
                }
            }
        }
    }

    /**
     * Returns the rectangle representation of a powerup
     * 
     * @return powerup rectangle
     */
    public Rectangle getRect(){
        return new Rectangle(this.x, this.y, WIDTH, HEIGHT);
    }
}
