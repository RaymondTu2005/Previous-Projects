//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    StudentRecord for holding student information like name, email, and grade
// Course:   CS 300 Spring 2024
//
// Author:   Raymond Tu
// Email:    rktu2@wisc.edu email address
// Lecturer: Hobbes LeGault
//
//////////////////////// ASSISTANCE/HELP CITATIONS ////////////////////////////
//
// Persons:         None used
// Online Sources:  (identify each by URL and describe how it helped)
//
///////////////////////////////////////////////////////////////////////////////

/**
 * This class models student course records.
 */
public class StudentRecord implements Comparable<StudentRecord> {
  public final String email; // This student's email address
  private double grade; // This student's current grade
  public final String name; // This student's name

  /**
   * Constructs a new StudentRecord given the name, email, and grade of a given student. If the name
   * or email is null or blank, the constructor throws an IllegalArgumentException. Additionally, if
   * the grade is not between the range 0.0 to 100.0 (inclusive) the constructor throws an
   * IllegalArgumentException.
   *
   * @param name  - the student's name
   * @param email - the student's email
   * @param grade - the student's grade
   * @throws IllegalArgumentException - if the name or email is null or blank or if grade is not in
   *                                  the range 0.0-100.0 (inclusive)
   */
  public StudentRecord(String name, String email, double grade) {
    if (name == null || email == null || name.isBlank() || email.isBlank() || grade > 100 || grade < 0) {
      throw new IllegalArgumentException("Name or email is blank, or grade is out of range");
    }
    this.name = name;
    this.email = email;
    this.grade = grade;
  }

  /**
   * Returns this student's current grade
   *
   * @return this student's current grade
   */
  public double getGrade() {
    return this.grade;
  }

  /**
   * Updates this student's current grade
   *
   * @param grade - the new value of this student's grade
   */
  public void setGrade(double grade) {
    this.grade = grade;
  }

  /**
   * Returns a String representation of this StudentRecord in the following format: "name (email):
   * grade"
   *
   * @return a String representation of this StudentRecord
   */
  @Override
  public String toString() {
    String toReturn = this.name + " (" + this.email + "): " + this.grade;
    return toReturn;
  }

  /**
   * Compares this StudentRecord to other StudentRecord passed as input. StudentRecords are compared
   * with respect to the lexicographical (alphabetical) order of the students emails.
   *
   * @param other - other StudentRecord to compare to
   * @return 0 if the email associated with the other Student is equal to the email of this Student;
   * a value less than 0 if the email associated with this Student is lexicographically less than
   * the email of the other Student argument; and a value greater than 0 if the email associated
   * with this Student is lexicographically greater than the email of the other Student  argument.
   */
  @Override
  public int compareTo(StudentRecord other) {
    return this.email.compareTo(other.email);
  }

  /**
   * Returns true if the given Object is a StudentRecord with an email that matches the email of
   * this StudentRecord.
   *
   * @param o - Object to compare with this StudentRecord.
   * @return true if the given Object is a StudentRecord with an email that matches the email of
   * this StudentRecord
   */
  @Override
  public boolean equals(Object o) {
    if (o instanceof StudentRecord && ((StudentRecord) o).email.equals(this.email)) {
      return true;
    } else {
      return false;
    }
  }
}
