//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Tester for Wardrobe Manager
// Course:   CS 300 Spring 2024
//
// Author:   Raymond Tu
// Email:    rktu2@wisc.edu
// Lecturer: Hobbes LeGault
//
//////////////////////// ASSISTANCE/HELP CITATIONS ////////////////////////////
//
// Persons: None
// Online Sources: None
//
///////////////////////////////////////////////////////////////////////////////

import java.util.Arrays;

/**
 * This class acts as a tester input for the Wardrobe Manager, calling its methods and
 * making sure that all of them function properly. Each method is designed to test a specific
 * aspect of a method existing in Wardrobe Manager and look for bugs and/or incorrect return
 * values.
 *
 * @author Raymond Tu
 */
public class WardrobeManagerTester {

  //// CONTAINS
  /**
   * Test method for checking the wardrobe for a specific clothing piece based on description and
   * brand for an empty wardrobe array.
   *
   * @return false if any of the conditions we are verifying are not what we expect; true ONLY if
   *         all of our expectations are correct
   */
  public static boolean testContainsEmpty() {
    // (1) set up the test variables and call the method we are testing - EXACT MATCH
    String [][] wardrobe = {null, null, null, null, null};
    String [][] expectedWardrobe = {null, null, null, null, null};
    boolean expected = false;
        boolean actual = WardrobeManager.containsClothing("black t-shirt", "Nike",
            wardrobe, 0);
    // (2) verify that the expected return value and the actual return value match
    if (actual != expected) { return false; }
    // (3) since this method should not modify the array, let's check it against our copy:
    if (!Arrays.deepEquals(wardrobe,expectedWardrobe)) { return false; }
    // (4) if all of those checks pass, NOW we can say we passed the test
    return true;
  }

  /**
   * PROVIDED: example test method for verifying whether an item is already in the wardrobe.
   * @return false if any of the conditions we are verifying are not what we expect; true ONLY if
   *         all of our expectations are correct
   */
  public static boolean testContainsTrue() {
    // (1) set up the test variables and call the method we are testing - EXACT MATCH
    String[][] wardrobe = {{"black t-shirt", "Hanes", "never"},
        {"dark blue jeans", "Levi", "never"}, null, null, null};
    String[][] wardrobeCopy = Arrays.copyOf(wardrobe, wardrobe.length);
    int size = 2;
    boolean expected = true;
    boolean actual = WardrobeManager.containsClothing("black t-shirt", "Hanes", wardrobe, size);
    // (2) verify that the expected return value and the actual return value match
    if (expected != actual) { return false; }
    // (3) another test method call, this time with case difference (that should still match!)
    actual = WardrobeManager.containsClothing("dark blue jeans", "LEVI", wardrobe, size);
    if (expected != actual) { return false; }
    // (4) since this method should not modify the array, let's check it against our copy:
    if (!Arrays.deepEquals(wardrobe, wardrobeCopy)) { return false; }
    // (5) if all of those checks pass, NOW we can say we passed the test
    return true;
  }
  /**
   * Test method for checking a wardrobe for a specific clothing item that does not exist in the
   * wardrobe
   *
   * @return false if any of the conditions we are verifying are not what we expect; true ONLY if
   *         all of our expectations are correct
   */
  public static boolean testContainsFalse() {
    // (1) set up the test variables and call the method we are testing - EXACT MATCH
    String[][] wardrobe = {{"black t-shirt", "Hanes", "never"},
        {"dark blue jeans", "Levi", "never"}, null, null, null};
    String[][] wardrobeCopy = Arrays.copyOf(wardrobe, wardrobe.length);
    int size = 2;
    boolean expected = false;
    boolean actual = WardrobeManager.containsClothing("white t-shirt", "Abercrombie",wardrobe, 2 );
    // (2) verify that the expected return value and the actual return value match
    if (actual != expected) { return false; }
    // (3) another test method call, this time with case difference (that should still match!)
    actual = WardrobeManager.containsClothing("purple pants", "H&M", wardrobe, 2);
    if (actual != expected) { return false; }
    // (4) since this method should not modify the array, let's check it against our copy:
    if (!Arrays.deepEquals(wardrobe,wardrobeCopy)) { return false; }
    // (5) if all of those checks pass, NOW we can say we passed the test
    return true;
  }

  //// ADD

