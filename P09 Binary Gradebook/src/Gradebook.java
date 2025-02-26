//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Gradebook for implementing StudentRecords into a BST
// Course:   CS 300 Spring 2024
//
// Author:   Raymond Tu
// Email:    rktu2@wisc.edu email address
// Lecturer: Hobbes LeGault
//
//////////////////////// ASSISTANCE/HELP CITATIONS ////////////////////////////
//
// Persons:         Hobbes LeGault Lectures - Talked about how to implement recursive BST
// functions such as successor, remove, add, and getMin functions, which I used on some of the
// methods
// Online Sources:  None used
//
///////////////////////////////////////////////////////////////////////////////
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class models a grade book for a specific course used to store student records. Every
 * gradebook is iterable, meaning the enhanced for loop can be used to iterate through its
 * contents.
 */
public class Gradebook implements Iterable<StudentRecord> {
  public final String course; // Name of this course
  public final double PASSING_GRADE; // Minimum passing grade for this course
  private boolean passingGradeIteratorEnabled; // Indicates whether the passing grade iterator is
  //  enabled (meaning equals true if this Gradebook is set to iterate through passing grades only)
  private BSTNode<StudentRecord> root; // Root of the BST
  private int size; // Total number of StudentRecords stored in this Gradebook

  /**
   * Constructs an empty Gradebook for a given course and define its passing grade. We assume that
   * this gradebook iterates through every stored grade (meaning that the passingGradeIterator is
   * not enabled by default)
   *
   * @param course       - name of the course
   * @param passingGrade - passing grade of the course
   * @throws IllegalArgumentException -  - if the course name is null or blank, or the passing grade
   *                                  is invalid. Valid passing grades are in the range [0 ...
   *                                  100.0]
   */
  public Gradebook(String course, double passingGrade) {
    if (course == null || course.isBlank() || passingGrade > 100 || passingGrade < 0) {
      throw new IllegalArgumentException("Course is null, blank, or passing grade out of bounds");
    }
    this.course = course;
    this.PASSING_GRADE = passingGrade;
    this.passingGradeIteratorEnabled = false;
  }

  /**
   * Returns true if this BST has an identical layout (all subtrees equal) to the given tree.
   *
   * @param node tree to compare this Gradebook to
   * @return true if the given tree looks identical to the root of this Gradebook
   * @author Ashley Samuelson
   * @see BSTNode#equals(Object)
   */
  public boolean equalBST(BSTNode<StudentRecord> node) {
    return root == node || (root != null && root.equals(node));
  }

