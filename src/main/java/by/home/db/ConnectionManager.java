package by.home.db;

import by.home.exception.PostgreSQLException;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class ConnectionManager {

    private static Connection connection;

    private static final String DB_PROPERTIES_FILE_NAME = "src/main/resources/postgresqldb.properties";
    private static final String DATABASE_URL_PROPERTY = "dburl";

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Properties props = loadProperties();
                String url = props.getProperty(DATABASE_URL_PROPERTY);
                connection = DriverManager.getConnection(url, props);
            } catch (SQLException e) {
                throw new PostgreSQLException(e.getMessage());
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new PostgreSQLException(e.getMessage());
            }
        }
    }

    private static Properties loadProperties() {
        try (FileInputStream fs = new FileInputStream(DB_PROPERTIES_FILE_NAME)) {
            Properties props = new Properties();
            props.load(fs);
            return props;
        } catch (IOException e) {
            throw new PostgreSQLException(e.getMessage());
        }
    }

    public static void closeStatement(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                throw new PostgreSQLException(e.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new PostgreSQLException(e.getMessage());
            }
        }
    }
}
