/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermarket.inventory;

import java.io.File;
import java.util.ArrayList; // import the ArrayList class
import java.util.Scanner; // Import the Scanner class to read text files
import java.io.FileWriter;
import java.io.IOException; //To handle errors
import java.util.InputMismatchException;
import supermarket.inventory.Product;

/**
 *
 * @author KTYJ
 */
public class Category {
    private String catId;
    private String catName;

    //Constructor and getter/setter
    public Category(String catId, String name) {
        this.catId = catId;
        this.catName = name;
    }

    public String getCatId() {
        return catId;
    }
    public void setCatId(String catId) {
        this.catId = catId;
    }
    public String getCatName() {
        return catName;
    }
    public void setCatName(String name) {
        this.catName = name;
    }

    @Override
    public String toString() {
        return catId + "\t" + catName;
    }
    
    public static ArrayList<Category> getCatList(){
        ArrayList<Category> categories = new ArrayList<>();
        
        try{
            String line;
            Scanner fReader = new Scanner(new File("cat.txt"));
            while(fReader.hasNextLine()){
                line = fReader.nextLine();
                String catId = line.split(",")[0].trim();       //e.g CT:001,
                String name = line.split(",")[1].trim();
                categories.add(new Category(catId,name));
            }
            
            fReader.close();
        }
        catch(IOException e){
            System.out.println("cat.txt error!");
            System.out.println(e.toString());  //prints the file exception, if exists.
            System.exit(-1);
        }
        
        return categories;

   }
    
   public static void displayList(ArrayList<Category> catlist){
       System.out.println("\u001B[33m--LIST OF CATEGORIE(S)--\u001B[0m");
       System.out.println("ID\tName\n==================");
       for(Category c : catlist){
           System.out.println(c);
       }
       System.out.println("\nShowing "+ catlist.size() +" Categories....");
   }
    
    public static void addCat(Scanner scanner, ArrayList<Category> catlist) {
    String name;
    String id;
    System.out.println("\u001B[33m--ADD A CATEGORY--\u001B[0m");
    do {
        boolean valid = true;
        // Get valid category name
        do {
            System.out.println("\u001B[36mNOTE: Press Enter without input to return to menu.\u001B[0m");
            System.out.print("Enter a category name: ");
            name = scanner.nextLine().trim();
            if(name.isEmpty()){
                return;
            }
            if (!checkCat(name)) { 
                //
                System.out.println("\u001B[31m"+"Invalid Name! It should contain ONLY ALPHABETICAL CHARACTERS"+"\033[0m");
                valid = false;
            } else {
                name = formatCat(name);
                valid = true;  

                for (Category cat : catlist) {
                    if (cat.getCatName().equals(name)) {
                        valid = false;  
                        break;  // Exit loop once a match is found
                    }
                }

                if (!valid)
                    System.out.println("\u001B[31m"+"Category already exists! (Same name)"+"\033[0m");
            }

        } while (!valid);

        // Get valid category ID
        do {
            id = "CT"; // Initialize an empty string
            for (int i = 0; i < 3; i++) {
                int randomDigit = (int)(Math.random()*10); // Generate a random integer between 0 and 9
                id += randomDigit; // Concatenate the random integer to the result string
            }          
            valid = true;  
            for (Category cat : catlist) {
                if (cat.getCatId().equals(id)) {
                    valid = false;  
                    break;  // Exit loop once a match is found
                }
            }
        }while (!valid);
        
        System.out.print("\nAre you sure you want to add \""+ id +" "+ name+"\" category? (yes/no,default is no): ");
        if (scanner.nextLine().equalsIgnoreCase("yes")) {
                // Add the new category to the list
                Category cat = new Category(id, name);
                catlist.add(cat); // Add the new category to the list
                
                //write to file!
                String data = id+","+name+"\n";
                // Try with resources to ensure the file writer is closed properly
                try (FileWriter writer = new FileWriter("cat.txt",true)) {
                    // Write the data to the file
                    writer.write(data);
                    System.out.println("\033[32;1m" + cat.toString() + " SUCESSFULLY ADDED TO FILE" + "\033[0m");
                } catch (IOException e) {
                    // Handle potential IOExceptions
                    System.out.println("\u001B[31m"+"An error occurred while writing to the file.");
                    System.out.print(e.toString()+ "\033[0m");
                }
        } else {
                System.out.println("\u001B[31m**Category addition canceled.**"+ "\033[0m");
        }
        


        // Ask if the user wants to add another category
        System.out.print("\nDo you want to add another category? (yes/no, default is no): ");
    } while (scanner.nextLine().equalsIgnoreCase("yes"));
}

   
   public static String formatCat(String string){
       return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
   }
   

