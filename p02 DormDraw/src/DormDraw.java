//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: Dorm Drawer to add, remove, move, and rotate furniture to design a dorm room
// Course:   CS 300 Spring 2024
//
// Author:   Raymond Tu
// Email:    rktu2@wisc.edu
// Lecturer: Hobbes LeGault
//
//////////////////////// ASSISTANCE/HELP CITATIONS ////////////////////////////
//
// Persons: None
// Online Sources:
// https://cs300-www.cs.wisc.edu/wp/wp-content/uploads/2024/spring/p2/doc/DormDraw.html - Helped
// provide method descriptions to the functionality of certain methods, return values, and
// parameters
// https://cs300-www.cs.wisc.edu/wp/wp-content/uploads/2024/spring/p2/doc/Symbol.html - Helped
// provide information about the symbols class and its subsequent methods that can be called
// using the symbols object.
//
///////////////////////////////////////////////////////////////////////////////

import processing.core.PImage;
import java.io.File;

/**
 * This class models an application to draw a floor plan for a dorm
 *
 */
public class DormDraw {
  private static PImage backgroundImage; // PImage object that represents the background image
  private static Symbol[] symbols; // non-compact perfect sized array storing dorm symbols added
                                   // to the display window
  /**
   * Main method: runs the application
   *
   * @param args - input arguments if any
   */

  public static void main(String[] args) {
    Utility.runApplication();
  }

  /**
   * Initializes the DormDraw data fields. This callback method is called once when the program starts.
   *
   */
  public static void setup() {
    backgroundImage = Utility.loadImage("images" + File.separator + "background.png");
    symbols = new Symbol[12];
  }

  /**
   * This callback method continuously draws and updates the application display window. It is
   * automatically called directly after setup() and continuously executes until the
   * program is stopped.
   *
   */
  public static void draw() {
    Utility.background(Utility.color(255,255,255)); // Snow Color
    Utility.image(backgroundImage, Utility.width()/2, Utility.height()/2);
    int i;
    for (i = 0; i < symbols.length; ++i) {
      if (symbols[i] != null ) {
        symbols[i].draw();
      }
    }
  }

  /**
   * Adds a new element (toAdd) to the perfect size array symbols. The toAdd Symbol must be added
   * to the first null position in the array. If the array is full, the method does nothing.
   *
   * @param symbols - a non-compact perfect size array storing elements of type Symbol
   * @return toAdd - the symbol to add
   */
  public static void addSymbol(Symbol[] symbols, Symbol toAdd) {
    int i;
    for (i = 0; i < symbols.length; ++i) {
      if (symbols[i] == null) {
        symbols[i] = toAdd;
        break;
      }
    }
  }

  /**
   * Callback method called once every time a key is pressed. The key that was pressed is returned
   * by the Utility.key() utility method.
   *
   */
  public static void keyPressed() {
    int i;
    switch (Utility.key()) {
      case 'b':
      case 'B':
        addSymbol(symbols, new Symbol("bed.png", Utility.mouseX(),Utility.mouseY()));
        break;
      case 'c':
      case 'C':
        addSymbol(symbols, new Symbol("chair.png", Utility.mouseX(),Utility.mouseY()));
        break;
      case 'd':
      case 'D':
        addSymbol(symbols, new Symbol("dresser.png", Utility.mouseX(),Utility.mouseY()));
        break;
      case 'k':
      case 'K':
        addSymbol(symbols, new Symbol("desk.png", Utility.mouseX(),Utility.mouseY()));
        break;
      case 'f':
      case 'F':
        addSymbol(symbols, new Symbol("sofa.png", Utility.mouseX(),Utility.mouseY()));
        break;
      case 'g':
      case 'G':
        addSymbol(symbols, new Symbol("rug.png", Utility.mouseX(),Utility.mouseY()));
        break;
      case 'p':
      case 'P':
        addSymbol(symbols, new Symbol("plant.png", Utility.mouseX(),Utility.mouseY()));
        break;
      case 'r':
      case 'R':
        for (i = 0; i < symbols.length; ++i) {
          if (symbols[i] != null) {
            if (isMouseOver(symbols[i])) {
            symbols[i].rotate();
              break;
            }
          }
        }
        break;
      case 's':
      case 'S':
        Utility.save("dormDraw.png");
        break;
      case Utility.BACKSPACE:
        for (i = 0; i < symbols.length; ++i) {
          if (symbols[i] != null) {
            if (isMouseOver(symbols[i])) {
              symbols[i] = null;
              break;
            }
          }
        }
        break;
    }
  }

  /**
   * Checks if the mouse is over a given symbol.
   *
   * @param symbol - reference to a given dorm symbol
   * @return true if the mouse is over the given symbol object (i.e. over the frame of the
   *         image of the symbol), false otherwise
   */
  public static boolean isMouseOver(Symbol symbol) {
    // This method returns true if the method is over the input symbol
    int symbolXPosition = symbol.x();
    int symbolYPosition = symbol.y();
    int rangeOfSymbolHeight = symbol.height() / 2;
    int rangeOfSymbolWidth = symbol.width() / 2;
    if ((Utility.mouseY()) >= (symbolYPosition - rangeOfSymbolHeight) && (Utility.mouseY() <=
        symbolYPosition + rangeOfSymbolHeight)) {
      if (Utility.mouseX() >= (symbolXPosition - rangeOfSymbolWidth) && (Utility.mouseX() <=
          (symbolXPosition + rangeOfSymbolWidth))) {
        return true;
      }
    }
return false;
  }

  /**
   * Callback method called each time the user presses the mouse
   */
  public static void mousePressed() {
    int i;
    for (i = 0; i < symbols.length; ++i) {
      if (symbols[i] != null) {
        if (isMouseOver(symbols[i])) {
          symbols[i].startDragging();
          break;
        }
      }
    }
  }

  /**
   * Callback method called each time the mouse is released
   */
  public static void mouseReleased() {
      int i;
      for (i = 0; i < symbols.length; ++i) {
        if (symbols[i] != null) {
          symbols[i].stopDragging();
        }
      }
  }
}
