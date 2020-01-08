package mate.academy.internetshop.dao;

import java.util.Optional;
import mate.academy.internetshop.model.Item;

public interface ItemDao {
    Item create(Item item);

    Optional<Item> get(Long id);

    Item update(Item item);

    boolean delete(Long id);

    boolean delete(Item item);

}
