package nolzOOP;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * @author Austin Nolz
 *
 * -Description: required component of every Javadoc
 */
public class NolzOOPProductionLineTracker extends Application {



  /**
   * The start method is the starting point of a JavaFX program. This start method sets the title.
   *
   * @param primaryStage The primary stage.
   * @throws Exception Any problem with the user input to be handled.
   */
  @Override
  public void start(Stage primaryStage) throws Exception {


    //display the JOB Data
    //btShowJobs.setOnAction(e -> showData());

    Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));

    Scene scene = new Scene(root, 600, 400);
    primaryStage.setTitle("Display JOB Information");
    primaryStage.setScene(scene);
    scene.getStylesheets()
        .add(NolzOOPProductionLineTracker.class.getResource("styleCSS.css").toExternalForm());

    primaryStage.show();

//    primaryStage.setTitle("Hello World");
//    primaryStage.setScene(new Scene(root, 300, 275));
//    primaryStage.show();

  }


  public static void main(String[] args) {

    launch(args);
  }
}
