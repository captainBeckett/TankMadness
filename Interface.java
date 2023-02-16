import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import javax.swing.Timer;
import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author
 */
public class Interface extends JComponent implements ActionListener {

    // Height and Width of our game
    static final int WIDTH = 800;
    static final int HEIGHT = 600;

    // Title of the window
    String title = "Tank Madness";

    // sets the framerate and delay for our game
    // this calculates the number of milliseconds per frame
    // you just need to select an appropriate framerate
    int desiredFPS = 60;
    int desiredTime = Math.round((1000 / desiredFPS));

    // timer used to run the game loop
    // this is what keeps our time running smoothly :)
    Timer gameTimer;

    // YOUR GAME VARIABLES WOULD GO HERE
    private IntroPage introPage = new IntroPage();
    private boolean intro = true;

    private ArrayList<Tank> tanks = new ArrayList<>();
    private Map m = new Map("Textfiles/MapCoordinates.txt");
    private int numPlayers = 2;

    private ArrayList<PowerUp> powerups = new ArrayList<>();
    private double powerUpTimer = 15 * desiredFPS;

    private int playerOneScore = 0;
    private int rightCounterTankOne = 0;
    private boolean tankOneDown = false;
    private boolean rotateTankOneRight = false;
    private boolean rotateTankOneLeft = false;
    private boolean tankOneForward = false;
    private boolean tankOneBackward = false;
    private boolean lazerOnTankOne = false;
    private boolean tankOneLazerPower = false;

    private int playerTwoScore = 0;
    private int rightCounterTankTwo = 0;
    private boolean tankTwoDown = false;
    private boolean rotateTankTwoRight = false;
    private boolean rotateTankTwoLeft = false;
    private boolean tankTwoForward = false;
    private boolean tankTwoBackward = false;
    private boolean lazerOnTankTwo = false;
    private boolean tankTwoLazerPower = false;

    private int mouseX;
    private int mouseY;

    // GAME VARIABLES END HERE

    // Constructor to create the Frame and place the panel in
    // You will learn more about this in Grade 12 :)
    public Interface() {
        // creates a windows to show my game
        JFrame frame = new JFrame(title);

        // sets the size of my game
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // adds the game to the window
        frame.add(this);

        // sets some options and size of the window automatically
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        // shows the window to the user
        frame.setVisible(true);

        // add listeners for keyboard and mouse
        frame.addKeyListener(new Keyboard());
        Mouse m = new Mouse();
        this.addMouseMotionListener(m);
        this.addMouseWheelListener(m);
        this.addMouseListener(m);

        // Set things up for the game at startup

        setup();

        // Start the game loop
        gameTimer = new Timer(desiredTime, this);
        gameTimer.setRepeats(true);
        gameTimer.start();
    }

    // drawing of the game happens in here
    // we use the Graphics object, g, to perform the drawing
    // NOTE: This is already double buffered!(helps with framerate/speed)
    @Override
    public void paintComponent(Graphics g) {
        // always clear the screen first!
        g.clearRect(0, 0, WIDTH, HEIGHT);

        // GAME DRAWING GOES HERE
        // always clear the screen first!
        g.clearRect(0, 0, WIDTH, HEIGHT);

        // GAME DRAWING GOES HERE

        // draw intro page before game begins
        if (intro) {
            introPage.drawIntroPage(g);
        } else {
            // draw game graphics
            // draw map of blocks on screen
            for (int i = 0; i < m.numBlocks(); i++) {
                this.m.getBlock(i).drawBlock(g);
            }

            // draw player score labels
            g.setFont(new Font("arial", Font.BOLD, 20));
            g.drawString("Player 1: " + playerOneScore, 10, 18);
            g.drawString("Player 2: " + playerTwoScore, 690, 18);

            // look through each tank 
            for (int i = 0; i < tanks.size(); i++) {
                // draw tank graphics
                this.tanks.get(i).drawTank(g);
                // draw each tank ammunition
                for (int j = 0; j < this.tanks.get(i).getAmmo().size(); j++) {
                    this.tanks.get(i).getAmmo().get(j).drawAmmo(g);
                }
                // draw lazer ammo if tank has powerup
                if (this.tanks.get(i).hasLazerPower()) {
                    // construct lazer
                    this.tanks.get(i).getLazer().buildLazer(m);
                    // draw lazer
                    this.tanks.get(i).getLazer().drawLazer(g);
                }
            }

            //draw tank labels underneath them
            g.setColor(Color.BLACK);
            g.drawString("Player 1", this.tanks.get(0).getX() - 20, this.tanks.get(0).getY() + 70);
            g.drawString("Player 2", this.tanks.get(1).getX() - 20, this.tanks.get(1).getY() + 70);

            // draw each powerup on screen
            for (int i = 0; i < this.powerups.size(); i++) {
                this.powerups.get(i).drawPowerUp(g);
            }
        }

        // GAME DRAWING ENDS HERE
    }

