package cabBookingService.service;

import cabBookingService.auth.UserAuth;
import cabBookingService.model.User;
import cabBookingService.model.UserRole;
import cabBookingService.repository.UserAuthRepo;
import cabBookingService.repository.UserRepo;

public class AuthService {
    private final UserRepo userRepo;
    private final UserAuthRepo userAuthRepo;

    public AuthService(UserRepo userRepo, UserAuthRepo userAuthRepo){
        this.userRepo = userRepo;
        this.userAuthRepo = userAuthRepo;
    }

    public boolean isUserExists(String email){
        return userRepo.isUserExists(email);
    }

    public User registerUser(String name, String phone, String email, String password, UserRole userRole){
        if(isUserExists(email)){
            return null;
        }

        User user = new User(name, phone, email, userRole);
        userRepo.save(user);
        userAuthRepo.save(user.getUserId(),new UserAuth(password));

        return user;
    }

    public User loginUser(String email, String password) {
        User user = userRepo.findByEmail(email);

        if(user == null){
            return null;
        }

        if(!userAuthRepo.validateCredentials(user.getUserId(), password)){
            return null;
        }

        return user;
    }
}
