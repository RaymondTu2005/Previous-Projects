import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

import static org.junit.Assert.*;

/**
 * This class extends RedBlackTree into a tree that supports iterating over the values it stores in
 * sorted, ascending order.
 */
public class IterableRedBlackTree<T extends Comparable<T>> extends RedBlackTree<T>
    implements IterableSortedCollection<T> {

  Comparable<T> maximumIterator = null; // Maximum value of the iterator
  Comparable<T> minimumIterator = null; // Minimum value of the iterator

  /**
   * Allows setting the start (minimum) value of the iterator. When this method is called, every
   * iterator created after it will use the minimum set by this method until this method is called
   * again to set a new minimum value.
   *
   * @param min the minimum for iterators created for this tree, or null for no minimum
   */
  public void setIteratorMin(Comparable<T> min) {
    this.minimumIterator = min; // Set all future iterators created to have this minimum value
  }

  /**
   * Allows setting the stop (maximum) value of the iterator. When this method is called, every
   * iterator created after it will use the maximum set by this method until this method is called
   * again to set a new maximum value.
   *
   * @param max the maximum for iterators created for this tree, or null for no maximum
   */
  public void setIteratorMax(Comparable<T> max) {
    this.maximumIterator = max; // Set all future iterators created to have this max value
  }

  /**
   * Returns an iterator over the values stored in this tree. The iterator uses the start (minimum)
   * value set by a previous call to setIteratorMin, and the stop (maximum) value set by a previous
   * call to setIteratorMax. If setIteratorMin has not been called before, or if it was called with
   * a null argument, the iterator uses no minimum value and starts with the lowest value that
   * exists in the tree. If setIteratorMax has not been called before, or if it was called with a
   * null argument, the iterator uses no maximum value and finishes with the highest value that
   * exists in the tree.
   */
  public Iterator<T> iterator() {
    RBTIterator<T> newIterator =
        new RBTIterator<>(this.root, this.minimumIterator, this.maximumIterator);
    // Creates a new iterator object and passes the minimum and maximum values to the iterator
    return newIterator;
  }

  /**
   * Nested class for Iterator objects created for this tree and returned by the iterator method.
   * This iterator follows an in-order traversal of the tree and returns the values in sorted,
   * ascending order.
   */
  protected static class RBTIterator<R> implements Iterator<R> {

    // stores the start point (minimum) for the iterator
    Comparable<R> min = null;
    // stores the stop point (maximum) for the iterator
    Comparable<R> max = null;
    // stores the stack that keeps track of the inorder traversal
    Stack<BSTNode<R>> stack = null;


    /**
     * Constructor for a new iterator if the tree with root as its root node, and min as the start
     * (minimum) value (or null if no start value) and max as the stop (maximum) value (or null if
     * no stop value) of the new iterator.
     *
     * @param root root node of the tree to traverse
     * @param min  the minimum value that the iterator will return
     * @param max  the maximum value that the iterator will return
     */
    public RBTIterator(BSTNode<R> root, Comparable<R> min, Comparable<R> max) {
      this.min = min;
      this.max = max;
      stack = new Stack<>();
      buildStackHelper(root);
    }

    /**
     * Helper method for initializing and updating the stack. This method both - finds the next data
     * value stored in the tree (or subtree) that is bigger than or equal to the specified start
     * point (maximum), and - builds up the stack of ancestor nodes that contain values larger than
     * or equal to the start point so that those nodes can be visited in the future.
     *
     * @param node the root node of the subtree to process
     */
    private void buildStackHelper(BSTNode<R> node) {
      if (node == null) {
        return; // This is the base case
      } else if (this.min == null || this.min.compareTo(node.getData()) <= 0) {
        // Node is greater than or equal to the iterator minimum value, so push the value onto
        // the stack recursively and call on the left subtree.
        this.stack.push(node);
        buildStackHelper(node.getLeft());
      } else {
        // The current node is less than the set minimum, so we call it recursively on the right
        // tree (this.min.compareTo(node.getData()) was greater than 0
        buildStackHelper(node.getRight());
      }
    }

    /**
     * Returns true if the iterator has another value to return, and false otherwise.
     */
    public boolean hasNext() {
      // When we pop a node, we check if it has a right subtree. Technically, when we pop the
      // last node, there will be no more nodes on the stack, (Or the min / max does not meet the
      // current conditions).
      if (this.stack.isEmpty()) {
        return false; // The stack should only be empty when all values in the min / max range have
        // been iterated through and popped.
      } else if (this.max == null) {
        return true; // If there is no maximum bound and the stack is not empty, return true
      } else if (this.max.compareTo(this.stack.peek().getData()) >= 0) {
        return true; // If the top value on the stack is still within bounds, there are still
        // values to return (pop in this case)
      } else {
        return false; // If value at top of stack is higher than the maximum, there should be
        // nothing to return (We have already added all the left subtree, everything in right
        // subtree would also exceed the max)
      }
      // When we pop values off the stack, it should always add more (given that the node popped
      // had a right subtree that is still above the minimum, and both the hasNext method will
      // check if it is above the maximum, and the next method will throw an exception if a node
      // that is higher than the maximum on the stack is popped and tried to be returned.)
    }

    /**
     * Returns the next value of the iterator.
     *
     * @throws NoSuchElementException if the iterator has no more values to return
     */
    public R next() {
      if (this.stack.isEmpty()) {
        throw new NoSuchElementException("No more values to return!");
      } else {
        // When this first runs, it should pop the leftmost item, which would be a leaf node or
        // (current) in the in order traversal (left-current-right)
        RBTNode<R> returnNode = (RBTNode<R>) this.stack.pop();
        buildStackHelper(returnNode.getRight());
        // If the node has a right subtree, add it onto the stack using the buildStackHelper
        // method since the one we are iterating through right now is "current" and we need to do
        // the right next (left - right - current);
        if (this.max == null) {
          return returnNode.getData();
        } else if (this.max.compareTo(returnNode.getData()) >= 0) {
          return returnNode.getData();
        } else {
          throw new NoSuchElementException("There are no more values to return");
        }
      }
    }
  }

  /**
   * This method tests the Iterator using a tree that contain duplicate values, making sure that it
   * properly iterates through the values.
   */
  @Test
  public void testDuplicatesWithBounds() {
    IterableRedBlackTree<Integer> testOne = new IterableRedBlackTree<>();
    testOne.insert(5);
    // Insert the root node
    // Manually create 2 black nodes
    RBTNode<Integer> six = new RBTNode<>(6);
    RBTNode<Integer> four = new RBTNode<>(4);
    six.flipColor();
    four.flipColor();
    testOne.root.setRight(six);
    testOne.root.getRight().setUp(testOne.root);
    testOne.root.setLeft(four);
    testOne.root.getLeft().setUp(testOne.root);
    // Manually connect the nodes
    testOne.insert(4);
    // Insert the duplicate and now run the iterator after
    testOne.setIteratorMax(10);
    testOne.setIteratorMin(1);
    Iterator<Integer> iteratorOne = testOne.iterator();
    assertTrue("hasNext should evaluate to True", iteratorOne.hasNext());
    Integer expected = 4;
    assertEquals("Should have returned 4", expected, iteratorOne.next());
    assertTrue("hasNext should evaluate to True", iteratorOne.hasNext());
    assertEquals("Should have returned 4", expected, iteratorOne.next());
    assertTrue("hasNext should evaluate to True", iteratorOne.hasNext());
    expected = 5;
    assertEquals("Should have returned 5", expected, iteratorOne.next());
    assertTrue("hasNext should evaluate to True", iteratorOne.hasNext());
    expected = 6;
    assertEquals("Should have returned 6", expected, iteratorOne.next());
    assertFalse("hasNext should evaluate to false", iteratorOne.hasNext());
    // Above, we are iterating through the tree, which should give us 4,4,5,6 when calling next.
    // Additionally, after 6 has been returned, the hasNext method should return false, and true
    // otherwise (When it hasn't returned 6 yet)
  }

  /**
   * This method tests that the Iterator in IterableRedBlackTree class properly works for different
   * data types like strings in this case, compared to other tests that are using integers.
   */
  @Test
  public void testDifferentDataWithBounds() {
    IterableRedBlackTree<String> testTwo = new IterableRedBlackTree<>();
    testTwo.setIteratorMin("AA");
    testTwo.setIteratorMax("ZZ");
    testTwo.insert("John");
    RBTNode<String> raymond = new RBTNode<>("Raymond");
    RBTNode<String> charlie = new RBTNode<>("Charlie");
    raymond.flipColor();
    charlie.flipColor();
    testTwo.root.setRight(raymond);
    testTwo.root.getRight().setUp(testTwo.root);
    testTwo.root.setLeft(charlie);
    testTwo.root.getLeft().setUp(testTwo.root);
    testTwo.insert("Bob");
    testTwo.insert("Vivian");
    // Now, we are going to test the iterator
    Iterator<String> iteratorTwo = testTwo.iterator();
    assertTrue("hasNext should be true", iteratorTwo.hasNext());
    assertEquals("Expected Bob", "Bob", iteratorTwo.next());
    assertTrue("hasNext should be true", iteratorTwo.hasNext());
    assertEquals("Expected Charlie", "Charlie", iteratorTwo.next());
    assertTrue("hasNext should be true", iteratorTwo.hasNext());
    assertEquals("Expected John", "John", iteratorTwo.next());
    assertTrue("hasNext should be true", iteratorTwo.hasNext());
    assertEquals("Expected Raymond", "Raymond", iteratorTwo.next());
    assertTrue("hasNext should be true", iteratorTwo.hasNext());
    assertEquals("Expected Vivian", "Vivian", iteratorTwo.next());
    assertFalse("hasNext should be false", iteratorTwo.hasNext());
    // Go through the list and iterate through it, testing that next() returns the correct value
    // and that once we have gone through all the possible elements in the iterator, hasNext
    // returns false.
  }

  /**
   * This method tests that having different bounds for the iterator returns the correct values and
   * throws the proper exceptions
   */
  @Test
  public void testDifferentMinMaxBounds() {
    IterableRedBlackTree<Integer> testThree = new IterableRedBlackTree<>();
    testThree.insert(10000);
    // Insert the root node
    // Manually create 2 black nodes
    RBTNode<Integer> six = new RBTNode<>(100000);
    RBTNode<Integer> four = new RBTNode<>(4000);
    six.flipColor();
    four.flipColor();
    testThree.root.setRight(six);
    testThree.root.getRight().setUp(testThree.root);
    testThree.root.setLeft(four);
    testThree.root.getLeft().setUp(testThree.root);
    // Manually connect the nodes
    testThree.insert(2000);
    testThree.insert(5000);
    testThree.insert(70000);
    testThree.insert(200000);
    // Insert extra red nodes
    testThree.setIteratorMin(null);
    testThree.setIteratorMax(10000);
    Iterator<Integer> iteratorThree = testThree.iterator();
    assertTrue("hasNext should return True", iteratorThree.hasNext());
    Integer expected = 2000;
    assertEquals("Should have returned 2000", expected, iteratorThree.next());
    assertTrue("hasNext should return True", iteratorThree.hasNext());
    expected = 4000;
    assertEquals("Should have returned 4000", expected, iteratorThree.next());
    assertTrue("hasNext should return True", iteratorThree.hasNext());
    expected = 5000;
    assertEquals("Should have returned 5000", expected, iteratorThree.next());
    assertTrue("hasNext should return True", iteratorThree.hasNext());
    expected = 10000;
    assertEquals("Should have returned 10000", expected, iteratorThree.next());
    assertFalse("hasNext should return False", iteratorThree.hasNext());
    // Above, we are iterating through the tree, which should give us 2000,4000,5000,10000 when
    // calling next. Additionally, after 10000 has been returned, the hasNext method should
    // return false, and true otherwise (When it hasn't returned 10000 yet).
    // We just tested it with no lower bound above and a set upper bound
    // Now, we are going to test it with no upper bound and a set lower bound
    testThree.setIteratorMin(5000);
    testThree.setIteratorMax(null);
    Iterator<Integer> iteratorFour = testThree.iterator();
    assertTrue("hasNext should return True", iteratorFour.hasNext());
    expected = 5000;
    assertEquals("Should have returned 5000", expected, iteratorFour.next());
    assertTrue("hasNext should return True", iteratorFour.hasNext());
    expected = 10000;
    assertEquals("Should have returned 10000", expected, iteratorFour.next());
    assertTrue("hasNext should return True", iteratorFour.hasNext());
    expected = 70000;
    assertEquals("Should have returned 70000", expected, iteratorFour.next());
    assertTrue("hasNext should return True", iteratorFour.hasNext());
    expected = 100000;
    assertEquals("Should have returned 100000", expected, iteratorFour.next());
    assertTrue("hasNext should return True", iteratorFour.hasNext());
    expected = 200000;
    assertEquals("Should have returned 200000", expected, iteratorFour.next());
    assertFalse("hasNext should return False", iteratorFour.hasNext());
    // Lastly, we test it with no bounds and make sure that when hasNext is false and we call
    // next(), it throws the proper exception
    testThree.setIteratorMin(null);
    testThree.setIteratorMax(null);
    Iterator<Integer> iteratorFive = testThree.iterator();
    assertTrue("hasNext should return True", iteratorFive.hasNext());
    expected = 2000;
    assertEquals("Should have returned 2000", expected, iteratorFive.next());
    assertTrue("hasNext should return True", iteratorFive.hasNext());
    expected = 4000;
    assertEquals("Should have returned 4000", expected, iteratorFive.next());
    assertTrue("hasNext should return True", iteratorFive.hasNext());
    expected = 5000;
    assertEquals("Should have returned 5000", expected, iteratorFive.next());
    assertTrue("hasNext should return True", iteratorFive.hasNext());
    expected = 10000;
    assertEquals("Should have returned 10000", expected, iteratorFive.next());
    assertTrue("hasNext should return True", iteratorFive.hasNext());
    expected = 70000;
    assertEquals("Should have returned 70000", expected, iteratorFive.next());
    assertTrue("hasNext should return True", iteratorFive.hasNext());
    expected = 100000;
    assertEquals("Should have returned 100000", expected, iteratorFive.next());
    assertTrue("hasNext should return True", iteratorFive.hasNext());
    expected = 200000;
    assertEquals("Should have returned 200000", expected, iteratorFive.next());
    assertFalse("hasNext should return False", iteratorFive.hasNext());
    // Now, we are going to call next() when hasNext has returned false, making sure it throws
    // the correct error.
    try {
      iteratorFive.next();
      Assert.fail("Should have thrown an exception");
    } catch (NoSuchElementException e) {
      // This exception should have been thrown
    } catch (Exception e) {
      Assert.fail("Wrong type of exception thrown");
    }
  }
}
