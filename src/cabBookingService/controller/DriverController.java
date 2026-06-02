package cabBookingService.controller;

import cabBookingService.exception.CabBookingException;
import cabBookingService.model.Cab;
import cabBookingService.model.Driver;
import cabBookingService.model.Location;
import cabBookingService.model.Ride;
import cabBookingService.service.DriverService;
import cabBookingService.util.InputUtil;
import cabBookingService.util.Validator;

import java.util.List;
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
            System.out.println("1. View current ride");
            System.out.println("2. Update Profile");
            System.out.println("3. Show earnings");
            System.out.println("4. View ride history");
            //have to do change password
            System.out.println("0. Back");

            System.out.print("Choose: ");

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    viewCurrentRide(driver);
                    break;
                case "2":
                    updateProfile(driver);
                    break;
                case "3":
                    showEarnings(driver);
                    break;
                case "4":
                    viewRideHistory(driver);
                    break;
                case "0":
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private void viewCurrentRide(Driver driver){
        Ride ride = driverService.getCurrentRide(driver);

        if (ride == null){
            System.out.println("No active ride at the moment.");
            return;
        }

        System.out.println("\n--- CURRENT RIDE ---");

        System.out.println("Pickup: " + ride.getPickupLocation());

        System.out.println("Drop: " + ride.getDropLocation());

        System.out.println("Fare: ₹" + ride.getFare());

        System.out.println("Status: "+ ride.getRideStatus());

        rideActionMenu(ride, driver);
    }

    private void rideActionMenu(Ride ride, Driver driver){
        boolean back = false;
        while(!back){
            System.out.println("\n  What would you like to do?");
            System.out.println("1 COMPLETE RIDE");
            System.out.println("2 CANCEL RIDE");
            System.out.println("0 BACK");
            System.out.print("Choose: ");
            switch (sc.nextLine().trim()){
                case "1":
                    completeRide(ride, driver);
                    back = true;
                    break;
                case "2":
                    cancelRide(ride, driver);
                    back = true;
                    break;
                case "0":
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option");
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
                if (name.isEmpty()) {
                    name = driver.getName();
                    break;
                }
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
                if (phone.isEmpty()) {
                    phone = driver.getPhone();
                    break;
                }
            }
        }

        System.out.println("  Current location: " + driver.getCurrentLocation());

        Location location = driver.getCurrentLocation();

        if (InputUtil.getYesOrNo(sc, "  Update location?")) {
            location = InputUtil.selectLocation(sc, "Select new location:");
        }
        if(location == null){
            location = driver.getCurrentLocation();
        }

        driverService.updateProfile(driver, name, phone, location);

        try {
            System.out.println("\nProfile updated successfully");
            printUpdatedProfile(driver);
        }
        catch (CabBookingException e){
            System.out.println("[!] Update failed " + e.getMessage());
        }
    }

    private void printUpdatedProfile(Driver driver) {
        System.out.println("\n--- UPDATED PROFILE ---");

        System.out.println("Name            : " + driver.getName());

        System.out.println("Phone           : " + driver.getPhone());

        System.out.println("Email           : " + driver.getEmail());

        System.out.println("Current Location: " + driver.getCurrentLocation());

        System.out.println("License Number  : " + driver.getLicenseNumber());
    }

    private void showEarnings(Driver driver){
        System.out.println("TOTAL EARNINGS = ₹" + driver.getEarnings());
    }

    private void viewRideHistory(Driver driver){
        List<Ride> rides = driverService.getRidesByDriver(driver);
        if (rides.isEmpty()) {
            System.out.println("\n  No rides in history yet.");
            return;
        }
        System.out.println("\n--- RIDE HISTORY (" + rides.size() + " rides) ---");

        for(Ride ride :rides){
            System.out.println(ride);
            System.out.println("\n-----------------------------------------------------");
        }
    }

    private void completeRide(Ride ride, Driver driver){
        try {
            driverService.completeRide(ride, driver);
            System.out.println("\n  Ride completed successfully!");
            showEarnings(driver);
        }
        catch (CabBookingException e) {
            System.out.println("  [!] " + e.getMessage());
        }
    }

    private void cancelRide(Ride ride, Driver driver){
        try {
            driverService.cancelRide(ride, driver);
            System.out.println("\n  Ride cancelled.");
        }
        catch (CabBookingException e) {
            System.out.println("  [!] " + e.getMessage());
        }
    }
}
