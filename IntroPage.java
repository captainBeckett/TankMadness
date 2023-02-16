import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

/**
 * Represents the intro page in the Tank Madness game
 * 
 * @author captainBeckett
 */
public class IntroPage {
    // instance variables
    private BufferedImage background;
    private BufferedImage title;
    private BufferedImage tank;
    private BufferedImage play;
    private BufferedImage howToPlay;
    private BufferedImage back;

    private BufferedImage upKey;
    private BufferedImage downKey;
    private BufferedImage leftKey;
    private BufferedImage rightKey;
    private BufferedImage spaceKey;

    private BufferedImage aKey;
    private BufferedImage sKey;
    private BufferedImage dKey;
    private BufferedImage wKey;
    private BufferedImage qKey;

    private boolean mouseOnPlay;
    private boolean mouseOnInstructions;
    private boolean instructions;

    /**
     * Creates a new intro page
     */
    public IntroPage() {
        // load in page components
        try {
            this.background = ImageIO.read(new File("Images/Background.jpg"));
            this.title = ImageIO.read(new File("Images/Title.png"));
            this.tank = ImageIO.read(new File("Images/WarTank.png"));
            this.play = ImageIO.read(new File("Images/PlayButton.png"));
            this.back = ImageIO.read(new File("Images/BackButton.png"));
            this.howToPlay = ImageIO.read(new File("Images/InstructionsButton.png"));

            this.aKey = ImageIO.read(new File("Images/A-Key.png"));
            this.sKey = ImageIO.read(new File("Images/S-Key.png"));
            this.dKey = ImageIO.read(new File("Images/D-Key.png"));
            this.wKey = ImageIO.read(new File("Images/W-Key.png"));
            this.qKey = ImageIO.read(new File("Images/Q-Key.png"));

            this.upKey = ImageIO.read(new File("Images/Up-Key.png"));
            this.downKey = ImageIO.read(new File("Images/Down-Key.png"));
            this.leftKey = ImageIO.read(new File("Images/Left-Key.png"));
            this.rightKey = ImageIO.read(new File("Images/Right-Key.png"));
            this.spaceKey = ImageIO.read(new File("Images/Space-Key.png"));
        } catch (Exception e) {
            // print out error
            e.printStackTrace();
        }
        // set mouse on button initially to false
        this.mouseOnPlay = false;
        this.mouseOnInstructions = false;
        this.instructions = false;
    }

    /**
     * Draws the intro page on Interface using regular Graphics
     * 
     * @param g Graphics
     */
    public void drawIntroPage(Graphics g) {
        g.drawImage(this.background, 0, 0, Interface.WIDTH, Interface.HEIGHT, null);
        g.drawImage(this.title, 100, 50, 600, 100, null);

        //draw intro page
        if (this.instructions == false) {
            g.drawImage(this.tank, 150, 100, 500, 400, null);
            //expand how to play button when mouse hovers over it
            if (this.mouseOnInstructions == false) {
                g.drawImage(this.howToPlay, 100, 450, 200, 75, null);
            } else {
                g.drawImage(this.howToPlay, 90, 440, 220, 95, null);
            }
        } else {
            //draw instructions page
            //draw keys for player one
            g.drawImage(wKey, 240, 200, 40, 40, null);
            g.drawImage(sKey, 240, 250, 40, 40, null);
            g.drawImage(aKey, 190, 250, 40, 40, null);
            g.drawImage(dKey, 290, 250, 40, 40, null);
            g.drawImage(qKey, 170, 180, 40, 40, null);

            //draw instructions for player one
            g.setColor(Color.BLACK);
            g.setFont(new Font("arial", Font.BOLD, 18));
            g.drawString("Player One: ", 490, 180);
            g.setFont(new Font("arial", Font.BOLD, 14));
            g.drawString("To move forward, press the 'w' key.", 400, 200);
            g.drawString("To move backwards, press the 's' key.", 400, 220);
            g.drawString("To rotate counter-clockwise, press the 'a' key.", 400, 240);
            g.drawString("To rotate clockwise, press the 'd' key.", 400, 260);
            g.drawString("To fire ammo, press the 'q' key.", 400, 280);

            //draw keys for player two
            g.drawImage(upKey, 240, 320, 40, 40, null);
            g.drawImage(downKey, 240, 370, 40, 40, null);
            g.drawImage(leftKey, 190, 370, 40, 40, null);
            g.drawImage(rightKey, 290, 370, 40, 40, null);
            g.drawImage(spaceKey, 60, 320, 150, 35, null);

            //draw instructions for player two
            g.setFont(new Font("arial", Font.BOLD, 18));
            g.drawString("Player Two: ", 490, 320);
            g.setFont(new Font("arial", Font.BOLD, 14));
            g.drawString("To move forward, press the 'up' key.", 400, 340);
            g.drawString("To move backwards, press the 'down' key.", 400, 360);
            g.drawString("To rotate counter-clockwise, press the 'left' key.", 400, 380);
            g.drawString("To rotate clockwise, press the 'right' key.", 400, 400);
            g.drawString("To fire ammo, press the 'space' key.", 400, 420);

            //expand back button when mouse hovers over it
            if(this.mouseOnInstructions == false){
                g.drawImage(this.back, 100, 450, 200, 75, null);
            }else{
                g.drawImage(this.back, 90, 440, 220, 95, null);
            }
        }

        // expands play button when mouse hovers over it
        if (this.mouseOnPlay == false) {
            g.drawImage(this.play, 500, 450, 200, 75, null);
        } else {
            g.drawImage(this.play, 490, 440, 220, 95, null);
        }

    }

    /**
     * Returns if the mouse is on play button according to mouse coordinates
     * 
     * @param x x coordinate
     * @param y y coordinate
     * @return if mouse is on play button
     */
    public boolean mouseOnPlayButton(int x, int y) {
        // check if mouse coordinates lie within button coordinates
        if (x >= 500 && x <= 700
                && y >= 450 && y <= 525) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns if the mouse is on instructions button according to mouse coordinates
     * 
     * @param mouse
     */
    public boolean mouseOnInstructionsButton(int x, int y) {
        // check if mouse coordinates lie within button coordinates
        if (x >= 100 && x <= 300
                && y >= 450 && y <= 525) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Sets mouseOnPlay boolean in Interface according to mouse coordinates
     * 
     * @param mouse if mouse is on button
     */
    public void setMouseOnButton(boolean mouse) {
        this.mouseOnPlay = mouse;
    }

    /**
     * Sets mouseOnInstructions boolean in Interface according to mouse coordinates
     * 
     * @param mouse if mouse is on button
     */
    public void setMouseOnInstructions(boolean mouse) {
        this.mouseOnInstructions = mouse;
    }

    /**
     * Sets instructions page according to interface input
     * 
     * @param isCalled is the instructions page being called
     */
    public void setInstructions(boolean isCalled) {
        if (isCalled) {
            if(this.instructions == false){
                this.instructions = true;
            }else{
                this.instructions = false;
            }
        } else {
            this.instructions = false;
        }
    }

}
