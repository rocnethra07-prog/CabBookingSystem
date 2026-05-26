package cabBookingService.repository;

import cabBookingService.model.Cab;
import cabBookingService.model.CabType;
import cabBookingService.model.Driver;

import java.util.*;

//repo for storing only the drivers
public class DriverRepo {

    private static DriverRepo driverRepo ;

    //key: id
    private final Map<String, Driver> driversById = new HashMap<>();

    //to check uniqueness
    private final Set<String> licenses = new HashSet<>();

    private DriverRepo(){}

    public static DriverRepo getInstance(){
        if(driverRepo == null){
            driverRepo = new DriverRepo();
        }
        return driverRepo;
    }

    public void save(Driver driver) {
        driversById.put(driver.getUserId(), driver);
        licenses.add(driver.getLicenseNumber());
    }

    public Driver findById(String driverId) {
        return driversById.get(driverId);
    }

    public boolean existsByLicense(String license){
        if(license == null){
            return false;
        }
        return licenses.contains(license.trim().toUpperCase());
    }

    public List<Driver> findAvailableDrivers(){

        List<Driver> availableDrivers = new ArrayList<>();
        for(Driver driver : driversById.values()){
            if(driver.isAvailable()){
                availableDrivers.add(driver);
            }
        }
        return availableDrivers;
    }

}
