package cabBookingService.model;

import cabBookingService.exception.CabBookingException;
import cabBookingService.util.IdGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Ride {
    private final String id;
    private final String riderId;
    private final String driverId;
    private final Location pickupLocation;
    private final Location dropLocation;
    private final BigDecimal fare;
    private RideStatus rideStatus;
    private final LocalDateTime bookedAt;

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public Ride(String riderId, String driverId, Location pickupLocation, Location dropLocation, BigDecimal fare) {

        if (riderId == null || riderId.isBlank()) {
            throw new CabBookingException("Rider ID cannot be null or blank");
        }

        if (driverId == null || driverId.isBlank()) {
            throw new CabBookingException("Driver ID cannot be null or blank");
        }

        if (pickupLocation == null) {
            throw new CabBookingException("Pickup location cannot be null");
        }

        if (dropLocation == null) {
            throw new CabBookingException("Drop location cannot be null");
        }

        if (pickupLocation == dropLocation) {
            throw new CabBookingException(
                    "Pickup and destination locations cannot be the same"
            );
        }

        if (fare == null) {
            throw new CabBookingException("Fare cannot be null");
        }

        if (fare.compareTo(BigDecimal.ZERO) <= 0) {
            throw new CabBookingException("Fare must be greater than zero");
        }

        this.id = IdGenerator.generateRideId();
        this.riderId = riderId.trim();
        this.driverId = driverId.trim();
        this.pickupLocation = pickupLocation;
        this.dropLocation = dropLocation;
        this.fare = fare;
        this.rideStatus = RideStatus.BOOKED;
        this.bookedAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public String getRiderId() {
        return riderId;
    }

    public String getDriverId() {
        return driverId;
    }

    public Location getPickupLocation() {
        return pickupLocation;
    }

    public Location getDropLocation() {
        return dropLocation;
    }

    public BigDecimal getFare() {
        return fare;
    }

    public RideStatus getRideStatus() {
        return rideStatus;
    }

    public LocalDateTime getBookedAt() {
        return bookedAt;
    }

    public void setRideStatus(RideStatus rideStatus) {

        if (rideStatus == null) {
            throw new CabBookingException("Ride status cannot be null.");
        }
        //no update needed
        if(this.rideStatus != null && this.rideStatus == rideStatus){
            return;
        }

        if((rideStatus == RideStatus.CANCELLED || rideStatus == RideStatus.COMPLETED)
                && this.rideStatus != RideStatus.BOOKED){
            throw new CabBookingException("Only booked rides can be completed or cancelled");
        }

        if(rideStatus == RideStatus.BOOKED && (this.rideStatus == RideStatus.CANCELLED || this.rideStatus == RideStatus.COMPLETED)){
            throw new CabBookingException( "Completed or cancelled rides cannot be booked again" );
        }

        this.rideStatus = rideStatus;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof Ride ride)) {
            return false;
        }

        return Objects.equals(id, ride.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Ride ID          : " + id +
                "\nRider ID         : " + riderId +
                "\nDriver ID        : " + driverId +
                "\nPickup Location  : " + pickupLocation +
                "\nDrop Location    : " + dropLocation +
                "\nFare             : " + fare +
                "\nStatus           : " + rideStatus +
                "\nBooked At        : " + bookedAt.format(DATE_TIME_FORMATTER);
    }
}
