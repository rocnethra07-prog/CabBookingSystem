package cabBookingService.util;
import cabBookingService.controller.AdminController;
import cabBookingService.controller.DriverController;
import cabBookingService.controller.RiderController;
import cabBookingService.model.Driver;
import cabBookingService.model.User;
import cabBookingService.service.DriverService;

public class MenuHandler {

    private final AdminController adminController;
    private final DriverController driverController;
    private final RiderController riderController;
    private final DriverService driverService;

    public MenuHandler(AdminController adminController, DriverController driverController, RiderController riderController, DriverService driverService){
        this.adminController = adminController;
        this.driverController = driverController;
        this.riderController = riderController;
        this.driverService = driverService;
    }

    public void showMenu(User user){
        switch (user.getUserRole()){
            case ADMIN :
                adminController.adminDashBoard();
                break;
            case DRIVER :
                // The session holds a User, but DriverController needs a Driver.
                //We resolve the Driver through DriverService.
                Driver driver = driverService.findDriverById(user.getUserId());
                if (driver == null) {
                    System.out.println("!!! Driver account not found. Please contact support.");
                    return;
                }
                driverController.showMenu(driver);
                break;
            case RIDER :
                riderController.showMenu(user);
                break;
        }
    }
}
