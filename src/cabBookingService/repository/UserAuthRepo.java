package cabBookingService.repository;

import cabBookingService.auth.UserAuth;

public class UserAuthRepo extends BaseRepository<UserAuth> {

    private static final UserAuthRepo INSTANCE = new UserAuthRepo();

    private UserAuthRepo(){}

    public static UserAuthRepo getInstance(){
        return INSTANCE;
    }

    // No containsKey check. Saves the authentication record for the given user.
    // If a key already exists for that user, it will be replaced and password changes
    public void save(String userId, UserAuth auth){
        super.save(userId, auth);
    }

    public boolean validateCredentials(String userId, String password){
        if (userId == null || userId.trim().isEmpty()) {
            return false;
        }

        if(!existsByKey(userId)){
            return false;
        }

        UserAuth auth = findByKey(userId.trim());
        if (auth == null) {
            return false;
        }

        return auth.checkPassword(password);
    }
}