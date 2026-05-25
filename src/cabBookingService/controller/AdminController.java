package cabBookingService.controller;

import cabBookingService.model.CabType;
import cabBookingService.model.Driver;
import cabBookingService.model.User;
import cabBookingService.service.AdminService;
import cabBookingService.util.InputUtil;

import java.util.Scanner;

public class AdminController {

    private final AdminService adminService;
    private final Scanner sc;
    public AdminController(AdminService adminService, Scanner sc){
        this.adminService = adminService;
        this.sc = sc;
    }

    public void adminDashBoard() {
        System.out.println("\n---- ADMIN MENU ----");

        boolean back = false;
        while (!back) {
            System.out.println("1. Add Driver");
            System.out.println("0. Back");

            System.out.print("Choose: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    addDriver();
                    break;
                case "0":
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    private void addDriver(){
        System.out.println("\nAdding a new driver: ");

        System.out.println("\nEnter Cab driver details:");
        String name = InputUtil.getName(sc, "Enter name: ", "Name must contain minimum 3 characters ");

        String phone = InputUtil.getPhone(sc, "Enter Phone: " , "Enter valid 10 digit phone");

        String email ;
        while(true){
             email = InputUtil.getEmail(sc, "Enter email: ", "Invalid email. Enter a valid email");
             if(!adminService.isUserExists(email)){
                 break;
             }
            System.out.println("User email already exists. Enter a valid email.");
        }

        String password = InputUtil.getPassword(sc, "Enter password: ", "Password must be 8+ characters with an uppercase letter, lowercase letter, and a special character. No spaces.");

        String currentLocation = InputUtil.getNonEmptyInput(sc, "Enter current location: " , "Location cannot be empty");

        String licenseNumber;
        while(true){
            licenseNumber = InputUtil.getNonEmptyInput(sc, "Enter license number: ", "License number cannot be empty");
            if(!adminService.isLicenseNumberExists(licenseNumber)){
                break;
            }
            System.out.println("License already exists");
        }
        System.out.println("\nEnter Cab details: ");
        String model = InputUtil.getNonEmptyInput(sc, "Enter Car model: " , "Car model cannot be empty");

        String registrationNumber;
        while(true){
            registrationNumber = InputUtil.getNonEmptyInput(sc,"Enter Car registration number: " , "Car registration number cannot be empty");
            if(!adminService.isRegistrationNumExists(registrationNumber)){
                break;
            }
            System.out.println("Registration already exists");
        }

        CabType cabType = selectCabType();

        Driver driver = adminService.addDriver(
                name, phone, email, password,
                currentLocation,
                licenseNumber,
                model,
                registrationNumber,
                cabType
        );

        if(driver == null){
            System.out.println("This email, license number, or registration number is already registered.");
            return;
        }
        System.out.println("\nDriver added successfully");

        System.out.println("Driver ID : " +driver.getUserId());
    }

    private CabType selectCabType(){
        while (true) {
            System.out.println("Cab type: ");
            System.out.println("1 MINI \n2 SEDAN \n3 SUV");

            String type = sc.nextLine().trim();

            switch (type) {
                case "1" :
                    return CabType.MINI;
                case "2" :
                    return CabType.SEDAN;
                case "3":
                    return CabType.SUV;
                default :
                    System.out.println("Invalid choice");
            }
        }
    }
}