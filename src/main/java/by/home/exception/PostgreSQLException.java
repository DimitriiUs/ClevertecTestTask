package by.home.exception;

import java.sql.SQLException;

public class PostgreSQLException extends RuntimeException {
    public PostgreSQLException(String reason) {
        super(reason);
    }
}
