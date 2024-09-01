/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermarket.inventory;

public class Product extends Category{
	private int prodID;
	private String name;
	private double price;
	private int qty;
	
	public Product(String catId, String catName, int prodID, String name, double price, int qty) {
                super(catId,catName);
                
		this.prodID = prodID;
		this.name = name;
		this.price = price;
		this.qty = qty;
	}
	
	public int getID() {
		return prodID;
	}
	
	public void setID(int newprodID) {
		this.prodID = newprodID;
	}
	
	public String getName() {
		return name;
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
	
        
        public String toString() {
            return "-------------------------\n" +
           "ID:\t\t" + getID() + "\n" +
           "Name:\t\t" + getName() + "\n" +
           "Price:\t\t" + getPrice() + "$\n" +
           "Qty:\t\t" + getID() + "\n";
}
        

}