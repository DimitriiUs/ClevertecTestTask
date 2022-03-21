package by.home.db;

import by.home.model.DiscountCard;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class DiscountCardDB {
    private List<DiscountCard> cards = Collections.emptyList();

    public DiscountCardDB(String dbFileName) {
        initDB(dbFileName);
    }

    private void initDB(String dbFileName) {
        Gson gson = new Gson();
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(dbFileName);
        Type listType = new TypeToken<List<DiscountCard>>() {}.getType();
        cards = gson.fromJson(new InputStreamReader(in), listType);
    }

    public boolean isDiscountCardExist(int id) {
        return cards.contains(new DiscountCard(id, 0,0));
    }
}
