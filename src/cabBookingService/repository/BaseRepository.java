package cabBookingService.repository;

import cabBookingService.exception.CabBookingException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseRepository<T> implements Repository<T> {

    protected final Map<String, T> storage = new HashMap<>();

    public void save(String key, T entity) {
        if(key == null || key.isBlank()){
            throw new CabBookingException("Key cannot be null or blank");
        }
        if(entity == null){
            throw new CabBookingException("Entity cannot be null");
        }
        key = key.trim();

        storage.put(key, entity);
    }

    public T findByKey(String key) {

        if(key == null || key.isBlank()){
            throw new CabBookingException("Key cannot be null or blank");
        }

        T entity = storage.get(key.trim());

        if(entity == null){
            throw new CabBookingException("No record found for key : " + key);
        }

        return entity;
    }

    public List<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    public boolean existsByKey(String key) {
        if(key == null || key.isBlank()){
            throw new CabBookingException("The key cannot be null ");
        }
        return storage.containsKey(key.trim());
    }

    public void deleteByKey(String key) {

        if(!existsByKey(key)){
            throw new CabBookingException("No record found for key : " + key);
        }

        storage.remove(key.trim());
    }

    @Override
    public int count() {
        return storage.size();
    }
}