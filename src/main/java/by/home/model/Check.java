package by.home.model;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Check {
    public static final int HUNDRED_PERCENT = 100;
    private final int id;
    private Map<Product, Integer> products;
    private DiscountCard discountCard;
    private AtomicInteger ids = new AtomicInteger(0);

    public Check(Map<Product, Integer> products) {
        id = createId();
        this.products = products;
        discountCard = null;
    }

    public Check(Map<Product, Integer> products, DiscountCard discountCard) {
        id = createId();
        this.products = products;
        this.discountCard = discountCard;
    }

    public float getTotalPriceForProduct(Product product) {
        int productQuantity = products.get(product);

        if (checkProductForDiscount(product)) {
            return productQuantity
                    * product.getPrice()
                    * (HUNDRED_PERCENT - discountCard.getDiscount()) / HUNDRED_PERCENT;
        }

        return productQuantity * product.getPrice();
    }

    public float getTotalPriceForCheck() {
        return products.keySet().stream()
                .reduce(0f, (subtotal, product) -> subtotal + getTotalPriceForProduct(product), Float::sum);
    }

    public boolean checkProductForDiscount(Product product) {
        int productQuantity = products.get(product);

        return discountCard != null
                && product.hasDiscount()
                && productQuantity >= discountCard.getProductsQuantityForDiscount();
    }

    private int createId() {
        return ids.incrementAndGet();
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Product, Integer> products) {
        this.products = products;
    }

    public int getId() {
        return id;
    }

    public DiscountCard getDiscountCard() {
        return discountCard;
    }

    public void setDiscountCard(DiscountCard discountCard) {
        this.discountCard = discountCard;
    }
}
