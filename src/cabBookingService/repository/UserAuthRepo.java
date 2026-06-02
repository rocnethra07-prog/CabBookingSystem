//package cabBookingService.repository;
//
//import cabBookingService.auth.UserAuth;
//
//import java.util.HashMap;
//import java.util.Map;
//
////repo for storing the user auth details
//public class UserAuthRepo extends BaseRepository<UserAuth> {
//
//    private final static UserAuthRepo INSTANCE = new UserAuthRepo();
//
//    private UserAuthRepo(){}
//
//    public static UserAuthRepo getInstance(){
//        return INSTANCE;
//    }
//
//    @Override
//    protected String getKey(UserAuth auth) {
//        return auth.getUserId();
//    }
//
//    public boolean validateCredentials(
//            String userId,
//            String password){
//
//        UserAuth auth = findById(userId);
//
//        return auth.checkPassword(password);
//    }
//
////    public void save(String userId, UserAuth userAuth) {
////        credentialsByUserId.put(userId, userAuth);
////    }
////
////    public boolean validateCredentials(String userId, String password){
////        UserAuth credential = credentialsByUserId.get(userId);
////
////        if(credential == null){
////            return false;
////        }
////        return credential.checkPassword(password);
////    }
//}


package cabBookingService.repository;

import cabBookingService.auth.UserAuth;

public class UserAuthRepo extends BaseRepository<UserAuth> {

    private static final UserAuthRepo INSTANCE = new UserAuthRepo();

    private UserAuthRepo(){}

    public static UserAuthRepo getInstance(){
        return INSTANCE;
    }

    public void save(String userId, UserAuth auth){
        super.save(userId, auth);
    }

    public boolean validateCredentials(String userId, String password){
        if(!existsByKey(userId)){
            return false;
        }
        UserAuth auth = findByKey(userId);
        return auth.checkPassword(password);
    }
}