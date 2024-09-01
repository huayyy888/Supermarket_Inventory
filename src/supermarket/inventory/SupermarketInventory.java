package supermarket.inventory;
import java.util.InputMismatchException;
import java.util.Scanner;

public class SupermarketInventory {
    
    public static void main(String[] args) {
       boolean valid = true;
       int mainChoice = 0;
       int kickCount = 0;
       Admin admin = new Admin();       //Call a new admin
       Scanner scanner = new Scanner(System.in);
       
       
       while (!login(scanner,admin)) {
            System.out.println("Invalid username or password. Please try again.");
            kickCount++;
            if(kickCount == 3){
                System.out.println("**BRUTEFORCE DETECTED** Exiting..");
                System.exit(0);
            }
        }
       
       //DEBUGGING PERPOSE
       //System.exit(0);
       ///////////////////
        do {
            clearScreen();
            if(valid == false)
                System.out.println("Invalid input. Try again.");
            System.out.println("===========================");
            System.out.println("Login successfully as " + admin.getID() + "!");
            System.out.println("Welcome to: Botitle Supermarket IMS:");
            System.out.println("===========================");
            System.out.println("1. Inventory management");
            System.out.println("2. Purchase Order");
            System.out.println("3. Delivery");
            System.out.println("4. Manage Supplier");
            System.out.println("5. Exit");
            System.out.println("6. ADMIN SETUP");
            System.out.print("Enter your choice -> ");
            //Error handling.
            try {
                mainChoice = scanner.nextInt();
            } catch (InputMismatchException e) {
                scanner.nextLine(); // Consume the invalid input   
            }   
            switch (mainChoice) {
                case 1:
                    //****************CALL OUT 
                    System.out.println("You selected Inventory/Product Management");
                    invMenu(scanner);
                    valid = true;
                    break;
                case 2:
                    //****************CALL OUT 
                    System.out.println("You selected Purchase Order");
                    valid = true;
                    break;
                case 3:
                    //****************CALL OUT 
                    System.out.println("You selected Delivery");
                    valid = true;
                    break;
                case 4:
                    //****************CALL OUT 
                    System.out.println("You selected Supplier Management");
                    valid = true;
                    break;
                case 5:
                    System.out.println("Exiting program...");
                    break;
                case 6:
                    System.out.println("Admin Settings...");
                    break;
                default:
                    valid = false;
            }
        } while (mainChoice != 5);

        scanner.close();
    }
    
    //Login process
    public static boolean login(Scanner scanner, Admin admin) {       
        System.out.print("Enter username (Press X to exit): ");
        
        String username = scanner.nextLine();
        if(username.length()!=0 &&(Character.toUpperCase(username.charAt(0)) == 'X')){
            System.out.println("Exited, Thank you");
            System.exit(0);
 
        }
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        
        final String USERNAME = admin.getID();
        final String PASSWORD = admin.getPass();
        
        return USERNAME.equals(username) && PASSWORD.equals(password);
    }
    
    //To print newlines and make code more organized
    public static void clearScreen(){
        for(int i =0;i<=10;i++){
            System.out.println("\n");
        }
    }
    
    public static void invMenu(Scanner scanner){
        int choice;
        
        System.out.println("======================\nInventory\n======================\n");
        System.out.print("->");
        try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                scanner.nextLine(); // Consume the invalid input   
            }   
    }
    
}
