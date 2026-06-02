package cabBookingService.main;

import cabBookingService.controller.*;
import cabBookingService.exception.CabBookingException;
import cabBookingService.model.User;
import cabBookingService.repository.*;
import cabBookingService.service.*;
import cabBookingService.config.AdminSeeder;
import cabBookingService.util.MenuHandler;

import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {

        //initialization :
        Scanner sc = new Scanner(System.in);

        //repo creation :
        UserRepo userRepo = UserRepo.getInstance();
        UserAuthRepo userAuthRepo = UserAuthRepo.getInstance();
        DriverRepo driverRepo = DriverRepo.getInstance();
        CabRepo cabRepo = CabRepo.getInstance();
        RideRepo rideRepo = RideRepo.getInstance();

        //auth:
        AuthService authService = new AuthService(userRepo, userAuthRepo);
        AuthController authController = new AuthController(authService,sc);

        //service:
        AdminService adminService = new AdminService(driverRepo, cabRepo,rideRepo,userRepo, authService);
        DriverService driverService = new DriverService(rideRepo, driverRepo);
        RiderService riderService = new RiderService(rideRepo, driverRepo, cabRepo);

        //controller:
        AdminController adminController = new AdminController(adminService, sc);
        DriverController driverController = new DriverController(driverService, sc);
        RiderController riderController = new RiderController(riderService, sc);

        //menu handler:
        MenuHandler menuHandler = new MenuHandler(adminController, driverController, riderController, driverService);

        //admin seed:
        AdminSeeder.seed(authService);

        System.out.println("\n---------------------------");
        System.out.println("--- CAB BOOKING SERVICE ---");
        System.out.println("---------------------------");

        boolean running = true;
        while(running) {
            System.out.println("1. Login");
            System.out.println("2. Register as a Rider");
            System.out.println("0. Exit ");
            System.out.println("Choose: ");
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    try {
                        handleSession(authController.login(), menuHandler);
                    }
                    catch (CabBookingException e){
                        System.out.println("[!] " + e.getMessage());
                    }
                    break;
                case "2":
                    try {
                        handleSession(authController.registerRider(), menuHandler);
                    }
                    catch (CabBookingException e){
                        System.out.println("[!] " + e.getMessage());
                    }
                    break;
                case "0":
                    System.out.println("Goodbye! See you next ride.");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Enter 1, 2 or 0.");
            }
        }
        sc.close();
    }

    private static void handleSession(User user,MenuHandler menuHandler){
        if(user != null){
            System.out.println("Welcome " + user.getName() + " !");
            menuHandler.showMenu(user);
        }
    }
}