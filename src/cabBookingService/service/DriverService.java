package cabBookingService.service;

import cabBookingService.model.Driver;

public class DriverService {

    public void updateProfile(Driver driver, String name, String phone, String currentLocation){
        driver.setName(name);
        driver.setPhone(phone);
        driver.setCurrentLocation(currentLocation);
    }
}
