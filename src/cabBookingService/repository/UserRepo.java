package cabBookingService.repository;


import cabBookingService.model.User;

import java.util.HashMap;
import java.util.Map;

//repo for storing all the users
public class UserRepo {

    private static UserRepo userRepo ;
    private final Map<String, User> usersByEmail = new HashMap<>(); //key: email

    private UserRepo(){}

    public static UserRepo getInstance(){
        if(userRepo == null){
            userRepo = new UserRepo();
        }
        return userRepo;
    }

    public void save(User user) {
        usersByEmail.put(user.getEmail(), user);
    }

    public User findByEmail(String email) {
        return usersByEmail.get(email.trim().toLowerCase());
    }

    public boolean isUserExists(String email) {
        if(email == null){
            return false;
        }
        return usersByEmail.containsKey(email.trim().toLowerCase());
    }

}
