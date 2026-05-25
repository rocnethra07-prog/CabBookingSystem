package cabBookingService.util;

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

}
