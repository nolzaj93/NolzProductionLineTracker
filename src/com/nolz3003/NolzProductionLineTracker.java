/**
 * The driver class of the application containing main and the start method. Date: 9/28/19
 *
 * @author Austin Nolz
 */

package com.nolz3003;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NolzProductionLineTracker extends Application {

  /**
   * The start method is the starting point of a JavaFX program. This start method sets the title.
   *
   * @param primaryStage The primary stage.
   * @throws Exception Any problem with the user input to be handled.
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));

    Scene scene = new Scene(root, 700, 600);
    primaryStage.setTitle("Production Line Tracker");
    primaryStage.setScene(scene);
    scene.getStylesheets()
        .add(NolzProductionLineTracker.class.getResource("styleCSS.css").toExternalForm());
    primaryStage.show();

    //primaryStage.setTitle("Hello World");
    //primaryStage.setScene(new Scene(root, 300, 275));
    //primaryStage.show();
  }

  public static void main(String[] args) {

    launch(args);
  }
}
