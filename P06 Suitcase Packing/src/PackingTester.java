//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    The Testing Class for the Packing Project ensuring correct functionality
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
// Online Sources:  None
//
///////////////////////////////////////////////////////////////////////////////

import java.util.ArrayList;

/**
 * Class used for testing the methods in the Packing class.
 */
public class PackingTester {
  /**
   * Tester method for the Packing.rushedPacking() method base cases. It should test at least the
   * following scenarios: - There are no items left to pack in the suitcase - There are items left
   * to pack, but none of them fit
   *
   * @return true if all tests pass, false otherwise
   */
  public static boolean rushedPackingBaseTest() {
    // This test case tests the case where there are items left to pack into the suitcase but
    // none of them can fit into the suitcase
    ArrayList<Item> items = new ArrayList<>();
    Item shirt = new Item("Shirt", 5, 5);
    Item pants = new Item("Pants", 3, 3);
    items.add(shirt);
    items.add(pants);
    Suitcase testOne = new Suitcase(2, 2, items);
    Suitcase testTwo = Packing.rushedPacking(testOne);
    if (testTwo.numItemsUnpacked() != 2) {
      return false;
    } // Should have packed 0 items
    if (testTwo.numItemsPacked() != 0) {
      return false;
    } // Should have packed 0 items
    if (testTwo.areaPacked() != 0) {
      return false;
    } // Should have 0 area packed so far
    if (!testTwo.equals(testOne)) {
      return false;
    } // Should have not modified the suitcase
    // Next, we are going to test a case where there is 0 items to pack into the suitcase, so it
    // should return a unchanged suitcase
    ArrayList<Item> itemsTwo = new ArrayList<>();
    Suitcase testThree = new Suitcase(3, 3, itemsTwo);
    Suitcase testFour = Packing.rushedPacking(testThree);
    if (testFour.numItemsUnpacked() != 0) {
      return false;
    }
    if (testFour.numItemsPacked() != 0) {
      return false;
    }
    if (testFour.areaPacked() != 0) {
      return false;
    }
    if (!testFour.equals(testThree)) {
      return false;
    } // If suitcase is modified, return false
    return true; // If both the previous test cases pass, now we can finally
  }

  /**
   * Tester method for the Packing.rushedPacking() method recursive cases. It should test at least
   * the following scenarios: - All the items remaining can fit in the suitcase - At least one item
   * remaining cannot fit in the suitcase
   *
   * @return true if all tests pass, false otherwise
   */
  public static boolean rushedPackingRecursiveTest() {
    // This test case tests when all the items can fit into the suitcase, making sure that the
    // method correctly recursively calls and returns the correct value
    ArrayList<Item> items = new ArrayList<>();
    Item shirt = new Item("Shirt", 2, 2);
    Item towel = new Item("Towel", 3, 3);
    Item pants = new Item("Pants", 2, 2);
    Item skinCare = new Item("Skincare", 3, 3);
    items.add(shirt);
    items.add(towel);
    items.add(pants);
    items.add(skinCare);
    Suitcase testOne = new Suitcase(6, 6, items); // Should be able to fit all items
    Suitcase testTwo = Packing.rushedPacking(testOne);
    if (testTwo.numItemsPacked() != 4) {
      return false; // Should have packed all the items
    }
    if (testTwo.numItemsUnpacked() != 0) {
      return false; // Should have packed all the items
    }
    if (testTwo.areaPacked() != 26) {
      return false; // Should pack 4 items with area 4 each
    }
    if (testTwo.equals(testOne)) {
      return false; // Should not be the same
    }
    if (!shirt.equals(testTwo.getPackedItems().get(0))) {
      return false;
    }
    if (!towel.equals(testTwo.getPackedItems().get(1))) {
      return false;
    }
    if (!pants.equals(testTwo.getPackedItems().get(2))) {
      return false;
    }
    if (!skinCare.equals(testTwo.getPackedItems().get(3))) {
      return false;
    }
    // This next test case does the same thing, except that the last item cannot fit into the
    // suitcase and should not pack it
    ArrayList<Item> itemsTwo = new ArrayList<>();
    Item jeans = new Item("Jeans", 6, 6);
    itemsTwo.add(shirt); // Item previously initialized and reused, should fit into suitcase
    itemsTwo.add(towel); // Item previously initialized and reused, should fit into suitcase
    itemsTwo.add(pants); // Item previously initialized and reused, should fit into suitcase
    itemsTwo.add(skinCare); // Item previously initialized and reused, should fit into suitcase
    itemsTwo.add(jeans); // New item initialized, should NOT fit into the suitcase
    Suitcase testThree = new Suitcase(6, 6, itemsTwo);
    Suitcase testFour = Packing.rushedPacking(testThree);
    if (testFour.numItemsPacked() != 4) {
      return false;
    } // Should have packed 4/5 items
    if (testFour.numItemsUnpacked() != 1) {
      return false; // Should have not packed one item
    }
    if (testFour.equals(testThree)) {
      return false; // Should have modified the suitcase
    }
    if (testFour.areaPacked() != 26) {
      return false; // Should have packed 4 items with area 4
    }
    if (!jeans.equals(testFour.getUnpackedItems().get(0))) {
      return false; // Should be same item
    }
    if (!shirt.equals(testFour.getPackedItems().get(0))) {
      return false; // Should return same item
    }
    if (!towel.equals(testFour.getPackedItems().get(1))) {
      return false; // Should return same item
    }
    if (!pants.equals(testFour.getPackedItems().get(2))) {
      return false; // Should return same item
    }
    if (!skinCare.equals(testFour.getPackedItems().get(3))) {
      return false; // Should return same item
    }
    return true; // returns true after all the previous tests pass
  }

