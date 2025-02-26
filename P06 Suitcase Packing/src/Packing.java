//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    The Packing Class for packing a suitcase in many different ways
// Course:   CS 300 Spring 2024
//
// Author:   Raymond Tu
// Email:    rktu2@wisc.edu
// Lecturer: Hobbes LeGault
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name:    (name of your pair programming partner)
// Partner Email:   (email address of your programming partner)
// Partner Lecturer's Name: (name of your partner's lecturer)
//
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//   ___ Write-up states that pair programming is allowed for this assignment.
//   ___ We have both read and understand the course Pair Programming Policy.
//   ___ We have registered our team prior to the team registration deadline.
//
//////////////////////// ASSISTANCE/HELP CITATIONS ////////////////////////////
//
// Persons:         None
// Online Sources:  ZyBooks Chapter 8 -
// https://learn.zybooks.com/zybook/WISCCOMPSCI300Spring2024/chapter/8/section/2 - Helped me
// understand how to identify base cases and correctly call Recursive Methods
//
//
///////////////////////////////////////////////////////////////////////////////


/**
 * Class used for packing a 2D suitcase with items using various strategies.
 */
public class Packing {
  /**
   * Tries to pack each item in the items list in-order. If an item can fit, it must be packed.
   * Otherwise, it should be skipped. Must be a recursive method.
   *
   * @param suitcase current Suitcase object
   * @return a Suitcase representing the outcome of a strategy in which the items were attempted to
   * be packed in-order.
   */
  public static Suitcase rushedPacking(Suitcase suitcase) {
    // This is our base case, when the number of items that we have to add to the suitcase is zero
    if (suitcase.numItemsUnpacked() == 0) { // CITE: Base Cases - ZyBooks Chapter 8
      return suitcase;
    }
    // We have to check that the next item that we are going to add to the suitcase is going to
    // be able to fit into the suitcase, and if not, then we skip it using a for loop in this case.
    int i;
    int j = -1; // The next item to be packed in the suitcase, or -1 if there is none to pack
    for (i = 0; i < suitcase.numItemsUnpacked(); ++i) {
      if (suitcase.canPackItem(suitcase.getUnpackedItems().get(i))) {
        j = i;
        break; // Exit the loop once we find an item we can input into the Suitcase
      }
    }
    // We want to make sure that if there are no items available to be packed into the suitcase
    // because it is too full or cannot fit, that we return the suitcase
    if (j == -1) {
      return suitcase; // CITE: Base Cases - ZyBooks Chapter 8
    }
    // Since we have not hit our base case and there is an available item to pack, we can now
    // finally pack the suitcase with the next available item
    Suitcase updatedSuitcase = suitcase.packItem(suitcase.getUnpackedItems().get(j));
    // After packing our suitcase, we want to make sure that if there are more items to pack,
    // that we continue to do so, so we recursively call the rushedPacking method again until we
    // hit our base case or there is no possibilities of items that can fit into the suitcase
    // again.
    return rushedPacking(updatedSuitcase);
  }

  /**
   * Packs items by greedily packing the largest item which can currently be packed. Must be a
   * recursive method.
   *
   * @param suitcase current Suitcase object
   * @return a Suitcase representing the outcome of a greedy strategy in which at each point the
   * largest item that can fit is packed.
   */
  public static Suitcase greedyPacking(Suitcase suitcase) {
    // First, we need to check if the base case is met to ensure that we do not have infinite
    // recursion and cause a StackOverflowError
    if (suitcase.numItemsUnpacked() == 0) {
      return suitcase; // CITE: Base Cases - ZyBooks Chapter 8
    }
    // Next, we must check to see if there is even any items that are available to fit in our
    // suitcase and if not, we should just return the suitcase using a for loop.
    int i;
    int j = -1; // The index of the first item found that can fit in the suitcase, used just to
    // check if we can actually fit any items before packing it greedily
    for (i = 0; i < suitcase.numItemsUnpacked(); ++i) {
      if (suitcase.canPackItem(suitcase.getUnpackedItems().get(i))) {
        j = i;
        break; // Exit the loop once we find an item we can input into the Suitcase
      }
    }
    // We want to make sure that if there are no items available to be packed into the suitcase
    // because it is too full or cannot fit, that we return the suitcase. A -1 j value indicates
    // the value was not modified and that we cannot fit anything. Anything else indicates we can
    // add our unpacked items into the suitcase
    if (j == -1) {
      return suitcase; // CITE: Base Cases - ZyBooks Chapter 8
    }
    // Afterward, we want to find the biggest object that we can fit in the suitcase, and
    // then add it to the suitcase using a for loop
    int biggestArea = -1; // We initialize it to a negative value so that the next item that is
    // checked and fits into the suitcase will adjust the biggestArea. We do not have to worry
    // about it being -1 because we already checked to make sure that at least one item can be
    // added, meaning biggestArea will guaranteed be modified
    int indexOfBiggest = -1; // Can set to -1 since we know this will be modified for sure
    for (i = 0; i < suitcase.numItemsUnpacked(); ++i) {
      if (suitcase.canPackItem(suitcase.getUnpackedItems().get(i))) {
        // If we find an item with a bigger area than the item we currently have, save it
        if (biggestArea < (suitcase.getUnpackedItems().get(i).width * suitcase.getUnpackedItems()
            .get(i).height)) {
          biggestArea = (suitcase.getUnpackedItems().get(i).width * suitcase.getUnpackedItems()
              .get(i).height);
          indexOfBiggest = i;
        }
      }
    }
    // Finally, after finding the index of the biggest item we finally pack it into the suitcase
    // and create a new Suitcase copy to recursively call to the greedyPacking case until we hit
    // our base case or there is no more possible room to pack into the suitcase
    Suitcase updatedSuitcase = suitcase.packItem(suitcase.getUnpackedItems().get(indexOfBiggest));
    // After all the recursion cases have returned, we can then finally return the fully packed
    // suitcases
    return greedyPacking(updatedSuitcase);
  }

