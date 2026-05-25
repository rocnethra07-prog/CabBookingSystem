package cabBookingService.model;

import cabBookingService.util.IdGenerator;

import java.time.LocalDateTime;
import java.util.Objects;

public class Ride {
    private final String id;
    private final String riderId;
    private final String driverId;
    private final String pickupLocation;
    private final String dropLocation;
    private final double fare;
    private RideStatus rideStatus; //not final, status will get updated
    private final LocalDateTime bookedAt;

    public Ride(String riderId, String driverId, String pickupLocation, String dropLocation, double fare) {
        if(riderId == null || riderId.isBlank()) {
            throw new IllegalArgumentException("Rider ID cannot be null or empty");
        }

        if(driverId == null || driverId.isBlank()) {
            throw new IllegalArgumentException("Driver ID cannot be null or empty");
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
        this.riderId = riderId.trim();
        this.driverId = driverId.trim();
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

    public String getRiderId() {
        return riderId;
    }

    public String getDriverId() {
        return driverId;
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

        if(rideStatus == RideStatus.CANCELLED && this.rideStatus != RideStatus.BOOKED){
            throw new IllegalArgumentException("Only booked rides can be cancelled");
        }

        if(rideStatus == RideStatus.COMPLETED && this.rideStatus != RideStatus.BOOKED){
           throw  new IllegalArgumentException("Only booked rides can be completed");
        }

        if(rideStatus == RideStatus.BOOKED && (this.rideStatus == RideStatus.CANCELLED || this.rideStatus == RideStatus.COMPLETED)){
            throw new IllegalStateException(
                    "Completed or cancelled rides cannot be booked again"
            );
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
