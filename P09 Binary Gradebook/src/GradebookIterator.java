//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    GradebookIterator for going through students in Gradebook BST
// Course:   CS 300 Spring 2024
//
// Author:   Raymond Tu
// Email:    rktu2@wisc.edu email address
// Lecturer: Hobbes LeGault
//
//////////////////////// ASSISTANCE/HELP CITATIONS ////////////////////////////
//
// Persons:         None used
// Online Sources:  None used
//
///////////////////////////////////////////////////////////////////////////////

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Iterator for traversing the records in a Gradebook in increasing order without skipping any
 * element.
 */
public class GradebookIterator implements Iterator<StudentRecord> {
  private StudentRecord current; // Current StudentRecord reference. It also referenced the next
  // StudentRecord to be returned by this iterator.
  private Gradebook gradebook; // Gradebook to iterate over

  /**
   * Creates a new GradebookIterator to iterate over the given Gradebook and initializes current to
   * references the minimum studentRecord in the gradebook.
   *
   * @param gradebook - Gradebook to iterate over.
   */
  public GradebookIterator(Gradebook gradebook) {
    this.gradebook = gradebook;
    current = gradebook.getMin();
  }

  /**
   * Returns true if the iteration has more elements (if current is not null).  (In other words,
   * returns true if next() would return an element rather than throwing an exception.)
   *
   * @return true if the iteration has more elements.
   */
  @Override
  public boolean hasNext() {
    if (current == null) {
      return false;
    } else {
      return true;
    }
  }

  /**
   * Returns the next element in the iteration (meaning the current StudentRecord from the
   * Gradebook), and advances the current pointer to the next StudentRecord in the gradebook in the
   * increasing order.
   *
   * @return
   * @throws NoSuchElementException - if the iteration has no more elements (meaning if hasNext()
   *                                returns false)
   */
  @Override
  public StudentRecord next() {
    if (current != null) {
      StudentRecord returnValue = current;
      current = gradebook.successor(current);
      return returnValue;
    } else {
      throw new NoSuchElementException("No next element in the BST");
    }
  }
}
