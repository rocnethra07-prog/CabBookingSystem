package cabBookingService.repository;

import cabBookingService.auth.UserAuth;

import java.util.HashMap;
import java.util.Map;

//repo for storing the user auth details
public class UserAuthRepo {

    private static UserAuthRepo userAuthRepo;
    private final Map<String, UserAuth> credentialsByUserId = new HashMap<>();

    private UserAuthRepo(){}

    public static UserAuthRepo getInstance(){
        if(userAuthRepo == null){
            userAuthRepo = new UserAuthRepo();
        }
        return userAuthRepo;
    }

    public void save(String userId, UserAuth userAuth) {
        credentialsByUserId.put(userId, userAuth);
    }

    public boolean validateCredentials(String userId, String password){
        UserAuth credential = credentialsByUserId.get(userId);

        if(credential == null){
            return false;
        }
        return credential.checkPassword(password);
    }
}
