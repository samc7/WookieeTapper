//import processing libraries, arrayList and list libraries, and sound libraries
import processing.core.*;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

//Start of WookieeTapper_Chowdhury
public class WookieeTapper_Chowdhury extends PApplet {
    //initialize all variable types
    PImage playerimg, full, empty, drink, enter, background, musicimg, soundimg, mathimg, cross;
    Wookiee wookiee1, wookiee2, wookiee3, wookiee4;
    boolean paused, gameover, offscreen, checkoffscreen, mathq, ask, wookieeFast, wookieeSlow, mugFast, mugSlow, powerupActive, showSpace, shotted, sfx, music;
    int score, blue, orange, mathinterval, selection, powerupCurrent, powerupTime, step;
    PFont google;
    String screen;
    Player player;
    Mug emptyreturn;
    Clip themeClip;
    List<Mug> mugList, emptyMugList;
    List<Wookiee> wookieeList;
    //set 2D array for math questions
    String[][] questions = {
        {"placeholder"},
        {"What are the asymptotes of", "1/(x+1) + 1 ?", "A) x = -1, y = 1", "B) x = 1, y = 1", "C) x = -1, y = -1", "D) x = 0.5, y = 0.5", "A"},
        {"What are the roots of", "(x - 3)(2x - 3) ?", "A) 3, 2", "B) 1.5, 3", "C) 0.6, 3", "D) 3, 6", "B"},
        {"What is the period of", "sin(2x) ?", "A) 4(pi)", "B) 2(pi)", "C) (pi)", "D) -(pi)", "C"},
        {"What is the period of", "cos(x/3) ?", "A) 4(pi)", "B) 2(pi)", "C) (pi)", "D) 6(pi)", "D"},
        {"What is the period of", "tan(x/2) ?", "A) 2(pi)", "B) 4(pi)", "C) (pi)", "D) 6(pi)", "A"},
        {"What is the factored form of", "x^2 + 2x + 1 ?", "A) 4(pi)", "B) (x + 1)^2", "C) (x + 1)(x - 1)", "D) (x^2 + 1)", "B"},
        {"What is the vertex of", "2(x - 5)^2 + 2 ?", "A) x = 5, y = -2", "B) x = -5, y = 2", "C) x = 5, y = 2", "D) y = 2, y = 3", "C"},
        {"What is the factored form of", "x^2 - 2x + 1 ?", "A) 4(pi)", "B) (x + 1)^2", "C) (x + 1)(x - 1)", "D) (x - 1)^2", "D"},
        {"What is the factored form of", "x^2 - 4x + 4 ?", "A) (x - 2)^2", "B) (x + 1)^2", "C) (x + 1)(x - 1)", "D) (x - 1)^2", "A"},
        {"What is the period of", "tan(x) ?", "A) 80", "B) (pi)", "C) 3(pi)", "D) (pi)/2 ", "B"}
    };
    
    @Override
    //game settings
    public void settings() {
        //Size of window
        size(800, 600);
    }
 
    @Override
    
