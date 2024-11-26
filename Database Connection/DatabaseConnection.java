package DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import models.Administrator;
import models.Book;
import models.Librarian;
import models.Request;
import models.Setting;
import models.Student;
import models.User;

public class DatabaseConnection {

    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USERNAME = "system";
    private static final String PASSWORD = "1234";

    private static DatabaseConnection handler = null;
    public static Connection con = null;
    private Statement stmt = null;

    private DatabaseConnection() {
        createConnection();
    }

    public static DatabaseConnection getInstance() {
        if (handler == null) {
            handler = new DatabaseConnection();
        }
        return handler;
    }

    public void createConnection() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Database connection established successfully!");

        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

//----------------------------------------------------------------------------------------------------------
    //Sysem mehods:
    public List<Book> displayAllBooks() {
        List<Book> books = new ArrayList<>();

        String sql = "SELECT * FROM Books";

        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String isbn = rs.getString("book_isbn");
                String title = rs.getString("book_title");
                String author = rs.getString("book_author");
                String availability = rs.getString("book_availability");
                boolean isAvailable = "Y".equals(availability);

                Book book = new Book(isbn, title, author, isAvailable);
                books.add(book);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return books;
    }

    public boolean addUser(String user_id, String user_name, String user_role) {
        String sql = "INSERT INTO UsersTable (user_id, user_name, user_role) VALUES ('" + user_id + "', '" + user_name + "', '" + user_role + "')";

        try {
            Statement stmt = con.createStatement();
            int rowsAffected = stmt.executeUpdate(sql);
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("error adding user in addUser method" + e.getMessage());
            return false;
        }
    }

//----------------------------------------------------------------------------------------------------------
    //Admin methods:
    public List<User> displayAllUsers() {
        List<User> users = new ArrayList<>();

        String sql = "SELECT * FROM UsersTable";

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {

                String userId = rs.getString("user_id");
                String username = rs.getString("user_name");
                String role = rs.getString("user_role");

                if (role.equals("Administrator")) {
                    User user = new Administrator(userId, username);
                    users.add(user);
                } else if (role.equals("Librarian")) {
                    User user = new Librarian(userId, username);
                    users.add(user);
                } else if (role.equals("Student")) {
                    User user = new Student(userId, username);
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }

    public boolean deleteUser(String user_id) {
        String sql = "DELETE FROM UsersTable WHERE user_id = '" + user_id + "'";
        try {
            stmt = con.createStatement();
            int rowsAffected = stmt.executeUpdate(sql);
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("error deleteing user in deleteUser: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean editUser(String user_name, String user_role, String user_id, String oldId) {
        String sql = "UPDATE UsersTable SET user_id = '" + user_id + "', user_name = '" + user_name + "', user_role = '" + user_role + "' WHERE user_id = '" + oldId + "'";

        try {
            stmt = con.createStatement();
            int rowsAffected = stmt.executeUpdate(sql);
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("error updating user in updateUser: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<Setting> disaplyAllSettings() {
        List<Setting> settings = new ArrayList<>();

        String sql = "SELECT * FROM SettingsTable";

        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String Key = rs.getString("setting_key");
                double Value = rs.getDouble("setting_value");
                String Description = rs.getString("setting_description");

                Setting setting = new Setting(Key, Value, Description);
                settings.add(setting);

            }
        } catch (SQLException e) {
            System.out.println("error displaying all settings in disaplyAllSettings" + e.getMessage());
            e.printStackTrace();
        }
        return settings;
    }

//----------------------------------------------------------------------------------------------------------
    //Librarian methods:
    public List<Request> displayAllRequests() {
        List<Request> requests = new ArrayList<>();

        String sql = "SELECT * FROM Requests";

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String userId = rs.getString("user_id");
                String bookIsbn = rs.getString("book_isbn");

                User user = getUserById(userId);
                Book book = getBookByIsbn(bookIsbn);

                if (user != null && book != null) {
                    Request request = new Request(
                            rs.getInt("request_id"),
                            user,
                            book,
                            rs.getDate("borrow_date").toLocalDate(),
                            rs.getDate("return_date").toLocalDate(),
                            rs.getString("request_status")
                    );
                    requests.add(request);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error displaying all requests in displayAllRequests: " + e.getMessage());
        }
        return requests;
    }

    public void acceptRequest(int requestId, String bookIsbn) {
        String updateStatusSql = "UPDATE Requests SET request_status = 'accepted' WHERE request_id = " + requestId;
        String updateAvailabilitySql = "UPDATE Books SET book_availability = 'N' WHERE book_isbn = '" + bookIsbn + "'";

        try {
            Statement stmt = con.createStatement();

            stmt.executeUpdate(updateStatusSql);
            stmt.executeUpdate(updateAvailabilitySql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void rejectRequest(int requestId) {
        String updateStatusSql = "UPDATE Requests SET request_status = 'rejected' WHERE request_id = " + requestId;

        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(updateStatusSql);

        } catch (SQLException e) {
            System.out.println("Error rejecting request: " + e.getMessage());
        }
    }

    public boolean addBook(String isbn, String title, String author, boolean availability) {
        String sql = "INSERT INTO Books (book_isbn, book_title, book_author, book_availability) VALUES ('"
                + isbn + "', '" + title + "', '" + author + "', '"
                + (availability ? "Y" : "N") + "')";

        try {
            Statement stmt = con.createStatement();
            int rowsAffected = stmt.executeUpdate(sql);
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("error adding book in addBook method" + e.getMessage());
            return false;
        }
    }

    public boolean editBook(String isbn, String title, String author, String oldISBN) {
        String sql = "UPDATE Books SET book_isbn = '" + isbn + "', book_title = '" + title
                + "', book_author = '" + author + "' WHERE book_isbn = '" + oldISBN + "'";

        try {
            Statement stmt = con.createStatement();
            int rowsAffected = stmt.executeUpdate(sql);
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("error editing book in edit book" + e.getMessage());
            return false;
        }
    }

    public boolean deleteBook(String isbn) {
        String sql = "DELETE FROM Books WHERE book_isbn = '" + isbn + "'";

        try {
            Statement stmt = con.createStatement();
            int rowsAffected = stmt.executeUpdate(sql);
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("error deleting book in deleteBook" + e.getMessage());
            return false;
        }
    }

    public List<Request> displayOverdueBorrows() {
        List<Request> overdue = new ArrayList<>();

        String sql = "SELECT * FROM Requests WHERE request_status = 'accepted'";

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                LocalDate returnDate = rs.getDate("return_date").toLocalDate();
                LocalDate currentDate = LocalDate.now();

                // Check if the return date was yesterday
                if (currentDate.isAfter(returnDate)) {
                    String userId = rs.getString("user_id");
                    String bookIsbn = rs.getString("book_isbn");

                    User user = getUserById(userId);
                    Book book = getBookByIsbn(bookIsbn);

                    if (user != null && book != null) {
                        Request request = new Request(
                                rs.getInt("request_id"),
                                user,
                                book,
                                rs.getDate("borrow_date").toLocalDate(),
                                returnDate,
                                rs.getString("request_status")
                        );
                        overdue.add(request);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving overdue borrows: " + e.getMessage());
        }
        return overdue;
    }

//----------------------------------------------------------------------------------------------------------
    //Student methods:
    public List<Book> displayAavilableBooks() {
        List<Book> books = new ArrayList<>();

        String sql = "SELECT * FROM Books WHERE book_availability = 'Y'";

        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String isbn = rs.getString("book_isbn");
                String title = rs.getString("book_title");
                String author = rs.getString("book_author");
                boolean isAvailable = true;

                Book book = new Book(isbn, title, author, isAvailable);
                books.add(book);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return books;
    }

    public boolean createRequest(String userId, String bookIsbn) {
        String sql = "INSERT INTO Requests (user_id, book_isbn, borrow_date, return_date, request_status) "
                + "VALUES ('" + userId + "' ,'" + bookIsbn + "', SYSDATE, SYSDATE + 30, 'waiting')";

        try {
            Statement stmt = con.createStatement();
            int rowsAffected = stmt.executeUpdate(sql);
            return rowsAffected > 0; // Return true if request was created successfully

        } catch (SQLException e) {
            System.out.println("Error creating request: " + e.getMessage());
            return false;
        }
    }

    public List<Book> displayBooksByUserId(String userId) {
        List<Book> books = new ArrayList<>();

        String sql = "SELECT Books.book_title, Books.book_author, Books.book_isbn "
                + "FROM Books "
                + "JOIN Requests ON Books.book_isbn = Requests.book_isbn "
                + "WHERE Requests.user_id = '" + userId + "' AND request_status = 'accepted'";

        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String isbn = rs.getString("book_isbn");
                String title = rs.getString("book_title");
                String author = rs.getString("book_author");
                boolean isAvailable = false;

                Book book = new Book(isbn, title, author, isAvailable);
                books.add(book);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return books;
    }

    public boolean returnBook(String isbn) {
        String updateRequestSql = "UPDATE Requests SET request_status = 'returned' WHERE book_isbn = '" + isbn + "'";
        String updateBookSql = "UPDATE Books SET book_availability = 'Y' WHERE book_isbn = '" + isbn + "'";

        try {
            Statement stmt = con.createStatement();
            int rowsAffected1 = stmt.executeUpdate(updateRequestSql);
            int rowsAffected2 = stmt.executeUpdate(updateBookSql);

            return rowsAffected1 > 0 && rowsAffected2 > 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

//----------------------------------------------------------------------------------------------------------
    //helper methods:
    public User getUserById(String userId) {
        String sql = "SELECT * FROM UsersTable WHERE user_id = '" + userId + "'";

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String username = rs.getString("user_name");
                String role = rs.getString("user_role");

                if (role.equals("Administrator")) {
                    User user = new Administrator(userId, username);
                    return user;
                } else if (role.equals("Librarian")) {
                    User user = new Librarian(userId, username);
                    return user;
                } else if (role.equals("Student")) {
                    User user = new Student(userId, username);
                    return user;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching user in getUserById: " + e.getMessage());
        }
        return null;
    }

    public Book getBookByIsbn(String bookIsbn) {
        String query = String.format("SELECT * FROM Books WHERE book_isbn = '%s'", bookIsbn);

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {

                String title = rs.getString("book_title");
                String author = rs.getString("book_author");
                String availability = rs.getString("book_availability");
                boolean isAvailable = "Y".equals(availability);

                Book book = new Book(bookIsbn, title, author, isAvailable);
                return book;
            }
        } catch (SQLException e) {
            System.out.println("Error fetching book in getBookByIsbn: " + e.getMessage());
        }
        return null;
    }
}
