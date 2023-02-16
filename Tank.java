import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 * Represents a tank within the Tank Madness Game
 * 
 * @author captainBeckett
 */
public class Tank {
    // instance variables
    private int x;
    private int y;
    public final int WIDTH = 35;
    public final int HEIGHT = 50;
    private int degreeTurn;

    private ArrayList<NormalAmmo> ammo;

    private BufferedImage tank;
    private BufferedImage explosion1;
    private BufferedImage explosion2;
    private BufferedImage explosion3;
    private BufferedImage explosion4;
    private BufferedImage explosion5;
    private BufferedImage explosion6;
    private BufferedImage explosion7;

    private Rectangle t;
    private boolean stillLive;
    private double timer;
    private int desiredFPS;
    private boolean terminate;
    private double speed;

    private boolean hasLazerPower;
    private LazerAmmo lazerPower;

    /**
     * Creates a new tank with the x coordinate and y coordinate
     * 
     * @param x x coordinate
     * @param y y coordinate
     */
    public Tank(int x, int y) {
        // initialize instance variables
        this.x = x;
        this.y = y;
        // load in buffered images of tank and explosions
        try {
            this.tank = ImageIO.read(new File("Images/RedTank.png"));
            this.explosion1 = ImageIO.read(new File("Images/Explosion1.png"));
            this.explosion2 = ImageIO.read(new File("Images/Explosion2.png"));
            this.explosion3 = ImageIO.read(new File("Images/Explosion3.png"));
            this.explosion4 = ImageIO.read(new File("Images/Explosion4.png"));
            this.explosion5 = ImageIO.read(new File("Images/Explosion5.png"));
            this.explosion6 = ImageIO.read(new File("Images/Explosion6.png"));
            this.explosion7 = ImageIO.read(new File("Images/Explosion7.png"));
        } catch (Exception e) {
            // print out the error
            e.printStackTrace();
        }
        this.ammo = new ArrayList<>();
        this.t = new Rectangle(this.x, this.y, WIDTH, HEIGHT);
        this.stillLive = true;
        this.desiredFPS = 60;
        this.timer = 0.7 * this.desiredFPS;
        this.terminate = false;
        this.speed = 4;
        this.hasLazerPower = false;
    }

    /**
     * Draws an instance of the tank on screen using Graphics 2D
     * 
     * @param g Graphics
     */
    public void drawTank(Graphics g) {
        //transform graphics variable into Graphics 2D
        Graphics2D g2d = (Graphics2D) g;

        //always draw tank on screen as long as timer is running
        if (this.timer > 0) {
            //rotate tank image on screen using Graphics 2D transformations
            g2d.translate(t.x + WIDTH / 2, t.y + HEIGHT / 2);
            g2d.rotate(Math.toRadians(this.degreeTurn));
            //draw tank image at angle of rotation
            g.drawImage(this.tank, -WIDTH / 2, -HEIGHT / 2, WIDTH, HEIGHT, null);
            g2d.rotate(Math.toRadians(-this.degreeTurn));
            g2d.translate(-t.x - WIDTH / 2, -t.y - HEIGHT / 2);
        }

        //draw tank explosion animation according to timer
        if (this.stillLive == false) {
            if (this.timer < 0.7 * this.desiredFPS && this.timer > 0.6 * this.desiredFPS) {
                g.drawImage(explosion1, t.x, t.y, WIDTH, HEIGHT, null);
            } else if (this.timer <= 0.6 * this.desiredFPS && this.timer > 0.5 * this.desiredFPS) {
                g.drawImage(explosion2, t.x, t.y, WIDTH, HEIGHT, null);
            } else if (this.timer <= 0.5 * this.desiredFPS && this.timer > 0.4 * this.desiredFPS) {
                g.drawImage(explosion3, t.x, t.y, WIDTH, HEIGHT, null);
            } else if (this.timer <= 0.4 * this.desiredFPS && this.timer > 0.3 * this.desiredFPS) {
                g.drawImage(explosion4, t.x, t.y, WIDTH, HEIGHT, null);
            } else if (this.timer <= 0.3 * this.desiredFPS && this.timer > 0.2 * this.desiredFPS) {
                g.drawImage(explosion5, t.x, t.y, WIDTH, HEIGHT, null);
            } else if (this.timer <= 0.2 * this.desiredFPS && this.timer > 0.1 * this.desiredFPS) {
                g.drawImage(explosion6, t.x, t.y, WIDTH, HEIGHT, null);
            } else if (this.timer <= 0.1 * this.desiredFPS) {
                g.drawImage(explosion7, t.x, t.y, WIDTH, HEIGHT, null);
            }
        }

    }

