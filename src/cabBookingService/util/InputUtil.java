package cabBookingService.util;

import cabBookingService.model.CabType;
import cabBookingService.model.Location;

import java.util.Scanner;
import java.util.function.Predicate;

public class InputUtil {

    private InputUtil(){}

    private static String getValidatedInput(Scanner sc, String prompt, Predicate<String> validator, String errorMessage){
        while(true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            if(validator.test(input)){
                return input;
            }
            System.out.println(errorMessage);
        }
    }

    public static String getNonEmptyInput(Scanner sc, String prompt, String errorMessage) {
        return getValidatedInput(sc, prompt, input -> !input.isEmpty() ,errorMessage);
    }

    public static String getName(Scanner sc, String prompt, String errorMessage){
        return getValidatedInput(sc, prompt, Validator::isValidName, errorMessage);
    }

    public static String getPhone(Scanner sc, String prompt, String errorMessage){
        return getValidatedInput(sc, prompt, Validator::isValid10DigitPhone, errorMessage);
    }

    public static String getEmail(Scanner sc, String prompt, String errorMessage){
        return getValidatedInput(sc, prompt, Validator::isValidEmail, errorMessage);
    }

    public static String getPassword(Scanner sc, String prompt, String errorMessage){
        return getValidatedInput(sc, prompt, Validator::isValidPassword, errorMessage);
    }

    public static CabType selectCabType(Scanner sc, String prompt){
        CabType[] cabTypes = CabType.values();
        while (true){
            System.out.println(prompt);
            for(int i = 0; i<cabTypes.length ; i++){
                System.out.println(i+1 + ". " + cabTypes[i]);
            }
            System.out.print("Choice: ");
            String input = sc.nextLine().trim();

            try{
                int idx = Integer.parseInt(input);
                if(idx >= 1 && idx <= cabTypes.length){
                    return cabTypes[idx - 1];
                }
            }
            catch (NumberFormatException ignored) {

            }
            System.out.println("Invalid selection. Enter a number between 1 and " + cabTypes.length);
        }
    }

    public static Location selectLocation(Scanner sc, String prompt) {
        Location[] locations = Location.values();
        while (true) {
            System.out.println(prompt);
            for (int i = 0; i < locations.length; i++) {
                System.out.println(i + 1 + ". " + locations[i]);
            }
            System.out.print("  Choice: ");
            String input = sc.nextLine().trim();
            try {
                int idx = Integer.parseInt(input);
                if (idx >= 1 && idx <= locations.length) {
                    return locations[idx - 1];
                }
            }
            catch (NumberFormatException ignored) {

            }
            System.out.println("Invalid selection. Enter a number between 1 and " + locations.length);
        }
    }

    public static boolean getYesOrNo(Scanner sc, String message) {
        while (true) {
            System.out.print(message + " (y/n): ");
            String input = sc.nextLine().trim();

            if (input.equalsIgnoreCase("y")) {
                return true;
            }
            if (input.equalsIgnoreCase("n")) {
                return false;
            }

            System.out.println("Invalid input. Please enter y or n.");
        }
    }

    public static String getOptionalName(Scanner sc, String current) {
        System.out.print("New name (Enter to keep '" + current + "'): ");
        String input = sc.nextLine().trim();
        if (input.isEmpty()) {
            return current;
        }
        return getValidatedInput(sc, "Enter valid name (min 3 chars): ",
                Validator::isValidName, "Name must be at least 3 characters.");
    }

    public static String getOptionalPhone(Scanner sc, String current) {
        System.out.print("New phone (Enter to keep current): ");
        String input = sc.nextLine().trim();
        if (input.isEmpty()){
            return current;
        }
        return getValidatedInput(sc, "Enter valid 10-digit phone: ",
                Validator::isValid10DigitPhone, "Invalid phone number.");
    }
}
