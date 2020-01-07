package mate.academy.internetshop.dao.impl;

import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.lib.IdGenerator;
import mate.academy.internetshop.lib.anotations.Dao;
import mate.academy.internetshop.model.Bucket;

import java.util.NoSuchElementException;
import java.util.Optional;

@Dao
public class BucketDaoImpl implements BucketDao {

    @Override
    public Bucket create(Bucket bucket) {
        int pos = getUserBucketPosition(bucket);
        bucket.setBucketId(IdGenerator.getBucketId());
        if (pos == -1) {
            Storage.buckets.add(bucket);
            return bucket;
        }
        Storage.buckets.set(pos, bucket);
        return bucket;
    }

    private int getUserBucketPosition(Bucket bucket) {
        boolean result = false;
        int pos = 0;
        for (Bucket tempBucket: Storage.buckets) {
            if (tempBucket.getUserId().equals(bucket.getUserId())) {
                result = true;
                break;
            }
            pos++;
        }
        return result ? pos : -1;
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
    public void delete(Long bucketId) {
        delete(get(bucketId).orElseThrow(()
                -> new NoSuchElementException("Can`t find bucket with id" + bucketId)));
    }

    @Override
    public void delete(Bucket bucket) {
        if (!Storage.buckets.remove(bucket)) {
            throw new NoSuchElementException("Can`t find bucket with id" + bucket.getBucketId());
        }
    }
}
