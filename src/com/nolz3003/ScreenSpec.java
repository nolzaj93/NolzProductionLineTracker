package com.nolz3003;

/**
 * ScreenSpec interface.
 *
 * @author austinnolz - ScreenSpec interface ensures all screen objects have the following methods.
 */
public interface ScreenSpec {

  String getResolution();

  int getRefreshRate();

  int getResponseTime();

}
