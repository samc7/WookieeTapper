
/**
*Object class Wookiee
*/
public class Wookiee {
    //int variables for Wookiee
    public int ypos, xpos, direction, maxR, maxL, knockback, level, time, fallback, count;
    //boolean variables for Wookiee
    public boolean drinking, push, exist, countstart;
    
    
    /**
    *Method that initializes Wookiee
    *@param level the table number the Wookiee is on
    */
    public Wookiee(int level) {
        //initialize each characteristic of the Wookiee
        this.direction = 1;
        this.level = level;
        this.drinking = false;
        this.push = false;
        this.count = 720;
        this.exist = true;
        this.countstart = false;
        
        //set y position, max horizontal left and right positions based on talbe number parameter
        switch (this.level) {
            case 1:
                this.maxR = 500;
                this.maxL = 220;
                this.ypos = 122;
                break;
            case 2:
                this.maxR = 550;
                this.maxL = 170;
                this.ypos = 242;
                break;
            case 3:
                this.maxR = 600;
                this.maxL = 120;
                this.ypos = 362;
                break;
            case 4:
                this.maxR = 650;
                this.maxL = 70;
                this.ypos = 482;
                break;
        }
        
        //set how much a Wookiee is knocked back when passed juice
//        this.knockback = (this.maxR - this.maxL) / 5;
        this.knockback = 130;
        
        //set starting horizontal position of Wookiee to maximum left position
        this.xpos = this.maxL;
    }
    
    
    /**
    *Method that moves Wookiee
    */
    public void move() {
        //move horizontally in the direction
        this.xpos += this.direction;
    }
    
    
    /**
    *Method that starts knockback sequence of Wookiee
    */
    public void knockback() {
        //set variable for how far to knockback to
        this.fallback = this.xpos - this.knockback;
        
        //set drinking state to true
        this.drinking = true;
        //set how long to drink for
        this.time = 240;
        //set false for variable that checks if Wookiee has travelled full knockback distance
        this.push = false;
    }
}