  /**
   * PROVIDED: example test method for adding a new clothing item to an EMPTY oversize array.
   * @return false if any of the conditions we are verifying are not what we expect; true ONLY if
   *         all of our expectations are correct
   */
  public static boolean testAddToEmpty() {
    // (1) set up the test variables and call the method we are testing
    String[][] empty = new String[10][];
    int size = 0;
    int expected = 1;
    int actual = WardrobeManager.addClothing("green crop top", "H&M", empty, size);

    // (2) verify the expected return value
    if (expected != actual) { return false; }

    // (3) verify that the provided array was updated correctly
    if (empty[0] == null) { return false; }
    if (!empty[0][0].equalsIgnoreCase("green crop top") || !empty[0][1].equalsIgnoreCase("H&M") ||
        !empty[0][2].equals("never")) { return false; }

    // (4) verify that NOTHING ELSE was changed unexpectedly
    for (int i=1; i<empty.length; i++) {
      if (empty[i] != null) { return false; }
    }

    // (5) if all of those checks pass, NOW we can say that we passed the test
    return true;
  }
  /**
   * Test method for adding a clothing item to a wardrobe that is already partially filled but
   * not full
   *
   * @return false if any of the conditions we are verifying are not what we expect; true ONLY if
   *         all of our expectations are correct
   */
  public static boolean testAddNonEmpty() {
    // (1) set up the test variables and call the method we are testing
    String[][] wardrobe = {{"black t-shirt", "Hanes", "never"},
        {"dark blue jeans", "Levi", "never"}, null, null, null};
    String[][] expectedWardrobe = {{"black t-shirt", "Hanes", "never"},
        {"dark blue jeans", "Levi", "never"}, {"white hoodie", "Hours", "never"}, null, null};
    int expectedSize = 3;
    int actualSize = WardrobeManager.addClothing("white hoodie", "Hours", wardrobe, 2);
    // (2) verify that the expected return value and the actual return value match
    if (actualSize != expectedSize) { return false; }
    // (3) since this method should modify the array, let's check it against our correct array:
    if (!Arrays.deepEquals(expectedWardrobe,wardrobe)) { return false; }
    // (4) if all of those checks pass, NOW we can say we passed the test
    return true;
  }
  /**
   * Test method for adding a clothing item to a wardrobe that already contains the same item,
   * verifying that it does not add the clothing item as a duplicate
   *
   * @return false if any of the conditions we are verifying are not what we expect; true ONLY if
   *         all of our expectations are correct
   */
  public static boolean testAddDuplicate() {
    // (1) set up the test variables and call the method we are testing - EXACT MATCH
    String[][] wardrobe = {{"black t-shirt", "Hanes", "never"},
        {"dark blue jeans", "Levi", "never"}, null, null, null};
    String[][] wardrobeCopy = Arrays.copyOf(wardrobe, wardrobe.length);
    int expectedSize = 2;
    int actualSize = WardrobeManager.addClothing("black t-shirt", "hanes", wardrobe, 2);
    // (2) verify that the expected return value and the actual return value match
    if (actualSize != expectedSize) { return false; }
    actualSize = WardrobeManager.addClothing("dark blue jeans", "levi", wardrobe, 2);
    if (actualSize != expectedSize) { return false; }
    // (3) verify that the provided array was not updated
    if (!Arrays.deepEquals(wardrobeCopy, wardrobe)) { return false; }
    // (4) if all of those checks pass, NOW we can say we passed the test
    return true;
  }
  /**
   * Test method for adding a clothing item to a wardrobe that is already full, making sure that
   * the wardrobe is not changed and that no items are added
   *
   * @return false if any of the conditions we are verifying are not what we expect; true ONLY if
   *         all of our expectations are correct
   */
  public static boolean testAddToFull() {
    // (1) set up the test variables and call the method we are testing - EXACT MATCH
    String [][] wardrobe = {{"black t-shirt", "Hanes", "2023-12-19"},
        {"grey UW hoodie", "gildan", "2020-03-16"}, {"dark blue jeans", "Levi", "2024-01-25"},
        {"green cabled sweater", "handmade", "never"}};
    String [][] wardrobeCopy = Arrays.copyOf(wardrobe,wardrobe.length);
    int expectedSize = 4;
    int actualSize = WardrobeManager.addClothing("Blue hoodie", "Essentials", wardrobe, 4);
    // (2) verify that the expected return value and the actual return value match
    if (actualSize != expectedSize) { return false; }
    // (3) since this method should not modify the array, let's check it against our copy:
    if (!Arrays.deepEquals(wardrobe, wardrobeCopy)) { return false; } // check if modified
    // (4) if all of those checks pass, NOW we can say we passed the test
    return true;
  }
  /**
   * Test method for finding a specific index of an article of clothing that does not exist in
   * the wardrobe
   *
   * @return false if any of the conditions we are verifying are not what we expect; true ONLY if
   *         all of our expectations are correct
   */
  //// INDEX OF

  public static boolean testIndexOfEmpty() {
    // (1) set up the test variables and call the method we are testing - EXACT MATCH
    String[][] emptyWardrobe = {null, null, null, null, null};
    String[][] expectedWardrobe = {null, null, null, null, null};
    int expectedIndex = -1;
    int actualSize = WardrobeManager.indexOfClothing("black hoodie", "Zara", emptyWardrobe, 0);
    // (2) verify that the expected return value and the actual return value match
    if (actualSize != expectedIndex) {return false; }
    // (3) since this method should not modify the array, let's check it against our copy:
    if (!Arrays.deepEquals(expectedWardrobe,emptyWardrobe)) { return false; }
    // (4) if all of those checks pass, NOW we can say we passed the test
    return true;
  }
  /**
   * Test method for finding an index of clothing that currently exists, matching the correct
   * description and brand at the start and end of the wardrobe
   *
   * @return false if any of the conditions we are verifying are not what we expect; true ONLY if
   *         all of our expectations are correct
   */