  /**
   * Checks whether this Gradebook is empty and false otherwise
   *
   * @return true if this Gradebook is empty and false otherwise
   */
  public boolean isEmpty() {
    if (root == null) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Returns the size of this Gradebook
   *
   * @return the total number of StudentRecord objects stored in this Gradebook
   */
  public int size() {
    return this.size;
  }

  /**
   * Adds a new StudentRecord to this Gradebook. This method tries to add record to this tree and
   * updates its size accordingly. Be sure to update the root to the BSTNode returned by the
   * addStudentHelper() method.
   *
   * @param record
   * @throws IllegalStateException - If a match with record is already in this tree
   */
  public void addStudent(StudentRecord record) {
    if (lookup(record.email) != null) {
      throw new IllegalStateException();
    }
    if (root == null) {
      root = new BSTNode<StudentRecord>(record, null, null);
    } else {
      addStudentHelper(record, root);
    }
    ++size;
  }

  /**
   * Recursive helper method to add a record to the subtree rooted at node
   *
   * @param record - new Student to add
   * @param node   - root of a subtree
   * @return the new root of this BST after adding the record to this tree
   * @throws IllegalStateException - if the subtree rooted at the node contains a duplicate record
   */
  protected static BSTNode<StudentRecord> addStudentHelper(StudentRecord record,
      BSTNode<StudentRecord> node) {
    // First, we need to find where we need to add the value using recursion and make sure it
    // doesn't exist by using the compareTo method we already wrote
    int comparison = record.compareTo(node.getData());
    // CITE: Hobbes Legault, coded an example of the Add Method in Class
    // Make sure the data does not already exist!
    if (comparison == 0) {
      throw new IllegalStateException("Node already exists");
    }
    // If the value is smaller than the root, move it to the left
    if (comparison < 0) { // Move to the left
      if (node.getLeft() == null) {
        node.setLeft(new BSTNode<StudentRecord>(record, null, null));
      } else {
        // Something is at the left node, so recursively call it again
        addStudentHelper(record, node.getLeft());
      }
    }
    // If the value is bigger than the root, move it to the right!
    if (comparison > 0) {
      if (node.getRight() == null) {
        node.setRight(new BSTNode<StudentRecord>(record, null, null));
      } else { // Node is NOT null, so we must recursively call it again!
        addStudentHelper(record, node.getRight());
      }
    }
    // After, then we return the root of the BST
    return node;
  }

  /**
   * Returns a String representation of the contents of this Gradebook in increasing order
   *
   * @return an in-order String representation of this Gradebook
   */
  @Override
  public String toString() {
    String returnString = toStringHelper(root);
    return returnString;
  }

  /**
   * Returns a String representation of the subtree rooted at node in increasing order
   *
   * @param node - root of a subtree
   * @return an in-order String representation of the subtree rooted at node
   */
  protected static String toStringHelper(BSTNode<StudentRecord> node) {
    String returnString = "";
    if (node != null) { // To find minimum value, check left, then current, then right
      // CITE: Hobbes Legault, coded an example of the toString Method in Class
      returnString = returnString + toStringHelper(node.getLeft());
      returnString = returnString + node.getData() + "\n";
      returnString = returnString + toStringHelper(node.getRight());
      return returnString;
    }
    return returnString;
  }

  /**
   * Returns a decreasing-order String representation of the structure of this subtree, indented by
   * four spaces for each level of depth in the larger tree.
   *
   * @param node  current subtree within the larger tree
   * @param depth depth of the current node within the larger tree
   * @return a String representation of the structure of this subtree
   * @author Ashley Samuelson
   */
  protected static String prettyStringHelper(BSTNode<StudentRecord> node, int depth) {
    if (node == null) {
      return "";
    }
    String indent = " ".repeat(depth);
    return prettyStringHelper(node.getRight(),
        depth + 1) + indent + node.getData().name + "\n" + prettyStringHelper(node.getLeft(),
        depth + 1);
  }

  /**
   * Returns a String representation of the structure of this BST. The String should print the
   * StudentRecords in decreasing order (largest-to-smallest), and each StudentRecord should have an
   * indentation (space from the left side of the screen to the student names) that increases by
   * four (4) spaces for each level of depth in the tree. For instance, the root has no indentation,
   * the root's left subtree has an indentation of 4 spaces, and the root's left subtree's right
   * child has an indentation of 8 spaces.
   *
   * @return a String representation of the structure of this BST
   */
  public String prettyString() {
    return prettyStringHelper(root, 0);
  }

  /**
   * Finds a StudentRecord given the associated email address
   *
   * @param email - email address of a student
   * @return the Student associated with the email argument if there is a match, or null otherwise
   */
  public StudentRecord lookup(String email) {
    StudentRecord lookup = new StudentRecord("Jimmy", email, 80);
    return lookupHelper(lookup, root);
  }

  /**
   * Recursive helper method which looks for a given StudentRecord given in the BST rooted at node
   *
   * @param target - the StudentRecord to search in the subtree rooted at node
   * @param node   - root of a subtree of this BST
   * @return the StudentRecord which matches the one passed as input if a match is found in the
   * subtree rooted at node, or null if no match found
   */
  protected static StudentRecord lookupHelper(StudentRecord target, BSTNode<StudentRecord> node) {
    // Use a comparison to determine if we have found the key or which way to go in the binary
    // search tree based on the comparison value
    StudentRecord foundRecord = null;
    if (node != null) {
      int comparison = target.compareTo(node.getData());
      // Negative means go left, positive means go right, 0 means found!
      if (comparison == 0) {
        return node.getData(); // The student has been found!
      }
      if (comparison < 0) { // Go left if not null!
        if (node.getLeft() != null) {
          foundRecord = lookupHelper(target, node.getLeft());
          return foundRecord;
        } else {
          return null;
        }
      }
      if (comparison > 0) {
        if (node.getRight() != null) {
          foundRecord = lookupHelper(target, node.getRight());
          return foundRecord;
        } else {
          return null;
        }
      }
      return foundRecord;
    } else {
      return null; // Current node is null!
    }
  }

  /**
   * Returns the StudentRecord with the lexicographically smallest email in this BST, or null if
   * this Gradebook is empty.
   *
   * @return the StudentRecord with the lexicographically smallest email in this BST
   */
  protected StudentRecord getMin() {
    return getMinHelper(root);
  }

  /**
   * Returns the smallest StudentRecord (with respect to the result of Student.compareTo() method)
   * in the subtree rooted at node
   *
   * @param node - root of a subtree of a binary search tree
   * @return the smallest Student in the subtree rooted at node, or null if the node is null
   */
  protected static StudentRecord getMinHelper(BSTNode<StudentRecord> node) {
    // To find the minimum, we always go left from the root until we find the lowest value that
    // is not null
    // CITE: Hobbes LeGault, coded an example of the getMin recursion in Class
    StudentRecord returnRecord = null;
    if (node == null) {
      return null;
    } else {
      if (node.getLeft() == null) {
        return node.getData(); // If there is no other node in the left most tree, return the
        // current node
      } else {
        returnRecord = getMinHelper(node.getLeft());
      }
    }
    return returnRecord;
  }

  /**
   * Deletes a StudentRecord from this Gradebook given their email, or throws a
   * NoSuchElementException if there is no StudentRecord with the given email.
   *
   * @param email - the email of the student to delete
   * @throws NoSuchElementException - if there is no matching StudentRecord in this Gradebook
   */
  public void removeStudent(String email) {
    if (lookup(email) == null) {
      throw new NoSuchElementException("Student doesn't exist");
    } else {
      StudentRecord compareStudent = new StudentRecord("David", email, 50);
      root = removeStudentHelper(compareStudent, root);
      size--;
    }
  }

  /**
   * Deletes the matching StudentRecord with toDrop if it is found within this tree, or otherwise
   * throws a NoSuchElementException.
   *
   * @param toDrop - the StudentRecord to be removed from this tree
   * @param node   - the root of the subtree to remove the student from
   * @return the new root of the subtree after removing the matching StudentRecord
   * @throws NoSuchElementException - if there is no matching StudentRecord in this subtree
   */
  protected static BSTNode<StudentRecord> removeStudentHelper(StudentRecord toDrop,
      BSTNode<StudentRecord> node) {
    // Base Case:
    // CITE: Hobbes LeGault, coded part of the remove recursion in Class
    if (node == null) {
      throw new NoSuchElementException();
    } else {
      // Recursive Case: Continue to find the matching student
      if (toDrop.compareTo(node.getData()) > 0) { // Go to the Right of Tree
        node.setRight(removeStudentHelper(toDrop, node.getRight()));
        return node;
      } else if (toDrop.compareTo(node.getData()) < 0) { // Go to the Left of tree
        node.setLeft(removeStudentHelper(toDrop, node.getLeft()));
        return node;
      } else { // The comparison is 0, so we have found the student, check number of children
        if (node.getLeft() == null && node.getRight() == null) {
          // There are no children, so set the current node to null
          node = null;
          return node;
        } else if (node.getLeft() == null) {
          node = node.getRight();
          return node; // Doesn't matter if right is null, because 0 children = null root
        } else if (node.getRight() == null) {
          node = node.getLeft();
          return node; // Doesn't matter if right is null, because 0 children = null root
        } else { // There are two children, so we have to set the successor to the current node
          // The successor can only have a right child or none
          StudentRecord compare = getMinHelper(node.getRight());
          node.setRight(removeStudentHelper(compare, node.getRight())); // This will work
          // because we know the minimum node must only have a right child or no children
          node = new BSTNode<StudentRecord>(compare, node.getLeft(), node.getRight());
          return node;
        }
      }
    }
  }

  /**
   * Returns the successor of a target StudentRecord (smallest value in the BST that is larger than
   * the target), or returns null if there is no successor in this Gradebook.
   *
   * @param target - the StudentRecord to find the successor of
   * @return the successor of the target in the Gradebook, or null if none exists
   */
  protected StudentRecord successor(StudentRecord target) {
    if (root == null) {
      return null;
    } else {
      return successorHelper(target, root);
    }
  }

  /**
   * Returns the successor of a target StudentRecord within the subtree (smallest value in the
   * subtree that is larger than the target), or returns null if there is no successor in this
   * subtree.
   *
   * @param target - the StudentRecord to find the successor of
   * @param node   - the subtree to search for a successor to the target
   * @return the successor of the target in the subtree rooted at node, or null if none exists
   */
  protected static StudentRecord successorHelper(StudentRecord target,
      BSTNode<StudentRecord> node) {
    // The first case is when the student target is greater than or equal to the node!
    // CITE: Hobbes LeGault, talked about what to do for successor in class
    if (target.compareTo(node.getData()) > 0) { // Must be in the right subtree
      if (node.getRight() == null) {
        return null; // We have gone all the way right, there is no successor
      }
      return successorHelper(target, node.getRight());
    } else if (target.compareTo(node.getData()) < 0) { // Must be in the left subtree
      // The second case is when the student target is less than the root node
      // If there are no nodes in the left subtree that are bigger than the target, the successor
      // is the root.
      // If there is one node in the left tree that is bigger than the target, the successor must
      // either be that node or to the left subtree of that node
      if (successorHelper(target, node.getLeft()) == null) { // Successor must be the root
        return node.getData(); // Successor is the root, so return the
      } else {
        return successorHelper(target, node.getLeft());
      }
    } else { // Get the minimum
      return getMinHelper(node.getRight());
    }
  }

  /**
   * Enables the passing grade iterator
   */
  public void enablePassingGradeIterator() {
    this.passingGradeIteratorEnabled = true;
  }

  /**
   * Disables the passing grade iterator
   */
  public void disablePassingGradeIterator() {
    this.passingGradeIteratorEnabled = false;
  }



  /**
   * Returns an iterator over the student records in this gradebook in the increasing order.  If the
   * passing grade iterator is enabled, this method returns an iterator that iterates through
   * records with passing grades only while skipping the ones that fail to pass.
   *
   * @return
   */
  @Override
  public Iterator<StudentRecord> iterator() {
    if (passingGradeIteratorEnabled) {
      PassingGradeIterator iterator = new PassingGradeIterator(this);
      return iterator;
    } else {
      GradebookIterator iterator = new GradebookIterator(this);
      return iterator;
    }
  }

  /**
   * Searches for the StudentRecord associated with the provided input email in this BST and checks
   * whether it has a passing grade for this course. The student with the provided email passes the
   * course if their grade is greater or equal to this Gradebook's passingGrade data field. Returns:
   * "No match found." if no match found with email in this Gradebook If a matching  StudentRecord
   * is found, this method returns: matchingStudent.toString() + ": PASS" if the student has a
   * passing grade matchingStudent.toString() + ": FAIL" if the student does not have a passing
   * grade For instance, "Charlie (charlie@wisc.edu) 85: PASS" "Andy (andy@wisc.edu) 56: FAIL"
   *
   * @param email
   * @return
   */
  public String checkPassingCourse(String email) {
    if (lookup(email) == null) {
      return "No match found.";
    } else {
      String student = lookup(email).toString();
      if (lookup(email).getGrade() >= PASSING_GRADE) {
        return student + ": PASS";
      } else {
        return student + ": FAIL";
      }
    }
  }
}
