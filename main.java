import event.Event;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Librarian;
import userinterface.MainStageContainer;
import userinterface.WindowPosition;

public class main extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    public void start(Stage primaryStage) {
        System.out.println("Library Assignment 2");
        System.out.println("Shain Krutz and Aaron Sprague");
        // Create the top-level container (main frame) and add contents to it.
        MainStageContainer.setStage(primaryStage, "Library Assignment 2");
        // Main frame of the application
        Stage mainStage = MainStageContainer.getInstance();
        // Finish setting up the stage (ENABLE THE GUI TO BE CLOSED USING THE TOP RIGHT
        // 'X' IN THE WINDOW), and show it.
        mainStage.setOnCloseRequest(event -> System.exit(0));
        try {
            new Librarian();
        } catch (Exception exc) {
            System.err.println("Could not create Librarian!");
            new Event(Event.getLeafLevelClassName(this), "LibrarySystem.<init>", "Unable to create Librarian object", Event.ERROR); exc.printStackTrace();
        }
        WindowPosition.placeCenter(mainStage);
        mainStage.show();
    }
}











// import java.util.Locale;
// import java.util.ResourceBundle;
// import java.io.FileOutputStream;
// import java.io.File;

// import javafx.application.Application;
// import javafx.event.ActionEvent;
// import javafx.event.EventHandler;
// import javafx.geometry.Insets;
// import javafx.geometry.Pos;
// import javafx.scene.Scene;
// import javafx.scene.control.Button;
// import javafx.scene.control.Label;
// import javafx.scene.control.PasswordField;
// import javafx.scene.control.TextField;
// import javafx.scene.layout.GridPane;
// import javafx.scene.layout.HBox;
// import javafx.scene.paint.Color;
// import javafx.scene.text.Font;
// import javafx.scene.text.FontWeight;
// import javafx.scene.text.Text;
// import javafx.stage.Stage;

// // project imports
// import event.Event;
// import event.EventLog;
// import common.PropertyFile;

// import model.Librarian;
// import userinterface.MainStageContainer;
// import userinterface.WindowPosition;

// /** The class containing the main program for the CSC429 Library Project */
// //=======================================================================
// public class main extends Application{
//     private  Librarian librarian;
//     /** Main gui stage for the application*/
//     private Stage mainStage;
//     // start method for this class, the main application object
//     //----------------------------------------------------------
//     public void start(Stage primaryStage){
//         System.out.println("Library");
//         System.out.println("Copyright 2023 Shain Krutz and Aaron Sprague");
//         // Create the top-level container (main frame) and add contents to it.
//         MainStageContainer.setStage(primaryStage, "Library System 1.0");
//         mainStage = MainStageContainer.getInstance();
//         // Finish setting up the stage (ENABLE THE GUI TO BE CLOSED USING THE TOP RIGHT
//         // 'X' IN THE WINDOW), and show it.
//         mainStage.setOnCloseRequest(new EventHandler <javafx.stage.WindowEvent>() { @Override public void handle(javafx.stage.WindowEvent event) {System.exit(0);}});
//         try{
//             librarian = new Librarian();
//         }
//         catch(Exception exc){
//             System.err.println("Could not create Librarian!");
//             new Event(Event.getLeafLevelClassName(this), "Assign2driver.<init>", "Unable to create Librarian object", Event.ERROR);
//             exc.printStackTrace();
//         }
//         WindowPosition.placeCenter(mainStage);
//         mainStage.show();
//     }
//     public static void main(String[] args){
//         launch(args);
//     }
// }