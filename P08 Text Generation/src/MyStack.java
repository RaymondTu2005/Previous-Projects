//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    MyStack Generic Class
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
import java.util.Collections;

/**
 * A generic singly-linked stack implementation, which contains some additional methods to
 * facilitate the workings of the Markov Model.
 * @param <T> the type of data contained in the stack
 */
public class MyStack <T> implements StackADT<T>{
  private LinkedNode<T> top; // A reference to the LinkedNode currently at the top of the stack,
                             // which is null when the stack is empty
  /**
   * Add a new element to the top of this stack, assumed to be non-null.
   * @param value the value to add
   */
  @Override
  public void push(T value) {
    LinkedNode<T> toAdd = new LinkedNode<T>(value,top);
    top = toAdd;
  }
  /**
   * Removes and returns the value added to this stack most recently
   * @return the most recently-added value, or null if the stack is empty
   */
  @Override
  public T pop() {
    if (!isEmpty()) {
      T returnData = top.getData();
      top = top.getNext(); // Change the top reference so the current top is deleted
      return returnData;
    } else {
      return null;
    }
  }

  /**
   * Accesses the value added to this stack most recently, without modifying the stack
   * @return the most recently-added value, or null if the stack is empty
   */
  @Override
  public T peek() {
    if (isEmpty()) {
      return null;
    } else {
      return top.getData(); // Returns the top of the stack data
    }
  }
  /**
   * Returns true if this stack contains no elements.
   * @return true if the stack contains no elements, false otherwise
   */
  @Override
  public boolean isEmpty() {
    return top == null; // Returns true if top of stack is null, false if not null
  }

  /**
   * Randomly reorder the contents of this stack:
   * 1. Create an ArrayList representation of all of the elements of this stack, in order
   * 2. Use Collections.shuffle() to create a new random ordering of the contents
   * 3. REPLACE the current contents of the stack with the contents in their new order from the
   * ArrayList
   * IMPORTANT: By the conventions established in getList(), the top of the stack is at index 0 in
   * the ArrayList representation!
   */
  public void shuffle() {
    ArrayList<T> shuffleArrayList = new ArrayList<T>();
    LinkedNode<T> tempStore = top;
    while (tempStore != null) {
      shuffleArrayList.add(tempStore.getData());
      tempStore = tempStore.getNext();
    }
    Collections.shuffle(shuffleArrayList);
    top = null; // Clear the LinkedNode List before making a new one
    for (int i = shuffleArrayList.size() - 1; i >= 0; --i) {
      push(shuffleArrayList.get(i));
    }
  }

  /**
   * Creates a copy of the current contents of this stack in the order they are present here, in
   * ArrayList form. This method should traverse the stack without removing any elements, and add
   * the values (not the nodes!) to an ArrayList in the order they appear in the stack, with the
   * top of the stack at index 0.
   * @return an ArrayList representation of the current state of this stack
   */
  public ArrayList<T> getList() {
    ArrayList<T> toReturn = new ArrayList<T>();
    LinkedNode<T> checkerNode = top;
    while (checkerNode != null) {
      toReturn.add(checkerNode.getData());
      checkerNode = checkerNode.getNext();
    }
    return toReturn;
  }
}
