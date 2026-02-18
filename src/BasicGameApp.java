//Basic Game Application
//Version 2
// Basic Object, Image, Movement
// Astronaut moves to the right.
// Threaded

//K. Chun 8/2018

//*******************************************************************************
//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;

//Graphics Libraries
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;


//*************************************
//******************************************
// Class Definition Section

//implement key listener
public class BasicGameApp implements Runnable, KeyListener {//tictoc

    //Variable Definition Section
    //Declare the variables used in the program
    //You can set their initial values too

    //Sets the width and height of the program window
    final int WIDTH = 1000;
    final int HEIGHT = 700;

    //Declare the variables needed for the graphics
    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;

    public BufferStrategy bufferStrategy;
    public Image astroPic;
    public Image asteroidPic;
    public Image backgroundPic;

    //Declare the objects used in the program
    //These are things that are made up of more than one variable type
    private Astronaut astro;
    public Astronaut astro2;
    public Asteroid asteroid1;
    public Asteroid asteroid2;


    // Main method definition
    // This is the code that runs first and automatically
    public static void main(String[] args) {
        BasicGameApp ex = new BasicGameApp();   //creates a new instance of the game
        new Thread(ex).start();                 //creates a threads & starts up the code in the run( ) method
    }

    //todo: make astro and astro2 go in different directions

    // Constructor Method
    // This has the same name as the class
    // This section is the setup portion of the program
    // Initialize your variables and construct your program objects here.
    public BasicGameApp(){

        setUpGraphics();

            //randomness
            //range 0-9
            int randx = (int) (Math.random() * 10);
            int randy = (int) (Math.random() * 700);
            //0.0001-0.9999
            //0.001 - 9.999
            //0.9

            //range to 1-10
            randx = (int) (Math.random() * 10);
            //0.001 = 0.999
            //0.1- 9.99
            //0-9
            //1-10

            //range 1-1000
            randx = (int) (Math.random() * 1000) + 1;

            //range 1-700
            randy = (int) (Math.random() * 10) + 1;

            //variable and objects
            //create (construct) the objects needed for the game and load up
            astroPic = Toolkit.getDefaultToolkit().getImage("astronaut.png"); //load the picture
            asteroidPic = Toolkit.getDefaultToolkit().getImage("asteroid.jpeg"); //load the picture
            backgroundPic = Toolkit.getDefaultToolkit().getImage("stars.jpeg"); //load the picture
            astro = new Astronaut(randy, 500);
            astro.dx = -2;
            astro.height = 100;
            astro2 = new Astronaut(20, randy);
            astro2.dx = 2;
            astro2.height = 100;

            asteroid1 = new Asteroid(467, 400);
            asteroid2 = new Asteroid(randy, 467);
            asteroid1.dx = -asteroid1.dx;


    }// BasicGameApp()


//*******************************************************************************
//User Method Section
//
// put your code to do things here.

    // main thread
    // this is the code that plays the game after you set things up
    public void run() {

        //for the moment we will loop things forever.
        while (true) {

            moveThings();  //move all the game objects
            render();  // paint the graphics
            pause(2); // sleep for 10 ms
        }
    }


    public void moveThings() {
        //calls the move( ) code in the objects
        astro.move();
        astro2.move();
        asteroid1.move();
        asteroid2.move();
        crashing();

    }

    public void crashing() {
        //if astros crash into each other
        if (astro.hitbox.intersects(astro2.hitbox) && astro2.isAlive == true) {
            System.out.println("CRASH!!!");
            astro.dx = -astro.dx;
            astro2.dx = -astro2.dx;
            astro.dy = -astro.dy;
            astro2.dy = -astro2.dy;
            astro2.isAlive = false;

        }
        if (asteroid1.hitbox.intersects(asteroid2.hitbox) && asteroid2.isCrashing == false) {
            System.out.println("CRASH!!!");
            asteroid1.dx = -asteroid1.dx;
            asteroid2.dx = -asteroid2.dx;
            asteroid1.dy = -asteroid1.dy;
            asteroid2.dy = -asteroid2.dy;
            asteroid2.isAlive = false;
            asteroid2.height = asteroid2.height +10;
            asteroid2.isCrashing = true;

        }

        if (!asteroid1.hitbox.intersects(asteroid2.hitbox)){
            asteroid2.isCrashing = false;
        }

    }
    public void pause ( int time ) {
        //sleep
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {

        }
    }
        //Graphics setup method
        private void setUpGraphics () {
            frame = new JFrame("Application Template");   //Create the program window or frame.  Names it.

            panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
            panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
            panel.setLayout(null);   //set the layout

            // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
            // and trap input events (Mouse and Keyboard events)
            canvas = new Canvas();

            //step 2: add key listener to canvas
            canvas.addKeyListener(this);

            canvas.setBounds(0, 0, WIDTH, HEIGHT);
            canvas.setIgnoreRepaint(true);

            panel.add(canvas);  // adds the canvas to the panel.

            // frame operations
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
            frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
            frame.setResizable(false);   //makes it so the frame cannot be resized
            frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!

            // sets up things so the screen displays images nicely.
            canvas.createBufferStrategy(2);
            bufferStrategy = canvas.getBufferStrategy();
            canvas.requestFocus();
            System.out.println("DONE graphic setup");

        }


        //paints things on the screen using bufferStrategy
        private void render () {
            Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
            g.clearRect(0, 0, WIDTH, HEIGHT);
            g.drawImage(backgroundPic, 0, 0, WIDTH, HEIGHT, null);

            //draw the image of the astronaut
            g.drawImage(astroPic, astro.xpos, astro.ypos, astro.width, astro.height, null);

            if (astro2.isAlive == true) {
                g.drawImage(astroPic, astro2.xpos, astro2.ypos, astro2.width, astro2.height, null);
            }
            g.drawImage(asteroidPic, asteroid1.xpos, asteroid1.ypos, asteroid1.width, asteroid1.height, null);
            g.drawImage(asteroidPic, asteroid2.xpos, asteroid2.ypos, asteroid2.width, asteroid2.height, null);
            g.drawRect(astro.hitbox.x, astro.hitbox.y, astro.hitbox.width, astro.hitbox.height);
            g.drawRect(astro2.hitbox.x, astro2.hitbox.y, astro2.hitbox.width, astro2.hitbox.height);



            //end of drawing things
            g.dispose();
            bufferStrategy.show();
        }

    //step 3: add methods
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyCode());

        if(e.getKeyCode()==38){ //upo arrow
            System.out.println("Going up");
            //astro.xpos = 0;
            //astro.ypos =astro.ypos-10;
            //astro.dy=-Math.abs(astro.dy);
            astro.isNorth =true;

        }
        if(e.getKeyCode()==40){ //upo arrow
            System.out.println("Going down");
            //astro.xpos = 0;
            //astro.ypos =astro.ypos-10;
            //astro.dy=-Math.abs(astro.dy);
            astro.isSouth =true;

        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("I stopped touching " +e.getKeyCode());

        if (e.getKeyCode() ==38){//38 is up arrow
            System.out.println("Not going up");
            astro.isNorth = false;
        }

        if (e.getKeyCode() ==40){//38 is up arrow
            System.out.println("Not going down");
            astro.isSouth = false;
        }


        }

    }