    // This method is used to do any pre-setup you might need to do
    // This is run before the game loop begins!
    public void setup() {
        // Any of your pre setup before the loop starts should go here

        // randomly generate tank starting positions on board
        for (int i = 0; i < this.numPlayers; i++) {
            // make sure starting position is within map
            int lowest = 20;
            int highestX = WIDTH - 100;
            int highestY = HEIGHT - 100;
            this.tanks.add(new Tank((int) (Math.random() * (highestX - lowest + 1) + lowest),
                    (int) (Math.random() * (highestY - lowest + 1) + lowest)));
        }

        // randomly generate first powerup's starting position
        // make sure starting position is within map
        int lowest = 20;
        int highestX = WIDTH - 100;
        int highestY = HEIGHT - 100;
        this.powerups.add(new PowerUp((int) (Math.random() * (highestX - lowest + 1) + lowest),
                (int) (Math.random() * (highestY - lowest + 1) + lowest), m));
    }

    // The main game loop
    // In here is where all the logic for my game will go
    public void loop() {

        // rotate tank 1 according to right counter and key pressed
        if (this.rotateTankOneRight) {
            // rotate right
            rightCounterTankOne++;
            this.tanks.get(0).setRotation(rightCounterTankOne);
        } else if (this.rotateTankOneLeft) {
            // rotate left
            rightCounterTankOne--;
            this.tanks.get(0).setRotation(rightCounterTankOne);
        }

        // rotate tank 2 according to right counter and key pressed
        if (rotateTankTwoRight) {
            // rotate right
            this.rightCounterTankTwo++;
            this.tanks.get(1).setRotation(this.rightCounterTankTwo);
        } else if (rotateTankTwoLeft) {
            // rotate left
            this.rightCounterTankTwo--;
            this.tanks.get(1).setRotation(this.rightCounterTankTwo);
        }

        // move tank one forward according to key pressed
        if (tankOneForward) {
            // move forward
            this.tanks.get(0).moveForward(true);
        } else {
            // stop forward movement
            this.tanks.get(0).moveForward(false);
        }

        // move tank one backwards according to key pressed
        if (tankOneBackward) {
            // move backwards
            this.tanks.get(0).moveBackward(true);
        } else {
            // stop backwards movement
            this.tanks.get(0).moveBackward(false);
        }

        // move tank two forward according to key pressed
        if (tankTwoForward) {
            // move forward
            this.tanks.get(1).moveForward(true);
        } else {
            // stop forward movement
            this.tanks.get(1).moveForward(false);
        }

        // move tank two backwards according to key pressed
        if (tankTwoBackward) {
            // move backwards
            this.tanks.get(1).moveBackward(true);
        } else {
            // stop backwards movement
            this.tanks.get(1).moveBackward(false);
        }

        // move normal ammo on screen
        // look through each tank
        for (int i = 0; i < this.tanks.size(); i++) {
            // look through each tank's ammo cartridge
            for (int j = 0; j < this.tanks.get(i).getAmmo().size(); j++) {
                // move each normal ammo in tank cartridge
                this.tanks.get(i).getAmmo().get(j).moveAmmo(m);
                // check to see if live ammunition has expired
                if (this.tanks.get(i).getAmmo().get(j).stillLive() == false) {
                    // remove expired ammunitions
                    this.tanks.get(i).getAmmo().remove(j);
                    // check if ammunition has collided with tank one
                } else if (this.tanks.get(i).getAmmo().get(j).getRectangle().intersects(this.tanks.get(0).getRect())) {
                    // tank one is down
                    this.tankOneDown = true;
                    // remove depleted ammunition from screen
                    this.tanks.get(i).getAmmo().remove(j);
                    //check if ammunition has collided with tank two
                } else if (this.tanks.get(i).getAmmo().get(j).getRectangle().intersects(this.tanks.get(1).getRect())) {
                    // tank two is down
                    this.tankTwoDown = true;
                    // remove depleted ammunition from screen
                    this.tanks.get(i).getAmmo().remove(j);
                }
            }
        }

        // check if lazer ammo intersects any tanks
        // look through each tank
        for(int i = 0; i < this.tanks.size(); i++){
            // check if tank lazer intersects either tank
            if (this.tanks.get(i).hasLazerPower()) {
                if (this.tanks.get(i).getLazer().lazerOnTank(this.tanks.get(0))) {
                    // intersects tank one
                    this.lazerOnTankOne = true;
                } else if (this.tanks.get(i).getLazer().lazerOnTank(this.tanks.get(1))) {
                    // intersects tank two
                    this.lazerOnTankTwo = true;
                } else {
                    // doesn't intersect either tank
                    this.lazerOnTankOne = false;
                    this.lazerOnTankTwo = false;
                }
            }
        }

        // check if tank has collided with any powerups
        // look through each powerup
        for (int i = 0; i < this.powerups.size(); i++) {
            // check if tank one collided with powerup
            if (!this.powerups.isEmpty() && this.tanks.get(0).getRect().intersects(this.powerups.get(i).getRect())) {
                // tank one collided with powerup
                // tank one has lazer powerup
                this.tankOneLazerPower = true;
                // remove powerup from screen
                this.powerups.remove(i);
                break;
                //check if tank two collided with powerup
            } else if (!this.powerups.isEmpty()
                    && this.tanks.get(1).getRect().intersects(this.powerups.get(i).getRect())) {
                // tank two collided with powerup
                // tank two has lazer powerup
                this.tankTwoLazerPower = true;
                // remove powerup from screen
                this.powerups.remove(i);
                break;
            }
        }

        // use collision detection to keep tanks inside map
        for (int i = 0; i < this.tanks.size(); i++) {
            // adjust each tank position inside map
            this.tanks.get(i).adjustCollision(m);
        }

        // add lazer power to tank one if it collided with powerup
        if (tankOneLazerPower) {
            this.tanks.get(0).setLazerPower(tankOneLazerPower);
        }

        // add lazer power to tank two if it collided with powerup
        if (tankTwoLazerPower) {
            this.tanks.get(1).setLazerPower(tankTwoLazerPower);
        }

        // if either tank is down, begin tank explosion animation
        this.tanks.get(0).bulletCollision(this.tankOneDown);
        this.tanks.get(1).bulletCollision(this.tankTwoDown);

        // reset tank settings to default after explosion animation is complete
        if (this.tanks.get(0).isTerminated()) {
            this.tanks.get(0).reset();
            this.tankOneDown = false;
            // increase player two score by one
            this.playerTwoScore++;
        } else if (this.tanks.get(1).isTerminated()) {
            this.tanks.get(1).reset();
            this.tankTwoDown = false;
            // increase player one score by one
            this.playerOneScore++;
        }

        // introduce a powerup to game screen every 15 seconds
        if (this.powerUpTimer <= 0) {
            // randomize powerup starting position
            int lowest = 20;
            int highestX = WIDTH - 100;
            int highestY = HEIGHT - 100;
            this.powerups.add(new PowerUp((int) (Math.random() * (highestX - lowest + 1) + lowest),
                    (int) (Math.random() * (highestY - lowest + 1) + lowest), m));
            // reset timer to 15 seconds
            this.powerUpTimer = 15 * desiredFPS;
        } else if(intro == false){
            // countdown
            this.powerUpTimer--;
        }

    }

