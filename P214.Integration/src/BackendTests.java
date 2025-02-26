import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class BackendTests {
  /**
   * This method is designed to test the functionality of the loadGraphData method in the backend
   * class, ensuring that it correctly reads file data and inserts them into the graph and throws
   * exceptions.
   */
  @Test
  public void roleTest1() {
    Graph_Placeholder testOne = new Graph_Placeholder();
    Backend testBackendInput = new Backend(testOne);
    // First, we are going to check if it properly adds the first node, memorial union, into the
    // graph and make sure that it does not throw any exception
    try {
      testBackendInput.loadGraphData("campus.dot");
      assertTrue("Should contain Memorial Union", testOne.containsNode("Memorial Union"));
    } catch (Exception e) {
      System.out.println(e.getMessage());
      Assert.fail("Should not throw any exception");
    }
    // Next, we are going to test the exception throwing of the method.
    Graph_Placeholder testTwo = new Graph_Placeholder();
    Backend testBackendInputException = new Backend(testTwo);
    // Try reading a file that does not exist in the current directory.
    try {
      testBackendInputException.loadGraphData("fileDoesNotExist.dot");
      Assert.fail("Should have thrown an exception");
    } catch (IOException e) {
      if (e.getMessage().isBlank())
        Assert.fail("Should have an error message");
    } catch (Exception e) {
      System.out.println(e.getMessage());
      Assert.fail("Should have not thrown any other exception");
    }
  }

  /**
   * The purpose of this method is to test the functionality of the getListOfAllLocations and
   * getReachableFromWithin method, noting the limitations of the methods using the
   * Graph_Placeholder class
   */
  @Test
  public void roleTest2() {
    // First, we are going to test the getListOfAllLocations method, checking if it gives us all
    // four locations
    Graph_Placeholder testOne = new Graph_Placeholder();
    Backend testGetAllLocations = new Backend(testOne);
    try {
      testGetAllLocations.loadGraphData("campus.dot");
      List<String> locations = testGetAllLocations.getListOfAllLocations();

      assertTrue("Should contain Union South", locations.contains("Union South"));
      assertTrue("Should contain Computer Sciences and Statistics",
          locations.contains("Computer " + "Sciences and Statistics"));
      assertTrue("Should contain Atmospheric, Oceanic and Space Sciences",
          locations.contains("Atmospheric, Oceanic and Space Sciences"));
      assertTrue("Should contain Memorial Union", locations.contains("Memorial Union"));
    } catch (Exception e) {
      System.out.println(e.getMessage());
      Assert.fail("Should not have thrown any exception");
    }
    // Next, we are going to test the getReachableFromWithin method, which should return
    Graph_Placeholder testTwo = new Graph_Placeholder();
    Backend testGetReachableFromWithin = new Backend(testTwo);
    try {
      testGetReachableFromWithin.loadGraphData("campus.dot");
      List<String> getReachableList =
          testGetReachableFromWithin.getReachableFromWithin("Memorial Union", 50);
      assertTrue("Should have 3 edges", testTwo.getEdgeCount() == 3);
      assertTrue("Should contain Union South", getReachableList.contains("Union South"));
      assertTrue("Should contain Computer Sciences and Statistics",
          getReachableList.contains("Computer Sciences and Statistics"));
      assertTrue("Should contain Atmospheric, Oceanic and Space Sciences",
          getReachableList.contains("Atmospheric, Oceanic and Space Sciences"));

    } catch (Exception e) {
      System.out.println(e.getMessage());
      Assert.fail("Should not have thrown any exception");
    }
    // Next, test a travelTime of 0, which should return 0 variables in the List
    Graph_Placeholder testThree = new Graph_Placeholder();
    Backend testNoTime = new Backend(testThree);
    try {
      testNoTime.loadGraphData("campus.dot");
      List<String> listOfZeroTime = testNoTime.getReachableFromWithin("Union South", 0);
      assertTrue("List should be empty because of 0 travelTime", listOfZeroTime.isEmpty());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      Assert.fail("Should not throw any exception");
    }
    // Next, test the exception throwing of the getReachableFromWithin with a location that does
    // not exist
    Graph_Placeholder testFour = new Graph_Placeholder();
    Backend testGetReachableException = new Backend(testFour);
    try {
      testGetReachableException.loadGraphData("campus.dot");
      testGetReachableException.getReachableFromWithin("RandomLocation", 1000);
      Assert.fail("Should have thrown an exception");
    } catch (NoSuchElementException e) {
      // This should have bene thrown
      if (e.getMessage().isBlank())
        Assert.fail("Empty error message");
    } catch (Exception e) {
      System.out.println(e.getMessage());
      Assert.fail("Should have not thrown any other exception");
    }
  }

  /**
   * This method is designed to check the functionality of the findTimesOnShortestPath and
   * findLocationsOnShortestPath methods in the Backend class, noting the limited functionality
   * given the Graph_Placeholder class
   */
  @Test
  public void roleTest3() {
    // First, test the FindLocationsOnShortestPath
    Graph_Placeholder testOne = new Graph_Placeholder();
    Backend testFindLocationsOnShortestPath = new Backend(testOne);
    try {
      testFindLocationsOnShortestPath.loadGraphData("campus.dot");
      List<String> locationList =
          testFindLocationsOnShortestPath.findLocationsOnShortestPath("Union South",
              "Memorial Union");
      assertTrue("Should start at Union South", locationList.contains("Union South"));
      assertTrue("Should go through Computer Sciences and Statistics",
          locationList.contains("Computer Sciences and Statistics"));
      assertTrue("Should go through Atmospheric, Oceanic and Space Sciences",
          locationList.contains("Atmospheric, Oceanic and Space Sciences"));
      assertTrue("Should end at Memorial Union", locationList.contains("Memorial Union"));
    } catch (Exception e) {
      Assert.fail("Should have not thrown any exception");
    }
    // Next, we test the method on locations that don't exist, checking if it returns an empty list
    Graph_Placeholder testTwo = new Graph_Placeholder();
    Backend testFindLocationsOnShortestPathEmpty = new Backend(testTwo);
    try {
      testFindLocationsOnShortestPathEmpty.loadGraphData("campus.dot");
      List<String> emptyList =
          testFindLocationsOnShortestPathEmpty.findLocationsOnShortestPath("Union South",
              "Campus Village");
      Assert.assertTrue("Should be empty", emptyList.isEmpty());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      Assert.fail("No exception should have been thrown");
    }
    // Lastly, test the findTimesOnShortestPath method in the Backend class
    Graph_Placeholder testThree = new Graph_Placeholder();
    Backend testFindTimes = new Backend(testThree);
    try {
      testFindTimes.loadGraphData("campus.dot");
      List<Double> times = testFindTimes.findTimesOnShortestPath("Union South", "Memorial Union");
      assertFalse("Should have travel times", times.isEmpty());
    } catch (Exception e) {
      Assert.fail("Should not throw any exception");
    }
  }

  /**
   * This method tests the functionality of the FindShortestPath method, making sure the information
   * given to the frontend is passed to the backend, and is then passed back to the frontend and
   * displays the correct output.
   */
  @Test
  public void testIntegrationFindShortestPath() {
    DijkstraGraph<String, Double> testOne = new DijkstraGraph<>();
    BackendInterface backend = new Backend(testOne);
    try {
      backend.loadGraphData("campus.dot");
    } catch (Exception e) {
      System.out.println(e.getMessage());
      Assert.fail("Should have not thrown any exception");
    }
    // We are going to test a location that is listed on the campus.dot file, checking that the
    // frontend correctly gives the information to the backend and outputs it properly.
    Frontend frontend = new Frontend(backend);
    String response = frontend.generateShortestPathResponseHTML("Lot 36 - Observatory Drive Ramp",
        "Russell Laboratories");
    assertTrue("Response should contain the travel time 204.2", response.contains("204.2"));
    assertFalse("Response should not say there is no path", response.contains(
        "No paths found. Either start and end locations were invalid or no paths exist between these two locations."));
    assertTrue("Response should contain the starting location",
        response.contains("Lot 36 - Observatory Drive Ramp"));
    assertTrue("Response should contain the ending location",
        response.contains("Russell Laboratories"));
  }

  /**
   * This method tests the functionality of the ReachableFromWithin, making sure that information
   * passed to the frontend is passed to the backend, and is displayed correctly after and is
   * computed correctly
   */
  @Test
  public void testIntegrationReachableFromWithin() {
    DijkstraGraph<String, Double> testTwo = new DijkstraGraph<>();
    BackendInterface backend = new Backend(testTwo);
    try {
      backend.loadGraphData("campus.dot");
    } catch (Exception e) {
      System.out.println(e.getMessage());
      Assert.fail("Should have not thrown any exception");
    }
    Frontend frontend = new Frontend(backend);
    // Now, test locations reachable from Van Vleck Hall in 110 seconds, which should be two
    // locations, Bascom Hall, and Sterling Hall.
    String response = frontend.generateReachableFromWithinResponseHTML("Van Vleck Hall", 110);
    assertFalse("Response should NOT be empty", response.isEmpty());
    assertFalse("Response should not say there is no places to go",
        response.contains("No destinations can be reached within the given travel time."));
    assertFalse("Response should have not thrown an exception in the backend",
        response.contains("Invalid start location given. No paths could be found."));
    assertTrue("Response should contain Sterling Hall", response.contains("Sterling Hall"));
    assertTrue("Response should contain Bascom Hall", response.contains("Bascom Hall"));
  }

  /**
   * In this method, we are testing the frontend and backend ReachableFromWithin method, making sure
   * that although wrong information is passed to the frontend, it is given to the backend and all
   * exceptions are correctly handled and the proper messages are displayed
   */
  @Test
  public void testIntegrationReachableFromWithinNoPath() {
    DijkstraGraph<String, Double> testThree = new DijkstraGraph<>();
    BackendInterface backend = new Backend(testThree);
    try {
      backend.loadGraphData("campus.dot");
    } catch (Exception e) {
      System.out.println(e.getMessage());
      Assert.fail("Should have not thrown any exception");
    }
    Frontend frontend = new Frontend(backend);
    // We are going to test two scenarios: One when the travel time is too low, and another when
    // the location does not exist in our data
    // First scenario is below:
    String lowTravelTime = frontend.generateReachableFromWithinResponseHTML("Memorial Union", 10);
    assertFalse("Response should NOT be empty", lowTravelTime.isEmpty());
    assertTrue("Should state that there is no destinations to be reached in that time",
        lowTravelTime.contains("No destinations can be reached within the given travel time."));
    assertFalse("Response should have not thrown any exception in the backend",
        lowTravelTime.contains("Invalid start location given. No paths could be found."));

    // Next, we test the other scenario, where the location does not exist, giving it a large
    // travel time because it should still return nothing
    String noLocation = frontend.generateReachableFromWithinResponseHTML("Minnesota", 5000);
    assertFalse("Response should NOT be empty", noLocation.isEmpty());
    assertTrue("It should say invalid start locations given",
        noLocation.contains("Invalid start location given. No paths could be found."));
  }

  @Test
  public void testIntegrationFindShortestPathNoPath() {
    DijkstraGraph<String, Double> testFour = new DijkstraGraph<>();
    BackendInterface backend = new Backend(testFour);
    try {
      backend.loadGraphData("campus.dot");
    } catch (Exception e) {
      System.out.println(e.getMessage());
      Assert.fail("Should have not thrown any exception");
    }
    Frontend frontend = new Frontend(backend);
    // Now, we test a path from one location that does exist, and one location that does not exist
    String noPath = frontend.generateShortestPathResponseHTML("Moore Hall",
        "Bakke Recreational " + "Center"); // The Bakke does not exist on campus.dot
    assertFalse("Response should NOT be empty", noPath.isEmpty());
    assertTrue("Response should state there is no path", noPath.contains(
        "No paths found. Either start and end locations were invalid or no paths exist between these two locations."));
  }
}

