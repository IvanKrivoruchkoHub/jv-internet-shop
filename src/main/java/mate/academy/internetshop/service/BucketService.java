package mate.academy.internetshop.service;

import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;

import java.util.List;


public interface BucketService {

    void addItem(Bucket bucket, Item item);

    void deleteItem(Bucket bucket, Item item);

    void clear(Bucket bucket);

    List<Item> getAllItems(Bucket bucket);

    Bucket create(Bucket bucket);

    Bucket get(Long bucketId);

    Bucket update(Bucket bucket);

    void delete(Long bucketId);

    void delete(Bucket bucket);
}
