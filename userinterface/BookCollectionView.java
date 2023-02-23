package userinterface;

// system imports

import impresario.IModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.Book;
import model.BookCollection;

import java.util.Enumeration;
import java.util.Vector;

// project imports

//==============================================================================
public class BookCollectionView extends View {
    protected TableView<BookTableModel> tableOfBooks;
    protected Button cancelButton;
    protected Button submitButton;

    protected MessageView statusLog;


    //--------------------------------------------------------------------------
    public BookCollectionView(IModel wsc) {
        super(wsc, "BookCollectionView");

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

    //--------------------------------------------------------------------------
    protected void populateFields() {
        getEntryTableModelValues();
    }

    //--------------------------------------------------------------------------
    protected void getEntryTableModelValues() {

        ObservableList<BookTableModel> tableData = FXCollections.observableArrayList();
        try {
            BookCollection bookList = (BookCollection) myModel.getState("bookList");

            Vector entryList = (Vector) bookList.getState("books");
            Enumeration entries = entryList.elements();

            while (entries.hasMoreElements()) {
                Book nextBook = (Book) entries.nextElement();
                Vector<String> view = nextBook.getEntryListView();

                // add this list entry to the list
                BookTableModel nextTableRowData = new BookTableModel(view);
                tableData.add(nextTableRowData);

            }

            tableOfBooks.setItems(tableData);
        } catch (Exception e) {//SQLException e) {
            // Need to handle this exception
        }
    }

    // Create the title container
    //-------------------------------------------------------------
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
    //-------------------------------------------------------------
    private VBox createFormContent() {
        VBox vbox = new VBox(10);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text prompt = new Text("LIST OF BOOKS");
        prompt.setWrappingWidth(350);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        tableOfBooks = new TableView<>();
        tableOfBooks.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        TableColumn bookIdColumn = new TableColumn("BookId");
        bookIdColumn.setMinWidth(45);
        bookIdColumn.setCellValueFactory(
                new PropertyValueFactory<BookTableModel, String>("bookId"));

        TableColumn authorColumn = new TableColumn("Author");
        authorColumn.setMinWidth(100);
        authorColumn.setCellValueFactory(
                new PropertyValueFactory<BookTableModel, String>("author"));

        TableColumn titleColumn = new TableColumn("Title");
        titleColumn.setMinWidth(200);
        titleColumn.setCellValueFactory(
                new PropertyValueFactory<BookTableModel, String>("bookTitle"));

        TableColumn pubYearColumn = new TableColumn("Publication\nYear");
        pubYearColumn.setMinWidth(100);
        pubYearColumn.setCellValueFactory(
                new PropertyValueFactory<BookTableModel, String>("pubYear"));

        TableColumn statusColumn = new TableColumn("Status");
        statusColumn.setMinWidth(60);
        statusColumn.setCellValueFactory(
                new PropertyValueFactory<BookTableModel, String>("status"));

        tableOfBooks.getColumns().addAll(bookIdColumn, authorColumn,
                titleColumn, pubYearColumn, statusColumn);

        tableOfBooks.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown() && event.getClickCount() >= 2) {
                processBookSelected();
            }
        });
//        ScrollPane scrollPane = new ScrollPane();
//        scrollPane.setPrefSize(115, 150);
//        scrollPane.setContent(tableOfBooks);

        submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            clearErrorMessage();
            // do the inquiry
            processBookSelected();

        });

        cancelButton = new Button("Back");
        cancelButton.setOnAction(e -> {
            clearErrorMessage();
            myModel.stateChangeRequest("CancelSearchBook", null);
        });

        HBox btnContainer = new HBox(100);
        btnContainer.setAlignment(Pos.CENTER);
        btnContainer.getChildren().add(submitButton);
        btnContainer.getChildren().add(cancelButton);

        tableOfBooks.setPrefSize(535,200);

        vbox.getChildren().add(grid);
        vbox.getChildren().add(tableOfBooks);
        vbox.getChildren().add(btnContainer);

        return vbox;
    }


    public void updateState(String key, Object value) {
    }

    private void processBookSelected() {
        BookTableModel selectedItem = tableOfBooks.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            String selectedBookId = selectedItem.getBookId();

            myModel.stateChangeRequest("BookSelected", selectedBookId);
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
	public void mouseClicked(MouseEvent click)
	{
		if(click.getClickCount() >= 2)
		{
			processBookSelected();
		}
	}
   */

}
