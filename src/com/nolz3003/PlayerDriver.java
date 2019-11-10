package com.nolz3003;

/**
 * The PlayerDriver class is for testing with the testPlayer() method.
 */
public class PlayerDriver {

  /**
   * The testPlayer method builds a MoviePlayer object.
   */
  public static void testPlayer() {

    MoviePlayer newMoviePlayer = new MoviePlayer("DBPOWER MK101", "OracleProduction",
        new Screen("720x480", 40, 22), MonitorType.LCD);
  }
}
