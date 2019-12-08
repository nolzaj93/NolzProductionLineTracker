package com.nolz3003;

/**
 * The MoviePlayer class.
 *
 * @author austinnolz - The MoviePlayer class builds a product object that holds specific data about
 * a movie player and implement the MultimediaControl interface.
 */
public class MoviePlayer extends Product implements MultimediaControl {

  /**
   * Constructs a MoviePlayer object.
   *
   * @param name - product name
   * @param manufacturer - manufacturer
   * @param screen - screen
   * @param monitorType - monitor type
   */
  MoviePlayer(String name, String manufacturer, Screen screen, MonitorType monitorType) {

    setProductName(name);
    setManufacturer(manufacturer);
    setScreen(screen);
    setMonitorType(monitorType);
    ItemType it = null;
    setItemType(it.VISUAL);
  }

  private Screen screen;
  private MonitorType monitorType;

  public Screen getScreen() {
    return screen;
  }

  public void setScreen(Screen screen) {
    this.screen = screen;
  }

  public MonitorType getMonitorType() {
    return monitorType;
  }

  public void setMonitorType(MonitorType monitorType) {
    this.monitorType = monitorType;
  }

  public void play() {
    System.out.println("Playing movie");
  }

  public void stop() {
    System.out.println("Stopping movie");
  }

  public void previous() {
    System.out.println("Previous movie");
  }

  public void next() {
    System.out.println("Next movie");
  }

  @Override
  public String toString() {
    return super.toString() + "Type: VISUAL\n" + "Screen:\n" + "Resolution: " + screen
        .getResolution()
        + "\n"
        + "Refresh Rate: " + screen.getRefreshRate() + "\n"
        + "Response Time: " + screen.getResponseTime() + "\n"
        + "Monitor Type: " + monitorType.name() + "\n";
  }
}
