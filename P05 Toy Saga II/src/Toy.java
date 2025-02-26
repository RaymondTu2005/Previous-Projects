//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Toy Class for dragging, drawing, and moving Toys
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
 * This class models Toy objects in the P05 Toy Saga II program, which inherits from
 * GraphicObject and implements the MouseListener, and Movable interfaces.
 */
public class Toy extends GraphicObject implements MouseListener, Movable {
  private boolean isDragging; // Indicates whether this Toy object is being dragged or not

  /**
   * Constructs a new Toy object positioned at the center of the display window. The image assigned
   * to this Toy object is defined by the provided filename. A newly created Toy object is not
   * being dragged and have been rotated zero times.
   * @param filename - filename of the image of this Toy object.
   */
  public Toy(String filename) {
    super(filename);
  }

  /**
   * Constructs a new Toy object positioned at the specific x and y position within the display
   * window. The image assigned to this Toy object is defined by the provided filename. A newly
   * created Toy object is not being dragged and have been rotated zero times.
   * @param filename - filename of the image of this Toy object.
   * @param x - x-position (horizontal position) of this Toy object
   * @param y - y-position (vertical position) of this Toy object
   */
  public Toy(String filename, int x, int y) {
    super(filename, x, y);
  }

  /**
   * Draws this Toy to the display window at its current (x,y) position taking into account of
   * its moves. This method first moves this toy by calling its move() method. Then, it draws it
   * to the screen as a GraphicObject by calling the draw() method defined in the super class.
   * @see "Implements draw from the Drawable interface and overrides draw from GraphicObject"
   */
  @Override
  public void draw() {
    move();
    super.draw();
  }

  /**
   * Checks whether this Toy object is dragging
   * @return true if this toy object is dragging
   */
  public boolean isDragging() {
    return this.isDragging;
  }

  /**
   * Starts dragging this object by setting its instance field isDragging to true.
   */
  public void startDragging() {
    isDragging = true;
  }

  /**
   * Stops dragging this object by setting its instance field isDragging to false.
   */
  public void stopDragging() {
    isDragging = false;
  }

  /**
   * Moves this toy object with the specific dx, and dy moves. This toy should not get out of the
   * boundaries of the display window of the toy saga.
   * @param dx - horizontal move
   * @param dy - vertical move
   */
  protected void move(int dx, int dy) {
    this.x = this.x + dx;
    this.y = this.y + dy;
    if (this.x > toySaga.width) {
      this.x = toySaga.width;
    }
    if (this.y > toySaga.height) {
      this.y = toySaga.height;
    }
    if (this.y < 0) {
      this.y = 0;
    }
    if (this.x < 0) {
      this.x = 0;
    }
  }

  /**
   * If this toy is dragging, this method moves it following the mouse moves. The current
   * x-position of the mouse is given by toySaga.mouseX The current y-position of the mouse is
   * given by toySaga.mouseY The old x-position of the mouse (in the frame previous to the
   * current frame) is given by toySaga.pmouseX The old y-position of the mouse (in the frame
   * previous to the current frame) is given by toySaga.pmouseY.
   * @see "Implements move from movable interface"
   */
  public void move() {
    int currentX = toySaga.mouseX;
    int currentY = toySaga.mouseY;
    int previousX = toySaga.pmouseX;
    int previousY = toySaga.pmouseY;
    int dx = currentX - previousX;
    int dy = currentY - previousY;
    if (isDragging) {
      move(dx, dy);
    }
  }

  /**
   * Checks whether this Toy object is over a given point: (x, y) position in the screen.
   * @param x - x-position within the display window
   * @param y - y-position within the display window
   * @return true if this toy is over the specific (x,y) coordinates.
   */
  public boolean isOver(int x, int y) {
    int lowerX = this.x - (image.width / 2);
    int lowerY = this.y - (image.height / 2);
    int higherX = this.x + (image.width / 2);
    int higherY = this.y + (image.height / 2);
    if (x >= (lowerX) && x <= (higherX)) {
      if (y >= (lowerY) && y <= (higherY)) {
        return true;
      }
    }
    return false;
  }

  /**
   * If no toy is dragging within the toy saga, this method begins dragging this Toy if the mouse
   * is over it and no toy is dragging in the toy saga scene
   * @see "Implements onClick from MouseListener interface"
   */
  public void onClick() {
    if (!isDragging()) {
      if (isMouseOver()) {
        if (toySaga.noToyIsDragging()) {
          startDragging();
        }
      }
    }
  }

  /**
   * Stops dragging this Toy
   */
  public void onRelease() {
    stopDragging();
  }

  /**
   * Returns true the mouse is over this Toy
   * @return true if the mouse is over this toy.
   * @see "Implements onRelease from MouseListener interface"
   */
  public boolean isMouseOver() {
    if (isOver(toySaga.mouseX, toySaga.mouseY)) {
      return true;
    } else {
      return false;
    }
  }
}
