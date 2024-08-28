package supermarket.inventory;
import java.util.Scanner;

public class SupermarketInventory {
    
    public static void main(String[] args) {
       int kickCount = 0;
       Scanner scanner = new Scanner(System.in);
       
       clearScreen();
       while (!login(scanner)) {
            System.out.println("Invalid username or password. Please try again.");
            kickCount++;
            if(kickCount == 5){
                System.out.println("Bruteforce detected.. Exiting");
                System.exit(-1);
            }
        }
       
       //DEBUGGING PERPOSE
       System.exit(0);
       ///////////////////
       
       clearScreen();
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
    
    //Login process
    public static boolean login(Scanner scanner) {       
        System.out.print("Enter username (Press X to exit): ");
        
        String username = scanner.nextLine();
        if(Character.toUpperCase(username.charAt(0)) == 'X'){
            System.out.println("Exited, Thank you");
            System.exit(0);
 
        }
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        
        Admin admin = new Admin();
        final String USERNAME = admin.getID();
        final String PASSWORD = admin.getPass();
        
        if (USERNAME.equals(username) && PASSWORD.equals(password)) {
            System.out.println("Login successful!");
            return true;
        } else {
            return false;
        }
    }
    
    //To print newlines and make code more organized
    public static void clearScreen(){
        for(int i =0;i<=10;i++){
            System.out.println("\n");
        }
    }
}
