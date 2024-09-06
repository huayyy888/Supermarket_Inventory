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
            System.out.printf("\u001B[31mLogin failed. %d attempt(s) left.\n\033[0m",chances-kickCount);
            if(kickCount == chances){
                System.out.println("Exiting..");
                System.exit(0);
            }
        }
       
        //Initialising values from file
        ArrayList<Category> catlist = Category.getCatList();
        ArrayList<Product> prodList = Product.getProdList(catlist);   //retrive category data from catlist

        clrs();
        do {
            System.out.println("\u001B[33m"+"===========================\nLogin successfully as " + admin.getID() + "!\n"+"Welcome to: Botitle Supermarket IMS:\n==========================="+"\u001B[0m");
            System.out.println("1. Inventory");
            System.out.println("2. Orders");
            System.out.println("3. Suppliers");
            System.out.println("4. Edit Admin Settings");
            System.out.println("5. Exit");
            System.out.print("-> ");
            //Error handling.
            try {
                mainChoice = scanner.nextInt();
                // consumes the dangling newline character
                scanner.nextLine();
            } catch (InputMismatchException e) {
                scanner.nextLine(); // Consume the invalid input   
            }   
            clrs();
            switch (mainChoice) {
                case 1:
                    //****************CALL OUT 
                    invMenu(scanner,catlist,prodList);
                    break;
                case 2:
                    //****************CALL OUT 
                    System.out.println("You selected Purchase Order");
                    break;
                case 3:
                    //****************CALL OUT 
                    System.out.println("You selected Supplier Management");
                    break;
                case 4:
                    //****************CALL OUT 
                    System.out.println("Edit Admin");
                    break;
                case 5:        
                    System.out.println("Thank you for using Botitle IMS!");
                    break;         
                default:    
                    System.out.println("\u001B[31m"+"Invalid choice!"+"\033[0m");
            }
        } while (mainChoice != 5);

        scanner.close();
    }
    
    
    public static void invMenu(Scanner scanner,ArrayList<Category> catlist,ArrayList<Product> prodList){
        clrs();
        int choice;
        do{
            choice = 0;
            System.out.println("\u001B[33m"+"=========================\n\tInventory\n========================="+"\u001B[0m");
            System.out.println("<-Categories->");
            System.out.println("1. Add Product Category");
            System.out.println("2. View Product Categories");
            System.out.println("3. Edit/Delete Category");
            
            System.out.println("\n<-Products->");
            System.out.println("4. Add a New Product");
            System.out.println("5. View Products");
            System.out.println("6. Delete a Product");
            System.out.println("7. Edit a Product");
            System.out.println("\n?. Return to main menu");
        
            System.out.print("-> ");
            try {
                    choice = scanner.nextInt();
                        // consumes the dangling newline character
                    scanner.nextLine();
                } catch (InputMismatchException e) {
                    scanner.nextLine(); // Consume the invalid input   
                }
            clrs();
            switch (choice) {
            //CATEGORY
            case 1:
                Category.addCat(scanner, catlist);
                clrs();
                break;
            case 2:
                Category.displayList(catlist);
                System.out.println("Press enter to continue..");
                scanner.nextLine();
                clrs();
                break;
            case 3:
                Category.manageCat(scanner,catlist,prodList);
                break;
            //PRODUCT
            case 4:
                Product.createNewProduct(scanner, catlist);
                clrs();
                break;
            case 5:
                Product.displayProd(prodList);
                System.out.println("Press enter to continue..");
                scanner.nextLine();
                clrs();
                break;
            case 6:
                Product.deleteProd(prodList);
                clrs();
                break;
            case 7:
                Product.editProdMenu(scanner,prodList,catlist);
                clrs();
                break;
            
            case 10:
                return;
            default:
                System.out.println("\u001B[31m"+"Invalid choice!"+"\033[0m");
            }
        }while(choice != 10);
    }

//Login process
    public static boolean login(Scanner scanner, Admin admin) {       
        System.out.print("Enter username (Press X to exit): ");
        
        String username = scanner.nextLine();
        if(username.length()!=0 &&(Character.toUpperCase(username.charAt(0)) == 'X')){
            System.out.println("Exiting...");
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
    

}

