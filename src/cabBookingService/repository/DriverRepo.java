package cabBookingService.repository;

import cabBookingService.exception.CabBookingException;
import cabBookingService.model.Driver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

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
    public List<Driver> findAvailableDrivers() {
        return findDrivers(Driver::isAvailable);
    }

    public List<Driver> findUnavailableDrivers() {
        return findDrivers(driver -> !driver.isAvailable());
    }

    private List<Driver> findDrivers(Predicate<Driver> condition) {
        List<Driver> result = new ArrayList<>();

        for (Driver driver : storage.values()) {
            if (condition.test(driver)) {
                result.add(driver);
            }
        }

        return Collections.unmodifiableList(result);
    }


    public boolean existsByLicense(String license){
        if(license == null || license.isBlank()){
            throw new CabBookingException("License number cannot be null or blank");
        }

        for(Driver driver : storage.values()){
            if(driver.getLicenseNumber().equalsIgnoreCase(license)){
                return true;
            }
        }
        return false;
     }
}