  /**
   * Tester method for the Packing.greedyPacking() method base cases. It should test at least the
   * following scenarios: - There are no items left to pack in the suitcase - There are items left
   * to pack, but none of them fit
   *
   * @return true if all tests pass, false otherwise
   */
  public static boolean greedyPackingBaseTest() {
    // Firstoff, we are going to test when there are no items left to pack into the suitcase.
    ArrayList<Item> testOne = new ArrayList<>();
    Suitcase testerOne = new Suitcase(5, 5, testOne);
    Suitcase testerTwo = Packing.greedyPacking(testerOne);
    if (!testerTwo.equals(testerOne)) {
      return false;
    }
    if (testerTwo.numItemsPacked() != 0) {
      return false;
    }
    if (testerTwo.numItemsUnpacked() != 0) {
      return false;
    }
    if (testerTwo.areaPacked() != 0) {
      return false;
    }
    // Next, we are going to test when there is no available items to fit into the suitcase to
    // make sure that the base case triggers correctly
    ArrayList<Item> testTwo = new ArrayList<>();
    Item shirt = new Item("Shirt", 5, 5);
    Item towel = new Item("Towel", 5, 5);
    Item pants = new Item("Pants", 5, 5);
    testTwo.add(shirt);
    testTwo.add(towel);
    testTwo.add(pants);
    Suitcase testerThree = new Suitcase(4, 4, testTwo);
    Suitcase testerFour = Packing.greedyPacking(testerThree);
    if (testerFour.numItemsUnpacked() != 3) {
      return false;
    }
    if (testerFour.numItemsPacked() != 0) {
      return false;
    }
    if (testerFour.areaPacked() != 0) {
      return false;
    }
    if (!testerFour.equals(testerThree)) {
      return false;
    }
    return true; // After all the previous tests pass, then return true!
  }

