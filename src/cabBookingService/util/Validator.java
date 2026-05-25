package cabBookingService.util;

public class Validator {

    private Validator(){}

    private static boolean isNullOrEmpty(String s){
        return s == null || s.isBlank();
    }

    public static boolean isValid10DigitPhone(String phoneNumber) {
        if (phoneNumber == null) {
            return false;
        }
        return phoneNumber.matches("\\d{10}");
    }

    private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    public static boolean isValidEmail(String email) {
        if (isNullOrEmpty(email)) {
            return false;
        }
        return email.matches(EMAIL_REGEX);
    }

    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!\\-_])(?=\\S+$).{8,}$";

    public static boolean isValidPassword(String password){
        if(isNullOrEmpty(password)) return false;
        return password.matches(PASSWORD_REGEX);
    }


    public static boolean isValidName(String name){
        if(isNullOrEmpty(name) ) return false;
        return name.trim().length() >= 3;
    }


}
