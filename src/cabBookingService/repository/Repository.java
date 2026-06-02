package cabBookingService.repository;

import java.util.List;

public interface Repository<T> {

    //key is the unique value
    //key is id for most of the entities, for User -> key: email
    void save(String key, T entity);

    T findByKey(String key);

    List<T> findAll();

    boolean existsByKey(String key);

    void deleteByKey(String key);

    int count();
}