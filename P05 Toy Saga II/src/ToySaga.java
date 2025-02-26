//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Graphical User Interface for Drawing, and Moving a Toy Room
// Course:   CS 300 Spring 2024
//
// Author:   Raymond Tu
// Email:    rktu2@wisc.edu
// Lecturer: Hobbes LeGault
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name:    N/A
// Partner Email:   N/A
// Partner Lecturer's Name: N/A
//
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//   ___ Write-up states that pair programming is allowed for this assignment.
//   ___ We have both read and understand the course Pair Programming Policy.
//   ___ We have registered our team prior to the team registration deadline.
//
//////////////////////// ASSISTANCE/HELP CITATIONS ////////////////////////////
//
// Persons:        None
// Online Sources: CS300 Javadocs - Helped Provide Information on instance variables, instance
// and static methods, as well as the certain functionality pertaining to certain methods -
// https://cs300-www.cs.wisc.edu/sp24/p05/doc/package-summary.html
//
///////////////////////////////////////////////////////////////////////////////

import java.io.File;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * This class implements the main graphic user interface (GUI) of the p05 Toy Saga II program
 * which inherits from PApplet
 *
 */
public class ToySaga extends PApplet {

  // CONSTANTS
  // PATH to the folder of all images
  private static final String IMAGES_PATH = "images" + File.separator;

  // filename of the day background image of this toy saga
  protected static final String DAY_BACKGROUND = IMAGES_PATH + "backgroundDay.png";

  // filename of the night background image of this toy saga
  protected static final String NIGHT_BACKGROUND = IMAGES_PATH + "backgroundNight.png";

  // filename of the image of the bed
  protected static final String BED = IMAGES_PATH + "bed.png";

  // filename of the image of the nightstand
  protected static final String NIGHTSTAND = IMAGES_PATH + "nightstand.png";

  // filename of the image of the rug
  protected static final String RUG = IMAGES_PATH + "rug.png";

  // filename of the image of the car
  protected static final String CAR = IMAGES_PATH + "car.png";

  // filename of the image of the teddy bear
  protected static final String BEAR = IMAGES_PATH + "teddyBear.png";

  // filename of the image of the hoverball when it is on (night mode)
  protected static final String HOVERBALL_ON = IMAGES_PATH + "hoverBallOn.png";

  // filename of the image of the hoverball when it is off (day mode)
  protected static final String HOVERBALL_OFF = IMAGES_PATH + "hoverBallOff.png";

  // day mode
  protected static final String DAY_MODE = "DAY";

  // night mode
  protected static final String NIGHT_MODE = "NIGHT";

  // Maximum number of visible toys that can be stored in the drawableObjects list.
  private static final int MAX_TOYS_COUNT = 8;

  // other fields
  private static PImage backgroundImage; // PImage object that represents the background image

  private ArrayList<Drawable> drawableObjects;
  // The drawableObjects arraylist stores elements of type Drawable (interface Drawable) ONLY.

  private String mode;
  // mode represents the current mode of this ToySaga application.


  /**
   * Driver method that launches the application by calling this.runApplication()
   *
   * @param args list of input arguments if any
   */
  public static void main(String[] args) {
    PApplet.main("ToySaga");
  }

  /**
   * Gets the current mode of this Toy Saga app. The mode might be DAY or NIGHT.
   *
   * @return the current mode of this application
   */
  public String getMode() {
    return this.mode;
  }

  /**
   * Returns true if this ToySaga mode is NIGHT_MODE
   *
   * @return true if this ToySaga mode is NIGHT_MODE
   */
  public boolean isNightMode() {
    if (this.mode.equals(NIGHT_MODE)) {
      return true;
    } else {
      return false;
    }
  }


  /**
   * Switches the mode of this toy saga application and loads the background image of the switched
   * mode. <BR>
   *
   * Meaning, sets the mode to NIGHT_MODE if it was DAY_MODE and vice versa, and updates the
   * background image accordingly.
   */
  public void switchMode() {
    if (mode.equals(NIGHT_MODE)) {
      mode = DAY_MODE;
      backgroundImage = this.loadImage(DAY_BACKGROUND);

    } else {
      mode = NIGHT_MODE;
      backgroundImage = this.loadImage(NIGHT_BACKGROUND);

    }
  }

  /**
   * Sets the size of the display window of this graphic application
   * @see "Overrides settings in processing.core.PApplet"
   */
  @Override
  public void settings() {
    this.size(800, 600);
  }

