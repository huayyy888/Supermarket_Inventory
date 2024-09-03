/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermarket.inventory;

import java.io.BufferedWriter;
import java.io.File;
import java.util.ArrayList; // import the ArrayList class
import java.util.Scanner; // Import the Scanner class to read text files
import java.io.FileWriter;
import java.io.IOException; //To handle errors

/**
 *
 * @author KTYJ
 */
public class Category {
    private String catId;
    private String name;

    //Constructor and getter/setter
    public Category(String catId, String name) {
        this.catId = catId;
        this.name = name;
    }

    public String getCatId() {
        return catId;
    }
    public void setCatId(String catId) {
        this.catId = catId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return catId + "\t" + name;
    }
    
    public static ArrayList<Category> getCatList(){
        ArrayList<Category> categories = new ArrayList<>();
        
        try{
            String line;
            File catFile = new File("cat.txt");
            Scanner fReader = new Scanner(catFile);
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
    
   public static void dislayList(ArrayList<Category> catlist){
       System.out.println("ID\tName\n==================");
       for(Category c : catlist){
           System.out.println(c);
       }
   }
    
   public static void writeDebug(){
     String data = "CT001,Seafood\nCT002,Fruits\n";

        // Try with resources to ensure the file writer is closed properly
        try (FileWriter writer = new FileWriter("cat.txt",true)) {
            // Write the data to the file
            writer.write(data);
            System.out.println("Data has been written to cat.txt successfully.");
        } catch (IOException e) {
            // Handle potential IOExceptions
            System.out.println("An error occurred while writing to the file.");
            System.out.print(e.toString());
        }
    }
   
   public static void addCat(Scanner scanner,ArrayList<Category> catlist){
       String name;
       String id;
       boolean valid = false;
       boolean choice = false;
       scanner.nextLine(); //Consume newline like rewind(stdin);
       do{
            
            do{
                System.out.print("Enter Name:");
                name = scanner.nextLine();
                if(name == null || name.isEmpty())
                   System.out.print("Invalid Name! Try again.");
                else{
                    
                    
                    for(Category cat:catlist){
                        if(cat.getName().equals(name));
                    
                    }
                }
            }while(!valid);

            scanner.nextLine(); //Consume newline like rewind(stdin);
            System.out.print("Enter a ID: ");
            id = scanner.nextLine();
            
       }while(choice);
   }
}
