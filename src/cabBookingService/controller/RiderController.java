package cabBookingService.controller;

import cabBookingService.exception.CabBookingException;
import cabBookingService.model.*;
import cabBookingService.service.RiderService;
import cabBookingService.util.InputUtil;
import cabBookingService.util.Validator;

import java.util.List;
import java.util.Scanner;

public class RiderController {
    private final Scanner sc;
    private final RiderService riderService;

    public RiderController(RiderService riderService, Scanner sc){
        this.sc = sc;
        this.riderService = riderService;
    }

    public void showMenu(User rider){
        System.out.println("\n--- RIDER MENU ---");

        boolean back = false;
        while(!back) {
            System.out.println("1. Book ride");
            System.out.println("2. View current ride");
            System.out.println("3. View ride history");
            System.out.println("4. Update profile");
            //have to do change password
            System.out.println("0. Logout");

            switch (sc.nextLine().trim()){
                case "1":
                    bookRide(rider);
                    break;
                case "2":
                    viewCurrentRide(rider);
                    break;
                case "3":
                    viewRideHistory(rider);
                    break;
                case "4":
                    updateProfile(rider);
                    break;
                case "0":
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void bookRide(User rider){

        if(riderService.hasActiveRide(rider)){
            System.out.println("You already have an active ride. Complete or cancel it first");
            return;
        }

        System.out.println("\n--- BOOK A RIDE ---");
        Location pickupLocation = InputUtil.selectLocation(sc, "Pickup Location: ");

        Location dropLocation;
        while(true) {
            dropLocation = InputUtil.selectLocation(sc, "Drop Location: ");
            if(dropLocation == pickupLocation){
                System.out.println("Drop Location must be different from the Pick Up Location");
                continue;
            }
            break;
        }

        CabType cabType = InputUtil.selectCabType(sc);

        try {
            Ride ride = riderService.bookRide(rider, pickupLocation, dropLocation, cabType);
            Driver driver = riderService.getDriverForRide(ride);
            System.out.println("\n  Ride booked successfully!");
            System.out.println(ride);
            if (driver != null) {
                System.out.println("  Driver       : " + driver.getName());
                System.out.println("  Driver phone : " + driver.getPhone());
            }
        }
        catch (CabBookingException e){
            System.out.println("\n[!] " + e.getMessage());
        }
    }

    private void viewCurrentRide(User rider){
        Ride ride =  riderService.getCurrentBookedRide(rider);
        if(ride == null){
            System.out.println("No active ride.");
            return;
        }

        System.out.println("\n--- CURRENT RIDE ---");

        Driver driver =  riderService.getDriverForRide(ride);

        if(driver == null ){
            System.out.println("No driver assigned");
            return;
        }

        System.out.println("Pickup: " + ride.getPickupLocation());

        System.out.println("Drop: " + ride.getDropLocation());

        System.out.println("Fare: ₹" + ride.getFare());

        System.out.println("Driver Name : " + driver.getName());

        System.out.println("Driver phone: " + driver.getPhone());

        System.out.println("Status: "+ ride.getRideStatus());

        viewRideOptions(ride, rider);

    }

    private void viewRideOptions(Ride ride, User rider){
        boolean back = false;
        while(!back){
            System.out.println("1 CANCEL RIDE");
            System.out.println("0 BACK");
            System.out.print("Choose: ");

            switch (sc.nextLine().trim()){
                case "1":
                    cancelRide(ride, rider);
                    back = true;
                    break;
                case "0":
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private void updateProfile(User rider){
        System.out.println("\n--- UPDATE PROFILE ---");
        System.out.println("(Press Enter to keep current value)");

        System.out.println("\nCurrent Name : " + rider.getName());

        System.out.print("New name: ");
        String name = sc.nextLine().trim();

        if(name.isEmpty()){
            name = rider.getName();
        }
        else {
            while (!Validator.isValidName(name)) {
                System.out.print("Enter valid name (minimum 3 characters): ");
                name = sc.nextLine().trim();
                if (name.isEmpty()) { name = rider.getName(); break; }
            }
        }

        System.out.println("\nCurrent Phone : " + rider.getPhone());

        System.out.print("New Phone: ");
        String phone = sc.nextLine().trim();

        if(phone.isEmpty()){
            phone = rider.getPhone();
        }
        else {
            while (!Validator.isValid10DigitPhone(phone)) {
                System.out.print("Enter valid phone: ");
                phone = sc.nextLine().trim();
                if (phone.isEmpty()) { phone = rider.getPhone(); break; }
            }
        }

        try {
            riderService.updateProfile(name, phone, rider);
            System.out.println("\nProfile updated successfully");
            printUpdatedProfile(rider);
        }
        catch (CabBookingException e){
            System.out.println("Update failed " + e.getMessage());
        }
    }

    private void printUpdatedProfile(User rider) {
        System.out.println("\n--- UPDATED PROFILE ---");

        System.out.println("Name     : " + rider.getName());

        System.out.println("Phone    : " + rider.getPhone());

        System.out.println("Email    : " + rider.getEmail());
    }

    private void viewRideHistory(User rider){
        List<Ride> rides = riderService.getRidesByRider(rider);
        if (rides.isEmpty()) {
            System.out.println("\n  No rides yet.");
            return;
        }
        System.out.println("\n--- RIDE HISTORY (" + rides.size() + " rides) ---");
        for(Ride ride : rides){
            System.out.println(ride);
            System.out.println("\n------------------------------------------------------------");
        }
    }

    private void cancelRide(Ride ride, User rider){
        try {
            riderService.cancelRide(ride, rider);
            System.out.println("Ride cancelled successfully.");
        } catch (CabBookingException e) {
            System.out.println("[!] " + e.getMessage());
        }
    }
}
