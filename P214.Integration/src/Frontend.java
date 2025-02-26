import java.util.List;
import java.util.NoSuchElementException;

/**
 * Class containing methods for implementing the frontend of an app that retrieves paths and
 * times between locations. Contains methods that return HTML fragments prompting a user for
 * input and HTML fragments with the resulting locations from those inputs, including the
 * shortest path between 2 locations and the locations reachable within a maximum time.
 *
 * @author Alyssa Lam
 */
public class Frontend implements FrontendInterface {

  /**
   * Private field storing the reference to the connected Backend class
   */
  private BackendInterface backend;

  /**
   * Constructor for the frontend, which will store given backend
   * @param backend reference to a backend class that is used for shortest path computations
   */
  public Frontend(BackendInterface backend) {
    this.backend = backend;
  }

  /**
   * Returns an HTML fragment that can be embedded within the body of a
   * larger html page.  This HTML output should include:
   * - a text input field with the id="start", for the start location
   * - a text input field with the id="end", for the destination
   * - a button labelled "Find Shortest Path" to request this computation
   * Ensure that these text fields are clearly labelled, so that the user
   * can understand how to use them.
   * @return an HTML string that contains input controls that the user can
   *         make use of to request a shortest path computation
   */
  @Override
  public String generateShortestPathPromptHTML() {
    // Create a string variable to hold the HTML fragment that will be returned
    String htmlShortestPathPrompt = "";

    // Text input field with the id="start", prompting user for start location with a placeholder
    htmlShortestPathPrompt = "<input id=\"start\" type=\"text\" placeholder=\"Enter Start Location\"></input>\n";

    // Text input field with the id="end", prompting user for the destination with a placeholder
    htmlShortestPathPrompt += "<input id=\"end\" type=\"text\" placeholder=\"Enter End Location\"></input>\n";

    // Button labelled "Find Shortest Path" to request the computation
    htmlShortestPathPrompt += "<input type=\"button\" value=\"Find Shortest Path\"></input>\n";

    // Return the string with the final HTML fragment
    return htmlShortestPathPrompt;
  }

  /**
   * Returns an HTML fragment that can be embedded within the body of a
   * larger html page.  This HTML output should include:
   * - a paragraph (p) that describes the path's start and end locations
   * - an ordered list (ol) of locations along that shortest path
   * - a paragraph (p) that includes the total travel time along this path
   * Or if there is no such path, the HTML returned should instead indicate
   * the kind of problem encountered.
   * @param start is the starting location to find a shortest path from
   * @param end is the destination that this shortest path should end at
   * @return an HTML string that describes the shortest path between these
   *         two locations
   */
  @Override
  public String generateShortestPathResponseHTML(String start, String end) {
    // Create a string variable to hold the HTML fragment that will be returned
    String htmlShortestPathResponse = "";

    // Paragraph that describes the path's start and end locations
    htmlShortestPathResponse = "<p> Path will start at " + start + " and end at " + end + ". </p>\n";

    // Retrieve the ordered list of the locations along the shortest path
    List<String> shortestPath = backend.findLocationsOnShortestPath(start, end);

    // If the list is empty, then state that there is no path or invalid input
    if (shortestPath.isEmpty()) {
      htmlShortestPathResponse += "<p> No paths found. Either start and end locations were invalid or no paths exist between these two locations. </p>\n";
    } else {
      // If given a non-empty list, add an ordered list of the locations to the HTML fragment
      htmlShortestPathResponse += "<p> List of locations on the shortest path: </p>\n";
      htmlShortestPathResponse += "<ol>\n";
      for (String location : shortestPath) {
        htmlShortestPathResponse += "<li> " + location + " </li>\n";
      }
      htmlShortestPathResponse += "</ol>\n";

      // Retrieve the list of travel times between the nodes on the shortest path
      List<Double> shortestPathTimes = backend.findTimesOnShortestPath(start, end);

      // Add the times together to get the total travel time and add to the HTML in a paragraph
      Double totalTime = 0.0;
      for (Double time : shortestPathTimes) {
        totalTime += time;
      }
      htmlShortestPathResponse += "<p> The total travel time along this path is " + totalTime + ". </p>\n";
    }

    // Return the string with the final HTML fragment
    return htmlShortestPathResponse;
  }

  /**
   * Returns an HTML fragment that can be embedded within the body of a
   * larger html page.  This HTML output should include:
   * - a text input field with the id="from", for the start locations
   * - a text input field with the id="time", for the max time limit
   * - a button labelled "Reachable From Within" to submit this request
   * Ensure that these text fields are clearly labelled, so that the user
   * can understand how to use them.
   * @return an HTML string that contains input controls that the user can
   *         make use of to request a ten closest destinations calculation
   */
  @Override
  public String generateReachableFromWithinPromptHTML() {
    // Create a string variable to hold the HTML fragment that will be returned
    String htmlReachablePrompt = "";

    // Text input field with the id="from", prompting user for start location with a placeholder
    htmlReachablePrompt = "<input id=\"from\" type=\"text\" placeholder=\"Enter Start Location\"></input>\n";

    // Text input field with the id="time", prompting user for the max time limit with a placeholder
    htmlReachablePrompt += "<input id=\"time\" type=\"text\" placeholder=\"Enter Maximum Time Limit\">" +
        "</input>\n";

    // Button labelled "Reachable From Within" to request the computation
    htmlReachablePrompt += "<input type=\"button\" value=\"Reachable from Within\"></input>\n";

    // Return the string with the final HTML fragment
    return htmlReachablePrompt;
  }

  /**
   * Returns an HTML fragment that can be embedded within the body of a
   * larger html page.  This HTML output should include:
   * - a paragraph (p) describing the start location and travel time allowed
   * - an unordered list (ul) of destinations that can be reached within
   *        that allowed travel time
   * Or if no such destinations can be found, the HTML returned should
   * instead indicate the kind of problem encountered.
   * @param start is the starting location to search from
   * @param travelTime is the maximum number of seconds away from the start
   *        that will allow a destination to be reported
   * @return an HTML string that describes the closest destinations from the
   *         specified start location.
   */
  @Override
  public String generateReachableFromWithinResponseHTML(String start, double travelTime) {
    // Create a string variable to hold the HTML fragment that will be returned
    String htmlReachableResponse = "";

    // Paragraph describing the start location and travel time allowed
    htmlReachableResponse = "<p> Paths will start at " + start + " and will travel to locations reachable within " +
        travelTime + " seconds. </p>\n";

    // Retrieve the locations reachable in the travel time
    try {
      List<String> reachableLocations = backend.getReachableFromWithin(start, travelTime);

      // If an empty list is returned, then state that there no locations found
      if (reachableLocations.isEmpty()) {
        htmlReachableResponse += "<p> No destinations can be reached within the given travel time. </p>\n";
      } else {
        // If a non-empty list is given, add an unordered list of reachable locations to the HTML
        htmlReachableResponse += "<p> List of reachable locations within the maximum travel time: </p>\n";
        htmlReachableResponse += "<ul>\n";
        for (String location : reachableLocations) {
          htmlReachableResponse += "<li> " + location + " </li>\n";
        }
        htmlReachableResponse += "</ul>\n";
      }
    } catch (NoSuchElementException e) {
      // If a NoSuchElementException is thrown, state that an invalid start location was given
      htmlReachableResponse += "<p> Invalid start location given. No paths could be found. </p>\n";
    }

    // Return the string with the final HTML fragment
    return htmlReachableResponse;
  }
}
