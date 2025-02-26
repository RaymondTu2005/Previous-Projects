//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Wardrobe Manager to find, remove, add, and update clothes information
// Course:   CS 300 Spring 2024
//
// Author:   Raymond Tu
// Email:    rktu2@wisc.edu
// Lecturer: Hobbes LeGault
//
//////////////////////// ASSISTANCE/HELP CITATIONS ////////////////////////////
//
// Online Sources: https://www.tutorialspoint.com/java/number_parseint.htm - Helped me
//                 specifically with coding my getMostRecentlyWorn method as I was having trouble
//                 converting the string date into an int date where I could actually use it to
//                 compare to other dates and find which one was the most recent date.
// Online Sources: https://cs300-www.cs.wisc.edu/wp/wp-content
//                 /uploads/2024/spring/p1/doc/WardrobeManager.html - Provided the method
//                 descriptions, parameters, and return values to help me code my methods and
//                 know what it is supposed to do and what it returns in certain scenarios such
//                 as the wardrobe being empty or full and knowing which values to return
//
///////////////////////////////////////////////////////////////////////////////

/**
 * This program contains methods for the Wardrobe Manager. Each method allows a user to control
 * their wardrobe doing things such as finding clothes of a certain brand, description, time last
 * worn, and update their wardrobe accordingly. Additionally, it allows users to remove clothes
 * never worn before and find the specific location of clothes in their wardrobe.
 *
 * @author Raymond Tu
 */
public class WardrobeManager {

  /**
   * Checks whether the oversize array defined by the provided two-dimensional array of strings
   * and its size contains an entry with the provided description AND brand.
   *
   * @param description - a general description of the clothing item
   * @param brand - the brand of the clothing item, or "handmade"
   * @param wardrobe - a two-dimensional array of Strings, which stores wardrobe entries.
   *                 wardrobe[i][0] contains a description of item i, wardrobe[i][1] contains its
   *                 brand name, and wardrobe[i][2] contains its last-worn date formatted as
   *                 "YYYY-MM-DD", or "never"
   * @param wardrobeSize - number of items currently stored in the wardrobe, assumed to be correct
   * @return true if the clothing exists in the wardrobe, false if it does not
   */
  public static boolean containsClothing(String description, String brand, String[][] wardrobe,
      int wardrobeSize) {
    int i;
    for (i = 0; i < wardrobeSize; ++i) {
      if (wardrobe[i][0].equalsIgnoreCase(description)) {
        if (wardrobe[i][1].equalsIgnoreCase(brand)) {
          return true;
        }
      }
    }
    return false; // return false after checking all indexes of the array
    // CITE: Helped me understand to return false in this case - CS300 JavaDoc
  }

  /**
   * Appends (adds at the end) a new clothing item to the given wardrobe oversize array defined by
   * the provided two-dimensional array of strings and its size, and returns the new size of the
   * oversize array.
   *
   * @param description - a general description of the clothing item
   * @param brand - the brand of the clothing item, or "handmade"
   * @param wardrobe - a two-dimensional array of Strings, which stores wardrobe entries.
   *                 wardrobe[i][0] contains a description of item i, wardrobe[i][1] contains its
   *                 brand name, and wardrobe[i][2] contains its last-worn date formatted as
   *                 "YYYY-MM-DD", or "never"
   * @param wardrobeSize - number of items currently stored in the wardrobe, assumed to be correct
   * @return the number of items in the wardrobe after trying to add the new item
   */
  public static int addClothing(String description, String brand, String[][] wardrobe,
      int wardrobeSize) {
    boolean expected = false;
    boolean actual = containsClothing(description, brand, wardrobe, wardrobeSize);
    if (expected != actual) {
      return wardrobeSize; // check to see if it already exists
    }
    if (wardrobeSize >= wardrobe.length) {
      return wardrobeSize; // check to see if it is full
    }
    wardrobe[wardrobeSize] = new String[] {description, brand, "never"};
    wardrobeSize = wardrobeSize + 1;
    return wardrobeSize;
  }

  /**
   * Finds the location (index) of a provided clothing item in an oversize array defined by the
   * provided two-dimensional array of strings and its size.
   *
   * @param description - a general description of the clothing item
   * @param brand - the brand of the clothing item, or "handmade"
   * @param wardrobe - a two-dimensional array of Strings, which stores wardrobe entries.
   *                 wardrobe[i][0] contains a description of item i, wardrobe[i][1] contains its
   *                 brand name, and wardrobe[i][2] contains its last-worn date formatted as
   *                 "YYYY-MM-DD", or "never"
   * @param wardrobeSize - number of items currently stored in the wardrobe, assumed to be correct
   * @return the index of the clothing item if it is present, or -1 if it is not
   */
  public static int indexOfClothing(String description, String brand, String[][] wardrobe,
      int wardrobeSize) {
    int i;
    for (i = 0; i < wardrobeSize; ++i) {
      if ((wardrobe[i][0]).equalsIgnoreCase(description)) {
        if ((wardrobe[i][1]).equalsIgnoreCase(brand)) {
          return i;
        }
      }
    }
    return -1;
    // CITE: Helped me understand to return this specific value in this case - CS300 JavaDoc
  }

