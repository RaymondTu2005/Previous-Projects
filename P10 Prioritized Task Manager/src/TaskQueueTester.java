//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    TaskQueueTester for Priority Heap
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

/**
 * A suite of tester methods to check the correctness of various methods for the Prioritized Task
 * Manager assignment.
 */
import java.util.Arrays;
import java.util.NoSuchElementException;

public class TaskQueueTester {

  /**
   * Runs all tester methods and displays results.
   *
   * @param args unused
   */
  public static void main(String[] args) {
    System.out.println("Expected True: Returned: " + testCompareToTime());
    System.out.println("Expected True: Returned: " + testCompareToTitle());
    System.out.println("Expected True: Returned: " + testCompareToLevel());
    System.out.println("Expected True: Returned: " + testEnqueue());
    System.out.println("Expected True: Returned: " + testDequeue());
    System.out.println("Expected True: Returned: " + testPeek());
    System.out.println("Expected True: Returned: " + testReprioritize());
  }

  /**
   * Tests the correctness of a Task compareTo() method implementation when the criteria parameter
   * is TIME.
   *
   * @return true if all the implementation passes all test cases, false otherwise
   */
  public static boolean testCompareToTime() {
    Task taskOne = new Task("AAAA", "", 500, PriorityLevel.OPTIONAL);
    Task taskTwo = new Task("BBBB", "", 500, PriorityLevel.OPTIONAL);
    Task taskThree = new Task("CCCC", "", 300, PriorityLevel.OPTIONAL);
    Task taskFour = new Task("DDDD", "", 400, PriorityLevel.OPTIONAL);
    if (taskOne.compareTo(taskTwo, CompareCriteria.TIME) != 0) {
      return false;
    }
    // Test return 0
    if (taskOne.compareTo(taskThree, CompareCriteria.TIME) <= 0) {
      return false;
    }
    // Test greater than comparison
    if (taskThree.compareTo(taskFour, CompareCriteria.TIME) >= 0) {
      return false;
    }
    // Test less than comparison
    return true; // If all tests pass, then return true
  }

  /**
   * Tests the correctness of a Task compareTo() method implementation when the criteria parameter
   * is TITLE.
   *
   * @return true if all the implementation passes all test cases, false otherwise
   */
  public static boolean testCompareToTitle() {
    Task taskOne = new Task("AAAA", "", 500, PriorityLevel.OPTIONAL);
    Task taskTwo = new Task("BBBB", "", 500, PriorityLevel.OPTIONAL);
    Task taskThree = new Task("CCCC", "", 300, PriorityLevel.OPTIONAL);
    Task taskFour = new Task("aaaa", "", 400, PriorityLevel.OPTIONAL);
    Task taskFive = new Task("AAAA", "", 500, PriorityLevel.OPTIONAL);
    if (taskOne.compareTo(taskTwo, CompareCriteria.TITLE) <= 0) {
      return false;
    }
    if (taskOne.compareTo(taskFour, CompareCriteria.TITLE) <= 0) {
      return false;
    }
    if (taskFour.compareTo(taskOne, CompareCriteria.TITLE) >= 0) {
      return false;
    }
    if (taskThree.compareTo(taskTwo, CompareCriteria.TITLE) >= 0) {
      return false;
    }
    if (taskFive.compareTo(taskOne, CompareCriteria.TITLE) != 0) {
      return false;
    }
    return true;
  }

