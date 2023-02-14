import model.Book;
import model.BookCollection;
import model.Patron;
import model.PatronCollection;

import java.util.Properties;
import java.util.Scanner;
import java.util.Vector;

public class Assign1 {
    private static Scanner scan = new Scanner(System.in).useDelimiter("(\\n)");

    public static void main(String args[]) {
        initialPrompt();
        mainMenu();
    }

    private static void initialPrompt() {
        System.out.println("+==========================================+");
        System.out.println("| CSC 429 - Assignment 1 - Pair Programming |");
        System.out.println("| Authors: Cholla Grygotis & Erik Nystrom  |");
        System.out.println("+==========================================+");
        System.out.println("+==========================================+");
    }

    private static void mainMenu() {
        System.out.println("+==========================================+");
        System.out.println("| Enter option to select. Then press ENTER |");
        System.out.println("| 1. Create a new Patron or Book           |");
        System.out.println("| 2. Pull up an existing entry             |");
        System.out.println("| 3. Run Test Script                       |");
        System.out.println("| 0. Exit Program                          |");
        System.out.println("+==========================================+");
        System.out.print("Option: ");
        // int i = scan.nextInt();
        String option = System.console().readLine();
        switch (option) {
            case "1":
                createMenu();
                break;
            case "2":
                findMenu();
                break;
            case "3":
                runTestScript();
                break;
            case "0":
                System.exit(0);
            default:
                mainMenu();
                break;
        }
    }

    private static void createMenu() {
        System.out.println("+==========================================+");
        System.out.println("| Enter option to select. Then press ENTER |");
        System.out.println("| 1. Create a new Book                     |");
        System.out.println("| 2. Create a new Patron                   |");
        System.out.println("| 5. Return to previous menu               |");
        System.out.println("+==========================================+");
        System.out.print("Option: ");
        String option = System.console().readLine();
        ;
        System.out.println();

        switch (option) {
            case "1":
                createBook();
                break;
            case "2":
                createPatron();
                break;
            case "5":
                mainMenu();
                break;
            default:
                createMenu();
                break;
        }
    }

    private static void findMenu() {
        System.out.println("+==========================================+");
        System.out.println("| Enter option to select. Then press ENTER |");
        System.out.println("| 1. Find a Book                           |");
        System.out.println("| 2. Find a Patron                         |");
        System.out.println("| 5. Return to previous menu               |");
        System.out.println("+==========================================+");
        System.out.print("Option: ");
        String option = System.console().readLine();
        System.out.println();

        switch (option) {
            case "1":
                findBook();
                break;
            case "2":
                findPatron();
                break;
            case "5":
                mainMenu();
                break;
            default:
                findMenu();
                break;
        }
    }

    private static void findBook() {
        System.out.println("+==========================================+");
        System.out.println("| Enter option to select. Then press ENTER |");
        System.out.println("| 1. Find all Books older than a date      |");
        System.out.println("| 2. Find all Books newer than a date      |");
        System.out.println("| 3. Find all Books with a title like      |");
        System.out.println("| 4. Find all Books with a author like     |");
        System.out.println("| 5. Return to previous menu               |");
        System.out.println("+==========================================+");
        System.out.print("Option: ");
        String option = System.console().readLine();
        System.out.println();
        switch (option) {
            case "1":
                findBooksOlderThan();
                break;
            case "2":
                findBooksNewerThan();
                break;
            case "3":
                findBooksWithTitleLike();
                break;
            case "4":
                findBooksWithAuthorLike();
                break;
            case "5":
                findMenu();
                break;
            default:
                findBook();
                break;
        }
    }

    private static void findPatron() {
        System.out.println("+==========================================+");
        System.out.println("| Enter option to select. Then press ENTER |");
        System.out.println("| 1. Find all Patrons older than a date    |");
        System.out.println("| 2. Find all Patrons younger than a date  |");
        System.out.println("| 3. Find all Patrons in a ZIP Code        |");
        System.out.println("| 4. Find all Patrons with names like      |");
        System.out.println("| 5. Return to previous menu               |");
        System.out.println("+==========================================+");
        System.out.print("Option: ");
        String option = System.console().readLine();
        System.out.println();

        switch (option) {
            case "1":
                findPatronsOlderThan();
                break;
            case "2":
                findPatronsYoungerThan();
                break;
            case "3":
                findPatronsInZip();
                break;
            case "4":
                findPatronsNameLike();
                break;
            case "5":
                findMenu();
                break;
            default:
                findPatron();
                break;
        }
    }

    private static void continueMenu() {
        System.out.println("\n+==========================================+");
        System.out.println("| Return to main menu?                     |");
        System.out.println("| Any key to continue, N/n to exit         |");
        System.out.println("+==========================================+");
        String s = System.console().readLine();

        if (s.equals("N") || s.equals("n")) {
            System.exit(0);
        }
        System.out.println();
        mainMenu();
    }