    //check if long enough and consist only numbers and whitespaces
   private static Boolean checkCat(String string){
       if(string.length()<2)
            return false;
       else if(Character.isWhitespace(string.charAt(0))){
           return false;
       }
       else{
           for(int i=0;i<string.length();i++){
              if(!(Character.isAlphabetic(string.charAt(i))))
                  return false;
           }
   
           return true;
       }
   }

    
   public static void manageCat(Scanner scanner, ArrayList<Category> catlist, ArrayList<Product> productList) {
        do{
            System.out.println("\u001B[33m--MANAGE CATEGORIES--\u001B[0m");
            System.out.println("No.  ID\tName\n==========================");
        for (int i = 0; i < catlist.size(); i++) {
            System.out.println((i + 1) + ".  " + catlist.get(i));
        }
        int index = -1;
        do{
            System.out.print("\nSelect a category from list by number (0 to exit): ");
           try {
                index = scanner.nextInt();
                // consumes the dangling newline character
                scanner.nextLine();
                if(index == 0)
                    return;
                else if(index < 1 || index>catlist.size())
                    System.out.println("\u001B[31m"+"Please enter a valid selection!"+"\033[0m");
            } catch (InputMismatchException e) {
                scanner.nextLine(); // Consume the invalid input
                System.out.println("\u001B[31m"+"Please enter a valid selection!"+"\033[0m");
            }
        }while(index<1 || index>catlist.size());
        
        Category cat2Edit = catlist.get(index-1);
        
        int choice = 0;
        boolean hasProducts = false;
        for(Product prod: productList){
            if(prod.getCatId().equals(cat2Edit.getCatId())){
            hasProducts = true;
            break;
            }
        }
            System.out.println("\n\u001B[33m== Current Category Details ==\u001B[0m");
            System.out.print(cat2Edit);
            System.out.println("\n\u001B[33m==============================\u001B[0m");
            System.out.println("1. Change Category NAME");
            System.out.print((hasProducts) ? "\u001B[31mCannot delete category as there are products associated with this category!\u001B[0m" : "2. DELETE This Category");
            System.out.print("\nWhat would you like to edit? : ");
            try {
                choice = scanner.nextInt();
                // consumes the newline character
                scanner.nextLine();
            } catch (InputMismatchException e) {
                scanner.nextLine(); // Consume the invalid input
                //System.out.println("\u001B[31mINVALID INPUT! Modification Cancelled..\u001B[0m");
            }
            switch(choice){
                case 1:
                    String newName;
                    do {
                        System.out.printf("Current Name :%s\n",cat2Edit.getCatName());
                        System.out.print("Enter the new category name OR PRESS ENTER TO SKIP: ");
                        newName = scanner.nextLine().trim();
                        if(!newName.isEmpty()){
                            if (newName.length() > 1 && checkCat(newName)) {
                                cat2Edit.setCatName(formatCat(newName));  // Update the name
                                System.out.printf("\u001B[32mCategory name changed to %s!\n\u001B[0m",cat2Edit.getCatName());
                                writeCat(catlist);  // Update the file
                            } else {
                                System.out.println("\u001B[31mInvalid name! Please try again.\u001B[0m");
                            }
                        }
                        else
                            break;
                    } while (!(newName.length() > 1 && checkCat(newName)));
                    break;
                case 2:
                    if(!hasProducts){
                        System.out.print("\u001B[32mAre you sure you want to delete this category? (y/n,default n): \u001B[0m");
                        if (scanner.nextLine().equalsIgnoreCase("y")) {
                            catlist.remove(cat2Edit);  // Remove from list
                            writeCat(catlist);  // Update the file
                            System.out.println("\u001B[32mCategory "+ cat2Edit.getCatName() +" deleted successfully!\u001B[0m");
                        } else {
                            System.out.println("\u001B[31mCategory deletion canceled.\u001B[0m");
                        }
                        break;
                    }
                default:
                    System.out.println("\u001B[31mINVALID CHOICE! Modification Cancelled\u001B[0m");
            }
            System.out.print("\n Do you wish to manage another category? (y/n, default no): ");
        }while(scanner.nextLine().trim().equalsIgnoreCase("y"));  
        System.out.println("\n\n\n\n\n\n\n");
   }
   
   public static void writeCat(ArrayList<Category> catlist) {
    try (FileWriter writer = new FileWriter("cat.txt")) {
        for (Category cat : catlist) {
            writer.write(cat.getCatId() + "," + cat.getCatName() + "\n");
        }
    } catch (IOException e) {
        System.out.println("\u001B[31mError writing to cat.txt file!\u001B[0m");
        System.out.println(e.toString());  //prints the file exception, if exists.
    }
}
}
