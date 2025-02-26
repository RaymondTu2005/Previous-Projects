import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.io.File;
import java.util.Scanner;

public class Backend implements BackendInterface {
  private GraphADT<String, Double> currentGraph;

  /*
   * Implementing classes should support the constructor below.
   * @param graph object to store the backend's graph data
   */
  public Backend(GraphADT<String, Double> graph) {
    if (graph == null) {
      throw new NullPointerException("Graph is null");
    }
    else {
      this.currentGraph = graph;
    }
  }

  /**
   * Loads graph data from a dot file.  If a graph was previously loaded, this method should first
   * delete the contents (nodes and edges) of the existing graph before loading a new one.
   *
   * @param filename the path to a dot file to read graph data from
   * @throws IOException if there was any problem reading from this file
   */

  @Override
  public void loadGraphData(String filename) throws IOException {
    // Check if currentGraph is null.

    // First, check if a graph has been already loaded, deleting the contents if it already
    // exists
      int numberOfNodes = currentGraph.getNodeCount();
      if (numberOfNodes > 0) { // Current graph is not empty, so remove the nodes
        List<String> nodeList = currentGraph.getAllNodes();
        for (int i = nodeList.size() - 1; i >= 0; --i) { // Iterate backwards for easier removing
          currentGraph.removeNode(nodeList.get(i));
        }
      }
      // Now, the graph has been cleared, so we can read from the file and now add to the graph.
      File fileToBeRead = new File(filename);
      // For this method, we are going to use a scanner in order to read the contents of the .dot file
      try {
        Scanner fileReader = new Scanner(fileToBeRead);
        if (fileReader.hasNextLine()) {
          fileReader.nextLine(); // Skip the first line of the .dot file if it is not empty
        }
        // Afterward, we can try reading the contents of the file using the scanner
        while (fileReader.hasNextLine()) {
          String lineBeingReadByScanner = fileReader.nextLine();
          if (fileReader.hasNextLine()) { // Use this to skip the last line
            ArrayList<String> lineContents = readDotFileHelper(lineBeingReadByScanner);
            // Now, split up the values using an array, noting that the first quote contains the
            // initial location, the second quote contains the second location, and the number after
            // the equals sign indicates the walking time (edge weight)
            String firstLocation = lineContents.get(0);
            String secondLocation = lineContents.get(1);
            double travelTime = Double.parseDouble(lineContents.get(2));
            if (!currentGraph.containsNode(firstLocation)) {
              currentGraph.insertNode(firstLocation); // Make sure 1st node isn't a duplicate
            }
            if (!currentGraph.containsNode(secondLocation)) {
              currentGraph.insertNode(secondLocation); // Make sure 2nd node isn't a duplicate
            }
              currentGraph.insertEdge(firstLocation, secondLocation, travelTime);
            }
          }
        fileReader.close(); // Close the scanner after reading all the lines
      } catch (IOException e) {
        throw new IOException("Error reading the file or loading the file called " + filename);
      } catch (NumberFormatException e) {
        throw new IOException("Error reading the file called" + filename);
      }
    }

  /**
   * A private helper method that reads a line and extracts the starting location, ending location,
   * and time to travel and returns the results in an ArrayList<String>
   *
   * @param lineToBeRead the line that should be traversed to extract data
   * @return returnArray - An arrayList of data containing relevant locations and travel time
   */
  private ArrayList<String> readDotFileHelper(String lineToBeRead) {
    ArrayList<String> returnArray = new ArrayList<>();
    ArrayList<Integer> indexOfQuotes = new ArrayList<Integer>(6);
    for (int i = 0; i < lineToBeRead.length(); ++i) {
      if (lineToBeRead.charAt(i) == '"')
        indexOfQuotes.add(i); // Find the first and second quotes that contain location

      if (lineToBeRead.charAt(i) == '=') // Find the index of where time starts
        indexOfQuotes.add(i);

      if (lineToBeRead.charAt(i) == ']') // Find index of where the time ends
        indexOfQuotes.add(i);

    }
    // Array 0-3 will contain quote indexes, 4 contains = index, 5 contains ] index
    returnArray.add(lineToBeRead.substring(indexOfQuotes.get(0) + 1, (indexOfQuotes.get(1))));
    // Above contains the first location
    returnArray.add(lineToBeRead.substring(indexOfQuotes.get(2) + 1, (indexOfQuotes.get(3))));
    // Above contains the second location
    returnArray.add(lineToBeRead.substring(indexOfQuotes.get(4) + 1, (indexOfQuotes.get(5))));
    // Above contains the time in a string form
    return returnArray;
  }

