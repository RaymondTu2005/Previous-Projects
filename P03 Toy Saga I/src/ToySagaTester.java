//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Tester Class for ToySaga Program to ensure correct functionality
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


/**
 * This class implements tester methods to ensure the correctness of the implementation of Furniture
 * and Toy classes in p03 Toy Saga I program.
 */
public class ToySagaTester {

  /**
   * This tester ensures the Furniture constructor which takes a String as input Furniture(String
   * name) correctly constructs a new Furniture object located at the center of the display window,
   * and assigned it a PImage and the name passed as input to the method call.
   *
   * @return true when this test verifies a correct functionality (ALL test scenarios PASS), and
   * false otherwise
   */
  public static boolean testFurnitureConstructor1Getters() {
    // Create at least one new Furniture object by called the constructor Furniture(String). The
    // String passed as input to the constructor call should be either "bed", "box", "nightstand",
    // or "rug".
    Furniture testFurniture = new Furniture("bed");
    // Ensure that the call of getX() on that Furniture object equals Utility.width() / 2
    int expected = Utility.width() / 2;
    int actual = testFurniture.getX();
    if (expected != actual) {
      return false;
    }
    // Ensure that the call of getY() on that Furniture object equals Utility.height() / 2
    expected = Utility.height() / 2;
    actual = testFurniture.getY();
    if (actual != expected) {
      return false;
    }
    // Ensure that the call of name() on that Furniture object returns the name passed as input to
    // the constructor call
    String expectedName = "bed";
    String actualName = testFurniture.name();
    if (expectedName != actualName) {
      return false;
    }
    // Ensure that the value of the image field on that Furniture object returns a NON-null reference.
    if (testFurniture.IMAGE == null) {
      return false;
    }
    return true; // Returns true if all the previous tests pass
  }

  /**
   * This tester ensures the Furniture constructor which takes a String, and two integers as input
   * Furniture(String name, int x, int y) correctly constructs a new Furniture object located at the
   * (x,y) input position, assigned it the name passed as input to the method call, and an image.
   *
   * @return true when this test verifies a correct functionality (ALL test scenarios PASS), and
   * false otherwise
   */
  public static boolean testFurnitureConstructor2Getters() {
    // Create at least one new Furniture object by called the constructor Furniture(String, int,
    // int). The String passed as input to the constructor call should be either "bed", "box",
    // "nightstand", or "rug".
    Furniture furnitureTester = new Furniture("nightstand", 50, 200);
    // Ensure that the call of getX() on that Furniture object equals the input x
    int expected = 50;
    int actual = furnitureTester.getX();
    if (actual != expected) {
      return false;
    }
    // Ensure that the call of getY() on that Furniture object equals the input y
    expected = 200;
    actual = furnitureTester.getY();
    if (actual != expected) {
      return false;
    }
    // Ensure that the call of name() on that Furniture object returns the name passed as input to
    // the constructor call
    String expectedName = "nightstand";
    String actualName = furnitureTester.name();
    if (expectedName != actualName) { return false; }
    // Ensure that the value of the image field on that Furniture object returns a NON-null reference.
    if (furnitureTester.IMAGE == null) { return false; }
    return true; // if all tests pass, return true, else false
  }

  /**
   * This tester ensures the Toy constructors, getters and setters of the x and y positions, and the
   * image field.
   *
   * @return true when this test verifies a correct functionality (ALL test scenarios PASS), and
   * false otherwise
   */
  public static boolean testToyConstructorsGettersSetters() {
    // This tester should check for the correctness of BOTH Toy(String) and Toy(String, int, int)
    // constructors, and the following setter and getter methods:
    Toy testerOne = new Toy("teddyBear", 50, 50);
    Toy testerTwo = new Toy("car");
    // getX(), getY()
    int expectedX = 50;
    int expectedY = 50;
    int actualX = testerOne.getX();
    int actualY = testerOne.getY();
    if (expectedX != actualX) { return false; }
    if (expectedY != actualY) { return false; }
    expectedX = (Utility.width() / 2);
    expectedY = (Utility.height() / 2);
    actualX = testerTwo.getX();
    actualY = testerTwo.getY();
    if (actualX != expectedX) { return false; }
    if (actualY != expectedY) { return false; }
    // setX(), setY()
    expectedX = 100;
    expectedY = 100;
    testerOne.setX(100);
    testerOne.setY(100);
    actualX = testerOne.getX();
    actualY = testerOne.getY();
    if (actualX != expectedX) { return false; }
    if (actualY != expectedY) { return false; }
    // The image field should contain a NON-null reference
    if (testerOne.IMAGE == null) { return false; }
    if (testerTwo.IMAGE == null) { return false; }
    // isDragging should return false on a new constructed Toy object
    boolean expected = false;
    boolean actualOne = testerOne.isDragging();
    boolean actualTwo = testerTwo.isDragging();
    if (expected != actualOne) { return false; }
    if (expected != actualTwo) { return false; }
    return true; // returns true if it passes all the following tests
  }

