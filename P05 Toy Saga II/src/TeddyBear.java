//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    TeddyBear Object for moving and drawing TeddyBear Toys
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

import processing.core.PApplet;

/**
 * This class models a TeddyBear, which inherits from Toy. The properties of the bear include the
 * rotation, rotationDirection, callout, and ToySaga object
 */
public class TeddyBear extends Toy {
  private float rotation; // Current rotation amount of the bear
  private boolean rotationDirection; // The direction the bear is rotating
  private Callout callout; // The callout object associated with the bear

  /**
   * Constructs a new TeddyBear object positioned at the specific x and y position within the
   * display window. The image assigned to this TeddyBear object is defined by ToySaga.BEAR
   * filename.
   * @param x - x-position (horizontal position) of this TeddyBear
   * @param y - y-position (vertical position) of this TeddyBear
   */
  public TeddyBear(int x, int y) {
    super(ToySaga.BEAR, x, y);
    this.callout = new Callout(x, y);
  }

  /**
   * Returns the rotation amount of the TeddyBear
   * @return float The rotation amount of the TeddyBear
   */
  public float getRotation() {
    return this.rotation;
  }

  /**
   * Sets the rotation amount
   * @param rotation - The float-valued rotation amount (in radians)
   */
  public void setRotation(float rotation) {
    this.rotation = rotation;
  }

  /**
   * Sets the rotation direction. The value true for rotation direction means clockwise direction.
   * @param direction - The rotation direction (boolean valued).
   */
  public void setRotationDirection(boolean direction) {
    this.rotationDirection = direction;
  }

  /**
   * Returns the rotation direction
   * @return boolean The rotation direction
   */
  public boolean getRotationDirection() {
    return this.rotationDirection;
  }

  /**
   * Draws this teddy bear by calling the helper method drawTeddyBearNightMode() if the toySaga
   * mode is night mode.
   * Draws this teddy bear to the display window as an ordinary toy by calling the draw() method
   * defined in its super class if the toySaga mode is day mode.
   * @see "Implements draw from Drawable interface and overrides draw in Toy"
   */
  @Override
  public void draw() {
    if (toySaga.isNightMode()) {
      drawTeddyBearNightMode();
    } else {
      super.draw();
    }
  }

  /**
   * Provided method to draw this talking TeddyBear at night with respect to its moves
   */
  private void drawTeddyBearNightMode() {
    move();
    toySaga.pushMatrix(); // Save the current transformation matrix
    toySaga.translate(x, y); // Translate to the teddy bear’s position
    toySaga.rotate(rotation * PApplet.PI / 2); // Apply rotation
    if (toySaga.getMode() == "NIGHT") {
      toySaga.image(callout.image, 20f, -90f);
    }
    toySaga.image(image, 0.0f, 0.0f); // Draw the image at the rotated position
    toySaga.popMatrix(); // Restore the previous transformation matrix
  }


  /**
   * If the toySaga mode is day mode, this method moves this teddy bear as an ordinary toy by
   * calling the move() method defined in its super class.
   *
   * If the toySaga mode is night mode, this method moves this TeddyBear as follows:
   * - Get the current rotation amount and increment it by 3 degrees using PApplet.radians(3)
   * - Change the rotation direction to false if the currentRotation is greater than or equal to 30
   * radians
   * - Change the rotation direction to true if the currentRotation is less than or equal to -30
   * radians
   * - Set the bear's rotation amount appropriately based on the rotation increment and the rotation
   * direction
   * @see "Implements move from movable Interface and overrides move from Toy"
   */
  @Override
  public void move() {
    if (!toySaga.isNightMode()) {
      super.move();
    } else {
      if (getRotation() >= PApplet.radians(30)) {
        setRotationDirection(false);
      } else if (getRotation() <= PApplet.radians(-30)) {
        setRotationDirection(true);
      }
      if (getRotationDirection()) {
        setRotation(PApplet.radians(3) + getRotation());
      } else {
        setRotation(getRotation() - PApplet.radians(3));

      }
    }
  }
}