    // Find book methods
    private static void findBooksOlderThan() {
        String input;
        System.out.print("Date to search before: ");
        input = System.console().readLine();
        System.out.println("Results: ");
        BookCollection bookCol = new BookCollection();
        Vector<Book> books = bookCol.findBooksOlderThanDate(input);
        for (Book book : books) {
            System.out.println(book.getEntryListView());
        }
        continueMenu();
    }

    private static void findBooksNewerThan() {
        String input;
        System.out.print("Date to search after: ");
        input = System.console().readLine();
        System.out.println("Results: ");
        BookCollection bookCol = new BookCollection();
        Vector<Book> books = bookCol.findBooksNewerThanDate(input);
        for (Book book : books) {
            System.out.println(book.getEntryListView());
        }
        continueMenu();
    }

    private static void findBooksWithTitleLike() {
        String input;
        System.out.print("Book Title: ");
        input = System.console().readLine();
        System.out.println("Results: ");
        BookCollection bookCol = new BookCollection();
        Vector<Book> books = bookCol.findBooksWithTitleLike(input);
        for (Book book : books) {
            System.out.println(book.getEntryListView());
        }
        continueMenu();
    }

    private static void findBooksWithAuthorLike() {
        String input;
        System.out.print("Author's Name: ");
        input = System.console().readLine();
        System.out.println("Results: ");
        BookCollection bookCol = new BookCollection();
        Vector<Book> books = bookCol.findBooksWithAuthorLike(input);
        for (Book book : books) {
            System.out.println(book.getEntryListView());
        }
        continueMenu();
    }

    // Find patron methods
    private static void findPatronsOlderThan() {
        String input;
        System.out.print("Date to search before: ");
        input = System.console().readLine();
        System.out.println("Results: ");
        PatronCollection patronCol = new PatronCollection();
        Vector<Patron> patrons = patronCol.findPatronsOlderThan(input);
        for (Patron patron : patrons) {
            System.out.println(patron.getEntryListView());
        }
        continueMenu();

    }

    private static void findPatronsYoungerThan() {
        String input;
        System.out.print("Date to search after: ");
        input = System.console().readLine();
        System.out.println("Results: ");
        PatronCollection patronCol = new PatronCollection();
        Vector<Patron> patrons = patronCol.findPatronsYoungerThan(input);
        for (Patron patron : patrons) {
            System.out.println(patron.getEntryListView());
        }
        continueMenu();

    }

    private static void findPatronsInZip() {
        String input;
        System.out.print("ZIP Code: ");
        input = System.console().readLine();
        System.out.println("Results: ");
        PatronCollection patronCol = new PatronCollection();
        Vector<Patron> patrons = patronCol.findPatronsAtZipCode(input);
        for (Patron patron : patrons) {
            System.out.println(patron.getEntryListView());
        }
        continueMenu();

    }

    private static void findPatronsNameLike() {
        String input;
        System.out.print("Patron name: ");
        input = System.console().readLine();
        System.out.println("Results: ");
        PatronCollection patronCol = new PatronCollection();
        Vector<Patron> patrons = patronCol.findPatronsWithNameLike(input);
        for (Patron patron : patrons) {
            System.out.println(patron.getEntryListView());
        }
        continueMenu();

    }

    private static void createBook() {
        System.out.println();
        String input = "";
        Properties props = new Properties();
        System.out.print("Book Author: ");
        input = System.console().readLine();
        props.setProperty("author", input);
        System.out.print("Book Title: ");
        input = System.console().readLine();
        props.setProperty("title", input);
        System.out.print("Publication year (yyyy): ");
        input = System.console().readLine();
        props.setProperty("pubYear", input);
        System.out.print("Status (active/inactive): ");
        input = System.console().readLine();
        props.setProperty("status", input);
        Book newBook = new Book(props);
        newBook.update();
        System.out.println("New book added!");
        continueMenu();

    }

    private static void createPatron() {
        String input = "";
        Properties props = new Properties();
        System.out.println("Name of Patron: ");
        input = System.console().readLine();
        props.setProperty("name", input);
        System.out.println("Address: ");
        input = System.console().readLine();
        props.setProperty("address", input);
        System.out.println("City: ");
        input = System.console().readLine();
        props.setProperty("city", input);
        System.out.println("State code: ");
        input = System.console().readLine();
        props.setProperty("stateCode", input);
        System.out.println("Zip code: ");
        input = System.console().readLine();
        props.setProperty("zip", input);
        System.out.println("Email Address: ");
        input = System.console().readLine();
        props.setProperty("email", input);
        System.out.println("Date of Birth(yyyy-mm-dd): ");
        input = System.console().readLine();
        props.setProperty("dateOfBirth", input);
        System.out.print("Status (active/inactive): ");
        input = System.console().readLine();
        props.setProperty("status", input);
        Patron newPatron = new Patron(props);
        newPatron.update();
        System.out.println("New patron added!");
        continueMenu();

    }

