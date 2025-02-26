//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Toy class for creating, modifying, rotating, moving, and drawing Toys to ToySaga
// Course:   CS 300 Spring 2024
//
// Author:   Raymond Tu
// Email:    rktu2@wisc.edu
// Lecturer: Hobbes LeGault
//
//////////////////////// ASSISTANCE/HELP CITATIONS ////////////////////////////
//
// Persons:         None
// Online Sources:  None
//
///////////////////////////////////////////////////////////////////////////////

import processing.core.PImage;
import java.io.File;

/**
 * This class implements the Toy objects in the P03 Toy Saga I project
 */
public class Toy {
  public final PImage IMAGE; // Store a PImage which will be drawn to represent this toy item.
  private int x; // Represents x position of Toy object in display window
  private int y; // Represents y position of Toy object in display window
  private boolean isDragging; // Indicates whether object is being dragged or not
  private int rotations; // Counts the times this object has been rotated 90 degrees clockwise

  /**
   * This constructor automatically assigns an x and y coordinate corresponding to the center of a
   * toy object, as well as gets the correct PImage file based on the given name
   *
   * @param name - The name of the toy object
   * @param x - The x coordinate of the toy object
   * @param y - The y coordinate of the toy object
   */
  public Toy(String name, int x, int y) {
    this.IMAGE = Utility.loadImage("images" + File.separator + name + ".png");
    this.x = x;
    this.y = y;
    this.isDragging = false;
    this.rotations = 0;
  }

  /**
   * This constructor automatically assigns the center of a toy object to be in the middle of the
   * Utility window, as well as gets the correct PImage file based on the given name
   *
   * @param name - The name of the toy object
   */
  public Toy(String name) {
    this.IMAGE = Utility.loadImage("images" + File.separator + name + ".png");
    this.x = (Utility.width() / 2);
    this.y = (Utility.height() / 2);
    this.isDragging = false;
    this.rotations = 0;
  }

  /**
   * This method returns the x coordinate corresponding to the center of a toy object
   *
   * @return this.x - The x coordinate of the toy object
   */
  public int getX() {
    return this.x;
  }

  /**
   * This method returns the y coordinate corresponding to the center of a toy object
   *
   * @return this.y - The y coordinate of the toy object
   */
  public int getY() {
    return this.y;
  }

  /**
   * This method modifies the x coordinate of a toy object based on the inputs
   *
   * @param x - the new x coordinate to be assigned to the toy object
   */
  public void setX(int x) {
    this.x = x;
  }

  /**
   * This method modifies the y coordinate of a toy object based on the inputs
   *
   * @param y - the new x coordinate to be assigned to the toy object
   */
  public void setY(int y) {
    this.y = y;
  }

  /**
   * This method returns the number of times an object has rotated in its existence
   *
   * @return this.rotations - The number of times the object has rotated
   */
  public int getRotationsCount() {
    return this.rotations;
  }

  /**
   * This method returns a boolean value of whether an object is being moved or not
   *
   * @return true if the object is currently being dragged, false otherwise
   */
  public boolean isDragging() {
    return this.isDragging;
  }

  /**
   * This method assigns the isDragging instance variable of a toy object to be true
   */
  public void startDragging() {
    this.isDragging = true;
  }

  /**
   * This method assigns the isDragging instance variable of a toy object to be false
   */
  public void stopDragging() {
    this.isDragging = false;
  }

  /**
   * This method increments the number of rotations on a toy object by one
   */
  public void rotate() {
    this.rotations = (this.rotations + 1);
  }

  /**
   * This method modifies the position of a toy object based on the inputted displacement against
   * the current position. If the new position is outside the Utility window, set it to the
   * nearest value inside the display window.
   *
   * @param dx - the amount of displacement in the x direction the toy object should travel
   * @param dy - the amount of displacement in the y direction the toy object should travel
   */
  public void move(int dx, int dy) {
    this.x = (this.x + dx);
    this.y = (this.y + dy);
    if (this.x < 0) {
      this.x = 0;
    }
    if (this.x > Utility.width()) {
      this.x = Utility.width();
    }
    if (this.y < 0) {
      this.y = 0;
    }
    if (this.y > Utility.height()) {
      this.y = Utility.height();
    }
  }

  /**
   * This method draws the toy object to the Utility Window at the current cursor position,
   * moving it if it is currently dragging based on the current mouse position.
   */
  public void draw() {
    if (this.isDragging) {
      int dx = Utility.mouseX() - Utility.pmouseX();
      int dy = Utility.mouseY() - Utility.pmouseY();
      move(dx, dy);
    }
    drawToyImage();
  }

  /**
   * Helper method to draw an image accounting for any rotations to the screen.
   * The implementation of this method is fully provided in the write-up.
   */
  private void drawToyImage() {
    Utility.pushMatrix();
    Utility.translate(x, y);
    Utility.rotate(this.rotations * Utility.PI / 2);
    Utility.image(IMAGE, 0.0f, 0.0f);
    Utility.popMatrix();
  }

  /**
   * This method takes in two parameters referencing x and y coordinates, and checks to see if
   * the corresponding toy object is within those coordinates respectively.
   *
   * @param x - the current x position to be checked to see if it is inside the toy object
   * @param y - the current y position to be checked to see if it is inside the toy object
   * @return true if the given coordinates are currently over the toy object, false if not
   */
  public boolean isOver(int x, int y) {
    int rangeOfToy = 0;
    int widthOfToy = 0;
    if (getRotationsCount() % 2 == 0) {
      rangeOfToy = (IMAGE.height / 2);
      widthOfToy = (IMAGE.width / 2);
    }
    if (getRotationsCount() % 2 == 1) {
      rangeOfToy = (IMAGE.width / 2);
      widthOfToy = (IMAGE.height / 2);
    }

    if (x >= (this.x - widthOfToy) && x <= (this.x + widthOfToy)) {
      if (y >= (this.y - rangeOfToy) && y <= (this.y + rangeOfToy)) {
        return true;
      }
    }
    return false;
  }

  /**
   * This method takes in a Furniture object and compares the Toy object to see if the Toy object
   * is currently overlapping the given Furniture object
   *
   * @param other - A furniture object that is used as a reference to see if a toy object is
   *              currently overlapping it
   * @return true if the Toy object is currently over the Furniture object, false otherwise
   */
  public boolean isOver(Furniture other) {
    int leftFurniture = (other.getX() - (other.IMAGE.width / 2));
    int rightFurniture = (other.getX() + (other.IMAGE.width / 2));
    int topFurniture = (other.getY() - (other.IMAGE.height / 2));
    int bottomFurniture = (other.getY() + (other.IMAGE.height / 2));
    int rangeOfToy = 0;
    int widthOfToy = 0;
    if (getRotationsCount() % 2 == 0) {
      rangeOfToy = (this.IMAGE.height / 2);
      widthOfToy = (this.IMAGE.width / 2);
    }
    if (getRotationsCount() % 2 == 1) {
      rangeOfToy = (this.IMAGE.width / 2);
      widthOfToy = (this.IMAGE.height / 2);
    }
    int leftOfToy = (this.x - widthOfToy);
    int rightOfToy = (this.x + widthOfToy);
    int topOfToy = (this.y - rangeOfToy);
    int bottomOfToy = (this.y + rangeOfToy);

    if (rightFurniture >= leftOfToy && leftFurniture <= rightOfToy && bottomFurniture >= topOfToy
        && topFurniture <= bottomOfToy) {
      return true;
    } else {
      return false;
    }
  }
}
