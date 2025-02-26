//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Gradebook Tester for the Gradebook, GradebookIterator, and PassingGradeIterator
// Course:   CS 300 Spring 2024
//
// Author:   Raymond Tu
// Email:    rktu2@wisc.edu email address
// Lecturer: Hobbes LeGault
//
//////////////////////// ASSISTANCE/HELP CITATIONS ////////////////////////////
//
// Persons:         None used
// Online Sources:  None
//
///////////////////////////////////////////////////////////////////////////////

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class tests the Gradebook, GradebookIterator, and PassingGradeIterator
 */
public class GradebookTester {
  /**
   * This method runs all the proper tester methods and shows the output of the tester using a
   * string
   * @param args unused
   */
  public static void main(String[] args) {
    System.out.println("Expected True: Returned: " + constructorTester());
    System.out.println("Expected True: Returned: " + isEmptySizeAddTester());
    System.out.println("Expected True: Returned: " + toStringTester());
    System.out.println("Expected True: Returned: " + prettyStringTester());
    System.out.println("Expected True: Returned: " + lookupTester());
    System.out.println("Expected True: Returned: " + getMinTester());
    System.out.println("Expected True: Returned: " + removeStudentTester());
    System.out.println("Expected True: Returned: " + successorTester());
    System.out.println("Expected True: Returned: " + iteratorTester());
    System.out.println("Expected True: Returned: " + passingIteratorTester());
    Gradebook test = new Gradebook("CS300",50);
  }

  /**
   * This method tests that the constructor works properly and throws the correct methods.
   * @return true if all passed, false otherwise
   */
  public static boolean constructorTester() {
    // Test a perfectly normal gradebook
    try {
      Gradebook tester = new Gradebook("CS300", 70);
      if (tester.PASSING_GRADE != 70) {
        return false;
      }
      if (!tester.course.equals("CS300")) {
        return false;
      }
    } catch (Exception e) {
      // No exception should be thrown, so return false
      System.out.println(e.getMessage());
      return false;
    }
    // Next, test the case where the name is null, which should throw a IllegalArgumentException
    try {
      Gradebook testerTwo = new Gradebook(null, 70);
      return false; // It should catch the exception after this and not continue on
    } catch (IllegalArgumentException e) {
      // This exception should be thrown
    } catch (Exception a) {
      System.out.println(a.getMessage());
      return false; // It should not throw any other exception
    }
    // Next, test the case where the name is blank
    try {
      Gradebook testerThree = new Gradebook("", 70);
      return false; // Should throw an exception
    } catch (IllegalArgumentException e) {
      // This exception should be thrown from the try block
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return false; // Should not catch any other exception
    }
    // Another case, test when the passingGrade is higher than 100
    try {
      Gradebook testerFour = new Gradebook("CS300", 101);
      return false; // Should not continue
    } catch (IllegalArgumentException e) {
      // This should be thrown
    } catch (Exception e) {
      // This should not be thrown
      return false;
    }
    // Last case, test when the passingGrade is lower than 0
    try {
      Gradebook testerFive = new Gradebook("CS300", -1);
      return false; // Should not continue
    } catch (IllegalArgumentException e) {
      // This should be thrown
    } catch (Exception e) {
      // This should not be thrown
      return false;
    }
    return true; // If all the cases pass, then return true
  }

