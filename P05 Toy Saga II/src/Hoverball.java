//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Hoverball Object for Drawing and Moving Hoverball Toy
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
 * This class models a Hoverball, which inherits from Toy. Properties of the car include the x and
 * y position of the ball
 */
public class Hoverball extends Toy {

  /**
   * Constructs a new Hoverball whose image filename is ToySaga.HOVERBALL_OFF located at the
   * specified (x, y) position within the display window
   * @param x - x-position of this Hoverball
   * @param y - y-position of this Hoverball
   */
  public Hoverball(int x, int y) {
    super(ToySaga.HOVERBALL_OFF, x, y);
  }

  /**
   * Sets the image of this Hoverball to ToySaga.HOVERBALL_ON if the toySaga mode is NIGHT_MODE
   * and to ToSaga.HOVERBALL_OFF, otherwise.
   */
  private void switchOnOff() {
    if (toySaga.isNightMode()) {
      this.image = toySaga.loadImage(ToySaga.HOVERBALL_ON);
    } else {
      this.image = toySaga.loadImage(ToySaga.HOVERBALL_OFF);
    }
  }

  /**
   * This method first sets the image of this Hoverball to ToySaga.HOVERBALL_ON if the toySaga
   * mode is NIGHT_MODE and to ToSaga.HOVERBALL_OFF, otherwise. Then, it draws this Hoverball by
   * calling the draw() method defined in its super class.
   * @see "Implements draw in Drawable interface and overrides draw in Toy"
   */
  @Override
  public void draw() {
    switchOnOff();
    toySaga.image(this.image, this.x, this.y);
    super.draw();
  }

  /**
   * If the toySaga mode is night mode, this method bounces this Hoverball vertically by moving it
   * with dx equals to zero and dy equals to a factor of 6 * PApplet.sin(toySaga.frameCount * 0.1f)
   * using Math.round to cast the float to an int. If the toySaga mode is day mode, this method
   * moves this Hoverball as an ordinary Toy by calling the move() method defined in its super
   * class.
   * @see "Implements move in Movable interface and overrides move in Toy"
   */
  @Override
  public void move() {
    if (toySaga.isNightMode()) {
      int dY = (Math.round(6 * PApplet.sin(toySaga.frameCount * 0.1f)));
      move(0, dY);
    } else {
      super.move();
    }
  }
}
