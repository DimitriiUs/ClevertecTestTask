package by.home.dao.impl;

import by.home.dao.DiscountCardsDao;
import by.home.db.ConnectionManager;
import by.home.exception.PostgreSQLException;
import by.home.model.DiscountCard;
import by.home.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DiscountCardsDaoImpl implements DiscountCardsDao {

    private Connection connection;

    public DiscountCardsDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(DiscountCard discountCard) {

        PreparedStatement st = null;

        try {
            String sql = "INSERT INTO discount_cards (DISCOUNT_card_id,discount,product_quantity_for_discount) VALUES (?, ?, ?)";
            st = connection.prepareStatement(sql);
            st.setInt(1, discountCard.getId());
            st.setInt(2, discountCard.getDiscount());
            st.setInt(3, discountCard.getProductsQuantityForDiscount());

            st.executeUpdate();

        } catch (SQLException e) {
            throw new PostgreSQLException(e.getMessage());
        } finally {
            ConnectionManager.closeStatement(st);
        }
    }

    @Override
    public void update(DiscountCard discountCard) {

        PreparedStatement st = null;

        try {
            String sql = "UPDATE discount_cards SET discount = ?, product_quantity_for_discount = ? WHERE DISCOUNT_card_id = ?";
            st = connection.prepareStatement(sql);
            st.setInt(1, discountCard.getDiscount());
            st.setInt(2, discountCard.getProductsQuantityForDiscount());
            st.setInt(3, discountCard.getId());

            st.executeUpdate();

        } catch (SQLException e) {
            throw new PostgreSQLException(e.getMessage());
        } finally {
            ConnectionManager.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {

        PreparedStatement st = null;

        try {
            String sql = "DELETE FROM discount_cards WHERE DISCOUNT_card_id = ?";
            st = connection.prepareStatement(sql);
            st.setInt(1, id);

            st.executeUpdate();

        } catch (SQLException e) {
            throw new PostgreSQLException(e.getMessage());
        } finally {
            ConnectionManager.closeStatement(st);
        }
    }

    @Override
    public DiscountCard findById(Integer id) {

        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM discount_cards WHERE DISCOUNT_card_id = ?";
            st = connection.prepareStatement(sql);
            st.setInt(1, id);

            rs = st.executeQuery();

            if (rs.next()) {
                return instantiateDiscountCard(rs);
            }

            return null;

        } catch (SQLException e) {
            throw new PostgreSQLException(e.getMessage());
        } finally {
            ConnectionManager.closeStatement(st);
            ConnectionManager.closeResultSet(rs);
        }
    }

    private DiscountCard instantiateDiscountCard(ResultSet rs) throws SQLException {
        return new DiscountCard(
                rs.getInt("DISCOUNT_card_id"),
                rs.getInt("discount"),
                rs.getInt("product_quantity_for_discount")
        );
    }
}
