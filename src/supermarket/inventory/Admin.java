/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermarket.inventory;

//import java.util.Scanner;
//import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner; //To handle errors

/**
 *
 * @author KTYJ
 */
public class Admin {    
    private static String id,password; 
    public Admin() {
       
    }

    //Constructor 
    public Admin (String id,String password){
        Admin.id=id;
        Admin.password=password;
    }
   
    //Getter
    public String getID(){
        return Admin.id;
    }
    public String getPass(){
        return Admin.password;
    }

    //setter 
    public void setAdminID(String newId) {
        this.id = newId;
        saveToFile(); // Save the updated ID to the file
    }

    public  void setAdminPassword(String newPassword) {
        this.password = newPassword;
        saveToFile(); // Save the updated password to the file
    }
    
    // Static block to initialize ID and password from the file
    static {
        File file = new File("admin.txt");
        // Check if the file exists; if not, create and initialize it
        // Attempt to read the file
        if (!readFromFile(file)) {
            System.out.println("Initialising with default values...");
            // If reading fails or file content is incorrect, initialize the file again
            initializeFile(file);
            readFromFile(file);  // Attempt to read again after reinitialization
        }
    }
 

    // Method to read ID and password from the file          
    private static boolean readFromFile(File file) {     //File file = new File("admin.txt");
        boolean success = false;
        try (Scanner reader = new Scanner(file)) {
            String line;
            while ((reader.hasNextLine())) {    //method 2 to read file
                    line = reader.nextLine();
                    id = line.split(":")[0].trim();
                    password = line.split(":")[1].trim();
            }
            
            // Check if both id and password were successfully read
            success = id != null && password != null;
        } catch (IOException e) {
            System.out.println("Admin file error!");
            System.out.println(e.toString());  //prints the file exception, if exists.
        }
        
        return success;
    }
    
    // Method to initialize the file with default content
    private static void initializeFile(File file) {
        try (FileWriter writer = new FileWriter("admin.txt")) { //BufferedWriter is almost similar to FileWriter but it uses internal buffer to write data into File
            // Writing default ID and password to the file
            writer.write("admin:3588\n");
        } catch (IOException e) {
            System.out.println(e.toString());  //prints the file exception, if exists.
            System.out.println("File error!");
        }
    }

    private static void saveToFile() {
        File file = new File("admin.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(id + ":" + password + "\n");
        } catch (IOException e) {
            System.out.println(e.toString());
            System.out.println("File error while saving admin settings!");
        }
    }
}