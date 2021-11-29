package by.home.db;

import by.home.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class ProductDB {
    private List<Product> products = Collections.emptyList();

    public ProductDB(String dbFileName) {
        initDB(dbFileName);
    }

    private void initDB(String dbFileName) {
        Gson gson = new Gson();
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(dbFileName);
        Type listType = new TypeToken<List<Product>>() {}.getType();
        products = gson.fromJson(new InputStreamReader(in), listType);
    }

    public Product getProductById(int id) {
        return products.stream()
                .filter(product -> product.getId() == id)
                .findAny()
                .orElse(null);
    }
}
