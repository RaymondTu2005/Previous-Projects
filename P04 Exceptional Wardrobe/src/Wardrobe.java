//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Wardrobe Manager to Remove, Add, and Modify things in the Wardrobe
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
// Persons:         None
// Online Sources:  https://cs300-www.cs.wisc.edu/sp24/p04/doc/Wardrobe.html - CS300 Javadocs
// helped provide me instructions on hwo to write the Wardrobe class, as well as the certain
// functionality of methods and how the methods respond to certain inputs.
//
///////////////////////////////////////////////////////////////////////////////

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * An instantiable class that represents a Wardrobe. A Wardrobe object contains and manages
 * Clothing. For use in the Wardrobe Manager project.
 */
public class Wardrobe {
  private Clothing[] wardrobe; // An oversize compacted array that stores the clothing inside
  // the wardrobe
  private int wardrobeSize; // The number of clothing inside this wardrobe

  /**
   * Creates a new Wardrobe object that is empty with the given capacity.
   *
   * @param capacity
   * @throws IllegalArgumentException - with a descriptive message if the capacity is non-positive
   *                                  (less than or equal to 0)
   */
  public Wardrobe(int capacity) {
    if (capacity <= 0) {
      throw new IllegalArgumentException("Capacity cannot be less than or equal to 0");
    } else {
      wardrobe = new Clothing[capacity];
    }
  }

  /**
   * Getter for the capacity of this wardrobe.
   *
   * @return the number of pieces of clothing this wardrobe can potentially hold
   */
  public int capacity() {
    return wardrobe.length;
  }

  /**
   * Getter for the size of this wardrobe.
   *
   * @return the number of pieces of clothing in this wardrobe
   */
  public int size() {
    return this.wardrobeSize;
  }

  /**
   * Gets the array that contains all the Clothing in the wardrobe.
   *
   * @return the wardrobe array
   */
  protected Clothing[] getArray() {
    return wardrobe;
  }

  /**
   * Finds and returns the piece of clothing with the matching description and brand.
   *
   * @param description - the description of the piece of clothing to find
   * @param brand       - the brand of the piece of clothing to find
   * @return the Clothing object in the Wardrobe that matches the given description and brand
   * @throws NoSuchElementException - with descriptive message if the clothing does not exist in the
   *                                wardrobe
   */
  public Clothing getClothing(String description, String brand) {
    int i;
    for (i = 0; i < wardrobeSize; ++i) {
      String clothingDescription = wardrobe[i].getDescription();
      String clothingBrand = wardrobe[i].getBrand();
      if (description.equalsIgnoreCase(clothingDescription) && brand.equalsIgnoreCase(
          clothingBrand)) {
        return (wardrobe[i]);
      }
    }
    throw new NoSuchElementException("Clothing item does not exist in the wardrobe");
  }

  /**
   * Adds a piece of clothing at the end of the wardrobe. If the wardrobe does not have room for the
   * piece of clothing, the wardrobe expands by doubling in capacity. Then adds the new piece of
   * clothing.
   *
   * @param toAdd - the piece of clothing to add to the wardrobe
   * @throws IllegalArgumentException - with a descriptive message if toAdd is already in the
   *                                  wardrobe
   */
  public void addClothing(Clothing toAdd) throws IllegalArgumentException {
    String toAddDescription = toAdd.getDescription();
    String toAddBrand = toAdd.getBrand();
    for (int i = 0; i < wardrobeSize; ++i) {
      String compareDescription = wardrobe[i].getDescription();
      String compareBrand = wardrobe[i].getBrand();
      if (compareDescription.equalsIgnoreCase(toAddDescription) && toAddBrand.equalsIgnoreCase(
          compareBrand)) {
        throw new IllegalArgumentException("Clothing item already exists in wardrobe");
      }
    }
    if (wardrobeSize == wardrobe.length) {
      Clothing[] wardrobeCopy = new Clothing[wardrobe.length * 2];
      for (int i = 0; i < wardrobeSize; ++i) {
        wardrobeCopy[i] = wardrobe[i];
      }
      wardrobe = new Clothing[wardrobeCopy.length];
      for (int i = 0; i < wardrobeSize; ++i) {
        wardrobe[i] = wardrobeCopy[i]; // Array made with wardrobe size doubled and same elements
      }
      for (int i = wardrobeSize; i < wardrobe.length; ++i) {
        wardrobe[i] = null; // Make sure null elements are inside elements not used
      }
      wardrobe[wardrobeSize] = toAdd;
      wardrobeSize++;
      return;
    }
    wardrobe[wardrobeSize] = toAdd;
    wardrobeSize++;
  }

