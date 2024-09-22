/**
 *  AACS2204 OOPT Assignment
 * @author TAN JIN YUAN, PATRICIA LEE HUAY, GAN KA CHUN, KER ZHENG FENG
 */
package supermarket.inventory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Vendor extends Item {
    //private String vendorID;
    //private String vendorName;

    private String vendorContact;
    private String email;
    private ArrayList<Product> suppliedProducts;
    
    public Vendor(String vendorID, String vendorName, String vendorContact,String email, ArrayList<Product> suppliedProducts) {
        super(vendorID, vendorName);
        this.vendorContact = vendorContact;
        this.email = email;
        this.suppliedProducts = suppliedProducts;
    }

    public String getVendorID() {
        return getId();
    }
    
    public void setVendorID(String vendorID) {
        setId(vendorID);
    }
    
    public String getVendorName() {
        return getName();
    }
    
    public void setVendorName(String vendorName) {
        setName(vendorName);
    }
    
    public String getVendorContact() {
        return vendorContact;
    }
    
    public void setVendorContact(String vendorContact) {
        this.vendorContact = vendorContact;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Product> getSuppliedProducts() {
        return suppliedProducts;
    }

    public void setSuppliedProducts(ArrayList<Product> SuppliedProducts){
        this.suppliedProducts = SuppliedProducts;
    }
    
    @Override
    public String toString() {
        return "Vendor ID: " + getId() + "\n" +
               "Vendor Name: " + getName() + "\n" +
               "Contact: " + this.vendorContact + "\n" +
               "Email: " + this.email;
    }

    @Override
    public String toFileString() {
        StringBuffer sb = new StringBuffer();
        sb.append(getId()).append(",");
        sb.append(getName()).append(",");
        sb.append(this.vendorContact).append(",");
        sb.append(this.email).append(",");

        // Append supplied product IDs
        if (!suppliedProducts.isEmpty()) {
            for (int i = 0; i < suppliedProducts.size(); i++) {
                sb.append(suppliedProducts.get(i).getID());
                if (i < suppliedProducts.size() - 1) {  //if not last element, add delimiter |
                    sb.append("|");
                }
            }
        }
        sb.append("\n");
        return sb.toString();
    }

    //////////////////////////////-------------------VENDOR DRIVER-----------------------////////////////////

    //------------------------SELECT PRODUCT-----------------//
    public static void selectProductsForVendor(Scanner scanner, ArrayList<Vendor> vendorList, ArrayList<Product> productList, ArrayList<Category> catlist) {
        // Get the most recently added vendor
        /*if arraylist have 5 element (0,1,2,3,4) 
        but last element is 4 so if wanna get last index then -1 
        5 size element - 1 size element = get 4th index*/
        Vendor currentVendor = vendorList.get(vendorList.size() - 1); 
        
        do {
            Product.displayProd(productList);
            Product selectedProduct;
            
            do {
                selectedProduct = Product.selectProductFromCat(scanner, catlist, productList);
            } while (selectedProduct == null);
            
            currentVendor.getSuppliedProducts().add(selectedProduct);

            String answer;
            do {
                System.out.print("Do you want to add another product? (yes/no): ");
                answer = scanner.nextLine().trim().toLowerCase();
                if (!answer.equals("yes") && !answer.equals("no")) {
                    System.out.println("\u001B[31mInvalid input. Please enter 'yes' or 'no'.\u001B[0m");
                }
            } while (!answer.equals("yes") && !answer.equals("no"));
            
            if (answer.equals("no")) {
                break;
            }
        } while (true);
    }

    public static void createNewVendor(Scanner scanner, ArrayList<Vendor> vendorList, ArrayList<Product> productList, ArrayList<Category> catlist) {
        System.out.println("\n\n--ADD A NEW VENDOR--\n");
        String vendorName;
        do {
            System.out.print("Enter vendor name: ");
            vendorName = scanner.nextLine().trim();
        } while (vendorName.isEmpty());

        String vendorContact;
        do {
            System.out.print("Enter vendor contact: ");
            vendorContact = scanner.nextLine().trim();
        } while (vendorContact.isEmpty());

        String email;
        do {
            System.out.print("Enter vendor email: ");
            email = scanner.nextLine().trim();
            if (email.isEmpty() || !isValidEmail(email)) {
                System.out.println("\u001B[31mInvalid email format! Please enter a valid email address.\033[0m");
            }
        } while (email.isEmpty() || !isValidEmail(email));

        String vendorID = generateUniqueVendorID(vendorList); // Generate a unique Vendor ID
        Vendor newVendor = new Vendor(vendorID, vendorName, vendorContact, email, new ArrayList<Product>()); // Create a new Vendor object
        vendorList.add(newVendor);// Add the new vendor to the list
        selectProductsForVendor(scanner, vendorList, productList, catlist); // Select products supplied by this vendor
        
        // Display all details and confirm 
        System.out.println("\n === Please confirm the following details: === ");
        System.out.println(newVendor);
        System.out.println("Supplied Products:");
        for (Product product : newVendor.getSuppliedProducts()) {
            System.out.println("- " + product.getName());
        }

        // Ask for confirmation
        String confirmation;
        while (true) {
            System.out.print("Do you want to add this vendor? (yes/no): ");
            confirmation = scanner.nextLine().trim().toLowerCase();
            if (confirmation.equals("yes") || confirmation.equals("no")) {
                break;
            } else {
                System.out.println("\u001B[31mInvalid input. Please enter 'yes' or 'no'.\u001B[0m");
            }
        }
        
        if (confirmation.equals("yes")) {
            writeVendorsToFile(vendorList);
            System.out.println("\u001B[32mNew vendor created and saved successfully!\u001B[0m");
        } else {
            System.out.println("\u001B[31mVendor addition canceled.\u001B[0m");
        }
    }

    // Validate email
    public static boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    // Generate unique vendor ID (create vendor)
    public static String generateUniqueVendorID(ArrayList<Vendor> vendorList) {
        String vendorID;
        do {
            int firstDigit = (int) (Math.random() * 9) + 1;
            int remainingDigits = (int) (Math.random() * 999999);
            vendorID = String.format("%07d", firstDigit * 1000000 + remainingDigits);
        } while (isVendorIDExists(vendorList, vendorID));
        return vendorID;
    }

    // Check if vendor ID exists 
    public static boolean isVendorIDExists(ArrayList<Vendor> vendorList, String vendorID) {
        for (Vendor vendor : vendorList) {
            if (vendor.getVendorID().equals(vendorID)) {
                return true;
            }
        }
        return false;
    }

    // ================================(R) Display vendors========================================
    public static void displayVendors(ArrayList<Vendor> vendorList) {
        // Display table header
        System.out.println("\n--DISPLAYING ALL VENDORS--\n");
        System.out.printf("%-10s | %-50s | %-15s | %-25s | %-30s\n", 
                        "Vendor ID", "Vendor Name", "Contact", "Email", "Supplied Products ID");
        System.out.println("-".repeat(130));

        // Loop through all vendors in vendorList and display their information
        for (Vendor vendor : vendorList) {
            List<String> productIDs = new ArrayList<>();
            for (Product product : vendor.getSuppliedProducts()) {
                productIDs.add(product.getID());
            }
            String suppliedProducts = String.join(", ", productIDs);
            System.out.printf("%-10s | %-50s | %-15s | %-25s | %-30s\n",
                vendor.getVendorID(),
                vendor.getVendorName(),
                vendor.getVendorContact(),
                vendor.getEmail(),
                suppliedProducts);
            System.out.println("-".repeat(130));
        }
    }
        
    public static ArrayList<Vendor> getVendorList() {
        ArrayList<Vendor> vendorList = new ArrayList<>();
        File file = new File("vendor.txt");
        
        // Check if the file exists, if not, create an empty one
        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("Created new vendor.txt file.");
            } catch (IOException e) {
                System.out.println("Error creating vendor.txt file");
                return vendorList; // Return empty list if file can't be created
            }
        }
        
        try (Scanner fileReader = new Scanner(file)) {
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                String[] vendorData = line.split(",");
                if (vendorData.length >= 5) {
                    ArrayList<Product> suppliedProducts = new ArrayList<>();
                    if (vendorData.length > 4 || !vendorData[4].isEmpty()) {
                        String[] productNames = vendorData[4].split("\\|");
                        for (String productName : productNames) {
                            suppliedProducts.add(new Product(productName.trim(), "", productName, productName, 0.0, 0));
                        }
                    }
                    
                    Vendor vendor = new Vendor(
                        vendorData[0].trim(),
                        vendorData[1].trim(),
                        vendorData[2].trim(),
                        vendorData[3].trim(),
                        suppliedProducts
                    );
                    vendorList.add(vendor);
                } else {
                    System.out.println("Skipping invalid vendor data: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading vendor.txt file");
        }
        return vendorList;
    }

    //write vendor list into file 
    public static void writeVendorsToFile(ArrayList<Vendor> vendorList) {
        try (FileWriter writer = new FileWriter("vendor.txt")) {
            for (Vendor vendor : vendorList) {
               writer.write(vendor.toFileString());
            }
            System.out.println("\u001B[32mVendor file successfully updated!\033[0m");
        } catch (IOException e) {
            System.out.println("\u001B[31mvendor.txt error! Check file.!\033[0m");
            System.out.println(e.toString());
        }
    }

    //===============================(U) Edit & Update vendor===============================================
    public static void editVendor(Scanner scanner, ArrayList<Vendor> vendorList, ArrayList<Product> productList) {
        String confirmation;

        System.out.println("\n\n--EDIT A VENDOR--\n");
        System.out.print("Enter the vendor ID to edit: ");
        String vendorID = scanner.nextLine().trim();
        //check vendor id 
        Vendor vendorToEdit = null;
        for (Vendor vendor : vendorList) {
            if (vendor.getVendorID().equals(vendorID)) {
                vendorToEdit = vendor;
                break;
            }
        }
        
        if (vendorToEdit == null) {
            System.out.println("\u001B[31mVendor Not Found!!\033[0m");
            return;
        }

        int choice;
        do {
            choice = 0;
            System.out.println("\u001B[33m" + "=========================\n    EDIT Vendor " + vendorToEdit.getVendorID() + "\n=========================" + "\u001B[0m");
            System.out.println("1. Edit Vendor name");
            System.out.println("2. Edit phone number");
            System.out.println("3. Edit email");
            System.out.println("4. Edit supplied products");
            System.out.println("5. Return back ");
            System.out.print("Choose an option: ");

            choice = getValidInput(scanner, 1, 5); 
            switch (choice) {
                //edit name
                case 1: 
                    System.out.print("Enter new vendor name (current: " + vendorToEdit.getVendorName() + "): ");
                    String newName = scanner.nextLine().trim();
                    if (!newName.isEmpty()) {
                        String oldName = vendorToEdit.getVendorName();
                        vendorToEdit.setVendorName(newName);
                    do {
                        System.out.print("Do you want to save these changes? (yes/no): ");
                        confirmation = scanner.nextLine().trim().toLowerCase();
                        if (!confirmation.equals("yes") && !confirmation.equals("no")) {
                            System.out.println("\u001B[31mInvalid input. Please enter 'yes' or 'no'.\u001B[0m");
                        }
                    } while (!confirmation.equals("yes") && !confirmation.equals("no"));
                        
                        if (confirmation.equals("yes")) {
                            writeVendorsToFile(vendorList);
                            System.out.println("\u001B[32mVendor details updated successfully!\u001B[0m");
                        } else {
                            vendorToEdit.setVendorName(oldName);
                            System.out.println("\u001B[31mChanges discarded. Vendor details not updated.\u001B[0m");
                        }
                    } else {
                        System.out.println("No changes made.");
                    }
                    
                    System.out.println("Press enter to continue..");
                    scanner.nextLine();
                    clrs();
                    break;
                
                //edit contactnum
                case 2:
                    String oldContact = vendorToEdit.getVendorContact();
                    while (true) {
                        System.out.print("Enter new vendor contact (current: " + oldContact + "): ");
                        String newContact = scanner.nextLine().trim();
                        if (newContact.isEmpty()) {
                            System.out.println("\\u001B[31mContact cannot be empty. Please try again.\u001B[0m");
                        } else {
                            vendorToEdit.setVendorContact(newContact);
                            break;
                        }
                    }
                    do {
                        System.out.print("Do you want to save these changes? (yes/no): ");
                        confirmation = scanner.nextLine().trim().toLowerCase();
                        if (!confirmation.equals("yes") && !confirmation.equals("no")) {
                            System.out.println("\u001B[31mInvalid input. Please enter 'yes' or 'no'.\u001B[0m");
                        }
                    } while (!confirmation.equals("yes") && !confirmation.equals("no"));
                    
                    if (confirmation.equals("yes")) {
                        writeVendorsToFile(vendorList);
                        System.out.println("\u001B[32mVendor contact updated successfully!\u001B[0m");
                    } else {
                        vendorToEdit.setVendorContact(oldContact);
                        System.out.println("\u001B[31mChanges discarded. Vendor contact not updated.\u001B[0m");
                    }

                    System.out.println("Press enter to continue..");
                    scanner.nextLine();
                    clrs();
                    break;

                //edit email
                case 3: 
                    String oldEmail = vendorToEdit.getEmail();
                    while (true) {
                        System.out.print("Enter new vendor email (current: " + oldEmail + "): ");
                        String newEmail = scanner.nextLine().trim();
                        if (newEmail.isEmpty()) {
                            System.out.println("\u001B[31mEmail cannot be empty. Please try again.\u001B[0m");
                        } else if (!isValidEmail(newEmail)) {
                            System.out.println("\u001B[31mInvalid email format. Please try again.\u001B[0m");
                        } else {
                            vendorToEdit.setEmail(newEmail);
                            break;
                        }
                    }
                    System.out.print("Do you want to save these changes? (yes/no): ");
                    confirmation = scanner.nextLine().trim().toLowerCase();
                    
                    if (confirmation.equals("yes")) {
                        writeVendorsToFile(vendorList);
                        System.out.println("\u001B[32mVendor email updated successfully!\u001B[0m");
                    } else {
                        vendorToEdit.setEmail(oldEmail);
                        System.out.println("\u001B[31mChanges discarded. Vendor email not updated.\u001B[0m");
                    }

                    System.out.println("Press enter to continue..");
                    scanner.nextLine();
                    clrs();
                    break;

                //edit supplied product
                case 4: 
                    System.out.println("Current supplied products:");
                    ArrayList<Product> suppliedProducts = vendorToEdit.getSuppliedProducts();
                    for (int i = 0; i < suppliedProducts.size(); i++) {
                        System.out.println((i + 1) + ". " + suppliedProducts.get(i).getName());
                    }
                    
                    System.out.print("Enter the number of the product you want to edit (or 0 to cancel): ");
                    int productChoice = getValidInput(scanner, 0, suppliedProducts.size());
                    
                    if (productChoice != 0) {
                        Product productToEdit = suppliedProducts.get(productChoice - 1);
                        System.out.println("Editing product: " + productToEdit.getID());
                        
                        System.out.println("\n\nAvailable products:");
                        for (int i = 0; i < productList.size(); i++) {
                            System.out.println((i + 1) + ". " + productList.get(i).getName());
                        }
                        
                        System.out.print("Enter the number of the new product to replace it with: ");
                        int newProductChoice = getValidInput(scanner, 1, productList.size());
                        Product newProduct = productList.get(newProductChoice - 1);
                        
                        do {
                            System.out.print("Do you want to replace " + productToEdit.getID() + " with " + newProduct.getID() + "? (yes/no): ");
                            confirmation = scanner.nextLine().trim().toLowerCase();
                            if (!confirmation.equals("yes") && !confirmation.equals("no")) {
                                System.out.println("\u001B[31mInvalid input. Please enter 'yes' or 'no'.\u001B[0m");
                            }
                        } while (!confirmation.equals("yes") && !confirmation.equals("no"));
                        
                        if (confirmation.equals("yes")) {
                            suppliedProducts.set(productChoice - 1, newProduct);
                            writeVendorsToFile(vendorList);
                        } else {
                            System.out.println("Changes discarded. Vendor's supplied products not updated.");
                        }
                    } else {
                        System.out.println("Editing cancelled.");
                    }
                    System.out.println("Press enter to continue..");
                    scanner.nextLine();
                    clrs();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("\u001B[31mInvalid choice!\033[0m");
            }
        }while(choice!=5); 
    }
            
    // ==========================================(D) Delete vendor =====================================================
    public static void deleteVendor(ArrayList<Vendor> vendorList) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n\n--DELETE A VENDOR--\n");
        System.out.print("Enter the vendor ID to delete: ");
        String vendorID = scanner.nextLine().trim();
        
        Vendor vendorToDelete = null;
        for (Vendor vendor : vendorList) {
            if (vendor.getVendorID().equals(vendorID)) {
                vendorToDelete = vendor;
                break;
            }
        }

        if (vendorToDelete == null) {
            System.out.println("\u001B[31mVendor not found!\u001B[0m");
        } else {
            System.out.println("Please confirm to delete the following vendor:\n");
            System.out.println(vendorToDelete);
            System.out.println("Supplied Products:");
            for (Product product : vendorToDelete.getSuppliedProducts()) {
                System.out.println("- " + product.getID());
            }

            String confirmation;
            while (true) {
                System.out.print("\nDo you want to delete this vendor permanently? (yes/no): ");
                confirmation = scanner.nextLine().trim().toLowerCase();
                if (confirmation.equals("yes") || confirmation.equals("no")) {
                    break;
                }
                System.out.print("\u001B[31mInvalid input. Please enter 'yes' or 'no'.\u001B[0m");
            }

            if (confirmation.equals("yes")) {
                vendorList.remove(vendorToDelete);
                writeVendorsToFile(vendorList);
                System.out.println("\u001B[32m**Vendor deleted successfully!**\u001B[0m");
            } else {
                System.out.println("\u001B[31m**Vendor deletion canceled.**\u001B[0m");
            }
        }
    }

    //===================================REQUEST TO RESTOCK==================================================
    public static void requestRestock(Scanner scanner, ArrayList<Vendor> vendorList, ArrayList<Product> productList) {
        System.out.println("\n--REQUEST RESTOCK--\n");
        System.out.println("Select a product to restock:");
        for (int i = 0; i < productList.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, productList.get(i).getName());
        }
        int productIndex = getValidInput(scanner, 1, productList.size()) - 1;
        Product selectedProduct = productList.get(productIndex);

        // Find vendors that supply this product
        ArrayList<Vendor> supplierVendors = new ArrayList<>();
        for (Vendor vendor : vendorList) {
            for (Product product : vendor.getSuppliedProducts()) {
                if (product.getID().equals(selectedProduct.getID())) {
                    supplierVendors.add(vendor);
                    break;  
                }
            }
        }
        if (supplierVendors.isEmpty()) {
            System.out.println("\u001B[31mNo vendors supply this product. Restock request cancelled.\u001B[0m");
            return;
        }

        // Select vendor
        System.out.println("\nSelect a vendor for this product:");
        for (int i = 0; i < supplierVendors.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, supplierVendors.get(i).getVendorName());
        }
        int vendorIndex = getValidInput(scanner, 1, supplierVendors.size()) - 1;
        Vendor selectedVendor = supplierVendors.get(vendorIndex);

        // Enter quantity
        System.out.print("Enter the quantity to restock --> ");
        int quantity = getValidInput(scanner, 1, Integer.MAX_VALUE);

        double costPerUnit = selectedProduct.getPrice() * 0.9;
        double totalCost = costPerUnit * quantity;

        // Display restock details and confirm
        System.out.println("\nRestock Details:");
        System.out.printf("Product: %s\n", selectedProduct.getName());
        System.out.printf("Vendor: %s\n", selectedVendor.getVendorName());
        System.out.printf("Quantity: %d\n", quantity);
        System.out.printf("Cost per unit: RM %.2f\n", costPerUnit);
        System.out.printf("Total cost: RM %.2f\n", totalCost);

        String confirmation;
        do {
            System.out.print("\nConfirm restock request? (yes/no): ");
            confirmation = scanner.nextLine().trim().toLowerCase();
            if (!confirmation.equals("yes") && !confirmation.equals("no")) {
                System.out.println("\u001B[31mInvalid input. Please enter 'yes' or 'no'.\u001B[0m");
            }
        } while (!confirmation.equals("yes") && !confirmation.equals("no"));

        if (confirmation.equals("yes")) {
            selectedProduct.restock(quantity);                                         
            System.out.println("\u001B[32mRestock request confirmed and sent to the vendor.\u001B[0m");
            Product.writeProd(productList);
        } else {
            System.out.println("\u001B[31mRestock request cancelled.\u001B[0m");
        }
    }

    private static int getValidInput(Scanner scanner, int min, int max) {
        int input;
        do {
            System.out.printf("Enter a number between %d and %d: ", min, max);
            while (!scanner.hasNextInt()) {
                System.out.println("\u001B[31mThat's not a valid number. Please try again.\u001B[0m");
                scanner.next();
            }
            input = scanner.nextInt();
            scanner.nextLine(); // Consume newline
        } while (input < min || input > max);
        return input;
    }

    public static void clrs(){
            for(int i =0;i<=10;i++){
                System.out.println("\n");
            }
        }
}