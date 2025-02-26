//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    MyQueue Generic Class
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
 * A generic singly-linked queue implementation, which contains some additional methods to
 * facilitate the workings of the Markov Model.
 * @param <T> - the type of data contained in the stack
 */
public class MyQueue<T> implements QueueADT<T>{
  private LinkedNode<T> back; // A reference to the LinkedNode currently at the back of the
                              // queue,  which contains the most-recently added value in the queue.
  private LinkedNode<T> front; // A reference to the LinkedNode currently at the front of the
                               // queue,  which contains the least-recently added value in the
                               // queue.
  private int size; // The number of values currently present in the queue

  /**
   * Enforces a maximum size for this queue. If the queue is already smaller than the requested
   * size, this method does nothing
   * @param size The maximum number of elements this queue should contain once the method has run
   */
  public void maintainSize(int size) {
    while (this.size > size) {
      // Start removing elements from the list until it is equal to the max size
      dequeue();
    }
  }

  /**
   * Creates a copy of the current contents of this queue in the order they are present here, in
   * ArrayList form. This method should traverse the queue without removing any elements, and add
   * the values (not the nodes!) to an ArrayList in the order they appear in the queue.
   * @return an ArrayList representation of the current state of this queue
   */
  public ArrayList<T> getList() {
    ArrayList<T> toReturn = new ArrayList<T>();
    LinkedNode<T> returnValue = front;
    while (returnValue != null) {
      toReturn.add(returnValue.getData());
      returnValue = returnValue.getNext();
    }
    return toReturn;
  }

  /**
   * Concatenates the string representation of all values in this queue in order, from the front
   * of the queue to the back. Does not separate values (no whitespace, no commas).
   * @return the string representation of this queue
   */
  @Override
  public String toString() {
    String toReturn = ""; // Initializes the String
    LinkedNode<T> currentNode = front;
    while (currentNode != null) {
      toReturn = toReturn + currentNode.getData();
      currentNode = currentNode.getNext();
    }
    return toReturn;
  }

  /**
   * Add a new element to the back of the queue, assumed to be non-null.
   * @param value the value to add
   */
  @Override
  public void enqueue(T value) {
    LinkedNode<T> toAdd = new LinkedNode<>(value, null);
    if (size == 0 ) {
      this.back = toAdd;
      this.front = toAdd;
    }
    if (size > 0) {
      back.setNext(toAdd);
      back = toAdd;
    }
    size++;
  }

  /**
   * Removes and returns the value added to this queue least recently
   * @return the least recently-added value, or null if the queue is empty
   */
  @Override
  public T dequeue() {
    T toReturn;
    if (size >= 2) {
      toReturn = front.getData();
      front = front.getNext();
      size--;
      return toReturn;
    } else if (size == 1) {
      toReturn = front.getData();
      front = null;
      back = null;
      size--;
      return toReturn;
    }
    else { // Size is 0 already
      return null;
    }
  }

  /**
   * Accesses the value added to this queue least recently, without modifying the queue
   * @return the least recently-added value, or null if the queue is empty
   */
  @Override
  public T peek() {
    if (!isEmpty()) {
      return front.getData();
    } else {
      return null;
    }
  }

  /**
   * Returns true if this queue contains no elements.
   * @return true if the queue contains no elements, false otherwise
   */
  @Override
  public boolean isEmpty() {
    return size == 0 && front == null && back == null;
  }

  /**
   * Returns the number of elements in the queue.
   * @return the number of elements in the queue
   */
  @Override
  public int size() {
    return this.size;
  }
}
