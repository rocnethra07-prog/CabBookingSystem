package cabBookingService.util;

public class Validator {

    private Validator(){}

    public static boolean isValid10DigitPhone(String phoneNumber) {
        if (phoneNumber == null) {
            return false;
        }
        return phoneNumber.matches("\\d{10}");
    }

    private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        return email.matches(EMAIL_REGEX);
    }

}