  /**
   * This method tests if the isEmpty, Size, and Add method work properly by creating test BSTs
   * and comparing the expected output to the actual output
   * @return true if all passed, false otherwise
   */
  public static boolean isEmptySizeAddTester() {
    // Test the isEmpty method
    try {
      Gradebook tester = new Gradebook("CS300", 70);
      if (!tester.isEmpty()) {
        return false;
      }
      if (!tester.equalBST(null)) {
        return false;
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return false;
    }
    // Test the Adder Method to an empty BST, checking if it is empty, size
    try {
      StudentRecord studentOne = new StudentRecord("Raymond", "Raymond@gmail.com", 80);
      Gradebook testerOne = new Gradebook("CS300", 70);
      testerOne.addStudent(studentOne);
      if (testerOne.size() != 1) {
        return false;
      }
      if (!testerOne.equalBST(new BSTNode<>(studentOne, null, null))) {
        return false;
      }
      if (testerOne.isEmpty()) {
        return false;
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return false;
    }
    // Try adding a duplicate and make sure that it throws an exception
    try {
      StudentRecord studentOne = new StudentRecord("Raymond", "Raymond@gmail.com", 80);
      StudentRecord studentTwo = new StudentRecord("Raymond", "Raymond@gmail.com", 80);
      Gradebook testerTwo = new Gradebook("CS300", 70);
      testerTwo.addStudent(studentOne);
      testerTwo.addStudent(studentTwo);
      return false; // Should not run since an exception should be thrown
    } catch (IllegalStateException e) {
      // This should be thrown
    } catch (Exception e) {
      // Should not throw any other exceptions
      return false;
    }
    // Test adding regular items to the BST
    try {
      StudentRecord studentOne = new StudentRecord("Raymond", "Raymond@gmail.com", 80);
      StudentRecord studentTwo = new StudentRecord("Richmond", "Richmond@gmail.com", 80);
      Gradebook testerTwo = new Gradebook("CS300", 70);
      testerTwo.addStudent(studentOne);
      testerTwo.addStudent(studentTwo);
      if (testerTwo.size() != 2) {
        return false;
      }
      if (!testerTwo.equalBST(
          new BSTNode<StudentRecord>(studentOne, null, new BSTNode<>(studentTwo, null, null))))
        ;
    } catch (Exception e) {
      // Should not throw any exceptions
      return false;
    }
    return true; // After all tests pass, return true!
  }

  /**
   * This method tests the toString method in the Gradebook class, comparing the actual string to
   * the expected string
   * @return true if all passed, false otherwise
   */
  public static boolean toStringTester() {
    try {
      StudentRecord studentOne = new StudentRecord("Abby", "Abby@gmail.com", 80);
      StudentRecord studentTwo = new StudentRecord("BigDavid", "BigDavid@gmail.com", 80);
      StudentRecord studentThree = new StudentRecord("Carl", "Carl@gmail.com", 80);
      StudentRecord studentFour = new StudentRecord("Far", "Far@gmail.com", 80);
      StudentRecord studentFive = new StudentRecord("Ed", "Ed", 80);
      StudentRecord studentSix = new StudentRecord("Greg", "Greg", 80);
      Gradebook testerTwo = new Gradebook("CS300", 70);
      testerTwo.addStudent(studentOne);
      testerTwo.addStudent(studentTwo);
      testerTwo.addStudent(studentThree);
      testerTwo.addStudent(studentFour);
      testerTwo.addStudent(studentFive);
      testerTwo.addStudent(studentSix);
      if (testerTwo.size() != 6) {
        return false;
      }
      String actual = testerTwo.toString();
      String expected =
          "Abby (Abby@gmail.com): 80.0" + "\n" + "BigDavid (BigDavid@gmail.com): " + "80.0" + "\n"
              + "Carl (Carl@gmail.com): 80.0" + "\n" + "Ed (Ed): 80.0" + "\n" + "Far (Far@gmail"
              + ".com):" + " 80.0" + "\n" + "Greg (Greg): 80.0" + "\n";
      if (!actual.equals(expected)) {
        return false;
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return false;
    }
    return true; // After all tests pass, return true!
  }

  /**
   * This method tests the prettyStringHelper method, confirming that it displays the correct
   * output based on an expected input
   * @return true if all passed, false otherwise
   */
  public static boolean prettyStringTester() {
    StudentRecord studentOne = new StudentRecord("D", "D", 80);
    StudentRecord studentTwo = new StudentRecord("B", "B", 80);
    StudentRecord studentThree = new StudentRecord("E", "E", 80);
    StudentRecord studentFour = new StudentRecord("A", "A", 80);
    StudentRecord studentFive = new StudentRecord("C", "C", 80);
    Gradebook testOne = new Gradebook("CS300", 70);
    testOne.addStudent(studentOne);
    testOne.addStudent(studentTwo);
    testOne.addStudent(studentThree);
    testOne.addStudent(studentFour);
    testOne.addStudent(studentFive);
    String actual = testOne.prettyString();
    String expected = " E" + "\n" + "D" + "\n" + "  C" + "\n" + " B" + "\n" + "  A" + "\n";
    if (!actual.equals(expected)) {
      return false;
    }
    return true; // returns true after all the other tests pass
  }
  /**
   * This method tests the lookup and lookupHelper method, confirming that it returns the correct
   * values in certain cases and throws the proper exceptions in other cases
   * @return true if all passed, false otherwise
   */
  public static boolean lookupTester() {
    try {
      StudentRecord studentOne = new StudentRecord("D", "D", 80);
      StudentRecord studentTwo = new StudentRecord("B", "B", 80);
      StudentRecord studentThree = new StudentRecord("E", "E", 80);
      StudentRecord studentFour = new StudentRecord("A", "A", 80);
      StudentRecord studentFive = new StudentRecord("C", "C", 80);
      Gradebook testOne = new Gradebook("CS300", 70);
      testOne.addStudent(studentOne);
      testOne.addStudent(studentTwo);
      testOne.addStudent(studentThree);
      testOne.addStudent(studentFour);
      testOne.addStudent(studentFive);
      if (testOne.lookup("F") != null) {
        return false;
      }
      if (testOne.lookup("D") != studentOne) {
        return false;
      }
      if (testOne.lookup("B") != studentTwo) {
        return false;
      }
      if (testOne.lookup("E") != studentThree) {
        return false;
      }
      if (testOne.lookup("A") != studentFour) {
        return false;
      }
      if (testOne.lookup("C") != studentFive) {
        return false;
      }
      if (testOne.lookup("G") != null) {
        return false;
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return false; // If any exception thrown, catch it!
    }
    return true;
  }
  /**
   * This method tests that the getMin method works properly in the Gradebook by creating tester
   * BSTs and calls the method to compare the actual output to the expected output
   * @return true if all passed, false otherwise
   */
  public static boolean getMinTester() {
    // Test it on a normal binary search tree
    StudentRecord studentOne = new StudentRecord("D", "D", 80);
    StudentRecord studentTwo = new StudentRecord("B", "B", 80);
    StudentRecord studentThree = new StudentRecord("E", "E", 80);
    StudentRecord studentFour = new StudentRecord("A", "A", 80);
    StudentRecord studentFive = new StudentRecord("C", "C", 80);
    Gradebook testOne = new Gradebook("CS300", 70);
    testOne.addStudent(studentOne);
    testOne.addStudent(studentTwo);
    testOne.addStudent(studentThree);
    testOne.addStudent(studentFour);
    testOne.addStudent(studentFive);
    if (testOne.getMin() != studentFour) {
      return false;
    }
    // Test it on a empty Gradebook
    Gradebook testTwo = new Gradebook("CS300", 50);
    if (testTwo.getMin() != null) {
      return false;
    }
    // Test on a gradebook where the highest value is the root!
    Gradebook testThree = new Gradebook("CS300", 40);
    testThree.addStudent(studentFour);
    testThree.addStudent(studentTwo);
    testThree.addStudent(studentFive);
    testThree.addStudent(studentOne);
    testThree.addStudent(studentThree);
    if (testThree.getMin() != studentFour) {
      return false;
    }
    return true;
  }

  /**
   * This method tests whether the removeStudentTester method works properly by testing multiple
   * cases:
   * The removeStudentTester() should test the following edge and normal cases: 1. Try removing an
   * element from an empty tree 2. Remove the root from a one-node tree 3. Remove an node that has
   * no children (a leaf) from a non-empty tree 4. Remove a node that has only one child from a
   * non-empty tree 5. Remove a node that has two children from a non-empty tree For the above test
   * cases 3,4, and 5 try considering nodes at different subtrees (left and right), and not each
   * time having the node to be removed at the same side of the tree. 6. Try removing a non-existing
   * element from a non-empty tree.
   * @return true if all cases pass, false otherwise
   */
  public static boolean removeStudentTester() {
    // Try removing an element from an empty tree
    try {
      Gradebook testOne = new Gradebook("CS300", 80);
      testOne.removeStudent("Bread");
      return false; // Should throw an exception so this would not be run
    } catch (NoSuchElementException e) {
      // This should be thrown
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return false;
    }
    // Try removing the root from a one-node tree
    try {
      Gradebook testTwo = new Gradebook("CS300", 70);
      StudentRecord studentFour = new StudentRecord("A", "A", 80);
      testTwo.addStudent(studentFour);
      testTwo.removeStudent("A");
      if (testTwo.size() != 0) {
        return false;
      }
      if (!testTwo.isEmpty()) {
        return false;
      }
      if (!testTwo.equalBST(null)) {
        return false;
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return false;
    }
    // Remove a node that has no children (A leaf)
    try {
      Gradebook testThree = new Gradebook("CS300", 80);
      StudentRecord studentOne = new StudentRecord("C", "C", 80);
      StudentRecord studentTwo = new StudentRecord("B", "B", 80);
      testThree.addStudent(studentOne);
      testThree.addStudent(studentTwo);
      testThree.removeStudent("B");
      if (testThree.size() != 1) {
        return false;
      }
      if (!testThree.equalBST(new BSTNode<>(studentOne, null, null))) {
        return false;
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return false; // No exception should be thrown
    }
    // Remove a node that only has one child from a non-empty tree
    try {
      Gradebook testFour = new Gradebook("CS300", 60);
      StudentRecord studentOne = new StudentRecord("C", "C", 80);
      StudentRecord studentTwo = new StudentRecord("B", "B", 80);
      StudentRecord studentThree = new StudentRecord("A", "A", 80);
      testFour.addStudent(studentOne);
      testFour.addStudent(studentTwo);
      testFour.addStudent(studentThree);
      testFour.removeStudent("B");
      if (testFour.size() != 2) {
        return false;
      }
      String actual = testFour.toString();
      String expected = "A (A): 80.0" + "\n" + "C (C): 80.0" + "\n";
      if (!actual.equals(expected)) {
        return false;
      }
      if (!testFour.equalBST(
          new BSTNode<>(studentOne, new BSTNode<StudentRecord>(studentThree, null, null), null))) {
        return false;
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return false;
    }
    // Try removing a non-existing node from a non-empty tree
    try {
      Gradebook testFive = new Gradebook("CS300", 80);
      StudentRecord studentOne = new StudentRecord("E", "E", 80);
      StudentRecord studentTwo = new StudentRecord("C", "C", 80);
      StudentRecord studentThree = new StudentRecord("B", "B", 80);
      StudentRecord studentFour = new StudentRecord("D", "D", 80);
      testFive.addStudent(studentOne);
      testFive.addStudent(studentTwo);
      testFive.addStudent(studentThree);
      testFive.addStudent(studentFour);
      testFive.removeStudent("Nathan");
      return false; // Exception should have been thrown already
    } catch (NoSuchElementException e) {
      // Should be thrown
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return false; // No other exception should be thrown
    }
    // Test removing from a node that has two children
    try {
      Gradebook testSix = new Gradebook("CS300", 80);
      StudentRecord studentOne = new StudentRecord("E", "E", 80);
      StudentRecord studentTwo = new StudentRecord("C", "C", 80);
      StudentRecord studentThree = new StudentRecord("B", "B", 80);
      StudentRecord studentFour = new StudentRecord("D", "D", 80);
      testSix.addStudent(studentOne);
      testSix.addStudent(studentTwo);
      testSix.addStudent(studentThree);
      testSix.addStudent(studentFour);
      testSix.removeStudent("C");
      if (testSix.size() != 3) {
        return false;
      }
      if (!testSix.equalBST(new BSTNode<StudentRecord>(studentOne,
          new BSTNode<StudentRecord>(studentFour,
              new BSTNode<StudentRecord>(studentThree, null, null), null), null))) {
        return false;
      }
    } catch (NoSuchElementException e) {
      // Should be thrown
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return false; // No other exception should be thrown
    }
    return true;
  }

  /**
   * This method tests whether the successor method in the Gradebook class works properly
   * by creating BSTs and comparing the method output to the expected output
   * @return true if all passed, false otherwise
   */
  public static boolean successorTester() {
    Gradebook test = new Gradebook("CS300", 80);
    StudentRecord studentOne = new StudentRecord("D", "D", 80);
    StudentRecord studentTwo = new StudentRecord("G", "G", 80);
    StudentRecord studentThree = new StudentRecord("E", "E", 80);
    StudentRecord studentFour = new StudentRecord("A", "A", 80);
    StudentRecord studentFive = new StudentRecord("C", "C", 80);
    StudentRecord studentSix = new StudentRecord("B", "B", 80);
    StudentRecord studentSeven = new StudentRecord("G", "G", 80);
    StudentRecord studentEight = new StudentRecord("H", "H", 80);
    test.addStudent(studentOne);
    test.addStudent(studentFive);
    test.addStudent(studentTwo);
    test.addStudent(studentThree);
    test.addStudent(studentFour);
    if (test.successor(studentSix) != studentFive) {
      return false;
    } // Test case where one node
    // in the left tree is greater than the target
    if (test.successor(studentFour) != studentFive) {
      return false;
    }
    if (test.successor(studentOne) != studentThree) {
      return false;
    }
    // Test case where the target data is equal to the root node
    if (test.successor(studentSeven) != null) {
      return false;
    }
    // Test case where the target is equal to the highest value in the BST and should be null
    if (test.successor(studentEight) != null) {
      return false;
    }
    // Test case where the target is greater than every node and should be null

    // Test an empty Gradebook
    Gradebook testTwo = new Gradebook("CS300", 50);
    if (testTwo.successor(studentEight) != null) {
      return false;
    }
    // Test where target is less than root but greater than all values in the left tree
    Gradebook testThree = new Gradebook("CS300", 10);
    testThree.addStudent(studentThree);
    testThree.addStudent(studentFive);
    testThree.addStudent(studentFour);
    if (testThree.successor(studentOne) != studentThree) {
      return false;
    } // Test case where target
    // is less than root but greater than everything else, so successor should be root
    Gradebook testFour = new Gradebook("CS300", 80);
    testFour.addStudent(studentThree);
    testFour.addStudent(studentFive);
    testFour.addStudent(studentFour);
    if (testFour.successor(studentOne) != studentThree) {
      return false;
    }
    return true;
  }

  /**
   * This method tests whether the GradebookIterator works properly or not by checking if it
   * correctly throws exceptions and returns input
   * @return true if all passed, false otherwise
   */
  public static boolean iteratorTester() {
    Gradebook test = new Gradebook("CS300", 80);
    StudentRecord studentOne = new StudentRecord("A", "A", 80);
    StudentRecord studentTwo = new StudentRecord("B", "B", 80);
    StudentRecord studentThree = new StudentRecord("C", "C", 80);
    StudentRecord studentFour = new StudentRecord("D", "D", 80);
    StudentRecord studentFive = new StudentRecord("E", "E", 80);
    StudentRecord studentSix = new StudentRecord("F", "F", 80);
    StudentRecord studentSeven = new StudentRecord("G", "G", 80);
    StudentRecord studentEight = new StudentRecord("H", "H", 80);
    test.addStudent(studentOne);
    test.addStudent(studentTwo);
    test.addStudent(studentThree);
    test.addStudent(studentFour);
    test.addStudent(studentFive);
    test.addStudent(studentSix);
    test.addStudent(studentSeven);
    test.addStudent(studentEight);
    Iterator<StudentRecord> testIterator = test.iterator();
    if (testIterator.next().compareTo(studentOne) != 0) {
      return false;
    }
    if (testIterator.next().compareTo(studentTwo) != 0) {
      return false;
    }
    if (testIterator.next().compareTo(studentThree) != 0) {
      return false;
    }
    if (testIterator.next().compareTo(studentFour) != 0) {
      return false;
    }
    if (testIterator.next().compareTo(studentFive) != 0) {
      return false;
    }
    if (testIterator.next().compareTo(studentSix) != 0) {
      return false;
    }
    if (testIterator.next().compareTo(studentSeven) != 0) {
      return false;
    }
    if (testIterator.next().compareTo(studentEight) != 0) {
      return false;
    }
    if (testIterator.hasNext()) {
      return false;
    }
    // Test if throws exception
    try {
      testIterator.next();
      return false; // Exception should be thrown
    } catch (NoSuchElementException e) {
      // Should be thrown
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return false;
    }
    return true;
  }
  /**
   * This tester tests whether the passingIteratorTester works properly or not by creating a
   * Gradebook BST and using the passingIteratorTester to check if it displays the correct
   * students that are currently passing the class
   * @return true if all passed, false otherwise
   */
  public static boolean passingIteratorTester() {
    Gradebook test = new Gradebook("CS300", 75);
    StudentRecord studentOne = new StudentRecord("A", "A", 80);
    StudentRecord studentTwo = new StudentRecord("B", "B", 70);
    StudentRecord studentThree = new StudentRecord("C", "C", 80);
    StudentRecord studentFour = new StudentRecord("D", "D", 70);
    StudentRecord studentFive = new StudentRecord("E", "E", 80);
    StudentRecord studentSix = new StudentRecord("F", "F", 70);
    StudentRecord studentSeven = new StudentRecord("G", "G", 80);
    StudentRecord studentEight = new StudentRecord("H", "H", 70);
    test.enablePassingGradeIterator();
    test.addStudent(studentOne);
    test.addStudent(studentTwo);
    test.addStudent(studentThree);
    test.addStudent(studentFour);
    test.addStudent(studentFive);
    test.addStudent(studentSix);
    test.addStudent(studentSeven);
    test.addStudent(studentEight);
    Iterator<StudentRecord> testIterator = test.iterator();
    if (testIterator.next().compareTo(studentOne) != 0) {
      return false;
    }
    if (testIterator.next().compareTo(studentThree) != 0) {
      return false;
    }
    if (testIterator.next().compareTo(studentFive) != 0) {
      return false;
    }
    if (testIterator.next().compareTo(studentSeven) != 0) {
      return false;
    }
    try { // Tests using next throws an exception properly
      testIterator.next();
      return false; // Exception should have been thrown
    } catch (NoSuchElementException e) {
      // Should be thrown
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return false;
    }
    return true;
  }
}
