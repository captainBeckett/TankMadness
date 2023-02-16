import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

/**
 * Represents a normal ammunition in the Tank Madness game
 * 
 * @author captainBeckett
 */
public class NormalAmmo extends Ammo {
    // instance variables
    private int ammoSpeed;
    private Rectangle ammoRect;
    private int desiredFPS = 60;
    private double timer = 5 * desiredFPS;
    public final int WIDTH = 6;
    public final int HEIGHT = 6;

    /**
     * Creates a new normal ammunition based on x coordinate, y coordinate and
     * initial tank degree turn
     * 
     * @param x          x coordinate
     * @param y          y coordinate
     * @param degreeTurn tank degree turn
     */
    public NormalAmmo(int x, int y, int degreeTurn) {
        //initialize instance variables
        super(x, y, degreeTurn);
        this.ammoSpeed = 5;
        this.ammoRect = new Rectangle(super.getX(), super.getY(), WIDTH, HEIGHT);

    }

    /**
     * Draws the ammo on the interface using regular graphics 2D
     * 
     * @param g Graphics
     */
    public void drawAmmo(Graphics g) {
        // transform graphics variable into Graphics 2D
        Graphics2D g2d = (Graphics2D) g;
        // set default ammo color to black
        g2d.setColor(Color.BLACK);
        // always update rectangle coordinates
        this.ammoRect.x = super.getX();
        this.ammoRect.y = super.getY();
        // draw ammo rectangle on screen
        g2d.fill(ammoRect);
    }

    /**
     * Moves the ammo on the map according to initial direction and potential
     * collisions with blocks in game map
     * 
     * @param m game map of blocks
     */
    public void moveAmmo(Map m) {
        //default block tracker
        int tracker = -1;
        //default collision rectangle
        Rectangle collide = new Rectangle(0, 0, 0, 0);
        //default collision rectangle area
        int area = collide.width * collide.height;
        //check if ammo rectangle intersects with any of the blocks in the map
        //grab the largest collision rectangle
        for (int i = 0; i < m.numBlocks(); i++) {
            // ammo collides with block
            if (this.ammoRect.intersects(m.getBlock(i).getRectangle())) {
                //compare area of intersection rectangle with collision rectangle
                if (this.ammoRect.intersection(m.getBlock(i).getRectangle()).height
                        * this.ammoRect.intersection(m.getBlock(i).getRectangle()).width > area) {
                    // if area is bigger, set collision rectangle equal to intersection
                    collide = this.ammoRect.intersection(m.getBlock(i).getRectangle());
                    //set tracker equal to block position in map
                    tracker = i;
                    //set area equal to new collision rectangle area
                    area = collide.height * collide.width;
                }
            }
        }
        // fix the smaller of height or width
        if (collide.height < collide.width) {
            //adjust normal ammo coordinates
            if (super.getY() < m.getBlock(tracker).getY()) {
                //move on top of block
                super.setY(super.getY() - collide.height);
            } else {
                //move underneath block
                super.setY(super.getY() + collide.height);
            }
            //change y direction
            if (super.getDY() == 1) {
                super.setDY(-1);
            } else {
                super.setDY(1);
            }
        } else if (area != 0) {
            //adjust normal ammo coordinates
            if (super.getX() < m.getBlock(tracker).getX()) {
                //move on the left of block
                super.setX(super.getX() - collide.width);
            } else {
                //move on the right of block
                super.setX(super.getX() + collide.width);
            }
            //change x direction
            if (super.getDX() == 1) {
                super.setDX(-1);
            } else {
                super.setDX(1);
            }
        }

        //change the color of block with the largest collision
        if (area > 0) {
            m.getBlock(tracker).setActive(true);
        }

        // adjust ammo position according to initial direction and potential collisions
        super.setY((int) Math.round(super.getY() + super.getDY() * this.ammoSpeed * super.getDirection()[0]));
        super.setX((int) Math.round(super.getX() + super.getDX() * this.ammoSpeed * super.getDirection()[1]));
    }

    /**
     * Returns whether the ammunition is alive or expired
     * 
     * @return if ammunition is alive
     */
    public boolean stillLive() {
        // timer is expired
        if (timer <= 0) {
            // ammunition is expired
            return false;
        } else {
            // countdown
            timer--;
            return true;
        }
    }

    /**
     * Returns the current rectangle of ammo
     * 
     * @return ammo rectangle
     */
    public Rectangle getRectangle() {
        // always update rectangle coordinates
        this.ammoRect.x = super.getX();
        this.ammoRect.y = super.getY();
        return this.ammoRect;
    }

}
