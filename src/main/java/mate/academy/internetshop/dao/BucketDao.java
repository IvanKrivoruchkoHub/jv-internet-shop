package mate.academy.internetshop.dao;

import java.util.Optional;
import mate.academy.internetshop.model.Bucket;

public interface BucketDao extends GenericDao<Bucket, Long> {
    Optional<Bucket> getByUserId(Long userId);
}
