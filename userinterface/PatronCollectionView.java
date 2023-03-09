package userinterface;

// system imports

import impresario.IModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.Patron;
import model.PatronCollection;

import java.util.Enumeration;
import java.util.Vector;

// project imports

//==============================================================================
public class PatronCollectionView extends View {
    protected TableView<PatronTableModel> tableOfPatrons;
    protected Button cancelButton;
    protected Button submitButton;

    protected MessageView statusLog;

    // --------------------------------------------------------------------------
    public PatronCollectionView(IModel wsc) {
        super(wsc, "PatronCollectionView");

        // create a container for showing the contents
        VBox container = new VBox(10);
        container.setPadding(new Insets(15, 5, 5, 5));

        // create our GUI components, add them to this panel
        container.getChildren().add(createTitle());
        container.getChildren().add(createFormContent());

        // Error message area
        container.getChildren().add(createStatusLog("                                            "));

        getChildren().add(container);

        populateFields();
    }

    // --------------------------------------------------------------------------
    protected void populateFields() {
        getEntryTableModelValues();
    }

    // --------------------------------------------------------------------------
    protected void getEntryTableModelValues() {

        ObservableList<PatronTableModel> tableData = FXCollections.observableArrayList();
        try {
            PatronCollection patronList = (PatronCollection) myModel.getState("patronList");

            Vector entryList = (Vector) patronList.getState("patrons");
            Enumeration entries = entryList.elements();

            while (entries.hasMoreElements()) {
                Patron nextPatron = (Patron) entries.nextElement();
                Vector<String> view = nextPatron.getEntryListView();

                // add this list entry to the list
                PatronTableModel nextTableRowData = new PatronTableModel(view);
                tableData.add(nextTableRowData);

            }

            tableOfPatrons.setItems(tableData);
        } catch (Exception e) {// SQLException e) {
            // Need to handle this exception
        }
    }

    // Create the title container
    // -------------------------------------------------------------
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
    // -------------------------------------------------------------
    private VBox createFormContent() {
        VBox vbox = new VBox(10);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text prompt = new Text("LIST OF PATRONS");
        prompt.setWrappingWidth(785);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        tableOfPatrons = new TableView<>();
        tableOfPatrons.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        TableColumn patronIdColumn = new TableColumn("Patron ID");
        patronIdColumn.setStyle("-fx-alignment: CENTER;");
        patronIdColumn.setMinWidth(45);
        patronIdColumn.setCellValueFactory(
                new PropertyValueFactory<PatronTableModel, String>("patronId"));

        TableColumn nameColumn = new TableColumn("Name");
        nameColumn.setMinWidth(100);
        nameColumn.setStyle("-fx-alignment: CENTER;");
        nameColumn.setCellValueFactory(
                new PropertyValueFactory<PatronTableModel, String>("name"));

        TableColumn addressColumn = new TableColumn("Address");
        addressColumn.setMinWidth(150);
        addressColumn.setStyle("-fx-alignment: CENTER;");
        addressColumn.setCellValueFactory(
                new PropertyValueFactory<PatronTableModel, String>("address"));

        TableColumn cityColumn = new TableColumn("City");
        cityColumn.setMinWidth(75);
        cityColumn.setStyle("-fx-alignment: CENTER;");
        cityColumn.setCellValueFactory(
                new PropertyValueFactory<PatronTableModel, String>("city"));

        TableColumn stateCodeColumn = new TableColumn("State");
        stateCodeColumn.setMinWidth(75);
        stateCodeColumn.setStyle("-fx-alignment: CENTER;");
        stateCodeColumn.setCellValueFactory(
                new PropertyValueFactory<PatronTableModel, String>("stateCode"));

        TableColumn zipColumn = new TableColumn("ZIP");
        zipColumn.setMinWidth(40);
        zipColumn.setStyle("-fx-alignment: CENTER;");
        zipColumn.setCellValueFactory(
                new PropertyValueFactory<PatronTableModel, String>("zip"));

        TableColumn emailColumn = new TableColumn("E-Mail");
        emailColumn.setMinWidth(150);
        emailColumn.setStyle("-fx-alignment: CENTER;");
        emailColumn.setCellValueFactory(
                new PropertyValueFactory<PatronTableModel, String>("email"));

        TableColumn dobColumn = new TableColumn("Date of Birth");
        dobColumn.setMinWidth(40);
        dobColumn.setStyle("-fx-alignment: CENTER;");
        dobColumn.setCellValueFactory(
                new PropertyValueFactory<PatronTableModel, String>("dateOfBirth"));

        TableColumn statusColumn = new TableColumn("Status");
        statusColumn.setMinWidth(60);
        statusColumn.setStyle("-fx-alignment: CENTER;");
        statusColumn.setCellValueFactory(
                new PropertyValueFactory<PatronTableModel, String>("status"));

        tableOfPatrons.getColumns().addAll(patronIdColumn, nameColumn,
                addressColumn, cityColumn, stateCodeColumn, zipColumn,
                emailColumn, dobColumn, statusColumn);

        tableOfPatrons.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown() && event.getClickCount() >= 2) {
                processPatronSelected();
            }
        });
        // ScrollPane scrollPane = new ScrollPane();
        // scrollPane.setPrefSize(115, 150);
        // scrollPane.setContent(tableOfPatrons);

        submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            clearErrorMessage();
            // do the inquiry
            processPatronSelected();

        });

        cancelButton = new Button("Back");
        cancelButton.setOnAction(e -> {
            clearErrorMessage();
            myModel.stateChangeRequest("CancelSearchPatron", null);
        });

        HBox btnContainer = new HBox(100);
        btnContainer.setAlignment(Pos.CENTER);
        btnContainer.getChildren().add(submitButton);
        btnContainer.getChildren().add(cancelButton);

        tableOfPatrons.setPrefSize(535, 200);

        vbox.getChildren().add(grid);
        vbox.getChildren().add(tableOfPatrons);
        vbox.getChildren().add(btnContainer);

        return vbox;
    }

    public void updateState(String key, Object value) {
    }

    private void processPatronSelected() {
        PatronTableModel selectedItem = tableOfPatrons.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            String selectedPatronId = selectedItem.getPatronId();

            myModel.stateChangeRequest("PatronSelected", selectedPatronId);
        }
    }

    protected MessageView createStatusLog(String initialMessage) {
        statusLog = new MessageView(initialMessage);

        return statusLog;
    }

    /**
     * Display info message
     */
    public void displayMessage(String message) {
        statusLog.displayMessage(message);
    }

    /**
     * Clear error message
     */
    public void clearErrorMessage() {
        statusLog.clearErrorMessage();
    }
    /*
     * public void mouseClicked(MouseEvent click)
     * {
     * if(click.getClickCount() >= 2)
     * {
     * processPatronSelected();
     * }
     * }
     */

}