package by.home.dao;

import by.home.model.DiscountCard;

public interface DiscountCardsDao {

    void insert(DiscountCard discountCard);
    void update(DiscountCard discountCard);
    void deleteById(Integer id);
    DiscountCard findById(Integer id);
}
