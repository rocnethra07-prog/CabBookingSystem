package cabBookingService.service;

import cabBookingService.auth.UserAuth;
import cabBookingService.exception.CabBookingException;
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
        return userRepo.existsByEmail(email);
    }

    public User registerUser(String name, String phone, String email, String password, UserRole userRole){
        if (isUserExists(email)) {
            throw new CabBookingException("An account with this email already exists.");
        }
        User user = new User(name, phone, email, userRole);
        saveUserCredentials(user, password);
        return user;
    }

    public void registerDriverCredentials(User user, String password) {
        if (isUserExists(user.getEmail())) {
            throw new CabBookingException("An account with this email already exists.");
        }
        saveUserCredentials(user, password);
    }

    private void saveUserCredentials(User user, String password) {
        userRepo.save(user);
        userAuthRepo.save(user.getUserId(), new UserAuth(password));
    }

    public User loginUser(String email, String password) {
        User user = userRepo.findByEmail(email);

        if(user == null){
            throw new CabBookingException("Account does not exist. Please register.");
        }

        if(!userAuthRepo.validateCredentials(user.getUserId(), password)){
            throw new CabBookingException("Invalid credentials.");
        }

        return user;
    }

    public void deleteUser(User user) {
        userRepo.deleteByEmail(user.getEmail());
        userAuthRepo.deleteByKey(user.getUserId());
    }
}
