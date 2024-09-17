package supermarket.inventory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Vendor {
    //datatype 
    private String vendorID;
    private String vendorName;
    private String vendorContact;
    private String email;
    private ArrayList<Product> suppliedProducts;
    
    //construtor parameter
    public Vendor(String vendorID, String vendorName, String vendorContact,String email, ArrayList<Product> suppliedProducts) {
        this.vendorID = vendorID;
        this.vendorName = vendorName;
        this.vendorContact = vendorContact;
        this.email = email;
        this.suppliedProducts = suppliedProducts;
    }
    //getter & setter &toString
    public String getVendorID() {
        return vendorID;
    }
    
    public void setVendorID(String vendorID) {
        this.vendorID = vendorID;
    }
    
    public String getVendorName() {
        return vendorName;
    }
    
    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
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
        return "Vendor ID: " + vendorID + "\n" +
               "Vendor Name: " + vendorName + "\n" +
               "Contact: " + vendorContact + "\n" +
               "Email: " + email;
    }

    //////////////////////////////-------------------VENDOR DRIVER-----------------------////////////////////

    //------------------------SELECT PRODUCT-----------------//
    public static void selectProductsForVendor(Scanner scanner, ArrayList<Vendor> vendorList, ArrayList<Product> productList, ArrayList<Category> catlist) {
        // Get the most recently added vendor
        /*if arraylist have 5 element (0,1,2,3,4) 
        but last element is 4 so if wanna get last index then -1 
        5 size element - 1 size element = get 4th index*/
        Vendor currentVendor = vendorList.get(vendorList.size() - 1); 
        
        while (true) {
            // Display all products
            Product.displayProd(productList);
            
            // Let user select a product (from Product class method)
            Product selectedProduct = Product.selectProductFromCat(scanner, catlist, productList);
            
            if (selectedProduct != null) {
                // Add the selected product to the vendor's list
                currentVendor.getSuppliedProducts().add(selectedProduct);  
                
                // Ask if user wants to add another product
                System.out.print("Do you want to add another product? (yes/no): ");
                String answer = scanner.nextLine().trim().toLowerCase();
                if (!answer.equals("yes")) {
                    break;
                }
            } else {
                System.out.println("No product selected. Moving on.");
                break;
            }
        }
    }
    
    //----------------------(C) CREATE VENDOR -----------------------//
    public static void createNewVendor(Scanner scanner, ArrayList<Vendor> vendorList, ArrayList<Product> productList, ArrayList<Category> catlist) {
        System.out.println("\n\n--ADD A NEW VENDOR--\n");

        // Get vendor details from user

        //vendor name 
        String vendorName;
        do {
            System.out.print("Enter vendor name: ");
            vendorName = scanner.nextLine().trim();
        } while (vendorName.isEmpty());
        
        //contact number
        String vendorContact;
        do {
            System.out.print("Enter vendor contact: ");
            vendorContact = scanner.nextLine().trim();
        } while (vendorContact.isEmpty());

        //email 
        String email;
        do {
            System.out.print("Enter vendor email: ");
            email = scanner.nextLine().trim();
        } while (email.isEmpty() || !isValidEmail(email));
    
        // Generate a unique Vendor ID
        String vendorID = generateUniqueVendorID(vendorList);
    
        // Create a new Vendor object
        Vendor newVendor = new Vendor(vendorID, vendorName, vendorContact, email, new ArrayList<Product>());
        
        // Add the new vendor to the list
        vendorList.add(newVendor);
        
        // Select products supplied by this vendor
        selectProductsForVendor(scanner, vendorList, productList, catlist);
        
        // Display all details and confirm
        System.out.println("\nPlease confirm the following details:");
        System.out.println(newVendor);
        System.out.println("Supplied Products:");
        for (Product product : newVendor.getSuppliedProducts()) {
            System.out.println("- " + product.getName());
        }
        
        // Ask for confirmation
        System.out.print("Do you want to add this vendor? (yes/no): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();
        
        if (confirmation.equals("yes")) {
            writeVendorsToFile(vendorList);
            System.out.println("New vendor created and saved successfully!");
        } else {
            vendorList.remove(newVendor);
            System.out.println("Vendor addition canceled.");
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
    try {
        FileWriter writer = new FileWriter("vendor.txt");
        
        for (Vendor vendor : vendorList) {
            // Create a string to store product IDs
            String suppliedProductIDs = "";
            
            // Loop through supplied products and add their IDs
            for (Product product : vendor.getSuppliedProducts()) { 
                if (!suppliedProductIDs.isEmpty()) {
                    suppliedProductIDs += "|";  //write produt ID | product ID 
                }
                suppliedProductIDs += product.getID();
            }
            
            // Write vendor information to file 
            writer.write(vendor.getVendorID() + "," +
                         vendor.getVendorName() + "," +
                         vendor.getVendorContact() + "," +
                         vendor.getEmail() + "," +
                         suppliedProductIDs + "\n");
        }
        
        writer.close();
        System.out.println("Vendor file successfully updated!");
    } catch (IOException e) {
        System.out.println("vendor.txt error! Check file.");
        System.out.println(e.toString());
    }
}

//===============================(U) Edit & Update vendor===============================================
public static void editVendor(Scanner scanner, ArrayList<Vendor> vendorList, ArrayList<Product> productList) {
    System.out.println("\n\n--EDIT A VENDOR--\n");
    System.out.print("Enter the vendor ID to edit: ");
    String vendorID = scanner.nextLine().trim();
    
    Vendor vendorToEdit = null;
    for (Vendor vendor : vendorList) {
        if (vendor.getVendorID().equals(vendorID)) {
            vendorToEdit = vendor;
            break;
        }
    }
    
    if (vendorToEdit == null) {
        System.out.println("Vendor not found!");
        return;
    }

    System.out.println("Editing details for: ");
    System.out.println(vendorToEdit);
    
    // Edit Vendor Name
    System.out.print("Enter new vendor name (current: " + vendorToEdit.getVendorName() + "): ");
    String newName = scanner.nextLine().trim();
    if (!newName.isEmpty()) {
        vendorToEdit.setVendorName(newName);
    }
    
    // Edit Vendor Contact
    System.out.print("Enter new vendor contact (current: " + vendorToEdit.getVendorContact() + "): ");
    String newContact = scanner.nextLine().trim();
    if (!newContact.isEmpty()) {
        vendorToEdit.setVendorContact(newContact);
    }

    // Edit Vendor Email
    System.out.print("Enter new vendor email (current: " + vendorToEdit.getEmail() + "): ");
    String newEmail = scanner.nextLine().trim();
    if (!newEmail.isEmpty()) {
        if (isValidEmail(newEmail)) {
            vendorToEdit.setEmail(newEmail);
        } else {
            System.out.println("Invalid email format. Email not updated.");
        }
    }
    
    //!!!!!!!!!!!!!!EDIT SUPPLIED PRODUCT!!!!!!!!!111111111
    // Confirm changes
    System.out.println("\nUpdated vendor details:");
    System.out.println(vendorToEdit);
    System.out.println("Supplied Products:");
    for (Product product : vendorToEdit.getSuppliedProducts()) {
        System.out.println("- " + product.getName());
    }
    System.out.print("Do you want to save these changes? (yes/no): ");
    String confirmation = scanner.nextLine().trim().toLowerCase();
    
    if (confirmation.equals("yes")) {
        writeVendorsToFile(vendorList);
        System.out.println("Vendor details updated successfully!");
    } else {
        System.out.println("Changes discarded. Vendor details not updated.");
    }
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
            System.out.println("Vendor not found!");
        } else {
            System.out.println("Please confirm to delete the following vendor:");
            System.out.println(vendorToDelete);
            System.out.println("Supplied Products:");
            for (Product product : vendorToDelete.getSuppliedProducts()) {
                System.out.println("- " + product.getID());
            }

            System.out.print("Do you want to delete this vendor permanently? (yes/no, default is no): ");
            if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
                vendorList.remove(vendorToDelete);
                writeVendorsToFile(vendorList);
                System.out.println("Vendor deleted successfully!");
            } else {
                System.out.println("**Vendor deletion canceled.**");
            }
        }
    }

    //===================================REQUEST TO RESTOCK==================================================
    public static void requestRestock(Scanner scanner, ArrayList<Vendor> vendorList, ArrayList<Product> productList) {
        System.out.println("\n--REQUEST RESTOCK--\n");

        // Select product
        System.out.println("Select a product to restock:");
        for (int i = 0; i < productList.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, productList.get(i).getName());
        }
        int productIndex = getValidInput(scanner, 1, productList.size()) - 1;
        Product selectedProduct = productList.get(productIndex);

        // Find vendors that supply this product
        ArrayList<Vendor> supplierVendors = new ArrayList<>();
        for (Vendor vendor : vendorList) {
        if (vendor.getSuppliedProducts().stream().anyMatch(p -> p.getID().equals(selectedProduct.getID()))) {
            supplierVendors.add(vendor);
        }
    }

        if (supplierVendors.isEmpty()) {
            System.out.println("No vendors supply this product. Restock request cancelled.");
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

        System.out.print("\nConfirm restock request? (yes/no): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
            //updateProductQty(selectedProduct.getID(), quantity);
            selectedProduct.restock(quantity);                                         
            System.out.println("Restock request confirmed and sent to the vendor.");
            
            Product.writeProd(productList);
        } else {
            System.out.println("Restock request cancelled.");
        }
    }

    //FOR REQUEST TO RESTOCK (PRODUCT TEXT FILE)
    
    //VALID NUMBER INPUT 
    private static int getValidInput(Scanner scanner, int min, int max) {
        int input;
        do {
            System.out.printf("Enter a number between %d and %d: ", min, max);
            while (!scanner.hasNextInt()) {
                System.out.println("That's not a valid number. Please try again.");
                scanner.next();
            }
            input = scanner.nextInt();
            scanner.nextLine(); // Consume newline
        } while (input < min || input > max);
        return input;
    }

}
