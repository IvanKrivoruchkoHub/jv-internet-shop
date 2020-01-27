package mate.academy.internetshop.exceptions;

import java.sql.SQLException;

public class DataProcessingExeption extends Exception {
    public DataProcessingExeption(String s, SQLException e) {
        super(s, e);
    }
}
