// specify the package
package model;

// system imports

import event.Event;
import impresario.IModel;
import impresario.IView;
import impresario.ModelRegistry;
import javafx.scene.Scene;
import javafx.stage.Stage;
import userinterface.MainStageContainer;
import userinterface.View;
import userinterface.ViewFactory;
import userinterface.WindowPosition;

import java.util.Hashtable;
import java.util.Properties;

/**
 * The class containing the Teller for the ATM application
 */
public class Librarian implements IView, IModel
// This class implements all these interfaces (and does NOT extend 'EntityBase')
// because it does NOT play the role of accessing the back-end database tables.
// It only plays a front-end role. 'EntityBase' objects play both roles.
{
    private ModelRegistry myRegistry;

    // GUI Components
    private Hashtable<String, Scene> myViews;
    private Stage myStage;

    private String transactionErrorMessage = "";

    // constructor for this class
    public Librarian() {
        myStage = MainStageContainer.getInstance();
        myViews = new Hashtable<>();

        // STEP 3.1: Create the Registry object - if you inherit from
        // EntityBase, this is done for you. Otherwise, you do it yourself
        myRegistry = new ModelRegistry("Librarian");

        // STEP 3.2: Be sure to set the dependencies correctly
        setDependencies();

        // Set up the initial view
        createAndShowLibrarianView();
    }

    private void setDependencies() {
        Properties dependencies = new Properties();
        // dependencies.setProperty("NewBook", "TransactionError");
        // dependencies.setProperty("NewPatron", "TransactionError");
        // dependencies.setProperty("SearchBooks", "TransactionError");
        // dependencies.setProperty("SearchPatrons", "TransactionError");
        myRegistry.setDependencies(dependencies);
    }

    /**
     * Method called from client to get the value of a particular field
     * held by the objects encapsulated by this object.
     *
     * @param key Name of database column (field) for which the client wants the
     *            value
     * @return Value associated with the field
     */
    public Object getState(String key) {
        switch (key) {
            case "TransactionError":
                return transactionErrorMessage;
            default:
                return "";
        }
    }

    public void stateChangeRequest(String key, Object value) {
        // STEP 4: Write the sCR method component for the key you just set up
        // dependencies for
        switch (key) {
            case "NewBook":
                createNewBook();
                break;
            case "SearchBookView":
                createAndShowBookSearchView();
                break;
            case "SearchBook":
                searchBooks((String) value);
                break;
            case "CancelTransaction":
                createAndShowLibrarianView();
                break;
        }

        myRegistry.updateSubscribers(key, this);
    }

    /**
     * Called via the IView relationship
     */
    public void updateState(String key, Object value) {
        stateChangeRequest(key, value);
    }

    private void createNewBook() {
        Book newBook = new Book();
        newBook.subscribe("CancelTransaction", this);
        newBook.createAndShowBookView();
    }

    private void searchBooks(String bookTitle) {
        BookCollection bookList = new BookCollection();
        bookList.subscribe("CancelTransaction", this);
        bookList.subscribe("SearchBookView", this);
        bookList.createAndShowBookCollectionView();//ha ha funny

    }

    private void createNewPatron() {

    }

    private void searchPatron() {

    }

    private void createAndShowLibrarianView() {
        Scene currentScene = myViews.get("LibrarianView");

        if (currentScene == null) {
            // create our initial view
            View newView = ViewFactory.createView("LibrarianView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("LibrarianView", currentScene);
        }

        swapToView(currentScene);

    }

    private void createAndShowBookSearchView() {
        Scene currentScene = myViews.get("BookSearchView");

        if (currentScene == null) {
            // create our initial view
            View newView = ViewFactory.createView("BookSearchView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("BookSearchView", currentScene);
        }

        swapToView(currentScene);

    }

    /**
     * Register objects to receive state updates.
     */
    public void subscribe(String key, IView subscriber) {
        myRegistry.subscribe(key, subscriber);
    }

    /**
     * Unregister previously registered objects.
     */
    public void unSubscribe(String key, IView subscriber) {
        myRegistry.unSubscribe(key, subscriber);
    }

    public void swapToView(Scene newScene) {
        if (newScene == null) {
            System.out.println("Librarian.swapToView(): Missing view for display");
            new Event(Event.getLeafLevelClassName(this), "swapToView",
                    "Missing view for display ", Event.ERROR);
            return;
        }
        myStage.setScene(newScene);
        myStage.sizeToScene();

        // Place in center
        WindowPosition.placeCenter(myStage);
    }
}
