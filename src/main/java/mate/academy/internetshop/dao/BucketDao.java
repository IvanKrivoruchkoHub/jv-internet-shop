package mate.academy.internetshop.dao;

import mate.academy.internetshop.model.Bucket;

import java.util.Optional;

public interface BucketDao extends GenericDao<Bucket, Long> {
    Optional<Bucket> getByUserId(Long userId);
}
