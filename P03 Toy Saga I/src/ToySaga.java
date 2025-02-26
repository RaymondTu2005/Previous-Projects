//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Program for setting up, adding, removing, and modifying a room with toys and furniture
// Course:   CS 300 Spring 2024
//
// Author:   Raymond Tu
// Email:    rktu2@wisc.edu
// Lecturer: Hobbes LeGault
//
//////////////////////// ASSISTANCE/HELP CITATIONS ////////////////////////////
//
// Persons:        None
// Online Sources: https://a8396-37286054.cluster77.canvas-user-content.com/courses/8396~398763/
// files/8396~37286054/course%20files/Uploaded%20Files/2024_spring/p3/doc/ToySaga.html - JavaDocs
// helped me fully understand the implementation of methods in the ToySaga class and their
// intended functionalities
//
///////////////////////////////////////////////////////////////////////////////

import processing.core.PImage;
import java.io.File;
import java.util.ArrayList;

/**
 * This class implements the main user interface of the p03 Toy Saga I program
 */
public class ToySaga {
  private static PImage backgroundImage; // Stores the application's background image
  private static ArrayList<Furniture> furnitureList; // Holds reference to furniture on the screen
  private static ArrayList<Toy> toyList; // Holds reference to Toys on the screen
  private static final String BOX_NAME = "box";  // Constant name of the toy box furniture
  private static final int MAX_TOYS_COUNT = 8; // Maximum number of toys in ToyList

  /**
   * Initializes the ToySaga data fields. This callback method is called once when the program starts.
   *
   */
  public static void setup() {
    backgroundImage = Utility.loadImage("images" + File.separator + "background.png");
    furnitureList = new ArrayList<>();
    toyList = new ArrayList<>();
    furnitureList.add(new Furniture("bed", 520, 270));
    furnitureList.add(new Furniture("rug", 220, 370));
    furnitureList.add(new Furniture("nightstand", 325, 240));
    furnitureList.add(new Furniture(BOX_NAME, 90, 230));
  }

  /**
   * This callback method continuously draws and updates the application display window. It is
   * automatically called directly after setup() and continuously executes until the program is
   * stopped. This method first draws the background image to the center[] of the screen. Updates
   * the contents of the toyList to remove any toy which is over the box furniture, and draws
   * furniture and toy objects.
   *
   */
  public static void draw() {
    Utility.image(backgroundImage, (Utility.width() / 2), (Utility.height() / 2));
    int i;
    for (i = 0; i < furnitureList.size(); ++i) {
      Furniture toAdd = furnitureList.get(i);
     toAdd.draw();
    }
    for (i = 0; i < toyList.size(); ++i) {
      Toy toAdd = toyList.get(i);
        toAdd.draw();
      }
    // CITE: Make sure to remove Toy if over "box" furniture item.
    for (i = 0; i < toyList.size(); ++i) {
      Toy checkOverBox = toyList.get(i);
      Furniture box = getToyBox();
      if (box != null ) {
        if (checkOverBox.isOver(box)) {
          toyList.remove(i);
        }
        }
    }
    }

  /**
   * Returns the Furniture object with a name matching BOX_NAME (exact match: case sensitive
   * comparison) if any is found.
   *
   * @return the box Furniture, or null of no match found.
   */
  public static Furniture getToyBox() {
    int i;
    for (i = 0; i < furnitureList.size(); ++i) {
      Furniture checkToyBox = furnitureList.get(i);
      if (checkToyBox.name() == BOX_NAME) { // Maybe
        return checkToyBox;
      }
    }
    return null; // Returns if no match
  }

  /**
   * Returns the toy which is currently dragging. We assume that there is at most one toy object
   * being dragged at a given time.
   *
   * @return to toy being dragged, or null if no toy is dragging.
   */
  public static Toy getDraggingToy() {
    int i;
    for (i = 0; i < toyList.size(); ++i) {
      Toy checkDragging = toyList.get(i);
      if (checkDragging.isDragging()) {
        return checkDragging;
      }
    }
    return null;
  }

  /**
   * Callback method called once after every time the mouse button is pressed. If no toy is
   * dragging, this method checks whether the mouse is over a toy and start dragging it. If the
   * mouse is over multiple toys, only the toy at the lowest index will start dragging.
   *
   */
  public static void mousePressed() {
    int i;
    for (i = 0; i < toyList.size(); ++i ) {
      Toy checkIfMoving = toyList.get(i);
      if (checkIfMoving.isOver(Utility.mouseX(),Utility.mouseY())) {
        if (getDraggingToy() == null) {
          checkIfMoving.startDragging();
          checkIfMoving.draw();
        }
      }
    }
  }

  /**
   * Callback method called every time the mouse button is released. This method stops dragging any
   * toy stored in the toy list.
   *
   */
  public static void mouseReleased() {
    int i;
    for (i = 0; i < toyList.size(); ++i ) {
      Toy checkIfMoving = toyList.get(i);
      if (checkIfMoving.isDragging()) {
        checkIfMoving.stopDragging();
      }
    }
  }

  /**
   * Callback method called once every time a key is pressed. The key that was pressed is
   * returned by the Utility.key() utility method. The ToySaga.keyPressed() method performs the
   * below actions based on the pressed key: - Pressing 'c' or 'C' adds a new toy car at the mouse
   * position if the MAX TOYS COUNT is not reached. - Pressing 't' or 'T' adds a teddy bear toy at
   * the mouse position if the MAX TOYS COUNT is not reached. - Pressing 'r' or 'R' rotates a toy
   * if the mouse is over it. Only one toy is rotated at once.
   *
   */
  public static void keyPressed() {
    switch (Utility.key()) {
      case 'c':
      case 'C':
        if (toyList.size() < MAX_TOYS_COUNT) {
          Toy car = new Toy("car", Utility.mouseX(), Utility.mouseY());
          toyList.add(car);
        }
      break;
      case 't':
      case 'T':
        if (toyList.size() < MAX_TOYS_COUNT) { // Adds a teddy bear toy at mouse position
          Toy teddyBear = new Toy("teddyBear", Utility.mouseX(), Utility.mouseY());
          toyList.add(teddyBear);
        }
      break;
      case 'r':
      case 'R':
        int i;
        for (i = 0; i < toyList.size(); ++i) {
          Toy isOver = toyList.get(i);
          if (isOver.isOver(Utility.mouseX(), Utility.mouseY())) {
            isOver.rotate();
          }
        }
      break;
    }
  }

  /**
   * Driver method that launches the application by calling Utility.runApplication()
   *
   * @param args - list of input arguments if any
   */
  public static void main(String[] args) {
    Utility.runApplication();
  }
}
