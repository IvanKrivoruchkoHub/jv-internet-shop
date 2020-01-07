package mate.academy.internetshop.dao.impl;

import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.lib.IdGenerator;
import mate.academy.internetshop.lib.anotations.Dao;
import mate.academy.internetshop.model.Order;

import java.util.NoSuchElementException;
import java.util.Optional;

@Dao
public class OrderDaoImpl implements OrderDao {
    @Override
    public Order create(Order order) {
        order.setOrderId(IdGenerator.getOrderId());
        Storage.orders.add(order);
        return order;
    }

    @Override
    public Optional<Order> get(Long orderId) {
        return Storage.orders
                .stream()
                .filter(o -> o.getOrderId().equals(orderId))
                .findFirst();
    }

    @Override
    public Order update(Order order) {
        Order orderToUpdate = get(order.getOrderId())
                .orElseThrow(() -> new NoSuchElementException("Can`t find order to update"));
        orderToUpdate.setUserId(order.getUserId());
        orderToUpdate.setItems(order.getItems());
        return orderToUpdate;
    }

    @Override
    public void delete(Long orderId) {
        delete(get(orderId).orElseThrow(()
                -> new NoSuchElementException("Can`t find order with id" + orderId)));
    }

    @Override
    public void delete(Order order) {
        if (!Storage.orders.remove(order)) {
            throw new NoSuchElementException("Can`t find order with id" + order.getOrderId());
        }
    }
}
