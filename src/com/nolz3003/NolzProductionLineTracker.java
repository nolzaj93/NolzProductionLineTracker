package com.nolz3003;

import java.util.Date;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * NolzProductionLineTracker class.
 *
 * @author austinnolz
 *
 *      The driver class of the application containing main and the start method.
 */
public class NolzProductionLineTracker extends Application {

  /**
   * The start method is the starting point of a JavaFX program. This start method sets the title.
   *
   * @param primaryStage The primary stage.
   * @throws Exception Any problem with the user input to be handled.
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    PlayerDriver.testPlayer();

    Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));

    Scene scene = new Scene(root, 600, 450);
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

    Product productProduced = new Widget("iPod", "Apple", ItemType.AUDIO);

    // test constructor used when creating production records from user interface
    int numProduced = 3;  // this will come from the combobox in the UI
    int itemCount = 0;

    for (int productionRunProduct = 0; productionRunProduct < numProduced; productionRunProduct++) {
      ProductionRecord pr = new ProductionRecord(productProduced, itemCount++);
      // using the iterator as the product id for testing
      System.out.println(pr.toString());
    }

    launch(args);
  }
}
