package supermarket.inventory;

public class Vendor {
    private String vendorID;
    private String name;
    private String contactNumber;
    private String email;
    private String address;
    
    public Vendor(String vendorID, String name, String contactNumber, String email, String address) {
        this.vendorID = vendorID;
        this.name = name;
        this.contactNumber = contactNumber;
        this.email = email;
        this.address = address;
    }

    public String getVendorID() {
        return vendorID;
    }

    public void setVendorID(String vendorID) {
        this.vendorID = vendorID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    
    public String toString() {
        return String.format("Vendor ID: %d\nName: %s\nContact Number: %s\nEmail: %s\nAddress: %s", vendorID, name, contactNumber, email, address);
    }
}