  /**
   * Finds the optimal packing of items by trying all packing orders. Must be a recursive method.
   *
   * @param suitcase current Suitcase
   * @return a Suitcase representing the optimal outcome.
   */
  public static Suitcase optimalPacking(Suitcase suitcase) {
    // First off, we want to check our base case to make sure that we are not at our base case
    // before we pack the suitcase at all
    if (suitcase.numItemsUnpacked() == 0) {
      return suitcase; // CITE: Base Cases - ZyBooks Chapter 8
    }
    // Next, we want to check if we can even add any item to the suitcase firsthand before we
    // start adding items into it by checking if any of the items can fit into the suitcase using
    // the canPack method
    int i;
    int j = -1; // The index of the first item found that can fit in the suitcase, used just to
    // check if we can actually fit any items before packing it optimally
    for (i = 0; i < suitcase.numItemsUnpacked(); ++i) {
      if (suitcase.canPackItem(suitcase.getUnpackedItems().get(i))) {
        j = i;
        break; // Exit the loop once we find an item we can input into the Suitcase
      }
    }
    // A -1 j value indicates that nothing can fit into the suitcase, so we return it and a
    // non-negative j value indicates that we can indeed fit items into the suitcase and
    // proceed forward
    if (j == -1) {
      return suitcase; // CITE: Base Cases - ZyBooks Chapter 8
    }
    // After checking that we can indeed add items to the suitcase without throwing exceptions,
    // we then proceed with trying to find the most optimal solution to the problem. We are going
    // to use a loop to continuously loop through every possible scenario in order to find the
    // one with the greatest areaPacked.
    int highestAreaPacked = 0; // The highest area packed value found so far, default to 0
    Suitcase bestCombination = suitcase; // This is going to be used to store the current most
    // optimal solution, and if none are able to pack any
    // items we will just return the original suitcase
    Suitcase currentIternation; // This stores the current state of the suitcase so that we can
    // use it for recursion without modifying the original suitcase, allowing us to loop through
    // every possible iteration
    for (i = 0; i < suitcase.getUnpackedItems().size(); ++i) {
      if (suitcase.canPackItem(suitcase.getUnpackedItems().get(i))) {
        // The loop will first check all the combinations here and then if it finds one
        // with the biggest area, it will save it to the bestCombination suitcase item. Then, it
        // will increment itself until it finds the next possible item that could be added first
        // and then goes down that tree of possibilities, searching for a possible better
        // optimization of the Suitcase
        currentIternation = suitcase.packItem(suitcase.getUnpackedItems().get(i));
        currentIternation = optimalPacking(currentIternation);
        // Current iteration will hold one of many combinations of suitcases, which we can then
        // use to check to the current best solution
        if (currentIternation.areaPacked() > highestAreaPacked) {
          highestAreaPacked = currentIternation.areaPacked();
          bestCombination = currentIternation;
        }
      }
      // After this, we are going to loop again to go through every possibility
    }
    return bestCombination; // We are going to return the best combination found out of every
    // scenario tested in this case after the loop has finished.
  }
}
