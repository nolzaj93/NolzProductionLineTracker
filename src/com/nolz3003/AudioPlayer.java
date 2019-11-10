package com.nolz3003;

/**
 * The AudioPlayer class.
 *
 * @author austinnolz - The AudioPlayer class is a type of Product class and implements the
 *      MultimediaControl interface. This is the data model for AudioPlayer objects.
 */
public class AudioPlayer extends Product implements MultimediaControl {

  private String supportedAudioFormats;
  private String supportedPlaylistFormats;

  /**
   * Constructs an AudioPlayer object.
   *
   * @param name - product name
   * @param manufacturer - manufacture name
   * @param supportedAudioFormats - Audio formats supported by the product
   * @param supportedPlaylistFormats - Playlist formats supported by the product.
   */
  AudioPlayer(String name, String manufacturer, String supportedAudioFormats,
      String supportedPlaylistFormats) {
    super(name, manufacturer, ItemType.AUDIO);
    setProductName(name);
    setManufacturer(manufacturer);

    setSupportedAudioFormats(supportedAudioFormats);
    setSupportedPlaylistFormats(supportedPlaylistFormats);
  }

  public String getSupportedAudioFormats() {
    return supportedAudioFormats;
  }

  private void setSupportedAudioFormats(String supportedAudioFormats) {
    this.supportedAudioFormats = supportedAudioFormats;
  }

  public String getSupportedPlaylistFormats() {
    return supportedPlaylistFormats;
  }

  private void setSupportedPlaylistFormats(String supportedPlaylistFormats) {
    this.supportedPlaylistFormats = supportedPlaylistFormats;
  }

  public void play() {
    System.out.println("Playing");
  }

  public void stop() {
    System.out.println("Stopping");
  }

  public void previous() {
    System.out.println("Previous");
  }

  public void next() {
    System.out.println("Next");
  }

  @Override
  public String toString() {

    return super.toString() + "Type: " + ItemType.AUDIO.name() + "\n"
        + "Supported Audio Formats: " + getSupportedAudioFormats() + "\n"
        + "Supported Playlist Formats: " + getSupportedPlaylistFormats();
  }
}
