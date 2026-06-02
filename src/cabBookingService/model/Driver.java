package cabBookingService.model;

import cabBookingService.exception.CabBookingException;

import java.math.BigDecimal;

//driver details
public class Driver extends User{

    private final String cabId;
    private final String licenseNumber;
    private Location currentLocation;
    private BigDecimal earnings;
    private boolean isAvailable;

    public Driver(String name, String phone,String email,Location currentLocation, String licenseNumber, String cabId){
        super(name, phone, email, UserRole.DRIVER);
        if(currentLocation == null ) {
            throw new CabBookingException("Location cannot be null");
        }

        if(licenseNumber == null || licenseNumber.isBlank()) {
            throw new CabBookingException("License number cannot be null or blank");
        }

        if(cabId == null || cabId.isBlank()) {
            throw new CabBookingException("Cab ID cannot be null or blank");
        }
        this.currentLocation = currentLocation;
        this.licenseNumber = licenseNumber.trim().toUpperCase();
        this.earnings = BigDecimal.ZERO;
        this.isAvailable = true;
        this.cabId = cabId.trim();
    }

    public String getCabId() {
        return cabId;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public BigDecimal getEarnings() {
        return earnings;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setCurrentLocation(Location location){

        if(location == null ) {
            throw new CabBookingException("Location cannot be null");
        }

        //No update required
        if (this.currentLocation != null && this.currentLocation == location){
            return;
        }

        this.currentLocation = location;
    }

    public void setAvailable(boolean available) {
        // No update needed
        if (this.isAvailable == available) {
            return;
        }

        this.isAvailable = available;
    }

    public void addEarnings(BigDecimal amount){
        if (amount == null) {
            throw new CabBookingException("Amount cannot be null");
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new CabBookingException("Amount must be greater than zero");
        }

        this.earnings = this.earnings.add(amount);
    }

    @Override
    public String toString() {
        return "Driver " + getUserId() +
                "\nCab Id           : " + cabId +
                "\nCurrent Location : " + currentLocation +
                "\nAvailability     : " + ((isAvailable) ? "YES" : "NO") +
                "\nLicense Number   : " + licenseNumber +
                "\nEarnings         : " + earnings;
    }
}