  /**
   * Tester method for the Packing.greedyPacking() method recursive cases. It should test at least
   * the following scenarios: - At least one item is packed out of order (an item with a higher
   * index is packed before an item with a lower index) - A scenario where the greedy packing method
   * packs more of the suitcase than the rushed packing (you can use the example given in the
   * writeup)
   *
   * @return true if all tests pass, false otherwise
   */
  public static boolean greedyPackingRecursiveTest() {
    // First, we are going to test the first case, where an item at the first index is not the
    // biggest in the ArrayList, and instead a bigger item exists at different index and is
    // packed accordingly
    ArrayList<Item> testOne = new ArrayList<>();
    Item shirt = new Item("Shirt", 2, 2);
    Item towel = new Item("Towel", 2, 2);
    Item pants = new Item("Pants", 4, 4);
    testOne.add(shirt);
    testOne.add(towel);
    testOne.add(pants);
    Suitcase testerOne = new Suitcase(6, 6, testOne);
    Suitcase testerTwo = Packing.greedyPacking(testerOne);
    if (testerTwo.numItemsPacked() != 3) {
      return false;
    }
    if (testerTwo.numItemsUnpacked() != 0) {
      return false;
    }
    if (!pants.equals(testerTwo.getPackedItems().get(0))) {
      return false; // Makes sure that the first item packed was the biggest one, which was pants
    }
    if (!shirt.equals(testerTwo.getPackedItems().get(1))) {
      return false; // Make sure added in the correct order
    }
    if (!towel.equals(testerTwo.getPackedItems().get(2))) {
      return false; // Make sure added in the correct order
    }
    if (testerTwo.areaPacked() != 24) {
      return false; // Make sure all items were added and have correct size
    }
    // Next, we are going to test the second scenario where greedy packing should give us more
    // areaPacked than rushedPacking
    ArrayList<Item> testTwo = new ArrayList<>();
    Item sweater = new Item("Sweater", 4, 2);
    Item book = new Item("Book", 6, 3);
    Item jacket = new Item("Jacket", 7, 4);
    Item hoodie = new Item("Hoodie", 4, 5);
    Item crewneck = new Item("Crewneck", 4, 5);
    Item wallet = new Item("Wallet", 5, 4);
    Item laptop = new Item("Laptop", 2, 6);
    testTwo.add(sweater);
    testTwo.add(book);
    testTwo.add(jacket);
    testTwo.add(hoodie);
    testTwo.add(crewneck);
    testTwo.add(wallet);
    testTwo.add(laptop);
    Suitcase testerThree = new Suitcase(10, 10, testTwo);
    Suitcase testerFour = Packing.greedyPacking(testerThree);
    if (testerFour.areaPacked() != 80) {
      return false;
    } // Make sure inputted correctly
    if (testerFour.numItemsPacked() != 4) {
      return false;
    }
    if (testerFour.numItemsUnpacked() != 3) {
      return false;
    }
    if (!sweater.equals(testerFour.getUnpackedItems().get(0))) {
      return false;
    }
    if (!book.equals(testerFour.getUnpackedItems().get(1))) {
      return false;
    }
    if (!wallet.equals(testerFour.getUnpackedItems().get(2))) {
      return false;
    }
    if (!jacket.equals(testerFour.getPackedItems().get(0))) {
      return false;
    }
    if (!hoodie.equals(testerFour.getPackedItems().get(1))) {
      return false;
    }
    if (!crewneck.equals(testerFour.getPackedItems().get(2))) {
      return false;
    }
    if (!laptop.equals(testerFour.getPackedItems().get(3))) {
      return false;
    }
    return true; // After all test cases are finished, we can finally then return true
  }

  /**
   * Tester method for the Packing.optimalPacking() method. This tester should test the
   * optimalPacking() method by randomly generating at least TEN (10) different scenarios, and
   * randomly generating at least ONE-HUNDRED (100) different packing solutions for EACH of the
   * scenarios. Each scenario should have at least FIVE (5) random items, and the suitcases should
   * be of size at least 5x5. If any random solution is better than the optimal packing then it is
   * not actually optimal, so the method does not pass the test. You should use the Utilities method
   * to generate random lists of items, and to randomly pack the suitcases.
   *
   * @return true if all tests pass, false otherwise
   */
  public static boolean optimalPackingRandomTest() {
    int i;
    int j;
    // Create a loop to test 10 different scenarios
    for (i = 0; i < 10; ++i) {
      Suitcase testerSuitcase = new Suitcase(7, 7, Utilities.randomItems(7));
      Suitcase testerSuitcaseTwo = Packing.optimalPacking(testerSuitcase);
      // Test 100 different packing solutions
      for (j = 0; j < 100; ++j) {
        Suitcase testerThree = Utilities.randomlyPack(testerSuitcase);
        if (testerThree.areaPacked() > testerSuitcaseTwo.areaPacked()) {
          return false; // Returns false if the optimal test is less than the random one
        }
      }
    }
    return true; // If all scenarios and test cases pass, return true;
  }

  public static void main(String[] args) {
    boolean allPass = true;
    String printFormat = "%-29s %s\n";

    boolean rushedBase = rushedPackingBaseTest();
    allPass &= rushedBase;
    System.out.printf(printFormat, "rushedPackingBaseTest():", rushedBase);

    boolean rushedRecur = rushedPackingRecursiveTest();
    allPass &= rushedRecur;
    System.out.printf(printFormat, "rushedPackingRecursiveTest():", rushedRecur);

    boolean greedyBase = greedyPackingBaseTest();
    allPass &= greedyBase;
    System.out.printf(printFormat, "greedyPackingBaseTest():", greedyBase);

    boolean greedyRecur = greedyPackingRecursiveTest();
    allPass &= greedyRecur;
    System.out.printf(printFormat, "greedyPackingRecursiveTest():", greedyRecur);

    boolean optimalRandom = optimalPackingRandomTest();
    allPass &= optimalRandom;
    System.out.printf(printFormat, "optimalPackingRandomTest():", optimalRandom);

    System.out.printf(printFormat, "All tests:", allPass);
  }
}
