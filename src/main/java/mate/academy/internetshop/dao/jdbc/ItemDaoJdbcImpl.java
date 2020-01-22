package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.lib.anotations.Dao;
import mate.academy.internetshop.model.Item;
import org.apache.log4j.Logger;

@Dao
public class ItemDaoJdbcImpl extends AbcstractDao<Item> implements ItemDao {
    private static Logger logger = Logger.getLogger(ItemDaoJdbcImpl.class);
    public static String DB_NAME = "internetShop_db";

    public ItemDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Item create(Item entity) {
        String query = String.format(Locale.ROOT,
                "insert into %s.items (name, price) values ('%s', %f)",
                DB_NAME, entity.getName(), entity.getPrice());
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return entity;
    }

    @Override
    public Optional<Item> get(Long entityId) {
        String query = String.format("select * from %s.items where item_id=%d",  DB_NAME, entityId);
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                Item item = getItemFromResultSet(resultSet);
                return Optional.of(item);
            }
        } catch (SQLException e) {
            logger.warn("Can't get item by id = " + entityId, e);
        }
        return Optional.empty();
    }

    @Override
    public Item update(Item entity) {
        String query = String.format(Locale.ROOT,
                "UPDATE %s.items SET name = '%s', price = %f WHERE item_id = %d",
                DB_NAME, entity.getName(), entity.getPrice(), entity.getId());
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return entity;
    }

    @Override
    public boolean deleteById(Long entityId) {
        String query = String.format("delete from %s.items\n"
                + "where item_id = %d", DB_NAME, entityId);
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Item entity) {
        return deleteById(entity.getId());
    }

    @Override
    public List<Item> getAll() {
        List<Item> itemList = new ArrayList<>();
        String query = String.format("select * from %s.items",  DB_NAME);
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Item item = getItemFromResultSet(resultSet);
                itemList.add(item);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return itemList;
    }

    private Item getItemFromResultSet(ResultSet resultSet) throws SQLException {
        Long itemId = resultSet.getLong("item_id");
        String name = resultSet.getString("name");
        Double price = resultSet.getDouble("price");
        Item item = new Item();
        item.setId(itemId);
        item.setName(name);
        item.setPrice(price);
        return item;
    }
}
