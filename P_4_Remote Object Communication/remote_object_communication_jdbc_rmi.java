Code Implementation
*********************************************************************************************************************************

1) create a project name DSCC_1
1.1) create a package name remote_object_communication_jdbc_rmi

********************************************************************************************************************************* 


---------------------------------- step 0 : downloadling dependencies ---------------------------------------------------------

https://dev.mysql.com/downloads/connector/j/


youtube link : https://youtu.be/lNeZe-vIHwU?feature=shared 


 -------------------------------Step 1 - Setting Up MySQL Databases and Tables --------------------------------------------------

1) Create the Library Database and Book Table:
sql command : 
CREATE DATABASE Library;

USE Library;

CREATE TABLE Book (
    Book_id INT PRIMARY KEY AUTO_INCREMENT,
    Book_name VARCHAR(100),
    Book_author VARCHAR(100)
);

INSERT INTO Book (Book_name, Book_author) VALUES 
('The Great Gatsby', 'F. Scott Fitzgerald'),
('To Kill a Mockingbird', 'Harper Lee'),
('1984', 'George Orwell');



2) Create the Electric_Bill Database and Bill Table:
sql command : 
CREATE DATABASE Electric_Bill;

USE Electric_Bill;

CREATE TABLE Bill (
    bill_id INT PRIMARY KEY AUTO_INCREMENT,
    consumer_name VARCHAR(100),
    bill_due_date DATE,
    bill_amount DECIMAL(10, 2)
);

INSERT INTO Bill (consumer_name, bill_due_date, bill_amount) VALUES 
('Alice', '2023-11-15', 150.75),
('Bob', '2023-11-20', 200.50),
('Charlie', '2023-12-01', 175.25);


-------------------------------------------- Step 2: Define RMI Interfaces ------------------------------------------------------------------------

[ LibraryService.java ] 
____________________________________________________________________________________________________________________________________________________-
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface LibraryService extends Remote {
    List<String> getBooks() throws RemoteException;
}

[ ElectricBillService.java ] 
________________________________________________________________________________________________________________________________________________________
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ElectricBillService extends Remote {
    List<String> getBills() throws RemoteException;
}

---------------------------------------------------Step 3: Implement Server-Side Classes with JDBC------------------------------------------------------

[ LibraryServiceImpl.java ]      be careful here change "yourusername" with yours "mysql_user_name" and "yourPassword" with yours "Mysql_password" 
________________________________________________________________________________________________________________________________________________________
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibraryServiceImpl extends UnicastRemoteObject implements LibraryService {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/Library";
    private static final String JDBC_USER = "yourUsername";
    private static final String JDBC_PASSWORD = "yourPassword";

    protected LibraryServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public List<String> getBooks() throws RemoteException {
        List<String> books = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Book")) {

            while (rs.next()) {
                String bookInfo = "ID: " + rs.getInt("Book_id") + ", Name: " + rs.getString("Book_name") + ", Author: " + rs.getString("Book_author");
                books.add(bookInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
}

************************************************************************************************************************************************
[ ElectricBillServiceImpl.java ] 
________________________________________________________________________________________________________________________________________________________
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ElectricBillServiceImpl extends UnicastRemoteObject implements ElectricBillService {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/Electric_Bill";
    private static final String JDBC_USER = "yourUsername";
    private static final String JDBC_PASSWORD = "yourPassword";

    protected ElectricBillServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public List<String> getBills() throws RemoteException {
        List<String> bills = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Bill")) {

            while (rs.next()) {
                String billInfo = "ID: " + rs.getInt("bill_id") + ", Consumer: " + rs.getString("consumer_name") + ", Due Date: " + rs.getDate("bill_due_date") + ", Amount: " + rs.getDouble("bill_amount");
                bills.add(billInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bills;
    }
}

--------------------------------------------- Step 4: Set Up the RMI Server -----------------------------------------------------------------
[ RMIServer.java ] 
________________________________________________________________________________________________________________________________________________________
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.Naming;

public class RMIServer {
    public static void main(String[] args) {
        try {
            LibraryService libraryService = new LibraryServiceImpl();
            ElectricBillService electricBillService = new ElectricBillServiceImpl();

            Registry registry = LocateRegistry.createRegistry(1099);
            Naming.rebind("LibraryService", libraryService);
            Naming.rebind("ElectricBillService", electricBillService);

            System.out.println("RMI Server is running and services are registered.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

----------------------------------------------------- Step 5: Create the Client Application --------------------------------------------------------------------------------
[ RMIClient.java ] 
________________________________________________________________________________________________________________________________________________________ 
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class RMIClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            LibraryService libraryService = (LibraryService) registry.lookup("LibraryService");
            ElectricBillService electricBillService = (ElectricBillService) registry.lookup("ElectricBillService");

            System.out.println("Fetching books from Library:");
            List<String> books = libraryService.getBooks();
            books.forEach(System.out::println);

            System.out.println("\nFetching bills from Electric Bill:");
            List<String> bills = electricBillService.getBills();
            bills.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

-------------------------------------------------------- Step 6: Compile and Run the Application ---------------------------------------------------------------------------

note : compile and run this practical  in eclipse by right click on RMIServer.java and select option run-as-java and after that open new comsole and right click on RMIClient.java and select option run-as-java 

1) Compile all files: 
code : javac *.java  or javac package_name\*.java

2) Run the RMI Server: 
code : java RMIServer

3) Run the Client (in a separate terminal): 
code : java RMIClient


-----------------------------------------------------------------------step 7 : expected output --------------------------------------------------------------------------------

Expected Output

Server Output:
RMI Server is running and services are registered.


Client Output:

Fetching books from Library:
ID: 1, Name: The Great Gatsby, Author: F. Scott Fitzgerald
ID: 2, Name: To Kill a Mockingbird, Author: Harper Lee
ID: 3, Name: 1984, Author: George Orwell

Fetching bills from Electric Bill:
ID: 1, Consumer: Alice, Due Date: 2023-11-15, Amount: 150.75
ID: 2, Consumer: Bob, Due Date: 2023-11-20, Amount: 200.50
ID: 3, Consumer: Charlie, Due Date: 2023-12-01, Amount: 175.25









