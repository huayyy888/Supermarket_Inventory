package supermarket.inventory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
//import java.util.InputMismatchException;
import java.util.Scanner;

public class Vendor {
    
    private String vendorID;
    private String vendorName;
    private String vendorContact;
    private String email;
    private ArrayList<Product> suppliedProducts;
    
    public Vendor(String vendorID, String vendorName, String vendorContact,String email, ArrayList<Product> suppliedProducts) {
        this.vendorID = vendorID;
        this.vendorName = vendorName;
        this.vendorContact = vendorContact;
        this.email = email;
        this.suppliedProducts = new ArrayList<>();
    }
    
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
        return String.format("Vendor ID: %s\nVendor Name: %s\nContact: %s\nEmail: %s", vendorID, vendorName, vendorContact,email);
    }

    // Select products for vendor
    public static void selectProductsForVendor(Scanner scanner, ArrayList<Vendor> vendorList, ArrayList<Product> productList, ArrayList<Category> catlist) {
        Vendor currentVendor = vendorList.get(vendorList.size() - 1);
        while (true) {
            Product.displayProd(productList);
            Product selectedProduct = Product.selectProductFromCat(scanner, catlist, productList);
            if (selectedProduct != null) {
                currentVendor.getSuppliedProducts().add(selectedProduct);  
                System.out.print("Do you want to add another product? (yes/no): ");
                if (!scanner.nextLine().trim().equalsIgnoreCase("yes")) {
                    break;
                }
            } else {
                System.out.println("No product selected. Moving on.");
                break;
            }
        }
    }
    
    // (C) Create new vendor
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
        } while (email.isEmpty() || !isValidEmail(email));
    
        // Generate Vendor ID
        String vendorID = generateUniqueVendorID(vendorList);
    
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
        System.out.print("Do you want to add this vendor? (yes/no, default is no): ");
        
        if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
            writeVendorsToFile(vendorList);
            System.out.println("New vendor created and saved successfully!");
        } else {
            vendorList.remove(newVendor);
            System.out.println("**Vendor addition canceled.**");
        }
    }

    // Generate unique vendor ID
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

    // Validate email
    public static boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
    
    // (R) Display vendors
    public static void displayVendors(ArrayList<Vendor> vendorList) {
        System.out.println("\n--DISPLAYING ALL VENDORS--\n");
        System.out.printf("%-10s | %-20s | %-15s | %-25s | %-30s\n", "Vendor ID", "Vendor Name", "Contact", "Email", "Supplied Products");
        System.out.println("-".repeat(110));
        for (Vendor vendor : vendorList) {
            String suppliedProducts = String.join(", ", 
                vendor.getSuppliedProducts().stream()
                    .map(Product::getName)
                    .collect(java.util.stream.Collectors.toList()));
            
            System.out.printf("%-10s | %-20s | %-15s | %-25s | %-30s\n",
                vendor.getVendorID(),
                vendor.getVendorName(),
                vendor.getVendorContact(),
                vendor.getEmail(),
                (suppliedProducts.length() > 100 ? suppliedProducts.substring(0, 100) + "..." : suppliedProducts));
            
            // If the product list is too long, print the rest on new lines
            if (suppliedProducts.length() > 30) {
                for (int i = 30; i < suppliedProducts.length(); i += 30) {
                    System.out.printf("%-74s | %-30s\n", "",
                        suppliedProducts.substring(i, Math.min(i + 30, suppliedProducts.length())));
                }
            }
            System.out.println("-".repeat(110));
        }
    }
    
    // Get vendor list (Read from file)
    public static ArrayList<Vendor> getVendorList() {
        ArrayList<Vendor> vendorList = new ArrayList<>();
        File file = new File("vendor.txt");
        
        // Check if the file exists, if not, create an empty one
        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("Created new vendor.txt file.");
            } catch (IOException e) {
                System.out.println("Error creating vendor.txt file: " + e.getMessage());
                return vendorList; // Return empty list if file can't be created
            }
        }
        
        try (Scanner fileReader = new Scanner(file)) {
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                String[] vendorData = line.split(",");
                if (vendorData.length >= 5) {
                    ArrayList<Product> suppliedProducts = new ArrayList<>();
                    if (vendorData.length > 4 && !vendorData[4].isEmpty()) {
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
            System.out.println("Error reading vendor.txt file: " + e.getMessage());
        }
        return vendorList;
    }
    

    public static void writeVendorsToFile(ArrayList<Vendor> vendorList) {
        try (FileWriter writer = new FileWriter("vendor.txt")) { // Remove 'true' to overwrite the file
            for (Vendor vendor : vendorList) {
                String suppliedProductNames = String.join("|", 
                    vendor.getSuppliedProducts().stream()
                        .map(Product::getName)
                        .collect(java.util.stream.Collectors.toList()));
                
                writer.write(String.format("%s,%s,%s,%s,%s\n",
                    vendor.getVendorID(),
                    vendor.getVendorName(),
                    vendor.getVendorContact(),
                    vendor.getEmail(),
                    suppliedProductNames));
            }
            System.out.println("Vendor file successfully updated!");
        } catch (IOException e) {
            System.out.println("vendor.txt error! Check file.");
            System.out.println(e.toString());
        }
    }
    
    // Edit & Update vendor
    public static void editVendor(Scanner scanner, ArrayList<Vendor> vendorList) {
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
        } else {
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
            
            writeVendorsToFile(vendorList);
            System.out.println("Vendor details updated successfully!");
        }
    }
    
    // (D) Delete vendor
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
            if (vendor.getSuppliedProducts().contains(selectedProduct)) {
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
        System.out.print("Enter the quantity to restock: ");
        int quantity = getValidInput(scanner, 1, Integer.MAX_VALUE);

        // Calculate cost (90% of product price)
        double costPerUnit = selectedProduct.getPrice() * 0.9;
        double totalCost = costPerUnit * quantity;

        // Display restock details and confirm
        System.out.println("\nRestock Details:");
        System.out.printf("Product: %s\n", selectedProduct.getName());
        System.out.printf("Vendor: %s\n", selectedVendor.getVendorName());
        System.out.printf("Quantity: %d\n", quantity);
        System.out.printf("Cost per unit: $%.2f\n", costPerUnit);
        System.out.printf("Total cost: $%.2f\n", totalCost);

        System.out.print("\nConfirm restock request? (yes/no): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
            // Here you would typically update inventory and financial records
            // For this example, we'll just print a confirmation message
            System.out.println("Restock request confirmed and sent to the vendor.");
        } else {
            System.out.println("Restock request cancelled.");
        }
    }

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
