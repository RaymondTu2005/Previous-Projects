public class BSTRotation<T extends Comparable<T>> extends BinarySearchTree<T> {

  /**
   * Performs the rotation operation on the provided nodes within this tree. When the provided child
   * is a left child of the provided parent, this method will perform a right rotation. When the
   * provided child is a right child of the provided parent, this method will perform a left
   * rotation. When the provided nodes are not related in one of these ways, this method will either
   * throw a NullPointerException: when either reference is null, or otherwise will throw an
   * IllegalArgumentException.
   *
   * @param child  is the node being rotated from child to parent position
   * @param parent is the node being rotated from parent to child position
   * @throws NullPointerException     when either passed argument is null
   * @throws IllegalArgumentException when the provided child and parent nodes are not initially
   *                                  (pre-rotation) related that way
   */
  protected void rotate(BSTNode<T> child, BSTNode<T> parent)
      throws NullPointerException, IllegalArgumentException {
    //
    if (child == null || parent == null) {
      throw new NullPointerException("Child or parent node is null");
    }
    if (parent.getData().compareTo(child.getData()) >= 0) {
      if (parent.getLeft() == null) {
        throw new IllegalArgumentException("Child is not a node of the parent");
      } else if (parent.getLeft().getData().compareTo(child.getData()) != 0) {
        throw new IllegalArgumentException("Child is not a node of the parent");
      }
    } else {
      if (parent.getRight() == null) {
        throw new IllegalArgumentException("Child is not a node of the parent");
      } else if (parent.getRight().getData().compareTo(child.getData()) != 0) {
        throw new IllegalArgumentException("Child is not a child node of the parent");
      }
    }
    // The provided child is actually a child of the parent, so we need to determine the
    // correct rotation (Left or Right)
    if (!child.isRightChild()) {
      // We should perform a right rotation (Child is currently the parents left child)
      // Make sure that we do not lose any references to any children
      // Cases:
      // 1. The child has no children, and the parent has no right children
      if (child.getLeft() == null && child.getRight() == null && parent.getRight() == null) {
        if (parent.getUp() != null) { // Parent is not a root node
          if (parent.getData().compareTo(parent.getUp().getData()) > 0) {
            // Parent is greater than its parent, so set its parent to the child now
            parent.getUp().setRight(child);
          } else {
            parent.getUp().setLeft(child);
          }
          child.setRight(parent);
          child.setUp(parent.getUp());
          parent.setUp(child);
          parent.setLeft(null);
        } else { // Parent is a root node
          root = child;
          child.setUp(null);
          child.setRight(parent);
          parent.setUp(child);
          parent.setLeft(null);
        }
      }
      // 2. The child has only left children, and the parent has no right children
      // In this case, it would become the parents left child
      else if (child.getLeft() != null && child.getRight() == null && parent.getRight() == null) {
        if (parent.getUp() != null) {
          // Parent is not a root node
          if (parent.getData().compareTo(parent.getUp().getData()) > 0) {
            parent.getUp().setRight(child);
          } else {
            parent.getUp().setLeft(child);
          }
          child.setRight(parent);
          child.setUp(parent.getUp());
          parent.setUp(child);
          parent.setLeft(null);
        } else {
          // Parent is a root node
          root = child;
          child.setRight(parent);
          parent.setUp(child);
          parent.setLeft(null);
          child.setUp(null);
        }
      }
      // 3. The child has only right children, and the parent has no right children
      // In this case, the child's right children would become the parents left children
      else if (child.getRight() != null && child.getLeft() == null && parent.getRight() == null) {
        if (parent.getUp() != null) {
          // Parent is not a root node
          if (parent.getData().compareTo(parent.getUp().getData()) > 0) {
            parent.getUp().setRight(child);
          } else {
            parent.getUp().setLeft(child);
          }
          child.setUp(parent.getUp());
          child.getRight().setUp(parent);
          parent.setLeft(child.getRight());
          child.setRight(parent);
          parent.setUp(child);
        } else {
          // Parent is a root node
          root = child;
          child.getUp().setLeft(child.getRight());
          child.setRight(parent);
          child.setUp(null);
          parent.getLeft().setUp(parent);
          parent.setUp(child);
        }
      }
      // 4. The child has both left and right children, and the parent has no right children
      else if (child.getRight() != null && child.getLeft() != null && parent.getRight() == null) {
        if (parent.getUp() != null) {
          // Parent is not a root node
          if (parent.getData().compareTo(parent.getUp().getData()) > 0) {
            parent.getUp().setRight(child);
          } else {
            parent.getUp().setLeft(child);
          }
          child.getRight().setUp(parent);
          parent.setLeft(child.getRight());
          child.setUp(parent.getUp());
          parent.setUp(child);
          child.setRight(parent);
        } else {
          // Parent is a root node
          root = child;
          parent.setUp(child);
          parent.setLeft(child.getRight());
          child.getRight().setUp(parent);
          child.setRight(parent);
          child.setUp(null);
        }
      }
      // 5. The child has both children, and the parent also has a right child
      else if (child.getLeft() != null && child.getRight() != null && parent.getRight() != null) {
        if (parent.getUp() != null) {
          // Parent is not a root node
          if (parent.getData().compareTo(parent.getUp().getData()) > 0) {
            parent.getUp().setRight(child);
          } else {
            parent.getUp().setLeft(child);
          }
          parent.setLeft(child.getRight());
          child.getRight().setUp(parent);
          child.setRight(parent);
          child.setUp(parent.getUp());
          parent.setUp(child);

        } else {
          // Parent is a root node
          root = child;
          parent.setUp(child);
          parent.setLeft(child.getRight());
          child.getRight().setUp(parent);
          child.setRight(parent);
          child.setUp(null);
        }
      }

      // 6. The child has only left children, and the parent has a right child
      else if (child.getLeft() != null && child.getRight() == null && parent.getRight() != null) {
        if (parent.getUp() != null) {
          // Parent is not a root node
          if (parent.getData().compareTo(parent.getUp().getData()) > 0) {
            parent.getUp().setRight(child);
          } else {
            parent.getUp().setLeft(child);
          }
          child.setRight(parent);
          child.setUp(parent.getUp());
          parent.setUp(child);
          parent.setLeft(null);
        } else {
          // Parent is a root node
          root = child;
          parent.setUp(child);
          child.setRight(parent);
          child.setUp(null);
          parent.setLeft(null);

        }
      }

      // 7. The child has only right children, and the parent has a right child
      else if (child.getRight() != null && child.getLeft() == null && parent.getRight() != null) {
        if (parent.getUp() != null) {
          // Parent is not a root node
          if (parent.getData().compareTo(parent.getUp().getData()) > 0) {
            parent.getUp().setRight(child);
          } else {
            parent.getUp().setLeft(child);
          }
          child.getUp().setLeft(child.getRight());
          child.getRight().setUp(parent);
          child.setRight(parent);
          child.setUp(parent.getUp());
          parent.setUp(child);
        } else {
          // Parent is a root node
          root = child;
          child.getUp().setLeft(child.getRight());
          parent.setUp(child);
          parent.getLeft().setUp(parent);
          child.setRight(parent);
          child.setUp(null);
        }
      }
      // 8. The child has no children, and the parent has a right child
      else {
        if (parent.getUp() != null) {
          // Parent is not a root node
          if (parent.getData().compareTo(parent.getUp().getData()) > 0) {
            parent.getUp().setRight(child);
          } else {
            parent.getUp().setLeft(child);
          }
          child.setRight(parent);
          child.setUp(parent.getUp());
          parent.setUp(child);
          parent.setLeft(null);
        } else {
          // Parent is a root node
          root = child;
          parent.setUp(child);
          child.setRight(parent);
          parent.setLeft(null);
          child.setUp(null);
        }
      }
    } else {
      // We should perform a left rotation (Child is currently the parents right child)
      // Cases:
      // 1. The child has no children and the parent has no left child
      if (child.getRight() == null && child.getLeft() == null && parent.getLeft() == null) {
        if (parent.getUp() != null) { // The parent is not a root node
          if (parent.getData().compareTo(parent.getUp().getData()) > 0) {
            parent.getUp().setRight(child);
          } else {
            parent.getUp().setLeft(child);
          }
          child.setLeft(parent);
          child.setUp(parent.getUp());
          parent.setUp(child);
          parent.setRight(null);
        } else { // The parent is a root node
          root = child;
          child.setLeft(parent);
          parent.setUp(child);
          child.setUp(null);
          parent.setRight(null);
        }

      }
      // 2. The child only has left children and the parent has no left child
      else if (child.getRight() == null && child.getLeft() != null && parent.getLeft() == null) {
        if (parent.getUp() != null) { // The parent is not a root node
          if (parent.getData().compareTo(parent.getUp().getData()) > 0) {
            parent.getUp().setRight(child);
          } else {
            parent.getUp().setLeft(child);
          }
          child.getLeft().setUp(parent);
          child.setUp(parent.getUp());
          parent.setUp(child);
          parent.setRight(child.getLeft());
          child.setLeft(parent);
        } else { // The parent is a root node
          root = child;
          child.getLeft().setUp(parent);
          child.setUp(null);
          parent.setUp(child);
          parent.setRight(child.getLeft());
          child.setLeft(parent);
        }
      }
      // 3. The child only has right children and the parent has no left child
      else if (child.getRight() != null && child.getLeft() == null && parent.getLeft() == null) {
        if (parent.getUp() != null) { // The parent is not a root node
          if (parent.getData().compareTo(parent.getUp().getData()) > 0) {
            parent.getUp().setRight(child);
          } else {
            parent.getUp().setLeft(child);
          }
          child.setLeft(parent);
          child.setUp(parent.getUp());
          child.getLeft().setUp(child);
          child.getLeft().setRight(null);
        } else { // The parent is a root node
          root = child;
          child.setLeft(parent);
          child.setUp(null);
          child.getLeft().setUp(child);
          child.getLeft().setRight(null);
        }
      }
      // 4. The child has both children and the parent has no left child
      else if (child.getRight() != null && child.getLeft() != null && parent.getLeft() == null) {
        if (parent.getUp() != null) { // The parent is not a root node
          if (parent.getData().compareTo(parent.getUp().getData()) > 0) {
            parent.getUp().setRight(child);
          } else {
            parent.getUp().setLeft(child);
          }
          child.getLeft().setUp(child.getUp());
          child.setUp(parent.getUp());
          parent.setRight(child.getLeft());
          child.setLeft(parent);
          parent.setUp(child);
        } else { // The parent is a root node
          root = child;
          child.getLeft().setUp(parent);
          parent.setUp(child);
          parent.setRight(child.getLeft());
          child.setLeft(parent);
          child.setUp(null);
        }
      }
      // 5. The child has no children and the parent has a left child
      else if (child.getRight() == null && child.getLeft() == null && parent.getLeft() != null) {
        if (parent.getUp() != null) { // The parent is not a root node
          if (parent.getData().compareTo(parent.getUp().getData()) > 0) {
            parent.getUp().setRight(child);
          } else {
            parent.getUp().setLeft(child);
          }
          child.setLeft(parent);
          child.setUp(parent.getUp());
          parent.setUp(child);
          parent.setRight(null);
        } else { // The parent is a root node
          root = child;
          child.setLeft(parent);
          child.setUp(null);
          parent.setUp(child);
          parent.setRight(null);
        }
      }
      // 6. The child has a left child and the parent has a left child
      else if (child.getRight() == null && child.getLeft() != null && parent.getLeft() != null) {
        if (parent.getUp() != null) { // The parent is not a root node
          if (parent.getData().compareTo(parent.getUp().getData()) > 0) {
            parent.getUp().setRight(child);
          } else {
            parent.getUp().setLeft(child);
          }
          child.getLeft().setUp(parent);
          child.setUp(parent.getUp());
          parent.setUp(child);
          parent.setRight(child.getLeft());
          child.setLeft(parent);
        } else { // The parent is a root node
          root = child;
          child.getLeft().setUp(parent);
          parent.setUp(child);
          parent.setRight(child.getLeft());
          child.setLeft(parent);
          child.setUp(null);
        }
      }
      // 7. The child has a right child and the parent has a left child
      else if (child.getRight() != null && child.getLeft() == null && parent.getLeft() != null) {
        if (parent.getUp() != null) { // The parent is not a root node
          if (parent.getData().compareTo(parent.getUp().getData()) > 0) {
            parent.getUp().setRight(child);
          } else {
            parent.getUp().setLeft(child);
          }
          child.setLeft(parent);
          child.setUp(parent.getUp());
          parent.setUp(child);
          parent.setRight(null);
        } else { // The parent is a root node
          root = child;
          child.setLeft(parent);
          parent.setUp(child);
          parent.setRight(null);
          child.setUp(null);
        }
      }
      // 8. The child has both children and the parent has a left child
      else {
        if (parent.getUp() != null) { // The parent is not a root node
          if (parent.getData().compareTo(parent.getUp().getData()) > 0) {
            parent.getUp().setRight(child);
          } else {
            parent.getUp().setLeft(child);
          }
          child.getLeft().setUp(child.getUp());
          child.setUp(parent.getUp());
          parent.setUp(child);
          parent.setRight(child.getLeft());
          child.setLeft(parent);
        } else { // The parent is a root node
          root = child;
          child.getLeft().setUp(parent);
          parent.setUp(child);
          child.setUp(null);
          parent.setRight(child.getLeft());
          child.setLeft(parent);
        }
      }
    }
  }



  // At a minimum these tests should check for correct behavior under the following circumstances:
  // 1. Performing both left and right rotations.
  // 2. Performing rotations that include the root node, and some that do not.
  // 3. Performing rotations on parent-child pairs of nodes that have between them 0, 1, 2, and 3
  // shared children (that do not include the child being rotated).

  /**
   * A static main method designed to run tester methods and ensure proper function.
   * @param args unused
   */
  public static void main(String[] args) {
    System.out.println("Expected true, Actual: " + test1());
    System.out.println("Expected true, Actual: " + test2());
    System.out.println("Expected true, Actual: " + test3());
  }
  /**
   * Tests left and right rotations
   *
   * @return true if passed all tests, false otherwise
   */
  public static boolean test1() {
    // Test a left rotation
    BSTRotation<Integer> testOne = new BSTRotation<>();
    testOne.insert(2);
    testOne.insert(1);
    testOne.insert(3);
    testOne.rotate(testOne.root.getRight(), testOne.root);
    if (!testOne.root.toLevelOrderString().equals("[ 3, 2, 1 ]"))
      return false;
    if (!testOne.root.getData().equals(3))
      return false;
    // Test a right rotation
    BSTRotation<Integer> testTwo = new BSTRotation<>();
    testTwo.insert(2);
    testTwo.insert(1);
    testTwo.insert(3);
    testTwo.rotate(testTwo.root.getLeft(), testTwo.root);
    if (!testTwo.root.toLevelOrderString().equals("[ 1, 2, 3 ]"))
      return false;
    if (!testTwo.root.getData().equals(1))
      return false;
    // Test the exceptions
    BSTRotation<Integer> testThree = new BSTRotation<>();
    testThree.insert(2);
    testThree.insert(1);
    testThree.insert(3);
    try {
      testThree.rotate(testThree.root, null);
      return false; // Should have thrown exception
    } catch (NullPointerException e) {
      if (e.getMessage().isBlank())
        return false;
    } catch (Exception e) {
      return false; // Should have not thrown any other exception
    }
    BSTRotation<Integer> testFour = new BSTRotation<>();
    testFour.insert(3);
    testFour.insert(2);
    testFour.insert(1);
    try {
      testFour.rotate(testFour.root.getLeft().getLeft(), testFour.root);
      return false;
    } catch (IllegalArgumentException e) {
      if (e.getMessage().isBlank())
        return false;
    } catch (Exception e) {
      return false; // Should not throw any other exception
    }
    return true;
  }

  /**
   * Performing test rotations that are NOT root nodes
   * @return true if all pass, false otherwise
   */
  public static boolean test2() {
    // A left rotation
    BSTRotation<Integer> testOne = new BSTRotation<>();
    testOne.insert(50);
    testOne.insert(40);
    testOne.insert(30);
    testOne.insert(45);
    testOne.insert(60);
    testOne.insert(55);
    testOne.insert(70);
    testOne.rotate(testOne.root.getRight().getRight(), testOne.root.getRight());
    if (!testOne.root.getData().equals(50)) return false;
    if (!testOne.root.toLevelOrderString().equals("[ 50, 40, 70, 30, 45, 60, 55 ]")) return false;
    // A right rotation
    BSTRotation<Integer> testTwo = new BSTRotation<>();
    testTwo.insert(50);
    testTwo.insert(40);
    testTwo.insert(30);
    testTwo.insert(45);
    testTwo.insert(60);
    testTwo.insert(55);
    testTwo.insert(70);
    testTwo.rotate(testTwo.root.getRight().getLeft(),testTwo.root.getRight());
    if (!testTwo.root.toLevelOrderString().equals("[ 50, 40, 55, 30, 45, 60, 70 ]")) return false;
    if (!testTwo.root.getData().equals(50)) { return false; }


    return true;
  }

  /**
   * Tests rotations with a different number of shared children
   * @return true if all tests pass, false otherwise
   */
  public static boolean test3() {
    // A rotation with 0 shared children
    BSTRotation<Integer> testOne = new BSTRotation<>();
    testOne.insert(50);
    testOne.insert(40);
    testOne.insert(30);
    testOne.insert(45);
    testOne.insert(60);
    testOne.insert(55);
    testOne.rotate(testOne.root.getRight().getLeft(),testOne.root.getRight());
    if (!testOne.root.toLevelOrderString().equals("[ 50, 40, 55, 30, 45, 60 ]")) return false;
    if (!testOne.root.getData().equals(50)) { return false; }
    // One shared child
    BSTRotation<Integer> testTwo = new BSTRotation<>();
    testTwo.insert(50);
    testTwo.insert(40);
    testTwo.insert(30);
    testTwo.rotate(testTwo.root.getLeft(),testTwo.root);
    if (!testTwo.root.toLevelOrderString().equals("[ 40, 30, 50 ]")) return false;
    if (!testTwo.root.getData().equals(40)) return false;
    // Two shared children
    BSTRotation<Integer> testThree = new BSTRotation<>();
    testThree.insert(50);
    testThree.insert(40);
    testThree.insert(30);
    testThree.insert(45);
    testThree.rotate(testThree.root.getLeft(), testThree.root);
    if (!testThree.root.toLevelOrderString().equals("[ 40, 30, 50, 45 ]")) return false;
    if (!testThree.root.getData().equals(40)) return false;
    // Three Shared children
    BSTRotation<Integer> testFour = new BSTRotation<>();
    testFour.insert(50);
    testFour.insert(40);
    testFour.insert(30);
    testFour.insert(45);
    testFour.insert(60);
    testFour.rotate(testFour.root.getLeft(),testFour.root);
    if (!testFour.root.toLevelOrderString().equals("[ 40, 30, 50, 45, 60 ]")) return false;
    if (!testFour.root.getData().equals(40)) return false;

    BSTRotation<String> testFive = new BSTRotation<String>();
    testFive.insert("o");
    testFive.insert("h");
    testFive.insert("v");
    testFive.insert("e");
    testFive.insert("j");
    testFive.insert("s");
    testFive.insert("x");
    testFive.insert("i");
    testFive.insert("k");
    testFive.insert("n");
    testFive.rotate(testFive.root.getLeft(), testFive.root);
    return true;
  }
}