    /**
     * Sets the rotational degree value of tank according to the number of times user clicks key
     * 
     * @param numTurns number of key clicks made by user
     */
    public void setRotation(int numTurns) {
        //adjust if tank has rotated over 360 degree
        if (numTurns < 0) {
            numTurns = 36 + (numTurns % 36);
        }
        //turn every user key clicked into a 10 degree turn clockwise
        this.degreeTurn = 10 * numTurns;
    }

    /**
     * Returns an array of perpendicular lines that guide the tank in the direction it's facing
     * 
     * @return the direction the tank must travel
     */
    public double[] rotationDirection() {
        double[] direction = new double[2];
        // update array depending on the rotational position of the tank
        if (this.degreeTurn % 360 >= 0 && this.degreeTurn % 360 <= 90) {
            //tank is in quadrant 1
            direction[0] = -Math.sin(Math.PI / 2 - Math.toRadians(this.degreeTurn));
            direction[1] = Math.sin(Math.toRadians(this.degreeTurn));
        } else if (this.degreeTurn % 360 > 90 && this.degreeTurn % 360 <= 180) {
            //tank is in quadrant 4
            direction[0] = Math.sin(Math.toRadians(this.degreeTurn) - Math.PI / 2);
            direction[1] = Math.sin(Math.PI / 2 - (Math.toRadians(this.degreeTurn) - Math.PI / 2));
        } else if (this.degreeTurn % 360 > 180 && this.degreeTurn % 360 <= 270) {
            //tank is in quadrant 3
            direction[0] = Math.sin(Math.PI * 3 / 2 - Math.toRadians(this.degreeTurn));
            direction[1] = -Math.sin(Math.PI / 2 - (Math.PI * 3 / 2 - Math.toRadians(this.degreeTurn)));
        } else if (this.degreeTurn % 360 > 270 && this.degreeTurn % 360 < 360) {
            //tank is in quadrant 2
            direction[0] = -Math.sin(Math.PI / 2 - (Math.PI * 2 - Math.toRadians(this.degreeTurn)));
            direction[1] = -Math.sin(Math.PI * 2 - Math.toRadians(this.degreeTurn));
        }
        return direction;
    }

    /**
     * Moves tank forward according to user input in Interface
     * 
     * @param moveForward Interface requested tank to move forward
     */
    public void moveForward(boolean moveForward) {
        if (moveForward) {
            // forward movement in all four quadrants
            t.y += this.speed * rotationDirection()[0];
            t.x += this.speed * rotationDirection()[1];
        }
    }

    /**
     * Moves tank backwards according to user input in Interface
     * 
     * @param moveBackward Interface requested tank to move backwards
     */
    public void moveBackward(boolean moveBackward) {
        if (moveBackward) {
            // backward movement in all four quadrants
            t.y -= this.speed * rotationDirection()[0];
            t.x -= this.speed * rotationDirection()[1];
        }
    }
    
