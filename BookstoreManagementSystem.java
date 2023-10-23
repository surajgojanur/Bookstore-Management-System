import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.ArrayList;

class Book {
    String title;
    String author;
    String publisher;
    double cost;

    public Book(String title, String author, String publisher, double cost) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.cost = cost;
    }
}

class Admin {
    ArrayList<Book> books = new ArrayList<>();

    public void addBook(String title, String author, String publisher, double cost) {
        Book book = new Book(title, author, publisher, cost);
        books.add(book);
        // Code to insert the book into the database should be added here (using SQL).
    }
}

public class BookstoreManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Admin admin = new Admin();

        // Database connection parameters
        String jdbcUrl = "jdbc:mysql://localhost:3306/your_database"; // Update with your database URL
        String jdbcUser = "your_username"; // Update with your database username
        String jdbcPassword = "your_password"; // Update with your database password

        // Initialize the database connection
        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);

            while (true) {
                System.out.println("1. Add Book");
                System.out.println("2. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        System.out.print("Enter book title: ");
                        String title = scanner.next();
                        System.out.print("Enter author: ");
                        String author = scanner.next();
                        System.out.print("Enter publisher: ");
                        String publisher = scanner.next();
                        System.out.print("Enter cost: ");
                        double cost = scanner.nextDouble();

                        // Add the book to the database
                        addBookToDatabase(connection, title, author, publisher, cost);

                        admin.addBook(title, author, publisher, cost);
                        System.out.println("Book added successfully.");
                        break;
                    case 2:
                        System.out.println("Exiting the system.");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to connect to the database.");
        }
    }

    // Method to insert a book into the database
    private static void addBookToDatabase(Connection connection, String title, String author, String publisher, double cost) throws SQLException {
        String insertQuery = "INSERT INTO books (title, author, publisher, cost) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, author);
            preparedStatement.setString(3, publisher);
            preparedStatement.setDouble(4, cost);
            preparedStatement.executeUpdate();
        }
    }
    // Created by Suraj
}
