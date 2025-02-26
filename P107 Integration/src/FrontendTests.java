import org.junit.Test;
import java.util.Iterator;
import java.util.Scanner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
public class FrontendTests {
  /**
   * This JUnit tester method is designed to check the functionality of the loadFile method
   */
  @Test
  public void roleTest1() {
    TextUITester inputOne = new TextUITester("L\nsongs.csv\nq\n");
    Frontend testOne =
        new Frontend(new Scanner(System.in), new Backend_Placeholder(new Tree_Placeholder()));
    String expected =
        "Please enter one of the following commands (case insensitive):\nL to " + "Load a File\nG to get songs\nF to set filter\nD to display top five\nQ to quit\n" + "Please enter in the name of a csv file you would like to load\n" + "File successfully " + "loaded!\nPlease enter one of the following commands (case insensitive):\nL to " + "Load a File\nG to get songs\nF to set filter\nD to display top five\nQ to quit\n";
    testOne.runCommandLoop();
    String output = inputOne.checkOutput();
    assertEquals("Outputs are NOT equal", expected, output);
  }

  /**
   * This JUnit tester method is designed to check the functionality of the getSongs method
   */
  @Test
  public void roleTest2() {
    TextUITester inputTwo = new TextUITester("G\n0\n999999\nQ\n");
    Frontend testTwo =
        new Frontend(new Scanner(System.in), new Backend_Placeholder(new Tree_Placeholder()));
    String expected =
        "Please enter one of the following commands (case insensitive):\nL to " + "Load a File\nG to get songs\nF to set filter\nD to display top five\nQ to quit\n" + "To " + "get songs, please type in the minimum energy level and hit enter, " + "followed by the " + "maximum energy level\nSuccess!\nSuccess!\n[BO$$, Cake By The Ocean]\nPlease enter one of " + "the following commands (case insensitive):\nL to " + "Load a File\nG to get songs\nF to set filter\nD to display top five\nQ to quit\n";
    testTwo.runCommandLoop();
    String output = inputTwo.checkOutput();
    assertEquals("Outputs are NOT equal", expected, output);

  }

  /**
   * This JUnit tester method is designed to test the functionality of the setFilter and
   * displayTopFive methods!
   */
  @Test
  public void roleTest3() {
    // This will test the setFilter Method
    TextUITester inputThree = new TextUITester("F\n0\nQ\n");
    Frontend testThree =
        new Frontend(new Scanner(System.in), new Backend_Placeholder(new Tree_Placeholder()));
    String expected =
        "Please enter one of the following commands (case insensitive):\nL to " + "Load a File\nG to get songs\nF to set filter\nD to display top five\nQ to quit\nPlease " + "enter a minimum danceability threshhold for your song list, or clear to remove the " + "filter!\nSuccess!\n" + "Please enter one of the following commands (case insensitive):\nL to Load a File\nG to " + "get songs\nF to set filter\nD to display top five\nQ to quit\n";
    testThree.runCommandLoop();
    String output = inputThree.checkOutput();
    assertEquals("Outputs are NOT equal", expected, output);
    // This will test the display top songs
    TextUITester inputFour = new TextUITester("d\nQ\n");
    Frontend testFour =
        new Frontend(new Scanner(System.in), new Backend_Placeholder(new Tree_Placeholder()));
    String expectedTwo =
        "Please enter one of the following commands (case insensitive):\nL to " + "Load a File\nG to get songs\nF to set filter\nD to display top five\nQ to quit\n[A L I E" + " N S, BO$$, Cake By The Ocean]\nPlease enter one of the following commands (case insensitive):\nL to " + "Load a File\nG to get songs\nF to set filter\nD to display top five\nQ to quit\n";
    testFour.runCommandLoop();
    String outputTwo = inputFour.checkOutput();
    assertEquals("Outputs are NOT equal", expectedTwo, outputTwo);
  }

  /**
   * This method tests the implementation of the loadFile command in the Frontend and readData in
   * the backend.
   */
  @Test
  public void IntegrationTestOne() {
    TextUITester inputIntegrationOne = new TextUITester("L\ntest.csv\nq\n");
    IterableSortedCollection<Song> tree = new IterableRedBlackTree();
    Frontend testIntegrationOne = new Frontend(new Scanner(System.in), new Backend(tree));
    testIntegrationOne.runCommandLoop();
    inputIntegrationOne.checkOutput();
    Iterator<Song> integrationTestOne = tree.iterator();
    assertTrue("Tree should not be empty", integrationTestOne.hasNext());
    // Make sure that the file is read properly and songs are being inserted properly into the tree
    // The first song should be "1+1" since numbers in ASCII are less than letters and the
    // iterator goes from smallest to highest
    String expected = "1+1";
    assertEquals("Expected 1+1 as the first song", expected, integrationTestOne.next().getTitle());
  }

