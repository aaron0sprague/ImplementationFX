package model;

import impresario.IView;
/* import javafx.scene.Scene;
import userinterface.View;
import userinterface.ViewFactory;
 */
import java.util.Properties;
import java.util.Vector;

// constructor
public class BookCollection extends EntityBase {
    private static final String myTableName = "Book";
    private Vector<Book> bookList;
    // class constructor
    public BookCollection(){
        super(myTableName);
        bookList = new Vector<>();
    }
    public Vector findBooksOlderThanDate(String year){
        String query = "SELECT * FROM "+myTableName+" WHERE (pubYear < "+year+") ORDER BY author ASC";
        return doQuery(query);
    }
    public Vector findBooksNewerThanDate(String year){
        String query = "SELECT * FROM "+myTableName+" WHERE (pubYear > "+year+") ORDER BY author ASC";
        return doQuery(query);
    }
    public Vector findBooksWithTitleLike(String bookTitle) {
        String query = "SELECT * FROM " + myTableName + " WHERE bookTitle LIKE '%" + bookTitle + "%' ORDER BY author ASC";
        return doQuery(query);
    }
    public Vector findBooksWithAuthorLike(String author) {
        String query = "SELECT * FROM " + myTableName + " WHERE author LIKE '%" + author + "%' ORDER BY author ASC";
        return doQuery(query);
    }
    private Vector doQuery(String query) {
        try {
            Vector allDataRetrieved = getSelectQueryResult(query);
            if (allDataRetrieved != null) {
                bookList = new Vector<>();
                for (int index = 0; index < allDataRetrieved.size(); index++) {
                    Properties data = (Properties) allDataRetrieved.elementAt(index);
                    Book book = new Book(data);
                    if (book != null) {
                        bookList.add(book);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        return bookList;
    }

    private void setDependencies() {
        Properties dependencies = new Properties();
        dependencies.setProperty("CancelSearchBook", "SearchBookView");

        myRegistry.setDependencies(dependencies);
    }
    protected void initializeSchema(String tableName) {
        if (mySchema == null) {
            mySchema = getSchemaInfo(tableName);
        }
    }
    
    public Object getState(String key) {
        if (key.equals("Accounts"))
            return bookList;
        else if (key.equals("BookList"))
            return this;
        return null;
    }

    // ----------------------------------------------------------------
    public void stateChangeRequest(String key, Object value) {

        myRegistry.updateSubscribers(key, this);
    }
}