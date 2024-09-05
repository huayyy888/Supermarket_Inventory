/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermarket.inventory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Product extends Category{

    
	private String prodID;
	private String name;
	private double price;
	private int qty;
	
	public Product(String catId, String catName, String prodID, String name, double price, int qty) {
                super(catId,catName);
             
		this.prodID = prodID;
		this.name = name;
		this.price = price;
		this.qty = qty;
	}
	
	public String getID() {
		return prodID;
	}
	
	public void setID(String newprodID) {
		this.prodID = newprodID;
	}
	
        public String getName() {
		return this.name;
	}
	
        public void setName(String name) {
		this.name = name;
	}
	
        public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public int getQty() {
		return qty;
	}
	
	public void setQty(int qty) {
		this.qty = qty;
	}
	
        
        @Override
       public String toString() {
        return String.format("%s\n-------------------------\n" +
            "SKU:\t\t%s\n" +
            "Name:\t\t%s\n" +
            "Price:\t\tRM%.2f\n" +
            "Qty:\t\t%d\n",super.toString(),
            getID(), getName(), getPrice(),getQty());
        }
        
        public static void createNewProduct(Scanner scanner, ArrayList<Category> categories) {
        do{
            System.out.println("\n\n\u001B[33m--ADD A PRODUCT--\u001B[0m\n\n");
        // Display list of categories to the user
        System.out.println("\u001B[33mSELECT A CATEGORY BELOW\u001B[0m");
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i).getCatName());
        }

        int categoryChoice = -1;
        while (categoryChoice < 1 || categoryChoice > categories.size()) {
            System.out.print("Enter the number corresponding to the category: ");
             try {
                categoryChoice = scanner.nextInt();
                // consumes the dangling newline character
                scanner.nextLine();
                if(categoryChoice < 1 || categoryChoice > categories.size()){
                    System.out.println("\u001B[31mPlease enter a valid NUMBER.\u001B[0m");
                }                
            } catch (InputMismatchException e) {
                scanner.nextLine(); // Consume the invalid input
                System.out.println("\u001B[31mPlease enter a valid NUMBER.\u001B[0m");
            }   
        }
        
        Category selectedCategory = categories.get(categoryChoice - 1);
         String productName = "";
        // Prompt the user to enter product details
        boolean valid = false;
        do{
            System.out.print("Enter product name: ");
            productName = scanner.nextLine();
            if(productName.length()>2){
                valid = true;                   
                productName = productName.substring(0, 1).toUpperCase() + productName.substring(1).toLowerCase();
            }
            else
                System.out.println("\u001B[31mPlease enter a valid NAME.\u001B[0m");
        }while(!valid);
        
        double productPrice = -1;
        while (productPrice < 0) {
            System.out.print("Enter product price: RM");
            try{
                productPrice = scanner.nextDouble();
                scanner.nextLine();
                if (productPrice < 0) {
                    System.out.println("\u001B[31mPlease enter a valid PRICE.\u001B[0m");
                }
            }
            catch(InputMismatchException e){
                scanner.nextLine(); // Consume the invalid input
                System.out.println("\u001B[31mPlease enter a valid PRICE.\u001B[0m");
            }
        }

        int productQty = -1;
        while (productQty < 0) {
            System.out.print("Enter product quantity: ");
            try{
                productQty = scanner.nextInt();
                scanner.nextLine();
            if (productQty < 0) {
                System.out.println("\u001B[31mQuantity cannot be negative. Please enter a valid quantity.\u001B[0m");
            }
            }
            catch(InputMismatchException e){
                scanner.nextLine(); // Consume the invalid input
                System.out.println("\u001B[31mPlease enter a valid QUANTITY.\u001B[0m");
            }
        }

        int firstDigit = (int) (Math.random() * 9) + 1;

        // Generate a random number between 0 and 999999
        int remainingDigits = (int) (Math.random() * 999999);

        // Combine the digits to form the 7-digit SKU
        String productID = String.format("%07d", firstDigit * 1000000 + remainingDigits);
        
        // Create a new Product object with the provided details
        Product newProduct = new Product(selectedCategory.getCatId(), selectedCategory.getCatName(), productID, productName, productPrice, productQty);
        
        // Display the product details and ask for confirmation
        System.out.println("\n\u001B[33mPlease confirm the following details:\u001B[0m");
        System.out.println(newProduct);
        System.out.print("Do you want to add this product? (yes/no, default is no): ");
        
        if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
            // Write product data to product.txt file
            try (FileWriter writer = new FileWriter("product.txt", true)) {
                writer.write(newProduct.getCatId() + ","+ productID + "," + productName + "," + productPrice + "," + productQty + "\n");
                System.out.println("\u001B[32mNew product \"+ product +\"created and saved successfully!\u001B[0m");
            } catch (IOException e) {
                System.out.println("\u001B[31mAn error occurred while writing to the file.\u001B[0m");
                System.out.print(e.toString());
            }
        } else {
            System.out.println("\u001B[31m**Product addition canceled.**\u001B[0m");
        }
        
        System.out.print("\nDo you want to add another product? (yes/no, default is no): ");
        } while (scanner.nextLine().equalsIgnoreCase("yes"));
    }
        
    public static void displayProd(ArrayList<Product> productList) {
        System.out.println("\u001B[33m===DISPLAYING PRODUCTS BY CATEGORY====\u001B[0m" + "\n\n\n");

        //CHECK PRINTED CATEGORIES
        ArrayList<String> printedCategories = new ArrayList<>();
        
        for (Product product : productList) {
            String categoryName = product.getCatName();
            String categoryID = product.getCatId();
            
            // If category has already been printed, skip it
            if (!printedCategories.contains(categoryName)) {
                printedCategories.add(categoryName);

                // Print header
                System.out.println("\u001B[33mCATEGORY: " + categoryName + " " + categoryID + "\u001B[0m");
                System.out.println("---------------------------------------------------");
                System.out.printf("%-10s %-20s %-10s %-10s%n", "Product ID", "Name", "Price(RM)", "Quantity");
                System.out.println("---------------------------------------------------");

                // Print all products in this category
                for (Product p : productList) {
                    if (p.getCatId().equals(categoryID)) {
                        System.out.printf("%-10s %-20s %-10.2f %-10d\n",
                                p.getID(),
                                p.getName(),
                                p.getPrice(),
                                p.getQty());
                    }
                }
                System.out.println(); // Blank line 
            }
        }
        
    }    
        
    public static ArrayList<Product> getProdList( ArrayList<Category> catlist){
        ArrayList<Product> prodList = new ArrayList<>();
        
        try{
            String line;
            Scanner fReader = new Scanner(new File("product.txt"));
            while(fReader.hasNextLine()){
                line = fReader.nextLine();
                String[] prodData = line.split(",");
                String catId = prodData[0].trim();       //e.g CT:001,
                String prodID = prodData[1].trim();       //e.g CT:001,
                String prodName = prodData[2].trim();
                double price = Double.parseDouble(prodData[3].trim());
                int qty= Integer.parseInt(prodData[4].trim());
                
                String categoryName = "\u001B[31unknown\u001B[0m";
                
                for(Category category:catlist){
                    if(catId.equals(category.getCatId()))
                        categoryName = category.getCatName();
                }
                
                

                
                prodList.add(new Product(catId, categoryName, prodID, prodName, price, qty));
            }
            fReader.close();
        }
        catch(IOException | NumberFormatException e){
        System.out.println("\u001B[31m****prod.txt error! Check file***\u001B[0m");
            System.out.println(e.toString());  //prints the file exception, if exists.
            System.exit(-1);
        }
            //prints the file exception, if exists.
        
        return prodList;

   }
}