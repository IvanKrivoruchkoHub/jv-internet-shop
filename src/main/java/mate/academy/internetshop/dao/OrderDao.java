package mate.academy.internetshop.dao;

import java.util.List;

import mate.academy.internetshop.exceptions.DataProcessingExeption;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.User;

public interface OrderDao extends GenericDao<Order, Long> {
    List<Order> getUserOrders(User user) throws DataProcessingExeption;
}
