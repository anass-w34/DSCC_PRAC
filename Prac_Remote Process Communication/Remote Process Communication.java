Code Implementation  (error dikha toh ctrl shift o )
***************************** ****************************************************************************************************

1) create a java project name DSCC_1
1.1) create a package name dscc_remote_process
    create class ChatServer


[ChatServer.java]
____________________________________________________________________________________________________________________________
import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static final int PORT = 12345; // Port number for the server
    private static Set<ClientHandler> clientHandlers = new HashSet<>(); // Set to store all connected clients

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Chat Server started on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept(); // Accept new client connections
                System.out.println("New client connected: " + clientSocket);

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientHandlers.add(clientHandler);
                new Thread(clientHandler).start(); // Start a new thread for each client
            }
        } catch (IOException e) {
            System.err.println("Server Error: " + e.getMessage());
        }
    }

    // Broadcasts a message to all connected clients
    public static void broadcast(String message, ClientHandler sender) {
        for (ClientHandler client : clientHandlers) {
            if (client != sender) { // Avoid sending the message back to the sender
                client.sendMessage(message);
            }
        }
    }

    // Removes a client from the client list when they disconnect
    public static void removeClient(ClientHandler clientHandler) {
        clientHandlers.remove(clientHandler);
        System.out.println("Client disconnected: " + clientHandler.getClientName());
    }
}

*******************************************************************************************************************************************
create class ClientHandler
    [ClientHandler.java]
_________________________________________________________
class ClientHandler implements Runnable {
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private String clientName;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
            output.println("Enter your name:");
            clientName = input.readLine(); // Get client's name
            broadcast(clientName + " has joined the chat.");
        } catch (IOException e) {
            System.err.println("ClientHandler Error: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        String message;
        try {
            while ((message = input.readLine()) != null) { // Read messages from the client
                System.out.println(clientName + ": " + message);
                ChatServer.broadcast(clientName + ": " + message, this); // Broadcast message to all clients
            }
        } catch (IOException e) {
            System.err.println("Connection error with " + clientName + ": " + e.getMessage());
        } finally {
            closeConnection();
        }
    }

    // Sends a message to this client
    public void sendMessage(String message) {
        output.println(message);
    }

    // Closes the client connection and removes it from the server's client list
    private void closeConnection() {
        try {
            socket.close();
            input.close();
            output.close();
        } catch (IOException e) {
            System.err.println("Error closing connection for " + clientName);
        }
        ChatServer.removeClient(this);
        broadcast(clientName + " has left the chat.");
    }

    // Returns the client's name
    public String getClientName() {
        return clientName;
    }

    // Broadcast a message to all clients
    private void broadcast(String message) {
        ChatServer.broadcast(message, this);
    }
}

*************************************************************************************************************************************
[ChatClient.java]
______________________________________________________________________________________________________________________________
import java.io.*;
import java.net.*;

public class ChatClient {
    private static final String SERVER_ADDRESS = "127.0.0.1"; // Localhost address
    private static final int SERVER_PORT = 12345; // Port number

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT)) {
            System.out.println("Connected to the chat server");

            // Input and output streams for communication
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            // Create a thread to handle incoming messages from the server
            new Thread(new IncomingMessageHandler(input)).start();

            // Handle outgoing messages to the server
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            String message;
            while ((message = userInput.readLine()) != null) {
                output.println(message); // Send user input to the server
            }
        } catch (IOException e) {
            System.err.println("ChatClient Error: " + e.getMessage());
        }
    }
}

// Handles incoming messages from the server
class IncomingMessageHandler implements Runnable {
    private BufferedReader input;

    public IncomingMessageHandler(BufferedReader input) {
        this.input = input;
    }

    @Override
    public void run() {
        String message;
        try {
            while ((message = input.readLine()) != null) { // Print incoming messages
                System.out.println(message);
            }
        } catch (IOException e) {
            System.err.println("IncomingMessageHandler Error: " + e.getMessage());
        }
    }
}


*********************************************************************************************************************
Running the Application
________________________________________
1) Start the Server:
 Run ChatServer first by executing:
code : javac ChatServer.java ClientHandler.java
code : java ChatServer

2) Start the Clients:
Open multiple terminals and run ChatClient in each , simulating different users:
code : javac ChatClient.java
code : java ChatClient

*******************************************************************************************************************
1) Server Error: Address already in use: bind

Solution 2: Identify and Kill the Process Using the Port
To free up the port (e.g., 12345), you can kill the process currently using it:

On Windows:
1.1) Open Command Prompt as Administrator.
1.2) Run the following command to find the process using port 12345:
code : netstat -aon | findstr :12345
Note the PID (Process ID) of the process using this port.
1.3) Kill the process by running
code: taskkill /PID <PID> /F

***************************************************************************************************************
2) class not found error 

2.1) Navigate to the source root directory (the directory above dscc_remote_process)
code : cd D:\eclipse\DSCC_1\src
2.2) Compile the classes:
code: javac dscc_remote_process\ChatServer.java dscc_remote_process\ClientHandler.java
javac package_name\class_name.java 
2.3) Run the server:
code : java dscc_remote_process.ChatServer
java package_name.class_name
















