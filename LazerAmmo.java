import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

/**
 * Represents a lazer ammunition in the Tank Madness game
 * 
 * @author captainBeckett
 */
public class LazerAmmo extends Ammo {
    // instance variables
    private Rectangle[] lazer;
    public final int WIDTH = 3;
    public final int HEIGHT = 3;
    private boolean lazerFired;

    /**
     * Creates a new lazer ammunition according to x coordinate, y coordinate and
     * initial direction of tank
     * 
     * @param x          x coordinate
     * @param y          y coordinate
     * @param degreeTurn initial direction of tank
     */
    public LazerAmmo(int x, int y, int degreeTurn) {
        // initialize instance variables
        super(x, y, degreeTurn);
        this.lazer = new Rectangle[200];
        this.lazerFired = false;
    }

    /**
     * Determines the coordinates for the lazer according to map
     * 
     * @param m map
     */
    public void buildLazer(Map m) {
        // determine the coordinates of each rectangle in lazer array
        // go through each rectangle in lazer
        for (int i = 0; i < this.lazer.length; i++) {
            // create new rectangle in array based on x and y coordinates
            this.lazer[i] = new Rectangle(super.getX(), super.getY(), WIDTH, HEIGHT);
            // default block tracker, collision rectangle and collision rectangle area
            int tracker = -1;
            Rectangle collide = new Rectangle(0, 0, 0, 0);
            int area = collide.width * collide.height;
            // grab the largest collision rectangle in map
            for (int j = 0; j < m.numBlocks(); j++) {
                // check if block intersects with lazer rectangle
                if (this.lazer[i].intersects(m.getBlock(j).getRectangle())) {
                    // if rectangle intersection has a larger area than collision rectangle, set
                    // collision rectangle equal to intersection rectangle
                    if (this.lazer[i].intersection(m.getBlock(j).getRectangle()).height
                            * this.lazer[i].intersection(m.getBlock(j).getRectangle()).width > area)
                        // replace current collision rectangle with new larger rectangle
                        collide = this.lazer[i].intersection(m.getBlock(j).getRectangle());
                        //set block tracker equal to block position in map
                        tracker = j;
                        //replace colllision area with new collision area
                        area = collide.width*collide.height;
                }
            }
            //adjust subsequent rectangle in lazer array according collision rectangle
            //adjust up and down
            if (collide.height < collide.width) {
                if (super.getY() < m.getBlock(tracker).getY()) {
                    //move up
                    super.setY(super.getY() - collide.height);
                } else {
                    //move down
                    super.setY(super.getY() + collide.height);
                }
                //change y direction
                if (super.getDY() == 1) {
                    super.setDY(-1);
                } else {
                    super.setDY(1);
                }
                //adjust left and right 
            } else if (area != 0) {
                if (super.getX() < m.getBlock(tracker).getX()) {
                    //move left
                    super.setX(super.getX() - collide.width);
                } else {
                    //move right
                    super.setX(super.getX() + collide.width);
                }
                //change x direction
                if (super.getDX() == 1) {
                    super.setDX(-1);
                } else {
                    super.setDX(1);
                }
            }
            //set coordinates for next rectangle in lazer
            super.setY((int) Math.round(super.getY() + 2 * super.getDY() * super.getDirection()[0]));
            super.setX((int) Math.round(super.getX() + 2 * super.getDX() * super.getDirection()[1]));
        }
    }

    /**
     * Draws a lazer ammunition in Interface using Graphics 2D
     * 
     * @param g Graphics
     */
    public void drawLazer(Graphics g) {
        //transform graphics variable into graphics 2D
        Graphics2D g2d = (Graphics2D) g;
        //set lazer color according to usage
        if (lazerFired) {
            //lazer was fired
            g.setColor(Color.GREEN);
        } else {
            //lazer was not fired
            g.setColor(Color.RED);
        }
        //draw lazer on screen
        //go through each rectangle in lazer
        for (int i = 0; i < this.lazer.length; i++) {
            //draw rectangle
            g2d.fill(this.lazer[i]);
        }
    }

    /**
     * Determines if lazer intersects tank
     * 
     * @param t tank
     * @return if lazer intersects tank
     */
    public boolean lazerOnTank(Tank t) {
        //look through each rectangle in lazer
        for (int i = 0; i < this.lazer.length; i++) {
            //check if rectangle intersects tank
            if (this.lazer[i].intersects(t.getRect())) {
                return true;
            }
        }
        //lazer does not intersect tank
        return false;
    }

    /**
     * Set lazer fired to true
     */
    public void lazerFired() {
        this.lazerFired = true;
    }

}
