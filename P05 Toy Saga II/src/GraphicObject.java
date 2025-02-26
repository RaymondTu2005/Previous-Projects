//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Graphical Object for drawing all objects to ToySaga
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


/**
 * This class models GraphicObject objects, inheriting from object. This is an implementation of
 * the Drawable interface
 */
public class GraphicObject extends Object implements Drawable {
  protected static ToySaga toySaga; // Reference to the ToySaga graphic application where this
  // object will be displayed. It represents the display window of this graphic application

  protected processing.core.PImage image; // Processing image of this GraphicObject
  protected int x; // x-position of this GraphicObject object in the display window
  protected int y; // y-position of this GraphicObject object in the display window

  /**
   * Constructs a new GraphicObject object positioned at the specific x and y position within the
   * display window. The image assigned to this GraphicObject object is defined by the provided
   * filename.
   *
   * @param filename - filename of the image of this graphic object
   * @param x - x-position (horizontal position) of this GraphicObject object
   * @param y - y-position (vertical position) of this GraphicObject object
   */
  public GraphicObject(String filename, int x, int y) {
    this.x = x;
    this.y = y;
    this.image = toySaga.loadImage(filename);
  }

  /**
   * Constructs a new GraphicObject object positioned at the center of the display window. The image
   * assigned to this GraphicObject object is defined by the provided filename.
   *
   * @param filename - filename of the image of this graphic object
   */
  public GraphicObject(String filename) {
    this.image = toySaga.loadImage(filename);
    this.x = toySaga.width / 2;
    this.y = toySaga.height / 2;
  }

  /**
   * Mutates the image of this GraphicObject by reloading it
   *
   * @param filename - filename of the image to load
   */
  public void setImage(String filename) {
    image = toySaga.loadImage(filename);
  }

  /**
   * Draws the image of this GraphicObject to the display window at its current (x,y) position
   * @see "Implements draw from the Drawable interface"
   */
  public void draw() {
    toySaga.image(image, this.x, this.y);
  }

  /**
   * Gets the x-position of this GraphicObject object
   *
   * @return the x-position of this GraphicObject object
   */
  public int getX() {
    return this.x;
  }

  /**
   * Gets the y-position of this GraphicObject object
   *
   * @return the y-position of this GraphicObject object
   */
  public int getY() {
    return this.y;
  }

  /**
   * Sets the ToySaga PApplet object where this graphic object will be drawn
   * @param toysaga - PApplet object that represents the display window of the ToySaga app
   */
  public static void setProcessing(ToySaga toysaga) {
    toySaga = toysaga;
  }
}
