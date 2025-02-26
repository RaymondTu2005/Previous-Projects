/**
 * A generic binary search tree class that contains functions such as clear, insert, size,
 * contains, and isEmpty()
 * @param <T> A generic parameter that is comparable for certain BST functions
 */
public class BinarySearchTree<T extends Comparable<T>> implements SortedCollection<T> {

  protected BSTNode<T> root; // Refers to the root of the current tree

  /**
   * Check whether data is stored in the tree.
   *
   * @param data the value to check for in the collection
   * @return true if the collection contains data one or more times, and false otherwise
   */
  @Override
  public boolean contains(Comparable<T> data) {
    if (data == null || this.root == null) {
      return false;
    } else {
      return containsHelper(root, data);
    }
  }

  /**
   * A helper method for the contains method that recursively checks if the data exists within
   * the binary tree
   * @param currentNode - The current node we are checking to see if its data matches
   * @param data - The value of the node that we are searching for
   * @return true if the data is found, false otherwise
   */
  private boolean containsHelper(BSTNode<T> currentNode, Comparable<T> data) {
    if (data.compareTo(currentNode.getData()) == 0) {
      return true;
    } else if (data.compareTo(currentNode.getData()) < 0) {
      // Go left
      if (currentNode.getLeft() == null) {
        // There is no other value to compare to, so return false
        return false;
      } else {
        return containsHelper(currentNode.getLeft(), data);
      }
    } else {
      // Go right
      if (currentNode.getRight() == null) {
        //There is no other value to compare to, so return false;
        return false;
      } else {
        return containsHelper(currentNode.getRight(), data);
      }
    }
  }


  /**
   * Counts the number of values in the collection, with each duplicate value being counted
   * separately within the value returned.
   *
   * @return the number of values in the collection, including duplicates
   */
  @Override
  public int size() {
    if (root == null) {
      return 0;
    } else {
      return sizeHelper(root);
    }
  }

  /**
   * A helper method that recursively finds the size of the Binary Search Tree
   * @param currentNode
   * @return The size of the subtree and its children if it exists
   */

  private int sizeHelper(BSTNode<T> currentNode) {
    // We are going to go all the way left and count the left most nodes, then the right most
    // nodes, then the current node and recursively work our way back up, like a post order
    // traversal
    if (currentNode.getLeft() == null && currentNode.getRight() == null) {
      // Base Case where there is no children, so return 1 for the size (only the current node)
      return 1;
    } else if (currentNode.getLeft() != null && currentNode.getRight() == null) {
      // Case where there is only a left child
      return sizeHelper(currentNode.getLeft()) + 1;
    } else if (currentNode.getLeft() == null && currentNode.getRight() != null) {
      return sizeHelper(currentNode.getRight()) + 1;
    } else {
      // Both children nodes exist
      return sizeHelper(currentNode.getLeft()) + sizeHelper(currentNode.getRight()) + 1;
    }
  }


  /**
   * Removes all values and duplicates from the collection.
   */
  @Override
  public void clear() {
    this.root.setRight(null);
    this.root.setLeft(null);
    this.root = null;
  }

