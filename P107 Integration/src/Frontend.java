import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Frontend implements FrontendInterface {
  Scanner reader;
  BackendInterface backend;

  public Frontend(Scanner in, BackendInterface backend) {
    this.reader = in;
    this.backend = backend;
  }


  /**
   * Repeatedly gives the user an opportunity to issue new commands until they select Q to quit.
   * Uses the scanner passed to the constructor to read user input.
   */
  @Override
  public void runCommandLoop() {
    char nextChar;
    do {
      displayMainMenu();
      String input = reader.next();
      nextChar = input.charAt(0);
      // Check user input for a character corresponding to a function, such as Load a file, get
      // song, set filter, display top five, or quit.
      if (nextChar == 'l' || nextChar == 'L') {
        loadFile(); // Load a provided file name from user input request
      }
      if (nextChar == 'g' || nextChar == 'G') {
        getSongs(); // Get song from user input request
      }
      if (nextChar == 'f' || nextChar == 'F') {
        setFilter(); // Set filter from user input request
      }
      if (nextChar == 'd' || nextChar == 'D') {
        displayTopFive(); // Display top five songs from user input request
      }
    } while (nextChar != 'q' && nextChar != 'Q');
    // Once a user input of Q or q is received, immediately exit the program
  }

  /**
   * Displays the menu of command options to the user.  Giving the user the instructions of entering
   * L, G, F, D, or Q (case-insensitive) to load a file, get songs, set filter, display the top
   * five, or quit.
   */
  @Override
  public void displayMainMenu() {
    System.out.println("Please enter one of the following commands (case insensitive):");
    System.out.println("L to Load a File");
    System.out.println("G to get songs");
    System.out.println("F to set filter");
    System.out.println("D to display top five");
    System.out.println("Q to quit");
  }

  /**
   * Provides text-based user interface for prompting the user to select the csv file that they
   * would like to load, provides feedback about whether this is successful vs any errors are
   * encountered. [L]oad Song File
   *
   * When the user enters a valid filename, the file with that name should be loaded. Uses the
   * scanner passed to the constructor to read user input and the backend passed to the constructor
   * to load the file provided by the user. If the backend indicates a problem with finding or
   * reading the file by throwing an IOException, a message is displayed to the user, and they will
   * be asked to enter a new filename.
   */
  @Override
  public void loadFile() {
    boolean readSuccess = false;
    while (!readSuccess) {
      System.out.println("Please enter in the name of a csv file you would like to load");
      try {
        backend.readData(reader.next());
        // If no exception is thrown, the next command will run
        System.out.println("File successfully loaded!");
        readSuccess = true;
      } catch (IOException e) {
        System.out.println("File failed to load or failed to read the file");
      }
    }
  }

  /**
   * Provides text-based user interface and error handling for retrieving a list of song titles that
   * are sorted by Energy.  The user should be given the opportunity to optionally specify a minimum
   * and/or maximum Energy to limit the number of songs displayed to that range. [G]et Songs by
   * Energy
   *
   * When the user enters only two numbers (pressing enter after each), the first of those numbers
   * should be interpreted as the minimum, and the second as the maximum Energy. Uses the scanner
   * passed to the constructor to read user input and the backend passed to the constructor to
   * retrieve the list of sorted songs.
   */
  @Override
  public void getSongs() {
    System.out.println(
        "To get songs, please type in the minimum energy level and hit enter, " + "followed by the maximum energy level");
    boolean validUserInputReceived = false;
    while (!validUserInputReceived) {
      try {
        Integer minimum = reader.nextInt();
        System.out.println("Success!");
        Integer maximum = reader.nextInt();
        System.out.println("Success!");
        System.out.println(backend.getRange(minimum, maximum));
        validUserInputReceived = true;
      } catch (InputMismatchException e) {
        // InputMismatchException when the user tries to enter an input into getRange that is
        // invalid, so tell the user that and request more inputs to get a song
        System.out.println(
            "Wrong input, enter in a integer for the minimum, followed by an " + "integer for the maximum energy levels");
        if (reader.hasNextLine()) {
          reader.nextLine();
          // If the user input is more than one line and was a invalid input, read the next
          // line to clear the scanner
        } else {
          if (!reader.hasNext()) {
            return;
          }
          reader.nextLine();
          // If there is another thing to be read in the scanner, but it is not a whole line,
          // skip to the next line to get the user input
        }
      } catch (NullPointerException e) {
        System.out.println(
            "Null input was given, please enter a minimum and maximum integer for " + "setting energy levels");
        // Since reader was null, do not have to clear it
      }
    }
  }

  /**
   * Provides text-based user interface and error handling for setting a filter threshold.  This and
   * future requests to retrieve songs  will only return the titles of songs that are larger than
   * the user specified Danceability.  The user should also be able to clear any previously
   * specified filters. [F]ilter Songs by Danceability
   *
   * When the user enters only a single number, that number should be used as the new filter
   * threshold. Uses the scanner passed to the constructor to read user input and the backend passed
   * to the constructor to set the filters provided by the user and retrieve songs that maths the
   * filter criteria.
   */
  @Override
  public void setFilter() {
    System.out.println(
        "Please enter a minimum danceability threshhold for your song list, or " + "clear to remove the filter!");
    boolean validUserInputReceived = false; // Runs the loop until a valid input is given
    while (!validUserInputReceived) {
      try {
        String input = reader.next();
        if (input.equalsIgnoreCase("clear")) { // User requests to clear filter
          System.out.println("Success!");
          backend.setFilter(null);
          return;
        }
        backend.setFilter(Integer.parseInt(input));
        // Change the input from string to integer to set the filter properly
        System.out.println("Success!");
        validUserInputReceived = true;
      } catch (NumberFormatException e) {
        // When parseInt fails, request the user for additional input
        System.out.println(
            "Wrong input, please enter a minimum danceability threshhold for your " + "song list, or clear to clear the filter.");
      } catch (NullPointerException e) {
        System.out.println(
            "User input was null, please enter a minimum threshhold for your song " + "list or 'clear' to clear the filter");
      }
      if (reader.hasNextLine()) {
        reader.nextLine();
        // If the user input is more than one line and was a invalid input, read the next
        // line to clear the scanner
      } else {
        if (!reader.hasNext()) {
          return;
        }
        reader.nextLine();
        // If there is another thing to be read in the scanner, but it is not a whole line,
        // skip to the next line to get the user input
      }
    }
  }

  /**
   * Displays the titles of up to five of the most Recent songs within the previously set Energy
   * range and larger than the specified Danceability.  If there are no such songs, then this method
   * should indicate that and recommend that the user change their current range or filter settings.
   * [D]isplay five most Recent
   *
   * The user should not need to enter any input when running this command. Uses the backend passed
   * to the constructor to retrieve the list of up to five songs.
   */
  @Override
  public void displayTopFive() {
    // Make sure the topFive songs is not empty because of filter settings limiting it
    if (backend.fiveMost().isEmpty()) {
      System.out.println(
          "The list is currently empty, please change your current energy range or danceability " +
              "filters to include more songs!");
    } else {
      try {
        System.out.println(backend.fiveMost());
      } catch (Exception e) {
        System.out.println(
            "Failed to provide the fiveMost songs, please change your energy range" + " or dancibility filters for more songs");
      }
    }
  }
}
