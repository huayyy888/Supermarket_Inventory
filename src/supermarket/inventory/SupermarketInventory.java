package supermarket.inventory;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

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
            System.out.println("3. Vendors");
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
                    invMenu(scanner,catlist,prodList);
                    break;
                case 2:
                    //****************CALL OUT 
                    System.out.println("You selected Purchase Order");
                    break;
                case 3:
                    //****************CALL OUT 
                    vendorMenu(scanner,prodList);
                    break;
                case 4:
                    //****************CALL OUT 
                    editAdminSettings(scanner,admin);
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

    //----------------ADMIN----------------------------------
    public static void editAdminSettings(Scanner scanner, Admin admin) {
        System.out.println("\u001B[33m" + "Edit Admin Settings" + "\u001B[0m");
        System.out.println("1. Change Admin ID");
        System.out.println("2. Change Password");
        System.out.println("3. Return to Main Menu");

        System.out.print("-> ");
        int choice;
        try {
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
        } catch (InputMismatchException e) {
            System.out.println("\u001B[31mInvalid input! Please enter a number.\033[0m");
            scanner.nextLine(); // Consume invalid input
            return;
        }
        clrs();
        switch (choice) {
            case 1:
                System.out.print("Enter new Admin ID: ");
                String newId = scanner.nextLine();
                admin.setAdminID(newId);
                System.out.println("Admin ID updated successfully!");
                break;
            case 2:
                System.out.print("Enter new Password: ");
                String newPassword = scanner.nextLine();
                admin.setAdminPassword(newPassword);
                System.out.println("Password updated successfully!");
                break;
            case 3:
                return;
            default:
                System.out.println("\u001B[31mInvalid choice!\033[0m");
        }
    }
    public static void vendorMenu(Scanner scanner,ArrayList<Product> productList){
        clrs();
        int choice;
        do{
            choice = 0;
            System.out.println("\u001B[33m"+"=========================\n\tVendor\n========================="+"\u001B[0m");
            System.out.println("1. Add Vendor");
            System.out.println("2. View Vendor List");
            System.out.println("3. Edit/Delete Vendor");
           
        System.out.print("-> ");
            try {
                    choice = scanner.nextInt();
                        // consumes the dangling newline character
                    scanner.nextLine();
                } catch (InputMismatchException e) {
                    scanner.nextLine(); // Consume the invalid input   
                }
        clrs();
        switch(choice){
            case 1:
                Product product1 = new Product("CT001","Seafood","8613345", "Grouper", 15.50, 20);
                Product product2 = new Product("CT002","Fruits","7852666", "Avocado", 5.25, 50);
                Product product3 = new Product("CT002","Fruits","8123003", "Banana", 3.60, 100);

                // Create a few Supplier instances
                Vendor vendor1 = new Vendor("S001", "FreshDairy Ltd.", "012-3456789", "info@freshdairy.com");
                Vendor vendor2 = new Vendor("S002", "Bakery Supplies Co.", "013-9876543", "contact@bakeryco.com");

                // Add products to suppliers
                vendor1.addSuppliedProduct(product1); // FreshDairy supplies Milk
                vendor1.addSuppliedProduct(product3); // FreshDairy supplies Eggs

                vendor2.addSuppliedProduct(product2); // Bakery Supplies Co. supplies Bread

                // Display Supplier details and their products
                System.out.println("== SUPPLIER DETAILS AND PRODUCTS SUPPLIED ==");
                System.out.println("\nSupplier 1:");
                System.out.println(vendor1); // Prints supplier info
                vendor1.displaySuppliedProducts(); // Prints products supplied by this supplier

                System.out.println("Vendor 2:");
                System.out.println(vendor2); // Prints supplier info
                vendor2.displaySuppliedProducts(); // Prints products supplied by this supplier

                // Demonstrating a supplier with no products
                Vendor vendor3 = new Vendor("S003", "EmptySupplies Co.", "014-5558888", "empty@supplies.com");
                System.out.println("Vendor 3 (No Products):");
                System.out.println(vendor3); // Prints supplier info
                vendor3.displaySuppliedProducts(); // This supplier has no products
                break;
            default:
        }
       
        
        }while(choice!=5);

    }
}

    
    


