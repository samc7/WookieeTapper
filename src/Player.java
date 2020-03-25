//import libraries to play sound
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


/**
*Object class Player
*/
public class Player {
    //int variables for Player
    public int speed, ypos, xpos, level, maxL, maxR, vertical, left, right;
    /**
    *Method that initializes player
    */
    public Player() {
        //set characteristics of Player
        this.speed = 4;
        this.xpos = 540;
        this.ypos = 180;
        this.level = 1;
        this.maxR = 540;
        this.maxL = 220;
        this.left = 0;
        this.right = 0;
        this.vertical = 0;
        
        
    }
    
    
    /**
    *Method that moves Player
    *@param sound check for status on sound effects
    */
    public void move(boolean sound) {
        //if moving up
        if (this.vertical == 1) {
            //set level based on previous level
            if (this.level == 4) {
                this.level = 1;
            }                
            else {
                this.level++;
            }
            
            //play vertical move sound
            if (sound) {
                try {
                    AudioInputStream move = AudioSystem.getAudioInputStream(new File("audio/jump.wav"));
                    Clip moveClip = AudioSystem.getClip();
                    moveClip.open(move);
                    moveClip.start();
                }
                catch (IOException | LineUnavailableException | UnsupportedAudioFileException i){
                    //output where the error is in command line
                    System.out.println("y move sound error");
                }
            }
        }
        //else if moving down
        else if (this.vertical == -1) {
            //set level based on previous level
            if (this.level == 1) {
                this.level = 4;
            }
            else {
                this.level--;
            }
            
            //play vertical moving sound
            if (sound) {
                try {
                    AudioInputStream move = AudioSystem.getAudioInputStream(new File("audio/jump.wav"));
                    Clip moveClip = AudioSystem.getClip();
                    moveClip.open(move);
                    moveClip.start();
                }
                catch (IOException | LineUnavailableException | UnsupportedAudioFileException i){
                    //output where the error is in command line
                    System.out.println("y move sound error");
                }
            }
        }
        
        //if moving left
        if (this.left == 1) {
            //move left unless reaches maximum left position
            if (this.xpos != this.maxL) {
                this.xpos += -1 * this.speed;    
            }
            else if (this.xpos <= this.maxL) {
                this.xpos = maxL;
            }
        }
        //if moving right
        if (this.right == 1) {
            //move right unless reaches maximum right position
            if (this.xpos != this.maxR) {
                this.xpos += 1 * this.speed;    
            }
            else if (this.xpos >= this.maxR) {
                this.xpos = maxR;
            }
        }
        
        //if moving any direction vertically
        if (this.vertical != 0) {
            //set new x and y position, and max left and right positions based on new level
            switch (this.level) {
                case 1:
                    this.xpos = 540;
                    this.ypos = 180;
                    this.maxR = 540;
                    this.maxL = 220;
                    break;
                case 2:
                    this.xpos = 590;
                    this.ypos = 300;
                    this.maxR = 590;
                    this.maxL = 170;
                    break;
                case 3:
                    this.xpos = 640;
                    this.ypos = 420;
                    this.maxR = 640;
                    this.maxL = 120;
                    break;
                case 4:
                    this.xpos = 690;
                    this.ypos = 540;
                    this.maxR = 690;
                    this.maxL = 70;
                    break;
            }
            //stop moving vertically
            this.vertical = 0;
        }
    }
}