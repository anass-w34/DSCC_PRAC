Code Implementation
*********************************************************************************************************************************

1) create a project name DSCC_1
1.1) create a package name mutual_exclusion


*********************************************************************************************************************************
[ TokenRing.java ] 
_______________________________________________________________________________________________________________________________
import java.util.Scanner;

public class TokenRing {
    static boolean[] criticalSection;
    static int n; // number of processes

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter number of processes: ");
        n = sc.nextInt();
        criticalSection = new boolean[n]; // to track which process is in critical section

        // Initialize the criticalSection array
        for (int i = 0; i < n; i++) {
            criticalSection[i] = false; 
        }

        int token = 0; // initially the token is with the first process
        
        while (true) {
            System.out.println("Token is with Process " + token);
            System.out.print("Does Process " + token + " want to enter the critical section? (yes/no): ");
            String input = sc.next();

            if (input.equalsIgnoreCase("yes")) {
                criticalSection[token] = true;
                System.out.println("Process " + token + " is in the critical section.");
                
                // Simulate the process finishing its critical section task
                System.out.print("Process " + token + " has finished. Release the token? (yes/no): ");
                input = sc.next();
                if (input.equalsIgnoreCase("yes")) {
                    criticalSection[token] = false;
                }
            }

            // Pass the token to the next process in the ring
            token = (token + 1) % n;
        }
    }
}

**************************************************************************************************************************************************************

Compile and Run:
For IDEs: Simply run the program using the "Run" button after pasting the code.
For Command Line:
Open the terminal and navigate to the folder where you saved TokenRing.java.
Compile the program using:
javac TokenRing.java
java TokenRing

Example Test Case:
Here’s an example of how the interaction might look:

Enter number of processes: 3
Token is with Process 0
Does Process 0 want to enter the critical section? (yes/no): yes
Process 0 is in the critical section.
Process 0 has finished. Release the token? (yes/no): yes
Token is with Process 1
Does Process 1 want to enter the critical section? (yes/no): no
Token is with Process 2
Does Process 2 want to enter the critical section? (yes/no): yes
Process 2 is in the critical section.
Process 2 has finished. Release the token? (yes/no): yes
Token is with Process 0
Does Process 0 want to enter the critical section? (yes/no): no
Token is with Process 1

