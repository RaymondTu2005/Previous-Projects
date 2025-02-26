//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Tester Class for the Wardrobe Class
// Course:   CS 300 Spring 2024
//
// Author:   Raymond Tu
// Email:    rktu2@wisc.edu email address)
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
// Persons:        None
// Online Sources: None
//
///////////////////////////////////////////////////////////////////////////////

import java.text.ParseException;
import java.time.LocalDate;
import java.util.NoSuchElementException;

/**
 * A tester class for the Wardrobe Manager project. It contains various tests to check the
 * correctness of the Wardrobe and Clothing classes.
 */
public class WardrobeManagerTester {

  /**
   * Tests both of the Clothing constructors and all getters for correctness. This test accounts for
   * the fact a bad implementation may throw an Exception.
   *
   * @return true if all tests pass, false otherwise
   * @author Michelle
   */
  public static boolean testClothingConstructorAndGetters() {

    // in case we get an unexpected exception from a broken implementation
    // we handle it with a try-catch to avoid our tester from crashing
    try {
      // test the 2-argument constructor
      Clothing c = new Clothing("black t-shirt", "Gildan");

      // check that the four instance data fields have been initialized correctly
      // using the getters to do this we are also checking their correctness
      // in a bad implementation either 1) the constructor didn't intialize a data field correctly
      // OR 2) the getter doesn't return the correct value
      if (!c.getDescription().equals("black t-shirt"))
        return false;
      if (!c.getBrand().equals("Gildan"))
        return false;
      if (c.getNumOfTimesWorn() != 0)
        return false;
      if (c.getLastWornDate() != null)
        return false;

      // test the 4 argument constructor
      // same idea as the previous test case
      LocalDate date = LocalDate.of(2024, 2, 14); // create a date object for last worn
      c = new Clothing("jeans", "Levi", 3, date);
      if (!c.getDescription().equals("jeans"))
        return false;
      if (!c.getBrand().equals("Levi"))
        return false;
      if (c.getNumOfTimesWorn() != 3)
        return false;
      if (!c.getLastWornDate().equals(date))
        return false;

    } catch (
        Exception e) { // we encounter an exception when we should not, it is a bad implementation
      e.printStackTrace();
      return false;
    }
    return true;
  }

  /**
   * Tests that both of the Clothing constructors throw the correct type of exception(s) with a
   * message in situations where an exception is expected.
   *
   * @return true if all tests pass, false otherwise
   * @author Michelle and Hobbes
   */
  public static boolean testClothingConstructorExceptions() {
    // Here we call constructors with input that should lead to an IllegalArgumentException
    // on a correct (good) implementation. ALL of these cases SHOULD throw exceptions,
    // so we test them in separate try-catch statements to verify that each individual
    // case throws an exception.

    try {
      // test the 2 argument constructor with a blank description
      new Clothing(" ", "Gildan");

      return false; // no exception was thrown when it should have been; it's a broken implementation
    } catch (IllegalArgumentException e) {
      // check if the exception has any message; if there is NO message it's a broken implementation
      if (e.getMessage() == null || e.getMessage().isBlank())
        return false;
    } catch (Exception e) { // any other type of exception is not good, it's a broken implementation
      e.printStackTrace();
      return false;
    }

    try {
      // and make sure a blank brand will also throw an exception
      new Clothing("black t-shirt", "  ");

      return false; // no exception was thrown
    } catch (IllegalArgumentException e) {
      // check if the exception has a message
      if (e.getMessage() == null || e.getMessage().isBlank())
        return false;
    } catch (Exception e) { // any other type of exception is not good
      e.printStackTrace();
      return false;
    }

    try {
      // test the 4 argument constructor with a blank description
      LocalDate date = LocalDate.of(2021, 12, 25);
      new Clothing(" ", "Gildan", 4, date);

      return false; // no exception was thrown
    } catch (IllegalArgumentException e) {
      // check if the exception has a message
      if (e.getMessage() == null || e.getMessage().isBlank())
        return false;
    } catch (Exception e) { // any other type of exception is not good
      e.printStackTrace();
      return false;
    }

    try {
      // and verifying that a blank brand will also throw an exception
      LocalDate date = LocalDate.of(2021, 12, 25);
      new Clothing("black t-shirt", "  ", 6, date);

      return false; // no exception was thrown
    } catch (IllegalArgumentException e) {
      // check if the exception has a message,
      if (e.getMessage() == null || e.getMessage().isBlank())
        return false;
    } catch (Exception e) { // any other type of exception is not good
      e.printStackTrace();
      return false;
    }

    // passed all the tests!
    return true;
  }

