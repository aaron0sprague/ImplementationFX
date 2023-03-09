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

import java.time.LocalDate;
import java.util.Properties;
import java.util.regex.Pattern;

// project imports

/**
 * The class containing the Book View for the Library System application
 */
public class PatronView extends View {

    // GUI components
    private TextField nameField;
    private TextField addressField;
    private TextField cityField;
    private TextField stateField;
    private TextField zipField;
    private TextField emailField;
    private TextField dobField;

    private ComboBox statusCombo;

    protected Button submitButton;

    private LocalDate oldest = LocalDate.of(1917, 01, 01);

    // For showing error message
    protected MessageView statusLog;

    // constructor for this class -- takes a model object
    PatronView(IModel book) {
        super(book, "PatronView");

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

        Text prompt = new Text("Enter New Patron Information");
        prompt.setWrappingWidth(300);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        Text nameLabel = new Text(" Name : ");
        Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
        nameLabel.setFont(myFont);
        nameLabel.setWrappingWidth(110);
        nameLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(nameLabel, 0, 1);

        nameField = new TextField();
        nameField.setOnAction(this::processAction);
        grid.add(nameField, 1, 1);

        Text addressLabel = new Text(" Address : ");
        addressLabel.setFont(myFont);
        addressLabel.setWrappingWidth(110);
        addressLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(addressLabel, 0, 2);

        addressField = new TextField();
        addressField.setOnAction(this::processAction);
        grid.add(addressField, 1, 2);

        Text cityLabel = new Text(" City : ");
        cityLabel.setFont(myFont);
        cityLabel.setWrappingWidth(110);
        cityLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(cityLabel, 0, 3);

        cityField = new TextField();
        cityField.setOnAction(this::processAction);
        grid.add(cityField, 1, 3);

        Text stateLabel = new Text(" State Code : ");
        stateLabel.setFont(myFont);
        stateLabel.setWrappingWidth(110);
        stateLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(stateLabel, 0, 4);

        stateField = new TextField();
        stateField.setOnAction(this::processAction);
        stateField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z]{0,2}")) {
                stateField.setText(oldValue);
            }
        });
        grid.add(stateField, 1, 4);

        Text zipLabel = new Text(" ZIP : ");
        zipLabel.setFont(myFont);
        zipLabel.setWrappingWidth(110);
        zipLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(zipLabel, 0, 5);

        zipField = new TextField();
        zipField.setOnAction(this::processAction);
        zipField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,5}")) {
                zipField.setText(oldValue);
            }
        });
        grid.add(zipField, 1, 5);

        Text emailLabel = new Text(" E-mail Address : ");
        emailLabel.setFont(myFont);
        emailLabel.setWrappingWidth(110);
        emailLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(emailLabel, 0, 6);

        emailField = new TextField();
        emailField.setOnAction(this::processAction);
        grid.add(emailField, 1, 6);

        Text dobLabel = new Text(" Date of Birth : ");
        dobLabel.setFont(myFont);
        dobLabel.setWrappingWidth(110);
        dobLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(dobLabel, 0, 7);

        dobField = new TextField();
        dobField.setOnAction(this::processAction);
        // dobField.textProperty().addListener((observable, oldValue, newValue) -> {
        // if (!newValue.matches("\\d{0,4}[-]\\d{0,2}[-]\\d{0,2}")) {
        // dobField.setText(oldValue);
        // }
        // });
        grid.add(dobField, 1, 7);

        Text statusLabel = new Text(" Status : ");
        statusLabel.setFont(myFont);
        statusLabel.setWrappingWidth(110);
        statusLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(statusLabel, 0, 8);

        statusCombo = new ComboBox();
        statusCombo.getItems().addAll("Active", "Inactive");
        statusCombo.setValue("Active");
        grid.add(statusCombo, 1, 8);

        HBox doneCont = new HBox(100);
        doneCont.setAlignment(Pos.CENTER);
        submitButton = new Button("Submit");
        // submitButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        submitButton.setOnAction(this::processAction);

        Button doneButton = new Button("Back");
        // doneButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
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

    // public void populateFields() {
    // nameField.setText((String) myModel.getState("author"));
    // addressField.setText((String) myModel.getState("title"));
    // cityField.setText((String) myModel.getState("pubYear"));
    // serviceCharge.setText((String) myModel.getState("status"));
    // }

    private void processAction(Event evt) {
        // DEBUG: System.out.println("TellerView.actionPerformed()");

        clearErrorMessage();

        String nameEntered = nameField.getText();
        String addressEntered = addressField.getText();
        String cityEntered = cityField.getText();
        String stateEntered = stateField.getText();
        String zipEntered = zipField.getText();
        String emailEntered = emailField.getText();
        String dateEntered = dobField.getText();
        String statusSelected = (String) statusCombo.getValue();

        Pattern dateValidation = Pattern.compile("^((19|2[0-9])[0-9]{2})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$");
        Pattern emailValidation = Pattern.compile("^([\\w \\._]+\\<[a-z0-9!#$%&'*+/=?^_`{|}~-]+" +
                "(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a" +
                "-z0-9](?:[a-z0-9-]*[a-z0-9])?\\>|[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%" +
                "&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-" +
                "]*[a-z0-9])?)$");

        if ((nameEntered == null) || (nameEntered.length() == 0)) {
            displayErrorMessage("Please enter a name!");
            nameField.requestFocus();
        } else if ((addressEntered == null) || (addressEntered.length() == 0)) {
            displayErrorMessage("Please enter an address!");
            addressField.requestFocus();
        } else if ((cityEntered == null) || (cityEntered.length() == 0)) {
            displayErrorMessage("Please enter a city!");
            cityField.requestFocus();
        } else if ((stateEntered == null) || (stateEntered.length() == 0)) {
            displayErrorMessage("Please enter a state code!");
            stateField.requestFocus();
        } else if ((zipEntered == null) || (zipEntered.length() == 0)) {
            displayErrorMessage("Please enter a ZIP code!");
            zipField.requestFocus();
        } else if (!emailValidation.matcher(emailEntered.toLowerCase()).matches()) {
            displayErrorMessage("Email not in address@server.com format!");
            emailField.requestFocus();
        } else if (!dateValidation.matcher(dateEntered).matches()) {
            displayErrorMessage("Date must be in YYYY-MM-DD");
            dobField.requestFocus();
        } else {
            LocalDate dob = LocalDate.parse(dobField.getText());
            LocalDate youngest = LocalDate.now().minusYears(18);
            if (dob.isBefore(oldest)) {
                displayErrorMessage("Date must be after " + oldest);
                dobField.requestFocus();
            } else if (dob.isAfter(youngest)) {
                displayErrorMessage("Date must be before " + youngest.plusDays(1));
                dobField.requestFocus();
            } else {
                Properties props = new Properties();
                props.setProperty("name", nameEntered);
                props.setProperty("address", addressEntered);
                props.setProperty("city", cityEntered);
                props.setProperty("stateCode", stateEntered.toUpperCase());
                props.setProperty("zip", zipEntered);
                props.setProperty("email", emailEntered);
                props.setProperty("dateOfBirth", dateEntered);
                props.setProperty("status", statusSelected.toLowerCase());
                myModel.stateChangeRequest("InsertPatron", props);
                displayMessage("Success!");
            }
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