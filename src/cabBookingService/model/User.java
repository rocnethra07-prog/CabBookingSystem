package cabBookingService.model;

import cabBookingService.exception.CabBookingException;
import cabBookingService.util.IdGenerator;
import cabBookingService.util.Validator;

import java.util.Objects;

public class User {
    private final String userId;
    private String name;
    private final String email;
    private String phone;
    private final UserRole userRole;

    public User(String name, String phone, String email,UserRole userRole){

        if(!Validator.isValidName(name)){
            throw new CabBookingException("Name must contain minimum 3 characters. Name cannot be null or blank");
        }

        if(!Validator.isValid10DigitPhone(phone)){
            throw new CabBookingException("Invalid phone number format. Phone cannot be null or blank");
        }

        if(!Validator.isValidEmail(email)){
            throw new CabBookingException("Invalid email format. Email cannot be null or blank");
        }

        if(userRole == null) {
            throw new CabBookingException("User role cannot be null");
        }

        this.userId = IdGenerator.generateUserId();
        this.name = name.trim();
        this.phone = phone.trim();
        this.email = email.trim().toLowerCase();
        this.userRole = userRole;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){

        if (!Validator.isValidName(name)){
            throw new CabBookingException("Name must contain minimum 3 characters. Name cannot be null or blank");
        }

        this.name = name.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone){

        if(!Validator.isValid10DigitPhone(phone)){
            throw new CabBookingException("Invalid phone number format. Phone cannot be null or blank");
        }

        this.phone = phone.trim();
    }

    public String getEmail(){
        return email;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    //Email is the unique identifier
    //Two users are equal if their email is same
    @Override
    public boolean equals(Object object) {
        if(this == object) {
            return true;
        }
        if (!(object instanceof User user)) {
            return false;
        }
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }
}