  /**
   * Tests for the correctness of the Clothing classes' wearClothing() method. This test accounts
   * for the fact a bad implementation may throw an Exception.
   *
   * @return true if all tests pass, false otherwise
   */
  public static boolean testClothingWear() {
    try {
      Clothing tester = new Clothing("Pants", "Adidas");
      tester.wearClothing(2024, 13, 05);
      return false; // no exception was thrown
    } catch (IllegalArgumentException e) {
      if (e.getMessage() == null || e.getMessage().isBlank()) { // Check if message exists

        return false;
      }
    } catch (Exception e) {
      e.printStackTrace();
      return false; // Unexpected exception caught
    }
    try {
      Clothing testerTwo = new Clothing("shirt", "adidas");
      testerTwo.wearClothing(0000, 03, 15);
      return false; // no exception was thrown
    } catch (IllegalArgumentException e) {
      if (e.getMessage() == null || e.getMessage().isBlank()) { // Check if no message
        return false;
      }
    } catch (Exception e) {
      e.printStackTrace();
      return false; // Unexpected exception caught
    }
    try {
      Clothing testerThree = new Clothing("shirt", "adidas");
      LocalDate compareTime = LocalDate.of(2022, 07, 12);
      testerThree.wearClothing(2022, 07, 12);
      if (testerThree.getLastWornDate().isEqual(compareTime)) {
        // do nothing since it should pass
      } else {
        return false;
      }
    } catch (IllegalArgumentException e) {
      if (e.getMessage() == null || e.getMessage().isBlank()) { // Check if no message
        return false;
      }
    } catch (Exception e) {
      e.printStackTrace();
      return false; // Unexpected exception caught
    }
    return true; // Passed all tests
  }

  /**
   * Tests the Wardrobe constructor and all getters for correctness. This test accounts for the fact
   * a bad implementation may throw an Exception.
   *
   * @return true if all tests pass, false otherwise
   */