    //runs on startup
    public void setup() {
        //frame rate of program
        frameRate(60);
        
        //load all sprites and background
        full = loadImage("images/mugfull.png");
        empty = loadImage("images/mugempty.png");
        drink = loadImage("images/drink.png");
        enter = loadImage("images/enter.png");
        background = loadImage("images/background.png");
        playerimg = loadImage("images/tapper.png");
        soundimg = loadImage("images/sound.png");
        musicimg = loadImage("images/play.png");
        mathimg = loadImage("images/math.png");  
        cross = loadImage("images/cross.png");
        
        //load font for text
        google = createFont("font/Google Product Sans.ttf", 32);
        
        //set main colors of game
        blue = color(0, 75, 190);
        orange = color(204, 102, 0);
        
        //set booleans for math questions
        ask = false;
        mathq = true;
        
        //set booleans for audio
        sfx = true;
        music = true;
        
        //set first screen
        screen = "menu";
        
        //call restart function to refresh all game variables
        restart();
        
        //play Root Beer Tapper theme song infinitely
        try {
            AudioInputStream theme = AudioSystem.getAudioInputStream(new File("audio/theme.wav"));
            themeClip = AudioSystem.getClip();
            themeClip.open(theme);
            
            //if music is enabled play theme song
            if (music) {
                themeClip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        }
        catch (IOException | LineUnavailableException | UnsupportedAudioFileException i){
            //output where the error is in command line
            System.out.println("Theme song error");
        }
    }
    
    @Override
    //draw function to show graphics
    public void draw() {
        //background to black
        background(0);
        
        //all objects are centre oriented
        imageMode(CENTER);
        rectMode(RADIUS);
        textAlign(CENTER, CENTER);
        
        //set font
        textFont(google);
        
        //no strokes
        strokeWeight(0);
        
        //set fill to orange
        fill(orange);

        //show background
        image(background, 400,300);
        
        //on menu screen
        switch (screen) {
            case "menu":
                //title
                textSize(60);
                text("Wookiee Tapper", 400,35);

                //all four menu button rectangles
                fill(orange);
                rect(250,200,120,80);
                rect(250,400,120,80);
                rect(550,200,120,80);
                rect(550,400,120,80);

                //all four menu button texts
                textSize(40);
                fill(blue);
                text("Play", 250, 200);
                text("Credits", 250, 400);
                text("Tutorial", 550, 200);
                text("Exit", 550, 400);   
                textSize(20);
                text("(Or press spacebar)", 250, 245);
                
                //set default fill back to orange
                fill(orange);
                rect(400,600,150,80);
                
                
                //settings buttons
                image(mathimg, 400, 565);
                if (!mathq) {
                    image(cross, 400,565);
                }
                
                image(soundimg, 490, 565);
                if (!sfx) {
                    image(cross, 490,565);
                }
                
                image(musicimg, 310, 565);
                if(!music) {
                    image(cross, 310,565);
                }
                        
                
                System.out.println(mouseX + ", " + mouseY);
                
                break;
            case "credits":
                //background to black
                background(0);

                //back button code
                fill(orange);
                rect(20, 585, 25,25);

                fill(blue);
                textSize(18);
                text("Back", 20,580);
                //end of back button

                //show title
                fill(orange);
                textSize(40);
                text("Credits",400,75);

                //stroke for lines
                stroke(orange);
                strokeWeight(5);

                //text for credits section
                textSize(20);
                text("Game Developer", 400, 150);
                line(270, 175, 530, 175);
                text("Sam Chowdhury", 400, 190) ;

                text("Sprite and Image Design", 400, 250);
                line(270, 275, 530, 275);
                text("Modified images from Terraria, SeekPNG.com, and Flaticon.com", 400, 290);

                text("Debuggers & Testers", 400, 350);
                line(270, 375, 530, 375);
                text("Safeer Ali, Crystal Jin, Mackayla Dixon, Cole Kapitan, Thea Vassilliou", 400, 390);

                text("Clients", 400, 450);
                line(270, 475, 530, 475);
                text("Chunfeng Li, Nathan Cassie", 400, 490);

                text("Thanks for playing!", 400, 575);
                break;
            
            case "play":
                //show background
                image(background, 400,300);

                //if game is unpaused and not over
                if (!paused && !gameover) {
                    //pause button code
                    fill(orange);
                    rect(0, 0, 60,40);

                    fill(blue);
                    textSize(18);
                    text("Pause", 30,15);
                    //end of pause button

                    //for each Mug object
                    for (Mug i: mugList) {
                        //move Mug
                        i.move();

                        //if at left boundary
                        if (i.xpos <= i.maxL) {
                            //end game
                            gameover = true;

                            //play gameover song and stop theme song if music enabled
                            if (sfx) {
                                try {
                                    AudioInputStream endgame = AudioSystem.getAudioInputStream(new File("audio/gameover.wav"));
                                    Clip overClip = AudioSystem.getClip();
                                    overClip.open(endgame);
                                    overClip.start();

                                    //stop theme song
                                    themeClip.stop();
                                }
                                catch (IOException | LineUnavailableException | UnsupportedAudioFileException c){
                                    //output where the error is in command line
                                    System.out.println("game over sound error");
                                }
                            }
                        }

                        //if powerup is active, set speed of Mug
                        if (powerupCurrent == 3 && powerupActive) {
                            i.speed = 7;
                        }
                        else if (powerupCurrent == 4 && powerupActive) {
                            i.speed = 2;    
                        }
                        //default speed
                        else {
                            i.speed = 5;
                        }
                    }

                    //for all returning mugs
                    for (Mug i: emptyMugList) {
                        //move
                        i.move();

                        //if powerup active, set speed
                        if (powerupCurrent == 1 && powerupActive) {
                            i.speed = 4;
                        }
                        else if (powerupCurrent == 2 && powerupActive) {
                            i.speed = 1;    
                        }
                        //default speed
                        else {
                            i.speed = 2;
                        }

                        //if reaches right boundary
                        if (i.xpos >= i.maxR) {
                            //end game
                            gameover = true;

                            //play gameover song and stop theme song if music enabled
                            if (sfx) {
                                try {
                                    AudioInputStream endgame = AudioSystem.getAudioInputStream(new File("audio/gameover.wav"));
                                    Clip overClip = AudioSystem.getClip();
                                    overClip.open(endgame);
                                    overClip.start();
                                    themeClip.stop();
                                }
                                catch (IOException | LineUnavailableException | UnsupportedAudioFileException w){
                                    //output where the error is in command line
                                    System.out.println("game over sound error");
                                }
                            }
                        }
                    }

                    //move Player
                    player.move(sfx);

                    //for each Wookiee
                    for (Wookiee y: wookieeList) {
                        //if offscreen count is over and is not on screen
                        if (y.count < 1 && !y.exist) {
                            //set existence
                            y.exist = true;
                            //respawn at left boundary
                            y.xpos = y.maxL;
                            //stop counter
                            y.countstart = false;
                            //check offscreen boolean disabled
                            checkoffscreen = false;
                        }

                        //if it does exist
                        if (y.exist) {
                            //if not drinking 
                            if (!y.drinking) {
                                //move right
                                y.move();
                            }
                            //if not fully pushed back by knockback
                            else if (!y.push) {
                                //knockback until reaches push destination
                                if (y.fallback < y.xpos) {
                                    y.xpos += -2;
                                }
                                //if reaches push destination
                                else {
                                    //stop pushing
                                    y.push = true;
                                }
                            }

                            //if there are full mugs
                            if (mugList.size() >= 1) {
                                //for each Mug
                                for (int i = 0; i < mugList.size(); i++) {
                                    //check if Wookiee can collect Mug
                                    if (Math.abs(y.xpos - mugList.get(i).xpos) <= 7 && !y.drinking && y.level == mugList.get(i).level) {
                                        //wookiee collects Mug and starts knockback sequence
                                        y.knockback();

                                        //remove full Mug from existence
                                        mugList.remove(i);
                                    }
                                }
                            }

                            //if the Wookiee has been pushed back
                            if (y.push) {
                                //start countdown for how long it stands
                                if (y.time > 0) {
                                    //countdown
                                    y.time--;
                                }
                                //if countdown is over
                                else {
                                    //not drinking and has not been pushed
                                    y.drinking = false;
                                    y.push = false;

                                    //generate chance for powerup
                                    double powerupChance = Math.ceil(Math.random()*100);

                                    //active powerup variable
                                    int powerup = 0;

                                    //20% chance of powerup
                                    if (powerupChance <= 20) {
                                        //random powerup selection 
                                        double powerupSelection = Math.ceil(Math.random()*100);

                                        //set powerup based on selection value
                                        if (powerupSelection <= 25) {
                                            powerup = 1;
                                        }
                                        else if (powerupSelection <= 50) {
                                            powerup = 2;
                                        }
                                        else if (powerupSelection <= 75) {
                                            powerup = 3;
                                        }
                                        else {
                                            powerup = 4;
                                        }
                                    }

                                    //create returning Mug with input powerup value
                                    Mug shot = new Mug(y.xpos, y.ypos + 58, y.level, 1, 2, powerup, sfx);

                                    //add Mug to returning Mug list
                                    emptyMugList.add(shot);
                                }
                            }

                            //if Wookiee reaches right boundary
                            if (y.xpos >= y.maxR) {
                                //end game
                                gameover = true;

                                //play gameover sound and stop main theme if music enabled
                                if(sfx) {
                                    try {
                                        AudioInputStream endgame = AudioSystem.getAudioInputStream(new File("audio/gameover.wav"));
                                        Clip overClip = AudioSystem.getClip();
                                        overClip.open(endgame);
                                        overClip.start();
                                        themeClip.stop();
                                    }
                                    catch (IOException | LineUnavailableException | UnsupportedAudioFileException e){
                                        //output where the error is in command line
                                        System.out.println("game over sound error");
                                    }
                                }
                            }

                            //if the Wookiee is pushed out of the bar
                            if (y.xpos < y.maxL) {
                                //reset countdown for respawn
                                y.count = 720;

                                //countdown start is true
                                y.countstart = true;

                                //set existence for Wookiee
                                y.exist = false;

                                //disable drinking and push variables
                                y.drinking = false;
                                y.push = false;

                                //add to score
                                score += 100;
                            }
                        }

                        //if countdown enabled
                        if (y.countstart) {
                            //countdown
                            y.count--;
                        }
                    }

                    //if game hasn't checked if off screen
                    if (!checkoffscreen) {
                        //for all wookiees
                        for (Wookiee i: wookieeList) {
                            //if inexistent
                            if (!i.exist) {
                                //all offscreen is true
                                offscreen = true;
                            }
                            else {
                                //one is on screen, so break out of loop
                                offscreen = false;
                                break;
                            }
                        }

                        //mini timer for respawning all wookiees if all offscreen
                        int smallTime = 30;

                        //if all offscreen
                        if (offscreen) {
                            //for each wookiee
                            for (Wookiee i: wookieeList) {
                                //set respawn countdown to smallTime
                                i.count = smallTime;
                                //increase smallTime by 0.5s
                                smallTime += 30;   

                                //play 1up sound if sfx enabled
                                if (sfx) {
                                    try {
                                        AudioInputStream up = AudioSystem.getAudioInputStream(new File("audio/1up.wav"));
                                        Clip upClip = AudioSystem.getClip();
                                        upClip.open(up);
                                        upClip.start();
                                    }
                                    catch (IOException | LineUnavailableException | UnsupportedAudioFileException e){
                                        //output where the error is in command line
                                        System.out.println("1up sound error");
                                    }
                                }
                            }

                            //add to score
                            score += 100;

                            //set checked offscreen to true so it doesn't check again until enemies are on screen
                            checkoffscreen = true;
                        }
                    }

                    //for each returning mug
                    for (int i = 0; i < emptyMugList.size(); i++) {
                        //if player collects
                        if (emptyMugList.get(i).level == player.level && Math.abs(emptyMugList.get(i).xpos - player.xpos) <= 8) {
                            //if no powerup is currently active
                            if (!powerupActive) {
                                //if powerup is 0
                                if (emptyMugList.get(i).powerup == 0) {
                                    //no powerup :(
                                    powerupActive = false;
                                }
                                else {
                                    //powerup is active!
                                    powerupActive = true;
                                    //set powerup to powerup stored in mug
                                    powerupCurrent = emptyMugList.get(i).powerup;
                                    //set time for powerup to be active
                                    powerupTime = 540;

                                    //play powerup sound if sfx enabled
                                    if (sfx) {
                                        try {
                                            AudioInputStream powerups = AudioSystem.getAudioInputStream(new File("audio/powerup.wav"));
                                            Clip powerClip = AudioSystem.getClip();
                                            powerClip.open(powerups);
                                            powerClip.start();
                                        }
                                        catch (IOException | LineUnavailableException | UnsupportedAudioFileException c){
                                            //output where the error is in command line
                                            System.out.println("powerup sound error");
                                        }
                                    }
                                }
                            }
                            //destroy returned mug
                            emptyMugList.remove(i);

                            //add to score
                            score += 150;
                        }
                    }

                    //show score 
                    fill(orange);
                    textSize(20);
                    text("Score:", 400, 25);
                    textSize(32);
                    text(score, 400, 50);

                    //if it's time to give a math question
                    if (mathq && score >= mathinterval) {
                        //pause the game
                        paused = true;

                        //set next math question interval
                        mathinterval += 1000;

                        //time to ask is true
                        ask = true;

                        //randomly select next math question
                        selection = (int)Math.ceil(Math.random()*10);
                        
                        //if enemies aren't offscreen (because going offscreen plays a sound as well)
                        //if sound effects enabled
                        if (!offscreen && sfx) {
                            //play pause sound
                            try {
                                AudioInputStream pause = AudioSystem.getAudioInputStream(new File("audio/pause.wav"));
                                Clip pauseClip = AudioSystem.getClip();
                                pauseClip.open(pause);
                                pauseClip.start();
                            }
                            catch (IOException | LineUnavailableException | UnsupportedAudioFileException i){
                                //output where the error is in command line
                                System.out.println("pause for question sound error");
                            }
                        }
                    }

                    //if powerup is active
                    if (powerupActive && powerupTime > 0) {
                        //countdown
                        powerupTime--;

                        //show which powerup is active
                        fill(256,256,256);
                        textSize(30);
                        switch (powerupCurrent) {
                            case 1:
                                text("Fast Empty Mugs", 400, 80);
                                break;
                            case 2:
                                text("Slow Empty Mugs", 400, 80);
                                break;
                            case 3:
                                text("Fast Full Mugs", 400, 80);
                                break;
                            case 4:
                                text("Slow Full Mugs", 400, 80);
                                break;
                        }
                    }
                    //if countdown is over
                    else {
                        //disable powerup
                        powerupActive = false;
                    }
                }

                //for each full mug, show on screen
                for (Mug i: mugList) {
                    image(full, i.xpos, i.ypos);
                }

                //for each returning mug, show on screen
                for (Mug i: emptyMugList) {
                    image(empty, i.xpos, i.ypos);
                    //show powerup stone in mug if available
                    switch (i.powerup) {
                        case 0:
                            break;
                        case 1:
                            fill(256,0,0);
                            ellipse(i.xpos, i.ypos + 5, 5, 5);
                            break;
                        case 2:
                            fill(0,256,0);
                            ellipse(i.xpos, i.ypos + 5, 5, 5);
                            break;
                        case 3:
                            fill(0,0,256);
                            ellipse(i.xpos, i.ypos + 5, 5, 5);
                            break;
                        case 4:
                            fill(0,256,256);
                            ellipse(i.xpos, i.ypos + 5, 5, 5);
                            break;
                    }
                }

                //for each wookiee
                for (int i = 0; i < wookieeList.size(); i++) {
                    //if not drinking and exists
                    if (!wookieeList.get(i).drinking && wookieeList.get(i).exist) {
                        //show moving image
                        image(enter, wookieeList.get(i).xpos, wookieeList.get(i).ypos);
                    }
                    //if drinking
                    else if (wookieeList.get(i).exist){
                        //show drinking image
                        image(drink, wookieeList.get(i).xpos, wookieeList.get(i).ypos);
                    }
                }

                //show player
                image(playerimg, player.xpos, player.ypos);

                //if end game
                if (gameover) {
                    //create rect window
                    fill(orange);
                    rect(400, 300, 200, 100);

                    //show GAME OVER
                    textSize(60);
                    fill(blue);
                    text("GAME OVER", 400, 230);

                    //final score
                    textSize(30);
                    text("Final Score:", 400, 300);
                    text(score, 400, 325);

                    //what to do next
                    textSize(18);
                    text("Click or press R to return to main menu", 400, 380);
                }
                //if it's time for a math question
                else if (paused && ask) {
                    //create rect window
                    fill(orange);
                    rect(400, 300, 300, 200);

                    //show question and each response
                    fill(blue);
                    textSize(20);
                    text(questions[selection][0], 400, 200);
                    text(questions[selection][1], 400, 230);
                    text(questions[selection][2], 400, 260);
                    text(questions[selection][3], 400, 290);
                    text(questions[selection][4], 400, 320);
                    text(questions[selection][5], 400, 350);

                    //what to do
                    text("Press the letter corresponding to the right answer", 400, 400);

                }
                //if paused game
                else if (paused) {
                    //create rect window
                    fill(orange);
                    rect(400, 300, 200, 100);

                    //show PAUSED
                    textSize(60);
                    fill(blue);
                    text("PAUSED", 400, 230);

                    //show score
                    textSize(30);
                    text("Score:", 400, 300);
                    text(score, 400, 325);

                    //what to do next
                    textSize(18);
                    text("Press R to return to main menu", 400, 360);
                    text("Click or press P to resume", 400, 380);
                }
                break;
            case "tutorial":
                //back button code
                fill(orange);
                rect(20, 585, 25,25);
                
                fill(blue);
                textSize(18);
                text("Back", 20,580);
                //end of back button

                //show based on which step of tutorial
                switch (step) {
                    case 0:
                        //create rect window
                        fill(orange);
                        rect(400, 300, 300, 200);

                        //show backstory
                        textSize(20);
                        fill(blue);
                        text("You’re a bartender on Kashyyyk, serving Wookiee Juice to", 400, 120);
                        text("thirsty Wookies. They are known to have infinite thirst,", 400, 150);
                        text("so they repeatedly come back for more juice. You are the", 400, 180);
                        text("only bartender available to slide Juice down the four ", 400, 210);
                        text("lanes to Wookies, and you must make sure you recollect", 400, 240);
                        text("their mugs as you do not have an infinite stash. ", 400, 270);

                        //show game elements
                        image(enter, 200, 350);
                        text("Wookiee", 200, 400);
                        image(empty, 333, 350);
                        text("No Juice", 333, 400);
                        image(full, 477, 350);
                        text("Juice", 477, 400);
                        image(playerimg, 600, 350);
                        text("You", 600, 400);
                        break;
                    case 1:
                        //move player
                        player.move(sfx);

                        //show player
                        image(playerimg, player.xpos, player.ypos);                    

                        //what to do
                        textSize(20);
                        fill(orange);
                        text("Use arrow keys to move", 400, 230);
                        break;
                    case 2:
                        //What to do
                        fill(orange);
                        text("Send juice using spacebar", 400, 230);

                        //show player
                        image(playerimg, player.xpos, player.ypos);

                        //show each full mug
                        for (Mug i: mugList) {
                            image(full, i.xpos, i.ypos);
                        }   

                        //if step incomplete
                        if (!showSpace) {
                            //move player
                            player.move(sfx);

                            //move each mug
                            for (Mug i: mugList) {
                                i.move();
                                //if mug reaches left boundary, step is complete
                                if (i.xpos <= i.maxL) {
                                    showSpace = true;
                                }
                            }
                        }  
                        break;
                    case 3:
                        //what to do
                        fill(orange);
                        text("Send *one* juice to this wookiee", 400, 230);

                        //show player
                        image(playerimg, player.xpos, player.ypos);

                        //show full mug
                        for (Mug i: mugList) {
                            image(full, i.xpos, i.ypos);
                        }

                        //for each wookiee 
                        for (int i = 0; i < wookieeList.size(); i++) {
                            //show moving if not drinking
                            if (!wookieeList.get(i).drinking && wookieeList.get(i).exist) {
                                image(enter, wookieeList.get(i).xpos, wookieeList.get(i).ypos);
                            }
                            //show drinking
                            else if (wookieeList.get(i).exist){
                                image(drink, wookieeList.get(i).xpos, wookieeList.get(i).ypos);
                            }
                            //break out of loop because only one wookiee needed
                            break;
                        }
                        //if step incomplete
                        if (!showSpace) {
                            //move player
                            player.move(sfx);

                            //move full mug
                            for (Mug i: mugList) {
                                i.move();
                            }

                            //for each wookiee
                            for (Wookiee y: wookieeList) {
                                //if exists
                                if (y.exist) {
                                    //if not drinking
                                    if (!y.drinking) {
                                        //move
                                        y.move();
                                    }
                                    //if not fully pushed back and drinking
                                    else if (!y.push) {
                                        //move wookiee back
                                        if (y.fallback < y.xpos) {
                                            y.xpos += -2;
                                        }
                                        //if knocked back fully stop pushing
                                        else {
                                            y.push = true;

                                            //step complete
                                            showSpace = true;
                                        }
                                    }

                                    //for each full mug
                                    for (int i = 0; i < mugList.size(); i++) {
                                        //if wookiee picks up
                                        if (Math.abs(y.xpos - mugList.get(i).xpos) <= 7 && !y.drinking && y.level == mugList.get(i).level) {
                                            //knock back wookiee
                                            y.knockback();
                                            //destroy mug
                                            mugList.remove(i);
                                        }
                                    }

                                    //if no mug is passed
                                    if (y.xpos >= y.maxR) {
                                        //go back to step 2
                                        step = 2;
                                    }
                                    //if wookiee reaches left boundary
                                    else if (y.xpos <= y.maxL) {
                                        //step complete
                                        showSpace = true;
                                    }
                                }
                                //break out since only one wookiee needed
                                break;
                            }
                        }
                        break;
                    case 4:
                        //if player collects returning mug
                        if (Math.abs(emptyreturn.xpos - player.xpos) < 5) {
                            showSpace = true;
                        }
                        //if returning mug hasn't reached right boundary
                        else if (emptyreturn.xpos <= emptyreturn.maxR) {
                            //move empty mug
                            emptyreturn.move();

                            //move player
                            player.move(sfx);

                            //show player and returning mug
                            image(empty, emptyreturn.xpos, emptyreturn.ypos);
                            image(playerimg, player.xpos, player.ypos);
                        }

                        //show what to do
                        fill(orange);
                        text("Collect the returning empty mug", 400, 230);
                        break;
                    case 5:
                        //create rect window
                        fill(orange);
                        rect(400, 300, 300, 200);

                        //final message for player
                        textSize(20);
                        fill(blue);
                        text("Remember to pass a single juice to each wookiee, and to", 400, 120);
                        text("also collect their returned mugs. Returned mugs may", 400, 150);
                        text("rarely contain tips which are powerup stones that grant", 400, 180);
                        text("your mugs special powers! ", 400, 210);
                        text("When a math question arrives, press the answer's", 400, 240);
                        text("corresponding letter to select the answer. You can turn", 400, 270);
                        text("off math questions at the bottom of the main page.", 400, 300);

                        //show stones in mugs
                        image(empty, 200, 350);
                        fill(256,0,0);
                        ellipse(200, 355, 5, 5);

                        image(empty, 333, 350);
                        fill(0,256,0);
                        ellipse(333, 355, 5, 5);

                        image(empty, 477, 350);
                        fill(0,0,256);
                        ellipse(477, 355, 5, 5);

                        image(empty, 600, 350);
                        fill(0,256,256);
                        ellipse(600, 355, 5, 5);

                        //message for mugs
                        fill(blue);
                        text("Stones in returning mugs", 400, 400);

                        //what to do
                        text("Good luck!", 400, 450);
                        text("PRESS SPACEBAR TO RETURN", 400, 470);
                        break;
                }

                //if step complete, show option to get to next step
                if (showSpace) {
                    text("PRESS SPACEBAR TO CONTINUE", 400, 470);
                }
            break;
        }
    }
    
    //if key is pressed
    @Override
    public void keyPressed() {
        //on menu
        if (screen.equals("menu")) {
            if (key == ' ') {
                //start playing
                screen = "play";
                
                //play start sound if sfx enabled
                if (sfx) {
                    try {
                        AudioInputStream starting = AudioSystem.getAudioInputStream(new File("audio/start.wav"));
                        Clip startClip = AudioSystem.getClip();
                        startClip.open(starting);
                        startClip.start();
                    }
                    catch (IOException | LineUnavailableException | UnsupportedAudioFileException i){
                        //output where the error is in command line
                        System.out.println("start sound error");
                    }
                }
            }
        }
        //if screen is tutorial
        else if (screen.equals("tutorial")) {
            if (key == ' ' && showSpace) {
                //if step is 3, create empty mug object for tutorial
                if (step == 3) {
                    emptyreturn = new Mug(230, 180, 1, 1, 2, 0, sfx);
                    
                    //has player already shot one mug? checker
                    shotted = false;
                }
                
                //go to next step
                step += 1;
                //step incomplete
                showSpace = false;
                
                //refresh variables
                restart();
                
                
                //play 1up sound for each step if sfx enabled
                if (sfx) {
                    try {
                        AudioInputStream coin = AudioSystem.getAudioInputStream(new File("audio/coin.wav"));
                        Clip coinClip = AudioSystem.getClip();
                        coinClip.open(coin);
                        coinClip.start();
                    }
                    catch (IOException | LineUnavailableException | UnsupportedAudioFileException e){
                        //output where the error is in command line
                        System.out.println("coin sound error");
                    }
                }
            }
            else if (step == 1) {    
                //movement of player code, and step completion
                if (key == CODED) {
                    if (keyCode == UP){
                        player.vertical = -1;
                        showSpace = true;
                    }
                    else if (keyCode == DOWN) {
                        player.vertical = 1;
                        showSpace = true;
                    }
                    if (keyCode == LEFT) {
                        player.left = 1;
                        showSpace = true;
                    }
                    if (keyCode == RIGHT) {
                        player.right = 1;
                        showSpace = true;
                    }
                    
                }
            }
            else if (step == 2) {
                //movement of player code
                if (key == CODED) {
                    if (keyCode == UP){
                        player.vertical = -1;
                    }
                    else if (keyCode == DOWN) {
                        player.vertical = 1;
                    }
                    if (keyCode == LEFT) {
                        player.left = 1;
                    }
                    if (keyCode == RIGHT) {
                        player.right = 1;
                    }
                }
                //shot mug code
                else if (key ==' ' && !showSpace) {
                    //create object
                    Mug shot = new Mug(player.xpos, player.ypos, player.level, -1, 5, 0, sfx);

                    //add to list of full mugs
                    mugList.add(shot);
                    System.out.println("yee");
                }
            }
            else if (step == 3) {
                //movement code for player horizontal ONLY
                if (key == CODED && step != 0) {
                    if (keyCode == LEFT) {
                        player.left = 1;
                    }
                    if (keyCode == RIGHT) {
                        player.right = 1;
                    }
                }
                //if step incomplete and hasn't shot yet
                else if (key ==' ' && !showSpace && shotted == false) {
                    //create mug
                    Mug shot = new Mug(player.xpos, player.ypos, player.level, -1, 5, 0, sfx);
                    
                    //player has shot already! no more shooting mugs
                    shotted = true;
                    
                    //add to list
                    mugList.add(shot);
                }
            }
            else if (step == 4) {
                //if step incomplete, allow player to move only horizontally
                if (key == CODED && step != 0) {
                    if (keyCode == LEFT) {
                        player.left = 1;
                    }
                    if (keyCode == RIGHT) {
                        player.right = 1;
                    }
                }
            }
            else if (step == 5) {
                if (key == ' ') {
                    //return to main menu
                    screen = "menu";
                
                    //play 1up sound if sfx enabled
                    if (sfx) {
                        try {
                            AudioInputStream coin = AudioSystem.getAudioInputStream(new File("audio/coin.wav"));
                            Clip coinClip = AudioSystem.getClip();
                            coinClip.open(coin);
                            coinClip.start();
                        }
                        catch (IOException | LineUnavailableException | UnsupportedAudioFileException e){
                            //output where the error is in command line
                            System.out.println("coin sound error");
                        }
                    }
                }
            }
        }
        //if on game screen
        else if (screen.equals("play")) {
            if (key == CODED) {
                //if game isn't paused
                if (!paused) {
                    //movement code for player
                    if (keyCode == UP){
                        player.vertical = -1;
                    }
                    else if (keyCode == DOWN) {
                        player.vertical = 1;
                    }
                    if (keyCode == LEFT) {
                        player.left = 1;
                    }
                    if (keyCode == RIGHT) {
                        player.right = 1;
                    }
                    
                }            
            }
            //if game is still alive
            else if (key ==' ' && !paused && !gameover) {
                //shoot new mug
                Mug shot = new Mug(player.xpos, player.ypos, player.level, -1, 5, 0, sfx);

                //add to mug list
                mugList.add(shot);
            }
            else if ((key =='p' || key =='P') && !paused && !gameover) {
                //pause game
                paused = true;
                
                //play pause sound if sfx enabled
                if (sfx) {    
                    try {
                        AudioInputStream pause = AudioSystem.getAudioInputStream(new File("audio/pause.wav"));
                        Clip pauseClip = AudioSystem.getClip();
                        pauseClip.open(pause);
                        pauseClip.start();
                    }
                    catch (IOException | LineUnavailableException | UnsupportedAudioFileException i){
                        //output where the error is in command line
                        System.out.println("pause gamesound error");
                    }    
                }
            }
            else if ((key =='p' || key =='P') && !ask && paused && !gameover) {
                //unpause game
                paused = false;
                
                //play pause sound if sfx enabled
                if (sfx) {
                    try {
                        AudioInputStream pause = AudioSystem.getAudioInputStream(new File("audio/pause.wav"));
                        Clip pauseClip = AudioSystem.getClip();
                        pauseClip.open(pause);
                        pauseClip.start();
                    }
                    catch (IOException | LineUnavailableException | UnsupportedAudioFileException i){
                        //output where the error is in command line
                        System.out.println("unpause game sound error");
                    }
                }
            }
            //if paused or ended game
            else if ((key == 'r' || key == 'R') && (paused || gameover) && !ask) {
                //return to menu
                screen = "menu";
                
                //refresh game variables
                restart();
                
                //play pause sound if sfx enabled
                if (sfx) {
                    try {
                        AudioInputStream pause = AudioSystem.getAudioInputStream(new File("audio/pause.wav"));
                        Clip pauseClip = AudioSystem.getClip();
                        pauseClip.open(pause);
                        pauseClip.start();
                    }
                    catch (IOException | LineUnavailableException | UnsupportedAudioFileException i){
                        //output where the error is in command line
                        System.out.println("return to menu sound error");
                    }
                }
                //if music is enabled play theme
                if (music) {
                    themeClip.loop(Clip.LOOP_CONTINUOUSLY);
                }
            }
            //if question time
            else if (ask && paused) {
                //store key in string and upper case
                String answer = Character.toString(Character.toUpperCase(key));
                
                //if the key pressed is one of the four answer options
                if (answer.equals("A") || answer.equals("B") || answer.equals("C") || answer.equals("D")) {
                    //if answer is right
                    if (questions[selection][6].equals(answer)) {
                        //add to score and continue game
                        score += 200;
                        paused = false;

                        //play coin sound if sfx enabled
                        if (sfx) {
                            try {
                                AudioInputStream coin = AudioSystem.getAudioInputStream(new File("audio/coin.wav"));
                                Clip coinClip = AudioSystem.getClip();
                                coinClip.open(coin);
                                coinClip.start();
                            }
                            catch (IOException | LineUnavailableException | UnsupportedAudioFileException i) {
                                //output where the error is in command line
                                System.out.println("game over sound error");
                            }
                        }
                    }
                    else {
                        //end game
                        gameover = true;

                        //play gameover sound if music enabled
                        if (sfx) {
                            try {
                                AudioInputStream endgame = AudioSystem.getAudioInputStream(new File("audio/gameover.wav"));
                                Clip overClip = AudioSystem.getClip();
                                overClip.open(endgame);
                                overClip.start();
                                themeClip.stop();
                            }
                            catch (IOException | LineUnavailableException | UnsupportedAudioFileException i) {
                                //output where the error is in command line
                                System.out.println("game over sound error");
                            }
                        }
                    }
                    
                    //asked question already
                    ask = false;
                }
            }
        }
    }
    
    //if key released
    public void keyReleased() {
        //during play or tutorial
        if (screen.equals("play") || screen.equals("tutorial")) {
            //stop moving player in direction released
            if (keyCode == LEFT) {
                player.left = 0;
            }
            if (keyCode == RIGHT) {
                player.right = 0;
            }
            if (keyCode == UP || keyCode == DOWN) {
                player.vertical = 0;
            }
        }
    }
    
    //if mouse clicked
    public void mouseClicked() {
        //on menu
        if (screen.equals("menu")) {
            //play button
            if (mouseX > 130 && mouseX < 370 && mouseY > 115 && mouseY < 285) {
                //set screen to play
                screen = "play";
                
                //refresh game variables
                restart();
                
                //play start sound if music enabled
                if (sfx) {    
                    try {
                        AudioInputStream starting = AudioSystem.getAudioInputStream(new File("audio/start.wav"));
                        Clip startClip = AudioSystem.getClip();
                        startClip.open(starting);
                        startClip.start();
                    }
                    catch (IOException | LineUnavailableException | UnsupportedAudioFileException i){
                        //output where the error is in command line
                        System.out.println("start sound error");
                    }
                }
            }
            //tutorial button
            else if (mouseX > 430 && mouseX < 670 && mouseY > 115 && mouseY < 285) {
                //set screen to tutorial
                screen = "tutorial";
                
                //refresh game variables
                restart();
                
                //reset step variable
                step = 0;
                
                //step complete boolean
                showSpace = true;
                
                //play start sound if music enabled
                if (sfx) {    
                    try {
                        AudioInputStream starting = AudioSystem.getAudioInputStream(new File("audio/start.wav"));
                        Clip startClip = AudioSystem.getClip();
                        startClip.open(starting);
                        startClip.start();
                    }
                    catch (IOException | LineUnavailableException | UnsupportedAudioFileException i){
                        //output where the error is in command line
                        System.out.println("start sound error");
                    }
                }
            }
            //credits button
            else if (mouseX > 130 && mouseX < 370 && mouseY > 315 && mouseY < 485) {
                //set screen to credits
                screen = "credits";
                
                //play button sound if sfx enabled
                if (sfx) {
                    try {
                        AudioInputStream button = AudioSystem.getAudioInputStream(new File("audio/button.wav"));
                        Clip buttonClip = AudioSystem.getClip();
                        buttonClip.open(button);
                        buttonClip.start();
                    }
                    catch (IOException | LineUnavailableException | UnsupportedAudioFileException i){
                        //output where the error is in command line
                        System.out.println("click credits sound error");
                    }
                }
            }
            //exit button
            else if (mouseX > 430 && mouseX < 670 && mouseY > 115 && mouseY < 485) {
                exit();
            }
            //math question toggle button
            else if (mouseX > 370 && mouseX < 430 && mouseY > 525) {
                //toggle math questions
                if (mathq){
                    mathq = false;
                }
                else if (!mathq) {
                    mathq = true;
                }
                
                //play toggle sound if sfx enabled
                if (sfx) {
                    try {
                        AudioInputStream toggle = AudioSystem.getAudioInputStream(new File("audio/toggle.wav"));
                        Clip toggleClip = AudioSystem.getClip();
                        toggleClip.open(toggle);
                        toggleClip.start();
                    }
                    catch (IOException | LineUnavailableException | UnsupportedAudioFileException i){
                        //output where the error is in command line
                        System.out.println("toggle math sound error");
                    }
                }
            }
            //music toggle button
            else if (mouseX > 275 && mouseX < 340 && mouseY > 525) {
                //toggle music
                if (music){
                    music = false;
                    themeClip.stop();
                }
                else if (!music) {
                    music = true;
                    themeClip.loop(Clip.LOOP_CONTINUOUSLY);
                }
                
                //play toggle sound if sfx enabled
                if (sfx) {
                    try {
                        AudioInputStream toggle = AudioSystem.getAudioInputStream(new File("audio/toggle.wav"));
                        Clip toggleClip = AudioSystem.getClip();
                        toggleClip.open(toggle);
                        toggleClip.start();
                    }
                    catch (IOException | LineUnavailableException | UnsupportedAudioFileException i){
                        //output where the error is in command line
                        System.out.println("toggle math sound error");
                    }
                }
            }
            //sound effects toggle button
            else if (mouseX > 455 && mouseX < 525 && mouseY > 525) {
                //toggle sound
                if (sfx){
                    sfx = false;
                }
                else if (!sfx) {
                    sfx = true;
                }
                
                //play toggle sound if sfx enabled
                if (sfx) {
                    try {
                        AudioInputStream toggle = AudioSystem.getAudioInputStream(new File("audio/toggle.wav"));
                        Clip toggleClip = AudioSystem.getClip();
                        toggleClip.open(toggle);
                        toggleClip.start();
                    }
                    catch (IOException | LineUnavailableException | UnsupportedAudioFileException i){
                        //output where the error is in command line
                        System.out.println("toggle math sound error");
                    }
                }
            }
        }
        
        //if play screen
        else if (screen.equals("play")) {
            //pause button
            if (mouseX < 50 && mouseY < 50 && !paused) {
                // pause game
                paused = true;
                
                //play pause sound if sfx enabled
                if (sfx) {
                    try {
                        AudioInputStream pause = AudioSystem.getAudioInputStream(new File("audio/pause.wav"));
                        Clip pauseClip = AudioSystem.getClip();
                        pauseClip.open(pause);
                        pauseClip.start();
                    }
                    catch (IOException | LineUnavailableException | UnsupportedAudioFileException i){
                        //output where the error is in command line
                        System.out.println("pause sound error");
                    }
                }
            }
            // if game is paused
            else if (paused && !ask) {
                //unpause
                paused = false;
                
                //play unpause sound if sfx enabled
                if (sfx) {
                    try {
                        AudioInputStream pause = AudioSystem.getAudioInputStream(new File("audio/pause.wav"));
                        Clip pauseClip = AudioSystem.getClip();
                        pauseClip.open(pause);
                        pauseClip.start();
                    }
                    catch (IOException | LineUnavailableException | UnsupportedAudioFileException i){
                        //output where the error is in command line
                        System.out.println("pause sound error");
                    }
                }
            }
            //if end game
            else if (gameover) {
                //return to menu
                screen = "menu";
                
                //play button sound if sfx enabled
                if (sfx) {
                    try {
                        AudioInputStream endgame = AudioSystem.getAudioInputStream(new File("audio/pause.wav"));
                        Clip overClip = AudioSystem.getClip();
                        overClip.open(endgame);
                        overClip.start();
                        //if music is enabled play theme
                        
                    }
                    catch (IOException | LineUnavailableException | UnsupportedAudioFileException i){
                        //output where the error is in command line
                        System.out.println("game over sound error");
                    }
                }
                //continue theme if music enabled
                if (music) {
                    themeClip.loop(Clip.LOOP_CONTINUOUSLY);
                }
            }
        }
        //tutorial screen or credits screen
        else if (screen.equals("tutorial") || screen.equals("credits")) {
            //back button
            if (mouseX < 45 && mouseY >= 560) {
                //return to menu
                screen = "menu";
                
                //play button sound if sfx enabled
                if (sfx) {
                    try {
                        AudioInputStream button = AudioSystem.getAudioInputStream(new File("audio/button.wav"));
                        Clip buttonClip = AudioSystem.getClip();
                        buttonClip.open(button);
                        buttonClip.start();
                    }
                    catch (IOException | LineUnavailableException | UnsupportedAudioFileException i){
                        //output where the error is in command line
                        System.out.println("return to menu sound error");
                    }
                }
            }
        }
    }
    
    //main class to run the game
    public static void main(String[] args) {
        PApplet.main("WookieeTapper_Chowdhury");
    }
    
    
    /**
    *Method to refresh all game variables when restarting game
    */
    public void restart() {
        player = new Player();
        wookiee1 = new Wookiee(1);
        wookiee2 = new Wookiee(2);
        wookiee3 = new Wookiee(3);
        wookiee4 = new Wookiee(4);
        wookieeList = new ArrayList<>();
        mugList = new ArrayList<>();
        emptyMugList = new ArrayList<>();
        paused = false;
        gameover = false;
        offscreen = false;
        checkoffscreen = false;
        wookieeFast = false;
        wookieeSlow = false;
        mugFast = false;
        mugSlow = false;
        powerupActive = false;
        score = 0;
        mathinterval = 1000;
        powerupCurrent = 0;
        powerupTime = 0;
        
        wookieeList.add(wookiee1);
        wookieeList.add(wookiee2);
        wookieeList.add(wookiee3);
        wookieeList.add(wookiee4);
    }
}