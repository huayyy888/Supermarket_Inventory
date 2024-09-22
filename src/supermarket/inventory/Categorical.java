package supermarket.inventory;

/**
 *  AACS2204 OOPT Assignment
 * @author TAN JIN YUAN, PATRICIA LEE HUAY, GAN KA CHUN, KER ZHENG FENG
 */
public interface Categorical {
    public abstract String getCatId();
    public abstract String getCatName();
    public abstract void setCatName(String catName);
    public abstract void setCatId(String catId);
}