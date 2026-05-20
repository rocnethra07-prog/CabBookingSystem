package cabBookingService.model;

import cabBookingService.util.IdGenerator;

import java.time.LocalDateTime;
import java.util.Objects;

//all fields are final
public class Ride {
    private final String id;
    private final User rider;
    private final Driver driver;
    private final String pickupLocation;
    private final String dropLocation;
    private final double fare;
    private RideStatus rideStatus; //not final, status will get updated
    private final LocalDateTime bookedAt;

    public Ride(User rider, Driver driver, String pickupLocation, String dropLocation, double fare) {
        if(rider == null) {
            throw new IllegalArgumentException("Rider cannot be null");
        }

        //only rider is allowed to book a ride
        if(rider.getUserRole() != UserRole.RIDER){
            throw new IllegalArgumentException("User is not a rider");
        }

        if(driver == null) {
            throw new IllegalArgumentException("Driver cannot be null");
        }

        if(!driver.isAvailable()){
            throw new IllegalArgumentException("Driver is not available");
        }

        if(pickupLocation == null || pickupLocation.isBlank()) {
            throw new IllegalArgumentException("Pickup location cannot be null or blank");
        }

        if(dropLocation == null || dropLocation.isBlank()) {
            throw new IllegalArgumentException("Drop location cannot be null or blank");
        }

        if(pickupLocation.trim().equalsIgnoreCase(dropLocation.trim())){
            throw new IllegalArgumentException("Pick Up and Destination cannot be the same");
        }

        if(fare <= 0) {
            throw new IllegalArgumentException("Fare must be positive");
        }

        this.id = IdGenerator.generateRideId();
        this.rider = rider;
        this.driver = driver;
        this.pickupLocation = pickupLocation.trim();
        this.dropLocation = dropLocation.trim();
        this.fare = fare;
        this.rideStatus = RideStatus.BOOKED; //ride is booked
        this.bookedAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public RideStatus getRideStatus(){
        return rideStatus;
    }

    public User getRider() {
        return rider;
    }

    public Driver getDriver() {
        return driver;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public LocalDateTime getBookedAt() {
        return bookedAt;
    }

    public String getDropLocation() {
        return dropLocation;
    }

    public double getFare() {
        return fare;
    }

    public void setRideStatus(RideStatus rideStatus) {
        if(rideStatus == null){
            throw new IllegalArgumentException("Ride status cannot be null");
        }
        this.rideStatus = rideStatus;
    }

    @Override
    public boolean equals(Object object) {
        if(this == object) {
            return true;
        }
        if (!(object instanceof Ride ride)) return false;
        return Objects.equals(id, ride.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
