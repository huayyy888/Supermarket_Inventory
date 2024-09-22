/**
 *  AACS2204 OOPT Assignment
 * @author TAN JIN YUAN, PATRICIA LEE HUAY, GAN KA CHUN, KER ZHENG FENG
 */
package supermarket.inventory;

public class OrderItem {
    private Product product;
    private int quantity;
    private double price;

    public OrderItem(Product product, int quantity, double price) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    //getter/setter
    public Product getProduct() {return product;}
    public int getQuantity() {return quantity;}
    public void setQuantity(int qty) {this.quantity = qty;}
    public double getPrice() {return price;}

}
