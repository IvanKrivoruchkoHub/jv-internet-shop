package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.lib.IdGenerator;
import mate.academy.internetshop.lib.anotations.Dao;
import mate.academy.internetshop.model.Item;

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
    public boolean deleteById(Long id) {
        Optional<Item> itemToDelete = get(id);
        itemToDelete.ifPresent(this::delete);
        return false;
    }

    @Override
    public boolean delete(Item item) {
        return Storage.items.remove(item);
    }

    @Override
    public List<Item> getAll() {
        return Storage.items;
    }

}
