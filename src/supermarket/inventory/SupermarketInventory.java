package supermarket.inventory;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class SupermarketInventory {
    
    public static void main(String[] args) {
       int mainChoice = 0,
       chances = 3;
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
        ArrayList<Vendor> vendorList = Vendor.getVendorList();
        
        clrs();
        do {
            System.out.println("\u001B[33m"+"===========================\nLogin successfully as " + admin.getID() + "!\n"+"Welcome to: Botitle Supermarket IMS:\n==========================="+"\u001B[0m");
            System.out.println("1. Inventory");
            System.out.println("2. Orders/Sales");
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
                    orderMenu(scanner,prodList,catlist,admin);
                    break;
                case 3:
                    vendorMenu(scanner, vendorList, prodList, catlist);
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
    
        //----------------ADMIN----------------------------------
    public static void editAdminSettings(Scanner scanner, Admin admin) {
        clrs();
        int choice;
        do{
            choice = 0;
            System.out.println("\u001B[33m" + "======================" + "\u001B[0m");
            System.out.println("  Edit Admin Settings");
            System.out.println("\u001B[33m" + "======================" + "\u001B[0m");
            System.out.println("1. Change Admin ID");
            System.out.println("2. Change Password");
            System.out.println("3. Return to Main Menu");

            System.out.print("-> ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

            clrs();
            switch (choice) {
                case 1:
                    System.out.print("Enter new Admin ID: ");
                    String newId = scanner.nextLine();
                    admin.setAdminID(newId);
                    clrs();
                    System.out.println("\u001B[32mAdmin ID updated successfully!\u001B[0m");
                    break;
                case 2:
                    System.out.print("Enter new Password: ");
                    String newPassword = scanner.nextLine();
                    admin.setAdminPassword(newPassword);
                    System.out.println("\u001B[32mPassword updated successfully!\u001B[0m");
                    break;
                case 3:
                    return;
                default:
                    System.out.println("\u001B[31mInvalid choice!\033[0m");
                }
            } catch (InputMismatchException e) {
                clrs();
                System.out.println("\u001B[31mInvalid input! Please enter a number.\033[0m");
                scanner.nextLine(); // Consume invalid input
            }
        }while(choice != 3);
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
            System.out.println("\u001B[34m8.\u001B[0m" + " Generate Product Overview");            
            System.out.println("\n9. Return to MENU");
        
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
                Product.createNewProduct(scanner, catlist,prodList);
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
            case 8:
                invReport(catlist, prodList);
                System.out.println("Press enter to continue..");
                scanner.nextLine();
                clrs();
                break;
            case 9:
                return;
            default:
                System.out.println("\u001B[31m"+"Invalid choice!"+"\033[0m");
            }
        }while(choice != 10);
    }

    //----------------------Vendor Menu--------------------------
    public static void vendorMenu(Scanner scanner, ArrayList<Vendor> vendorList, ArrayList<Product> productList, ArrayList<Category> catlist) {
        clrs();
        int choice;
        do {
            choice = 0;
            System.out.println("\u001B[33m"+"=========================\n\tVendor\n========================="+"\u001B[0m");
            System.out.println("1. Add Vendor");
            System.out.println("2. View Vendor List");
            System.out.println("3. Edit Vendor");
            System.out.println("4. Delete Vendor");
            System.out.println("5. Request Restock");
            System.out.println("6. Return to Main Menu");
           
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
                Vendor.createNewVendor(scanner, vendorList,productList,catlist);
                System.out.println("Press enter to continue..");
                scanner.nextLine();
                clrs();
                break;
            case 2: 
                Vendor.displayVendors(vendorList);
                System.out.println("Press enter to continue..");
                scanner.nextLine();
                clrs();
                break;
            case 3: 
                Vendor.editVendor(scanner, vendorList, productList);
                System.out.println("Press enter to continue..");
                scanner.nextLine();
                clrs();
                break;
            case 4: 
                Vendor.deleteVendor(vendorList);
                System.out.println("Press enter to continue..");
                scanner.nextLine();
                clrs();
                break;
            case 5:
                Vendor.requestRestock(scanner, vendorList, productList);
                System.out.println("Press enter to continue..");
                scanner.nextLine();
                clrs();
                break;
            case 6:
                return;
            default:
            System.out.println("\u001B[31mInvalid choice!\033[0m");
        }
        }while(choice!=6);
    }
    public static void orderMenu(Scanner scanner, ArrayList<Product> prodList,ArrayList<Category> catlist,Admin admin){
        clrs();
        int choice;
        do{
            choice = 0;
            System.out.println("\u001B[33m"+"=========================\n\tInventory\n========================="+"\u001B[0m");
            System.out.println("1. Add Order");


            System.out.println("\n2. Return to main menu");
        
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
                Order.addOrder(scanner, prodList, catlist,admin);
                clrs();
                break;
            case 2:
                return;
            default:
                System.out.println("\u001B[31m"+"Invalid choice!"+"\033[0m");
            }
        }while(choice != 4);
    }
    public static void clrs(){
        for(int i =0;i<=10;i++){
            System.out.println("\n");
        }
    //System.out.print("\033c"); ///Clear screen in console cmd
    }
  
    private static void invReport(ArrayList<Category> categories, ArrayList<Product> products) {
        System.out.println("\t\u001B[33m=== INVENTORY REPORT ===\u001B[0m");    
        double totalValue = 0;
        int totalItems = 0;
        int lowStockItems = 0;
    
        for (Category category : categories) {
            System.out.println("\n\u001B[36mCategory: " + category.getCatName() + " (" + category.getCatId() + ")\u001B[0m");
            System.out.println("----------------------------");
            System.out.printf("%-10s %-20s %-10s %-10s %-15s\n", "ID", "Name", "Price(RM)", "Quantity", "Total Value(RM)");
    
            double categoryValue = 0;
            int categoryItems = 0;
    
            for (Product product : products) {
                if (product.getCatId().equals(category.getCatId())) {
                    double productValue = product.getPrice() * product.getQty();
                    System.out.printf("%-10s %-20s %-10.2f %-10d %-15.2f\n", 
                        product.getID(), product.getName(), product.getPrice(), product.getQty(), productValue);
                    
                    categoryValue += productValue;
                    categoryItems += product.getQty();
                    
                    if (product.getQty() <= Product.getReminderQty()) {
                        lowStockItems++;
                    }
                }
            }
    
            System.out.println("----------------------------");
            System.out.printf("Category Total: %d items, Value: RM %.2f\n", categoryItems, categoryValue);
    
            totalValue += categoryValue;
            totalItems += categoryItems;
        }
    
        System.out.println("\n\u001B[33m=== SUMMARY ===\u001B[0m");
        System.out.printf("Total Inventory Value: RM %.2f\n", totalValue);  //total value of inventory
        System.out.printf("Total Number of Items: %d\n", totalItems);
        System.out.printf("Low Stock Items (<=d): %d\n", Product.getReminderQty(),lowStockItems);

        System.out.println("\n\u001B[33m=== PRODUCT AMOUNT DISTRIBUTION ===\u001B[0m");
        barChart(categories, products, totalItems);

        System.out.println("\n\u001B[33m=== PRODUCT COST DISTRIBUTION ===\u001B[0m");
        barChart(categories, products, totalValue, 30);
    
    }

    //for quantity chart
    private static void barChart(ArrayList<Category> categories, ArrayList<Product> products, int totalItems) {
        for (int i = 0; i < categories.size(); i++) {
            Category cat = categories.get(i);
            int catQty = 0;
            for (Product product : products) {
                if (product.getCatId().equals(cat.getCatId())) {
                    catQty += product.getQty();
                }
            }
            
            int barLength = (int) Math.round(((double)catQty /totalItems) * 20);
            String bar = "\u001B[42m" + " ".repeat(barLength) + "\u001B[0m";
            
            System.out.printf("%-15s [%-20s] %5.2f%%\n", 
                cat.getCatName(), 
                bar + (" ".repeat(20-barLength)),   //to ensure all bars are the same length
                ((double)catQty / totalItems) * 100);
        }
    }
    //for cost chart
    private static void barChart(ArrayList<Category> categories, ArrayList<Product> products, double totalCost,int maxLength) {
        for (int i = 0; i < categories.size(); i++) {
            Category cat = categories.get(i);
            double catCost = 0;
            for (Product product : products) {
                if (product.getCatId().equals(cat.getCatId())) {
                    catCost += product.getQty() * product.getPrice();
                }
            }
            
            int barLength = (int) Math.round((catCost/totalCost) * maxLength);
            String bar = "\u001B[46m" + " ".repeat(barLength) + "\u001B[0m" + " ".repeat(maxLength - barLength);
            
            System.out.printf("%-15s [%-20s] %5.2f%%\n", 
                cat.getCatName(), 
                bar,   //to ensure all bars are the same length (green + black = maxlength)
                (catCost / totalCost) * 100);
        }
    }
    


}

    
    


