package cabBookingService.model;

import java.util.Objects;

//driver details
public class Driver extends User{

    private final Cab cab; //a driver owns only one cab
    private final String licenseNumber;
    private String currentLocation;
    private double earnings;
    private boolean isAvailable;

    public Driver(String name, String phone,String email,String currentLocation, String licenseNumber, Cab cab){
        super(name, phone, email, UserRole.DRIVER);
        if(currentLocation == null || currentLocation.isBlank()) {
            throw new IllegalArgumentException("Location cannot be null or blank");
        }

        if(licenseNumber == null || licenseNumber.isBlank()) {
            throw new IllegalArgumentException("License number cannot be null or blank");
        }

        if(cab == null) {
            throw new IllegalArgumentException("Cab cannot be null");
        }
        this.currentLocation = currentLocation.trim();
        this.licenseNumber = licenseNumber.trim().toUpperCase();
        this.earnings = 0.0;
        this.isAvailable = true;
        this.cab = cab;
    }

    public Cab getCab() {
        return cab;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public double getEarnings() {
        return earnings;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setCurrentLocation(String location){
        if(location == null || location.isBlank()) {
            throw new IllegalArgumentException("Location cannot be null or blank");
        }
        this.currentLocation = location.trim();
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    public void addEarnings(double amount){
        if(amount <= 0){
            throw new IllegalArgumentException("Amount cannot be 0 or negative.");
        }
        this.earnings += amount;
    }

    @Override
    public boolean equals(Object object) {
        if(this == object) return true;
        if (!(object instanceof Driver driver)) return false;
        if (!super.equals(object)) return false;
        return Objects.equals(licenseNumber, driver.licenseNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), licenseNumber);
    }
}
