package supermarket.inventory;
/**
 *  AACS2204 OOPT Assignment
 * @author TAN JIN YUAN, PATRICIA LEE HUAY, GAN KA CHUN, KER ZHENG FENG
 */
public abstract class Item {
    protected String id;
    protected String name;

    public Item(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ID: " + id + "\n" + "Name: " + name + "\n";
    }

    // Abstract method to be implemented by subclasses
    public abstract String toFileString();
}