// == CS400 Fall 2024 File Header Information ==
// Name: Raymond Tu
// Email: rktu2@wisc.edu
// Group: P2.2915
// Lecturer: Gary Dahl
// Notes to Grader: N/A

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class HashtableMap<KeyType, ValueType> implements MapADT<KeyType, ValueType> {
  protected class Pair {

    public KeyType key;
    public ValueType value;

    public Pair(KeyType key, ValueType value) {
      this.key = key;
      this.value = value;
    }

  }


  protected LinkedList<Pair>[] table = null;
  // To handle collisions, use chaining with LinkedLists. Above contains the key/value pairs
  // stored in an Array.

  /**
   * A constructor that allows the user to initialize the capacity of the Array containing
   * LinkedLists to whatever they would like it to be.
   *
   * @param capacity the initial capacity of the array of LinkedLists
   */
  @SuppressWarnings("unchecked")
  public HashtableMap(int capacity) {
    LinkedList[] newTable = new LinkedList[capacity];
    this.table = (LinkedList<Pair>[]) newTable;
  }

  /**
   * The default constructor for HashtableMap, where the capacity is initialized to 64.
   */
  @SuppressWarnings("unchecked")
  public HashtableMap() {
    LinkedList[] newTable = new LinkedList[64];
    this.table = (LinkedList<Pair>[]) newTable;
  }

  /**
   * Adds a new key,value pair/mapping to this collection.
   *
   * @param key   the key of the key,value pair
   * @param value the value that key maps to
   * @throws IllegalArgumentException if key already maps to a value
   * @throws NullPointerException     if key is null
   */
  @Override
  public void put(KeyType key, ValueType value) throws IllegalArgumentException {
    if (key == null) {
      throw new NullPointerException("Key is a null value");
    } else if (containsKey(key)) {
      throw new IllegalArgumentException("Key is already used to map to another value");
    } else {
      // We can add it to the hashmap. Note that if capacity >= 80%, double and rehash all
      // elements AFTER adding.
      Pair toAddPair = new Pair(key, value);
      int indexToAdd = Math.abs(key.hashCode()) % this.table.length;
      if (table[indexToAdd] == null) {
        LinkedList<Pair> toAdd = new LinkedList<>();
        toAdd.add(toAddPair);
        table[indexToAdd] = toAdd;
      } else {
        LinkedList<Pair> existingList = table[indexToAdd];
        existingList.add(toAddPair);
      }
    }
    // Check if we need to resize after adding
    if (getSize() >= getCapacity() * 0.8) {
      // We should resize everything by doubling the capacity of the array
      @SuppressWarnings("unchecked")
      LinkedList<Pair>[] newResizedTable = new LinkedList[this.table.length * 2];
      for (int i = 0; i < this.table.length; ++i) {
        if (this.table[i] == null) { // Nothing inside this index, don't have to rehash since
        } else {
          LinkedList<Pair> rehashList = this.table[i];
          for (Pair rehashPair : rehashList) {
            int indexToAdd = Math.abs(rehashPair.key.hashCode()) % newResizedTable.length;
            if (newResizedTable[indexToAdd] == null) {
              LinkedList<Pair> newHead = new LinkedList<>();
              newHead.add(rehashPair);
              newResizedTable[indexToAdd] = newHead;
            } else {
              newResizedTable[indexToAdd].add(rehashPair);
            }
          }
        }
      }
      this.table = (LinkedList<Pair>[]) newResizedTable;
    }
  }

  /**
   * Checks whether a key maps to a value in this collection.
   *
   * @param key the key to check
   * @return true if the key maps to a value, and false is the key doesn't map to a value
   */
  @Override
  public boolean containsKey(KeyType key) {
    if (key == null) {
      return false;
    } else {
      int indexToCheck = (Math.abs(key.hashCode()) % this.table.length);
      if (this.table[indexToCheck] == null) {
        return false;
      } else {
        LinkedList<Pair> head = this.table[indexToCheck];
        for (Pair checkKey : head) {
          if (checkKey.key.equals(key)) {
            return true;
          }
        }
        // After looping through and not finding it, return false
        return false;
      }
    }
  }

  /**
   * Retrieves the specific value that a key maps to.
   *
   * @param key the key to look up
   * @return the value that key maps to
   * @throws NoSuchElementException when key is not stored in this collection
   */
  @Override
  public ValueType get(KeyType key) throws NoSuchElementException {
    if (key == null) {
      throw new NoSuchElementException("Key is not stored in the collection");
    }
    int indexStoredAt = (Math.abs(key.hashCode()) % this.table.length);
    if (this.table[indexStoredAt] == null) {
      throw new NoSuchElementException("Key is not stored in the collection");
    }
    LinkedList<Pair> head = this.table[indexStoredAt];
    // Iterate through each pair and see if it contains the same key
    for (Pair compare : head) {
      if (compare.key.equals(key)) {
        return compare.value;
      } else {
        // Do nothing
      }
    }
    // After loop ends, we have gone through every element and not found anything, throw an
    // exception
    throw new NoSuchElementException("Key is not stored in the collection");
  }

  /**
   * Remove the mapping for a key from this collection.
   *
   * @param key the key whose mapping to remove
   * @return the value that the removed key mapped to
   * @throws NoSuchElementException when key is not stored in this collection
   */
  @Override
  public ValueType remove(KeyType key) throws NoSuchElementException {
    if (key == null) { throw new NoSuchElementException("Key is not stored in this collection"); }
    // Continue on and find index where key is stored, and if an array exists there iterate
    // through it to see if it exists.
    int indexOfKey = (Math.abs(key.hashCode()) % this.table.length);
    if (this.table[indexOfKey] == null) {
      throw new NoSuchElementException("Key is not stored in the collection");
    }
    LinkedList<Pair> head = this.table[indexOfKey];
    for (int i = 0; i < head.size(); ++i) {
      Pair toCompare = head.get(i);
      if (toCompare.key.equals(key)) {
        // If we find the proper key, remove it and return that key's value
        head.remove(i);
        return toCompare.value;
      }
    }
    throw new NoSuchElementException("Key is not stored in the collection");
  }

  /**
   * Removes all key,value pairs from this collection.
   */
  @Override
  public void clear() {
    // To do this, iterate through the Array and set each to null.
    for (int i = 0; i < this.table.length; ++i) {
      if (this.table[i] == null) { // Do nothing
      } else {
        this.table[i] = null;
      }
    }
  }

  /**
   * Retrieves the number of keys stored in this collection.
   *
   * @return the number of keys stored in this collection
   */
  @Override
  public int getSize() {
    int numberOfKeys = 0;
    for (int i = 0; i < this.table.length; ++i) {
      if (this.table[i] == null) { // Do nothing since this index is empty
      } else { // Index is not empty, add its size
        numberOfKeys = numberOfKeys + this.table[i].size();
      }
    }
    return numberOfKeys;
  }

  /**
   * Retrieves this collection's capacity.
   *
   * @return the size of the underlying array for this collection
   */
  @Override
  public int getCapacity() {
    return this.table.length;
  }


  /**
   * Retrieves this collection's keys.
   * @return a list of keys in the underlying array for this collection
   */
  public List<KeyType> getKeys() {
    LinkedList<KeyType> returnKeys = new LinkedList<>();
    for (int i = 0; i < this.table.length; ++i) {
      if (this.table[i] != null) {
        for (Pair keyToAdd : this.table[i]) {
          returnKeys.add(keyToAdd.key);
        }
      }
    }
    return returnKeys;
  }

  /**
   * This method tests the functionality of the size and capacity method, noting that size refers to
   * the number of keys in the Array of LinkedLists, while the capacity refers to the number of
   * array elements
   */
  @Test
  public void testSizeAndCapacity() {
    // First, initialize with size 10.
    HashtableMap<Integer, String> testOne = new HashtableMap<>(10);
    // Add 10 elements to achieve a size of 10.
    testOne.put(10, "Hi");
    testOne.put(20, "My");
    testOne.put(30, "Name");
    testOne.put(40, "Is");
    testOne.put(50, "John");
    // Now, fill up other spots to increase capacity and size
    testOne.put(1, "I");
    testOne.put(2, "Like");
    testOne.put(3, "Eating");
    testOne.put(4, "Fancy");
    testOne.put(5, "Food");
    // Now, capacity should be 20 (Doubled) and size should be 10
    assertTrue("Size of the HashMap should be 10", testOne.getSize() == 10);
    assertTrue("Capacity of the HashMap should be 20", testOne.getCapacity() == 20);
  }

  @Test
  public void testClearAndContainsKey() {
    // Make a HashTable containing 6 keys, then test the containsKey. After, we use the clear
    // method and use containsKey on the same key.
    HashtableMap<Integer, String> testTwo = new HashtableMap<>(10);
    testTwo.put(13, "I");
    testTwo.put(24, "Cannot");
    testTwo.put(62, "Think");
    testTwo.put(74, "Of");
    testTwo.put(30, "A");
    testTwo.put(93, "Message");
    // Now, we make sure that the containsKey returns true for these keys and returns false for
    // keys that we did not put into the Array of LinkedList
    assertTrue("Should contain 13", testTwo.containsKey(13));
    assertTrue("Should contain 24", testTwo.containsKey(24));
    assertTrue("Should contain 62", testTwo.containsKey(62));
    assertTrue("Should contain 74", testTwo.containsKey(74));
    assertTrue("Should contain 30", testTwo.containsKey(30));
    assertTrue("Should contain 93", testTwo.containsKey(93));
    assertFalse("Should NOT contain 9234", testTwo.containsKey(9234));
    assertFalse("Should NOT contain 61", testTwo.containsKey(61));
    // Now, clear it and make sure none of the previous keys exist
    testTwo.clear();
    // Test the previous keys that returned true when calling the containsKey method, and see if
    // it now returns false like it should
    assertFalse("Should NOT contain 13 after calling clear()", testTwo.containsKey(13));
    assertFalse("Should NOT contain 24 after calling clear()", testTwo.containsKey(24));
    assertFalse("Should NOT contain 62 after calling clear()", testTwo.containsKey(62));
    assertFalse("Should NOT contain 74 after calling clear()", testTwo.containsKey(74));
    assertFalse("Should NOT contain 30 after calling clear()", testTwo.containsKey(30));
    assertFalse("Should NOT contain 93 after calling clear()", testTwo.containsKey(93));
  }

  /**
   * This method tests the functionality of the remove method and makes sure that it removes
   * key-value pairs properly whilst also throwing exceptions if the node does NOT exist.
   */
  @Test
  public void testRemove() {
    // First initialize a HashtableMap
    HashtableMap<Integer, String> testThree = new HashtableMap<>(10);
    testThree.put(92, "Steak");
    testThree.put(963, "Is");
    testThree.put(962, "My");
    testThree.put(157, "Favorite");
    testThree.put(75, "Food");
    // Now, we remove two nodes and see if it contains it in the Array of LinkedLists.
    try {
      testThree.remove(92);
      testThree.remove(963);
    } catch (Exception e) {
      Assert.fail("Should not have thrown any exception");
    }
    assertFalse("Should NOT contain the key 92 after we removed it", testThree.containsKey(92));
    assertFalse("Should NOT contain the key 963 after we removed it", testThree.containsKey(963));

    // Now, test remove on a key that does not exist. This should throw a NoSuchElementException
    try {
      testThree.remove(93484395); // This key does not exist.
      Assert.fail("Should have thrown an exception and not continued on");
    } catch (NoSuchElementException e) {
      assertFalse("Exception message should not be empty", e.getMessage().isEmpty());
    } catch (Exception e) {
      Assert.fail("Should have not thrown any other exception");
    }
  }

  /**
   * This method tests the functionality of the put method and makes sure that it throws the correct
   * exceptions.
   */
  @Test
  public void testPut() {
    HashtableMap<Integer, String> testFour = new HashtableMap<>(10);
    // First, test the exception handling of the put method. When given an null key, it should
    // throw a null pointer exception.
    try {
      testFour.put(null, "Haha");
      Assert.fail("Should have thrown a NullPointerException");
    } catch (NullPointerException e) {
      assertFalse("Should contain an exception message", e.getMessage().isEmpty());
    } catch (Exception e) {
      Assert.fail("Should have not thrown any other exception");
    }
    // Next, we put two of the same keys and make sure it throws the correct exception
    testFour.put(12, "John");
    try {
      testFour.put(12, "Isner");
      Assert.fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertFalse("Message should not be empty", e.getMessage().isEmpty());
    } catch (Exception e) {
      Assert.fail("Should not have thrown any other exception");
    }
    // Afterward, the put method should not make any changes to the table if we try to add two of
    // the same key, so check for that
    assertTrue("Size should be 1", testFour.getSize() == 1);
    // Lastly, add some key-value pairs and see if it is properly inserted
    testFour.put(15, "My");
    testFour.put(63, "Name");
    testFour.put(75, "Is");
    testFour.put(203, "Raymond");
    testFour.put(646, "Tu");
    assertTrue("Should contain 12", testFour.containsKey(12));
    assertTrue("Should contain 15", testFour.containsKey(15));
    assertTrue("Should contain 63", testFour.containsKey(63));
    assertTrue("Should contain 75", testFour.containsKey(75));
    assertTrue("Should contain 203", testFour.containsKey(203));
    assertTrue("Should contain 646", testFour.containsKey(646));
  }

  /**
   * This method tests the functionality of the get() method and makes sure that it works properly
   * and throws the correct exceptions.
   */
  @Test
  public void testGet() {
    HashtableMap<Integer, String> testFive = new HashtableMap<>(10);
    testFive.put(12, "Colton");
    testFive.put(13, "Godec");
    testFive.put(14, "Reilly");
    testFive.put(15, "Principe");
    testFive.put(16, "Nathan");
    testFive.put(17, "Butters");
    // Now, test the functionality of the get method.
    assertTrue("Value should be Colton", testFive.get(12).equals("Colton"));
    assertTrue("Value should be Godec", testFive.get(13).equals("Godec"));
    assertTrue("Value should be Reilly", testFive.get(14).equals("Reilly"));
    assertTrue("Value should be Principe", testFive.get(15).equals("Principe"));
    assertTrue("Value should be Nathan", testFive.get(16).equals("Nathan"));
    assertTrue("Value should be Butters", testFive.get(17).equals("Butters"));
    // Afterward, try and see if it throws the correct exception for a key that does not exist
    try {
      testFive.get(93495); // A key that does not exist
      Assert.fail("Should have thrown an exception");
    } catch (NoSuchElementException e) {
      assertFalse("Message should not be empty", e.getMessage().isEmpty());
    } catch (Exception e) {
      Assert.fail("No other exception should have been thrown");
    }
  }
}
