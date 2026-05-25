package cabBookingService.main;

import cabBookingService.controller.AdminController;
import cabBookingService.controller.AuthController;
import cabBookingService.controller.DriverController;
import cabBookingService.model.User;
import cabBookingService.repository.CabRepo;
import cabBookingService.repository.DriverRepo;
import cabBookingService.repository.UserAuthRepo;
import cabBookingService.repository.UserRepo;
import cabBookingService.service.AdminService;
import cabBookingService.service.AuthService;
import cabBookingService.config.AdminSeeder;
import cabBookingService.service.DriverService;
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

        //auth:
        AuthService authService = new AuthService(userRepo, userAuthRepo);
        AuthController authController = new AuthController(authService,sc);

        //service:
        AdminService adminService = new AdminService(userRepo, userAuthRepo, driverRepo, cabRepo);
        DriverService driverService = new DriverService();

        //controller:
        AdminController adminController = new AdminController(adminService, sc);
        DriverController driverController = new DriverController(driverService, sc);

        //menu handler:
        MenuHandler menuHandler = new MenuHandler(adminController, driverController);

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
                    handleSession(authController.login(),menuHandler);
                    break;
                case "2":
//                    handleSession(authController.registerRider(),menuHandler);
                    break;
                case "0":
                    System.out.println("App exiting");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void handleSession(User user,MenuHandler menuHandler){
        if(user != null){
            System.out.println("Welcome " + user.getName() + " !");
            menuHandler.showMenu(user);
        }
    }
}