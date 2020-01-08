package mate.academy.internetshop.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.lib.anotations.Inject;
import mate.academy.internetshop.lib.anotations.Service;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.service.BucketService;

@Service
public class BucketServiceImpl implements BucketService {
    @Inject
    private static BucketDao bucketDao;

    @Inject
    private static ItemDao itemDao;

    @Override
    public void addItem(Bucket bucket, Item item) {
        Bucket tempBucket = bucketDao.get(bucket.getBucketId())
                .orElseThrow(NoSuchElementException::new);
        tempBucket.getItems().add(item);
    }

    @Override
    public void deleteItem(Bucket bucket, Item item) {
        Bucket tempBucket = bucketDao.get(bucket.getBucketId())
                .orElseThrow(NoSuchElementException::new);
        tempBucket.getItems().remove(item);
        bucketDao.update(tempBucket);
    }

    @Override
    public void clear(Bucket bucket) {
        bucket.getItems().clear();
    }

    @Override
    public List<Item> getAllItems(Bucket bucket) {
        return bucket.getItems();
    }

    @Override
    public Bucket create(Bucket bucket) {
        return bucketDao.create(bucket);
    }

    @Override
    public Optional<Bucket> get(Long bucketId) {
        return bucketDao.get(bucketId);
    }

    @Override
    public Bucket update(Bucket bucket) {
        return bucketDao.update(bucket);
    }

    @Override
    public boolean delete(Long bucketId) {
        return bucketDao.delete(bucketId);
    }

    @Override
    public boolean delete(Bucket bucket) {
        return bucketDao.delete(bucket);
    }
}