  /**
   * This method checks the functionality of the getSongs method in the Frontend and the
   * functionality of the getRange method in the Backend
   */
  @Test
  public void IntegrationTestTwo() {
    // Test getSongs with an absurdly high energy level and make sure that the getSongs method
    // returns no songs in the array.
    TextUITester inputIntegrationTwo = new TextUITester("L\ntest.csv\ng\n9999\n99999\nq\n");
    IterableSortedCollection<Song> tree = new IterableRedBlackTree();
    Frontend testIntegrationTwo = new Frontend(new Scanner(System.in), new Backend(tree));
    testIntegrationTwo.runCommandLoop();
    String actual = inputIntegrationTwo.checkOutput();
    String expected = "Please enter one of the following commands (case insensitive):\n" +
        "L to Load a File\n" + "G to get songs\n" + "F to set filter\n" + "D to display top five\n"
        + "Q to quit\n" + "Please enter in the name of a csv file you would like to load\n" +
        "File successfully loaded!\n" + "Please enter one of the following commands (case " +
        "insensitive):" +
        "\nL to " + "Load a File\nG" + " to get songs\nF to set filter\nD to display top five\n" +
        "Q to quit\n" + "To get songs, please type in the minimum energy level and hit enter, " +
            "followed by the " + "maximum energy level\nSuccess!\nSuccess!\n[]\nPlease enter one " +
            "of " + "the following commands (case insensitive):\nL to " + "Load a File\nG to get " +
            "songs\nF to set filter\nD to display top five\nQ to quit\n";
    assertEquals("Expected no songs to be returned,", expected, actual);
    // Next, test the same thing but with an energy level that will only give us one song, which
    // in this case is Love the Way You Lie by Eminem using a shortened CSV file for testing
    // simplicity
    TextUITester testThree = new TextUITester("L\ntestTwo.csv\ng\n93\n99999\nq\n");
    IterableSortedCollection<Song> tree1 = new IterableRedBlackTree();
    Frontend testIntegrationTwoPointOne = new Frontend(new Scanner(System.in), new Backend(tree1));
    testIntegrationTwoPointOne.runCommandLoop();
    actual = testThree.checkOutput();
    expected = "Please enter one of the following commands (case insensitive):\n" +
        "L to Load a File\n" + "G to get songs\n" + "F to set filter\n" + "D to display top five\n"
        + "Q to quit\n" + "Please enter in the name of a csv file you would like to load\n" +
        "File successfully loaded!\n" + "Please enter one of the following commands (case " +
        "insensitive):" +
        "\nL to " + "Load a File\nG" + " to get songs\nF to set filter\nD to display top five\n" +
        "Q to quit\n" + "To get songs, please type in the minimum energy level and hit enter, " +
        "followed by the " + "maximum energy level\nSuccess!\nSuccess!\n[Love The Way " +
        "You Lie]\nPlease enter " +
        "one " +
        "of " + "the following commands (case insensitive):\nL to " + "Load a File\nG to get " +
        "songs\nF to set filter\nD to display top five\nQ to quit\n";
    assertEquals("Expected Love The Way You Lie by Eminem", expected, actual);
  }

  /**
   * This method checks the functionality of the setFilter method in the Frontend and the
   * functionality of setFilter in the backend
   */
  @Test
  public void IntegrationTestThree() {
    // First test case is testing a very high dancibility to make sure the backend does not
    // return anything and frontend does not display any songs
    TextUITester inputIntegrationThree = new TextUITester("L\ntest.csv\nF\n90000\ng\n0\n100\nq\n");
    IterableRedBlackTree<Song> tree = new IterableRedBlackTree<>();
    Frontend testIntegrationThree = new Frontend(new Scanner(System.in), new Backend(tree));
    testIntegrationThree.runCommandLoop();
    String expected = "Please enter one of the following commands (case insensitive):\n" +
        "L to Load a File\n" + "G to get songs\n" + "F to set filter\n" + "D to display top five\n"
        + "Q to quit\n" + "Please enter in the name of a csv file you would like to load\n" +
        "File successfully loaded!\n" + "Please enter one of the following commands (case " +
        "insensitive):\nL to " +
        "Load a File\nG to get songs\nF to set filter\nD to display top five\nQ to quit\nPlease " +
        "enter a minimum danceability threshhold for your song list, or clear to remove the " +
        "filter!\nSuccess!\n" + "Please enter one of the following commands (case insensitive):\n" +
        "L to Load a File\nG to " + "get songs\nF to set filter\nD to display top five\nQ to quit\n"
        + "To get songs, please type in the minimum energy level and hit enter, " +
        "followed by the " + "maximum energy level\nSuccess!\nSuccess!\n[]\n" + "Please enter one" +
        " of the" + " following commands (case insensitive):\nL to Load a File\nG to get " +
        "songs\nF to set filter\nD to display top five\nQ to quit\n";
    String actual = inputIntegrationThree.checkOutput();
    assertEquals("Expected no songs", expected, actual);
    // Next, we are going to test it with a smaller CSV file so we can isolate and know which
    // songs will return. In this case, telephone and The Time (Dirty Bit) should return when we
    // call getSongs after running the setFilter method.
    TextUITester inputIntegrationThreeTestTwo = new TextUITester("L\ntestTwo.csv\nF\n81\ng\n0" +
        "\n100" + "\nq\n");
    IterableRedBlackTree<Song> tree1 = new IterableRedBlackTree<>();
    Frontend testIntegrationThreeTwo = new Frontend (new Scanner(System.in), new Backend(tree1));
    testIntegrationThreeTwo.runCommandLoop();
    actual = inputIntegrationThreeTestTwo.checkOutput();
    expected = "Please enter one of the following commands (case insensitive):\n" +
        "L to Load a File\n" + "G to get songs\n" + "F to set filter\n" + "D to display top five\n"
        + "Q to quit\n" + "Please enter in the name of a csv file you would like to load\n" +
        "File successfully loaded!\n" + "Please enter one of the following commands (case " +
        "insensitive):\nL to " +
        "Load a File\nG to get songs\nF to set filter\nD to display top five\nQ to quit\nPlease " +
        "enter a minimum danceability threshhold for your song list, or clear to remove the " +
        "filter!\nSuccess!\n" + "Please enter one of the following commands (case insensitive):\n" +
        "L to Load a File\nG to " + "get songs\nF to set filter\nD to display top five\nQ to quit\n"
        + "To get songs, please type in the minimum energy level and hit enter, " +
        "followed by the " + "maximum energy level\nSuccess!\nSuccess!\n[The Time (Dirty Bit), " +
        "Telephone]\nPlease enter one" + " of the" + " following commands (case insensitive):\nL to" +
        " Load a File\nG to get songs\nF to set filter\nD to display top five\nQ to quit\n";
    assertEquals("Expected Telephone and The Time(Dirty Bit)", expected, actual);
  }

