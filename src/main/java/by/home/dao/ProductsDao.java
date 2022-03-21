package by.home.dao;

import by.home.model.Product;

public interface ProductsDao {

    void insert(Product product);
    void update(Product product);
    void deleteById(Integer id);
    Product findById(Integer id);
}
