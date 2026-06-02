package cabBookingService.repository;

import cabBookingService.model.User;

public class UserRepo extends BaseRepository<User> {

    private static final UserRepo INSTANCE = new UserRepo();

    private UserRepo(){}

    public static UserRepo getInstance(){
        return INSTANCE;
    }

    public void save(User user){
        super.save(user.getEmail().trim().toLowerCase(), user);
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
}