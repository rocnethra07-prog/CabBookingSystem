package cabBookingService.model;

import cabBookingService.util.IdGenerator;

import java.util.Objects;

public class User {
    private final String userId;
    private final String name;
    private final String email;
    private final String phone;
    private final UserRole userRole;

    public User(String name, String phone, String email,UserRole userRole){
        if(name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }

        if(phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("Phone cannot be null or blank");
        }

        if(email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or blank");
        }

        if(userRole == null) {
            throw new IllegalArgumentException("User role cannot be null");
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

    public String getPhone() {
        return phone;
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