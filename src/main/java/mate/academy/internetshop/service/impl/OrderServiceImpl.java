package mate.academy.internetshop.service.impl;

import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.lib.anotations.Inject;
import mate.academy.internetshop.lib.anotations.Service;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.OrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Inject
    private static OrderDao orderDao;

    @Override
    public Order completeOrder(List<Item> items, User user) {
        Order order = new Order();
        order.setItems(items);
        order.setUserId(user.getId());
        return orderDao.create(order);
    }

    @Override
    public List<Order> getUserOrders(User user) {
        List<Order> resultList = new ArrayList<>();
        for (Order tempOrder: Storage.orders) {
            if (tempOrder.getUserId().equals(user.getId())) {
                resultList.add(tempOrder);
            }
        }
        return resultList;
    }

    @Override
    public Order create(Order order) {
        return orderDao.create(order);
    }

    @Override
    public Optional<Order> get(Long orderId) {
        return orderDao.get(orderId);
    }

    @Override
    public Order update(Order order) {
        return orderDao.update(order);
    }

    @Override
    public void delete(Long orderId) {
        orderDao.delete(orderId);
    }

    @Override
    public void delete(Order order) {
        orderDao.delete(order);
    }
}
