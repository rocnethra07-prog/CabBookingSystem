package cabBookingService.repository;

import cabBookingService.exception.CabBookingException;
import cabBookingService.model.Ride;
import cabBookingService.model.RideStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class RideRepo extends BaseRepository<Ride> {

    private static final RideRepo INSTANCE = new RideRepo();

    private RideRepo() {}

    public static RideRepo getInstance() {
        return INSTANCE;
    }

    public void save(Ride ride) {
        String key = ride.getId().trim();
        if (existsByKey(key)) {
            throw new CabBookingException("Record already exists for key: " + key);
        }
        super.save(key, ride);
    }

    public List<Ride> findRidesByRider(String riderId) {
        return findRides(ride -> Objects.equals(ride.getRiderId(), riderId));
    }

    public List<Ride> findRidesByDriver(String driverId) {
        return findRides(ride -> Objects.equals(ride.getDriverId(), driverId));
    }

    public Ride findCurrentRideOfDriver(String driverId) {
        return findFirstRide(ride -> Objects.equals(ride.getDriverId(), driverId) &&
                ride.getRideStatus() == RideStatus.BOOKED);
    }

    public Ride findCurrentRideOfRider(String riderId) {
        return findFirstRide(ride -> Objects.equals(ride.getRiderId(), riderId) &&
                        ride.getRideStatus() == RideStatus.BOOKED);
    }

    public List<Ride> findRidesByStatus(RideStatus status) {
        if (status == null) {
            throw new CabBookingException("Ride status cannot be null");
        }
        return findRides(ride -> ride.getRideStatus() == status);
    }

    private List<Ride> findRides(Predicate<Ride> predicate) {
        List<Ride> rides = new ArrayList<>();
        for (Ride ride : storage.values()) {
            if (predicate.test(ride)) {
                rides.add(ride);
            }
        }
        return Collections.unmodifiableList(rides);
    }

    private Ride findFirstRide(Predicate<Ride> predicate) {
        for (Ride ride : storage.values()) {
            if (predicate.test(ride)) {
                return ride;
            }
        }
        return null;
    }


    public Ride findLastCompletedRideByRider(String riderId) {
        Ride latestRide = null;
        for (Ride ride : storage.values()) {
            if (!Objects.equals(ride.getRiderId(), riderId)) {
                continue;
            }
            if (ride.getRideStatus() != RideStatus.COMPLETED) {
                continue;
            }
            if (latestRide == null || ride.getBookedAt().isAfter(latestRide.getBookedAt())) {
                latestRide = ride;
            }
        }
        return latestRide;
    }
}