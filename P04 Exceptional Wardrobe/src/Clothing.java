//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Clothing Object to wear, add, remove, and modify clothing objects
// Course:   CS 300 Spring 2024
//
// Author:   Raymond Tu
// Email:    rktu2@wisc.edu
// Lecturer: Hobbes LeGault
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name:    (name of your pair programming partner)
// Partner Email:   (email address of your programming partner)
// Partner Lecturer's Name: (name of your partner's lecturer)
//
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//   ___ Write-up states that pair programming is allowed for this assignment.
//   ___ We have both read and understand the course Pair Programming Policy.
//   ___ We have registered our team prior to the team registration deadline.
//
//////////////////////// ASSISTANCE/HELP CITATIONS ////////////////////////////
//
// Persons:  Hobbes LeGault - Helped identify an infinite recursion in my getDescription()
//           method which was causing StackOverflowErrors on Piazza
// Online Sources: https://cs300-www.cs.wisc.edu/sp24/p04/doc/Clothing.html - Helped provide me
// the certain functionality of methods such as what methods are doing and how they respond to
// certain inputs, as well as providing method headers to help me understand how the methods
// should function in the context of a user vs. program.
//
///////////////////////////////////////////////////////////////////////////////
import java.time.LocalDate;

/**
 * An instantiable class that represents a piece of Clothing. For use in the Wardrobe Manager
 * project.
 */
public class Clothing {
  private String brand; // The brand for this piece of clothing.
  private String description; // The description for this piece of clothing.
  private LocalDate lastWornDate; // The date that this piece of clothing was last worn.
  private int timesWorn; // The number of times this piece of clothing has been worn.

  /**
   * Creates a new clothing object with the given description and brand. This piece of clothing
   * has been never worn, so its lastWornDate will be null.
   * @param description - the description for this piece of clothing
   * @param brand - the brand of this piece of clothing
   * @throws IllegalArgumentException - with a descriptive message if the description or brand is
   *         blank
   */
  public Clothing(String description, String brand) {
    this.lastWornDate = null;
    if (brand != null && !brand.trim().isEmpty()) {
      this.brand = brand;
    } else {
      throw new IllegalArgumentException("Brand is blank");
    }
    if (description != null && !description.trim().isEmpty()) {
      this.description = description;
    } else {
      throw new IllegalArgumentException("Description is blank");
    }
  }

  /**
   * Creates a new clothing object with the given description, brand, timesWorn and lastWorn date
   * values.
   * @param description - the description for this piece of clothing
   * @param brand - the brand of this piece of clothing
   * @param timesWorn - the number of times this piece of clothing has been worn
   * @param lastWornDate - the date that this piece of clothing was last worn
   * @throws IllegalArgumentException - with a descriptive message if the description or brand is
   *         blank
   */
  public Clothing(String description, String brand, int timesWorn, LocalDate lastWornDate) {
    this.lastWornDate = lastWornDate;
    this.timesWorn = timesWorn;
    if (brand != null && !brand.trim().isEmpty()) {
      this.brand = brand;
    } else {
      throw new IllegalArgumentException("Brand is blank");
    }
    if (description != null && !description.trim().isEmpty()) {
      this.description = description;
    } else {
      throw new IllegalArgumentException("Description is blank");
    }
  }

  /**
   * Updates the number of times this piece of clothing has been worn and the last worn date
   * @param year - the year of the new last worn date
   * @param month - the month of the new last worn date
   * @param day - the day of the last worn date
   * @throws IllegalArgumentException - with a descriptive message if the year is less than 1, or
   *         the month is outside the range [1,12]
   */
  public void wearClothing(int year, int month, int day) throws IllegalArgumentException {
    if (year > 1) {
      if (month > 12 || month < 1) {
        throw new IllegalArgumentException("Invalid Input for Month");
      } else {
        this.lastWornDate = LocalDate.of(year, month, day);
        this.timesWorn++;
      }
    } else {
      throw new IllegalArgumentException("Invalid Input for Year");
    }

  }

  /**
   * Getter for the description of this piece of clothing.
   * @return this clothing's description
   */
  public String getDescription() {
    return this.description; // CITE: Hobbes LeGault helped me fix infinite recursion problem
  }

  /**
   * Getter for the brand of this piece of clothing.
   * @return this clothing's brand
   */
  public String getBrand() {
    return this.brand;
  }

  /**
   * Getter for the date that this piece of clothing was last worn.
   * @return this clothing's last worn date
   */
  public LocalDate getLastWornDate() {
    return lastWornDate;
  }

  /**
   * Getter for the number of times this piece of clothing has been worn.
   * @return this clothing's number of times worn
   */
  public int getNumOfTimesWorn() {
    return this.timesWorn;
  }

  /**
   * Checks if Object o equals this clothing object, that is the current instance of Clothing.
   * @param o - the object to check if it equal to this piece of clothing
   * @return true if and only if 1) o is of type Clothing 2) the brands match ignoring case AND
   *         3) the descriptions match ignoring case, otherwise return false
   */

  @Override
  public boolean equals(Object o) {
    if (o instanceof Clothing) {
      if ((((Clothing) o).getBrand()).equalsIgnoreCase(this.brand)) {
        if (((Clothing) o).getDescription().equalsIgnoreCase(this.description)) {
          return true;
        } else {
          return false;
        }
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  /**
   * Creates and returns a string representation of this Clothing object.
   * @return the String representation of this Clothing object
   */
  @Override
  public String toString() {
    String returnString;
    if (this.lastWornDate == null) {
      returnString = this.description + "," + this.brand + "," + "null" + "," + this.timesWorn;
      return returnString;
    }
    if (this.lastWornDate.getMonthValue() > 0 && this.lastWornDate.getMonthValue() < 10) {
      if (this.lastWornDate.getDayOfMonth() >= 1 && this.lastWornDate.getDayOfMonth() < 10) {
        returnString =
            this.description + "," + this.brand + ",0" + this.lastWornDate.getMonthValue() + "/0" +
                this.lastWornDate.getDayOfMonth() + "/" + this.lastWornDate.getYear() + "," +
                this.timesWorn;
      } else {
        returnString =
            this.description + "," + this.brand + ",0" + this.lastWornDate.getMonthValue() + "/" +
                this.lastWornDate.getDayOfMonth() + "/" + this.lastWornDate.getYear() + "," +
                this.timesWorn;
      }
    } else {
      if (this.lastWornDate.getDayOfMonth() >= 1 && this.lastWornDate.getDayOfMonth() < 10) {
        returnString =
            this.description + "," + this.brand + "," + this.lastWornDate.getMonthValue() + "/0"
                + this.lastWornDate.getDayOfMonth() + "/" + this.lastWornDate.getYear() + "," +
                this.timesWorn;
      } else {
        returnString =
            this.description + "," + this.brand + "," + this.lastWornDate.getMonthValue() + "/" +
                this.lastWornDate.getDayOfMonth() + "/" + this.lastWornDate.getYear() + "," +
                this.timesWorn;
      }
    }
    return returnString;
  }
}
