package cabBookingService.service;

import cabBookingService.model.Driver;
import cabBookingService.model.Location;
import cabBookingService.model.Ride;
import cabBookingService.model.RideStatus;
import cabBookingService.repository.DriverRepo;
import cabBookingService.repository.RideRepo;

import java.util.List;

public class DriverService {

    private final RideRepo rideRepo;
    private final DriverRepo driverRepo;
    public DriverService(RideRepo rideRepo, DriverRepo driverRepo){
        this.rideRepo = rideRepo;
        this.driverRepo = driverRepo;
    }

    public void updateProfile(Driver driver, String name, String phone, Location currentLocation){
        driver.setName(name);
        driver.setPhone(phone);
        driver.setCurrentLocation(currentLocation);
    }

    public Ride getCurrentRide(Driver driver){
        return rideRepo.findCurrentRideOfDriver(driver.getUserId());
    }

    public void completeRide(Ride ride, Driver driver){
        if(!ride.getDriverId().equals(driver.getUserId())){
            throw new IllegalArgumentException("Only the assigned driver can complete this ride");
        }

        ride.setRideStatus(RideStatus.COMPLETED);
        driver.addEarnings(ride.getFare());
        markDriverAsAvailable(driver);
    }

    public void cancelRide(Ride ride, Driver driver){
        if(!ride.getDriverId().equals(driver.getUserId())){
            throw new IllegalArgumentException("Only the assigned driver can cancel this ride");
        }

        ride.setRideStatus(RideStatus.CANCELLED);
        markDriverAsAvailable(driver);
    }

    private void markDriverAsAvailable(Driver driver){
        driver.setAvailable(true);
    }

    public List<Ride> getRidesByDriver(Driver driver){
        return rideRepo.findRidesByDriver(driver.getUserId());
    }

    public Driver findDriverById(String userId){
        return driverRepo.findByKey(userId);
    }
}
