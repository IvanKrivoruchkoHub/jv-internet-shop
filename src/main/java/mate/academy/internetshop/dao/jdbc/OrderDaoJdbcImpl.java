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

import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.exceptions.DataProcessingExeption;
import mate.academy.internetshop.lib.anotations.Dao;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.User;

@Dao
public class OrderDaoJdbcImpl extends AbcstractDao<Order> implements OrderDao {
    private static final String DB_NAME = "internetShop_db";

    public OrderDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Order create(Order entity) throws DataProcessingExeption {
        String insertOrderQuery = String.format(Locale.ROOT,
                "INSERT INTO %s.orders (user_id) VALUES (?)",
                DB_NAME);
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(insertOrderQuery,
                Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, entity.getUserId());
            preparedStatement.executeUpdate();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setOrderId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingExeption("Can't create order with user_id = "
                    + entity.getUserId(), e);
        }
        addItemsIntoOrdersItemsTable(entity);
        return entity;
    }

    @Override
    public Optional<Order> get(Long entityId) throws DataProcessingExeption {
        String query = String.format(Locale.ROOT,
                "select orders.order_id, orders.user_id, items.item_id, items.name, items.price\n"
                        + "from %1$s.orders\n"
                        + "join %1$s.orders_items using(order_id)\n"
                        + "join %1$s.items using(item_id)\n"
                        + "where order_id = ?", DB_NAME);
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, entityId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Order order = null;
            while (resultSet.next()) {
                if (order == null) {
                    order = new Order(new ArrayList<>());
                    order.setUserId(resultSet.getLong("user_id"));
                    order.setOrderId(resultSet.getLong("order_id"));
                }
                Item item = new Item();
                item.setId(resultSet.getLong("item_id"));
                item.setName(resultSet.getString("name"));
                item.setPrice(resultSet.getDouble("price"));
                order.getItems().add(item);
            }
            return Optional.ofNullable(order);
        } catch (SQLException e) {
            throw new DataProcessingExeption("Can't create order with order_id = "
                    + entityId, e);
        }
    }

    @Override
    public Order update(Order entity) throws DataProcessingExeption {
        String updateOrderQuery = String.format(Locale.ROOT,
                "UPDATE %s.orders SET user_id = ? WHERE (order_id = ?)", DB_NAME);
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(updateOrderQuery)) {
            preparedStatement.setLong(1, entity.getUserId());
            preparedStatement.setLong(2, entity.getOrderId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String deleteFromOrdersItemTable = String.format("delete from %s.orders_items "
                + "where order_id = ?", DB_NAME);
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(deleteFromOrdersItemTable)) {
            preparedStatement.setLong(1, entity.getOrderId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingExeption("Can't update order with order_id = "
                    + entity.getOrderId(), e);
        }
        addItemsIntoOrdersItemsTable(entity);
        return entity;
    }

    private void addItemsIntoOrdersItemsTable(Order entity) throws DataProcessingExeption {
        String insertOrderItemQuery = String.format("INSERT INTO %s.orders_items "
                + "(order_id, item_id) VALUES (?, ?)", DB_NAME);
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(insertOrderItemQuery)) {
            preparedStatement.setLong(1, entity.getOrderId());
            for (Item item : entity.getItems()) {
                preparedStatement.setLong(2, item.getId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingExeption("Can't add items to orders_items with order_id = "
                    + entity.getOrderId(), e);
        }
    }

    @Override
    public boolean deleteById(Long entityId) throws DataProcessingExeption {
        String query = String.format(" delete orders, orders_items from %1$s.orders\n"
                + "left join %1$s.orders_items using(order_id)\n"
                + "where order_id = ?", DB_NAME);
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, entityId);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DataProcessingExeption("Can't delete order with order_id = "
                    + entityId, e);
        }
    }

    @Override
    public boolean delete(Order entity) throws DataProcessingExeption {
        return deleteById(entity.getOrderId());
    }

    @Override
    public List<Order> getAll() throws DataProcessingExeption {
        String getAllOrdersQuery = String.format(Locale.ROOT,
                "select orders.order_id, orders.user_id, items.item_id, items.name, items.price\n"
                        + "from %1$s.orders\n"
                        + "join %1$s.orders_items using(order_id)\n"
                        + "join %1$s.items using(item_id)\n"
                        + "order by orders.order_id;", DB_NAME);
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(getAllOrdersQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return getOrders(resultSet);
        } catch (SQLException e) {
            throw new DataProcessingExeption("Can't get all orders", e);
        }
    }

    @Override
    public List<Order> getUserOrders(User user) throws DataProcessingExeption {
        String getOrderQuery = String.format(Locale.ROOT,
                "select orders.order_id, orders.user_id, items.item_id, items.name, items.price\n"
                        + "from %1$s.orders\n"
                        + "join %1$s.orders_items using(order_id)\n"
                        + "join %1$s.items using(item_id)\n"
                        + "where orders.user_id = ? \n",
                DB_NAME, user.getId());
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(getOrderQuery)) {
            preparedStatement.setLong(1, user.getId());
            return getOrders(preparedStatement.executeQuery());
        } catch (SQLException e) {
            throw new DataProcessingExeption("Can't get all orders for user  with user_id = "
                    + user.getId(), e);
        }
    }

    private List<Order> getOrders(ResultSet resultSet) throws SQLException {
        List<Order> orderListResult = new ArrayList<>();
        Order order = null;
        while (resultSet.next()) {
            if (order == null) {
                order = new Order(new ArrayList<>());
            }
            if (order.getOrderId() != null
                    && !order.getOrderId().equals(resultSet.getLong("order_id"))) {
                orderListResult.add(order);
                order = new Order(new ArrayList<>());
            }
            if (order.getOrderId() == null) {
                order.setOrderId(resultSet.getLong("order_id"));
                order.setUserId(resultSet.getLong("user_id"));
            }
            Item item = new Item();
            item.setId(resultSet.getLong("item_id"));
            item.setName(resultSet.getString("name"));
            item.setPrice(resultSet.getDouble("price"));
            order.getItems().add(item);
        }
        if (order != null) {
            orderListResult.add(order);
        }
        return orderListResult;
    }
}
