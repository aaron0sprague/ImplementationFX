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
 * The class containing the Book View for the Library System application
 */
public class BookView extends View {

    // GUI components
    private TextField authorField;
    private TextField bookTitleField;
    private TextField pubYearField;

    private ComboBox statusCombo;

    protected Button submitButton;

    // For showing error message
    protected MessageView statusLog;

    // constructor for this class -- takes a model object
    BookView(IModel book) {
        super(book, "BookView");

        // create a container for showing the contents
        VBox container = new VBox(10);
        container.setPadding(new Insets(15, 5, 5, 5));

        // Add a title for this panel
        container.getChildren().add(createTitle());

        // create our GUI components, add them to this Container
        container.getChildren().add(createFormContent());

        container.getChildren().add(createStatusLog("             "));

        getChildren().add(container);

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

        Text prompt = new Text("Enter New Book Information");
        prompt.setWrappingWidth(300);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        Text authorLabel = new Text(" Author : ");
        Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
        authorLabel.setFont(myFont);
        authorLabel.setWrappingWidth(110);
        authorLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(authorLabel, 0, 1);

        authorField = new TextField();
        authorField.setOnAction(this::processAction);
        grid.add(authorField, 1, 1);

        Text titleLabel = new Text(" Title : ");
        titleLabel.setFont(myFont);
        titleLabel.setWrappingWidth(110);
        titleLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(titleLabel, 0, 2);

        bookTitleField = new TextField();
        bookTitleField.setOnAction(this::processAction);

        grid.add(bookTitleField, 1, 2);

        Text pubYearLabel = new Text(" Publication Year : ");
        pubYearLabel.setFont(myFont);
        pubYearLabel.setWrappingWidth(110);
        pubYearLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(pubYearLabel, 0, 3);

        pubYearField = new TextField();
        pubYearField.setOnAction(this::processAction);
        pubYearField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,4}")) {
                pubYearField.setText(oldValue);
            }
        });
        grid.add(pubYearField, 1, 3);

        Text statusLabel = new Text(" Status : ");
        statusLabel.setFont(myFont);
        statusLabel.setWrappingWidth(110);
        statusLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(statusLabel, 0, 4);

        statusCombo = new ComboBox();
        statusCombo.getItems().addAll("Active", "Inactive");
        statusCombo.setValue("Active");
        grid.add(statusCombo, 1, 4);


        HBox doneCont = new HBox(100);
        doneCont.setAlignment(Pos.CENTER);
        submitButton = new Button("Submit");
//        submitButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        submitButton.setOnAction(this::processAction);

        Button doneButton = new Button("Back");
//        doneButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        doneButton.setOnAction(e -> {
            clearErrorMessage();
            myModel.stateChangeRequest("CancelAddBook", null);
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

 /*   public void populateFields() {
       authorField.setText((String) myModel.getState("author"));
       bookTitleField.setText((String) myModel.getState("bookTitle"));
       pubYearField.setText((String) myModel.getState("pubYear"));
       //serviceCharge.setText((String) myModel.getState("status"));
   } */

    public void processAction(Event evt)
    {
        // DEBUG: System.out.println("TellerView.actionPerformed()");

        clearErrorMessage();

        String authorEntered = authorField.getText();
        String bookTitleEntered = bookTitleField.getText();
        String pubYearEntered = pubYearField.getText();
        String statusSelected = (String) statusCombo.getValue();

        Pattern pubYearValidation = Pattern.compile("^(18\\d\\d|19\\d\\d|200\\d|201[0-8])$");

        if ((authorEntered == null) || (authorEntered.length() == 0)) {
            displayErrorMessage("Please enter an author!");
            authorField.requestFocus();
        } else if ((bookTitleEntered == null) || (bookTitleEntered.length() == 0)) {
            displayErrorMessage("Please enter a title!");
            bookTitleField.requestFocus();
        } else if (!pubYearValidation.matcher(pubYearEntered).matches()) {
            displayErrorMessage("Year must be between 1800 and 2018");
            pubYearField.requestFocus();
        } else {
            Properties props = new Properties();
            props.setProperty("author", authorEntered);
            props.setProperty("bookTitle", bookTitleEntered);
            props.setProperty("pubYear", pubYearEntered);
            props.setProperty("status", statusSelected.toLowerCase());
            myModel.stateChangeRequest("InsertBook", props);
            displayMessage("Success!");
        }

    }

    /**
     * Update method
     */
    public void updateState(String key, Object value) {
        clearErrorMessage();
    }

    /**
     * Display error message
     */
    public void displayErrorMessage(String message) {
        statusLog.displayErrorMessage(message);
    }

    /**
     * Display info message
     */
    private void displayMessage(String message) {
        statusLog.displayMessage(message);
    }

    /**
     * Clear error message
     */
    public void clearErrorMessage() {
        statusLog.clearErrorMessage();
    }

}


