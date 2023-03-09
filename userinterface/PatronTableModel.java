package userinterface;

import javafx.beans.property.SimpleStringProperty;

import java.util.Vector;

public class PatronTableModel {
    private final SimpleStringProperty patronId;
    private final SimpleStringProperty name;
    private final SimpleStringProperty address;
    private final SimpleStringProperty city;
    private final SimpleStringProperty stateCode;
    private final SimpleStringProperty zip;
    private final SimpleStringProperty email;
    private final SimpleStringProperty dateOfBirth;
    private final SimpleStringProperty status;

    PatronTableModel(Vector<String> bookInfo) {
        patronId = new SimpleStringProperty(bookInfo.elementAt(0));
        name = new SimpleStringProperty(bookInfo.elementAt(1));
        address = new SimpleStringProperty(bookInfo.elementAt(2));
        city = new SimpleStringProperty(bookInfo.elementAt(3));
        stateCode = new SimpleStringProperty(bookInfo.elementAt(4));
        zip = new SimpleStringProperty(bookInfo.elementAt(5));
        email = new SimpleStringProperty(bookInfo.elementAt(6));
        dateOfBirth = new SimpleStringProperty(bookInfo.elementAt(7));
        status = new SimpleStringProperty(bookInfo.elementAt(8));
    }

    public String getPatronId() {
        return patronId.get();
    }

    public void setPatronId(String id) {
        patronId.set(id);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String bookAuthor) {
        name.set(bookAuthor);
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String bookTitle) {
        address.set(bookTitle);
    }

    public String getCity() {
        return city.get();
    }

    public void setCity(String year) {
        city.set(year);
    }

    public String getStateCode() {
        return stateCode.get();
    }

    public void setStateCode(String id) {
        stateCode.set(id);
    }

    public String getZip() {
        return zip.get();
    }

    public void setZip(String bookAuthor) {
        zip.set(bookAuthor);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String bookTitle) {
        email.set(bookTitle);
    }

    public String getDateOfBirth() {
        return dateOfBirth.get();
    }

    public void setDateOfBirth(String year) {
        dateOfBirth.set(year);
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String dbStatus) {
        status.set(dbStatus);
    }
}