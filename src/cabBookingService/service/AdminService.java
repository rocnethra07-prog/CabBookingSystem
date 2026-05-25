package cabBookingService.service;

import cabBookingService.auth.UserAuth;
import cabBookingService.model.Cab;
import cabBookingService.model.CabType;
import cabBookingService.model.Driver;
import cabBookingService.repository.*;

public class AdminService {
    private final UserRepo userRepo;
    private final UserAuthRepo userAuthRepo;
    private final DriverRepo driverRepo;
    private final CabRepo cabRepo;

    public AdminService(UserRepo userRepo, UserAuthRepo userAuthRepo, DriverRepo driverRepo, CabRepo cabRepo) {
        this.userRepo = userRepo;
        this.userAuthRepo = userAuthRepo;
        this.driverRepo = driverRepo;
        this.cabRepo = cabRepo;
    }

    public boolean isUserExists(String email){
        return userRepo.isUserExists(email);
    }

    public boolean isLicenseNumberExists(String license){
        return driverRepo.existsByLicense(license);
    }

    public boolean isRegistrationNumExists(String registration){
        return cabRepo.existsByRegNumber(registration);
    }

    public Driver addDriver(String name,String phone, String email, String password, String currentLocation, String licenseNumber, String model, String registrationNumber, CabType cabType ){

        if(isUserExists(email) || isLicenseNumberExists(licenseNumber)
                || isRegistrationNumExists(registrationNumber)){
            return null;
        }
        Cab cab = new Cab(registrationNumber, model, cabType);
        Driver driver = new Driver(name, phone, email, currentLocation,licenseNumber, cab.getCabId());

        driverRepo.save(driver);
        userRepo.save(driver);
        userAuthRepo.save(driver.getUserId(), new UserAuth(password));
        cabRepo.save(cab);

        return driver;
    }
}
