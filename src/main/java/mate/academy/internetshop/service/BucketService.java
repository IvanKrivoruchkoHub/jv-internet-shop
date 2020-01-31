package mate.academy.internetshop.service;

import mate.academy.internetshop.exceptions.DataProcessingExeption;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;

public interface BucketService extends GenericService<Bucket, Long> {

    void addItem(Bucket bucket, Item item) throws DataProcessingExeption;

    void deleteItem(Bucket bucket, Item item) throws DataProcessingExeption;

    void clear(Bucket bucket) throws DataProcessingExeption;

    Bucket getByUserId(Long userId) throws DataProcessingExeption;
}
