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
        return String.format("%s\n" +
            "SKU\t:%s\n" +
            "Name\t:%s\n" +
            "Price\t:RM%.2f\n" +
            "Qty\t:%d\n",super.toString(),
            getID(), getName(), getPrice(),getQty());
        }
        
        public static void createNewProduct(Scanner scanner, ArrayList<Category> categories) {
        do{
            System.out.println("\n\n\u001B[33m--ADD A PRODUCT--\u001B[0m\n\n");
            // Display categories list
            System.out.println("\u001B[33mSELECT A CATEGORY BELOW\u001B[0m");
            for (int i = 0; i < categories.size(); i++) {
                System.out.println((i + 1) + ". " + categories.get(i).getCatName());
            }

        //CATEGORY SELECT
        int categoryChoice = -1;
        while (categoryChoice < 1 || categoryChoice > categories.size()) {
            System.out.print("Enter the number corresponding to the category: ");
             try {
                categoryChoice = scanner.nextInt();
                // consumes the newline character
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
        // NAME SELECT
        boolean valid = false;
        do{
            System.out.print("Enter product name: ");
            productName = scanner.nextLine();
            if(productName.length()>2){
                productName = formatProdName(productName);
                valid = true;
            }
            else
                System.out.println("\u001B[31mPlease enter a valid NAME.\u001B[0m");
        }while(!valid);
        
        //PRICE SELECT
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

        //SELECT QTY
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

        int remainingDigits = (int) (Math.random() * 999999);
        String productID = String.format("%07d", firstDigit * 1000000 + remainingDigits);
        
        Product newProduct = new Product(selectedCategory.getCatId(), selectedCategory.getCatName(), productID, productName, productPrice, productQty);
        
        // Display ALL DETAIL N ask for confirmation
        System.out.println("\n\u001B[33mPlease confirm the following details:\u001B[0m");
        System.out.println(newProduct);
        System.out.print("Do you want to add this product? (yes/no, default is no): ");
        
        if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
            // Write data to product.txt file
            try (FileWriter writer = new FileWriter("product.txt", true)) {
                writer.write(newProduct.getCatId() + ","+ productID + "," + productName + "," + productPrice + "," + productQty + "\n");
                System.out.println("\u001B[32mNew product \"+ product +\"created and saved successfully!\u001B[0m");
            } catch (IOException e) {
                System.out.println("\u001B[31m****File error! Check file***\u001B[0m");
                System.out.println(e.toString());  
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

                //header
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
                System.out.println(); //\n\n
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
                String prodID = prodData[1].trim();       //e.g 7849662
                String prodName = prodData[2].trim();
                double price = Double.parseDouble(prodData[3].trim());
                int qty= Integer.parseInt(prodData[4].trim());
                
                String categoryName = "\u001B[31unknown\u001B[0m";  //if no matching id is found
                
                for(Category category:catlist){
                    if(catId.equals(category.getCatId()))
                        categoryName = category.getCatName();
                }
                
                prodList.add(new Product(catId, categoryName, prodID, prodName, price, qty));
            }
            fReader.close();
        }
        catch(IOException | NumberFormatException e){
            System.out.println("\u001B[31m****product.txt error! Check file***\u001B[0m");
            System.out.println(e.toString());  //prints the file exception, if exists.
            System.exit(-1);
        }
            //prints the file exception, if exists.
        
        return prodList;

   }
    
    public static void writeProd(ArrayList<Product> productList) {
        try (FileWriter writer = new FileWriter("product.txt")) {
            for (Product product : productList) {
                writer.write(product.getCatId() + "," + product.getID() + "," + product.getName() + "," + product.getPrice() + "," + product.getQty() + "\n");
            }
            System.out.println("\u001B[32mProduct file successfully updated!\u001B[0m");
        } catch (IOException e) {
            System.out.println("\u001B[31m****product.txt error! Check file***\u001B[0m");
            System.out.println(e.toString());  //prints the file exception, if exists.
        }
    }
    
    public static void deleteProd(ArrayList<Product> productList) {
    Scanner scanner = new Scanner(System.in);    
    do {
        System.out.println("\n\n\u001B[33m--DELETE A PRODUCT--\u001B[0m\n");
        System.out.print("Enter the product number/ID to delete (e.g. 1234567): ");
        String prodID = scanner.nextLine().trim();

        // Search for the product in the product list
        Product productToDelete = null;
        for (Product product : productList) {
            if (product.getID().equals(prodID)) {
                productToDelete = product;
                break;
            }
        }

        if (productToDelete == null) {
            // If product not found, ask if the user wants to search again
            System.out.print("\u001B[31mProduct not found! \u001B[0m");
        } else {
            // If the product is found, confirm deletion
            System.out.println("\n\u001B[33mPlease confirm you want to delete the following product:\u001B[0m");
            System.out.println(productToDelete);

            System.out.print("Do you want to delete this product permanently? (yes/no, default is no): ");
            if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
                // Update product list & file
                productList.remove(productToDelete);
                writeProd(productList);
            } else {
                System.out.println("\u001B[31m**Product deletion canceled.**\u001B[0m");
            }
        }
        
        System.out.print("\nDo you want to try again? (yes/no, default is no): ");
        
    } while (scanner.nextLine().trim().equalsIgnoreCase("yes"));
    }
    
    public static void editProdMenu(Scanner scanner,ArrayList<Product> productList, ArrayList<Category> categories) {
        do{
            System.out.print("Enter a product number/ID (e.g. 1234567): ");
            String prodID = scanner.nextLine().trim();

        // Search for the product in the product list
            Product prod = null;
            for (Product product : productList) {
                if (product.getID().equals(prodID)) {
                    prod = product;
                    break;
                }
            }
            if(prod == null){
                System.out.println("\u001B[31mProduct not found! \u001B[0m");
            }else{
                editProd(scanner,prod,categories);
                System.out.print("\n\n\n" + prod);
            }
            writeProd(productList); //The updated value is already inside
            System.out.print("\nDo you want to try again? (yes/no, default is no): ");
        }while(scanner.nextLine().trim().equalsIgnoreCase("yes"));
    }
    
    private static void editProd(Scanner scanner,Product product2Edit,ArrayList<Category> categories){
        System.out.println("\n\n\u001B[33m--EDIT A PRODUCT--\u001B[0m\n");           
        int choice;
        boolean again;
        do{
            choice = 0;
            again = false;
            System.out.println("\n\u001B[33m=========Current Product Details=========\u001B[0m");
            System.out.print(product2Edit);
            System.out.println("\u001B[33m=========================================\u001B[0m");
            System.out.println("1. Category");
            System.out.println("2. Name");
            System.out.println("3. Price");
            System.out.println("4. Quantity");
            
            System.out.println("\n5. SAVE CHANGES AND EXIT");

            
                System.out.print("What would you like to edit? : ");
                try {
                    choice = scanner.nextInt();
                    // consumes the newline character
                    scanner.nextLine();         
                    
                } catch (InputMismatchException e) {
                    scanner.nextLine(); // Consume the invalid input
                        System.out.println("\u001B[31mPlease enter a VALID choice.\u001B[0m");
                    }   
                if(choice == 5){    //exit
                    System.out.print("\u001B[33m\nSave your changes and exit? (y/n, default is n): \u001B[0m");
                    again = scanner.nextLine().trim().equalsIgnoreCase("y");
                }
                else if(choice == 1){   //category
                    System.out.println("\n\u001B[33mSELECT A NEW CATEGORY or press Enter to keep the current category:\u001B[0m");
                    for (int i = 0; i < categories.size(); i++) {
                        System.out.println((i + 1) + ". " + categories.get(i).getCatName());
                    }
                    System.out.printf("Enter the number corresponding to the new category (current: %s %s):",product2Edit.getCatId(),product2Edit.getCatName());
                    String categoryChoice = scanner.nextLine().trim();
                    if (!categoryChoice.isEmpty()) {
                        try {
                            int catchoice = Integer.parseInt(categoryChoice);
                            if (choice >= 1 && choice <= categories.size()) {
                                Category newCategory = categories.get(choice - 1);
                                product2Edit.setCatId(newCategory.getCatId());
                                product2Edit.setCatName(newCategory.getCatName());
                            } else {
                                System.out.println("\u001B[31mInvalid category choice.\u001B[0m");
                            }
                        } catch (NumberFormatException e) {
                                System.out.println("\u001B[31mPlease enter a valid NUMBER.\u001B[0m");
                        }
                    }
                }                
                else if(choice == 2){   //namme
                    System.out.print("Enter new product name (current: " + product2Edit.getName() + "): ");
                    String newName = scanner.nextLine().trim();
                    if (!newName.isEmpty() && newName.length() > 2) {
                        product2Edit.setName(Product.formatProdName(newName));
                    }
                }
                else if(choice == 3){   //price
                    double productPrice = -1;
                    while (productPrice < 0) {
                        try{
                            System.out.printf("Enter new product price (current: RM%.2f): ",product2Edit.getPrice());
                            productPrice = scanner.nextDouble();
                            scanner.nextLine();
                            if (productPrice < 0) {
                                System.out.println("\u001B[31mPlease enter a valid PRICE.\u001B[0m");
                            }
                            else{
                                product2Edit.setPrice(productPrice);
                            }
                        }
                        catch(InputMismatchException e){
                            scanner.nextLine(); // Consume the invalid input
                            System.out.println("\u001B[31mPlease enter a valid PRICE.\u001B[0m");
                        }
                    }
                }
                else{
                    System.out.println("\u001B[31mPlease enter a VALID choice.\u001B[0m");
                }
        }while(!again);
        
    }
    
    public static String formatProdName(String productName){
    //To format the name of a product (with or w/o multiple words)
         String[] tempStr = productName.split(" ");
                productName = ""; //reset the variable temporary, but set it later after formating
                for(String str: tempStr){
                    if(!str.isEmpty()){
                        productName += (str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase() + " ");
                    }
                }
                return productName.trim();
    }
}