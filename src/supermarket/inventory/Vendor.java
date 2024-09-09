package supermarket.inventory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Vendor {
    
    private String vendorID;
    private String vendorName;
    private String vendorContact;
    private String email;
    private ArrayList<Product> suppliedProducts;
    
    public Vendor(String vendorID, String vendorName, String vendorContact,String email) {
        this.vendorID = vendorID;
        this.vendorName = vendorName;
        this.vendorContact = vendorContact;
        this.email = email;
        suppliedProducts = new ArrayList<>();
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
    
    public static void createNewVendor(Scanner scanner, ArrayList<Vendor> vendorList,ArrayList<Product> productList) {
        //display product list
        Product.displayProd( productList);

        //!----------------SELECT PROD SUPPLIER PROC----------------------

        System.out.println("\n\n--ADD A NEW VENDOR--\n");
        
        // Vendor Name Input
        System.out.print("Enter vendor name: ");
        String vendorName = scanner.nextLine().trim();
        
        // Vendor Contact Input
        System.out.print("Enter vendor contact: ");
        String vendorContact = scanner.nextLine().trim();
        
        //email 
        System.out.print("Enter vendor email: ");
        String email = scanner.nextLine().trim();

        // Generate Vendor ID
        int firstDigit = (int) (Math.random() * 9) + 1;
        int remainingDigits = (int) (Math.random() * 999999);
        String vendorID = String.format("%07d", firstDigit * 1000000 + remainingDigits);

        Vendor newVendor = new Vendor(vendorID, vendorName, vendorContact,email);
        
        // Display all details and confirm
        System.out.println("\nPlease confirm the following details:");
        System.out.println(newVendor);
        System.out.print("Do you want to add this vendor? (yes/no, default is no): ");
        
        if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
            // Save to file
            try (FileWriter writer = new FileWriter("vendor.txt", true)) {
                writer.write(vendorID + "," + vendorName + "," + vendorContact + "\n");
                System.out.println("New vendor created and saved successfully!");
            } catch (IOException e) {
                System.out.println("File error! Check file.");
                System.out.println(e.toString());
            }
        } else {
            System.out.println("**Vendor addition canceled.**");
        }
    }
    
    public static void displayVendors(ArrayList<Vendor> vendorList) {
        System.out.println("\n--DISPLAYING ALL VENDORS--\n");
        for (Vendor vendor : vendorList) {
            System.out.println(vendor);
            System.out.println("-----------------------------------");
        }
    }
    
    public static ArrayList<Vendor> getVendorList() {
        ArrayList<Vendor> vendorList = new ArrayList<>();
        try (Scanner fileReader = new Scanner(new File("vendor.txt"))) {
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                String[] vendorData = line.split(",");
                vendorList.add(new Vendor(vendorData[0].trim(), vendorData[1].trim(), vendorData[2].trim(),vendorData[3].trim()));
            }
        } catch (IOException e) {
            System.out.println("File error! Check file.");
            System.out.println(e.toString());
        }
        return vendorList;
    }
    
    public static void writeVendors(ArrayList<Vendor> vendorList) {
        try (FileWriter writer = new FileWriter("vendor.txt")) {
            for (Vendor vendor : vendorList) {
                writer.write(vendor.getVendorID() + "," + vendor.getVendorName() + "," + vendor.getVendorContact() + "\n");
            }
            System.out.println("Vendor file successfully updated!");
        } catch (IOException e) {
            System.out.println("vendor.txt error! Check file.");
            System.out.println(e.toString());
        }
    }
    
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
                writeVendors(vendorList);
                System.out.println("Vendor deleted successfully!");
            } else {
                System.out.println("**Vendor deletion canceled.**");
            }
        }
    }
    
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
            
            writeVendors(vendorList);
            System.out.println("Vendor details updated successfully!");
        }
    }
}