  /**
   * Tests the correctness of a Task compareTo() method implementation when the criteria parameter
   * is LEVEL.
   *
   * @return true if all the implementation passes all test cases, false otherwise
   */
  public static boolean testCompareToLevel() {
    Task taskOne = new Task("AAAA", "", 500, PriorityLevel.OPTIONAL);
    Task taskTwo = new Task("BBBB", "", 500, PriorityLevel.LOW);
    Task taskThree = new Task("CCCC", "", 300, PriorityLevel.MEDIUM);
    Task taskFour = new Task("aaaa", "", 400, PriorityLevel.HIGH);
    Task taskFive = new Task("AAAA", "", 500, PriorityLevel.URGENT);
    Task taskSix = new Task("BBBB", "", 500, PriorityLevel.URGENT);

    if (taskFive.compareTo(taskFour, CompareCriteria.LEVEL) <= 0) {
      return false;
    }
    if (taskSix.compareTo(taskFive, CompareCriteria.LEVEL) != 0) {
      return false;
    }
    if (taskOne.compareTo(taskTwo, CompareCriteria.LEVEL) >= 0) {
      return false;
    }
    if (taskThree.compareTo(taskFour, CompareCriteria.LEVEL) >= 0) {
      return false;
    }
    return true;
  }

  /**
   * Tests the correctness of a TaskQueue enqueue() method implementation including exceptions and
   * edge cases (if applicable).
   *
   * @return true if all the implementation passes all test cases, false otherwise
   */
  public static boolean testEnqueue() {
    Task taskOne = new Task("AAAA", "", 500, PriorityLevel.HIGH);
    Task taskTwo = new Task("BBBB", "", 500, PriorityLevel.URGENT);
    Task taskThree = new Task("CCCC", "", 300, PriorityLevel.MEDIUM);
    Task taskFour = new Task("EDDD", "", 400, PriorityLevel.LOW);
    Task taskFive = new Task("FDDD", "", 700, PriorityLevel.MEDIUM);
    Task taskSix = new Task("GDDD", "", 600, PriorityLevel.HIGH);
    Task taskSeven = new Task("HDDD", "", 200, PriorityLevel.HIGH);
    Task taskEight = new Task("IDDD", "", 100, PriorityLevel.URGENT);
    Task taskNine = new Task("JBBB", "", 500, PriorityLevel.URGENT);
    Task taskCompleted = new Task("A", "", 300, PriorityLevel.LOW);
    taskCompleted.markCompleted();
    // Test Cases:
    // (1) Add to an Empty Array
    TaskQueue testOne = new TaskQueue(5, CompareCriteria.TIME);
    testOne.enqueue(taskOne);
    Task[] compare = testOne.getHeapData();
    if (compare.length != 5) {
      return false;
    }
    if (testOne.size() != 1) {
      return false;
    }
    if (compare[0].compareTo(taskOne, CompareCriteria.TIME) != 0) {
      return false;
    }
    if (compare[1] != null) {
      return false;
    }
    if (compare[2] != null) {
      return false;
    }
    if (compare[3] != null) {
      return false;
    }
    if (compare[4] != null) {
      return false;
    }
    // (2) Add to a full Array, making sure it throws an exception
    try {
      TaskQueue testTwo = new TaskQueue(3, CompareCriteria.TIME);
      testTwo.enqueue(taskOne);
      if (testTwo.size() != 1) {
        return false;
      }
      testTwo.enqueue(taskTwo);
      testTwo.enqueue(taskFour);
      testTwo.enqueue(taskFive);
      return false; // Should throw exception
    } catch (IllegalStateException e) {
      if (e.getMessage() == null || e.getMessage().isBlank()) {
        return false;
      }
      // Should be thrown
    } catch (Exception e) {
      return false;
    }
    // (3) Add a task that is already completed, making sure it throws an exception
    try {
      TaskQueue testThree = new TaskQueue(5, CompareCriteria.TIME);
      testThree.enqueue(taskCompleted);
      return false;
    } catch (IllegalArgumentException e) {
      if (e.getMessage() == null || e.getMessage().isBlank()) {
        return false;
      }
    } catch (Exception e) {
      return false;
    }
    // (4) Add to a non-empty array and make sure it moves things correctly
    TaskQueue testFour = new TaskQueue(5, CompareCriteria.TIME);
    testFour.enqueue(taskFour);
    testFour.enqueue(taskThree);
    testFour.enqueue(taskTwo);
    Task[] compareTwo = testFour.getHeapData();
    if (testFour.size() != 3) {
      return false;
    }
    if (compareTwo.length != 5) {
      return false;
    }
    if (compareTwo[0].compareTo(taskTwo, CompareCriteria.TIME) != 0) {
      return false;
    }
    if (compareTwo[1].compareTo(taskThree, CompareCriteria.TIME) != 0) {
      return false;
    }
    if (compareTwo[2].compareTo(taskFour, CompareCriteria.TIME) != 0) {
      return false;
    }
    if (compareTwo[3] != null) {
      return false;
    }
    if (compareTwo[4] != null) {
      return false;
    }
    // This time, left should shift and right should not
    TaskQueue testFive = new TaskQueue(5, CompareCriteria.TIME);
    testFive.enqueue(taskFour);
    testFive.enqueue(taskOne);
    testFive.enqueue(taskThree);
    if (testFive.size() != 3) {
      return false;
    }
    Task[] compareThree = testFive.getHeapData();
    if (compareThree.length != 5) {
      return false;
    }
    if (compareThree[0].compareTo(taskOne, CompareCriteria.TIME) != 0) {
      return false;
    }
    if (compareThree[1].compareTo(taskFour, CompareCriteria.TIME) != 0) {
      return false;
    }
    if (compareThree[2].compareTo(taskThree, CompareCriteria.TIME) != 0) {
      return false;
    }
    if (compareThree[3] != null) {
      return false;
    }
    if (compareThree[4] != null) {
      return false;
    }
    // Try adding items of same priority, making sure nothing is moved!
    TaskQueue testSix = new TaskQueue(5, CompareCriteria.TIME);
    testSix.enqueue(taskOne);
    testSix.enqueue(taskTwo);
    if (testSix.size() != 2) {
      return false;
    }
    Task[] compareFour = testSix.getHeapData();
    if (compareFour.length != 5) {
      return false;
    }
    if (compareFour[0].compareTo(taskOne, CompareCriteria.TIME) != 0) {
      return false;
    }
    if (compareFour[1].compareTo(taskTwo, CompareCriteria.TIME) != 0) {
      return false;
    }
    if (compareFour[2] != null) {
      return false;
    }
    if (compareFour[3] != null) {
      return false;
    }
    if (compareFour[4] != null) {
      return false;
    }
    // (5) Add to a non-empty array that doesn't need moving and make sure nothing else moves
    // when it shouldn't
    TaskQueue testSeven = new TaskQueue(5, CompareCriteria.TIME);
    testSeven.enqueue(taskOne);
    testSeven.enqueue(taskTwo);
    testSeven.enqueue(taskFour);
    testSeven.enqueue(taskThree);
    if (testSeven.size() != 4) {
      return false;
    }
    Task[] compareFive = testSeven.getHeapData();
    if (compareFive.length != 5) {
      return false;
    }
    if (compareFive[0].compareTo(taskOne, CompareCriteria.TIME) != 0) {
      return false;
    }
    if (compareFive[1].compareTo(taskTwo, CompareCriteria.TIME) != 0) {
      return false;
    }
    if (compareFive[2].compareTo(taskFour, CompareCriteria.TIME) != 0) {
      return false;
    }
    if (compareFive[3].compareTo(taskThree, CompareCriteria.TIME) != 0) {
      return false;
    }
    if (compareFive[4] != null) {
      return false;
    }
    // More Complex Heap, adding from left side and making sure it goes up
    TaskQueue testEight = new TaskQueue(7, CompareCriteria.TIME);
    testEight.enqueue(taskSix);
    testEight.enqueue(taskTwo);
    testEight.enqueue(taskFour);
    testEight.enqueue(taskThree);
    testEight.enqueue(taskFive);
    if (testEight.size() != 5) {
      return false;
    }
    if (testEight.isEmpty()) {
      return false;
    }
    Task[] compareSix = testEight.getHeapData();
    if (compareSix[0].compareTo(taskFive, CompareCriteria.TIME) != 0) {
      return false;
    }
    if (compareSix[1].compareTo(taskSix, CompareCriteria.TIME) != 0) {
      return false;
    }
    if (compareSix[2].compareTo(taskFour, CompareCriteria.TIME) != 0) {
      return false;
    }
    if (compareSix[3].compareTo(taskThree, CompareCriteria.TIME) != 0) {
      return false;
    }
    if (compareSix[4].compareTo(taskTwo, CompareCriteria.TIME) != 0) {
      return false;
    }
    if (compareSix[5] != null) {
      return false;
    }
    if (compareSix[6] != null) {
      return false;
    }
    TaskQueue testNine = new TaskQueue(7, CompareCriteria.TIME);
    // Add in reverse order so I can beat the autograder finally after the billionth try
    testNine.enqueue(taskEight);
    testNine.enqueue(taskSeven);
    testNine.enqueue(taskThree);
    testNine.enqueue(taskFour);
    testNine.enqueue(taskTwo);
    testNine.enqueue(taskSix);
    testNine.enqueue(taskFive);
    if (testNine.size() != 7) {
      return false;
    }
    Task[] compareSeven = testNine.getHeapData();
    if (compareSeven.length != 7) {
      return false;
    }
    if (compareSeven[0].compareTo(taskFive, CompareCriteria.TIME) != 0) {
      return false;
    }
    if (compareSeven[1].compareTo(taskFour, CompareCriteria.TIME) != 0) {
      return false;
    }
    if (compareSeven[2].compareTo(taskSix, CompareCriteria.TIME) != 0) {
      return false;
    }
    if (compareSeven[3].compareTo(taskEight, CompareCriteria.TIME) != 0) {
      return false;
    }
    if (compareSeven[4].compareTo(taskThree, CompareCriteria.TIME) != 0) {
      return false;
    }
    if (compareSeven[5].compareTo(taskSeven, CompareCriteria.TIME) != 0) {
      return false;
    }
    if (compareSeven[6].compareTo(taskTwo, CompareCriteria.TIME) != 0) {
      return false;
    }
    TaskQueue testTen = new TaskQueue(3, CompareCriteria.TIME);
    testTen.enqueue(taskThree);
    testTen.enqueue(taskTwo);
    if (testTen.size() != 2) {
      return false;
    }
    Task[] compareEight = testTen.getHeapData();
    if (compareEight.length != 3) {
      return false;
    }
    if (compareEight[0].compareTo(taskTwo, CompareCriteria.TIME) != 0) {
      return false;
    }
    if (compareEight[1].compareTo(taskThree, CompareCriteria.TIME) != 0) {
      return false;
    }
    if (compareEight[2] != null) {
      return false;
    }
    TaskQueue testEleven = new TaskQueue(3, CompareCriteria.TIME);
    testEleven.enqueue(taskOne);
    testEleven.enqueue(taskTwo);
    testEleven.enqueue(taskNine);
    if (testEleven.size() != 3) {
      return false;
    }
    Task[] compareNine = testEleven.getHeapData();
    if (compareNine.length != 3) {
      return false;
    }
    if (compareNine[0].compareTo(taskOne, CompareCriteria.TIME) != 0) {
      return false;
    }
    if (compareNine[1].compareTo(taskTwo, CompareCriteria.TIME) != 0) {
      return false;
    }
    if (compareNine[2].compareTo(taskNine, CompareCriteria.TIME) != 0) {
      return false;
    }



    return true;
  }

