package supermarket.inventory;

import java.util.ArrayList;

public class Vendor {
    private String vendorId;
    private String vendorName;
    private String contactNumber;
    private String email;
    private ArrayList<Product> suppliedProducts;

    
    public Vendor(String vendorID, String name, String contactNumber, String email) {
        this.vendorId = vendorID;
        this.vendorName = name;
        this.contactNumber = contactNumber;
        this.email = email;
        suppliedProducts = new ArrayList<Product>();
    }

    public String getVendorId() {
        return vendorId;
    }
    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }
    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getContactNumber() {
        return contactNumber;
    }
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
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
    
    public void addSuppliedProduct(Product product) {
        this.suppliedProducts.add(product);
    }

    public void removeSuppliedProduct(Product product) {
        this.suppliedProducts.remove(product); // Remove a product from the vendor’s list
    }
    
    public void displaySuppliedProducts() {
        if (suppliedProducts.isEmpty()) {
            System.out.println(vendorName + " has no products!");
        } else {
            System.out.println("Products supplied by " + vendorName + ":");
            for (Product product : suppliedProducts) {
                System.out.println("- " + product.getName() + " (ID: " + product.getID() + ")");
            }
        }
    }
    
    public String toString() {
        return String.format("Vendor ID: %s\nName: %s\nContact: %s\nEmail: %s\nSupplied Products: %d",
                vendorId, vendorName, contactNumber, email, suppliedProducts.size());
    }
}


