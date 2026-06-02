package cabBookingService.service;

import cabBookingService.builder.DriverRegistrationData;
import cabBookingService.model.*;
import cabBookingService.repository.*;

public class AdminService {
    private final DriverRepo driverRepo;
    private final CabRepo cabRepo;
    private final AuthService authService;

    public AdminService(DriverRepo driverRepo, CabRepo cabRepo,AuthService authService) {
        this.authService = authService;
        this.driverRepo = driverRepo;
        this.cabRepo = cabRepo;
    }

    public boolean isUserExists(String email){
        return authService.isUserExists(email);
    }

    public boolean isLicenseNumberExists(String license){
        return driverRepo.existsByLicense(license);
    }

    public boolean isRegistrationNumExists(String registration){
        return cabRepo.existsByRegNumber(registration);
    }

    public Driver addDriver(DriverRegistrationData driverBuilder){

        if(isUserExists(driverBuilder.getEmail())
                || isLicenseNumberExists(driverBuilder.getLicenseNumber())
                || isRegistrationNumExists(driverBuilder.getRegistrationNumber())){
            return null;
        }

        Cab cab = new Cab(
                driverBuilder.getRegistrationNumber(),
                driverBuilder.getModel(),
                driverBuilder.getCabType()
        );

        Driver driver = new Driver(
                driverBuilder.getName(),
                driverBuilder.getPhone(),
                driverBuilder.getEmail(),
                driverBuilder.getCurrentLocation(),
                driverBuilder.getLicenseNumber(),
                cab.getCabId()
        );

        cabRepo.save(cab);
        driverRepo.save(driver);
        authService.registerUser(driverBuilder.getName(),
                driverBuilder.getPhone(),
                driverBuilder.getEmail(),
                driverBuilder.getPassword(),
                UserRole.DRIVER);

        return driver;
    }

}
