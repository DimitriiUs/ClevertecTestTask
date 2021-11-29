package by.home;

import by.home.db.DiscountCardDB;
import by.home.db.ProductDB;

import java.util.HashMap;
import java.util.Map;

public class CheckUtils {

    public Check getCheck(String[] args, ProductDB productDB, DiscountCardDB discountCardDB) {
        DiscountCard discountCard = null;
        final String DELIMITER = "-";
        final String DISCOUNT_CARD = "card";
        final int AFTER_DELIMITER = 1;
        final int BEFORE_DELIMITER = 0;

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
}