  public static boolean testIndexOfFirstLast() {
    // (1) set up the test variables and call the method we are testing - EXACT MATCH
    String [][] wardrobe = {{"black t-shirt", "Hanes", "2023-12-19"},
        {"grey UW hoodie", "gildan", "2020-03-16"}, {"dark blue jeans", "Levi", "2024-01-25"},
        {"green cabled sweater", "handmade", "never"}};
    String[][] expectedWardrobe = Arrays.copyOf(wardrobe, wardrobe.length);
    int expectedFirst = 0;
    int expectedLast = 3;
    int actualFirst = WardrobeManager.indexOfClothing("Black t-shirt", "hanes", wardrobe, 4);
    int actualLast = WardrobeManager.indexOfClothing("green cabled sweater", "handmade", wardrobe
        , 4);
    // (2) verify that the expected return value and the actual return value match in both cases
    if (actualFirst != expectedFirst) { return false; }
    if (actualLast != expectedLast) { return false; }
    // (3) since this method should not modify the array, let's check it against our copy:
    if (!Arrays.deepEquals(expectedWardrobe,wardrobe)) { return false; }
    // (4) if all of those checks pass, NOW we can say we passed the test
    return true;
  }
  /**
   * Test method for finding an index for an article of clothing in the middle of the wardrobe
   * matching the correct description and brand
   *
   * @return false if any of the conditions we are verifying are not what we expect; true ONLY if
   *         all of our expectations are correct
   */
  public static boolean testIndexOfMiddle() {
    // (1) set up the test variables and call the method we are testing - EXACT MATCH
    String [][] wardrobe = {{"black t-shirt", "Hanes", "2023-12-19"},
        {"grey UW hoodie", "gildan", "2020-03-16"}, {"dark blue jeans", "Levi", "2024-01-25"},
        {"green cabled sweater", "handmade", "never"}, {"red t-shirt", "UW Merch", "never"}};
    String [][] expectedWardrobe = {{"black t-shirt", "Hanes", "2023-12-19"},
        {"grey UW hoodie", "gildan", "2020-03-16"}, {"dark blue jeans", "Levi", "2024-01-25"},
        {"green cabled sweater", "handmade", "never"}, {"red t-shirt", "UW Merch", "never"}};
    int expected = 2;
    int actual = WardrobeManager.indexOfClothing("dark blue jeans", "Levi", wardrobe, 5);
    // (2) verify that the expected return value and the actual return value match
    if (actual != expected) { return false; }
    // (3) since this method should not modify the array, let's check it against our copy:
    if (!Arrays.deepEquals(expectedWardrobe,wardrobe)) { return false; }
    // (4) if all of those checks pass, NOW we can say we passed the test
    return true;
  }
  /**
   * Test method for finding the index of an article of clothing that does not currently exist in
   * the wardrobe and making sure that it returns -1 indicating that it does not exist
   *
   * @return false if any of the conditions we are verifying are not what we expect; true ONLY if
   *         all of our expectations are correct
   */
  public static boolean testIndexOfNoMatch() {
    // (1) set up the test variables and call the method we are testing - EXACT MATCH
    String [][] wardrobe = {{"black t-shirt", "Hanes", "2023-12-19"},
        {"grey UW hoodie", "gildan", "2020-03-16"}, {"dark blue jeans", "Levi", "2024-01-25"},
        {"green cabled sweater", "handmade", "never"}, {"red t-shirt", "UW Merch", "never"}};
    String [][] expectedWardrobe = {{"black t-shirt", "Hanes", "2023-12-19"},
        {"grey UW hoodie", "gildan", "2020-03-16"}, {"dark blue jeans", "Levi", "2024-01-25"},
        {"green cabled sweater", "handmade", "never"}, {"red t-shirt", "UW Merch", "never"}};
    int expected = -1;
    int actual = WardrobeManager.indexOfClothing("light blue pants", "Old Navy", wardrobe, 5);
    // (2) verify that the expected return value and the actual return value match
    if (actual != expected) { return false; }
    // (3) since this method should not modify the array, let's check it against our copy:
    if (!Arrays.deepEquals(expectedWardrobe,wardrobe)) { return false; }
    // (4) if all of those checks pass, NOW we can say we passed the test
    return true;
  }
  /**
   * Test method for finding an article of clothing that matches the correct description and
   * brand, and then updating the last worn date to the provided date
   *
   * @return false if any of the conditions we are verifying are not what we expect; true ONLY if
   *         all of our expectations are correct
   */
  //// WEAR

