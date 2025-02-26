//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    MarkovTester for MarkovModel, MyQueue, and MyStack
// Course:   CS 300 Spring 2024
//
// Author:   Raymond Tu
// Email:    rktu2@wisc.edu Email Address
// Lecturer: Hobbes LeGault
//
//////////////////////// ASSISTANCE/HELP CITATIONS ////////////////////////////
//
// Persons:         None
// Online Sources:  None
//
///////////////////////////////////////////////////////////////////////////////

import java.util.ArrayList;

/**
 * This class tests the MarkovModel, MyQueue, and MyStack classes and methods for correct
 * functionality
 */
public class MarkovTester {
  /**
   * Verifies that adding things to the stack correctly increases the stack’s size, and that the
   * ordering of all elements is correct.
   * @return false if test unsuccessful, true if successful
   */
  public static boolean testStackAdd() {
    MyStack<Integer> toTest = new MyStack<Integer>();
    toTest.push(10);
    toTest.push(20);
    toTest.push(30);
    toTest.push(40);
    toTest.push(50);
    ArrayList<Integer> checker = new ArrayList<>();
    checker = toTest.getList();
    // Check that the added values are in the correct position and the size is correct for the stack
    if (checker.get(0) != 50 ) { return false; }
    if (checker.get(1) != 40 ) { return false; }
    if (checker.get(2) != 30 ) { return false; }
    if (checker.get(3) != 20 ) { return false; }
    if (checker.get(4) != 10 ) { return false; }
    if (checker.size() != 5) { return false; }
    return true;
  }
  /**
   * Verifies that removing things from the stack (after adding them) correctly decreases the
   * stack’s size, and that the ordering of all remaining elements is correct. Additionally,
   * it verifies that a stack that has had elements added to it can become empty again later.
   * @return false if test unsuccessful, true if successful
   */
  public static boolean testStackRemove() {
    MyStack<Integer> toTest = new MyStack<Integer>();
    toTest.push(10);
    toTest.push(20);
    toTest.push(30);
    toTest.push(40);
    toTest.push(50);
    Integer resultOne = toTest.pop();
    Integer resultTwo = toTest.pop();
    ArrayList<Integer> checker = new ArrayList<>();
    checker = toTest.getList();
    if (checker.size() != 3 ) { return false; }
    if (checker.get(0) != 30) { return false; }
    if (checker.get(1) != 20) { return false; }
    if (checker.get(2) != 10) { return false; }
    if (resultOne != 50) { return false; }
    if (resultTwo != 40) { return false; }
    // Now, we check if we can empty it now
    toTest.pop();
    toTest.pop();
    toTest.pop();
    // Now, ArrayList should be empty and isEmpty() should return true
    ArrayList<Integer> checkerTwo = new ArrayList<>();
    checkerTwo = toTest.getList();
    if (checkerTwo.size() != 0) { return false; }
    if (toTest.isEmpty() == false) { return false; }
    return true; // If everything else passes, return true;
  }
  /**
   * Verifies that calling shuffle() on the stack results in a stack that still contains all the
   * same elements, but in any order that is different from the original order.
   * @return false if test unsuccessful, true if successful
   */
  public static boolean testStackShuffle() {
    MyStack<Integer> toTest = new MyStack<Integer>();
    toTest.push(10);
    toTest.push(20);
    toTest.push(30);
    toTest.push(40);
    toTest.push(50);
    toTest.push(60);
    toTest.push(70);
    toTest.push(80);
    toTest.shuffle();
    ArrayList<Integer> checker = new ArrayList<>();
    checker = toTest.getList();
    // Check that the no elements were removed and that some of them are in different places
    if (checker.size() != 8) { return false; }
    if (checker.get(0) == 80 && checker.get(1) == 70 && checker.get(2) == 60 &&
        checker.get(3) == 50 && checker.get(4) == 40 && checker.get(5) == 30 && checker.get(6)
        == 20 && checker.get(7) == 10) { return false; }
    return true;
  }
  /**
   * Verifies that adding things to the queue correctly increases the queue’s size, and that the
   * ordering of all elements is correct.
   * @return false if test unsuccessful, true if successful
   */
  public static boolean testQueueAdd() {
    MyQueue<Integer> tester = new MyQueue<Integer>();
    tester.maintainSize(5);
    tester.enqueue(50);
    tester.enqueue(40);
    tester.enqueue(30);
    tester.enqueue(20);
    tester.enqueue(10);
    ArrayList<Integer> checker = new ArrayList<Integer>();
    checker = tester.getList();
    // Check proper elements are at the right spot and that the size is correct
    if (checker.size() != 5 ) { return false; }
    if (checker.get(0) != 50 ) { return false; }
    if (checker.get(1) != 40 ) { return false; }
    if (checker.get(2) != 30 ) { return false; }
    if (checker.get(3) != 20 ) { return false; }
    if (checker.get(4) != 10 ) { return false; }
    return true; // After all the previous tests have passes, then can return true!
  }
  /**
   * Verifies that removing things from the queue (after adding them) correctly decreases the
   * queue’s size, and that the ordering of all remaining elements is correct. This test should
   * also verify that the custom method maintainSize(int) works as described.
   * @return false if test unsuccessful, true if successful
   */
  public static boolean testQueueRemove() {
    // We are first going to test the dequeue method
    MyQueue<Integer> tester = new MyQueue<Integer>();
    tester.enqueue(90); // This value should be removed after dequeue is called
    tester.enqueue(80); // This value should be removed after dequeue is called
    tester.enqueue(70);
    tester.enqueue(60);
    tester.enqueue(50);
    tester.enqueue(40);
    tester.enqueue(30);
    tester.enqueue(20);
    tester.enqueue(10);
    // Afterward, we are going to remove more elements from the list and make sure that the
    // correct ones are removed
    Integer testOne = tester.dequeue();
    Integer testTwo = tester.dequeue();
    ArrayList<Integer> checker = tester.getList();
    if (testOne != 90 ) { return false; }
    if (testTwo != 80 ) { return false; }
    if (checker.size() != 7) { return false; }
    if (checker.get(0) != 70 ) { return false; }
    if (checker.get(1) != 60 ) { return false; }
    if (checker.get(2) != 50 ) { return false; }
    if (checker.get(3) != 40 ) { return false; }
    if (checker.get(4) != 30 ) { return false; }
    if (checker.get(5) != 20 ) { return false; }
    if (checker.get(6) != 10 ) { return false; }
    // Then, after we are going to check and make sure that the maintain size
    tester.maintainSize(5);
    checker = tester.getList();
    if (checker.size() != 5 ) { return false; } // Should maintain a size of 5
    if (checker.get(0) != 50 ) { return false; } // Make sure correct elements not removed
    if (checker.get(1) != 40 ) { return false; } // Make sure correct elements not removed
    if (checker.get(2) != 30 ) { return false; } // Make sure correct elements not removed
    if (checker.get(3) != 20 ) { return false; } // Make sure correct elements not removed
    if (checker.get(4) != 10 ) { return false; } // Make sure correct elements not removed
    ArrayList<Integer> checkerOne = tester.getList();
    checkerOne= tester.getList();
    // Next, we are going to remove all elements from the list and check if it is empty
    tester.dequeue();
    tester.dequeue();
    tester.dequeue();
    tester.dequeue();
    tester.dequeue();
    ArrayList<Integer> checkerTwo = tester.getList();
    checkerTwo = tester.getList();
    if (checkerTwo.size() != 0) { return false; }
    if (!tester.isEmpty()) { return false; }
    return true;
  }

