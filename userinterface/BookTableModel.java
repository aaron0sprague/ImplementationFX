package userinterface;

import javafx.beans.property.SimpleStringProperty;

import java.util.Vector;

public class BookTableModel {
    private final SimpleStringProperty bookId;
    private final SimpleStringProperty author;
    private final SimpleStringProperty bookTitle;
    private final SimpleStringProperty pubYear;
    private final SimpleStringProperty status;

    BookTableModel(Vector<String> bookInfo) {
        bookId = new SimpleStringProperty(bookInfo.elementAt(0));
        author = new SimpleStringProperty(bookInfo.elementAt(1));
        bookTitle = new SimpleStringProperty(bookInfo.elementAt(2));
        pubYear = new SimpleStringProperty(bookInfo.elementAt(3));
        status = new SimpleStringProperty(bookInfo.elementAt(4));
    }

    public String getBookId() {
        return bookId.get();
    }

    public void setBookId(String id) {
        bookId.set(id);
    }

    public String getAuthor() {
        return author.get();
    }

    public void setAuthor(String bookAuthor) {
        author.set(bookAuthor);
    }

    public String getbookTitle() {
        return bookTitle.get();
    }

    public void setbookTitle(String bookTitle) {
        bookTitle.set(bookTitle);
    }

    public String getPubYear() {
        return pubYear.get();
    }

    public void setPubYear(String year) {
        pubYear.set(year);
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String dbStatus) {
        status.set(dbStatus);
    }
}