  public static boolean testWearClothingTrue() {
    // (1) set up the test variables and call the method we are testing
    String [][] wardrobe = {{"black t-shirt", "Hanes", "2023-12-19"},
        {"grey UW hoodie", "gildan", "2020-03-16"}, {"dark blue jeans", "Levi", "2024-01-25"},
        {"green cabled sweater", "handmade", "never"}, {"red t-shirt", "UW Merch", "never"}};
    String [][] expectedWardrobe = {{"black t-shirt", "Hanes", "2023-12-19"},
        {"grey UW hoodie", "gildan", "2020-03-16"}, {"dark blue jeans", "Levi", "2024-01-25"},
        {"green cabled sweater", "handmade", "2024-01-29"}, {"red t-shirt", "UW Merch", "never"}};
    boolean expectedReturn = true;
    boolean actualReturn = WardrobeManager.wearClothing("green cabled sweater",
        "handmade", "2024-01-29", wardrobe, 5);
    // (2) verify that the expected return value and the actual return value match
    if (actualReturn != expectedReturn) { return false; }
    // (3) since this method should modify the array, let's check it against our correct array:
    if (!Arrays.deepEquals(wardrobe,expectedWardrobe)) { return false; }
    // (4) if all of those checks pass, NOW we can say we passed the test
    return true;
  }
  /**
   * Test method for updating the last worn date for an article of clothing that does not
   * currently exist in the wardrobe, making sure that it does not change any worn date for
   * clothes in the wardrobe
   *
   * @return false if any of the conditions we are verifying are not what we expect; true ONLY if
   *         all of our expectations are correct
   */
  public static boolean testWearClothingNoMatch() {
    // (1) set up the test variables and call the method we are testing - EXACT MATCH
    String [][] wardrobe = {{"black t-shirt", "Hanes", "2023-12-19"},
        {"grey UW hoodie", "gildan", "2020-03-16"}, {"dark blue jeans", "Levi", "2024-01-25"},
        {"green cabled sweater", "handmade", "never"}, {"red t-shirt", "UW Merch", "never"}};
    String [][] expectedWardrobe = {{"black t-shirt", "Hanes", "2023-12-19"},
        {"grey UW hoodie", "gildan", "2020-03-16"}, {"dark blue jeans", "Levi", "2024-01-25"},
        {"green cabled sweater", "handmade", "never"}, {"red t-shirt", "UW Merch", "never"}};
    boolean expectedReturn = false;
    boolean actualReturn = WardrobeManager.wearClothing("white t-shirt", "Gucci",
        "2020-01-29", wardrobe, 5);
    // (2) verify that the expected return value and the actual return value match
    if (actualReturn != expectedReturn) { return false; }
    // (3) since this method should not modify the array, let's check it against our copy:
    if (!Arrays.deepEquals(wardrobe,expectedWardrobe)) { return false; }
    // (4) if all of those checks pass, NOW we can say we passed the test
    return true;
  }
  /**
   * Test method for checking the amount of matching brands in a wardrobe that contains all
   * the same brand
   *
   * @return false if any of the conditions we are verifying are not what we expect; true ONLY if
   *         all of our expectations are correct
   */
  //// BRAND COUNT

  public static boolean testBrandCountAllMatch() {
    // (1) set up the test variables and call the method we are testing - EXACT MATCH
    String [][] wardrobe = {{"black hoodie", "Abercrombie", "never"},
        {"white hoodie", "Abercrombie", "never"}, {"black pants", "abercrombie", "2020-05-14"},
        {"white t-shirt", "abercrombie", "2023-08-19"}};
    String [][] expectedWardrobe = {{"black hoodie", "Abercrombie", "never"},
        {"white hoodie", "Abercrombie", "never"}, {"black pants", "abercrombie", "2020-05-14"},
        {"white t-shirt", "abercrombie", "2023-08-19"}};
    int expected = 4;
    int actual = WardrobeManager.getBrandCount("abercrombie", wardrobe, 4);
    // (2) verify that the expected return value and the actual return value match
    if (expected != actual) { return false; }
    // (3) since this method should not modify the array, let's check it against our copy:
    if (!Arrays.deepEquals(expectedWardrobe,wardrobe)) { return false; }
    // (4) if all of those checks pass, NOW we can say we passed the test
    return true;
  }
  /**
   * Test method for checking the amount of matching brands in a wardrobe that contains some of
   * the same brands, and some different brands
   *
   * @return false if any of the conditions we are verifying are not what we expect; true ONLY if
   *         all of our expectations are correct
   */

