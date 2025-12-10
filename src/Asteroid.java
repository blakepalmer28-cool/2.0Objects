public class Asteroid {
    //VARIABLE DECLARATION SECTION
    //Here's where you state which variables you are going to use.
    public String name;                //holds the name of the hero
    public int xpos;                //the x position
    public int ypos;                //the y position
    public int dx;                    //the speed of the hero in the x direction
    public int dy;                    //the speed of the hero in the y direction
    public int width;
    public int height;
    public boolean isAlive;            //a boolean to denote if the hero is alive or dead.


    // METHOD DEFINITION SECTION

    // Constructor Definition
    // A constructor builds the object when called and sets variable values.


    //This is a SECOND constructor that takes 3 parameters.  This allows us to specify the hero's name and position when we build it.
    // if you put in a String, an int and an int the program will use this constructor instead of the one above.
    public Asteroid(int pXpos, int pYpos) {
        xpos = pXpos;
        ypos = pYpos;
        dx = -1;
        dy = 4;
        width = 160;
        height = 160;
        isAlive = true;


    } // constructor


    //The move method.  Everytime this is run (or "called") the hero's x position and y position change by dx and dy
    public void move() {

        if (xpos < 0) {//left wall
            xpos=970;

        }
        if (xpos > 970) {//right wall
            xpos=0;

        }
        if (ypos < 0) {
            dy = -dy;

        }
        if (ypos > 700 - height) {
            dy = -dy;
        }

        xpos = xpos + dx;
        ypos = ypos + dy;

    }


    }