  /**
   * Verifies that calling peek() on both a stack and a queue returns the correct element AND
   * does not make any modifications to the data structure.
   * @return false if test unsuccessful, true if successful
   */
  public static boolean testPeek() {
    MyQueue<Integer> tester = new MyQueue<Integer>();
    MyStack<Integer> testerTwo = new MyStack<>();
    tester.enqueue(50); // Should be shown when peeked
    tester.enqueue(40);
    tester.enqueue(30);
    testerTwo.push(100);
    testerTwo.push(110);
    testerTwo.push(120); // Should be shown when peeked
    Integer queueChecker = tester.peek();
    Integer stackChecker = testerTwo.peek();
    ArrayList<Integer> checkerOne = tester.getList();
    ArrayList<Integer> checkerTwo = testerTwo.getList();
    if (queueChecker != 50) { return false; } // Check proper value shown
    if (stackChecker != 120) { return false; } // Check proper value shown
    if (checkerOne.size() != 3 ) { return false; } // Check proper size
    if (checkerTwo.size() != 3 ) { return false; } // Check proper size
    if (checkerOne.get(0) != 50) { return false; } // Check
    if (checkerOne.get(1) != 40) { return false; }
    if (checkerOne.get(2) != 30) { return false; }
    if (checkerTwo.get(0) != 120) { return false; }
    if (checkerTwo.get(1) != 110) { return false; }
    if (checkerTwo.get(2) != 100) { return false; }
    return true;
  }

  /**
   * This main method calls the tester methods and prints out the result
   * @param args - Arguments to test the methods
   */
  public static void main(String [] args) {
  System.out.println("TestStackAdd: Expected: True | Returned " + testStackAdd());
  System.out.println("TestStackRemove: Expected: True | Returned " + testStackRemove());
  System.out.println("TestStackShuffle: Expected: True | Returned " + testStackShuffle());
  System.out.println("TestQueueAdd: Expected: True | Returned " + testQueueAdd());
  System.out.println("TestQueueRemove: Expected: True | Returned " + testQueueRemove());
  System.out.println("testPeek: Expected: True | Returned " + testPeek());
}
}
