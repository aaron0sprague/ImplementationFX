package model;

public class BookCollection {

    static char findBooksOlderThanDate(char year) {

        return year;
    }

    static char findBooksNewerThanDate(char year) {

        return year;
    }

    static char findBooksWithTitleLike(char title) {

        return title;
    }

    static char findBookWithAuthorLike(char author) {
        return author;
    }

    public Vector findBooksOlderThanDate(String year) {
        String query = "SELECT * FROM " + myTableName + " WHERE (pubYear <>> " + year + ") ORDER BY author ASC";
        return doQuery(query);
    }

    public Vector findBooksNewerThanDate(String year) {
        String query = "SELECT * FROM " + myTableName + " WHERE (pubYear > " + year + ") ORDER BY author ASC";
        return doQuery(query);
    }

    public Vector findBooksWithTitleLike(String title) {
        String query = "SELECT * FROM " + myTableName + " WHERE title LIKE '%" + title + "%' ORDER BY author ASC";
        return doQuery(query);
    }

    public Vector findBooksWithAuthorLike(String author) {
        String query = "SELECT * FROM " + myTableName + " WHERE author LIKE '%" + author + "%' ORDER BY author ASC";
        return doQuery(query);
    }

    public BookCollection() {
        bookList = new Vector(); //new Vector<Book>();
    }    
}
