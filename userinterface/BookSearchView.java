// specify the package
package userinterface;

// system imports

import impresario.IModel;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.Properties;
import java.util.regex.Pattern;

// project imports

/**
 * The class containing the Account View  for the ATM application
 */
public class BookSearchView extends View {

    // GUI components
    private TextField titleSearchField;

    // For showing error message
    private MessageView statusLog;

    // constructor for this class -- takes a model object
    BookSearchView(IModel librarian) {
        super(librarian, "BookSearchView");

        // create a container for showing the contents
        VBox container = new VBox(10);
        container.setPadding(new Insets(15, 5, 5, 5));

        // Add a title for this panel
        container.getChildren().add(createTitle());

        // create our GUI components, add them to this Container
        container.getChildren().add(createFormContent());

        container.getChildren().add(createStatusLog("             "));

        getChildren().add(container);

        myModel.subscribe("ServiceCharge", this);
        myModel.subscribe("UpdateStatusMessage", this);
    }


    // Create the title container
    private Node createTitle() {
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);

        Text titleText = new Text(" Brockport Library System ");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleText.setWrappingWidth(300);
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.DARKGREEN);
        container.getChildren().add(titleText);

        return container;
    }

    // Create the main form content
    private VBox createFormContent() {
        VBox vbox = new VBox(10);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 10, 25));

        Text prompt = new Text("Enter book title to search for");
        prompt.setWrappingWidth(200);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        Text titleSearchLabel = new Text(" Title : ");
        Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
        titleSearchLabel.setFont(myFont);
        titleSearchLabel.setWrappingWidth(50);
        titleSearchLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(titleSearchLabel, 0, 1);

        titleSearchField = new TextField();
        titleSearchField.setOnAction(e -> processAction(e));
        grid.add(titleSearchField, 1, 1);

        HBox doneCont = new HBox(100);
        doneCont.setAlignment(Pos.CENTER);
        Button submitButton = new Button("Submit");
//        submitButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        submitButton.setOnAction(this::processAction);

        Button doneButton = new Button("Back");
//        doneButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        doneButton.setOnAction(e -> {
            clearErrorMessage();
            myModel.stateChangeRequest("CancelTransaction", null);
        });
        doneCont.getChildren().add(submitButton);
        doneCont.getChildren().add(doneButton);

        vbox.getChildren().add(grid);
        vbox.getChildren().add(doneCont);

        return vbox;
    }


    // Create the status log field
    protected MessageView createStatusLog(String initialMessage) {
        statusLog = new MessageView(initialMessage);

        return statusLog;
    }

    private void processAction(Event evt)
    {
        clearErrorMessage();

        String titleEntered = titleSearchField.getText();

//        if ((titleEntered == null) || (titleEntered.length() == 0)) {
//            displayErrorMessage("Please enter a title!");
//            titleSearchField.requestFocus();
//        } else {
            myModel.stateChangeRequest("SearchBook", titleEntered);
//            displayMessage("Success!");
//        }

    }

    /**
     * Update method
     */
    //---------------------------------------------------------
    public void updateState(String key, Object value) {
        clearErrorMessage();
    }

    /**
     * Display error message
     */
    //----------------------------------------------------------
    public void displayErrorMessage(String message) {
        statusLog.displayErrorMessage(message);
    }

    /**
     * Display info message
     */
    //----------------------------------------------------------
    public void displayMessage(String message) {
        statusLog.displayMessage(message);
    }

    /**
     * Clear error message
     */
    //----------------------------------------------------------
    public void clearErrorMessage() {
        statusLog.clearErrorMessage();
    }

}

//---------------------------------------------------------------
//	Revision History:
//


