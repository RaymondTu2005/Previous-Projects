import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RedBlackTree<T extends Comparable<T>> extends BSTRotation<T> {
  /**
   * Checks if a new red node in the RedBlackTree causes a red property violation by having a red
   * parent. If this is not the case, the method terminates without making any changes to the tree.
   * If a red property violation is detected, then the method repairs this violation and any
   * additional red property violations that are generated as a result of the applied repair
   * operation.
   *
   * @param newRedNode a newly inserted red node, or a node turned red by previous repair
   */
  protected void ensureRedProperty(RBTNode<T> newRedNode) {
    // NOTE: We do not have to worry about the parent being the root and calling newRedNode.getUp()
    // .getUp() resulting in a null pointer exception because the root should be black, causing
    // the method to terminate instantaneously
    if (((RBTNode<T>) newRedNode.getUp()).isRed()) {
      // There is a red node violation, so we must repair it based on the 3 following cases:
      // Case 1: Red Aunts
      // For this case, we should swap the colors of the parent, aunt, and grandparents. If the
      // grandparent's parent (Great Grandparent) is red as well, this could cause another red
      // violation, so we have to recursively call this method again.
      // Case 2: Black-Line: Black Aunt Violation
      // For this case, we should do a rotation between the parent and grandparent, then swap
      // their colors.
      // Case 3: Black Zig: (Don't always go left or right, have a combination of left & right)
      // In this case, we should rotate the parent and child nodes, then fix using case 2.
      if (newRedNode.getUp().isRightChild()) {
        // The parent is a right child
        if (newRedNode.getUp().getUp().getLeft() != null && newRedNode.getUp().getUp().getLeft()
            .isRed()) {
          // We are in case 1, flip the colors of the parents and grandparent
          newRedNode.getUp().flipColor();
          newRedNode.getUp().getUp().flipColor();
          newRedNode.getUp().getUp().getLeft().flipColor();
          // If the grandparent is the root, we should return. If the grandparent is NOT the
          // root, we should recursively call this method again and work our way up the tree.
          if (newRedNode.getUp().getUp().getData().compareTo(root.getData()) == 0) {
            // The grandparent is the root, so do nothing and return
          } else {
            ensureRedProperty(newRedNode.getUp().getUp());
          }
        } else {
          // We are either in case 2 or 3.
          // If we are in case 3, the child will be a left child
          if (!newRedNode.isRightChild()) {
            // We are in case 3 (Black Zig)
            // Should rotate the parent and child nodes, then we are in case 2
            rotate(newRedNode, newRedNode.getUp());
            newRedNode.flipColor();
            newRedNode.getUp().flipColor();
            rotate(newRedNode, newRedNode.getUp());
            // After this, run case 2
          } else {
            // We are in case 2 (Black Aunt Violation)
            // We should rotate the parent and grandparent, and swap their colors.
            newRedNode.getUp().flipColor(); // Change parent color
            newRedNode.getUp().getUp().flipColor(); // Change grandparent color
            // Now rotate grandparent and parent
            rotate(newRedNode.getUp(), newRedNode.getUp().getUp());
            // After that, all cases should be complete
          }
        }
      } else {
        // The parent is a left child
        if (newRedNode.getUp().getUp().getRight() != null && newRedNode.getUp().getUp().getRight()
            .isRed()) {
          // We are in case 1, flip the colors of the parents and grandparent
          newRedNode.getUp().flipColor();
          newRedNode.getUp().getUp().flipColor();
          newRedNode.getUp().getUp().getRight().flipColor();
          // If the grandparent is the root, we should return. If the grandparent is NOT the
          // root, we should recursively call this method again and work our way up the tree.
          if (newRedNode.getUp().getUp().getData().compareTo(root.getData()) == 0) {
            // The grandparent is the root, so do nothing and return
          } else {
            ensureRedProperty(newRedNode.getUp().getUp());
          }
        } else {
          // We are either in case 2 or 3.
          // If we in case 3, the child will be a right child
          if (newRedNode.isRightChild()) {
            // We are in case 3 (Black Zig)
            // Should rotate the parent and child nodes, then we are in case 2
            rotate(newRedNode, newRedNode.getUp());
            newRedNode.flipColor();
            newRedNode.getUp().flipColor();
            rotate(newRedNode, newRedNode.getUp());
          } else {
            newRedNode.getUp().flipColor(); // Change parent color
            newRedNode.getUp().getUp().flipColor(); // Change grandparent color
            // Now rotate grandparent and parent
            rotate(newRedNode.getUp(), newRedNode.getUp().getUp());
            // After that, all cases should be complete
          }
        }
      }
    }
    // If the parent is not red, the void method terminates.
  }

  @Override
  public void insert(T data) {
    // This class should use the BST class's insert helper to add a new node to the tree
    if (data == null) {
      throw new NullPointerException("Data provided is not sufficient");
    } else if (this.root == null) {
      this.root = new RBTNode<>(data);
      if (((RBTNode<T>) this.root).isRed()) {
        ((RBTNode<T>) this.root).flipColor(); // If the new root is red, flip the color to make
        // sure it is black
      }
    } else {
      RBTNode newNode = new RBTNode<T>(data);
      if (!newNode.isRed()) {
        newNode.flipColor(); // If the new node is black, change the color!
      }
      insertHelper(newNode, this.root);
      ensureRedProperty(newNode);
      // Ensure the root is black after an insertion of a new red node and any potential red
      // property repair operations to ensure a black root node.
      if (((RBTNode<T>) this.root).isRed()) {
        ((RBTNode<T>) this.root).flipColor(); // If the root is red, flip it to make it black,
        // will not cause a black height violation
      }
    }
  }

  /**
   * This method tests the case where when there is a red parent - red child violation, the aunt of
   * the child node is red, checking if the tree properly adjusts itself.
   */
  @Test
  public void testRedAunt() {
    RedBlackTree<Integer> testOne = new RedBlackTree<>();
    testOne.insert(5);
    testOne.insert(4);
    testOne.insert(6);
    // The root should be black and 5, with a left child of 4 and 6 that are both red
    assertFalse("Expected false, was true", ((RBTNode<T>) testOne.root).isRed());
    assertTrue("Expected true, was false", ((RBTNode<T>) testOne.root.getLeft()).isRed());
    assertTrue("Expected true, was false", ((RBTNode<T>) testOne.root.getRight()).isRed());
    testOne.insert(3);
    // After inserting, this should cause a red parent red child violation with a red aunt, so
    // our tree should swap the colors of the parent, aunt, and grandparent together and then
    // afterward turn the root node. 5, 4, 6, should be black, 3 should be red.
    assertFalse("Expected false, was true", ((RBTNode<T>) testOne.root).isRed());
    assertFalse("Expected false, was true", ((RBTNode<T>) testOne.root.getLeft()).isRed());
    assertFalse("Expected false, was true", ((RBTNode<T>) testOne.root.getRight()).isRed());
    assertTrue("Expected true, was false", ((RBTNode<T>) testOne.root.getLeft().getLeft()).isRed());
  }

  /**
   * This test tests case 2, where there is a black aunt and the child is on the same side as the
   * parent to the grandparent.
   */
  @Test
  public void testBlackAuntNoZag() {
    RedBlackTree<Integer> testTwo = new RedBlackTree<>();
    testTwo.insert(5);
    // Insert the root node
    // Manually create 2 black nodes
    RBTNode<Integer> six = new RBTNode<>(6);
    RBTNode<Integer> four = new RBTNode<>(4);
    six.flipColor();
    four.flipColor();
    testTwo.root.setRight(six);
    testTwo.root.getRight().setUp(testTwo.root);
    testTwo.root.setLeft(four);
    testTwo.root.getLeft().setUp(testTwo.root);
    // Manually connect the nodes
    testTwo.insert(3);
    // Insert the red parent with a null aunt (black)
    // Now we will test adding a red node which will cause a case 2 violation.
    testTwo.insert(2); // This should cause a violation
    // The following tree (updated) (level order) should be: 5 (Black) 4 (black) 6 (black)
    // 3 (red) 5 (red)
    String expected = "[ 5(b), 3(b), 6(b), 2(r), 4(r) ]";
    assertEquals("Node colors or order is wrong", expected, testTwo.root.toLevelOrderString());
    // Tests that the nodes are the correct color
    assertFalse("Expected false, was true", ((RBTNode<T>) testTwo.root.getRight()).isRed());
    assertFalse("Expected false, was true", ((RBTNode<T>) testTwo.root).isRed());
    assertFalse("Expected false, was true", ((RBTNode<T>) testTwo.root.getLeft()).isRed());
    assertTrue("Expected true, was false",
        ((RBTNode<T>) testTwo.root.getLeft().getLeft()).isRed());
    assertTrue("Expected true, was false",
        ((RBTNode<T>) testTwo.root.getLeft().getRight()).isRed());
  }

  /**
   * This test tests case 2, where there is a black aunt and the child is not on the same side of
   * the tree as the parent to the grandparent. This test also implements example #3 from the
   * Q103.RBTInsert.
   */
  @Test
  public void testZigZagCase() {
    RedBlackTree<Character> testThree = new RedBlackTree<Character>();
    testThree.insert('J');
    testThree.insert('D');
    // Insert some nodes
    RBTNode<Character> U = new RBTNode<Character>('U');
    RBTNode<Character> B = new RBTNode<Character>('B');
    RBTNode<Character> H = new RBTNode<Character>('H');
    // Create nodes we will manually insert
    U.flipColor();
    B.flipColor();
    H.flipColor();
    testThree.root.setRight(U);
    testThree.root.getRight().setUp(testThree.root);
    testThree.insert('N');
    testThree.root.getLeft().setLeft(B);
    testThree.root.getLeft().getLeft().setUp(testThree.root.getLeft());
    testThree.root.getLeft().setRight(H);
    testThree.root.getLeft().getRight().setUp(testThree.root.getLeft());
    // Above, we are inserting some elements and manually creating the connections to ensure that
    // we have the correct tree before testing the insert function that follows the example problem
    testThree.insert('P');
    assertTrue("Expected true, was false",
        ((RBTNode<T>) testThree.root.getRight().getRight()).isRed());
    assertTrue("Expected true, was false",
        ((RBTNode<T>) testThree.root.getRight().getLeft()).isRed());
    assertTrue("Expected true, was false", ((RBTNode<T>) testThree.root.getLeft()).isRed());
    assertFalse("Expected false, was true", ((RBTNode<T>) testThree.root).isRed());
    assertFalse("Expected false, was true",
        ((RBTNode<T>) testThree.root.getLeft().getLeft()).isRed());
    assertFalse("Expected false, was true",
        ((RBTNode<T>) testThree.root.getLeft().getRight()).isRed());
    assertFalse("Expected false, was true", ((RBTNode<T>) testThree.root.getRight()).isRed());
    // Above, we are checking that the nodes are the correct color
    String expected = "[ J(b), D(r), P(b), B(b), H(b), N(r), U(r) ]";
    assertEquals("Incorrect tree", expected, testThree.root.toLevelOrderString());
    // We are also checking that the nodes are in the correct order, as well as double-checking
    // the color.
  }
}
