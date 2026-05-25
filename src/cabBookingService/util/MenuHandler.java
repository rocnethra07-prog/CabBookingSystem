package cabBookingService.util;
import cabBookingService.controller.AdminController;
import cabBookingService.controller.DriverController;
import cabBookingService.model.Driver;
import cabBookingService.model.User;
import cabBookingService.repository.DriverRepo;
import java.util.Scanner;

public class MenuHandler {

    private final AdminController adminController;
    private final DriverController driverController;

    public MenuHandler(AdminController adminController, DriverController driverController){
        this.adminController = adminController;
        this.driverController = driverController;
    }

    public void showMenu(User user){
        switch (user.getUserRole()){
            case ADMIN :
                adminController.adminDashBoard();
                break;
            case DRIVER :
                //to avoid downcasting and DriverController requires Driver
                Driver driver = DriverRepo.getInstance().findById(user.getUserId());
                if(driver == null){
                    System.out.println("Driver not found");
                    return;
                }
                driverController.showMenu(driver);
                break;
            case RIDER :
                //yet to implement
                break;
        }
    }
}
