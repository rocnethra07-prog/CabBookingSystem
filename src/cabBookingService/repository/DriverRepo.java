package cabBookingService.repository;

import cabBookingService.exception.CabBookingException;
import cabBookingService.model.Driver;

import java.util.ArrayList;
import java.util.List;

//repo for storing only the drivers
public class DriverRepo extends BaseRepository<Driver> {

    private static final DriverRepo INSTANCE = new DriverRepo();

    private DriverRepo(){}

    public static DriverRepo getInstance(){
        return INSTANCE;
    }

    public void save(Driver driver){
        String key = driver.getUserId().trim();
        if(existsByKey(key)){
            throw new CabBookingException("Record already exists for key : " + key);
        }
        super.save(key, driver);
    }

    public List<Driver> findAvailableDrivers(){
        List<Driver> availableDrivers = new ArrayList<>();

        for(Driver driver : storage.values()){
            if(driver.isAvailable()){
                availableDrivers.add(driver);
            }
        }
        return availableDrivers;
    }

    public List<Driver> findUnavailableDrivers(){
        List<Driver> unavailableDrivers = new ArrayList<>();

        for(Driver driver : storage.values()){
            if(!driver.isAvailable()){
                unavailableDrivers.add(driver);
            }
        }
        return unavailableDrivers;
    }


    public boolean existsByLicense(String license){
        if(license == null){
            throw new CabBookingException("License number cannot be null");
        }

        for(Driver driver : storage.values()){
            if(driver.getLicenseNumber().equalsIgnoreCase(license)){
                return true;
            }
        }
        return false;
     }
}