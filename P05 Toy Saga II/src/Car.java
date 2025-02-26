//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    ToySaga Car Object for Moving and Drawing a Toy Car
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
 * This class models a Car, which inherits from Toy. Properties of the car include the
 * moveDirection (a boolean) which makes the car travel right if true and left otherwise
 */
public class Car extends Toy {
  private static int absoluteSpeed = 8; // Speed of move of cars, initially set to 8. All cars
  // have the same absolute move speed
  private int speed; // Speed of the horizontal movement of this car
  private boolean isMovingRightward; // Indicates whether this car is travelling rightward or
  // leftward

  /**
   * Constructs a new Car object positioned at the specific x and y position within the display
   * window. The image assigned to this Car object is defined by the ToySaga.CAR filename. When
   * initially created, a car is set to be moving rightward and its speed is set to absoluteSpeed.
   * @param x - x-position (horizontal position) of this car
   * @param y - y-position (vertical position) of this car
   */
  public Car(int x, int y) {
    super(ToySaga.CAR, x, y);
    this.isMovingRightward = true;
    this.speed = absoluteSpeed;
  }

  /**
   * Provided method to draw a car at night with respect to its move direction
   */
  private void drawCarNightMode() {
    toySaga.pushMatrix();
    toySaga.rotate(0.0f);
    toySaga.translate(x, y);
    if (!isMovingRightward) {
      toySaga.scale(-1.0f, 1.0f);
    }
    toySaga.image(image, 0.0f, 0.0f);
    toySaga.popMatrix();
  }

  /**
   * If the toySaga mode is night mode, this method first moves this car, than draw it by calling
   * drawCarNightMode() helper method. Otherwise, this method draws this car as an ordinary toy
   * if the mode is day mode.
   * @see "This method implements the draw method from Drawable and overrides draw in the Toy
   * Class"
   */
  @Override
  public void draw() {
    if (!toySaga.isNightMode()) {
      toySaga.image(this.image, this.x, this.y);
      super.move();
    } else {
      move();
      drawCarNightMode();
    }
  }

  /**
   * Sets the absolute speed of car toys
   * @param speed - new absolute speed to be assigned to the class car
   */
  public static void setSpeed(int speed) {
    absoluteSpeed = speed;
  }

  /**
   * Gets the absolute speed of car toys
   * @return the absolute speed of car toys
   */
  public static int getSpeed() {
    return absoluteSpeed;
  }

  /**
   * Flips the Car's move direction from true to false or false to true and sets the speed of this
   * car to -speed.
   */
  public void flipMoveDirection() {
    if (isMovingRightward) {
      this.isMovingRightward = false;
      speed = speed * -1;
    } else {
      this.isMovingRightward = true;
      speed = speed * -1;
    }
  }

  /**
   * If the toySaga mode is day mode, this car moves as an ordinary toy (meaning call the move()
   * method defined in the super class). If the toySaga mode is night mode, this method increment
   * the car's x position by its speed. This car moves horizontally, meaning no changes will be
   * made to its y-coordinate.While moving rightward, if this car reaches (isOver) the right or the
   * left border of the screen, if flips move direction.
   * @see "Implements move in the Movable Interface and Overrides move from Toy"
   */
  @Override
  public void move() {
    if (!toySaga.isNightMode()) {
      super.move();
    } else {
      this.x = this.x + speed;
      if (isOver(toySaga.width, this.y) || isOver(0, this.y)) {
        flipMoveDirection();
      }
    }
  }
}
