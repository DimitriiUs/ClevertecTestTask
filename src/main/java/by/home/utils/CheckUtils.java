package by.home.utils;

import by.home.dao.DiscountCardsDao;
import by.home.dao.ProductsDao;
import by.home.dao.impl.DiscountCardsDaoImpl;
import by.home.dao.impl.ProductsDaoImpl;
import by.home.db.ConnectionManager;
import by.home.db.DiscountCardDB;
import by.home.db.ProductDB;
import by.home.model.Check;
import by.home.model.DiscountCard;
import by.home.model.Product;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class CheckUtils {

    private final String DELIMITER = "-";
    private final String DISCOUNT_CARD = "card";
    private final int AFTER_DELIMITER = 1;
    private final int BEFORE_DELIMITER = 0;

    public Check getCheck(String[] args, ProductDB productDB, DiscountCardDB discountCardDB) {

        DiscountCard discountCard = null;
        Map<Product, Integer> products = new HashMap<>();

        for (String arg : args) {
            if (arg.startsWith(DISCOUNT_CARD)) {
                String[] t = arg.split(DELIMITER);
                int cardId = Integer.parseInt(t[AFTER_DELIMITER]);
                if (discountCardDB.isDiscountCardExist(cardId)) {
                    discountCard = new DiscountCard(cardId);
                }
                continue;
            }

            if (arg.contains(DELIMITER)) {
                int productId = Integer.parseInt(arg.split(DELIMITER)[BEFORE_DELIMITER]);
                Product product = productDB.getProductById(productId);
                int quantity = Integer.parseInt(arg.split(DELIMITER)[AFTER_DELIMITER]);
                products.put(product, quantity);
            }
        }

        return new Check(products, discountCard);
    }

    public Check getCheck(String[] args) {

        Connection connection = ConnectionManager.getConnection();
        ProductsDao productsDB = new ProductsDaoImpl(connection);
        DiscountCardsDao discountCardsDB = new DiscountCardsDaoImpl(connection);

        DiscountCard discountCard = null;
        Map<Product, Integer> products = new HashMap<>();

        for (String arg : args) {
            if (arg.startsWith(DISCOUNT_CARD)) {
                String[] t = arg.split(DELIMITER);
                int cardId = Integer.parseInt(t[AFTER_DELIMITER]);
                discountCard = discountCardsDB.findById(cardId);
                continue;
            }

            if (arg.contains(DELIMITER)) {
                int productId = Integer.parseInt(arg.split(DELIMITER)[BEFORE_DELIMITER]);
                Product product = productsDB.findById(productId);
                int quantity = Integer.parseInt(arg.split(DELIMITER)[AFTER_DELIMITER]);
                products.put(product, quantity);
            }
        }

        return new Check(products, discountCard);
    }
}