    /**
     * Tank collision detection with blocks in map
     * 
     * @param m map
     */
    public void adjustCollision(Map m) {
        //check if tank intersects with each block in the map
        for (int i = 0; i < m.numBlocks(); i++) {
            //tank intersects block
            if (t.intersects(m.getBlock(i).getRectangle())) {
                //get the collision rectangle
                Rectangle collide = t.intersection(m.getBlock(i).getRectangle());
                //adjust tank coordinates according to collision rectangle height and width
                if (collide.height < collide.width) {
                    // fix up or down
                    if (t.y < m.getBlock(i).getY()) {
                        //on top
                        t.y -= collide.height;
                    } else {
                        //underneath
                        t.y += collide.height;
                    }
                } else {
                    // fix left or right
                    if (t.x < m.getBlock(i).getX()) {
                        //left
                        t.x -= collide.width;
                    } else {
                        //right
                        t.x += collide.width;
                    }
                }
            }
        }
    }

    /**
     * Activate tank explosion timer after tank collision with ammo
     * 
     * @param collide  has the tank collided with ammo
     */
    public void bulletCollision(boolean collide) {
        //tank collided with ammo
        if (collide) {
            //tank is no longer alive
            this.stillLive = false;
            if (this.timer > 0) {
                //countdown
                this.timer--;
            } else {
                //if timer for tank explosion is expired, terminate tank
                this.terminate = true;
            }
        }
    }

    /**
     * Resets tank variables after termination
     */
    public void reset(){
        this.terminate = false;
        this.stillLive = true;
        this.timer = 0.7 * desiredFPS;
        int lowest = 20;
        int highestX = Interface.WIDTH-100;
        int highestY = Interface.HEIGHT-100;
        t.x = (int)(Math.random()*(highestX - lowest + 1) + lowest);
        t.y = (int)(Math.random()*(highestY - lowest + 1) + lowest);
    }
    
    /**
     * Returns the x coordinate of the tank
     * 
     * @return x coordinate
     */
    public int getX() {
        return t.x;
    }

    /**
     * Returns the y coordinate of the tank
     * 
     * @return y coordinate
     */
    public int getY() {
        return t.y;
    }

    /**
     * Returns the degree turn of the tank
     * 
     * @return degree turn
     */
    public int getDegreeTurn() {
        return this.degreeTurn;
    }

    /**
     * Adds ammunition to tank cartridge
     * 
     * @param a ammunition
     */
    public void addAmmo(NormalAmmo a) {
        this.ammo.add(a);
    }

    /**
     * Removes ammunition from tank cartridge
     * 
     * @param a ammunition
     */
    public void removeAmmo(NormalAmmo a) {
        this.ammo.remove(a);
    }

    /**
     * Returns tank cartridge of ammo
     * 
     * @return arraylist of ammo
     */
    public ArrayList<NormalAmmo> getAmmo(){
        return this.ammo;
    }

    /**
     * Returns tank rectangle coordinates
     * 
     * @return tank rectangle
     */
    public Rectangle getRect() {
        return this.t;
    }

    /**
     * Returns whether the tank is terminated or not
     * 
     * @return is tank terminated
     */
    public boolean isTerminated() {
        return this.terminate;
    }

    /**
     * Returns whether the tank has the lazer power or not
     * 
     * @return if tank has lazer power
     */
    public boolean hasLazerPower(){
        return this.hasLazerPower;
    }

    /**
     * Creates lazer ammo for tank if it has the lazer power
     * 
     * @param power if tank has lazer power
     */
    public void setLazerPower(boolean power){
        this.hasLazerPower = power;
        if(power){
            //create a new instance of a lazer ammo based on tank current direction and location
            int x2 = (int) Math.round(50 * rotationDirection()[1]);
            int y2 = (int) Math.round(50 * rotationDirection()[0]);
            this.lazerPower = new LazerAmmo(t.x + WIDTH / 2 + x2,
            t.y + HEIGHT / 2 + y2, this.degreeTurn);
        }
    }

    /**
     * Returns the tank's lazer ammunition
     * 
     * @return tank lazer ammunition
     */
    public LazerAmmo getLazer(){
        return this.lazerPower;
    }
}
