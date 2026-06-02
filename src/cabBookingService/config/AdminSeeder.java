package cabBookingService.config;

import cabBookingService.model.UserRole;
import cabBookingService.service.AuthService;

//Seeds the default admin account on first startup.
//Hardcoded here only for this in-memory demo.

public class AdminSeeder {

    private static final String ADMIN_EMAIL = "admin@cabbooking.com";
    private static final String ADMIN_NAME = "System Admin";
    private static final String ADMIN_PHONE = "9999999999";
    private static final String ADMIN_PASSWORD = "Admin@123";

    private AdminSeeder() {}

    public static void seed(AuthService authService){
        if(!authService.isUserExists(ADMIN_EMAIL)) {
            authService.registerUser(
                    ADMIN_NAME, ADMIN_PHONE,
                    ADMIN_EMAIL, ADMIN_PASSWORD,
                    UserRole.ADMIN
            );
        }

//        if(u != null){
//            System.out.println("Admin account initialized: " + ADMIN_EMAIL);
//        }
    }
}