  /**
   * Locates the clothing item matching the provided description/brand in the oversize array
   * defined by the provided two-dimensional array of strings and size, and updates the last-worn
   * date to the provided date value (assumed to be formatted as "YYYY-MM-DD").
   *
   * @param description - a general description of the clothing item
   * @param brand - the brand of the clothing item, or "handmade"
   * @param date - the date on which this clothing item was worn, as "YYYY-MM-DD"
   * @param wardrobe - a two-dimensional array of Strings, which stores wardrobe entries.
   *                 wardrobe[i][0] contains a description of item i, wardrobe[i][1] contains its
   *                 brand name, and wardrobe[i][2] contains its last-worn date formatted as
   *                 "YYYY-MM-DD", or "never"
   * @param wardrobeSize - number of items currently stored in the wardrobe, assumed to be correct
   * @return true if the item's last-worn date was successfully updated, false otherwise
   */
  public static boolean wearClothing(String description, String brand, String date,
      String[][] wardrobe, int wardrobeSize) {
    int notPresentIndex = -1;
    int actualIndex = indexOfClothing(description, brand, wardrobe, wardrobeSize);
    if (actualIndex == notPresentIndex) {
      return false;
    } // check if clothes doesn't exist
    wardrobe[actualIndex][2] = date;
    return true;
  }

  /**
   * Counts the number of clothing items in the oversize array defined by the provided
   * two-dimensional array of strings and size which have a brand that matches the provided brand.
   *
   * @param brand - the brand of a clothing item, or "handmade"
   * @param wardrobe - a two-dimensional array of Strings, which stores wardrobe entries.
   *                 wardrobe[i][0] contains a description of item i, wardrobe[i][1] contains its
   *                 brand name, and wardrobe[i][2] contains its last-worn date formatted as
   *                 "YYYY-MM-DD", or "never"
   * @param wardrobeSize - number of items currently stored in the wardrobe, assumed to be correct
   * @return the number of items in wardrobe which have a brand matching the provided string
   */
  public static int getBrandCount(String brand, String[][] wardrobe, int wardrobeSize) {
    int i;
    int numOfMatches = 0;
    for (i = 0; i < wardrobeSize; ++i) {
      if (wardrobe[i][1].equalsIgnoreCase(brand)) {
        numOfMatches = numOfMatches + 1;
      }
    }
    return numOfMatches;
  }

  /**
   * Counts the number of clothing items in the oversize array defined by the provided
   * two-dimensional array of strings and size which have a last-worn date of "never".
   *
   * @param wardrobe - a two-dimensional array of Strings, which stores wardrobe entries.
   *                 wardrobe[i][0] contains a description of item i, wardrobe[i][1] contains its
   *                 brand name, and wardrobe[i][2] contains its last-worn date formatted as
   *                 "YYYY-MM-DD", or "never"
   * @param wardrobeSize - number of items currently stored in the wardrobe, assumed to be correct
   * @return the number of clothing items in wardrobe which have a last-worn date of "never"
   */
  public static int getNumUnwornClothes(String[][] wardrobe, int wardrobeSize) {
    int i;
    int numOfUnworn = 0;
    for (i = 0; i < wardrobeSize; ++i) {
      if ((wardrobe[i][2]).equalsIgnoreCase("never")) {
        numOfUnworn = numOfUnworn + 1;
      }
    }
    return numOfUnworn;
  }

