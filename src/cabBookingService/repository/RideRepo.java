package cabBookingService.repository;

import cabBookingService.exception.CabBookingException;
import cabBookingService.model.Ride;
import cabBookingService.model.RideStatus;

import java.util.*;

public class RideRepo extends BaseRepository<Ride> {

    private final static RideRepo INSTANCE = new RideRepo();

    private RideRepo(){}

    public static RideRepo getInstance(){
        return INSTANCE;
    }

    public void save(Ride ride){
        String key = ride.getId().trim();
        if(existsByKey(key)){
            throw new CabBookingException("Record already exists for key : " + key);
        }
        super.save(key, ride);
    }

    public List<Ride> findRidesByRider(String riderId){
        List<Ride> rides = new ArrayList<>();

        for(Ride ride : storage.values()){
            if(ride.getRiderId().equals(riderId)){
                rides.add(ride);
            }
        }
        return rides;
    }

    public List<Ride> findRidesByDriver(String driverId){
        List<Ride> rides = new ArrayList<>();

        for(Ride ride : storage.values()){
            if(ride.getDriverId().equals(driverId)){
                rides.add(ride);
            }
        }
        return rides;
    }

    public Ride findCurrentRideOfDriver(String driverId){
        for(Ride ride : storage.values()){
            if(ride.getDriverId().equals(driverId) && ride.getRideStatus() == RideStatus.BOOKED){
                return ride;
            }
        }
        return null;
    }

    public Ride findCurrentRideOfRider(String riderId){
        for(Ride ride: storage.values()){
            if(ride.getRiderId().equals(riderId) && ride.getRideStatus() == RideStatus.BOOKED){
                return ride;
            }
        }
        return null;
    }

    public List<Ride> findRidesByStatus(RideStatus status){
        List<Ride> rides = new ArrayList<>();
        for(Ride ride : storage.values()){
            if(ride.getRideStatus() == status){
                rides.add(ride);
            }
        }
        return rides;
    }
}