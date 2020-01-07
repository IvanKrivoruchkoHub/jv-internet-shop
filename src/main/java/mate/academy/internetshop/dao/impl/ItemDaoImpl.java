package mate.academy.internetshop.dao.impl;

import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.lib.IdGenerator;
import mate.academy.internetshop.lib.anotations.Dao;
import mate.academy.internetshop.model.Item;

import java.util.NoSuchElementException;
import java.util.Optional;

@Dao
public class ItemDaoImpl implements ItemDao {

    @Override
    public Item create(Item item) {
        item.setId(IdGenerator.getItemId());
        Storage.items.add(item);
        return item;
    }

    @Override
    public Optional<Item> get(Long id) {
        return Storage.items
                .stream()
                .filter(i -> i.getId().equals(id))
                .findFirst();
    }

    @Override
    public Item update(Item item) {
        Item itemToUpdate = get(item.getId())
                .orElseThrow(() -> new NoSuchElementException("Can`t find item to update"));
        itemToUpdate.setPrice(item.getPrice());
        itemToUpdate.setName(item.getName());
        return itemToUpdate;
    }

    @Override
    public void delete(Long id) {
        delete(get(id).orElseThrow(()
                -> new NoSuchElementException("Can`t find bucket with id" + id)));
    }

    @Override
    public void delete(Item item) {
        if (!Storage.items.remove(item)) {
            throw new NoSuchElementException("Can`t find item with id" + item.getId());
        }
    }

}
