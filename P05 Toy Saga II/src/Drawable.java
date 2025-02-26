//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Drawable Interface for Toys and Graphical Objects
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
 * This interface models drawable objects. Drawable objects have a draw() method, which takes no
 * parameters and returns nothing
 */
public interface Drawable {

  /**
   * Draws the object to the screen
   */
  public void draw();
}

