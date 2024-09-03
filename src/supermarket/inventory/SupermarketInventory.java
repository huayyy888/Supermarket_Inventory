package supermarket.inventory;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;

public class SupermarketInventory {
    
    public static void main(String[] args) {
       int mainChoice = 0,chances = 3;
       int kickCount = 0;
       Admin admin = new Admin();       //Call a new admin
       Scanner scanner = new Scanner(System.in);
       
       
       while (!login(scanner,admin)) {
            kickCount++;
            System.out.printf("Login failed. %d attempt(s) left.\n",chances-kickCount);
            if(kickCount == chances){
                System.out.println("Exiting..");
                System.exit(0);
            }
        }
       
        //Initialising values from file
        ArrayList<Category> catlist = Category.getCatList();
        /////////////////
        do {
            clrs();
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
            System.out.print("-> ");
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
                    invMenu(scanner,catlist);
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
                    System.out.println("You selected Supplier Management");
                    break;
                case 5:
                    System.out.println("Exiting program...");
                    break;
                case 6:
                    System.out.println("Admin Settings...");
                    break;
                default:
                   System.out.println("Invalid choice! try again.");
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
    public static void clrs(){
        for(int i =0;i<=10;i++){
            System.out.println("\n");
        }
    //System.out.print("\033c"); ///Clear screen in console cmd
    }
    
    public static void invMenu(Scanner scanner,ArrayList<Category> catlist){
        int choice;
        
        do{
            clrs();
            choice = 0;
            System.out.println("======================\nInventory\n======================");
            System.out.println("1. Add Product Category");
            System.out.println("2. View Product Categories");
            System.out.println("3. DEBUG: ADD category");
            System.out.println("5. Return to main menu");
        
            System.out.print("->");
            try {
                    choice = scanner.nextInt();
                } catch (InputMismatchException e) {
                    scanner.nextLine(); // Consume the invalid input   
                }
            switch (choice) {
                    case 3:
                        //****************CALL OUT 
                        Category.writeDebug();
                        break;
                    case 2:
                        Category.dislayList(catlist);
                        System.out.println("Press enter to continue..");                        
                        scanner.nextLine();
                        scanner.nextLine();
                        break;
                    case 1:
                        Category.addCat(scanner,catlist);
                    default:
                        System.out.println("Invalid choice!");
            }
        }while(choice != 5);
    }
    
}
