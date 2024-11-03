Code Implementation
*********************************************************************************************************************************

1) create a project name DSCC_1
2) create a package name remote_procedure_call

[ CalculatorServer.java ]
_______________________________________________________________________________________________________________________________________
import java.net.*;
import java.io.*;

public class CalculatorServer {
    private static final int PORT = 9876;

    public static void main(String[] args) {
        try (DatagramSocket serverSocket = new DatagramSocket(PORT)) {
            System.out.println("Calculator Server is running on port " + PORT);

            byte[] receiveBuffer = new byte[1024];
            byte[] sendBuffer;

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                serverSocket.receive(receivePacket);

                String request = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Received request: " + request);

                String response = handleRequest(request);

                sendBuffer = response.getBytes();
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, clientAddress, clientPort);
                serverSocket.send(sendPacket);
            }
        } catch (IOException e) {
            System.err.println("Calculator Server Error: " + e.getMessage());
        }
    }

    private static String handleRequest(String request) {
        String[] parts = request.trim().split(" ");
        if (parts.length < 3) return "Invalid request";

        String operation = parts[0].toUpperCase();
        try {
            double num1 = Double.parseDouble(parts[1]);
            double num2 = Double.parseDouble(parts[2]);

            switch (operation) {
                case "ADD":
                    return "Result: " + (num1 + num2);
                case "SUB":
                    return "Result: " + (num1 - num2);
                case "MUL":
                    return "Result: " + (num1 * num2);
                case "DIV":
                    return num2 != 0 ? "Result: " + (num1 / num2) : "Error: Division by zero";
                default:
                    return "Error: Unsupported operation";
            }
        } catch (NumberFormatException e) {
            return "Error: Invalid number format";
        }
    }
}


*********************************************************************************************************************************
[ DateTimeServer.java ] 
____________________________________________________________________________________________________________________________
import java.net.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeServer {
    private static final int PORT = 9877;

    public static void main(String[] args) {
        try (DatagramSocket serverSocket = new DatagramSocket(PORT)) {
            System.out.println("Date Time Server is running on port " + PORT);

            byte[] receiveBuffer = new byte[1024];
            byte[] sendBuffer;

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                serverSocket.receive(receivePacket);

                String request = new String(receivePacket.getData(), 0, receivePacket.getLength()).trim();
                System.out.println("Received request: " + request);

                String response;
                if ("DATE".equalsIgnoreCase(request)) {
                    response = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                } else if ("TIME".equalsIgnoreCase(request)) {
                    response = new SimpleDateFormat("HH:mm:ss").format(new Date());
                } else {
                    response = "Invalid request";
                }

                sendBuffer = response.getBytes();
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, clientAddress, clientPort);
                serverSocket.send(sendPacket);
            }
        } catch (IOException e) {
            System.err.println("Date Time Server Error: " + e.getMessage());
        }
    }
}

***********************************************************************************************************************************************
[ Client.java ] 
____________________________________________________________________________________________________________________________________________
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {
    private static final int CALCULATOR_PORT = 9876;
    private static final int DATETIME_PORT = 9877;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter server type (calculator/datetime): ");
        String serverType = scanner.nextLine().trim().toLowerCase();

        int port = serverType.equals("calculator") ? CALCULATOR_PORT : DATETIME_PORT;
        InetAddress serverAddress;
        try {
            serverAddress = InetAddress.getByName("localhost");

            while (true) {
                System.out.println("Enter request:");
                String request = scanner.nextLine();

                byte[] sendBuffer = request.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, serverAddress, port);
                try (DatagramSocket clientSocket = new DatagramSocket()) {
                    clientSocket.send(sendPacket);

                    byte[] receiveBuffer = new byte[1024];
                    DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                    clientSocket.receive(receivePacket);

                    String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
                    System.out.println("Server response: " + response);
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("Client Error: Unknown host");
        } catch (IOException e) {
            System.err.println("Client Error: " + e.getMessage());
        }
    }
}

******************************************************************************************************************************************************************
How to Run the Application 

1) Start the Calculator Server: 
Open a terminal, navigate to the directory containing the CalculatorServer.java, and run
code : javac CalculatorServer.java   OR javac packagename\classname.java 
code : java CalculatorServer            java packagename.classname 

    D:\eclipse\DSCC_1\src>javac remote_procedure_call/CalculatorServer.java

D:\eclipse\DSCC_1\src>java remote_procedure_call/CalculatorServer.java
Calculator Server is running on port 9876
    

2) Start the Date Time Server:
open another terminal, navigate to the directory containing DateTimeServer.java, and run: 
code : javac DateTimeServer.java
code : java DateTimeServer

D:\eclipse\DSCC_1\src>javac remote_procedure_call/DateTimeServer.java

D:\eclipse\DSCC_1\src>java remote_procedure_call/DateTimeServer.java
    
3) Run the Client:
Open a third terminal for the client application.
Compile and run the client:
code : javac /Client.java
code : java /Client

4) Test the Application:
In the client console, specify which server to connect to (calculator or datetime).
For the calculator server, you can enter commands like ADD 5 10, SUB 10 4, MUL 3 7, DIV 15 3.
For the date time server, you can enter DATE or TIME.


D:\eclipse\DSCC_1\src>java remote_procedure_call/Client.java
Enter server type (calculator/datetime):
calculator
Enter request:
add 5 10
Server response: Result: 15.0
Enter request:
mul 51 0

    
D:\eclipse\DSCC_1\src>java remote_procedure_call/Client.java
Enter server type (calculator/datetime):
datetime
Enter request:
date
Server response: 2024-11-03
Enter request:
time
Server response: 16:38:46

    



