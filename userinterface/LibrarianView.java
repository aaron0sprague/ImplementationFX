// specify the package
package userinterface;

// system imports

import impresario.IModel;
import javafx.event.Event;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

// project imports

/**
 * The class containing the Teller View  for the ATM application
 */
public class LibrarianView extends View {

    // For showing error message
    private MessageView statusLog;

    // constructor for this class -- takes a model object
    LibrarianView(IModel librarian) {

        super(librarian, "LibrarianView");

        // create a container for showing the contents
        VBox container = new VBox(10);
        container.setAlignment(Pos.CENTER);

        container.setPadding(new Insets(15, 5, 0, 5));

        // create a Node (Text) for showing the title
        container.getChildren().add(createTitle());

        // create a Node (GridPane) for showing data entry fields
        container.getChildren().add(createFormContents());

        // Error message area
        container.getChildren().add(createStatusLog("                          "));

        getChildren().add(container);

        // STEP 0: Be sure you tell your model what keys you are interested in
        myModel.subscribe("TransactionError", this);
    }

    // Create the label (Text) for the title of the screen
    private Node createTitle() {

        Text titleText = new Text(" Brockport Library System ");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.BLACK);

        return titleText;
    }

    // Create the main form contents
    private GridPane createFormContents() {
        GridPane grid = new GridPane();
        grid.setPrefWidth(150);
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 0, 25));

        // New book
        Button insertNewBook = new Button("Insert New Book");
        insertNewBook.setOnAction(
                // Lambda function call to stateChangeRequest
                e -> myModel.stateChangeRequest("NewBook", ""));
        insertNewBook.setMinWidth(grid.getPrefWidth());
        grid.add(insertNewBook, 0,0);
        GridPane.setHalignment(insertNewBook, HPos.CENTER);

        // New patron
        Button insertNewPatron = new Button("Insert New Patron");
        insertNewPatron.setOnAction(
                // Lambda function call to stateChangeRequest
                e -> myModel.stateChangeRequest("NewPatron", ""));
        insertNewPatron.setMinWidth(grid.getPrefWidth());
        grid.add(insertNewPatron, 0,1);
        GridPane.setHalignment(insertNewPatron, HPos.CENTER);

        // Search books
        Button searchBooks = new Button("Search Books");
        searchBooks.setOnAction(
                // Lambda function call to stateChangeRequest
                e -> myModel.stateChangeRequest("SearchBookView", ""));
        searchBooks.setMinWidth(grid.getPrefWidth());
        grid.add(searchBooks, 0,2);
        GridPane.setHalignment(searchBooks, HPos.CENTER);

        // Search patrons
        Button searchPatrons = new Button("Search Patrons");
        searchPatrons.setOnAction(
                // Lambda function call to stateChangeRequest
                e -> myModel.stateChangeRequest("SearchPatrons", ""));
        searchPatrons.setMinWidth(grid.getPrefWidth());
        grid.add(searchPatrons, 0,3);
        GridPane.setHalignment(searchPatrons, HPos.CENTER);


        Button doneButton = new Button("Done");
        doneButton.setOnAction(e -> System.exit(1));
        doneButton.setMinWidth(grid.getPrefWidth());

//        HBox btnContainer = new HBox(10);
//        btnContainer.setAlignment(Pos.BOTTOM_RIGHT);
//        btnContainer.getChildren().add(doneButton);
//        grid.add(btnContainer, 0, 4);
        grid.add(doneButton, 0, 6);
        GridPane.setHalignment(doneButton, HPos.CENTER);


        return grid;
    }


    // Create the status log field
    private MessageView createStatusLog(String initialMessage) {
        return statusLog = new MessageView(initialMessage);
    }

    // This method processes events generated from our GUI components.
    // Make the ActionListeners delegate to this method
    private void processAction(Event e) {
//        this.e = e;
        System.out.println("TellerView.actionPerformed() " + e);
        System.out.println(e.getTarget());
        clearErrorMessage();

    }

    public void updateState(String key, Object value) {
        // STEP 6: Be sure to finish the end of the 'perturbation'
        // by indicating how the view state gets updated.
        if (key.equals("LoginError")) {
            // display the passed text
            displayErrorMessage((String) value);
        }

    }

    /**
     * Display error message
     */
    public void displayErrorMessage(String message) {
        statusLog.displayErrorMessage(message);
    }

    /**
     * Clear error message
     */
    public void clearErrorMessage() {
        statusLog.clearErrorMessage();
    }

}

