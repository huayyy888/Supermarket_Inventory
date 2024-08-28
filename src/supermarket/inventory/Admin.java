/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermarket.inventory;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException; //To handle errors

/**
 *
 * @author KTYJ
 */
public class Admin {    
    private static String id,password;
    
    //Getter
    public String getID(){
        return Admin.id;
    }
    public String getPass(){
        return Admin.password;
    }
    
// Static block to initialize ID and password from the file
 static {
        Scanner scanner = new Scanner(System.in);
        File file = new File("admin.txt");

        // Check if the file exists; if not, create and initialize it
        if (!file.exists()) {
            initializeFile(file);
        }

        // Attempt to read the file
        if (!readFromFile(file)) {
            System.out.println("No login file found! Initialising with default values...");
            System.out.println("Press any key to continue...");
            scanner.next(); 
            // If reading fails or file content is incorrect, initialize the file again
            initializeFile(file);
            readFromFile(file);  // Attempt to read again after reinitialization
        }
    }
 

    // Method to read ID and password from the file
    private static boolean readFromFile(File file) {
        boolean success = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("id:")) {
                    id = line.split(":")[1].trim();
                } else if (line.startsWith("pass:")) {
                    password = line.split(":")[1].trim();
                }
            }

            // Check if both id and password were successfully read
            success = id != null && password != null;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return success;
    }

    // Method to initialize the file with default content
    private static void initializeFile(File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            // Writing default ID and password to the file
            writer.write("id:ganjungkook\n");
            writer.write("pass:wxh3588\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}