  /**
   * Checks if the collection is empty.
   *
   * @return true if the collection contains 0 values, false otherwise
   */
  @Override
  public boolean isEmpty() {
    if (root == null) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Inserts a new data value into the sorted collection.
   *
   * @param data the new value being insterted
   * @throws NullPointerException if data argument is null, we do not allow null values to be stored
   *                              within a SortedCollection
   */
  @Override
  public void insert(T data) throws NullPointerException {
    if (data == null) {
      throw new NullPointerException("Null values are not allowed");
    } else if (root == null) {
      root = new BSTNode<>(data);
    } else {
      insertHelper(new BSTNode<>(data), root);
      // A recursive method to insert the new node, given that it is a valid node
    }
  }

  /**
   * Performs the naive binary search tree insert algorithm to recursively insert the provided
   * newNode (which has already been initialized with a data value) into the provided tree/subtree.
   * When the provided subtree is null, this method does nothing.
   */
  protected void insertHelper(BSTNode<T> newNode, BSTNode<T> subtree) {
    if (subtree == null) {
      // Do nothing
    } else {
      if (newNode.getData().compareTo(subtree.getData()) > 0) {
        // The new node is greater than the parent, so it should go to the right
        if (subtree.getRight() == null) {
          // we can insert it into the right child of the subtree
          subtree.setRight(newNode);
          newNode.setUp(subtree);
        } else {
          // The right child is taken, so recursively call it again
          insertHelper(newNode, subtree.getRight());
        }
      } else if (newNode.getData().compareTo(subtree.getData()) <= 0) {
        // The new node we are inserting is less than or equal to the parent, so it should go to
        // the left subtree
        if (subtree.getLeft() == null) {
          // We can insert it into the left child of the subtree
          subtree.setLeft(newNode);
          newNode.setUp(subtree);
        } else {
          // The left child is taken, so we recursively call it again
          insertHelper(newNode, subtree.getLeft());
        }
      }
    }
  }

  /**
   * A main method for running the tester methods and ensuring proper function of the Binary
   * Search Tree Operations
   * @param args unused
   */

  public static void main(String[] args) {
    System.out.println("Expected: True || Actual: " + testAdd());
    System.out.println("Expected: True || Actual: " + testContainsAndClear());
    System.out.println("Expected: True || Actual: " + testSizeAndClear());

  }

  /**
   * A tester method that tests the insert function, ensuring that they are properly added to the
   * Binary Tree in the correct space without violating the BST rules
   * @return true if all tests pass, false otherwise
   */
  public static boolean testAdd() {
    BinarySearchTree<String> treeOne = new BinarySearchTree<>();
    // Try inserting a null data set
    try {
      treeOne.insert(null);
      return false; // Returns false if an exception is NOT thrown
    } catch (NullPointerException e) {
      if (e.getMessage().isBlank()) {
        return false;
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return false; // If any other exception beside a NullPointerException is thrown, return false
    }
    treeOne.insert("Gold");
    treeOne.insert("Carl");
    treeOne.insert("Ray");
    treeOne.insert("Sold");
    treeOne.insert("Pineapple");
    treeOne.insert("Dog");
    treeOne.insert("Apple");
    if (!treeOne.root.toLevelOrderString()
        .equals("[ Gold, Carl, Ray, Apple, Dog, Pineapple, Sold" + " ]"))
      return false;
    // Test Adding things to the left side only
    BinarySearchTree<Integer> treeTwo = new BinarySearchTree<>();
    treeTwo.insert(5);
    treeTwo.insert(4);
    treeTwo.insert(3);
    treeTwo.insert(2);
    treeTwo.insert(1);
    if (!treeTwo.root.toLevelOrderString().equals("[ 5, 4, 3, 2, 1 ]"))
      return false;
    if (!treeTwo.root.getLeft().data.equals(4))
      return false;
    if (!treeTwo.root.getLeft().getLeft().data.equals(3))
      return false;
    if (!treeTwo.root.getLeft().getLeft().getLeft().data.equals(2))
      return false;
    if (!treeTwo.root.getLeft().getLeft().getLeft().getLeft().data.equals(1))
      return false;
    BinarySearchTree<Integer> testThree = new BinarySearchTree<>();
    // Try adding things to the right side only, seeing if it adds in the right order
    testThree.insert(1);
    testThree.insert(5);
    testThree.insert(8);
    testThree.insert(10);
    if (!testThree.root.toLevelOrderString().equals("[ 1, 5, 8, 10 ]"))
      return false;
    if (!testThree.root.data.equals(1))
      return false;
    if (!testThree.root.getRight().data.equals(5))
      return false;
    if (!testThree.root.getRight().getRight().data.equals(8))
      return false;
    if (!testThree.root.getRight().getRight().getRight().data.equals(10))
      return false;
    // Try adding a bunch of different elements in random order, seeing if it adds them into the
    // correct spots with random branches and elements of the same value
    BinarySearchTree<Integer> testFour = new BinarySearchTree<>();
    testFour.insert(50);
    testFour.insert(70);
    testFour.insert(60);
    testFour.insert(65);
    testFour.insert(50);
    testFour.insert(55);
    testFour.insert(40);
    testFour.insert(45);
    testFour.insert(50);
    if (!testFour.root.toLevelOrderString().equals("[ 50, 50, 70, 40, 60, 45, 55, 65, 50 ]"))
      return false;
    return true;
  }

  /**
   * A tester method for the Contains() and Clear() method within the BinarySearchTree Class
   * @return true if all tests pass, false otherwise
   */
  public static boolean testContainsAndClear() {
    BinarySearchTree treeFive = new BinarySearchTree();
    treeFive.insert(50);
    treeFive.insert(60);
    treeFive.insert(55);
    treeFive.insert(70);
    treeFive.insert(40);
    treeFive.insert(45);
    treeFive.insert(30);
    if (!treeFive.contains(60)) return false;
    if (!treeFive.contains(50)) return false;
    if (!treeFive.contains(55)) return false;
    if (!treeFive.contains(40)) return false;
    if (!treeFive.contains(70)) return false;
    if (!treeFive.contains(30)) return false;
    if (!treeFive.contains(45)) return false;
    System.out.println(treeFive.root.toLevelOrderString());

    treeFive.clear();
    // Make sure the root has been removed
    if (treeFive.root != null) { return false; }
    return true;
  }

  public static boolean testSizeAndClear() {
    Integer two = 2;
    Integer three = 3;
    Integer four = 4;
    Integer five = 5;
    BinarySearchTree treeSix = new BinarySearchTree();
    treeSix.size();
    if (treeSix.size() != 0)
      return false;
    treeSix.insert(three);
    treeSix.insert(four);
    treeSix.insert(five);
    treeSix.insert(two);
    treeSix.insert(two); // Test inserting the same element, seeing if it counts it twice
    if (treeSix.size() != 5)
      return false;
    treeSix.clear();
    if (treeSix.size() != 0)
      return false;
    // Try the same thing with strings to make sure it can handle different types
    BinarySearchTree<String> treeSeven = new BinarySearchTree<>();
    treeSeven.insert("John");
    treeSeven.insert("Jane");
    treeSeven.insert("James");
    treeSeven.insert("Gordon");
    treeSeven.insert("Ramsey");
    if (treeSeven.size() != 5) return false;
    treeSeven.clear();
    if (treeSeven.size() != 0) return false;
    return true;
  }
}
