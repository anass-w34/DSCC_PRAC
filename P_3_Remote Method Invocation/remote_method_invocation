Code Implementation
*********************************************************************************************************************************

1) create a project name DSCC_1
1.1) create a package name remote_method_invocation

[ DateTimeService.java ] 
___________________________________________________________________________________________________________________________________
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

public interface DateTimeService extends Remote {
    Date getCurrentDate() throws RemoteException;
    String getCurrentTime() throws RemoteException;
}

*********************************************************************************************************************************

[ EquationSolverService.java ] 
___________________________________________________________________________________________________________________________________
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface EquationSolverService extends Remote {
    double solveEquation(double a, double b) throws RemoteException;
}

*********************************************************************************************************************************
[ DateTimeServiceImpl.java ] 
___________________________________________________________________________________________________________________________________ 
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeServiceImpl extends UnicastRemoteObject implements DateTimeService {
    protected DateTimeServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public Date getCurrentDate() throws RemoteException {
        return new Date(); // Returns the current date
    }

    @Override
    public String getCurrentTime() throws RemoteException {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        return formatter.format(new Date()); // Returns formatted current time
    }
}

*********************************************************************************************************************************
[ EquationSolverServiceImpl.java ] 
___________________________________________________________________________________________________________________________________ 
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class EquationSolverServiceImpl extends UnicastRemoteObject implements EquationSolverService {
    protected EquationSolverServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public double solveEquation(double a, double b) throws RemoteException {
        // Solves (a - b)^2 = a^2 - 2ab + b^2
        return (a * a) - (2 * a * b) + (b * b);
    }
}

*********************************************************************************************************************************
[ RMIServer.java ]
___________________________________________________________________________________________________________________________________
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.Naming;

public class RMIServer {
    public static void main(String[] args) {
        try {
            // Create instances of services
            DateTimeService dateTimeService = new DateTimeServiceImpl();
            EquationSolverService equationSolverService = new EquationSolverServiceImpl();

            // Bind services to RMI registry
            Registry registry = LocateRegistry.createRegistry(1099);
            Naming.rebind("DateTimeService", dateTimeService);
            Naming.rebind("EquationSolverService", equationSolverService);

            System.out.println("RMI Server is running and services are registered.");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}

*********************************************************************************************************************************
[ RMIClient.java ]
___________________________________________________________________________________________________________________________________
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class RMIClient {
    public static void main(String[] args) {
        try {
            // Connect to the RMI registry
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            // Lookup DateTimeService
            DateTimeService dateTimeService = (DateTimeService) registry.lookup("DateTimeService");
            System.out.println("Current Date: " + dateTimeService.getCurrentDate());
            System.out.println("Current Time: " + dateTimeService.getCurrentTime());

            // Lookup EquationSolverService
            EquationSolverService equationSolverService = (EquationSolverService) registry.lookup("EquationSolverService");

            // Get values for 'a' and 'b' from the user
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter value for a: ");
            double a = scanner.nextDouble();
            System.out.print("Enter value for b: ");
            double b = scanner.nextDouble();
            scanner.close();

            // Solve equation and display result
            double result = equationSolverService.solveEquation(a, b);
            System.out.println("Result of (a - b)^2 = a^2 - 2ab + b^2 for a=" + a + " and b=" + b + " is: " + result);

        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}


*********************************************************************************************************************************

1) Compile all classes: 
javac DateTimeService.java DateTimeServiceImpl.java EquationSolverService.java EquationSolverServiceImpl.java RMIServer.java RMIClient.java
                    or 
javac remote_method_invocation\DateTimeService.java remote_method_invocation\DateTimeServiceImpl.java remote_method_invocation\EquationSolverService.java remote_method_invocation\EquationSolverServiceImpl.java remote_method_invocation\RMIServer.java remote_method_invocation\RMIClient.java
                   or 
javac remote_method_invocation\*.java


2) Run the RMI Server and Client
Start the RMI Server:
java RMIServer
   or 
java remote_method_invocation.RMIServer

3) Run the Client: Open a new terminal and run:
java RMIClient
   or 
java remote_method_invocation.RMIClient





*********************************************************************************************************************************************
Expected Output
On the Server Console:
RMI Server is running and services are registered.


On the Client Console:
Current Date: Sun Oct 29 14:23:07 IST 2023
Current Time: 14:23:07
Enter value for a: 5
Enter value for b: 2
Result of (a - b)^2 = a^2 - 2ab + b^2 for a=5.0 and b=2.0 is: 9.0