  public static boolean testBrandCountSomeMatch() {
    // (1) set up the test variables and call the method we are testing - EXACT MATCH
    String [][] wardrobe = {{"black hoodie", "Abercrombie", "never"},
        {"white hoodie", "Levi", "never"}, {"black pants", "abercrombie", "2020-05-14"},
        {"white t-shirt", "Adidas", "2023-08-19"}};
    String [][] expectedWardrobe = {{"black hoodie", "Abercrombie", "never"},
        {"white hoodie", "Levi", "never"}, {"black pants", "abercrombie", "2020-05-14"},
        {"white t-shirt", "Adidas", "2023-08-19"}};
    int expected = 2;
    int actual = WardrobeManager.getBrandCount("abercrombie", wardrobe, 4);
    // (2) verify that the expected return value and the actual return value match
    if (actual != expected) { return false; }
    // (3) since this method should not modify the array, let's check it against our copy:
    if (!Arrays.deepEquals(expectedWardrobe,wardrobe)) { return false; }
    // (4) if all of those checks pass, NOW we can say we passed the test
    return true;
  }
  /**
   * Test method for checking the amount of matching brands in a wardrobe that contains none of
   * the specified brand that is being searched for
   *
   * @return false if any of the conditions we are verifying are not what we expect; true ONLY if
   *         all of our expectations are correct
   */
  public static boolean testBrandCountNoMatch() {
    // (1) set up the test variables and call the method we are testing - EXACT MATCH
    String [][] wardrobe = {{"black t-shirt", "Hanes", "2023-12-19"},
        {"grey UW hoodie", "gildan", "2020-03-16"}, {"dark blue jeans", "Levi", "2024-01-25"},
        {"green cabled sweater", "handmade", "never"}, {"red t-shirt", "UW Merch", "never"}};
    String [][] expectedWardrobe = {{"black t-shirt", "Hanes", "2023-12-19"},
        {"grey UW hoodie", "gildan", "2020-03-16"}, {"dark blue jeans", "Levi", "2024-01-25"},
        {"green cabled sweater", "handmade", "never"}, {"red t-shirt", "UW Merch", "never"}};
    int expected = 0;
    int actual = WardrobeManager.getBrandCount("abercrombie", wardrobe, 5);
    // (2) verify that the expected return value and the actual return value match
    if (expected != actual) { return false; }
    // (3) since this method should not modify the array, let's check it against our copy:
    if (!Arrays.deepEquals(wardrobe,expectedWardrobe)) { return false; }
    // (4) if all of those checks pass, NOW we can say we passed the test
    return true;
  }
  /**
   * Test method for counting the number of unworn clothing in the wardrobe, with all the clothes
   * in the wardrobe never being worn before
   *
   * @return false if any of the conditions we are verifying are not what we expect; true ONLY if
   *         all of our expectations are correct
   */
  //// COUNT UNWORN

  public static boolean testUnwornCountAllMatch() {
    // (1) set up the test variables and call the method we are testing - EXACT MATCH
    String [][] wardrobe = {{"black t-shirt", "Hanes", "never"},
        {"grey UW hoodie", "gildan", "never"}, {"dark blue jeans", "Levi", "never"},
        {"green cabled sweater", "handmade", "never"}, {"red t-shirt", "UW Merch", "never"}};
    String [][] expectedWardrobe = {{"black t-shirt", "Hanes", "never"},
        {"grey UW hoodie", "gildan", "never"}, {"dark blue jeans", "Levi", "never"},
        {"green cabled sweater", "handmade", "never"}, {"red t-shirt", "UW Merch", "never"}};
    int expected = 5;
    int actual = WardrobeManager.getNumUnwornClothes(wardrobe,5);
    // (2) verify that the expected return value and the actual return value match
    if (expected != actual) { return false; }
    // (3) since this method should not modify the array, let's check it against our copy:
    if (!Arrays.deepEquals(wardrobe,expectedWardrobe)) { return false; }
    // (4) if all of those checks pass, NOW we can say we passed the test
    return true;
  }
  /**
   * Test method for counting the number of articles of clothing never worn before, with some of
   * the clothes in the wardrobe never being worn before
   *
   * @return false if any of the conditions we are verifying are not what we expect; true ONLY if
   *         all of our expectations are correct
   */
  public static boolean testUnwornCountSomeMatch() {
    // (1) set up the test variables and call the method we are testing - EXACT MATCH
    String [][] wardrobe = {{"black t-shirt", "Hanes", "2020-05-14"},
        {"grey UW hoodie", "gildan", "2023-07-09"}, {"dark blue jeans", "Levi", "never"},
        {"green cabled sweater", "handmade", "never"}, {"red t-shirt", "UW Merch", "never"}};
    String [][] expectedWardrobe = {{"black t-shirt", "Hanes", "2020-05-14"},
        {"grey UW hoodie", "gildan", "2023-07-09"}, {"dark blue jeans", "Levi", "never"},
        {"green cabled sweater", "handmade", "never"}, {"red t-shirt", "UW Merch", "never"}};
    int expected = 3;
    int actual = WardrobeManager.getNumUnwornClothes(wardrobe, 5);
    // (2) verify that the expected return value and the actual return value match
    if (expected != actual) { return false; }
    // (3) since this method should not modify the array, let's check it against our copy:
    if (!Arrays.deepEquals(wardrobe,expectedWardrobe)) { return false; }
    // (4) if all of those checks pass, NOW we can say we passed the test
    return true;
  }
  /**
   * Test method for counting the number of unworn clothes in the wardrobe, with a wardrobe where
   * all the clothes have been worn before
   *
   * @return false if any of the conditions we are verifying are not what we expect; true ONLY if
   *         all of our expectations are correct
   */
  public static boolean testUnwornCountNoMatch() {
    // (1) set up the test variables and call the method we are testing - EXACT MATCH
    String [][] wardrobe = {{"black t-shirt", "Hanes", "2020-05-14"},
        {"grey UW hoodie", "gildan", "2023-07-09"}, {"dark blue jeans", "Levi", "2024-01-28"},
        {"green cabled sweater", "handmade", "2017-05-09"},
        {"red t-shirt", "UW Merch", "2018-02-12"}};
    String [][] expectedWardrobe = {{"black t-shirt", "Hanes", "2020-05-14"},
        {"grey UW hoodie", "gildan", "2023-07-09"}, {"dark blue jeans", "Levi", "2024-01-28"},
        {"green cabled sweater", "handmade", "2017-05-09"},
        {"red t-shirt", "UW Merch", "2018-02-12"}};
    int expected = 0;
    int actual = WardrobeManager.getNumUnwornClothes(wardrobe, 5);
    // (2) verify that the expected return value and the actual return value match
    if (actual != expected) { return false; }
    // (3) since this method should not modify the array, let's check it against our copy:
    if (!Arrays.deepEquals(wardrobe,expectedWardrobe)) { return false; }
    // (4) if all of those checks pass, NOW we can say we passed the test
    return true;
  }