  /**
   * Wears the piece of Clothing in this Wardrobe equal to the provided Clothing on the given date.
   *
   * @param toWear - the piece of clothing in the Wardrobe that we want to wear
   * @param year   - the year that it will be worn
   * @param month  - the month that it will be worn
   * @param day    - the day that it will be worn
   * @throws IllegalArgumentException - with a descriptive message if the year is less than 1, or
   *                                  the month is outside the range [1,12]
   */
  public void wearClothing(Clothing toWear, int year, int month, int day) {
    for (int i = 0; i < wardrobeSize; ++i) {
      if (toWear.equals(wardrobe[i])) {
        if (year > 0) {
          if (month < 13 && month > 0) {
            wardrobe[i].wearClothing(year, month, day);
          } else {
            throw new IllegalArgumentException("Month outside 1-12 range");
          }
        } else {
          throw new IllegalArgumentException("Year must be greater than 1");
        }
      }
    }
  }

  /**
   * Removes the piece of clothing from the wardrobe that has a matching description and brand.
   *
   * @param description - the description of the piece of clothing to remove
   * @param brand       - the brand of the piece of clothing to remove
   * @throws IllegalStateException  - with a descriptive message if the wardrobe is empty
   * @throws NoSuchElementException - with a descriptive message if the piece of clothing is not in
   *                                the wardrobe
   */
  public void removeClothing(String description, String brand) {
    if (wardrobeSize == 0) {
      throw new IllegalStateException("Wardrobe is empty");
    }
    for (int i = 0; i < wardrobeSize; ++i) {
      Clothing nextToCheck = wardrobe[i];
      String clothingDescription = nextToCheck.getDescription();
      String clothingBrand = nextToCheck.getBrand();
      if (description.equalsIgnoreCase(clothingDescription) && clothingBrand.equalsIgnoreCase(
          brand)) {
        wardrobe[i] = null;
        for (int j = i; j < wardrobeSize - 1; ++j) { // Make compact
          wardrobe[j] = wardrobe[j + 1];
        }
        wardrobe[wardrobeSize - 1] = null;
        wardrobeSize = wardrobeSize - 1;
        return;
      }
    }
    throw new NoSuchElementException("Clothing is not in the wardrobe");
  }

  /**
   * Removes all pieces of clothing from the wardrobe whose last worn date is BEFORE the given day,
   * month, and year.
   *
   * @param year  - the year of the date to use to remove clothes
   * @param month - the month of the date to use to remove clothes
   * @param day   - the day of the date to use to remove clothes
   */
  public void removeAllClothingWornBefore(int year, int month, int day) {
    if (year > 1 && month > 0 && month < 13 && day > 0 && day < 32) {
      LocalDate compareBefore = LocalDate.of(year, month, day);
      if (wardrobeSize > 0) {
        int constantSize = wardrobeSize;
        for (int i = constantSize - 1; i >= 0; --i) {
          if (wardrobe[i] != null) {
            if (wardrobe[i].getNumOfTimesWorn() == 0) {
              String removeDescription = wardrobe[i].getDescription();
              String removeBrand = wardrobe[i].getBrand();
              removeClothing(removeDescription, removeBrand);
            } else if (wardrobe[i].getLastWornDate() != null) {
              LocalDate compareTo = wardrobe[i].getLastWornDate();
              if ((compareTo.isBefore(compareBefore))) {
                String removeDescription = wardrobe[i].getDescription();
                String removeBrand = wardrobe[i].getBrand();
                removeClothing(removeDescription, removeBrand);
              }
            }
          }
        }
      }
    }
  }

  /**
   * Removes all pieces of clothing from the wardrobe who have been worn less times than the given
   * threshold.
   *
   * @param threshhold - the upperbound (exclusive) of number of times worn
   */
  public void removeAllClothingWornNumTimes(int threshhold) {
    int unmodifiedSize = wardrobeSize;
    for (int i = unmodifiedSize - 1; i >= 0; --i) {
      if (wardrobe[i] != null) {
        int toCompare = wardrobe[i].getNumOfTimesWorn();
        if (toCompare < threshhold) {
          String removeDescription = wardrobe[i].getDescription();
          String removeBrand = wardrobe[i].getBrand();
          removeClothing(removeDescription, removeBrand);
        }
      }
    }
  }

