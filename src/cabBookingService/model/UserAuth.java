package cabBookingService.model;

import java.util.Objects;


//Credentials related class : for password
public class UserAuth {
    private final String userId;
    private final String hashedPassword;

    public UserAuth(String userId, String password){

        if(userId == null || userId.isBlank()){
            throw new IllegalArgumentException("User Id cannot be null or empty.");
        }
        if(password == null || password.isBlank()){
            throw new IllegalArgumentException("Password cannot be null or empty.");
        }

        this.userId =  userId;
        this.hashedPassword = hash(password.trim());
    }


    private String hash(String password){
        return Integer.toHexString(password.hashCode());
    }

    @Override
    public boolean equals(Object object) {
        if(this == object) {
            return true;
        }
        if (!(object instanceof UserAuth userAuth)) return false;
        return Objects.equals(userId, userAuth.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userId);
    }
}
