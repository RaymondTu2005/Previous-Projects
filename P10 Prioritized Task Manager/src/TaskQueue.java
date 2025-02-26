//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    TaskQueue Class for Priority Heap
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

import java.util.Arrays;
import java.util.NoSuchElementException;

public class TaskQueue {
  private Task[] heapData; // oversized array that holds all Tasks in the heap
  private int size; // the number of items in the TaskQueue
  private CompareCriteria priorityCriteria; // the criteria used to determine how to prioritize
  // Tasks in the queue

  public TaskQueue(int capacity, CompareCriteria priorityCriteria) {
    if (capacity <= 0) {
      throw new IllegalArgumentException("Capacity is non-positive");
    }
    heapData = new Task[capacity];
    this.priorityCriteria = priorityCriteria;
  }

  public CompareCriteria getPriorityCriteria() {
    return this.priorityCriteria;
  }

  public boolean isEmpty() {
    if (heapData[0] == null) {
      return true;
    } else {
      return false;
    }
  }

  public int size() {
    return this.size;
  }

  public Task peekBest() {
    if (heapData[0] != null) {
      return heapData[0];
    } else {
      throw new NoSuchElementException("Taskqueue is empty");
    }
  }

  public void enqueue(Task newTask) {
    if (heapData[heapData.length - 1] != null) {
      throw new IllegalStateException("Priority Queue Full");
    } else if (newTask.isCompleted()) {
      throw new IllegalArgumentException("Task is already complete");
    } else {
      heapData[size] = newTask;
      percolateUp(size);
      size++;
    }
  }

  protected void percolateUp(int index) {
    if (index == 0) {
      return;
    }
    // (1) Compare index to parent
    if (heapData[index].compareTo(heapData[((index - 1) / 2)], priorityCriteria) > 0) {
      // Current Index is higher priority than parent, swap them
      Task temp = heapData[((index - 1) / 2)];
      heapData[((index - 1) / 2)] = heapData[index];
      heapData[index] = temp;
    }
    percolateUp((index - 1) / 2);
  }

  public Task dequeue() {
    if (isEmpty()) {
      throw new NoSuchElementException("Taskqueue is empty");
    } else {
      Task returnValue = heapData[0];
      heapData[0] = heapData[size - 1];
      heapData[size - 1] = null;
      size--;
      percolateDown(0);
      return returnValue;
    }
  }

  protected void percolateDown(int index) {
    // First, find the children using the formula 2n+1 and 2n+2 and make sure they are in bounds
    boolean leftExist = false;
    boolean rightExist = false;
    int leftIndex = ((index * 2) + 1);
    int rightIndex = ((index * 2) + 2);
    if (leftIndex < size) {
      leftExist = true;
    }
    if (rightIndex < size) {
      rightExist = true;
    }
    if (leftExist == false) { // It is a leaf node, do nothing and return
      return;
    }
    if (leftExist && rightExist == false) { // Compare left to parent and swap if needed
      if (heapData[index].compareTo(heapData[leftIndex], priorityCriteria) < 0) {
        // Swap them
        Task temp = heapData[index];
        heapData[index] = heapData[leftIndex];
        heapData[leftIndex] = temp;
        // After swap, call percolateDown again
        percolateDown(leftIndex);
      } else {
        return;
      }
    }
    if (leftExist && rightExist == true) { // Compare to each other, and then compare to parent
      int compareValue = heapData[leftIndex].compareTo(heapData[rightIndex], priorityCriteria);
      if (compareValue >= 0) { // Left index higher priority or equal than right
        if (heapData[index].compareTo(heapData[leftIndex], priorityCriteria) < 0) {
          // Swap them
          Task temp = heapData[index];
          heapData[index] = heapData[leftIndex];
          heapData[leftIndex] = temp;
          // After swap, call percolateDown again
          percolateDown(leftIndex);
        } else {
          return;
        }
      }
      if (compareValue < 0) { // Right index high priority than left
        if (heapData[index].compareTo(heapData[rightIndex], priorityCriteria) < 0) {
          // Swap them
          Task temp = heapData[index];
          heapData[index] = heapData[rightIndex];
          heapData[rightIndex] = temp;
          percolateDown(rightIndex);
        } else {
          return;
        }
      }
    }
  }

  public void reprioritize(CompareCriteria priorityCriteria) {
    this.priorityCriteria = priorityCriteria;
    int i;
    for (i = ((size - 2) / 2 ); i >= 0; --i) {
      percolateDown(i);
    }
  }

  public Task[] getHeapData() {
    return Arrays.copyOf(heapData, heapData.length);
  }
}
