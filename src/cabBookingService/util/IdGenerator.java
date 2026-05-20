package cabBookingService.util;

public class IdGenerator {

    private IdGenerator(){}

    private static int userId = 1;
    private static int cabId = 1;
    private static int rideId = 1;

    public static String generateUserId(){
        return "USR-"+ userId++;
    }
    public static String generateCabId(){
        return "CAB-"+ cabId++;
    }
    public static String generateRideId(){
        return "RIDE-" + rideId++;
    }
}