  //// MOST RECENTLY WORN

  /**
   * PROVIDED: example test method for verifying that the most recently worn item is found
   * correctly. Note that this tester is not comprehensive; if you wish to verify additional
   * behavior you are welcome to add additional tester methods (please specify such methods
   * to be PRIVATE).
   *
   * @return false if any of the conditions we are verifying are not what we expect; true ONLY if
   *         all of our expectations are correct
   */
  public static boolean testMostRecentlyWorn() {
    // (1) set up the test variables and call the method we are testing - EXACT MATCH
    String[][] wardrobe = {{"black t-shirt", "Hanes", "2023-12-19"},
        {"grey UW hoodie", "gildan", "2020-03-16"},
        {"dark blue jeans", "Levi", "2024-01-25"},
        {"green cabled sweater", "handmade", "never"}, null};
    String[][] wardrobeCopy = Arrays.copyOf(wardrobe, wardrobe.length);
    int size = 4;
    int expected = 2;
    int actual = WardrobeManager.getMostRecentlyWorn(wardrobe, size);

    // (2) verify that the expected return value and the actual return value match
    if (expected != actual) return false;

    // (3) since this method should not modify the array, let's check it against our copy:
    if (!Arrays.deepEquals(wardrobe, wardrobeCopy)) return false;

    // (4) if all of those checks pass, NOW we can say we passed the test
    return true;
  }