  /**
   * Sets the title and defines the initial environment properties of this graphic application. <br>
   * This method initializes all the data fields defined in this class.
   * @see "Overrides setup in processing.core.PApplet"
   */
  @Override
  public void setup() {
    // sets the title and graphic environment properties of the display window
    this.getSurface().setTitle("P5 Toy Saga v2.0");
    this.textAlign(CENTER, CENTER); // horizontal alignment: center, vertical alignment: center
    this.imageMode(CENTER); // interprets the second and third parameters of image() as the
    // image’s center point.
    this.rectMode(CORNERS); // interprets the first two parameters of rect() as the location
    // of one corner, and the third and fourth parameters as the
    // location of the opposite corner.
    this.focused = true;// sets the processing program to be focused (true), meaning that
    // it is active and will accept input from mouse or keyboard
    this.mode = this.DAY_MODE; // Sets the starting mode to DAY_MODE
    backgroundImage = this.loadImage(DAY_BACKGROUND);
    drawableObjects = new ArrayList<>();
    SwitchButton.setProcessing(this);
    GraphicObject.setProcessing(this);
    drawableObjects.add(new GraphicObject(BED, 520, 270));
    drawableObjects.add(new SwitchButton(565, 20));
    drawableObjects.add(new GraphicObject(RUG, 220, 370));
    drawableObjects.add(new GraphicObject(NIGHTSTAND, 325, 240));
  }

  /**
   * This callback method continuously draws and updates the application display window. It is
   * automatically called directly after setup() and continuously executes until the program is
   * stopped.
   *
   * This method first draws the background image to the center of the screen. Then, it draws every
   * object stored in the drawableObjects list
   * @see "Overrides draw from processing.core.PApplet"
   */
  @Override
  public void draw() {
    this.image(backgroundImage, this.width / 2, this.height / 2);
    for (int i = 0; i < drawableObjects.size(); ++i) {
      drawableObjects.get(i).draw();
    }
  }

  /**
   * Callback method called once after every time the mouse button is pressed.
   *
   * This method calls the onClick() method on every instance of MouseListener stored in the
   * drawableObjects list
   * @see "Overrides mousePressed from processing.core.PApplet"
   */
  @Override
  public void mousePressed() {
    for (int i = 0; i < drawableObjects.size(); ++i) {
      if (drawableObjects.get(i) instanceof MouseListener) {
        ((MouseListener) drawableObjects.get(i)).onClick();
      }
    }
  }

  /**
   * Callback method called every time the mouse button is released.
   *
   * This method calls the onRelease() method on every instance of MouseListener stored in the
   * drawableObjects list
   * @see "Overrides mouseReleased from processing.core.PApplet"
   */
  @Override
  public void mouseReleased() {
    for (int i = 0; i < drawableObjects.size(); ++i) {
      if (drawableObjects.get(i) instanceof MouseListener) {
        ((MouseListener) drawableObjects.get(i)).onRelease();
      }
    }
  }

  /**
   * Callback method called once every time a key is pressed. The key that was pressed is returned
   * by the this.key() this method.<BR> The ToySaga.keyPressed() method performs the below actions
   * based on the pressed key: <BR>
   *
   * - Pressing 'c' or 'C' adds a new Car object at the mouse position if the MAX TOYS COUNT is not
   * reached. <BR> - Pressing 't' or 'T' adds a new TeddyBear object at the mouse position if the
   * MAX TOYS COUNT is not reached. <BR> - Pressing 'h' or 'H' adds a new Hoverball object at the
   * mouse position if the MAX TOYS COUNT is not reached. <BR> - Pressing 'd' or 'D' sets/switches
   * the mode to DAY_MODE and loads the DAY_BACKGROUND for the background image of this
   * application.
   * <BR> - Pressing 'n' or 'N' sets/switches the mode to NIGHT_MODE and loads the NIGHT_BACKGROUND
   * for the background image of this application. <BR>
   * @see "Overrides keyPressed in processing.core.PApplet"
   */
  @Override
  public void keyPressed() {

    switch (this.key) {
      case 'C':
      case 'c':
        if (getToyCount() < MAX_TOYS_COUNT) {
          drawableObjects.add(new Car(mouseX, mouseY));
        }
        break;
      case 'h':
      case 'H':
        if (getToyCount() < MAX_TOYS_COUNT) {
          drawableObjects.add(new Hoverball(mouseX, mouseY));
        }
        break;
      case 't':
      case 'T':
        if (getToyCount() < MAX_TOYS_COUNT) {
          drawableObjects.add(new TeddyBear(mouseX, mouseY));
        }
        break;
      case 'd':
      case 'D':
        if (mode.equals(DAY_MODE)) {
          break;
        } else {
          switchMode();
        }
        break;
      case 'n':
      case 'N':
        if (mode.equals(NIGHT_MODE)) {
          break;
        } else {
          switchMode();
        }
        break;
    }
  }

  /**
   * Returns true if NO Toy object is currently dragging. We assume that there is at most one object
   * being dragged at a given time.
   *
   * @return true if no toy is being dragged, or false otherwise.
   */
  public boolean noToyIsDragging() {
    int i;
    for (i = 0; i < drawableObjects.size(); ++i) {
      if (drawableObjects.get(i) instanceof Toy) {
        if (((Toy) drawableObjects.get(i)).isDragging()) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Gets the number of Toy instances present in this application
   *
   * @return the number of Toy objects stored in the drawableObjects list
   */
  public int getToyCount() {
    int size = 0;
    int i;
    for (i = 0; i < drawableObjects.size(); ++i) {
      if (drawableObjects.get(i) instanceof Toy) {
        size++;
      }
    }
    return size;
  }
}
