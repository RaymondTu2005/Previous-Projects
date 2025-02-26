//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    PassingGradeIterator for determining passing students in Gradebook
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
import java.util.NoSuchElementException;

/**
 * Iterator for traversing the records in a Gradebook in increasing order, while also skipping over
 * StudentRecords who do not have a passing grade. This iterator iterates through the StudentRecord
 * objects with passing grades, only.
 */
public class PassingGradeIterator extends GradebookIterator {
  private StudentRecord next; // Reference to the current StudentRecord with a passing grade to
  // be returned by this iterator
  private double passingGrade; // Passing grade

  /**
   * Constructs a new PassingGradeIterator to iterate over the StudentRecords with passing grades in
   * a given Gradebook (StudentRecords with NO passing grades are skipped by this iterator). This
   * iterator sets the passing grade to the gradebook passing grade and advances the iterator to the
   * first student record with passing grade in the iteration by calling the
   * advanceToNextPassingGrade() helper method.
   *
   * @param gradebook - Gradebook to iterate over.
   */
  public PassingGradeIterator(Gradebook gradebook) {
    super(gradebook);
    passingGrade = gradebook.PASSING_GRADE;
    advanceToNextPassingGrade();
  }

  /**
   * Private helper method that advances this iterator to the next StudentRecord with a passing
   * grade. Then, it stores it into next. If no more StudentRecord with a passing grade are
   * available in the iteration, this method sets next to null. This method uses super.hasNext() and
   * super.next() in a while loop to operate.
   */
  private void advanceToNextPassingGrade() {
    while (super.hasNext()) {
      StudentRecord checker = super.next();
      if (checker.getGrade() >= passingGrade) {
        next = checker;
        return;
      }
    }
    // No more elements are available with passing grades
    next = null;
  }

  /**
   * Returns true if the iteration has more elements (if next is not null). (In other words, returns
   * true if next() would return an element rather than throwing an exception.)
   *
   * @return true if the iteration has more elements.
   */
  @Override
  public boolean hasNext() {
    if (this.next == null) {
      return false;
    } else {
      return true;
    }
  }

  /**
   * Returns the next StudentRecord object with a passing grade in the iteration and advances the
   * iteration to the next record with passing grade.
   *
   * @return the next StudentRecord with a passing grade in the iteration
   * @throws NoSuchElementException - if the iteration has no more elements (meaning has no more
   *                                StudentRecord objects with a passing grade)
   */
  @Override
  public StudentRecord next() {
    if (next != null) {
      StudentRecord returnValue = next;
      advanceToNextPassingGrade();
      return returnValue;
    } else {
      throw new NoSuchElementException();
    }
  }
}
