package cabBookingService.model;

//driver details
public class Driver extends User{

    private final String cabId; //a driver owns only one cab
    private final String licenseNumber;
    private String currentLocation;
    private double earnings;
    private boolean isAvailable;

    public Driver(String name, String phone,String email,String currentLocation, String licenseNumber, String cabId){
        super(name, phone, email, UserRole.DRIVER);
        if(currentLocation == null || currentLocation.isBlank()) {
            throw new IllegalArgumentException("Location cannot be null or blank");
        }

        if(licenseNumber == null || licenseNumber.isBlank()) {
            throw new IllegalArgumentException("License number cannot be null or blank");
        }

        if(cabId == null || cabId.isBlank()) {
            throw new IllegalArgumentException("Cab ID cannot be null or blank");
        }
        this.currentLocation = currentLocation.trim();
        this.licenseNumber = licenseNumber.trim().toUpperCase();
        this.earnings = 0.0;
        this.isAvailable = true;
        this.cabId = cabId.trim();
    }

    public String getCabId() {
        return cabId;
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

    //no equals check, inherits user class equals()
}
