package model;

public class Book {
    /**
     * @param args
     */
    public static void main(String []args){
        Integer id = 0;
        char title 0;

    }
    public Book(String bookId)
            throws InvalidPrimaryKeyException {
        super(myTableName);

        setDependencies();
        String query = String.format("SELECT * FROM %s WHERE (bookId = %s)",
                myTableName, bookId);

        Vector<Properties> allDataRetrieved = getSelectQueryResult(query);
    public static int bookID(int id){
        
        return id;
    }   
    public static char bookTitle(char title){
        
        return title;
    } 
    public static char author(char auth){
        
        return auth;
    }
    public static char pubYear(char year){
       
        return year;
    }
    public static char status(char stat){
        
        return stat;
    }
}
