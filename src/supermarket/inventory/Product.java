/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermarket.inventory;

public class Product {
	private int prodID;
	private String name;
	private String description;
	private double price;
	private int qty;
	
	public Product() {
		
	}
	
	public Product(int prodID, String name, String description, double price, int qty) {
		this.prodID = prodID;
		this.name = name;
		this.description = description;
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
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
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
	
	public void print() {
		System.out.println("-------------------------");
		System.out.println("ID:\t\t"+getID());
		System.out.println("Name:\t\t"+getName());
		System.out.println("Description:\t"+getDescription());
		System.out.println("Price:\t\t"+getPrice()+"$");
		System.out.println("Qty:\t\t"+getID());
	}

}