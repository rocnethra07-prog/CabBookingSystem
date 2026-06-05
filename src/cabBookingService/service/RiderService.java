package cabBookingService.service;

import cabBookingService.exception.CabBookingException;
import cabBookingService.model.*;
import cabBookingService.repository.CabRepo;
import cabBookingService.repository.DriverRepo;
import cabBookingService.repository.RideRepo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RiderService {

    private final RideRepo rideRepo;
    private final DriverRepo driverRepo;
    private final CabRepo cabRepo;

    public RiderService(RideRepo rideRepo, DriverRepo driverRepo, CabRepo cabRepo){
        this.rideRepo = rideRepo;
        this.driverRepo = driverRepo;
        this.cabRepo = cabRepo;
    }

    public void updateProfile(User user, String name, String phone){
        user.setName(name);
        user.setPhone(phone);
    }

    public Ride bookRide(User rider, Location pickupLocation, Location dropLocation, CabType cabType) {

        Driver driver = findDriver(cabType, pickupLocation);
        if (driver == null) {
            throw new CabBookingException("No " + cabType.name() + " drivers are available right now. Please try a different cab type or try again later.");
        }

        BigDecimal fare = fareBasedOnCabType(cabType);
        Ride ride = new Ride(rider.getUserId(), driver.getUserId(), pickupLocation, dropLocation, fare);

        rideRepo.save(ride);
        driver.setAvailable(false);
        return ride;
    }

    private Driver findDriver(CabType cabType, Location pickupLocation) {

        List<Driver> matchingDrivers = new ArrayList<>();

        for (Driver driver : driverRepo.findAvailableDrivers()) {

            Cab cab = cabRepo.findByKey(driver.getCabId());

            if (cab != null && cab.getCabType() == cabType) {
                matchingDrivers.add(driver);
            }
        }

        if (matchingDrivers.isEmpty()) {
            return null;
        }

        for (Driver driver : matchingDrivers) {
            if (driver.getCurrentLocation() == pickupLocation) {
                return driver;
            }
        }

        return matchingDrivers.get(0);
    }


    public Driver getDriverForRide(Ride ride){
        return driverRepo.findByKey(ride.getDriverId());
    }

    public boolean hasActiveRide(User user){
        return (getCurrentBookedRide(user) != null);
    }

    //Since fare calculation is very basic and simple, I did not move it to a separate class (later if needed could be moved to a separate FareCalculator class)
    private BigDecimal fareBasedOnCabType(CabType cabType){
        return switch (cabType){
            case SUV -> BigDecimal.valueOf(250);
            case SEDAN -> BigDecimal.valueOf(180);
            case MINI -> BigDecimal.valueOf(120);
        };
    }

    public Ride getCurrentBookedRide(User rider){
        return rideRepo.findCurrentRideOfRider(rider.getUserId());
    }

    public void cancelRide(Ride ride, User rider){
        if(!ride.getRiderId().equals(rider.getUserId())){
            throw new CabBookingException("Only the rider who booked this ride can cancel it");
        }

        if (ride.getRideStatus() != RideStatus.BOOKED) {
            throw new CabBookingException("Ride status must be BOOKED to cancel it");
        }

        ride.setRideStatus(RideStatus.CANCELLED);
        markDriverAsAvailable(ride);
    }

    private void markDriverAsAvailable(Ride ride){
        Driver driver = getDriverForRide(ride);
        if(driver == null){
            return;
        }
        driver.setAvailable(true);
    }

    public List<Ride> getRidesByRider(User rider) {
        return rideRepo.findRidesByRider(rider.getUserId());
    }
    public Ride getLastCompletedRide(User rider) {
        return rideRepo.findLastCompletedRideByRider(rider.getUserId());
    }

    public void rateDriver(Ride ride, User rider, int rating) {

        if(ride.isRated()){
            throw new CabBookingException("Ride is already rated.");
        }

        if(rating < 1 || rating > 5){
            throw new CabBookingException("Rating must be between 1 and 5");
        }

        ride.setRating(rating);
        Driver driver = driverRepo.findByKey(ride.getDriverId());
        driver.addRating(rating);
    }

}