  /**
   * Returns a list of all locations (node data) available in the graph.
   *
   * @return list of all location names
   */
  @Override
  public List<String> getListOfAllLocations() {
    return currentGraph.getAllNodes();
    // Could return null, note that so frontend should check if it is empty.
  }

  /**
   * Return the sequence of locations along the shortest path from startLocation to endLocation, or
   * an empty list if no such path exists.
   *
   * @param startLocation the start location of the path
   * @param endLocation   the end location of the path
   * @return a list with the nodes along the shortest path from startLocation to endLocation, or an
   * empty list if no such path exists
   */
  @Override
  public List<String> findLocationsOnShortestPath(String startLocation, String endLocation) {
    // First, check if the nodes exist in the graph
    List<String> pathToBeReturned = new ArrayList<String>();
    if (currentGraph.containsNode(startLocation) && currentGraph.containsNode(endLocation)) {
      // shortestPathData can throw a exception, so use a try/catch statement
      try {
        pathToBeReturned = currentGraph.shortestPathData(startLocation, endLocation);
        return pathToBeReturned;
      } catch (NoSuchElementException e) {
        // There is path to the two nodes, so we should return an empty list.
        return pathToBeReturned;
      }
    }
    // The graph does not contain the specified nodes, so return it.
    return pathToBeReturned;
  }

  /**
   * Return the walking times in seconds between each two nodes on the shortest path from
   * startLocation to endLocation, or an empty list of no such path exists.
   *
   * @param startLocation the start location of the path
   * @param endLocation   the end location of the path
   * @return a list with the walking times in seconds between two nodes along the shortest path from
   * startLocation to endLocation, or an empty list if no such path exists
   */
  @Override
  public List<Double> findTimesOnShortestPath(String startLocation, String endLocation) {
    List<String> shortestPath;
    List<Double> returnEdgeWeight = new ArrayList<>(); // List of edge weights (Seconds to travel)
    if (currentGraph.containsNode(startLocation) && currentGraph.containsNode(endLocation)) {
      try {
        shortestPath = currentGraph.shortestPathData(startLocation, endLocation);
        for (int i = 0; i < shortestPath.size() - 1; ++i) {
          returnEdgeWeight.add(currentGraph.getEdge(shortestPath.get(i), shortestPath.get(i + 1)));
        }
      } catch (NoSuchElementException e) {
        return returnEdgeWeight; // Return an empty list if no such path exists
      }
    }
    return returnEdgeWeight;
  }

  /**
   * Returns the list of locations that can be reached when starting from the provided
   * startLocation, and travelling a maximum of travelTime seconds.
   *
   * @param startLocation the location to find the reachable locations from
   * @param travelTime    is the maximum number of seconds away the start location that a
   *                      destination must be in order to be returned
   * @return the list of destinations that can be reached from startLocation in travelTime seconds
   * or less
   * @throws NoSuchElementException if startLocation does not exist
   */
  @Override
  public List<String> getReachableFromWithin(String startLocation, double travelTime)
      throws NoSuchElementException {
    // Idea: Get all nodes and compare their paths using the shortestPathData and seeing if it is
    // viable to travel to that node, or even go to it.

    if (currentGraph.containsNode(startLocation)) {
      List<String> returnList = new ArrayList<>();
      List<String> nodeList = currentGraph.getAllNodes();

      if (nodeList.size() == 1) {
        return returnList; // There are no nodes beside the startLocation to travel to
      } else {
        for (int i = 0; i < nodeList.size(); ++i) {
          if (!nodeList.get(i).equals(startLocation)) {
            double pathToNodeCost = 0;
            try {
              pathToNodeCost = currentGraph.shortestPathCost(startLocation, nodeList.get(i));
            } catch (NoSuchElementException e) {
              continue; // If there is no path to the node, which throws an exception passed by
              // shortestPathCost and thrown from computeShortestPath, do not bother checking and
              // adding this node to the list.
            }
            double TimeCheck =
                travelTime - pathToNodeCost;

            if (TimeCheck >= 0) {
              returnList.add(nodeList.get(i));
            } // Else, we do nothing because the path is too long.

          }
        }
      }
      return returnList; // After looping through everything, we return the possible list of
      // places to go
    } else {
      throw new NoSuchElementException("StartLocation is not in the current graph");
    }
  }
}

