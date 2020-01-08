package mate.academy.internetshop.dao.impl;

import java.util.NoSuchElementException;
import java.util.Optional;
import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.lib.IdGenerator;
import mate.academy.internetshop.lib.anotations.Dao;
import mate.academy.internetshop.model.Bucket;

@Dao
public class BucketDaoImpl implements BucketDao {

    @Override
    public Bucket create(Bucket bucket) {
        bucket.setBucketId(IdGenerator.getBucketId());
        Storage.buckets.add(bucket);
        return bucket;
    }

    @Override
    public Optional<Bucket> get(Long bucketId) {
        return Storage.buckets
                .stream()
                .filter(b -> b.getBucketId().equals(bucketId))
                .findFirst();
    }

    @Override
    public Bucket update(Bucket bucket) {
        Bucket bucketToUpdate = get(bucket.getBucketId())
                .orElseThrow(() -> new NoSuchElementException("Can`t find bucket to update"));
        bucketToUpdate.setUserId(bucket.getUserId());
        bucketToUpdate.setItems(bucket.getItems());
        return bucketToUpdate;
    }

    @Override
    public boolean delete(Long bucketId) {
        Optional<Bucket> bucketToDelete = get(bucketId);
        bucketToDelete.ifPresent(this::delete);
        return false;
    }

    @Override
    public boolean delete(Bucket bucket) {
        return Storage.buckets.remove(bucket);
    }
}
