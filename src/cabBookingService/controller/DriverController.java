package cabBookingService.controller;

import cabBookingService.model.Driver;
import cabBookingService.service.DriverService;
import cabBookingService.util.Validator;
import java.util.Scanner;

public class DriverController {

    private final DriverService driverService;
    private final Scanner sc;

    public DriverController(DriverService driverService, Scanner sc){
        this.driverService = driverService;
        this.sc = sc;
    }

    public void showMenu(Driver driver){

        boolean back = false;

        while(!back) {
            System.out.println("\n--- DRIVER MENU ---");
            System.out.println("1. Update Profile");
            System.out.println("0. Exit");

            System.out.print("Choose: ");

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    updateProfile(driver);
                    break;
                case "0":
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }


    private void updateProfile(Driver driver){
        System.out.println("\n--- UPDATE PROFILE ---");
        System.out.println("(Press Enter to keep current value)");

        System.out.println("\nCurrent Name : " + driver.getName());

        System.out.print("New name: ");
        String name = sc.nextLine().trim();

        if(name.isEmpty()){
            name = driver.getName();
        }
        else {
            while (!Validator.isValidName(name)) {
                System.out.print("Enter valid name (minimum 3 characters): ");
                name = sc.nextLine().trim();
            }
        }

        System.out.println("\nCurrent Phone : " + driver.getPhone());

        System.out.print("New Phone: ");
        String phone = sc.nextLine().trim();

        if(phone.isEmpty()){
            phone = driver.getPhone();
        }
        else {
            while (!Validator.isValid10DigitPhone(phone)) {
                System.out.print("Enter valid phone: ");
                phone = sc.nextLine().trim();
            }
        }

        System.out.println("\nCurrent Location : " + driver.getCurrentLocation());

        System.out.print("New location: ");
        String location = sc.nextLine().trim();

        if(location.isBlank()){
            location = driver.getCurrentLocation();
        }

        driverService.updateProfile(driver, name, phone, location);

        System.out.println("\nProfile updated successfully");
        printUpdatedProfile(driver);
    }

    private void printUpdatedProfile(Driver driver) {

        System.out.println("\n--- UPDATED PROFILE ---");

        System.out.println("Name            : " + driver.getName());

        System.out.println("Phone           : " + driver.getPhone());

        System.out.println("Email           : " + driver.getEmail());

        System.out.println("Current Location: " + driver.getCurrentLocation());

        System.out.println("License Number  : " + driver.getLicenseNumber());
    }
}
