// specify the package
package model;

// system imports

import exception.InvalidPrimaryKeyException;
import impresario.IView;
import javafx.scene.Scene;
import userinterface.View;
import userinterface.ViewFactory;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

// project imports

/**
 * The class containing the Book for the ATM application
 */
//==============================================================
public class Book extends EntityBase implements IView {
    private static final String myTableName = "Book";
    public String[] fields = {"author", "title", "pubYear", "status"};

    // GUI Components
    private String updateStatusMessage = "";

    // constructor for this class
    public Book(String bookId)
            throws InvalidPrimaryKeyException {
        super(myTableName);

        setDependencies();
        String query = String.format("SELECT * FROM %s WHERE (bookId = %s)",
                myTableName, bookId);

        Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

        // Book must exist
        if (allDataRetrieved != null) {
            int size = allDataRetrieved.size();

            // There should be EXACTLY one book. Else error
            if (size != 1) {
                throw new InvalidPrimaryKeyException("Multiple books matching id : "
                        + bookId + " found.");
            } else {
                // copy all the retrieved data into persistent state
                Properties retrievedBookData = allDataRetrieved.elementAt(0);
                persistentState = new Properties();

                Enumeration allKeys = retrievedBookData.propertyNames();
                while (allKeys.hasMoreElements()) {
                    String nextKey = (String) allKeys.nextElement();
                    String nextValue = retrievedBookData.getProperty(nextKey);

                    if (nextValue != null) {
                        persistentState.setProperty(nextKey, nextValue);
                    }
                }

            }
        }
        // If no book found for this user name, throw an exception
        else {
            throw new InvalidPrimaryKeyException("No book matching id : "
                    + bookId + " found.");
        }
    }

    // Can also be used to create a NEW Book (if the system it is part of
    // allows for a new book to be set up)
    public Book(Properties bookInfo) {
        super(myTableName);

        setDependencies();
        persistentState = new Properties();
        Enumeration allKeys = bookInfo.propertyNames();
        while (allKeys.hasMoreElements()) {
            String nextKey = (String) allKeys.nextElement();
            String nextValue = bookInfo.getProperty(nextKey);

            if (nextValue != null) {
                persistentState.setProperty(nextKey, nextValue);
            }
        }
    }

    public Book() {
        super(myTableName);
        setDependencies();
        persistentState = new Properties();
    }

    private void setDependencies() {
        Properties dependencies = new Properties();
        dependencies.setProperty("CancelAddBook", "CancelTransaction");

        myRegistry.setDependencies(dependencies);
    }

    public Object getState(String key) {
        if (key.equals("UpdateStatusMessage"))
            return updateStatusMessage;

        return persistentState.getProperty(key);
    }

    public void stateChangeRequest(String key, Object value) {

        if (key.equals("InsertBook")) {
            processNewBook((Properties) value);
        }

        myRegistry.updateSubscribers(key, this);
    }

    /**
     * Called via the IView relationship
     */
    public void updateState(String key, Object value) {
        stateChangeRequest(key, value);
    }

    public void update() {
        updateStateInDatabase();
    }

    private void updateStateInDatabase() {
        try {
            if (persistentState.getProperty("bookId") != null) {
                Properties whereClause = new Properties();
                whereClause.setProperty("bookId",
                        persistentState.getProperty("bookId"));
                updatePersistentState(mySchema, persistentState, whereClause);
                updateStatusMessage = "Book data for book ID : "
                        + persistentState.getProperty("bookId")
                        + " updated successfully in database!";
            } else {
                Integer bookId =
                        insertAutoIncrementalPersistentState(mySchema, persistentState);
                persistentState.setProperty("bookId", "" + bookId);
                updateStatusMessage = "Book data for new book : "
                        + persistentState.getProperty("bookId")
                        + "installed successfully in database!";
            }
        } catch (SQLException ex) {
            updateStatusMessage = "Error inserting book data into database!";
        }
    }
    protected void createAndShowBookView()
    {
        // create our new view
        View newView = ViewFactory.createView("BookView", this);
        Scene newScene = new Scene(newView);

        // make the view visible by installing it into the frame
        swapToView(newScene);
    }

    private void processNewBook(Properties bookInfo) {
        persistentState = new Properties();
        Enumeration allKeys = bookInfo.propertyNames();
        while (allKeys.hasMoreElements()) {
            String nextKey = (String) allKeys.nextElement();
            String nextValue = bookInfo.getProperty(nextKey);

            if (nextValue != null) {
                persistentState.setProperty(nextKey, nextValue);
            }
        }
        updateStateInDatabase();
    }

    /**
     * This method is needed solely to enable the Book information to be displayable in a table
     */
    public Vector<String> getEntryListView() {
        Vector<String> v = new Vector<>();

        v.addElement(persistentState.getProperty("bookId"));
        v.addElement(persistentState.getProperty("author"));
        v.addElement(persistentState.getProperty("title"));
        v.addElement(persistentState.getProperty("pubYear"));
        v.addElement(persistentState.getProperty("status"));

        return v;
    }

    protected void initializeSchema(String tableName) {
        if (mySchema == null) {
            mySchema = getSchemaInfo(tableName);
        }
    }
}