    // Test script to run the various commands without manually typing all options
    private static void runTestScript() {

        System.out.println("Test Script initiate");
        for (int i = 1; i < 20; i++) {
            createBook(i);
            createPatron(i);
        }
        System.out.println("** Find books newer than 1970 **");
        findBooksNewerThan("1970");
        System.out.println("** Find books older than 1970 **");
        findBooksOlderThan("1970");
        continueMenu();
    }

    // Create book and patron method for testing
    private static void createBook(int count) {
        Properties props = new Properties();
        props.setProperty("author", "Name " + count);
        props.setProperty("title", "Title " + count);
        int year = 1950 + count * 3;
        props.setProperty("pubYear", String.valueOf(year));
        props.setProperty("status", "active");
        Book newBook = new Book(props);
        newBook.update();
    }

    private static void createPatron(int count) {
        Properties props = new Properties();
        props.setProperty("name", "FName LName" + count);
        int address = 1 + count;
        props.setProperty("address", String.valueOf(address) + " Evergreen Terrace");
        props.setProperty("city", "City" + count);
        props.setProperty("stateCode", "NY");
        int zip = 14400 + count;
        props.setProperty("zip", String.valueOf(zip));
        props.setProperty("email", "email" + count + "@gmail.com");
        int year = 1950 + count * 2;
        props.setProperty("dateOfBirth", String.valueOf(year) + "-01-01");
        props.setProperty("status", "active");
        Patron newPatron = new Patron(props);
        newPatron.update();
    }

    // Find book methods using input from method
    private static void findBooksOlderThan(String input) {
        System.out.println("Date to search before: " + input);
        System.out.println("Results: ");
        BookCollection bookCol = new BookCollection();
        Vector<Book> books = bookCol.findBooksOlderThanDate(input);
        for (Book book : books) {
            System.out.println(book.getEntryListView());
        }
    }

    private static void findBooksNewerThan(String input) {
        System.out.println("Date to search after: " + input);
        System.out.println("Results: ");
        BookCollection bookCol = new BookCollection();
        Vector<Book> books = bookCol.findBooksNewerThanDate(input);
        for (Book book : books) {
            System.out.println(book.getEntryListView());
        }
    }

    private static void findBooksWithTitleLike(String input) {
        System.out.print("Book Title: " + input);
        System.out.println("Results: ");
        BookCollection bookCol = new BookCollection();
        Vector<Book> books = bookCol.findBooksWithTitleLike(input);
        for (Book book : books) {
            System.out.println(book.getEntryListView());
        }
    }

    private static void findBooksWithAuthorLike(String input) {
        System.out.println("Author's Name: " + input);
        System.out.println("Results: ");
        BookCollection bookCol = new BookCollection();
        Vector<Book> books = bookCol.findBooksWithAuthorLike(input);
        for (Book book : books) {
            System.out.println(book.getEntryListView());
        }
    }

    // Find patron methods using input from method
    private static void findPatronsOlderThan(String input) {
        System.out.println("Date to search before: " + input);
        System.out.println("Results: ");
        PatronCollection patronCol = new PatronCollection();
        Vector<Patron> patrons = patronCol.findPatronsOlderThan(input);
        for (Patron patron : patrons) {
            System.out.println(patron.getEntryListView());
        }
    }

    private static void findPatronsYoungerThan(String input) {
        System.out.println("Date to search after: " + input);
        System.out.println("Results: ");
        PatronCollection patronCol = new PatronCollection();
        Vector<Patron> patrons = patronCol.findPatronsYoungerThan(input);
        for (Patron patron : patrons) {
            System.out.println(patron.getEntryListView());
        }
    }

    private static void findPatronsInZip(String input) {
        System.out.println("ZIP Code: " + input);
        System.out.println("Results: ");
        PatronCollection patronCol = new PatronCollection();
        Vector<Patron> patrons = patronCol.findPatronsAtZipCode(input);
        for (Patron patron : patrons) {
            System.out.println(patron.getEntryListView());
        }
    }

    private static void findPatronsNameLike(String input) {
        System.out.println("Patron name: " + input);
        System.out.println("Results: ");
        PatronCollection patronCol = new PatronCollection();
        Vector<Patron> patrons = patronCol.findPatronsWithNameLike(input);
        for (Patron patron : patrons) {
            System.out.println(patron.getEntryListView());
        }
    }
}
