package cabBookingService.service;

import cabBookingService.builder.DriverRegistrationData;
import cabBookingService.exception.CabBookingException;
import cabBookingService.model.*;
import cabBookingService.repository.*;

import java.util.List;

public class AdminService {

    private final DriverRepo driverRepo;
    private final CabRepo cabRepo;
    private final RideRepo rideRepo;
    private final UserRepo userRepo;
    private final AuthService authService;

    public AdminService(DriverRepo driverRepo, CabRepo cabRepo, RideRepo rideRepo, UserRepo userRepo, AuthService authService) {
        this.driverRepo = driverRepo;
        this.cabRepo = cabRepo;
        this.rideRepo = rideRepo;
        this.userRepo = userRepo;
        this.authService = authService;
    }

    public boolean isUserExists(String email) {
        return authService.isUserExists(email);
    }

    public boolean isLicenseNumberExists(String license) {
        return driverRepo.existsByLicense(license);
    }

    public boolean isRegistrationNumExists(String registration) {
        return cabRepo.existsByRegNumber(registration);
    }

    //─── Driver Management

    public Driver addDriver(DriverRegistrationData data) {
        Cab cab = new Cab(data.getRegistrationNumber(), data.getModel(), data.getCabType());
        Driver driver = new Driver(
                data.getName(), data.getPhone(), data.getEmail(),
                data.getCurrentLocation(), data.getLicenseNumber(), cab.getCabId()
        );

        authService.registerDriverCredentials(driver, data.getPassword());
        cabRepo.save(cab);
        driverRepo.save(driver);

        return driver;
    }

    //Delete a driver only if they have no active (BOOKED) ride.
    //Removes the driver, their cab, and their user/auth records.

    public boolean deleteDriver(String driverId) {
        Driver driver = driverRepo.findByKey(driverId);

        Ride activeRide = rideRepo.findCurrentRideOfDriver(driverId);
        if (activeRide != null) {
            return false;
        }

        cabRepo.deleteByKey(driver.getCabId());
        driverRepo.deleteByKey(driverId);
        authService.deleteUser(driver);

        return true;
    }

    public List<Driver> getAllDrivers() {
        return driverRepo.findAll();
    }

    public List<Driver> getAvailableDrivers() {
        return driverRepo.findAvailableDrivers();
    }

    public List<Driver> getUnavailableDrivers() {
        return driverRepo.findUnavailableDrivers();
    }

    public Driver findDriverById(String driverId) {
        return driverRepo.findByKey(driverId);
    }

    public Cab getCabForDriver(Driver driver) {
        return cabRepo.findByKey(driver.getCabId());
    }

    public List<Ride> getRidesForDriver(String driverId) {
        return rideRepo.findRidesByDriver(driverId);
    }

    public List<User> getAllRiders() {
        return userRepo.findRiders();
    }

    public List<Ride> getRidesForRider(String riderId) {
        return rideRepo.findRidesByRider(riderId);
    }


    public List<Ride> getAllRides() {
        return rideRepo.findAll();
    }

    public List<Ride> getRidesByStatus(RideStatus status) {
        return rideRepo.findRidesByStatus(status);
    }

    public List<Ride> getActiveRides() {
        return getRidesByStatus(RideStatus.BOOKED);
    }

    public List<Ride> getCompletedRides() {
        return getRidesByStatus(RideStatus.COMPLETED);
    }

    public List<Ride> getCancelledRides() {
        return getRidesByStatus(RideStatus.CANCELLED);
    }

    public List<Cab> getAllCabs() {
        return cabRepo.findAll();
    }

    public List<Cab> getCabsByType(CabType cabType) {
        return cabRepo.findCabsByCabType(cabType);
    }


}