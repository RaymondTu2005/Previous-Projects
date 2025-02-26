//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Furniture class for adding, moving, naming, and drawing furniture to ToySaga
// Course:   CS 300 Spring 2024
//
// Author:   Raymond Tu
// Email:    rktu2@wisc.edu
// Lecturer: Hobbes LeGault
//
//////////////////////// ASSISTANCE/HELP CITATIONS ////////////////////////////
//
// Persons:        None
// Online Sources: None
//
///////////////////////////////////////////////////////////////////////////////

import processing.core.PImage;
import java.io.File;

/**
 * This class implements the furniture object in the P03 Toy Saga I project
 */
public class Furniture {
  public final PImage IMAGE; // PImage which will be drawn to represent a furniture item
  private String name; // Holds the name of this furniture item
  private int x; // Represents x-position of this furniture object in the display window
  private int y; // Represents y-position of this furniture object in the display window

  /**
   * This constructor automatically assigns an x and y coordinate corresponding to the center of a
   * furniture object, as well as gets the correct PImage file based on the given name
   *
   * @param name - The name of the furniture object
   * @param x - The x coordinate of the furniture object
   * @param y - The y coordinate of the furniture object
   */
  public Furniture(String name, int x, int y) { // Initializes variables
    this.name = name;
    this.x = x;
    this.y = y;
    this.IMAGE = Utility.loadImage("images" + File.separator + name + ".png");
  }
  /**
   * This constructor automatically assigns the center of a furniture object to be in the middle of
   * the Utility window, as well as gets the correct PImage file based on the given name
   *
   * @param name - The name of the furniture object
   */
  public Furniture(String name) { // Initializes variables
    this.name = name;
    this.x = (Utility.width() / 2);
    this.y = (Utility.height() / 2);
    this.IMAGE = Utility.loadImage("images" + File.separator + name + ".png");
  }

  /**
   * Returns the x position of a Furniture object, where the x position refers to the center of
   * the object
   *
   * @return this.x - The current x coordinate position of the Furniture object
   */
  public int getX() {
    return this.x;
  }

  /**
   * Returns the y position of a Furniture object, where the y position refers to the center of
   * the object
   *
   * @return this.y - The current y coordinate position of the Furniture object
   */
  public int getY(){
    return this.y;
  }

  /**
   * Returns the name of a specific Furniture object
   *
   * @return this.name - the name of the Furniture object
   */
  public String name() {
    return this.name;
  }

  /**
   * Draws a specific furniture object to the Utility Display at the objects coordinates
   *
   */
  public void draw() { // Draws the image to the screen
    Utility.image(IMAGE, x, y);
  }
}

