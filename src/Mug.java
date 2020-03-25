//imports to run sound files
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


/**
*Object class Mug
*/
public class Mug {
    //int variables for Mug
    public int xpos, ypos, speed, level, maxL, maxR, direction, powerup;
    
    /**
    *Method that initializes a mug
    *@param xpos the starting x position of the mug
    *@param ypos the starting y position of the mug
    *@param level the mug's table number
    *@param direction the direction in which the mug is traveling
    *@param speed how fast the mug is traveling
    *@param powerup the power up value the mug holds
    *@param sound check for status on sound effects
    */
    public Mug(int xpos, int ypos, int level, int direction, int speed, int powerup, boolean sound) {
        //initialize each parameter into Mug characteristic
        this.xpos = xpos;
        this.ypos = ypos - 35;
        this.speed = speed;
        this.direction = direction;
        this.level = level;
        this.powerup = powerup;
        
        //set maximum left and right horizontal position based on table number
        switch (this.level) {
            case 1:
                this.maxR = 540;
                this.maxL = 220;
                break;
            case 2:
                this.maxR = 590;
                this.maxL = 170;
                break;
            case 3:
                this.maxR = 640;
                this.maxL = 120;
                break;
            case 4:
                this.maxR = 690;
                this.maxL = 70;
                break;
        }
        
        //play sound for shooting a Mug
        if (sound) {
            try {
                AudioInputStream shoot = AudioSystem.getAudioInputStream(new File("audio/shoot.wav"));
                Clip shootClip = AudioSystem.getClip();
                shootClip.open(shoot);
                shootClip.start();
            }
            catch (IOException | LineUnavailableException | UnsupportedAudioFileException i){
                //output error message
                System.out.println("shoot mug sound error");
            }
        }
    }
    
    
    /**
    *Method that moves the Mug horizontally when called
    */
    public void move() {
        //move horizontally, multiply direction by speed
        this.xpos += this.direction * this.speed;
    }
}
