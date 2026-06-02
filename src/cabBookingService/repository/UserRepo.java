package cabBookingService.repository;

import cabBookingService.exception.CabBookingException;
import cabBookingService.model.User;
import cabBookingService.model.UserRole;

import java.util.ArrayList;
import java.util.List;

public class UserRepo extends BaseRepository<User> {

    private static final UserRepo INSTANCE = new UserRepo();

    private UserRepo(){}

    public static UserRepo getInstance(){
        return INSTANCE;
    }

    public void save(User user){
        String key = user.getEmail().trim().toLowerCase();
        if(existsByKey(key)){
            throw new CabBookingException("Record already exists for key : " + key);
        }
        super.save(key, user);
    }

    public User findByEmail(String email){
        return findByKey(email.trim().toLowerCase());
    }

    public boolean existsByEmail(String email){
        if(email == null){
            return false;
        }
        return existsByKey(email.trim().toLowerCase());
    }

    public void deleteByEmail(String email) {
        deleteByKey(email.trim().toLowerCase());
    }

    public List<User> findRiders() {
        List<User> riders = new ArrayList<>();
        for (User user : storage.values()) {
            if (user.getUserRole() == UserRole.RIDER) {
                riders.add(user);
            }
        }
        return riders;
    }
}