  /**
   * Creates a new Clothing object based on the given String formatted "description,brand,
   * lastWornDate,timesWorn".
   *
   * @param str - the String parse to make a Clothing object
   * @return a Clothing object with the pieces of information in the given string
   * @throws ParseException - with a descriptive message if the string does not have the 4 required
   *                        pieces of information OR if there was an issue converting pieces of
   *                        information to an int or Date object
   */
  public static Clothing parseClothing(String str) throws ParseException {
    String[] splitInfo = str.split(",");
    if (splitInfo[0] == null || splitInfo[1] == null || splitInfo[0].trim()
        .isEmpty() || splitInfo[1].trim().isEmpty() || splitInfo.length != 4) {
      throw new ParseException("Does not have the required 4 pieces of info", 0);
    }
    if (splitInfo[2].equals("null")) {
      Clothing newClothingParse = new Clothing(splitInfo[0], splitInfo[1], 0, null);
      return newClothingParse;
    } else {
      try {
        String[] splitDate = splitInfo[2].split("/");
        if (splitDate.length != 3) {
          throw new ParseException("Incorrect date format", 0);
        }
        if (splitDate[0].trim().length() != 2 || splitDate[1].trim()
            .length() != 2 || splitDate[2].trim().length() != 4) {
          throw new ParseException("Incorrect character length for date", 0);
        }
        int month = Integer.parseInt(splitDate[0]);
        int day = Integer.parseInt(splitDate[1]);
        int year = Integer.parseInt(splitDate[2]);
        if (month >= 13 || month <= 0 || day >= 32 || day <= 0 || year < 0) {
          throw new ParseException("Date is out of bounds", 0);
        }
        LocalDate newClothingDate = LocalDate.of(year, month, day);
        Clothing newClothing =
            new Clothing(splitInfo[0], splitInfo[1], Integer.parseInt(splitInfo[3]),
                newClothingDate);
        return newClothing;
      } catch (NumberFormatException e) {
        throw new ParseException("Error converting string to a number", 0);
      } catch (IndexOutOfBoundsException e) {
        throw new ParseException("Issue converting string to date or int", 0);
      }
    }
  }

  /**
   * Creates and returns a string representation of this Wardrobe object. Each piece of clothing in
   * the wardrobe should be printed in order on a new line enclosed in [] brackets. The last line
   * should NOT have a new line character.
   *
   * @return - the String representation of this Wardrobe object
   */
  @Override
  public String toString() {
    String returnValue = "";
    if (wardrobeSize != 0) {
      for (int i = 0; i < wardrobeSize - 1; ++i) {
        if (wardrobe[i] != null) {
          returnValue += "[" + wardrobe[i].toString() + "]" + "\n";
        }
      }
      if (wardrobe[wardrobeSize - 1] != null) {
        returnValue += "[" + wardrobe[wardrobeSize - 1].toString() + "]";
      }
    }
    return returnValue;
  }

  /**
   * Loads all of pieces of clothing into this wardrobe from the designated file. Each piece of
   * clothing in the Wardrobe is written on its own line, formatted as description,brand,
   * lastWornDate,timesWorn. The date must be formatted MM/DD/YYYY.
   *
   * @param saveFile - the File that the information should be read from
   * @return true if ANY of the lines from the file were parsed successfully into Clothing for this
   * Wardrobe, false otherwise
   */
  public boolean loadFromFile(File saveFile) {
    int numRead = 0;
    int numAddedToArray = 0;
    String[] contentsOfFile = new String[10000];
    Scanner scnr = null;
    try {
      scnr = new Scanner(saveFile);
      while (scnr.hasNextLine()) {
        String toRead = scnr.nextLine();
        if (toRead != null && toRead.trim().length() > 0) {
          contentsOfFile[numAddedToArray] = toRead;
          numAddedToArray++;
        }
      }
    } catch (FileNotFoundException e) {
      System.out.println("File cannot be loaded");
    }
    for (int i = 0; i < numAddedToArray; ++i) {
      try {
        Clothing toAdd = parseClothing(contentsOfFile[i]);
        addClothing(toAdd);
        numRead++;
      } catch (ParseException e) {
        System.out.println("Cannot parse line to Clothing object");
      }
    }

    if (numRead == 0) {
      return false;
    } else {
      return true;
    }
  }

  /**
   * Saves all of pieces of clothing in this wardrobe to the designated file. Each piece of clothing
   * in the Wardrobe is written on its own line, formatted as description,brand,
   * lastWornDate,timesWorn. The date must be formatted MM/DD/YYYY.
   *
   * @param saveFile - the File that the information should be written to
   * @return true if the file saved successfully, false otherwise
   */
  public boolean saveToFile(File saveFile) {
    PrintWriter toWrite = null;
    boolean written = false;
    try {
      toWrite = new PrintWriter(saveFile);
      for (int i = 0; i < wardrobeSize; ++i) {
        toWrite.println(wardrobe[i].toString());
      }
    } catch (FileNotFoundException e) {
    } finally {
      toWrite.close();
      written = true;
    }
    return written;
  }
}

