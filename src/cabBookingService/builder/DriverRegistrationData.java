package cabBookingService.builder;

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
            this.name = name;
            return this;
        }

        public Builder phone(String phone){
            this.phone = phone;
            return this;
        }

        public Builder email(String email){
            this.email = email;
            return this;
        }

        public Builder password(String password){
            this.password = password;
            return this;
        }

        public Builder currentLocation(Location currentLocation){
            this.currentLocation = currentLocation;
            return this;
        }

        public Builder licenseNumber(String licenseNumber){
            this.licenseNumber = licenseNumber;
            return this;
        }

        public Builder model(String model){
            this.model = model;
            return this;
        }

        public Builder registrationNumber(String registrationNumber){
            this.registrationNumber = registrationNumber;
            return this;
        }

        public Builder cabType(CabType cabType){
            this.cabType = cabType;
            return this;
        }

        public DriverRegistrationData build(){
            return new DriverRegistrationData(this);
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