  //// REMOVE BY INDEX
  /**
   * Test method for removing a clothing article that comes last in the wardrobe
   *
   * @return false if any of the conditions we are verifying are not what we expect; true ONLY if
   *         all of our expectations are correct
   */
  public static boolean testRemoveLastItem() {
    // (1) set up the test variables and call the method we are testing
    String [][] wardrobe = {{"black t-shirt", "Hanes", "2020-05-14"},
        {"grey UW hoodie", "gildan", "2023-07-09"}, {"dark blue jeans", "Levi", "2024-01-28"},
        {"green cabled sweater", "handmade", "2017-05-09"},
        {"red t-shirt", "UW Merch", "2018-02-12"}};
    String [][] expectedWardrobe = {{"black t-shirt", "Hanes", "2020-05-14"},
        {"grey UW hoodie", "gildan", "2023-07-09"}, {"dark blue jeans", "Levi", "2024-01-28"},
        {"green cabled sweater", "handmade", "2017-05-09"}, null};
    int expectedSize = 4;
    int actualSize = WardrobeManager.removeClothingAtIndex(4, wardrobe, 5);
    // (2) verify that the expected return value and the actual return value match
    if (actualSize != expectedSize) { return false; }
    // (3) since this method should modify the array, let's check it against our correct array:
    if (!Arrays.deepEquals(expectedWardrobe,wardrobe)) { return false; }
    // (4) since it is removing one item, make sure that the last item is null
    if (wardrobe[4] != null) { return false; }
    // (5) if all of those checks pass, NOW we can say we passed the test
    return true;
  }
  /**
   * Test method for removing the first article of clothing inside a wardrobe and making sure
   * that every subsequent item in the wardrobe is shifted down one index
   *
   * @return false if any of the conditions we are verifying are not what we expect; true ONLY if
   *         all of our expectations are correct
   */
  public static boolean testRemoveFirstItem() {
    // (1) set up the test variables and call the method we are testing
    String [][] wardrobe = {{"black t-shirt", "Hanes", "2020-05-14"},
        {"grey UW hoodie", "gildan", "2023-07-09"}, {"dark blue jeans", "Levi", "never"},
        {"green cabled sweater", "handmade", "never"}, {"red t-shirt", "UW Merch", "never"}};
    String [][] expectedWardrobe = { {"grey UW hoodie", "gildan", "2023-07-09"},
        {"dark blue jeans", "Levi", "never"}, {"green cabled sweater", "handmade", "never"},
        {"red t-shirt", "UW Merch", "never"}, null};
    int expected = 4;
    int actual = WardrobeManager.removeClothingAtIndex(0, wardrobe, 5);
    // (2) verify that the expected return value and the actual return value match
    if (actual != expected) { return false; }
    // (3) since this method should modify the array, let's check it against our correct array:
    if (!Arrays.deepEquals(wardrobe,expectedWardrobe)) { return false; }
    // (4) since the first item is removed, make sure that the removed element is moved to the end
    if (wardrobe[4] != null) { return false; }
    // (5) Since the first item was removed, make sure it was properly filled in this case
    if (wardrobe[0] == null) { return false; }
    // (6) if all of those checks pass, NOW we can say we passed the test
    return true;
  }
  /**
   * Test method for removing a clothing article of an index that is bigger than the size of the
   * wardrobe, making sure that nothing from the wardrobe is removed or added.
   *
   * @return false if any of the conditions we are verifying are not what we expect; true ONLY if
   *         all of our expectations are correct
   */
  public static boolean testRemoveBadIndex() {
    // (1) set up the test variables and call the method we are testing - EXACT MATCH
    String [][] wardrobe = {{"black t-shirt", "Hanes", "2020-05-14"},
        {"grey UW hoodie", "gildan", "2023-07-09"}, {"dark blue jeans", "Levi", "never"},
        {"green cabled sweater", "handmade", "never"}, {"red t-shirt", "UW Merch", "never"}};
    String [][] expectedWardrobe = {{"black t-shirt", "Hanes", "2020-05-14"},
        {"grey UW hoodie", "gildan", "2023-07-09"}, {"dark blue jeans", "Levi", "never"},
        {"green cabled sweater", "handmade", "never"}, {"red t-shirt", "UW Merch", "never"}};
    int expected = 5;
    int actual = WardrobeManager.removeClothingAtIndex(5, wardrobe, 5);
    // (2) verify that the expected return value and the actual return value match
    if (actual != expected) { return false; }
    // (3) since this method should not modify the array, let's check it against our copy:
    if (!Arrays.deepEquals(wardrobe, expectedWardrobe)) { return false; }
    // (4) if all of those checks pass, NOW we can say we passed the test
    return true;
  }
  /**
   * Test method for removing all the clothes that have never been worn from the wardrobe, with
   * this specific wardrobe containing no clothes that have never been worn before
   *
   * @return false if any of the conditions we are verifying are not what we expect; true ONLY if
   *         all of our expectations are correct
   */
  //// REMOVE ALL UNWORN
  public static boolean testRemoveUnwornNoMatch() {
    // (1) set up the test variables and call the method we are testing - EXACT MATCH
    String [][] wardrobe = {{"black t-shirt", "Hanes", "2020-05-14"},
        {"grey UW hoodie", "gildan", "2023-07-09"}, {"dark blue jeans", "Levi", "2024-01-28"},
        {"green cabled sweater", "handmade", "2017-05-09"},
        {"red t-shirt", "UW Merch", "2018-02-12"}};
    String [][] expectedWardrobe = {{"black t-shirt", "Hanes", "2020-05-14"},
        {"grey UW hoodie", "gildan", "2023-07-09"}, {"dark blue jeans", "Levi", "2024-01-28"},
        {"green cabled sweater", "handmade", "2017-05-09"},
        {"red t-shirt", "UW Merch", "2018-02-12"}};
    int expected = 5;
    int actual = WardrobeManager.removeAllUnworn(wardrobe, 5);
    int expectedUnworn = 0;
    int actualUnworn = WardrobeManager.getNumUnwornClothes(wardrobe, actual);
    // (2) verify that the expected return value and the actual return value match
    if (actual != expected) { return false; }
    // (3) since this method should not modify the array, let's check it against our copy:
    if (!Arrays.deepEquals(expectedWardrobe,wardrobe)) { return false; }
    // (4) since this method should remove all unworn, let's make sure there are 0 unworn clothes
    if (expectedUnworn != actualUnworn) { return false; }
    // (5) if all of those checks pass, NOW we can say we passed the test
    return true;
  }
  /**
   * Test method for removing all the unworn clothes from a wardrobe, with this specific
   * wardrobe containing some articles of clothing that have never been worn before. Also makes
   * sure that elements are shifted into a compact oversize array after removing elements
   *
   * @return false if any of the conditions we are verifying are not what we expect; true ONLY if
   *         all of our expectations are correct
   */
  public static boolean testRemoveUnwornSomeMatch() {
    // (1) set up the test variables and call the method we are testing
    String [][] wardrobe = {{"black t-shirt", "Hanes", "2020-05-14"},
        {"grey UW hoodie", "gildan", "never"}, {"dark blue jeans", "Levi", "2020-07-17"},
        {"green cabled sweater", "handmade", "never"}, {"red t-shirt", "UW Merch", "2017-08-15"}};
    String [][] expectedWardrobe = {{"black t-shirt", "Hanes", "2020-05-14"}, {"dark blue jeans",
        "Levi", "2020-07-17"}, {"red t-shirt", "UW Merch", "2017-08-15"}, null, null};
    int expected = 3;
    int expectedUnworn = 0;
    int actual = WardrobeManager.removeAllUnworn(wardrobe, 5);
    int actualUnworn = WardrobeManager.getNumUnwornClothes(wardrobe, actual);
    // (2) verify that the expected return value and the actual return value match
    if (actual != expected) { return false; }
    // (3) since this method should modify the array, let's check it against our correct array:
    if (!Arrays.deepEquals(expectedWardrobe,wardrobe)) { return false; }
    // (4) since this method should remove all unworn, make sure there are 0 unworn clothes
    if (expectedUnworn != actualUnworn) { return false; }
    // (5) if all of those checks pass, NOW we can say we passed the test
    return true;
  }
  /**
   * This test method removes all the unworn articles of clothing from a wardrobe, with all the
   * articles of clothing being never worn before in this wardrobe
   *
   * @return false if any of the conditions we are verifying are not what we expect; true ONLY if
   *         all of our expectations are correct
   */
  public static boolean testRemoveUnwornAllMatch() {
    // (1) set up the test variables and call the method we are testing
    String [][] wardrobe = {{"black t-shirt", "Hanes", "never"},
        {"grey UW hoodie", "Gildan", "never"}, {"dark blue jeans", "Levi", "never"},
        {"green cabled sweater", "handmade", "never"}, {"red t-shirt", "UW Merch", "never"}};
    String [][] expectedWardrobe = {null, null, null, null, null};
    int expected = 0;
    int expectedUnworn = 0;
    int actual = WardrobeManager.removeAllUnworn(wardrobe,5);
    int actualUnworn = WardrobeManager.getNumUnwornClothes(wardrobe, actual);
    // (2) verify that the expected return value and the actual return value match
    if (actual != expected) { return false; }
    // (3) since this method should modify the array, let's check it against our correct array:
    if (!Arrays.deepEquals(expectedWardrobe,wardrobe)) { return false; }
    // (4) since this method should remove all unworn, make sure there are 0 unworn clothes
    if (expectedUnworn != actualUnworn) { return false; }
    // (5) if all of those checks pass, NOW we can say we passed the test
    return true;
  }

