package cabBookingService.repository;

import cabBookingService.model.Driver;
import java.util.HashMap;
import java.util.Map;

//repo for storing only the drivers
public class DriverRepo {

    private static DriverRepo driverRepo ;

    //key: id
    private final Map<String, Driver> driversById = new HashMap<>();

    //key: license
    private final Map<String, Driver> driversByLicenseNumber = new HashMap<>();

    private DriverRepo(){}

    public static DriverRepo getInstance(){
        if(driverRepo == null){
            driverRepo = new DriverRepo();
        }
        return driverRepo;
    }

    public void save(Driver driver) {
        driversById.put(driver.getUserId(), driver);
        driversByLicenseNumber.put(driver.getLicenseNumber(),  driver);
    }

    public Driver findById(String driverId) {
        return driversById.get(driverId);
    }

    public boolean existsByLicense(String license){
        if(license == null){
            return false;
        }
        return driversByLicenseNumber.containsKey(license.trim().toUpperCase());
    }

}
