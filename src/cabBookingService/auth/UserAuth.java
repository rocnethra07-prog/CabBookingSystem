package cabBookingService.auth;


//external class for hashing password
import cabBookingService.exception.CabBookingException;
import org.mindrot.jbcrypt.BCrypt;


//Auth credential related class
//Store it as a Map in Repo as User ID (key) -> UserAuth
public class UserAuth {
    private String hashedPassword;

    public UserAuth(String password) {
        if(password == null || password.isBlank()){
            throw new CabBookingException("Password cannot be null or empty.");
        }
        this.hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean checkPassword(String inputPassword){
        if(inputPassword == null || inputPassword.isBlank()){
            return false;
        }
        return BCrypt.checkpw(inputPassword, this.hashedPassword);
    }

    public void updatePassword(String newPassword) {
        if (newPassword == null || newPassword.isBlank()) {
            throw new CabBookingException("New password cannot be null or empty.");
        }
        this.hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
    }
}