  /**
   * This tester ensures the correctness of Toy.startDragging() and Toy.stopDragging instance
   * methods
   *
   * @return true when this test verifies a correct functionality (ALL test scenarios PASS), and
   * false otherwise
   */
  public static boolean testToyStartStopDragging() {
    // This tester should construct at least one Toy object and call startDragging() and
    // stopDragging() methods on that object.
    Toy testerOne = new Toy("car", 50, 50);
    // Ensure the isDragging() method call returns true after every call of the startDragging()
    // method on the Toy object
    boolean expectedBefore = false;
    boolean expectedAfter = true;
    boolean actualBefore = testerOne.isDragging();
    testerOne.startDragging();
    boolean actualAfter = testerOne.isDragging();
    if (expectedBefore != actualBefore) { return false; }
    if (expectedAfter != actualAfter) { return false; }
    // Ensure the isDragging() method call returns false after every call of the stopDragging()
    // method on the Toy object
    actualAfter = testerOne.isDragging();
    if (expectedAfter != actualAfter) { return false; }
    testerOne.stopDragging();
    actualAfter = testerOne.isDragging();
    if (actualAfter != expectedBefore) { return false; }
    return true; // return true if all previous tests pass
  }

  /**
   * This tester ensures the correctness of Toy.move() method
   *
   * @return true when this test verifies a correct functionality (ALL test scenarios PASS), and
   * false otherwise
   */
  public static boolean testToyMove() {
    // This tester should construct at least one Toy object at a given x,y position
    Toy tester = new Toy("teddyBear", 300, 300);
    // Every call on move(int dx, int dy) method should add dx and dy to the current x and y
    // position of the Toy object, respectively.
    int expectedX = 400;
    int expectedY = 450;
    tester.move(100, 150);
    int actualX = tester.getX();
    int actualY = tester.getY();
    if (expectedX != actualX) { return false; }
    if (expectedY != actualY) { return false; }
    // Try calling move method with positive and negative dx and dy inputs
    expectedX = 250;
    expectedY = 200;
    tester.move(-150,-250);
    actualX = tester.getX();
    actualY = tester.getY();
    if (actualX != expectedX) { return false; }
    if (actualY != expectedY) { return false; }
    return true; // ONLY return true if all previous test cases pass
  }

  /**
   * This tester ensures the correctness of Toy.rotate() method.
   *
   * @return true when this test verifies a correct functionality (ALL test scenarios PASS), and
   * false otherwise
   * @author Mouna
   */
  public static boolean testToyRotate() {
    // This method's implementation is entirely provided to you
    // Create two Toy objects
    Toy car1 = new Toy("car");
    Toy car2 = new Toy("car");

    // Ensures getRotationsCount() returns zero when called on newly constructed Toy objects
    if (car1.getRotationsCount() != 0) {
      System.out.println(
          "Toy.getRotationsCount() should return zero when called on a new created Toy object.");
      return false;
    }

    if (car2.getRotationsCount() != 0) {
      System.out.println(
          "Toy.getRotationsCount() should return zero when called on a new created Toy object.");
      return false;
    }
    // rotate car1 5 times
    for (int i = 0; i < 5; i++) {
      car1.rotate();
    }
    // Ensure the getRotationsCount returns the expected output
    if (car1.getRotationsCount() != 5) {
      System.out.println(
          "Toy.getRotationsCount() did not return the expected output after calling the rotate() " + "method multiple times.");
      return false;
    }
    // rotate car2 3 times
    for (int i = 0; i < 3; i++) {
      car2.rotate();
    }
    // Ensure the getRotationsCount returns the expected output
    if (car2.getRotationsCount() != 3) {
      System.out.println(
          "Toy.getRotationsCount() did not return the expected output after calling the rotate() " + "method multiple times.");
      return false;
    }
    return true; // Test passes with no errors
  }

