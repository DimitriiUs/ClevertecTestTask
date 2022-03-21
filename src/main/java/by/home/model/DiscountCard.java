package by.home.model;

public class DiscountCard {
    private int id;
    private int discount;
    private int productsQuantityForDiscount;

    public DiscountCard(int id) {
        this.id = id;
        discount = 10;
        productsQuantityForDiscount = 5;
    }

    public DiscountCard(int id, int discount, int productsQuantityForDiscount) {
        this.id = id;
        this.discount = discount;
        this.productsQuantityForDiscount = productsQuantityForDiscount;
    }

    public int getId() {
        return id;
    }

    public int getDiscount() {
        return discount;
    }

    public int getProductsQuantityForDiscount() {
        return productsQuantityForDiscount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DiscountCard that = (DiscountCard) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "DiscountCard{" +
                "id=" + id +
                '}';
    }
}