    // Used to implement any of the Mouse Actions
    private class Mouse extends MouseAdapter {

        // if a mouse button has been pressed down
        @Override
        public void mousePressed(MouseEvent e) {
            // get coordinates of the mouse
            mouseX = e.getX();
            mouseY = e.getY();

            if(intro){
                // begin game if mouse clicks play button on intro page
                if (introPage.mouseOnPlayButton(mouseX, mouseY)) {
                    intro = false;
                }else if(introPage.mouseOnInstructionsButton(mouseX, mouseY)){
                    introPage.setInstructions(true);
                }
            }
        
        }

        // if a mouse button has been released
        @Override
        public void mouseReleased(MouseEvent e) {

        }

        // if the scroll wheel has been moved
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {

        }

        // if the mouse has moved positions
        @Override
        public void mouseMoved(MouseEvent e) {
            // get mouse coordinates
            mouseX = e.getX();
            mouseY = e.getY();

            // expand play button icon whenever mouse hovers over it on intro page
            if (intro) {
                introPage.setMouseOnButton(introPage.mouseOnPlayButton(mouseX, mouseY));
                introPage.setMouseOnInstructions(introPage.mouseOnInstructionsButton(mouseX, mouseY));
            }
        }
    }

    // Used to implements any of the Keyboard Actions
    private class Keyboard extends KeyAdapter {

