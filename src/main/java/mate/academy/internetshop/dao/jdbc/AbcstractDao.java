package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;

public class AbcstractDao<T> {
    protected final Connection connection;

    public AbcstractDao(Connection connection) {
        this.connection = connection;
    }
}
