// === CS400 File Header Information ===
// Name: Raymond
// Email: rktu2@wisc.edu email address>
// Group and Team: P2.2915
// Lecturer: Gary Dahl
// Notes to Grader: N/A

import org.junit.Test;
import java.util.PriorityQueue;
import java.util.List;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import org.junit.Assert;
import static org.junit.Assert.*;

/**
 * This class extends the BaseGraph data structure with additional methods for computing the total
 * cost and list of node data along the shortest path connecting a provided starting to ending
 * nodes. This class makes use of Dijkstra's shortest path algorithm.
 */
public class DijkstraGraph<NodeType, EdgeType extends Number> extends BaseGraph<NodeType, EdgeType>
    implements GraphADT<NodeType, EdgeType> {

  /**
   * While searching for the shortest path between two nodes, a SearchNode contains data about one
   * specific path between the start node and another node in the graph. The final node in this path
   * is stored in its node field. The total cost of this path is stored in its cost field. And the
   * predecessor SearchNode within this path is referened by the predecessor field (this field is
   * null within the SearchNode containing the starting node in its node field).
   *
   * SearchNodes are Comparable and are sorted by cost so that the lowest cost SearchNode has the
   * highest priority within a java.util.PriorityQueue.
   */
  protected class SearchNode implements Comparable<SearchNode> {
    public Node node;
    public double cost;
    public SearchNode predecessor;

    public SearchNode(Node node, double cost, SearchNode predecessor) {
      this.node = node;
      this.cost = cost;
      this.predecessor = predecessor;
    }

    public int compareTo(SearchNode other) {
      if (cost > other.cost)
        return +1;
      if (cost < other.cost)
        return -1;
      return 0;
    }
  }

  /**
   * Constructor that sets the map that the graph uses.
   */
  public DijkstraGraph() {
    super(new HashtableMap<>());
  }

  /**
   * This helper method creates a network of SearchNodes while computing the shortest path between
   * the provided start and end locations. The SearchNode that is returned by this method is
   * represents the end of the shortest path that is found: it's cost is the cost of that shortest
   * path, and the nodes linked together through predecessor references represent all of the nodes
   * along that shortest path (ordered from end to start).
   *
   * @param start the data item in the starting node for the path
   * @param end   the data item in the destination node for the path
   * @return SearchNode for the final end node within the shortest path
   * @throws NoSuchElementException when no path from start to end is found or when either start or
   *                                end data do not correspond to a graph node
   */
  protected SearchNode computeShortestPath(NodeType start, NodeType end) {
    // Note that when implementing this, the get and containsKeys method will be useful
    // Make use of PriorityQueue to explore shorter paths before longer paths
    // Make use of MapADT and PlaceholderMap to keep track of nodes you have already visited
    // Use the map as a set by inserting graph nodes as keys and values into the map and use the
    // containsKey method to check if a node is contained in the set.
    // To use the PlaceholderMap in this way, you'll need to create a new instance of it in your
    // computeShortestPath method.

    if (start == null || end == null || !this.nodes.containsKey(start) || !this.nodes.containsKey(end)) {
      throw new NoSuchElementException(
          "The current graph does not contain the specified start " + "and end nodes");
    } else {
      // Continue on, trying to find the shortest path
      PriorityQueue<SearchNode> shortestPathWeight = new PriorityQueue<>();
      Node startingNode = this.nodes.get(start);
      SearchNode path = new SearchNode(startingNode, 0, null);
      double costOfTravel = 0; // Start the travel cost at 0
      HashtableMap<NodeType, Node> trackLocations = new HashtableMap<>();
      boolean foundInGraph = false;
      trackLocations.put(startingNode.data, startingNode); // Put the first node into the map
      // Now that we have initialized everything and put the first node into the map, we can now
      // continuously loop until the SearchNode's data is equal to our end point.
      while (!path.node.data.equals(end)) {
        for (int i = 0; i < path.node.edgesLeaving.size(); ++i) {
          // Iterate through every edge and add them to the priority queue. If an edge already
          // exists to that node, check if the total weight is less, and update it accordingly.
          Edge addingEdge = path.node.edgesLeaving.get(i);
          SearchNode addingNode =
              new SearchNode(addingEdge.successor, addingEdge.data.doubleValue() + costOfTravel,
                  path);
          if (trackLocations.containsKey(addingNode.node.data)) {
            // Do nothing, we have already visited this node and found the cheapest path to it so
            // do not add it to the priorityQueue
          } else {
            for (SearchNode check : shortestPathWeight) {
              if (check.node.data.equals(addingNode.node.data)) {
                // If they have the same path
                foundInGraph = true;
                if (addingNode.compareTo(check) == -1) {
                  // Weight is less, so remove the other path and add our current path
                  shortestPathWeight.remove(check);
                  shortestPathWeight.add(addingNode);
                  break;
                }
              }
            } if (!foundInGraph) {
              shortestPathWeight.add(addingNode);
              // If there is no path after looping through the priority queue, add it.
            }
          }
        }
        // After all the edges have been added, pop the cheapest one.
        path = shortestPathWeight.poll();
        if (path == null) {
          throw new NoSuchElementException("Cannot get to the desired endpoint");
          // We have popped all the SearchNodes and none have lead to the endpoint, so it is
          // impossible to get there
        } else {
          costOfTravel = path.cost;
          trackLocations.put(path.node.data, path.node);
        }
        foundInGraph = false; // So we can use again
      }
      return path;
    }
  }

  /**
   * Returns the list of data values from nodes along the shortest path from the node with the
   * provided start value through the node with the provided end value. This list of data values
   * starts with the start value, ends with the end value, and contains intermediary values in the
   * order they are encountered while traversing this shorteset path. This method uses Dijkstra's
   * shortest path algorithm to find this solution.
   *
   * @param start the data item in the starting node for the path
   * @param end   the data item in the destination node for the path
   * @return list of data item from node along this shortest path
   */
  public List<NodeType> shortestPathData(NodeType start, NodeType end) {
    List<NodeType> returnList = new LinkedList<>(); // Stores the data
    SearchNode lastNode = computeShortestPath(start,end); // Contains endpoint
    while (!lastNode.node.data.equals(start)) {
      returnList.addFirst(lastNode.node.data);
      // The front of the list should be the start, and since we are going from the back to
      // start, add every new node to the start of the list. The first one will be the start and
      // the last one in the LinkedList is the end.
      lastNode = lastNode.predecessor;
    }
    returnList.addFirst(lastNode.node.data);
    return returnList;
  }

  /**
   * Returns the cost of the path (sum over edge weights) of the shortest path freom the node
   * containing the start data to the node containing the end data. This method uses Dijkstra's
   * shortest path algorithm to find this solution.
   *
   * @param start the data item in the starting node for the path
   * @param end   the data item in the destination node for the path
   * @return the cost of the shortest path between these nodes
   */
  public double shortestPathCost(NodeType start, NodeType end) {
    SearchNode lastNode = computeShortestPath(start,end);
    // The lastNode contains the total cost of the trip since we track the total cost of the
    // path, not just the edge the lastNode is connected to. So we can just call its cost function
    return lastNode.cost;
  }

  /**
   * This method properly checks that in a given graph from our in-class example, the path between
   * the starting nodes D and the ending nodes E is correct and that it is indeed the lowest cost
   * path from D to E, and that the path cost is correct in this case.
   */
  @Test
  public void testClassTracePath() {
    // We are going to use the example of Dijkstra's Algorithm Done in class on the 10/29 Lecture
    // (Gary Dahl)
    DijkstraGraph<Character, Integer> inClassTest = new DijkstraGraph();
    inClassTest.insertNode('A');
    inClassTest.insertNode('B');
    inClassTest.insertNode('M');
    inClassTest.insertNode('I');
    inClassTest.insertNode('E');
    inClassTest.insertNode('D');
    inClassTest.insertNode('F');
    inClassTest.insertNode('G');
    inClassTest.insertNode('H');
    inClassTest.insertNode('L');
    inClassTest.insertEdge('A', 'H', 7);
    inClassTest.insertEdge('A', 'B', 1);
    inClassTest.insertEdge('A','M',5);
    inClassTest.insertEdge('B', 'M', 3);
    inClassTest.insertEdge('M', 'E', 3);
    inClassTest.insertEdge('M', 'I', 4);
    inClassTest.insertEdge('M', 'F', 4);
    inClassTest.insertEdge('H', 'B', 6);
    inClassTest.insertEdge('H', 'I', 2);
    inClassTest.insertEdge('H', 'L', 2);
    inClassTest.insertEdge('I', 'D', 1);
    inClassTest.insertEdge('I', 'H', 2);
    inClassTest.insertEdge('D', 'A', 7);
    inClassTest.insertEdge('D', 'F', 4);
    inClassTest.insertEdge('D', 'G', 2);
    inClassTest.insertEdge('F', 'G', 9);
    inClassTest.insertEdge('G', 'A', 4);
    inClassTest.insertEdge('G', 'H', 9);
    inClassTest.insertEdge('G', 'L', 7);
    // After inserting all the nodes and edges, we check the smallest path from D to E, which
    // should be, according to the in-class trace, D -> G -> A -> B -> M -> E.
    // We test this by calling the shortestPathData with start 'D' and end E.
    List<Character> orderOfShortest = inClassTest.shortestPathData('D', 'E');
    assertTrue("Size of the path should be 6, including the starting and ending locations",
        orderOfShortest.size() == 6);
    assertTrue("First location should be 'D'", orderOfShortest.get(0) == 'D');
    assertTrue("Second location should be 'G'", orderOfShortest.get(1) == 'G');
    assertTrue("Third location should be 'A'", orderOfShortest.get(2) == 'A');
    assertTrue("Fourth location should be 'B'", orderOfShortest.get(3) == 'B');
    assertTrue("Fifth location should be 'M'", orderOfShortest.get(4) == 'M');
    assertTrue("Sixth location should be 'E'", orderOfShortest.get(5) == 'E');
    assertTrue("The total cost of the path should be 13",
        inClassTest.shortestPathCost('D', 'E') == 13);
  }

  /**
   * This method tests the same graph as before where the starting node and the ending nodes are
   * different, but still makes sure that it follows the correct path and correctly calcualtes the
   * cost.
   */
  @Test
  public void testClassTraceCost() {
    // Use the same graph as before (in class example, 10/29 Gary Dahl)
    DijkstraGraph<Character, Integer> inClassTest = new DijkstraGraph();
    inClassTest.insertNode('A');
    inClassTest.insertNode('B');
    inClassTest.insertNode('M');
    inClassTest.insertNode('I');
    inClassTest.insertNode('E');
    inClassTest.insertNode('D');
    inClassTest.insertNode('F');
    inClassTest.insertNode('G');
    inClassTest.insertNode('H');
    inClassTest.insertNode('L');
    inClassTest.insertEdge('A', 'H', 7);
    inClassTest.insertEdge('A', 'B', 1);
    inClassTest.insertEdge('A','M',5);
    inClassTest.insertEdge('B', 'M', 3);
    inClassTest.insertEdge('M', 'E', 3);
    inClassTest.insertEdge('M', 'I', 4);
    inClassTest.insertEdge('M', 'F', 4);
    inClassTest.insertEdge('H', 'B', 6);
    inClassTest.insertEdge('H', 'I', 2);
    inClassTest.insertEdge('H', 'L', 2);
    inClassTest.insertEdge('I', 'D', 1);
    inClassTest.insertEdge('I', 'H', 2);
    inClassTest.insertEdge('D', 'A', 7);
    inClassTest.insertEdge('D', 'F', 4);
    inClassTest.insertEdge('D', 'G', 2);
    inClassTest.insertEdge('F', 'G', 9);
    inClassTest.insertEdge('G', 'A', 4);
    inClassTest.insertEdge('G', 'H', 9);
    inClassTest.insertEdge('G', 'L', 7);
    // Use the same graph, but in this case we are going to try to find the shortest path from
    // Node H to F, which should cost 7 and be H -> I -> D -> F
    List<Character> pathTwo = inClassTest.shortestPathData('H', 'F');
    assertTrue("The path length should be 4, including start and end", pathTwo.size() == 4);
    assertTrue("The path should start at H", pathTwo.get(0) == 'H');
    assertTrue("The path go through I", pathTwo.get(1) == 'I');
    assertTrue("The path go through D", pathTwo.get(2) == 'D');
    assertTrue("The path should end at F", pathTwo.get(3) == 'F');
    assertTrue("The path should cost 7", inClassTest.shortestPathCost('H', 'F') == 7);
  }

  /**
   * This method tests that if there is no path from a start node to an end node, the proper
   * exception is thrown with the proper message.
   */
  @Test
  public void testNoPathToNodes() {
    // In this test case, we are going to use the same graph as before. However, we are going to
    // test the case where two nodes, our start and end nodes, are not connected and is
    // impossible to go through (given the current graph we are given)
    DijkstraGraph<Character, Integer> inClassTest = new DijkstraGraph();
    inClassTest.insertNode('A');
    inClassTest.insertNode('B');
    inClassTest.insertNode('M');
    inClassTest.insertNode('I');
    inClassTest.insertNode('E');
    inClassTest.insertNode('D');
    inClassTest.insertNode('F');
    inClassTest.insertNode('G');
    inClassTest.insertNode('H');
    inClassTest.insertNode('L');
    inClassTest.insertEdge('A', 'H', 7);
    inClassTest.insertEdge('A', 'B', 1);
    inClassTest.insertEdge('B', 'M', 3);
    inClassTest.insertEdge('M', 'E', 3);
    inClassTest.insertEdge('M', 'I', 4);
    inClassTest.insertEdge('M', 'F', 4);
    inClassTest.insertEdge('H', 'B', 6);
    inClassTest.insertEdge('H', 'I', 2);
    inClassTest.insertEdge('H', 'L', 2);
    inClassTest.insertEdge('I', 'D', 4);
    inClassTest.insertEdge('I', 'H', 2);
    inClassTest.insertEdge('D', 'A', 7);
    inClassTest.insertEdge('D', 'F', 4);
    inClassTest.insertEdge('D', 'G', 2);
    inClassTest.insertEdge('F', 'G', 9);
    inClassTest.insertEdge('G', 'A', 4);
    inClassTest.insertEdge('G', 'H', 9);
    inClassTest.insertEdge('G', 'L', 7);
    // In this case, we are going to try to go from node E to node H, which should be impossible
    // given that E does not have any edges directed from itself to another node.
    try {
      inClassTest.shortestPathData('E', 'H');
      Assert.fail("An exception should have been thrown by the previous command");
    } catch (NoSuchElementException e) {
      if (e.getMessage().isEmpty())
        Assert.fail("Exception has no message");
    } catch (Exception e) {
      Assert.fail("No other exception should have been thrown");
    }
  }
}
