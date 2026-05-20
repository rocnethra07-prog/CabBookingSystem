package cabBookingService.auth;

import java.util.Objects;

//Auth credential related class
//Store it as a Map in Repo as User ID (key) -> UserAuth
public class UserAuth {
    private final String hashedPassword;

    public UserAuth(String password){

        if(password == null || password.isBlank()){
            throw new IllegalArgumentException("Password cannot be null or empty.");
        }

        this.hashedPassword = hash(password);
    }

    public boolean checkPassword(String inputPassword){
        if(inputPassword == null || inputPassword.isBlank()){
            return false;
        }
        return Objects.equals(this.hashedPassword, hash(inputPassword));
    }

    private String hash(String password){
        return Integer.toHexString(password.hashCode());
    }

}
