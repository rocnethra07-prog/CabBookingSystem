package cabBookingService.builder;

import cabBookingService.exception.CabBookingException;
import cabBookingService.model.CabType;
import cabBookingService.model.Location;

public class DriverRegistrationData {

    private final String name;
    private final String phone;
    private final String email;
    private final String password;
    private final Location currentLocation;
    private final String licenseNumber;
    private final String model;
    private final String registrationNumber;
    private final CabType cabType;

    private DriverRegistrationData(Builder builder) {
        //to ensure that all the required fields are built
        if(builder.name == null)
            throw new CabBookingException("Name is required.");

        if(builder.phone == null)
            throw new CabBookingException("Phone is required.");

        if(builder.email == null)
            throw new CabBookingException("Email is required.");

        if(builder.password == null)
            throw new CabBookingException("Password is required.");

        if(builder.currentLocation == null)
            throw new CabBookingException("Location is required.");

        if(builder.licenseNumber == null)
            throw new CabBookingException("License Number is required.");

        if(builder.model == null)
            throw new CabBookingException("Cab Model is required.");

        if(builder.registrationNumber == null)
            throw new CabBookingException("Registration Number is required.");

        if(builder.cabType == null)
            throw new CabBookingException("Cab Type is required.");

        this.name = builder.name;
        this.phone = builder.phone;
        this.email = builder.email;
        this.password = builder.password;
        this.currentLocation = builder.currentLocation;
        this.licenseNumber = builder.licenseNumber;
        this.model = builder.model;
        this.registrationNumber = builder.registrationNumber;
        this.cabType = builder.cabType;
    }

    public static class Builder {

        private String name;
        private String phone;
        private String email;
        private String password;
        private Location currentLocation;
        private String licenseNumber;
        private String model;
        private String registrationNumber;
        private CabType cabType;

        public Builder name(String name){
            validateStringValue(name, "Name");
            this.name = name;
            return this;
        }

        public Builder phone(String phone){
            validateStringValue(phone, "Phone");
            this.phone = phone;
            return this;
        }

        public Builder email(String email){
            validateStringValue(email, "Email");
            this.email = email;
            return this;
        }

        public Builder password(String password){
            validateStringValue(password, "Password");
            this.password = password;
            return this;
        }

        public Builder currentLocation(Location currentLocation){
            if(currentLocation == null){
                throw new CabBookingException("Location is required.");
            }

            this.currentLocation = currentLocation;
            return this;
        }

        public Builder licenseNumber(String licenseNumber){
            validateStringValue(licenseNumber, "License Number");
            this.licenseNumber = licenseNumber;
            return this;
        }

        public Builder model(String model){
            validateStringValue(model, "Cab Model");
            this.model = model;
            return this;
        }

        public Builder registrationNumber(String registrationNumber){
            validateStringValue(registrationNumber, "Registration Number");
            this.registrationNumber = registrationNumber;
            return this;
        }

        public Builder cabType(CabType cabType){
            if(cabType == null){
                throw new CabBookingException("Cab Type is required.");
            }

            this.cabType = cabType;
            return this;
        }

        public DriverRegistrationData build(){
            return new DriverRegistrationData(this);
        }

        private void validateStringValue(String value, String fieldName) {
            if (value == null || value.isBlank()) {
                throw new CabBookingException(fieldName + " is required.");
            }
        }
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public String getModel() {
        return model;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public CabType getCabType() {
        return cabType;
    }
}