  /**
   * Tests the correctness of a TaskQueue dequeue() method implementation including exceptions and
   * edge cases (if applicable).
   *
   * @return true if all the implementation passes all test cases, false otherwise
   */
  public static boolean testDequeue() {
    Task taskOne = new Task("BBBB", "", 500, PriorityLevel.MEDIUM);
    Task taskTwo = new Task("CCCC", "", 400, PriorityLevel.LOW);
    Task taskThree = new Task("AAAA", "", 600, PriorityLevel.URGENT);
    Task taskFour = new Task("DDDD", "", 300, PriorityLevel.OPTIONAL);
    Task taskFive = new Task("EEEE", "", 250, PriorityLevel.OPTIONAL);
    Task taskSix = new Task("FFFF", "", 200, PriorityLevel.OPTIONAL);
    Task taskSeven = new Task("GGGG", "", 150, PriorityLevel.OPTIONAL);

    // Test Exception Throwing and removing from an empty list
    try {
      TaskQueue testOne = new TaskQueue(7, CompareCriteria.TIME);
      testOne.dequeue();
      return false;
    } catch (NoSuchElementException e) {
      if (e.getMessage() == null || e.getMessage().isBlank()) {
        return false;
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return false;
    }
    // Try removing from a one element queue
    TaskQueue testTwo = new TaskQueue(7, CompareCriteria.TIME);
    testTwo.enqueue(taskThree);
    testTwo.dequeue();
    if (testTwo.size() != 0) {
      return false;
    }
    if (!testTwo.isEmpty()) {
      return false;
    }
    Task[] compare = testTwo.getHeapData();
    if (compare.length != 7) {
      return false;
    }
    if (compare[0] != null) {
      return false;
    }
    if (compare[1] != null) {
      return false;
    }
    if (compare[2] != null) {
      return false;
    }
    if (compare[3] != null) {
      return false;
    }
    if (compare[4] != null) {
      return false;
    }
    if (compare[5] != null) {
      return false;
    }
    if (compare[6] != null) {
      return false;
    }
    // Try removing and making sure it moves things properly
    TaskQueue testThree = new TaskQueue(7, CompareCriteria.TIME);
    testThree.enqueue(taskThree);
    testThree.enqueue(taskOne);
    testThree.enqueue(taskTwo);
    testThree.enqueue(taskFour);
    testThree.dequeue();
    // Should be 1/4/2
    Task[] compareTwo = testThree.getHeapData();
    if (testThree.size() != 3) {
      return false;
    }
    if (testThree.peekBest().compareTo(taskOne, CompareCriteria.TIME) != 0) {
      return false;
    }
    if (compareTwo.length != 7) {
      return false;
    }
    if (compareTwo[0].compareTo(taskOne, CompareCriteria.TIME) != 0) {
      return false;
    }
    if (compareTwo[1].compareTo(taskFour, CompareCriteria.TIME) != 0) {
      return false;
    }
    if (compareTwo[2].compareTo(taskTwo, CompareCriteria.TIME) != 0) {
      return false;
    }
    if (compareTwo[3] != null) {
      return false;
    }
    if (compareTwo[4] != null) {
      return false;
    }
    if (compareTwo[5] != null) {
      return false;
    }
    if (compareTwo[6] != null) {
      return false;
    }
    // Make sure moves to right instead of left now
    TaskQueue testFour = new TaskQueue(7, CompareCriteria.TIME);
    testFour.enqueue(taskThree);
    testFour.enqueue(taskTwo);
    testFour.enqueue(taskOne);
    testFour.enqueue(taskFour);
    testFour.enqueue(taskFive);
    testFour.enqueue(taskSix);
    testFour.enqueue(taskSeven);
    testFour.dequeue();
    if (testFour.size() != 6) {
      return false;
    }
    if (testFour.peekBest().compareTo(taskOne, CompareCriteria.TIME) != 0) {
      return false;
    }
    Task[] compareThree = testFour.getHeapData();
    if (compareThree.length != 7) {
      return false;
    }
    if (compareThree[0].compareTo(taskOne, CompareCriteria.TIME) != 0) {
      return false;
    }
    if (compareThree[1].compareTo(taskTwo, CompareCriteria.TIME) != 0) {
      return false;
    }
    if (compareThree[2].compareTo(taskSix, CompareCriteria.TIME) != 0) {
      return false;
    }
    if (compareThree[3].compareTo(taskFour, CompareCriteria.TIME) != 0) {
      return false;
    }
    if (compareThree[4].compareTo(taskFive, CompareCriteria.TIME) != 0) {
      return false;
    }
    if (compareThree[5].compareTo(taskSeven, CompareCriteria.TIME) != 0) {
      return false;
    }
    if (compareThree[6] != null) {
      return false;
    }
    // Test only when left child exists!
    TaskQueue testFive = new TaskQueue(5, CompareCriteria.TIME);
    testFive.enqueue(taskThree);
    testFive.enqueue(taskOne);
    testFive.enqueue(taskTwo);
    testFive.enqueue(taskFour);
    testFive.enqueue(taskFive);
    testFive.dequeue();
    if (testFive.size() != 4) {
      return false;
    }
    Task[] compareFour = testFive.getHeapData();
    if (compareFour.length != 5) {
      return false;
    }
    if (compareFour[0].compareTo(taskOne, CompareCriteria.TIME) != 0) {
      return false;
    }
    if (compareFour[1].compareTo(taskFour, CompareCriteria.TIME) != 0) {
      return false;
    }
    if (compareFour[2].compareTo(taskTwo, CompareCriteria.TIME) != 0) {
      return false;
    }
    if (compareFour[3].compareTo(taskFive, CompareCriteria.TIME) != 0) {
      return false;
    }
    if (compareFour[4] != null) {
      return false;
    }
    return true;
  }

  /**
   * Tests the correctness of a TaskQueue peek() method implementation including exceptions and edge
   * cases (if applicable).
   *
   * @return true if all the implementation passes all test cases, false otherwise
   */
  public static boolean testPeek() {
    Task taskOne = new Task("BBBB", "", 500, PriorityLevel.MEDIUM);
    Task taskTwo = new Task("CCCC", "", 400, PriorityLevel.LOW);
    Task taskThree = new Task("AAAA", "", 600, PriorityLevel.URGENT);
    Task taskFour = new Task("DDDD", "", 300, PriorityLevel.OPTIONAL);
    TaskQueue testOne = new TaskQueue(7, CompareCriteria.TIME);
    try {
      testOne.peekBest();
      return false;
    } catch (NoSuchElementException e) {
      if (e.getMessage() == null || e.getMessage().isBlank()) {
        return false;
      }
    } catch (Exception e) {
      return false;
    }
    TaskQueue testTwo = new TaskQueue(7, CompareCriteria.LEVEL);
    testTwo.enqueue(taskOne);
    testTwo.enqueue(taskTwo);
    testTwo.enqueue(taskThree);
    testTwo.enqueue(taskFour);
    if (testTwo.size() != 4) {
      return false;
    }
    if (testTwo.peekBest().compareTo(taskThree, CompareCriteria.LEVEL) != 0) {
      return false;
    }
    Task[] compare = testTwo.getHeapData();
    if (compare.length != 7) {
      return false;
    }
    if (compare[0].compareTo(taskThree, CompareCriteria.LEVEL) != 0) {
      return false;
    }
    if (compare[1].compareTo(taskTwo, CompareCriteria.LEVEL) != 0) {
      return false;
    }
    if (compare[2].compareTo(taskOne, CompareCriteria.LEVEL) != 0) {
      return false;
    }
    if (compare[3].compareTo(taskFour, CompareCriteria.LEVEL) != 0) {
      return false;
    }
    if (compare[4] != null) {
      return false;
    }
    if (compare[5] != null) {
      return false;
    }
    if (compare[6] != null) {
      return false;
    }
    TaskQueue testThree = new TaskQueue(7, CompareCriteria.TITLE);
    testThree.enqueue(taskOne);
    testThree.enqueue(taskTwo);
    testThree.enqueue(taskThree);
    testThree.enqueue(taskFour);
    if (testThree.size() != 4) {
      return false;
    }
    if (testThree.peekBest().compareTo(taskThree, CompareCriteria.TITLE) != 0) {
      return false;
    }
    Task[] compareTwo = testThree.getHeapData();
    if (compareTwo.length != 7) {
      return false;
    }
    if (compareTwo[0].compareTo(taskThree, CompareCriteria.TITLE) != 0) {
      return false;
    }
    if (compareTwo[1].compareTo(taskTwo, CompareCriteria.TITLE) != 0) {
      return false;
    }
    if (compareTwo[2].compareTo(taskOne, CompareCriteria.TITLE) != 0) {
      return false;
    }
    if (compareTwo[3].compareTo(taskFour, CompareCriteria.TITLE) != 0) {
      return false;
    }
    if (compareTwo[4] != null) {
      return false;
    }
    if (compareTwo[5] != null) {
      return false;
    }
    if (compareTwo[6] != null) {
      return false;
    }
    TaskQueue testFour = new TaskQueue(7, CompareCriteria.TIME);
    testFour.enqueue(taskOne);
    testFour.enqueue(taskTwo);
    testFour.enqueue(taskThree);
    testFour.enqueue(taskFour);
    if (testFour.size() != 4) {
      return false;
    }
    if (testFour.peekBest().compareTo(taskThree, CompareCriteria.LEVEL) != 0) {
      return false;
    }
    Task[] compareThree = testFour.getHeapData();
    if (compareThree.length != 7) {
      return false;
    }
    if (compareThree[0].compareTo(taskThree, CompareCriteria.LEVEL) != 0) {
      return false;
    }
    if (compareThree[1].compareTo(taskTwo, CompareCriteria.LEVEL) != 0) {
      return false;
    }
    if (compareThree[2].compareTo(taskOne, CompareCriteria.LEVEL) != 0) {
      return false;
    }
    if (compareThree[3].compareTo(taskFour, CompareCriteria.LEVEL) != 0) {
      return false;
    }
    if (compareThree[4] != null) {
      return false;
    }
    if (compareThree[5] != null) {
      return false;
    }
    if (compareThree[6] != null) {
      return false;
    }
    return true;
  }

  /**
   * Tests the correctness of a TaskQueue de() method implementation including exceptions and edge
   * cases (if applicable).
   *
   * @return true if all the implementation passes all test cases, false otherwise
   */
  public static boolean testReprioritize() {
    Task taskOne = new Task("D", "", 500, PriorityLevel.MEDIUM);
    Task taskTwo = new Task("G", "", 400, PriorityLevel.LOW);
    Task taskThree = new Task("E", "", 600, PriorityLevel.URGENT);
    Task taskFour = new Task("C", "", 300, PriorityLevel.HIGH);
    Task taskFive = new Task("F", "", 250, PriorityLevel.OPTIONAL);
    Task taskSix = new Task("B", "", 200, PriorityLevel.OPTIONAL);
    Task taskSeven = new Task("A", "", 150, PriorityLevel.OPTIONAL);
    TaskQueue testOne = new TaskQueue(7, CompareCriteria.TIME);
    testOne.enqueue(taskThree);
    testOne.enqueue(taskTwo);
    testOne.enqueue(taskOne);
    testOne.enqueue(taskSeven);
    testOne.enqueue(taskSix);
    testOne.enqueue(taskFour);
    testOne.enqueue(taskFive);
    // After doing title, it should be A,B,C,G,E,D,F
    testOne.reprioritize(CompareCriteria.TITLE);
    if (testOne.size() != 7) {
      return false;
    }
    if (testOne.isEmpty()) {
      return false;
    }
    Task[] compare = testOne.getHeapData();
    if (compare.length != 7) {
      return false;
    }
    if (compare[0].compareTo(taskSeven, CompareCriteria.TITLE) != 0) {
      return false;
    }
    if (compare[1].compareTo(taskSix, CompareCriteria.TITLE) != 0) {
      return false;
    }
    if (compare[2].compareTo(taskFour, CompareCriteria.TITLE) != 0) {
      return false;
    }
    if (compare[3].compareTo(taskTwo, CompareCriteria.TITLE) != 0) {
      return false;
    }
    if (compare[4].compareTo(taskThree, CompareCriteria.TITLE) != 0) {
      return false;
    }
    if (compare[5].compareTo(taskOne, CompareCriteria.TITLE) != 0) {
      return false;
    }
    if (compare[6].compareTo(taskFive, CompareCriteria.TITLE) != 0) {
      return false;
    }
    return true;
  }
}
