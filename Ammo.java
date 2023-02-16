/**
 * Represents a default ammo in the Tank Madness Game
 * 
 * @author captainBeckett
 */
public class Ammo {
    // instance variables
    private int y;
    private int x;
    private int dy;
    private int dx;
    private double[] initialDirection;

    /**
     * Creates a new ammo with the x coordinate, y coordinate and the initial degree
     * turn of the corresponding tank
     * 
     * @param x          the x coordinate
     * @param y          the y coordinate
     * @param degreeTurn the corresponding tank's degree turn
     */
    public Ammo(int x, int y, int degreeTurn) {
        // initialize instance variables
        this.x = x;
        this.y = y;
        this.dy = 1;
        this.dx = 1;
        this.initialDirection = getInitialDirection(degreeTurn);
    }

    /**
     * Returns an array of perpendicular lines that guide the ammo in the direction
     * it was fired from
     * 
     * @param degreeTurn the initial rotational position of the corresponding tank
     * @return the direction the ammo must travel
     */
    public double[] getInitialDirection(int degreeTurn) {
        double[] direction = new double[2];
        // update array depending on the rotational position of the tank
        if (degreeTurn % 360 >= 0 && degreeTurn % 360 <= 90) {
            // tank is in quadrant 1
            direction[0] = -Math.sin(Math.PI / 2 - Math.toRadians(degreeTurn));
            direction[1] = Math.sin(Math.toRadians(degreeTurn));
        } else if (degreeTurn % 360 > 90 && degreeTurn % 360 <= 180) {
            // tank is in quadrant 4
            direction[0] = Math.sin(Math.toRadians(degreeTurn) - Math.PI / 2);
            direction[1] = Math.sin(Math.PI / 2 - (Math.toRadians(degreeTurn) - Math.PI / 2));
        } else if (degreeTurn % 360 > 180 && degreeTurn % 360 <= 270) {
            // tank is in quadrant 3
            direction[0] = Math.sin(Math.PI * 3 / 2 - Math.toRadians(degreeTurn));
            direction[1] = -Math.sin(Math.PI / 2 - (Math.PI * 3 / 2 - Math.toRadians(degreeTurn)));
        } else if (degreeTurn % 360 > 270 && degreeTurn % 360 < 360) {
            // tank is in quadrant 2
            direction[0] = -Math.sin(Math.PI / 2 - (Math.PI * 2 - Math.toRadians(degreeTurn)));
            direction[1] = -Math.sin(Math.PI * 2 - Math.toRadians(degreeTurn));
        }
        // return guiding lines
        return direction;
    }

    /**
     * Returns the current x coordinate of the ammo
     * 
     * @return x coordinate
     */
    public int getX() {
        return this.x;
    }

    /**
     * Returns the current y coordinate of the ammo
     * 
     * @return y coordinate
     */
    public int getY() {
        return this.y;
    }

    /**
     * Changes the current x position of ammo
     * 
     * @param x new x position
     */
    public void setX(int x){
        this.x = x;
    }

    /**
     * Changes the current y position of ammo
     * 
     * @param y new y position
     */
    public void setY(int y){
        this.y = y;
    }

    /**
     * Returns the dx value of ammo
     * 
     * @return dx
     */
    public int getDX(){
        return this.dx;
    }

    /**
     * Returns the dy value of ammo
     * 
     * @return dy
     */
    public int getDY(){
        return this.dy;
    }

    /**
     * Changes the current dx value of ammo
     * 
     * @param x dx
     */
    public void setDX(int x){
        this.dx = x;
    }

    /**
     * Changes the curent dy value of ammo
     * 
     * @param y dy
     */
    public void setDY(int y){
        this.dy = y;
    }
    /**
     * Returns the initial direction of the ammo
     * 
     * @return initial direction
     */
    public double[] getDirection(){
        return this.initialDirection;
    }

}
