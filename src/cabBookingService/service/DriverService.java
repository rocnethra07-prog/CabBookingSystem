package cabBookingService.service;

import cabBookingService.exception.CabBookingException;
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
        endRide(ride, driver, RideStatus.COMPLETED);
        driver.setCurrentLocation(ride.getDropLocation());
        driver.addEarnings(ride.getFare());
    }

    public void cancelRide(Ride ride, Driver driver){
        endRide(ride, driver, RideStatus.CANCELLED);
    }

    private void endRide(Ride ride, Driver driver, RideStatus rideStatus){
        if(!ride.getDriverId().equals(driver.getUserId())){
            throw new CabBookingException("Only the assigned driver can end this ride");
        }

        if (ride.getRideStatus() != RideStatus.BOOKED) {
            throw new CabBookingException("Ride status must be BOOKED to end it");
        }
        ride.setRideStatus(rideStatus);
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