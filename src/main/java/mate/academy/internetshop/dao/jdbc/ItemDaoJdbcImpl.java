package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.exceptions.DataProcessingExeption;
import mate.academy.internetshop.lib.anotations.Dao;
import mate.academy.internetshop.model.Item;

@Dao
public class ItemDaoJdbcImpl extends AbcstractDao<Item> implements ItemDao {
    private static final String DB_NAME = "internetShop_db";

    public ItemDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Item create(Item entity) throws DataProcessingExeption {
        String query = String.format(Locale.ROOT,
                "insert into %s.items (name, price) values (?, ?)", DB_NAME);
        try (PreparedStatement preparedStatement
                    = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setDouble(2, entity.getPrice());
            preparedStatement.executeUpdate();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingExeption("Can't create item", e);
        }
        return entity;
    }

    @Override
    public Optional<Item> get(Long entityId) throws DataProcessingExeption {
        String query = String.format("select * from %s.items where item_id=?",  DB_NAME);
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, entityId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Item item = getItemFromResultSet(resultSet);
                return Optional.of(item);
            }
        } catch (SQLException e) {
            throw new DataProcessingExeption("Can't get item  with id = "
                    + entityId, e);
        }
        return Optional.empty();
    }

    @Override
    public Item update(Item entity) throws DataProcessingExeption {
        String query = String.format(Locale.ROOT,
                "UPDATE %s.items SET name=?, price=? WHERE item_id=?",
                DB_NAME);
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(query)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setDouble(2, entity.getPrice());
            preparedStatement.setLong(3, entity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingExeption("Can't update item with id = "
                    + entity.getId(), e);
        }
        return entity;
    }

    @Override
    public boolean deleteById(Long entityId) throws DataProcessingExeption {
        String query = String.format("delete from %s.items\n"
                + "where item_id = ?", DB_NAME);
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, entityId);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DataProcessingExeption("Can't delete item with id = "
                    + entityId, e);
        }
    }

    @Override
    public boolean delete(Item entity) throws DataProcessingExeption {
        return deleteById(entity.getId());
    }

    @Override
    public List<Item> getAll() throws DataProcessingExeption {
        List<Item> itemList = new ArrayList<>();
        String query = String.format("select * from %s.items",  DB_NAME);
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Item item = getItemFromResultSet(resultSet);
                itemList.add(item);
            }
        } catch (SQLException e) {
            throw new DataProcessingExeption("Can't get all items", e);
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
