package cabBookingService.controller;

import cabBookingService.builder.DriverRegistrationData;
import cabBookingService.exception.CabBookingException;
import cabBookingService.model.*;
import cabBookingService.service.AdminService;
import cabBookingService.util.*;

import java.util.List;
import java.util.Scanner;

public class AdminController {

    private final AdminService adminService;
    private final Scanner sc;

    public AdminController(AdminService adminService, Scanner sc) {
        this.adminService = adminService;
        this.sc = sc;
    }

    public void adminDashBoard() {
        System.out.println("--- ADMIN DASHBOARD ---");

        boolean back = false;
        while (!back) {
            System.out.println("\n---- ADMIN MENU ----");
            System.out.println("1.  Driver Management");
            System.out.println("2.  Rider Management");
            System.out.println("3.  Ride Management");
            System.out.println("4.  Cab Management");
            System.out.println("0.  Logout");
            System.out.print("Choose: ");

            switch (sc.nextLine().trim()) {
                case "1":
                    driverManagementMenu();
                    break;
                case "2":
                    riderManagementMenu();
                    break;
                case "3":
                    rideManagementMenu();
                    break;
                case "4":
                    cabManagementMenu();
                    break;
                case "0":
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void driverManagementMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n---- DRIVER MANAGEMENT ----");
            System.out.println("1. Add Driver");
            System.out.println("2. Delete Driver");
            System.out.println("3. View All Drivers");
            System.out.println("4. View Available Drivers");
            System.out.println("5. View Unavailable Drivers");
            System.out.println("6. Search Driver by ID");
            System.out.println("7. View Driver's Ride History");
            System.out.println("0. Back");
            System.out.print("Choose: ");

            switch (sc.nextLine().trim()) {
                case "1":
                    addDriver();
                    break;
                case "2":
                    deleteDriver();
                    break;
                case "3":
                    viewAllDrivers();
                    break;
                case "4":
                    viewAvailableDrivers();
                    break;
                case "5":
                    viewUnavailableDrivers();
                    break;
                case "6":
                    searchDriverById();
                    break;
                case "7":
                    viewDriverRideHistory();
                    break;
                case "0":
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private void addDriver() {
        System.out.println("\n--- ADD NEW DRIVER ---");

        System.out.println("\nEnter Driver details:");
        String name = InputUtil.getName(sc, "  Name  : ", "Name must contain minimum 3 characters.");

        String phone = InputUtil.getPhone(sc, "  Phone  : ", "Enter a valid 10-digit phone number.");

        //checking existence check in controller for better user experience on input.
        String email;
        while (true) {
            email = InputUtil.getEmail(sc, "  Email  : ", "Invalid email. Enter a valid email.");
            if (!adminService.isUserExists(email)) break;
            System.out.println("[!]This email is already registered.");
        }

        String password = InputUtil.getPassword(sc, "  Password   : ",
                "Password must be at least 8 characters, with an uppercase, a lowercase, and a special character. No spaces.");

        Location currentLocation = InputUtil.selectLocation(sc, "Current location:");

        String licenseNumber;
        while (true) {
            licenseNumber = InputUtil.getNonEmptyInput(sc, "  License No : ", "License number cannot be empty.");
            if (!adminService.isLicenseNumberExists(licenseNumber)) break;
            System.out.println("[!]License number already exists.");
        }

        System.out.println("\nEnter Cab details:");
        String model = InputUtil.getNonEmptyInput(sc, "  Car Model  : ", "Car model cannot be empty.");

        String registrationNumber;
        while (true) {
            registrationNumber = InputUtil.getNonEmptyInput(sc, "  Reg. No.   : ", "Registration number cannot be empty.");
            if (!adminService.isRegistrationNumExists(registrationNumber)) break;
            System.out.println("[!] Registration number already exists.");
        }

        CabType cabType = InputUtil.selectCabType(sc, "Enter Cab Type: ");

        try {
            DriverRegistrationData request = new DriverRegistrationData.Builder()
                    .name(name)
                    .phone(phone)
                    .email(email)
                    .password(password)
                    .currentLocation(currentLocation)
                    .licenseNumber(licenseNumber)
                    .model(model)
                    .registrationNumber(registrationNumber)
                    .cabType(cabType)
                    .build();

            Driver driver = adminService.addDriver(request);

            System.out.println("\n  Driver added successfully!");

            System.out.println(driver);

        } catch (CabBookingException e) {
            System.out.println("[!] Failed to add driver: " + e.getMessage());
        }
    }

    private void deleteDriver() {
        System.out.println("\n--- DELETE DRIVER ---");
        String driverId = InputUtil.getNonEmptyInput(sc,"Enter Driver ID to delete:", "Driver ID cannot be empty");

        try {
            Driver driver = adminService.findDriverById(driverId);
            System.out.println("\n  Driver found:");
            System.out.println(driver);

            if (!InputUtil.getYesOrNo(sc, "\n  Confirm delete driver '" + driver.getName() + "'?")) {
                System.out.println("  Deletion cancelled.");
                return;
            }

            boolean deleted = adminService.deleteDriver(driverId);
            if (deleted) {
                System.out.println("  Driver deleted successfully.");
            }
            else {
                System.out.println("[!]Cannot delete: driver has an active ride in progress.");
            }

        } catch (CabBookingException e) {
            System.out.println("[!] " + e.getMessage());
        }
    }

    private void viewAllDrivers() {
        List<Driver> drivers = adminService.getAllDrivers();
        System.out.println("\n--- ALL DRIVERS (" + drivers.size() + ") ---");
        if (drivers.isEmpty()) {
            System.out.println("  No drivers registered yet.");
            return;
        }
        for (Driver d : drivers) {
            System.out.println(d);
            System.out.println("  -----------------------------------------------");
        }
    }

    private void viewAvailableDrivers() {
        List<Driver> drivers = adminService.getAvailableDrivers();
        System.out.println("\n--- AVAILABLE DRIVERS (" + drivers.size() + ") ---");
        if (drivers.isEmpty()) {
            System.out.println("  No drivers available right now.");
            return;
        }
        for (Driver d : drivers) {
            System.out.println(d);
            System.out.println("  -----------------------------------------------");
        }
    }

    private void viewUnavailableDrivers() {
        List<Driver> drivers = adminService.getUnavailableDrivers();
        System.out.println("\n--- BUSY DRIVERS (" + drivers.size() + ") ---");
        if (drivers.isEmpty()) {
            System.out.println("  All drivers are currently available.");
            return;
        }
        for (Driver d : drivers) {
            System.out.println(d);
            System.out.println("  -----------------------------------------------");
        }
    }

    private void searchDriverById() {
        System.out.println("\n--- SEARCH DRIVER BY ID ---");
        String driverId = InputUtil.getNonEmptyInput(sc, "  Enter Driver ID: ", "Driver ID cannot be empty.");

        try {
            Driver driver = adminService.findDriverById(driverId);
            System.out.println(driver);

            Cab cab = adminService.getCabForDriver(driver);
            System.out.println("\n  Assigned Cab:");
            System.out.println(cab);

        } catch (CabBookingException e) {
            System.out.println("[!] " + e.getMessage());
        }
    }

    private void viewDriverRideHistory() {
        System.out.println("\n--- DRIVER RIDE HISTORY ---");
        String driverId = InputUtil.getNonEmptyInput(sc, "  Enter Driver ID: ", "Driver ID cannot be empty.");

        try {
            Driver driver = adminService.findDriverById(driverId);
            System.out.println("  Driver: " + driver.getName() + " (" + driverId + ")");

            List<Ride> rides = adminService.getRidesForDriver(driverId);
            if (rides.isEmpty()) {
                System.out.println("  No rides found for this driver.");
                return;
            }
            System.out.println("  Total rides: " + rides.size());
            for (Ride ride : rides) {
                System.out.println(ride);
                System.out.println("  -----------------------------------------------");
            }

        } catch (CabBookingException e) {
            System.out.println("[!] " + e.getMessage());
        }
    }


    private void riderManagementMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n---- RIDER MANAGEMENT ----");
            System.out.println("1. View All Riders");
            System.out.println("2. View Rider's Ride History");
            System.out.println("0. Back");
            System.out.print("Choose: ");

            switch (sc.nextLine().trim()) {
                case "1": viewAllRiders();          break;
                case "2": viewRiderRideHistory();   break;
                case "0": back = true;              break;
                default:  System.out.println("Invalid option.");
            }
        }
    }

    private void viewAllRiders() {
        List<User> riders = adminService.getAllRiders();
        System.out.println("\n--- ALL RIDERS (" + riders.size() + ") ---");
        if (riders.isEmpty()) {
            System.out.println("  No riders registered yet.");
            return;
        }
        for (User rider : riders) {
            System.out.println(rider);
            System.out.println("  -----------------------------------------------");
        }
    }

    private void viewRiderRideHistory() {
        System.out.println("\n--- RIDER RIDE HISTORY ---");
        String riderId = InputUtil.getNonEmptyInput(sc, "  Enter Rider ID: ", "Rider ID cannot be empty.");
        try {
            List<Ride> rides = adminService.getRidesForRider(riderId);
            if (rides.isEmpty()) {
                System.out.println("  No rides found for this rider.");
                return;
            }
            System.out.println("  Total rides: " + rides.size());
            for (Ride ride : rides) {
                System.out.println(ride);
                System.out.println("  -----------------------------------------------");
            }

        } catch (CabBookingException e) {
            System.out.println("[!] " + e.getMessage());
        }
    }

    private void rideManagementMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n---- RIDE MANAGEMENT ----");
            System.out.println("1. View All Rides");
            System.out.println("2. View Active Rides");
            System.out.println("3. View Completed Rides");
            System.out.println("4. View Cancelled Rides");
            System.out.println("0. Back");
            System.out.print("Choose: ");

            switch (sc.nextLine().trim()) {
                case "1":
                    viewAllRides();
                    break;
                case "2":
                    viewActiveRides();
                    break;
                case "3":
                    viewCompletedRides();
                    break;
                case "4":
                    viewCancelledRides();
                    break;
                case "0":
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private void viewAllRides() {
        List<Ride> rides = adminService.getAllRides();

        if (rides.isEmpty()) {
            System.out.println("\n  No rides in history yet.");
            return;
        }

        System.out.println("\n--- ALL RIDES (" + rides.size() + ") ---");
        for (Ride ride : rides){
            System.out.println(ride);
            System.out.println("  -----------------------------------------------");
        }
    }

    private void viewActiveRides() {
        List<Ride> rides = adminService.getActiveRides();

        if (rides.isEmpty()) {
            System.out.println("\n  No active rides right now.");
            return;
        }

        System.out.println("\n--- ACTIVE RIDES (" + rides.size() + ") ---");
        for (Ride ride : rides){
            System.out.println(ride);
            System.out.println("  -----------------------------------------------");
        }
    }

    private void viewCompletedRides() {
        List<Ride> rides = adminService.getCompletedRides();

        if (rides.isEmpty()) {
            System.out.println("\n  No completed rides in history yet.");
            return;
        }

        System.out.println("\n--- COMPLETED RIDES (" + rides.size() + ") ---");
        for (Ride ride : rides){
            System.out.println(ride);
            System.out.println("  -----------------------------------------------");
        }
    }

    private void viewCancelledRides() {
        List<Ride> rides = adminService.getCancelledRides();

        if (rides.isEmpty()) {
            System.out.println("\n  No cancelled rides in history yet.");
            return;
        }

        System.out.println("\n--- CANCELLED RIDES (" + rides.size() + ") ---");
        for (Ride ride : rides){
            System.out.println(ride);
            System.out.println("  -----------------------------------------------");
        }
    }

    private void cabManagementMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n---- CAB MANAGEMENT ----");
            System.out.println("1. View All Cabs");
            System.out.println("2. View Cabs by Type");
            System.out.println("0. Back");
            System.out.print("Choose: ");

            switch (sc.nextLine().trim()) {
                case "1": viewAllCabs();        break;
                case "2": viewCabsByType();     break;
                case "0": back = true;          break;
                default:  System.out.println("Invalid option.");
            }
        }
    }

    private void viewAllCabs() {
        List<Cab> cabs = adminService.getAllCabs();

        if(cabs.isEmpty()){
            System.out.println("\nNo cabs in registry");
            return;
        }

        System.out.println("\n--- ALL CABS (" + cabs.size() + ") ---");

        for (Cab cab : cabs) {
            System.out.println(cab);
            System.out.println("  -----------------------------------------------");
        }
    }

    private void viewCabsByType() {
        CabType type = InputUtil.selectCabType(sc, "Choose Cab Type: ");
        List<Cab> cabs = adminService.getCabsByType(type);

        if (cabs.isEmpty()) {
            System.out.println("  No " + type + " cabs registered.");
            return;
        }

        System.out.println("\n--- " + type + " CABS (" + cabs.size() + ") ---");

        for (Cab cab : cabs) {
            System.out.println(cab);
            System.out.println("  -----------------------------------------------");
        }
    }

}