  /**
   * This method checks the functionality of the fiveMost method in the Frontend and the
   * functionality of the displayTopFive method in the Backend
   */
  @Test
  public void IntegrationTestFour() {
    // The first case we are going to test is when there are no songs because the dancibility is
    // so high, so it should tell us that we should adjust our settings to add songs
    TextUITester inputIntegrationFour = new TextUITester("L\ntest.csv\nF\n99999\nD\nq\n");
    IterableRedBlackTree<Song> tree = new IterableRedBlackTree<>();
    Frontend testIntegrationFour = new Frontend(new Scanner(System.in), new Backend(tree));
    testIntegrationFour.runCommandLoop();
    String actual = inputIntegrationFour.checkOutput();
    String expected = "Please enter one of the following commands (case insensitive):\n" +
        "L to Load a File\n" + "G to get songs\n" + "F to set filter\n" + "D to display top five\n"
        + "Q to quit\n" + "Please enter in the name of a csv file you would like to load\n" +
        "File successfully loaded!\n" + "Please enter one of the following commands (case " +
        "insensitive):\nL to Load a File\nG to get songs\nF to set filter\nD to display top " +
        "five\nQ to quit\nPlease " +
        "enter a minimum danceability threshhold for your song list, or clear to remove the " +
        "filter!\nSuccess!\n" + "Please enter one of the following commands (case insensitive):\n" +
        "L to Load a File\nG to get songs\nF to set filter\nD to display top five\nQ to quit\n" +
        "The list is currently empty, please change your current energy range or danceability filters" +
        " to include more songs!\nPlease enter one of the following commands (case insensitive):\n" +
        "L to Load a File\n" + "G to get songs\n" + "F to set filter\n" + "D to display top five\n"
        + "Q to quit\n";
    assertEquals("Expected no songs",expected,actual);
    // Next, we are going to test a case where there are exactly four songs that are going to be
    // shown by displayTopFive. The four songs are going to be OMG (feat. will.i.am), Only Girl
    // (In The World), Telephone, and The Time (Dirty Bit).
    TextUITester inputIntegrationFourPointOne = new TextUITester("L\ntestTwo.csv\nF\n77\nD\nq\n");
    IterableRedBlackTree<Song> tree1 = new IterableRedBlackTree<>();
    Frontend testIntegrationFourPointOne = new Frontend(new Scanner(System.in), new Backend(tree1));
    testIntegrationFourPointOne.runCommandLoop();
    actual = inputIntegrationFourPointOne.checkOutput();
    expected = "Please enter one of the following commands (case insensitive):\n" +
        "L to Load a File\n" + "G to get songs\n" + "F to set filter\n" + "D to display top five\n"
        + "Q to quit\n" + "Please enter in the name of a csv file you would like to load\n" +
        "File successfully loaded!\n" + "Please enter one of the following commands (case " +
        "insensitive):\nL to Load a File\nG to get songs\nF to set filter\nD to display top " +
        "five\nQ to quit\nPlease " +
        "enter a minimum danceability threshhold for your song list, or clear to remove the " +
        "filter!\nSuccess!\n" + "Please enter one of the following commands (case insensitive):\n" +
        "L to Load a File\nG to get songs\nF to set filter\nD to display top five\nQ to quit\n" +
        "[OMG (feat. will.i.am), Only Girl (In The World), Telephone, The Time (Dirty Bit)]\nPlease" +
        " enter one of the following commands (case insensitive):\nL to Load a File\n" + "G to get " +
        "songs\n" + "F to set filter\n" + "D to display top five\nQ to quit\n";
    assertEquals("Expected four songs",expected,actual);
  }
}