  /**
   * PROVIDED: calls all tester methods and displays the results of the tests.
   *
   * All tests are called in the order they were provided in this file. The output of this method
   * will NOT be graded so you may modify it however you wish.
   *
   * @param args unused
   */
  public static void main(String[] args) {
    System.out.println("CONTAINS:\n  "+(testContainsEmpty()?"pass":"FAIL")+", "+
        (testContainsTrue()?"pass":"FAIL")+", "+(testContainsFalse()?"pass":"FAIL"));
    System.out.println("ADD:\n  "+(testAddToEmpty()?"pass":"FAIL")+", " +
        ""+(testAddNonEmpty()?"pass":"FAIL")+
        ", "+(testAddDuplicate()?"pass":"FAIL")+", "+(testAddToFull()?"pass":"FAIL"));
    System.out.println("INDEX OF:\n  "+(testIndexOfEmpty()?"pass":"FAIL")+", " +
        ""+(testIndexOfFirstLast()?"pass":"FAIL")+
        ", "+(testIndexOfMiddle()?"pass":"FAIL")+", "+(testIndexOfNoMatch()?"pass":"FAIL"));
    System.out.println("WEAR:\n  "+(testWearClothingTrue()?"pass":"FAIL")+", " +
        ""+(testWearClothingNoMatch()?"pass":"FAIL"));
    System.out.println("BRAND COUNT:\n  "+(testBrandCountAllMatch()?"pass":"FAIL")+", "+
        (testBrandCountSomeMatch()?"pass":"FAIL")+", "+(testBrandCountNoMatch()?"pass":"FAIL"));
    System.out.println("UNWORN COUNT:\n  "+(testUnwornCountAllMatch()?"pass":"FAIL")+", "+
        (testUnwornCountSomeMatch()?"pass":"FAIL")+", "+(testUnwornCountNoMatch()?"pass":"FAIL"));
    System.out.println("MOST RECENTLY WORN:\n  "+(testMostRecentlyWorn()?"pass":"FAIL"));
    System.out.println("REMOVE BY INDEX:\n  "+(testRemoveLastItem()?"pass":"FAIL")+", "+
        (testRemoveFirstItem()?"pass":"FAIL")+", "+(testRemoveBadIndex()?"pass":"FAIL"));
    System.out.println("REMOVE UNWORN:\n  "+(testRemoveUnwornNoMatch()?"pass":"FAIL")+", "+
        (testRemoveUnwornSomeMatch()?"pass":"FAIL")+", " +
        ""+(testRemoveUnwornAllMatch()?"pass":"FAIL"));
  }
}