        // if a key has been pressed down
        @Override
        public void keyPressed(KeyEvent e) {
            // get the key code
            int key = e.getKeyCode();

            // make sure game has begun
            if (intro == false) {
                // use keys a,s,d,w for tank 1 movement and rotation
                if (key == KeyEvent.VK_D) {
                    // rotate tank 1 clockwise if 'd' key is pressed
                    rotateTankOneRight = true;
                } else if (key == KeyEvent.VK_A) {
                    // rotate tank 1 counter-clockwise if 'a' key is pressed
                    rotateTankOneLeft = true;
                } else if (key == KeyEvent.VK_W) {
                    // move tank 1 forward if 'w' key is pressed
                    tankOneForward = true;
                } else if (key == KeyEvent.VK_S) {
                    // move tank 1 backwards if 's' key is pressed
                    tankOneBackward = true;
                } else if (key == KeyEvent.VK_Q) {
                    // create an instance of tank 1 ammo if 'q' key is pressed
                    // create normal ammo if tank one doesn't have powerup
                    if (tanks.get(0).getAmmo().size() < 5 && tankOneLazerPower == false) {
                        // determine normal ammo starting position and direction based on tank one location and degree turn
                        int x2 = (int) Math.round(32 * tanks.get(0).rotationDirection()[1]);
                        int y2 = (int) Math.round(32 * tanks.get(0).rotationDirection()[0]);
                        // add normal ammo directly in front of tank one and aimed in the same direction
                        tanks.get(0).addAmmo(new NormalAmmo(tanks.get(0).getX() + tanks.get(0).WIDTH / 2 + x2,
                                tanks.get(0).getY() + tanks.get(0).HEIGHT / 2 + y2, tanks.get(0).getDegreeTurn()));
                        // fire lazer ammo if tank one has powerup
                    } else if (tankOneLazerPower) {
                        // animate lazer fired
                        tanks.get(0).getLazer().lazerFired();
                        // check if lazer fired intersects with either tank
                        if (lazerOnTankOne) {
                            // tank one is down
                            tankOneDown = true;
                        } else if (lazerOnTankTwo) {
                            // tank two is down
                            tankTwoDown = true;
                        }
                        // deactivate lazer power for tank one
                        tankOneLazerPower = false;
                        tanks.get(0).setLazerPower(false);
                    }
                }


                // use keys 'left', 'right', 'down', 'up' for tank 2 movement and rotation
                if (key == KeyEvent.VK_RIGHT) {
                    // rotate tank 2 clockwise if 'right' key is pressed
                    rotateTankTwoRight = true;
                } else if (key == KeyEvent.VK_LEFT) {
                    // rotate tank 2 counter-clockwise if 'left' key is pressed
                    rotateTankTwoLeft = true;
                } else if (key == KeyEvent.VK_UP) {
                    // move tank 2 forward if 'up' key is pressed
                    tankTwoForward = true;
                } else if (key == KeyEvent.VK_DOWN) {
                    // move tank 2 backwards if 'down' key is pressed
                    tankTwoBackward = true;
                } else if (key == KeyEvent.VK_SPACE) {
                    // create an instance of tank 2 ammo if 'space' key is pressed
                    // create normal ammo if tank two doesn't have powerup
                    if (tanks.get(1).getAmmo().size() < 5 && tankTwoLazerPower == false) {
                        // determine normal ammo starting position and direction based on tank one location and degree turn
                        int x2 = (int) Math.round(32 * tanks.get(1).rotationDirection()[1]);
                        int y2 = (int) Math.round(32 * tanks.get(1).rotationDirection()[0]);
                        // add normal ammo directly in front of tank two and aimed in the same direction
                        tanks.get(1).addAmmo(new NormalAmmo(tanks.get(1).getX() + tanks.get(1).WIDTH / 2 + x2,
                                tanks.get(1).getY() + tanks.get(1).HEIGHT / 2 + y2, tanks.get(1).getDegreeTurn()));
                        // fire lazer ammo if tank two has powerup
                    } else if (tankTwoLazerPower) {
                        // animate lazer fired
                        tanks.get(1).getLazer().lazerFired();
                        // check if lazer fired intersect with either tank
                        if (lazerOnTankOne) {
                            // tank one is down
                            tankOneDown = true;
                        } else if (lazerOnTankTwo) {
                            // tank two is down
                            tankTwoDown = true;
                        }
                        // deactivate lazer power for tank two
                        tankTwoLazerPower = false;
                        tanks.get(1).setLazerPower(false);
                    }
                }
            }
        }

        // if a key has been released
        @Override
        public void keyReleased(KeyEvent e) {
            // get the key code
            int key = e.getKeyCode();
            
            // make sure game has begun
            if (intro == false) {
                // disable all tank 1 movements when keys are released
                if (key == KeyEvent.VK_D) {
                    rotateTankOneRight = false;
                } else if (key == KeyEvent.VK_A) {
                    rotateTankOneLeft = false;
                } else if (key == KeyEvent.VK_W) {
                    tankOneForward = false;
                } else if (key == KeyEvent.VK_S) {
                    tankOneBackward = false;
                }else if(key == KeyEvent.VK_Q){
                    tankOneLazerPower = false;
                }

                // disable all tank 2 movements when keys are released
                if (key == KeyEvent.VK_RIGHT) {
                    rotateTankTwoRight = false;
                } else if (key == KeyEvent.VK_LEFT) {
                    rotateTankTwoLeft = false;
                } else if (key == KeyEvent.VK_UP) {
                    tankTwoForward = false;
                } else if (key == KeyEvent.VK_DOWN) {
                    tankTwoBackward = false;
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        loop();
        repaint();
    }

}
