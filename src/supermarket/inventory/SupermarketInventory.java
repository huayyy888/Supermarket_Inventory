package supermarket.inventory;
import java.util.Scanner;

public class SupermarketInventory {
    public static void main(String[] args) {
       Scanner scanner = new Scanner(System.in);
       
       while (!login(scanner)) {
            System.out.println("Invalid username or password. Please try again.");
        }
       
       int mainChoice;
        do {
            System.out.println("\nMain Menu:");
            System.out.println("1. Inventory management");
            System.out.println("2. Purchase Order");
            System.out.println("3. Delivery");
            System.out.println("4. Edit Supplier");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            mainChoice = scanner.nextInt();

            switch (mainChoice) {
                case 1:
                    //****************CALL OUT 
                    System.out.println("You selected Inventory management");
                    break;
                case 2:
                    //****************CALL OUT 
                    System.out.println("You selected Purchase Order");
                    break;
                case 3:
                    //****************CALL OUT 
                    System.out.println("You selected Delivery");
                    break;
                case 4:
                    //****************CALL OUT 
                    System.out.println("You selected Edit Supplier");
                    break;
                case 5:
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        } while (mainChoice != 5);

        scanner.close();
    }
    
    public static boolean login(Scanner scanner) {
        final String USERNAME = "admin001";
        final String PASSWORD = "12345";
        
        System.out.print("Enter username: ");
        String username = scanner.next();

        System.out.print("Enter password: ");
        String password = scanner.next();

        if (USERNAME.equals(username) && PASSWORD.equals(password)) {
            System.out.println("Login successful!");
            return true;
        } else {
            return false;
        }
    }
}