  public static boolean testWardrobeConstructorAndGetters() {
    try {
      Wardrobe tester = new Wardrobe(5);
      // Test the functionality of the constructor and make sure it is currect using the getter
      // methods in wardrobe
      if (tester.size() != 0)
        return false;
      if (tester.capacity() != 5)
        return false;
      // No exception should be thrown, so return false if any are
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return true; // after all cases pass
  }

  /**
   * Tests that the Wardrobe constructor throws the correct type of exception(s) with a message in
   * situations where an exception is expected.
   *
   * @return true if all tests pass, false otherwise
   */
  public static boolean testWardrobeConstructorExceptions() {
    try {
      new Wardrobe(-5); // Test negative number for capacity
      return false; // no exception was thrown
    } catch (IllegalArgumentException e) {
      if (e.getMessage() == null || e.getMessage().isBlank()) {
        return false;
      }
    } catch (Exception e) {
      e.printStackTrace();
      return false; // If any other exception is thrown, return false
    }
    try {
      new Wardrobe(0); // Test edge cases, which is 0 in this case
      return false; // no exception was thrown
    } catch (IllegalArgumentException e) {
      if (e.getMessage() == null || e.getMessage().isBlank()) {
        return false;
      }
    } catch (Exception e) {
      e.printStackTrace();
      return false; // If any other exception is thrown, return false
    }
    return true;
  }

  /**
   * Tests that the Wardrobe's addClothing() method throws the correct type of exception(s) with a
   * message in situations where an exception is expected.
   *
   * @return true if all tests pass, false otherwise
   */
  public static boolean testAddClothingExceptions() {
    try {
      Wardrobe tester = new Wardrobe(5);
      LocalDate testTime = LocalDate.of(2023, 12, 2);
      Clothing testerClothes = new Clothing("T-shirt", "Nike", 0, testTime);
      Clothing testerClothesTwo = new Clothing("T-shirt", "Nike");
      tester.addClothing(testerClothes);
      tester.addClothing(testerClothesTwo); // Should throw exception since considered same
      return false; // Exception should have been thrown
    } catch (IllegalArgumentException e) {
      if (e.getMessage() == null || e.getMessage().isBlank()) {
        return false;
      }
    } catch (Exception e) {
      e.printStackTrace();
      return false; // Exception not expected, return false
    }
    try {
      Wardrobe tester = new Wardrobe(1);
      LocalDate testTime = LocalDate.of(2023, 12, 2);
      Clothing testerClothes = new Clothing("T-shirt", "Nike", 0, testTime);
      // tests the same thing, but making sure capitalization does not matter
      Clothing testerClothesTwo = new Clothing("t-shirt", "nike");
      tester.addClothing(testerClothes);
      tester.addClothing(testerClothesTwo); // Should throw since considered same
      return false; // Exception should have been thrown
    } catch (IllegalArgumentException e) {
      if (e.getMessage() == null || e.getMessage().isBlank()) {
        return false;
      }
    } catch (Exception e) {
      e.printStackTrace();
      return false; // Exception not expected, return false
    }
    return true; // Returns true if all passed!
  }

  /**
   * Tests the Wardrobe's addClothing() method for correctness. This test accounts for the fact a
   * bad implementation may throw an Exception.
   *
   * @return true if all tests pass, false otherwise
   */
  public static boolean testAddClothing() {
    try {
      Wardrobe tester = new Wardrobe(1);
      LocalDate testTime = LocalDate.of(2015, 6, 2);
      LocalDate testTimeTwo = LocalDate.of(2014, 1, 3);
      LocalDate testTimeThree = LocalDate.of(2013, 3, 2);
      Clothing testerClothes = new Clothing("T-shirt", "Nike", 0, testTime);
      Clothing testerClothesTwo = new Clothing("Pants", "Adidas", 0, testTimeTwo);
      Clothing testerClothesThree = new Clothing("Shorts", "UnderArmor,", 0, testTimeThree);
      tester.addClothing(testerClothes);
      tester.addClothing(testerClothesTwo);
      tester.addClothing(testerClothesThree);
      if (tester.size() != 3)
        return false; // 3 items added, so size should be three
      if (tester.capacity() != 4)
        return false; // Capacity should double twice from 1->2->4
    } catch (Exception e) {
      e.printStackTrace();
      return false; // Should not throw any exception
    }
    try {
      // Same tester as the other one, but in this case initial capacity is sufficient to handle
      // all of the items that are planned to be added to the array
      Wardrobe tester = new Wardrobe(16);
      LocalDate testTime = LocalDate.of(2015, 6, 2);
      LocalDate testTimeTwo = LocalDate.of(2014, 1, 3);
      LocalDate testTimeThree = LocalDate.of(2013, 3, 2);
      Clothing testerClothes = new Clothing("T-shirt", "Nike", 0, testTime);
      Clothing testerClothesTwo = new Clothing("Pants", "Adidas", 0, testTimeTwo);
      Clothing testerClothesThree = new Clothing("Shorts", "UnderArmor,", 0, testTimeThree);
      tester.addClothing(testerClothes);
      tester.addClothing(testerClothesTwo);
      tester.addClothing(testerClothesThree);
      if (tester.size() != 3)
        return false; // 3 items added, so size should be three
      if (tester.capacity() != 16)
        return false; // Make sure capacity doesn't change since not full
    } catch (Exception e) {
      e.printStackTrace();
      return false; // Should not throw any exception in this case
    }
    // Makes sure that when the size is doubled, the elements are in the correct order
    try {
      Wardrobe tester = new Wardrobe(1);
      LocalDate testTime = LocalDate.of(2015, 10, 10);
      LocalDate testTimeTwo = LocalDate.of(2014, 11, 11);
      Clothing testerClothes = new Clothing("tshirt", "nike", 0, testTime);
      Clothing testerClothesTwo = new Clothing("Pants", "Adidas", 0, testTimeTwo);
      tester.addClothing(testerClothes);
      tester.addClothing(testerClothesTwo);
      if (tester.size() != 2)
        return false; // 3 items added, so size should be three
      if (tester.capacity() != 2)
        return false; // Capacity should double twice from 1->2
      String actual = "[tshirt,nike,10/10/2015,1]" + "\n" + "[Pants,Adidas,11/11/2014,1]";
      if (actual.equals(tester.toString()))
        return false;
    } catch (Exception e) {
      e.printStackTrace();
      return false; // Should not throw any exception
    }
    return true;
  }

  /**
   * Tests the Wardrobe's getClothing() method for correctness. This test accounts for the fact a
   * bad implementation may throw an Exception.
   *
   * @return true if all tests pass, false otherwise
   */
  public static boolean testGetClothing() {
    try {
      // Tests that the GetClothing method works properly
      Wardrobe tester = new Wardrobe(5);
      LocalDate testOne = LocalDate.of(2000, 11, 05);
      LocalDate testTwo = LocalDate.of(2345, 03, 05);
      Clothing testOneClothes = new Clothing("Shoes", "UnderArmor", 0, testOne);
      Clothing testTwoClothes = new Clothing("Socks", "Tommy Hilfighter", 0, testTwo);
      tester.addClothing(testOneClothes);
      tester.addClothing(testTwoClothes);
      Clothing comparisonOne = tester.getClothing("Shoes", "UnderArmor");
      if (!comparisonOne.equals(testOneClothes)) {
        return false; // If the found clothing doesn't return match, return false.
      }
    } catch (Exception e) {
      e.printStackTrace();
      return false; // Should not throw any exception in this case
    }
    try {
      // Tests the GetClothing method making sure that it is not case-sensitive
      Wardrobe tester = new Wardrobe(5);
      LocalDate testOne = LocalDate.of(2000, 11, 05);
      LocalDate testTwo = LocalDate.of(2345, 03, 05);
      Clothing testOneClothes = new Clothing("Shoes", "UnderArmor", 0, testOne);
      Clothing testTwoClothes = new Clothing("Socks", "Tommy Hilfighter", 0, testTwo);
      tester.addClothing(testOneClothes);
      tester.addClothing(testTwoClothes);
      Clothing comparisonOne = tester.getClothing("socks", "tommy hilfighter");
      if (!comparisonOne.equals(testTwoClothes)) {
        return false; // If the found clothing doesn't return match, return false.
      }
    } catch (Exception e) {
      e.printStackTrace();
      return false; // Should not throw any exception in this case
    }
    return true;
  }


  /**
   * Tests that the Wardrobe's getClothing() method throws the correct type of exception(s) with a
   * message in situations where an exception is expected.
   *
   * @return true if all tests pass, false otherwise
   */
  public static boolean testGetClothingExceptions() {
    try { // This test case tries to find a clothing item that does not exist currently
      Wardrobe tester = new Wardrobe(5);
      LocalDate testOne = LocalDate.of(2000, 11, 05);
      LocalDate testTwo = LocalDate.of(2345, 03, 05);
      Clothing testOneClothes = new Clothing("Shoes", "UnderArmor", 0, testOne);
      Clothing testTwoClothes = new Clothing("Socks", "Tommy Hilfighter", 0, testTwo);
      tester.addClothing(testOneClothes);
      tester.addClothing(testTwoClothes);
      tester.getClothing("Hoodie", "Abercrombie");
      return false; // Exception should be thrown
    } catch (NoSuchElementException e) {
      if (e.getMessage() == null || e.getMessage().isBlank()) {
        return false;
      }
    } catch (Exception e) { // Catches any unexpected exceptions
      e.printStackTrace();
      return false;
    }
    try { // This test case tries to find a clothing item with an empty wardrobe, seeing if it
      // will throw the wrong type of exception such as NullPointerException or
      // ArrayIndexOutOfBoundsException, etc
      Wardrobe tester = new Wardrobe(5);
      tester.getClothing("Joggers", "LuluLemon");
    } catch (NoSuchElementException e) {
      if (e.getMessage() == null || e.getMessage().isBlank()) {
        return false;
      }
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  /**
   * Tests that the Wardrobe's removeClothing() method throws the correct type of exception(s) with
   * a message in situations where an exception is expected.
   *
   * @return true if all tests pass, false otherwise
   */
  public static boolean testRemoveClothingExceptions() {
    try {
      Wardrobe tester = new Wardrobe(5);
      tester.removeClothing("Earings", "K-Jeweler");
      return false; // Previous line should throw an exception
    } catch (IllegalStateException e) {
      if (e.getMessage() == null || e.getMessage().isBlank()) {
        return false; // Should have a message
      }
    } catch (NoSuchElementException e) {
      return false; // Wrong type of exception thrown
    } catch (Exception e) {
      return false; // Should not throw any other exception beside the two above if it throws one
    }
    try {
      Wardrobe tester = new Wardrobe(5);
      LocalDate testOne = LocalDate.of(2015, 06, 07);
      LocalDate testTwo = LocalDate.of(2012, 03, 25);
      Clothing testerOne = new Clothing("Leggings", "Lululemon", 2, testOne);
      Clothing testerTwo = new Clothing("Pants", "Adidas", 3, testTwo);
      tester.addClothing(testerOne);
      tester.addClothing(testerTwo);
      tester.removeClothing("Earings", "K-Jeweler");
      return false; // Previous line should throw an exception
    } catch (NoSuchElementException e) {
      if (e.getMessage() == null || e.getMessage().isBlank()) {
        return false; // Should have a message
      }
    } catch (Exception e) {
      return false; // Should not throw any other exception beside the two above if it throws one
    }
    return true;
  }

  /**
   * Tests the Wardrobe's removeClothings() method for correctness. This test accounts for the fact
   * a bad implementation may throw an Exception.
   *
   * @return true if all tests pass, false otherwise
   */
  public static boolean testRemoveClothing() {
    try {
      Wardrobe tester = new Wardrobe(3);
      Clothing[] testCompare = new Clothing[3]; // Array to check that indexes are shifted properly
      Clothing[] toCompare;
      LocalDate testerOne = LocalDate.of(2020, 10, 5);
      LocalDate testerTwo = LocalDate.of(2019, 9, 4);
      LocalDate testerThree = LocalDate.of(2018, 8, 3);
      Clothing testOne = new Clothing("Pants", "Adidas", 5, testerOne);
      Clothing testTwo = new Clothing("Shoes", "Nike", 4, testerTwo);
      Clothing testThree = new Clothing("Hoodie", "Abercrombie", 3, testerThree);
      tester.addClothing(testOne);
      tester.addClothing(testTwo);
      tester.addClothing(testThree);
      testCompare[0] = testTwo;
      testCompare[1] = testThree;
      tester.removeClothing("Pants", "Adidas");
      if (tester.size() != 2) {
        return false;
      }
      if (tester.capacity() != 3) {
        return false;
      }
      // Test that the right clothing items are removed
      if (!tester.getClothing("Shoes", "Nike").equals(testTwo)) {
        return false;
      }
      if (!tester.getClothing("Hoodie", "Abercrombie").equals(testThree)) {
        return false;
      }
      toCompare = tester.getArray();
      if (!toCompare[0].equals(testCompare[0])) {
        return false;
      } // Make sure shifted down properly
      if (!toCompare[1].equals(testCompare[1])) {
        return false;
      } // Make sure shifted down properly
      if (toCompare[2] != null) {
        return false;
      }
    } catch (Exception e) {
      e.printStackTrace();
      return false; // Should not throw any exception
    }
    try { // Tests the same thing, but removes the last element and checks if it equals null and
      // makes sure that none of the elements are shifted in the wrong direction
      Wardrobe tester = new Wardrobe(3);
      Clothing[] testCompare = new Clothing[3]; // Array to check that indexes are shifted properly
      Clothing[] toCompare;
      LocalDate testerOne = LocalDate.of(2020, 10, 5);
      LocalDate testerTwo = LocalDate.of(2019, 9, 4);
      LocalDate testerThree = LocalDate.of(2018, 8, 3);
      Clothing testOne = new Clothing("Pants", "Adidas", 5, testerOne);
      Clothing testTwo = new Clothing("Shoes", "Nike", 4, testerTwo);
      Clothing testThree = new Clothing("Hoodie", "Abercrombie", 3, testerThree);
      tester.addClothing(testOne);
      tester.addClothing(testTwo);
      tester.addClothing(testThree);
      testCompare[0] = testOne;
      testCompare[1] = testTwo;
      tester.removeClothing("Hoodie", "Abercrombie");
      if (tester.size() != 2) {
        return false;
      }
      if (tester.capacity() != 3) {
        return false;
      }
      // Test that the right clothing items are removed in the wardrobe
      if (!tester.getClothing("Pants", "Adidas").equals(testOne)) {
        return false;
      }
      if (!tester.getClothing("Shoes", "Nike").equals(testTwo)) {
        return false;
      }
      toCompare = tester.getArray();
      if (!toCompare[0].equals(testCompare[0])) {
        return false;
      } // Make sure none shifted
      if (!toCompare[1].equals(testCompare[1])) {
        return false;
      } // Make sure none shifted
      if (toCompare[2] != null) {
        return false;
      } // Make sure last element is set to null
    } catch (Exception e) {
      e.printStackTrace();
      return false; // Should not throw any exception
    }
    return true;
  }

  /**
   * Tests the Wardrobe's removeAllClothingWornBefore() method for correctness. This test accounts
   * for the fact a bad implementation may throw an Exception.
   *
   * @return true if all tests pass, false otherwise
   */
  public static boolean testRemoveAllClothingWornBefore() {
    // Tests that it removes the correct elements and shifts them all down to the lowest
    // available index without corrupting other elements
    try {
      Wardrobe tester = new Wardrobe(10);
      LocalDate firstTime = LocalDate.of(2020, 10, 10);
      LocalDate secondTime = LocalDate.of(2000, 06, 23);
      Clothing testOne = new Clothing("Pants", "Adidas", 3, secondTime);
      Clothing testTwo = new Clothing("Shirt", "Essentials", 0, firstTime);
      Clothing testThree = new Clothing("Underwear", "Nike", 3, secondTime);
      Clothing testFour = new Clothing("Shorts", "Hanes", 1, firstTime);
      Clothing testFive = new Clothing("Socks", "Nike", 4, secondTime);
      Clothing testSix = new Clothing("Mittens", "Gymshark", 3, firstTime);
      tester.addClothing(testOne);
      tester.addClothing(testTwo);
      tester.addClothing(testThree);
      tester.addClothing(testFour);
      tester.addClothing(testFive);
      tester.addClothing(testSix);
      tester.removeAllClothingWornBefore(2010, 05, 20); // Remove all before 05/20/2010
      if (!tester.getClothing("Shorts", "Hanes").equals(testFour)) {
        return false; // Make sure clothing item still exists
      }
      if (!tester.getClothing("Mittens", "Gymshark").equals(testSix)) {
        return false; // Make sure clothing item still exists
      }
      // Makes sure the capacity is not modified
      if (tester.capacity() != 10) {
        return false;
      }
    } catch (Exception e) {
      e.printStackTrace();
      return false; // Method should not throw any exception
    }
    return true;
  }

  /**
   * Tests the Wardrobe's removeAllClothingWornNumTimes() method for correctness. This test accounts
   * for the fact a bad implementation may throw an Exception.
   *
   * @return true if all tests pass, false otherwise
   */
  public static boolean testRemoveAllClothingWornNumTimes() {
    try { // Tests that it removes the proper elements and shifts it down the correct number of
      // times
      Wardrobe tester = new Wardrobe(6);
      Clothing[] testCompare = new Clothing[3];
      Clothing[] actual;
      LocalDate firstTime = LocalDate.of(2020, 10, 10);
      LocalDate secondTime = LocalDate.of(2000, 06, 23);
      Clothing testOne = new Clothing("Pants", "Adidas", 3, firstTime);
      Clothing testTwo = new Clothing("Shirt", "Essentials", 0, firstTime);
      Clothing testThree = new Clothing("Underwear", "Nike", 3, firstTime);
      Clothing testFour = new Clothing("Shorts", "Hanes", 1, secondTime);
      Clothing testFive = new Clothing("Socks", "Nike", 4, secondTime);
      tester.addClothing(testOne);
      tester.addClothing(testTwo);
      tester.addClothing(testThree);
      tester.addClothing(testFour);
      tester.addClothing(testFive);
      tester.removeAllClothingWornNumTimes(3);
      testCompare[0] = testOne;
      testCompare[1] = testThree;
      testCompare[2] = testFive;
      if (!tester.getClothing("Pants", "Adidas").equals(testOne)) {
        return false;
      }
      if (!tester.getClothing("Underwear", "Nike").equals(testThree)) {
        return false;
      }
      if (!tester.getClothing("Socks", "Nike").equals(testFive)) {
        return false;
      }
      actual = tester.getArray();
      if (!actual[0].equals(testCompare[0])) {
        return false;
      }
      if (!actual[1].equals(testCompare[1])) {
        return false;
      }
      if (!actual[2].equals(testCompare[2])) {
        return false;
      }
      if (actual[3] != null) {
        return false;
      } // Double check previous elements are null
      if (actual[4] != null) {
        return false;
      } // Double check previous elements are null
    } catch (Exception e) {
      e.printStackTrace();
      return false; // Method should not throw any exception
    }
    return true;
  }

  /**
   * Tests that the Wardrobe's parseClothing() method throws the correct type of exception(s) with a
   * message in situations where an exception is expected.
   *
   * @return true if all tests pass, false otherwise
   */
  public static boolean testParseClothingExceptions() {
    try { // Tests when description is empty
      String parseString = "  ,Nike,06/13/2026,5"; // String with no description
      Clothing newParse = Wardrobe.parseClothing(parseString);
      return false; // Should not continue and previous line should throw an exception
    } catch (ParseException e) {
      if (e.getMessage() == null || e.getMessage().isBlank()) {
        return false; // Exception should contain descriptive message
      }
    } catch (Exception e) {
      e.printStackTrace();
      return false; // should not throw any other exception
    }
    try { // Tests when brand is empty
      String parseString = "Shorts,   ,06/13/2026,5"; // String with no brand
      Clothing newParse = Wardrobe.parseClothing(parseString);
      return false; // Should not continue and previous line should throw an exception
    } catch (ParseException e) {
      if (e.getMessage() == null || e.getMessage().isBlank()) {
        return false; // Exception should contain descriptive message
      }
    } catch (Exception e) {
      e.printStackTrace();
      return false; // should not throw any other exception
    }
    try { // Tests when there is only three pieces of information given
      String parseString = "Shorts,Nike,06/13/2026"; // String with only 3 pieces of information
      Clothing newParse = Wardrobe.parseClothing(parseString);
      return false; // Should not continue and previous line should throw an exception
    } catch (ParseException e) {
      if (e.getMessage() == null || e.getMessage().isBlank()) {
        return false; // Exception should contain descriptive message
      }
    } catch (Exception e) {
      e.printStackTrace();
      return false; // should not throw any other exception
    }
    try { // Tests when number of times worn is NOT a number
      String parseString = "Shorts,Nike,06/13/2026,one"; // String with incorrect times worn
      Clothing newParse = Wardrobe.parseClothing(parseString);
      return false; // Should not continue and previous line should throw an exception
    } catch (ParseException e) {
      if (e.getMessage() == null || e.getMessage().isBlank()) {
        return false; // Exception should contain descriptive message
      }
    } catch (Exception e) {
      e.printStackTrace();
      return false; // should not throw any other exception
    }
    try { // Tests when the date given only contains two pieces of required information, rather
      // than three pieces of information
      String parseString = "Shorts,Nike,06/13,5"; // String with only month and day
      Clothing newParse = Wardrobe.parseClothing(parseString);
      return false; // Should not continue and previous line should throw an exception
    } catch (ParseException e) {
      if (e.getMessage() == null || e.getMessage().isBlank()) {
        return false; // Exception should contain descriptive message
      }
    } catch (Exception e) {
      e.printStackTrace();
      return false; // should not throw any other exception
    }
    try { // Tests when one of the dates is not able to parse into an Integer
      String parseString = "Shorts,Nike,One/13/2026"; // String with incorrect date
      Clothing newParse = Wardrobe.parseClothing(parseString);
      return false; // Should not continue and previous line should throw an exception
    } catch (ParseException e) {
      if (e.getMessage() == null || e.getMessage().isBlank()) {
        return false; // Exception should contain descriptive message
      }
    } catch (Exception e) {
      e.printStackTrace();
      return false; // should not throw any other exception
    }

    try { // Tests when the year given is three characters long
      String parseString = "Shorts,Nike,06/13/966,0";
      Clothing newParse = Wardrobe.parseClothing(parseString);
      return false; // Should not continue and previous line should throw an exception
    } catch (ParseException e) {
      if (e.getMessage() == null || e.getMessage().isBlank()) {
        return false; // Exception should contain descriptive message
      }
    } catch (Exception e) {
      e.printStackTrace();
      return false; // should not throw any other exception
    }
    try { // Tests when the month and day of month given is only one character long
      String parseString = "Shorts,Nike,2/1/2026,3";
      Clothing newParse = Wardrobe.parseClothing(parseString);
      return false; // Should not continue and previous line should throw an exception
    } catch (ParseException e) {
      if (e.getMessage() == null || e.getMessage().isBlank()) {
        return false; // Exception should contain descriptive message
      }
    } catch (Exception e) {
      e.printStackTrace();
      return false; // should not throw any other exception
    }
    return true;

  }

  /**
   * Tests the Wardrobe's parseClothing method for correctness. This test accounts for the fact a
   * bad implementation may throw an Exception.
   *
   * @return true if all tests pass, false otherwise
   */
  public static boolean testParseClothing() {
    try { // Tests the normal function of the ParseClothing
      LocalDate testOne = LocalDate.of(2020, 5, 17);
      Clothing compareOne = new Clothing("Shirt", "Abercrombie", 5, testOne);
      String parseString = "Shirt,Abercrombie,05/17/2020,5";
      Clothing parsedClothing = Wardrobe.parseClothing(parseString);
      // Checks if the dates match
      if (!parsedClothing.getLastWornDate().isEqual(testOne)) {
        return false;
      }
      // Check if description and brand match
      if (!compareOne.equals(parsedClothing)) {
        return false;
      }
      // Checks if numTimesWorn is correct
      if (parsedClothing.getNumOfTimesWorn() != 5) {
        return false;
      }
    } catch (ParseException e) {
      e.printStackTrace();
      return false; // Should not throw exception
    } catch (Exception e) {
      e.printStackTrace();
      return false; // Should not throw any exception
    }
    try { // Tests the normal function of the ParseClothing, but in this case lastWornDate is set
      // to null instead of having a LocalDate
      Clothing compareOne = new Clothing("Shirt", "Abercrombie", 0, null);
      String parseString = "Shirt,Abercrombie,null,0";
      Clothing parsedClothing = Wardrobe.parseClothing(parseString);
      // Checks if the dates match
      if (parsedClothing.getLastWornDate() != null) {
        return false;
      }
      // Check if description and brand match
      if (!compareOne.equals(parsedClothing)) {
        return false;
      }
      // Checks if numTimesWorn is correct
      if (parsedClothing.getNumOfTimesWorn() != 0) {
        return false;
      }
    } catch (ParseException e) {
      e.printStackTrace();
      return false; // Should not throw exception
    } catch (Exception e) {
      e.printStackTrace();
      return false; // Should not throw any exception
    }
    return true;
  }

  /**
   * Runs all testing methods and prints out their results.
   *
   * @return true if and only if all the tests return true, false otherwise
   */
  public static boolean runAllTests() {
    boolean test1 = testClothingConstructorExceptions();
    System.out.println("testClothingConstructorExceptions(): " + (test1 ? "pass" : "FAIL"));

    boolean test2 = testClothingConstructorAndGetters();
    System.out.println("testClothingConstructorAndGetters(): " + (test2 ? "pass" : "FAIL"));

    boolean test3 = testClothingWear();
    System.out.println("testClothingWear(): " + (test3 ? "pass" : "FAIL"));

    boolean test4 = testWardrobeConstructorAndGetters();
    System.out.println("testWardrobeConstructorAndGetters(): " + (test4 ? "pass" : "FAIL"));

    boolean test5 = testWardrobeConstructorExceptions();
    System.out.println("testWardrobeConstructorExceptions(): " + (test5 ? "pass" : "FAIL"));

    boolean test6 = testAddClothingExceptions();
    System.out.println("testAddClothingExceptions(): " + (test6 ? "pass" : "FAIL"));

    boolean test7 = testAddClothing();
    System.out.println("testAddClothing(): " + (test7 ? "pass" : "FAIL"));

    boolean test8 = testGetClothing();
    System.out.println("testGetClothing(): " + (test8 ? "pass" : "FAIL"));

    boolean test9 = testGetClothingExceptions();
    System.out.println("testGetClothingExceptions(): " + (test9 ? "pass" : "FAIL"));

    boolean test10 = testRemoveClothing();
    System.out.println("testRemoveClothing(): " + (test10 ? "pass" : "FAIL"));

    boolean test11 = testRemoveClothingExceptions();
    System.out.println("testRemoveClothingExceptions(): " + (test11 ? "pass" : "FAIL"));

    boolean test12 = testRemoveAllClothingWornBefore();
    System.out.println("testRemoveAllClothingWornBefore(): " + (test12 ? "pass" : "FAIL"));

    boolean test13 = testRemoveAllClothingWornNumTimes();
    System.out.println("testRemoveAllClothingWornNumTimes(): " + (test13 ? "pass" : "FAIL"));

    boolean test14 = testParseClothingExceptions();
    System.out.println("testParseClothingExceptions(): " + (test14 ? "pass" : "FAIL"));

    boolean test15 = testParseClothing();
    System.out.println("testParseClothing(): " + (test15 ? "pass" : "FAIL"));

    return test1 && test2 && test3 && test4 && test5 && test6 && test7 && test8 && test9 && test10 && test11 && test12 && test13 && test14 && test15;
  }

  public static void main(String[] args) {
    System.out.println("runAllTests(): " + runAllTests());
  }
}
