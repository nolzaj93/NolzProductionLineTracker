package com.nolz3003;

/**
 * Every MoviePlayer has a Screen object, which contains details about the screen and implements the
 * ScreenSpec interface.
 */
public class Screen implements ScreenSpec {


  Screen(String resolution, int refreshRate, int responseTime) {
    setResolution(resolution);
    setRefreshRate(refreshRate);
    setResponseTime(responseTime);
  }

  private String resolution;
  private int refreshRate;
  private int responseTime;

  @Override
  public String getResolution() {
    return resolution;
  }

  private void setResolution(String resolution) {
    this.resolution = resolution;
  }

  @Override
  public int getRefreshRate() {
    return refreshRate;
  }

  private void setRefreshRate(int refreshRate) {
    this.refreshRate = refreshRate;
  }

  @Override
  public int getResponseTime() {
    return responseTime;
  }

  private void setResponseTime(int responseTime) {
    this.responseTime = responseTime;
  }

  /**
   * Overridden toString() method returns the screen information as a String.
   *
   * @return - Resolution, refresh rate, and response time are returned as a String.
   */
  @Override
  public String toString() {
    return "Resolution: " + resolution + "\n"
        + "Refresh Rate: " + refreshRate + "\n"
        + "Response Time: " + responseTime + "\n";
  }
}
