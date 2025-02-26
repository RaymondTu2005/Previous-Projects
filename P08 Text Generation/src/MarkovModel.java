//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    MarkovModel Text Generator
// Course:   CS 300 Spring 2024
//
// Author:   Raymond Tu
// Email:    rktu2@wisc.edu email address
// Lecturer: Hobbes LeGault
//

//////////////////////// ASSISTANCE/HELP CITATIONS ////////////////////////////
//
// Persons:         Michelle Jensen Piazza - Helped Remind to Update Queue Size
// Online Sources:  None
//
///////////////////////////////////////////////////////////////////////////////

import java.util.HashMap;

/**
 * A class that represents a Markov Model for generating random text based on a sample text. The
 * model uses a sliding window approach to analyze the occurrence of characters following a
 * sequence of characters of length K
 */
public class MarkovModel {
  private HashMap<String, MyStack<Character>> model; // A map of substrings of length windowWidth
  // to stacks containing the observed characters which follow that substring of characters.
  private MyQueue<Character> currentQueue; // The current windowWidth number of characters that
  // the  model will use to predict the next character. Should always be maintained at length
  // windowWidth using methods from MyQueue.
  private int windowWidth; // The number of characters to consider in a substring when generating
  // new text.
  private boolean shuffleStacks; // A boolean value indicating whether to shuffle the stacks
  // during text generation

  /**
   * Constructs a MarkovModel with a specified order. This model will predict the next character in
   * the generated text based on strings of length k.
   *
   * @param k       - the order of the Markov model (length of substrings to consider).
   * @param shuffle - whether this model should shuffle the stacks during the text generation
   *                phase.
   */
  public MarkovModel(int k, boolean shuffle) {
    this.shuffleStacks = shuffle;
    this.windowWidth = k;
    this.model = new HashMap<String, MyStack<Character>>();
  }

  /**
   * Reads in the provided text and builds a model, which maps each k-length substring of the text
   * to a stack containing all the characters that follow that substring in the text.
   *
   * @param text - the text to be processed to build the model.
   */
  public void processText(String text) {
    int i;
    String subStrings;
    char character;
    for (i = 0; i < text.length() - windowWidth; ++i) {
      subStrings = text.substring(i, i + windowWidth);
      character = text.charAt(i + windowWidth);
      // Updates OR creates a new stack for the given substring and adds next char to it
      model.computeIfAbsent(subStrings, k -> new MyStack<>()).push(character);
    }
  }

  /**
   * Initializes the current queue with the first k-letter substring from the text, setting the
   * initial state for text generation.
   *
   * @param text - the text from which to derive the initial queue state.
   */
  public void initializeQueue(String text) {
    currentQueue = new MyQueue<>();
    String firstKLetters = text.substring(0, windowWidth);
    int i;
    for (i = 0; i < windowWidth; ++i) {
      currentQueue.enqueue(firstKLetters.charAt(i));
    }
  }

  /**
   * Generates text of a specified length based on the model.
   *
   * @param length - the desired length of the generated text.
   * @param text   - the text to use for re-seeding the model if necessary.
   * @return the generated text.
   */
  public String generateText(int length, String text) {
    String toAdd = "";
    // Find the currentQueue length and see if it is equal to the length of the text wanted
    toAdd = currentQueue.toString();
    while (toAdd.length() < length) {
      String checkKey = currentQueue.toString();
      // Get the current queue state and see if HashMap has an entry for it
      if (model.containsKey(checkKey)) {
        Character characterToAdd = model.get(checkKey).peek();
        toAdd = toAdd + characterToAdd;
        // If shuffleStacks is true, shuffle the stack corresponding to the current queue state
        // before continuing
        if (shuffleStacks) {
          model.get(checkKey).shuffle();
        }
        currentQueue.enqueue(characterToAdd);
        currentQueue.maintainSize(windowWidth); // CITE: Michelle Jensen, helped remind to
        // maintain size
      } else if (model.containsKey(checkKey) == false || model.get(checkKey) == null) {
        initializeQueue(text);
        toAdd = toAdd + "\n";
      }
    }
    return toAdd;
  }
}
