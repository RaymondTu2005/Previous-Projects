import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class Backend implements BackendInterface {

  private final IterableSortedCollection<Song> tree;
  private Integer danceabilityThreshold = null;
  private Integer rangeLow = null;
  private Integer rangeHigh = null;

  public Backend(IterableSortedCollection<Song> tree) {
    this.tree = tree;
  }

  /**
   * Loads data from the .csv file referenced by filename.  You can rely on the exact headers found
   * in the provided songs.csv, but you should not rely on them always being presented in this order
   * or on there not being additional columns describing other song qualities. After reading songs
   * from the file, the songs are inserted into the tree passed to the constructor.
   *
   * @param filename is the name of the csv file to load data from
   * @throws IOException when there is trouble finding/reading file
   */
  @Override
  public void readData(String filename) throws IOException {
    //create a new file object with the provided file name
    File file = new File(filename);

    //use a Scanner to read the file
    try (Scanner scanner = new Scanner(file)) {
      //skip the header line
      if (scanner.hasNextLine()) {
        scanner.nextLine(); //skipping headers
      }
      //read each line
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        String[] values;

        //if the line starts with a quote meaning there may be a comma in the title, use helper
        if (line.charAt(0) == '\"') {
          values = readDataHelper(line);
        } else { //otherwise split the line normally
          values = line.split(",");
        }

        String title = values[0].replace("\"", ""); // Remove quotes if present
        String artist = values[1];
        String genres = values[2];
        int year = Integer.parseInt(values[3]);
        int bpm = Integer.parseInt(values[4]);
        int energy = Integer.parseInt(values[5]);
        int danceability = Integer.parseInt(values[6]);
        int loudness = Integer.parseInt(values[7]);
        int liveness = Integer.parseInt(values[8]);

        //create a Song object using the values
        Song song =
            new Song(title, artist, genres, year, bpm, energy, danceability, loudness, liveness);

        //insert the song into the tree
        tree.insert(song);
      }
    } catch (IOException e) {
      throw new IOException("Can't read file: " + filename, e);
    }
  }

  /**
   * Retrieves a list of song titles from the tree passed to the contructor. The songs should be
   * ordered by the songs' Energy, and that fall within the specified range of Energy values.  This
   * Energy range will also be used by future calls to the setFilter and getmost Recent methods.
   *
   * If a Danceability filter has been set using the setFilter method below, then only songs that
   * pass that filter should be included in the list of titles returned by this method.
   *
   * When null is passed as either the low or high argument to this method, that end of the range is
   * understood to be unbounded.  For example, a null high argument means that there is no maximum
   * Energy to include in the returned list.
   *
   * @param low  is the minimum Energy of songs in the returned list
   * @param high is the maximum Energy of songs in the returned list
   * @return List of titles for all songs from low to high, or an empty list when no such songs have
   * been loaded
   */
  @Override
  public List<String> getRange(Integer low, Integer high) {
    int effectiveLow = (low != null) ? low : Integer.MIN_VALUE;
    int effectiveHigh = (high != null) ? high : Integer.MAX_VALUE;

    List<Song> result = new ArrayList<>();
    this.rangeLow = effectiveLow;
    this.rangeHigh = effectiveHigh;

    //iterate through the tree with songs
    for (Song song : tree) {
      int energy = song.getEnergy();
      if ((energy >= effectiveLow) && (high == null || energy <= effectiveHigh)) {
        if (danceabilityThreshold == null || song.getDanceability() > danceabilityThreshold) {
          result.add(song);
        }
      }
    }
    //sort the filtered songs by energy
    result.sort(Comparator.comparingInt(Song::getEnergy));

    //extract the titles of the sorted songs
    List<String> songTitles = new ArrayList<>();
    for (Song song : result) {  // Enhanced for loop to extract titles
      songTitles.add(song.getTitle());
    }
    return songTitles;

  }

  /**
   * Retrieves a list of song titles that have a Danceability that is larger than the specified
   * threshold.  Similar to the getRange method: this list of song titles should be ordered by the
   * songs' Energy, and should only include songs that fall within the specified range of Energy
   * values that was established by the most recent call to getRange.  If getRange has not
   * previously been called, then no low or high Energy bound should be used.  The filter set by
   * this method will be used by future calls to the getRange and getmost Recent methods.
   *
   * When null is passed as the threshold to this method, then no Danceability threshold should be
   * used.  This effectively clears the filter.
   *
   * @param threshold filters returned song titles to only include songs that have a Danceability
   *                  that is larger than this threshold.
   * @return List of titles for songs that meet this filter requirement, or an empty list when no
   * such songs have been loaded
   */
  @Override
  public List<String> setFilter(Integer threshold) {
    this.danceabilityThreshold = threshold;
    return getRange(rangeLow, rangeHigh);
  }

  /**
   * This method returns a list of song titles representing the five most Recent songs that both
   * fall within any attribute range specified by the most recent call to getRange, and conform to
   * any filter set by the most recent call to setFilter.  The order of the song titles in this
   * returned list is up to you.
   *
   * If fewer than five such songs exist, return all of them.  And return an empty list when there
   * are no such songs.
   *
   * @return List of five most Recent song titles
   */
  @Override
  public List<String> fiveMost() {
    List<Song> filteredSongs = new ArrayList<>();

    //gather songs that fall within the last specified range and meet the danceability filter
    for (Song song : tree) {
      int energy = song.getEnergy();
      if ((rangeLow == null || energy >= rangeLow) && (rangeHigh == null || energy <= rangeHigh)) {
        if (danceabilityThreshold == null || song.getDanceability() > danceabilityThreshold) {
          filteredSongs.add(song);
        }
      }
    }

    //sort the filtered songs by year (most recent first)
    filteredSongs.sort((s1, s2) -> Integer.compare(s2.getYear(), s1.getYear()));

    //create a list to hold the titles of the top five songs
    List<String> result = new ArrayList<>();
    for (int i = 0; i < Math.min(5, filteredSongs.size()); i++) {
      result.add(filteredSongs.get(i).getTitle());
    }
    return result;
  }

  /**
   * Helper method that parses a line of song data where the song title may contain commas and is in
   * double quotes. The method extracts the title and splits the remaining data by commas.
   *
   * @param currentLine a string representing the current line of data it is parsing.
   * @return a string array where the first element is the title of the song with the commas and
   * other fields
   */
  private String[] readDataHelper(String currentLine) {
    //find the first and last quotes in the line
    int firstQuoteIndex = currentLine.indexOf('"');
    int lastQuoteIndex = currentLine.lastIndexOf('"');

    //if no quotes, return the entire line split by commas
    if (firstQuoteIndex == -1) {
      return new String[] {currentLine};
    }

    //get the title (excluding quotes) and the rest of the line
    String title = (currentLine.substring(firstQuoteIndex, lastQuoteIndex + 1)).replace("\"", "");
    String restOfLine =
        currentLine.substring(lastQuoteIndex + 2); // Skip the closing quote and comma

    //split the remaining line and combine it with the title
    String[] values = restOfLine.split(",");
    String[] completeSongData = new String[values.length + 1];

    completeSongData[0] = title;
    System.arraycopy(values, 0, completeSongData, 1, values.length);

    return completeSongData;
  }
}
