package mate.academy.internetshop.service;

import java.util.List;

import mate.academy.internetshop.exceptions.DataProcessingExeption;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;

public interface BucketService extends GenericService<Bucket, Long> {

    void addItem(Bucket bucket, Item item) throws DataProcessingExeption;

    void deleteItem(Bucket bucket, Item item) throws DataProcessingExeption;

    void clear(Bucket bucket) throws DataProcessingExeption;

    List<Item> getAllItemsFromBucket(Bucket bucket);

    Bucket getByUserId(Long userId) throws DataProcessingExeption;
}
