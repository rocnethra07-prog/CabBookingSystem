package cabBookingService.repository;


import cabBookingService.model.User;

import java.util.HashMap;
import java.util.Map;

//repo for storing all the users
public class UserRepo {

    private final static UserRepo INSTANCE = new UserRepo() ;
    private final Map<String, User> usersByEmail = new HashMap<>(); //key: email

    private UserRepo(){}

    public static UserRepo getInstance(){
        return INSTANCE;
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