  /**
   * Finds the most recently worn item of clothing in the oversize array defined by the provided
   * two-dimensional array of strings and size.
   *
   * @param wardrobe - a two-dimensional array of Strings, which stores wardrobe entries.
   *                 wardrobe[i][0] contains a description of item i, wardrobe[i][1] contains its
   *                 brand name, and wardrobe[i][2] contains its last-worn date formatted as
   *                 "YYYY-MM-DD", or "never"
   * @param wardrobeSize - number of items currently stored in the wardrobe, assumed to be correct
   * @return the smallest index of a clothing item in the wardrobe with the most recent last-worn
   * date, or -1 if the wardrobe is empty
   */
  public static int getMostRecentlyWorn(String[][] wardrobe, int wardrobeSize) {
    int i;
    int indexOfMostRecentWorn = -1;
    int mostRecentWornMonth = -1;
    int mostRecentWornDay = -1;
    int mostRecentWornYear = -1;
    if ((getNumUnwornClothes(wardrobe, wardrobeSize)) == wardrobeSize) {
      return -1;
      // CITE: Helped me understand to return this specific value in this case - CS300 JavaDoc
    }
    // Checks to see if any clothes have been worn at all
    if (wardrobeSize <= 0) {
      return -1;
      // CITE: Helped me understand to return this specific value in this case - CS300 JavaDoc
    }
    for (i = 0; i < wardrobeSize; ++i) {
      if (!(wardrobe[i][2]).equalsIgnoreCase("never")) {
        if ((Integer.parseInt(wardrobe[i][2].substring(0, 4))) > mostRecentWornYear) {
          // CITE: Helped me understand the functionality of the parseInt method, which
          // parameters to pass, and what the method returns - Tutorials Point
          mostRecentWornYear = (Integer.parseInt(wardrobe[i][2].substring(0, 4)));
          mostRecentWornMonth = (Integer.parseInt(wardrobe[i][2].substring(5, 7)));
          mostRecentWornDay = (Integer.parseInt(wardrobe[i][2].substring(8, 10)));
          indexOfMostRecentWorn = i;
          continue;
        }
        if ((Integer.parseInt(wardrobe[i][2].substring(0, 4))) >= mostRecentWornYear) {
          if (((Integer.parseInt(wardrobe[i][2].substring(5, 7))) > mostRecentWornMonth)) {
            mostRecentWornYear = (Integer.parseInt(wardrobe[i][2].substring(0, 4)));
            mostRecentWornMonth = (Integer.parseInt(wardrobe[i][2].substring(5, 7)));
            mostRecentWornDay = (Integer.parseInt(wardrobe[i][2].substring(8, 10)));
            indexOfMostRecentWorn = i;
            continue;
          }
        }
        if ((Integer.parseInt(wardrobe[i][2].substring(0, 4))) >= mostRecentWornYear) {
          if (((Integer.parseInt(wardrobe[i][2].substring(5, 7))) >= mostRecentWornMonth)) {
            if ((Integer.parseInt(wardrobe[i][2].substring(8, 10))) > mostRecentWornDay) {
              mostRecentWornYear = (Integer.parseInt(wardrobe[i][2].substring(0, 4)));
              mostRecentWornMonth = (Integer.parseInt(wardrobe[i][2].substring(5, 7)));
              mostRecentWornDay = (Integer.parseInt(wardrobe[i][2].substring(8, 10)));
              indexOfMostRecentWorn = i;
            }
          }
        }
      }
    }
    return indexOfMostRecentWorn;
  }

  /**
   * Removes the single clothing item entry at the provided index of the oversize array defined by
   * the provided two-dimensional array of strings and size, updates the array to uphold the
   * oversize array requirements, and returns the new size of the array.
   *
   * @param index - the index of the clothing item to remove from the wardrobe
   * @param wardrobe - a two-dimensional array of Strings, which stores wardrobe entries.
   *                 wardrobe[i][0] contains a description of item i, wardrobe[i][1] contains its
   *                 brand name, and wardrobe[i][2] contains its last-worn date formatted as
   *                 "YYYY-MM-DD", or "never"
   * @param wardrobeSize - number of items currently stored in the wardrobe, assumed to be correct
   * @return size of the oversize array after attempting to remove the item at the provided index
   */
  public static int removeClothingAtIndex(int index, String[][] wardrobe, int wardrobeSize) {
    int i;
    if (index > wardrobeSize - 1 || index < 0) {
      return wardrobeSize;
    }
    wardrobe[index] = null;
    for (i = (index); i < wardrobeSize - 1; ++i) {
      wardrobe[i] = wardrobe[i + 1];
    }
    wardrobe[wardrobeSize - 1] = null;
    return wardrobeSize - 1;
  }

  /**
   * Removes any clothing item from the oversize array defined by the provided two-dimensional
   * array of strings and size where the item's last-worn date is "never", and returns the updated
   * size of the oversize array.
   *
   * @param wardrobe - a two-dimensional array of Strings, which stores wardrobe entries.
   *                 wardrobe[i][0] contains a description of item i, wardrobe[i][1] contains its
   *                 brand name, and wardrobe[i][2] contains its last-worn date formatted as
   *                 "YYYY-MM-DD", or "never"
   * @param wardrobeSize - number of items currently stored in the wardrobe, assumed to be correct
   * @return the size of the oversize array after all never-worn items have been removed
   */
  public static int removeAllUnworn(String[][] wardrobe, int wardrobeSize) {
    int i;
    int numRemoved = 0;
    for (i = wardrobeSize - 1; i >= 0; --i) {
      if ((wardrobe[i][2]).equalsIgnoreCase("never")) {
         removeClothingAtIndex(i, wardrobe, wardrobeSize);
         numRemoved = numRemoved + 1;
      }
    }
    return wardrobeSize - numRemoved;
  }
}