  /**
   * This tester checks the correctness of Toy.isOver(int, int) method
   *
   * @return true when this test verifies a correct functionality (ALL test scenarios PASS), and
   * false otherwise
   */
  public static boolean testToyIsOverPoint() {
    // This tester should check for the correctness of Toy.isOver(int x, int y) method
    // This tester should construct at least one Toy object at a given (x,y) position
    // At least 4 scenarios should be considered.
    // TeddyBear below ranges from (250, 247) to (350, 353)
    Toy testerToy = new Toy("teddyBear", 300, 300);
    // (1) Calling the isOver(int, int) method on that Toy object passing it a point defined by x
    // and y coordinates defined inside the area of the image of the toy should return true
    boolean expected = true;
    boolean actual = testerToy.isOver(330, 330);
    if (expected != actual) { return false; }
    // (2) Calling the isOver(int, int) method with a point (x,y) defined outside the area of the
    // image of the toy should return false.
    expected = false;
    actual = testerToy.isOver(0,0);
    if (actual != expected) { return false; }
    // Call the rotate() method one time or an odd number of times on the toy. This should
    // rotate the image of the Toy object PI/2 clockwise so that the width and height of the toy are
    // expected to be switched.
    testerToy.rotate(); // Rotated TeddyBear should now range from (247, 250) to (353, 350)

    // (3) Call the isOver(int, int) method with a point (x,y) inside the area of the Toy,
    // considering this change on the width and height dimensions, should return true.
    expected = true;
    // Test coordinates previously outside the range before rotation, but inside after rotation
    actual = (testerToy.isOver(248, 300));
    if (expected != actual) { return false; }
    // (4) Call the isOver(int, int) method with a point (x,y) outside the area of the Toy,
    // considering this change on the width and height dimensions, should return false.
    expected = false;
    // Test coordinate that were previously inside the range, which are now outside due to rotation
    actual = testerToy.isOver(300, 352);
    if (expected != actual) { return false; }
    return true; // Returns true if all previous cases pass
  }

  /**
   * This tester checks the correctness of Toy.isOver(Furniture) method
   *
   * @return true when this test verifies a correct functionality (ALL test scenarios PASS), and
   * false otherwise
   */
  public static boolean testToyIsOverFurniture() {
    // This tester should check for the correctness of Toy.isOver(Furniture other) method
    // Create a Furniture object at a given (x, y) position.
    // Bed created below ranges from (150,184) to (450,416) - Confirmed by test
    Furniture testerFurn = new Furniture("bed", 300, 300);
    // You can then create at least three Toy objects:
    // (1) one intersecting with the Furniture object,
    // TeddyBear below ranges from (370,327) to (470,433) - Confirmed by test
    Toy testerOne = new Toy("teddyBear", 420, 380);
    boolean expectedOne = true;
    boolean actualOne = testerOne.isOver(testerFurn);
    if (expectedOne != actualOne) { return false; }
    // (2) one enclosed by it,
    // Car below ranges from (270,283) to (330, 317) - Confirmed by test
    Toy testerTwo = new Toy("car", 300, 300);
    boolean expectedTwo = true;
    boolean actualTwo = testerTwo.isOver(testerFurn);
    if (expectedTwo != actualTwo) { return false; }
    // (3) one not overlapping with the Furniture.
    // Car below ranges from (470, 483) to (530, 517) - Confirmed by test
    Toy testerThree = new Toy("car", 500, 500);
    boolean expectedThree = false;
    boolean actualThree = testerThree.isOver(testerFurn);
    if (expectedThree != actualThree) { return false; }
    // Calling the isOver(Furniture) on each of the Toy objects should return the expected output.
    return true; // returns true if all the previous tests pass
  }

  /**
   * Runs all the tester methods defined in this class
   *
   * @return true when this test verifies a correct functionality (ALL test scenarios PASS), and
   * false otherwise
   */
  public static boolean runAllTests() {
    System.out.println("Class Furniture Testers:");
    boolean test1Result = testFurnitureConstructor1Getters();
    System.out.println("testFurnitureConstructor1Getters: " + (test1Result ? "PASS" : "FAIL"));

    boolean test2Result = testFurnitureConstructor2Getters();
    System.out.println("testFurnitureConstructor2Getters: " + (test2Result ? "PASS" : "FAIL"));

    System.out.println();
    System.out.println("Class Toy Testers:");
    boolean test3Result = testToyConstructorsGettersSetters();
    System.out.println("testToyConstructorsGettersSetters: " + (test3Result ? "PASS" : "FAIL"));

    boolean test4Result = testToyStartStopDragging();
    System.out.println("testToyStartStopDragging: " + (test4Result ? "PASS" : "FAIL"));

    boolean testToyMove = testToyMove();
    System.out.println("testToyMove: " + (testToyMove ? "PASS" : "FAIL"));

    boolean testToyRotate = testToyRotate();
    System.out.println("testToyRotate: " + (testToyRotate ? "PASS" : "FAIL"));

    boolean testToyIsOverPoint = testToyIsOverPoint();
    System.out.println("testToyIsOverPoint: " + (testToyIsOverPoint ? "PASS" : "FAIL"));

    boolean testToyIsOverFurniture = testToyIsOverFurniture();
    System.out.println("testToyIsOverFurniture: " + (testToyIsOverFurniture ? "PASS" : "FAIL"));

    return test1Result && test2Result && test3Result && test4Result && testToyMove && testToyRotate && testToyIsOverPoint && testToyIsOverFurniture;
  }


  /**
   * Driver method to run all the tests defined in this class
   *
   * @param args list of command-line input arguments if any.
   */
  public static void main(String[] args) {
    // DO NOT MAKE ANY CHANGES TO THE IMPLEMENTATION OF THIS METHOD
    Utility.runTester();
  }

}
