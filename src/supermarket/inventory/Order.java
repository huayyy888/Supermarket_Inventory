package supermarket.inventory;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Order {
    private String orderId;
    private String date;
    private ArrayList<OrderItem> items;
    private double total;

    public Order() {
        this.orderId = generateOrderId();
        this.date = getCurrentDate();
        this.items = new ArrayList<>();
        this.total = 0.0;
    }

    public boolean addItem(OrderItem newItem) {
        Product newProduct = newItem.getProduct();

        for (OrderItem existingItem : items) {  //for each item in our itemlist(cart)
            if (existingItem.getProduct().getID().equals(newProduct.getID())) {
                // Product already exists in order, update quantity (add more)
                int newQuantity = existingItem.getQuantity() + newItem.getQuantity();
                if (newQuantity > newProduct.getQty()) {
                    return false; // Not enough stock
                }
                existingItem.setQuantity(newQuantity);  // if new qty is valid, set the new qty
                // Update total
                total += newItem.getQuantity() * newItem.getPrice();
                return true;    //product added successfully
            }
        }

        //item does not exist, check again
        if(newItem.getQuantity() > newProduct.getQty())
            return false;

        items.add(newItem);        //add to items arraylist
        total += newItem.getQuantity() * newItem.getPrice();    //add the 
        return true;
    }

    //AFTER ORDER IS PLACED
    public void processOrder(ArrayList<Product> productList) {
        for (OrderItem item : items) {
            Product product = item.getProduct();
            int newQuantity = product.getQty() - item.getQuantity();
            product.setQty(newQuantity);
        }
        Product.writeProd(productList); // Update the product.txt
    }

    public void displayOrder() {
        System.out.println("\n\u001B[33m=== Order ===\u001B[0m");
        System.out.println("Order ID: " + orderId);
        System.out.println("Date: " + date);
        for (OrderItem item : items) {
            System.out.printf("%20s x%2d - RM%.2f each\n", 
                              item.getProduct().getName(), item.getQuantity(), item.getPrice());
        }
        System.out.printf("\nTotal: RM%.2f\n", total);
        System.out.println("\u001B[33m=============\u001B[0m");
    }

    private String generateOrderId() {
        return String.format("%s-%03d", getCurrentDate(),(int)(Math.random()*1000));
    }

    private String getCurrentDate() {   //to get date of system
        LocalDate currentDate = LocalDate.now();    
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return currentDate.format(formatter);
    }
    
    public static void addOrder(Scanner scanner, ArrayList<Product> productList, ArrayList<Category> categories, Admin admin) {
        System.out.println("\u001B[33m" + "==============" + "\u001B[0m");
        System.out.println(" Create Order");
        System.out.println("\u001B[33m" + "==============" + "\u001B[0m");
        Order order = new Order();
        
        while (true) {
            System.out.println();
            System.out.println("\u001B[33m" + "**********************************" + "\u001B[0m");
            System.out.printf("\u001B[36mItems in cart: %d\u001B[0m\n", order.getItems().size());
            System.out.println("1. Add product by ID");
            System.out.println("2. Select product from category");
            System.out.println("3. Cart Overview");
            System.out.println("4. Finish Order/Exit");
            System.out.println("\u001B[33m" + "**********************************" + "\u001B[0m");
            System.out.print("Choose an option: ");
            
            int choice = -1;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                scanner.nextLine();
            }
            
            if (choice == 4) break;
            
            Product product = null;
            
            if (choice == 1) {
                System.out.print("Enter product ID: ");
                String productId = scanner.nextLine().trim();
                product = Product.findProductById(productList, productId);
            } else if (choice == 2) {
                product = Product.selectProductFromCat(scanner, categories, productList);
            } else if (choice == 3) {
                order.displayOrder();
                System.out.println("Press Enter to Continue...");
                scanner.nextLine();
                continue;
            } else {
                System.out.println("\u001B[31mInvalid choice. Please try again.\u001B[0m");
                continue;
            }
            
            if (product == null) {
                System.out.println("\u001B[31mProduct not found. Please try again.\u001B[0m");
                continue;
            }
            double prodPrice = product.getPrice();
            System.out.printf("\nProduct: %s\nPrice: RM%.2f\nStock Available: %d\n", 
                            product.getName(), prodPrice, product.getQty()); //10% profit from each product unit
            
            int quantity = -1;
            while (quantity < 0) {
                System.out.print("Enter quantity to order: ");
                try{
                    quantity = scanner.nextInt();
                    scanner.nextLine();
                    if (quantity <= 0) {
                        System.out.println("\u001B[31mPlease enter a valid quantity. (cannot be 0 or less)\u001B[0m");
                    }
                    else if (quantity > product.getQty()) {
                        System.out.println("\u001B[31mInsufficient stock("+ product.getQty() +" unit(s) left). Please enter a lower quantity.\u001B[0m");
                    }
                }
                catch(InputMismatchException e){
                    scanner.nextLine(); // Consume the invalid input
                    System.out.println("\u001B[31mPlease enter a valid quantity.\u001B[0m");
                }
            }   

            
            OrderItem item = new OrderItem(product, quantity, product.getPrice());
            if (order.addItem(item)) {
                System.out.println("\u001B[32mProduct added to cart.\u001B[0m");
            } else {
                System.out.println("\u001B[31mInsufficient stock. Product not added to cart.\u001B[0m");
            }
        }
        
        if (order.getItems().isEmpty()) {
            System.out.println("\u001B[31mNo items in cart! Order cancelled.\u001B[0m");
        }else{
            order.displayOrder();

            System.out.printf("Confirm order %s? (yes/no): ",order.orderId);
            String confirm = scanner.nextLine().trim();

            if (confirm.equalsIgnoreCase("yes")) {
                order.processOrder(productList);
                System.out.println("\u001B[32mOrder processed successfully!\u001B[0m");
                System.out.println("Your Order ID: " + order.getOrderId());
                System.out.println("Do you want to print a receipt? (y/n, default is n)");
                if(scanner.nextLine().equalsIgnoreCase("y")){
                    order.printReceipt(admin);
                }
            } else {
                System.out.println("\u001B[31mOrder cancelled.\u001B[0m");
            }
        }
        
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }

    public void printReceipt(Admin admin) {
        int itemCount = 0;
    String fileName = this.orderId + ".txt";
    try (FileWriter writer = new FileWriter(fileName)) {
        writer.write("               BOTITLE SUPERMARKET (KL BRANCH)\n");
        writer.write("                888, Lot 88, Jalan Klang 88,\n");
        writer.write("                  53300 Kuala Lumpur\n");
        writer.write("-------------------------------------------------------------\n");
        writer.write(String.format("Staff: %-10s\tOrder ID: %s\n", admin.getID(),this.orderId));
        writer.write("Date: " + this.date + "\n");

        writer.write("\n" + String.format("%-27s \t %8s", "Description","Amount (RM)\n"));
        writer.write("-------------------------------------------------------------\n");
        for (OrderItem item : this.items) {
            writer.write(String.format("%2d x %-27\t%4.2f\n", 
            item.getQuantity(), item.getProduct().getName(), item.getPrice()*item.getQuantity()));
            itemCount++;
        
        }

        writer.write("-------------------------------------------------------------\n");
        writer.write(String.format("\tItems: %d\tTotal: RM %4.2f \n", itemCount,this.total));
        writer.write(String.format("\t\t\t  Tax: RM %.2f\n", 0.0));
        writer.write("\nTHANK YOU FOR SHOPPING, PLS COME AGAIN\n");
        writer.write("GOODS SOLD ARE SUBJECT TO NO-REFUND POLICY\n");
        writer.write("-------------------------------------------------------------\n");

        System.out.println("\u001B[32mReceipt printed and stored as " + fileName + "\u001B[0m");
    } catch (IOException e) {
        System.out.println("\u001B[31mError printing receipt: " + e.toString() + "\u001B[0m");
    }
}

    // Getters
    public String getOrderId() { return orderId; }
    public String getDate() { return date; }
    public ArrayList<OrderItem> getItems() { return items; }
    public double getTotal() { return total; }
}