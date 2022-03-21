package by.home.dao.impl;

import by.home.dao.ProductsDao;
import by.home.db.ConnectionManager;
import by.home.exception.PostgreSQLException;
import by.home.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductsDaoImpl implements ProductsDao {

    private Connection connection;

    public ProductsDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Product product) {

        PreparedStatement st = null;

        try {
            String sql = "INSERT INTO products (product_id,title,price,discount) VALUES (?, ?, ?, ?)";
            st = connection.prepareStatement(sql);
            st.setInt(1, product.getId());
            st.setString(2, product.getName());
            st.setFloat(3, product.getPrice());
            st.setBoolean(4, product.hasDiscount());

            st.executeUpdate();

        } catch (SQLException e) {
            throw new PostgreSQLException(e.getMessage());
        } finally {
            ConnectionManager.closeStatement(st);
        }
    }

    @Override
    public void update(Product product) {

        PreparedStatement st = null;

        try {
            String sql = "UPDATE products SET title = ?, price = ?, discount = ? WHERE product_id = ?";
            st = connection.prepareStatement(sql);
            st.setString(1, product.getName());
            st.setFloat(2, product.getPrice());
            st.setBoolean(3, product.hasDiscount());
            st.setInt(4, product.getId());

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
            String sql = "DELETE FROM products WHERE product_id = ?";
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
    public Product findById(Integer id) {

        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM products WHERE product_id = ?";
            st = connection.prepareStatement(sql);
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                return instantiateProduct(rs);
            }

            return null;
        } catch (SQLException e) {
            throw new PostgreSQLException(e.getMessage());
        } finally {
            ConnectionManager.closeStatement(st);
            ConnectionManager.closeResultSet(rs);
        }
    }

    private Product instantiateProduct(ResultSet rs) throws SQLException {
        return new Product(
                rs.getInt("product_id"),
                rs.getString("title"),
                rs.getFloat("price"),
                rs.getBoolean("discount")
        );
    }

}
