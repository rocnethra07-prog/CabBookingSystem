package cabBookingService.controller;

import cabBookingService.exception.CabBookingException;
import cabBookingService.model.User;
import cabBookingService.model.UserRole;
import cabBookingService.service.AuthService;
import cabBookingService.util.InputUtil;

import java.util.Scanner;

public class AuthController {

    private final Scanner sc;
    private final AuthService authService;

    public AuthController(AuthService authService, Scanner sc){
        this.authService = authService;
        this.sc = sc;
    }

    public User registerRider(){
        String name = InputUtil.getName(sc, "Enter name: " , "Name must contain minimum 3 characters");
        String phone = InputUtil.getPhone(sc, "Enter phone: ", "Invalid phone.");
        String email;

        while (true){
            email = InputUtil.getEmail(sc, "Enter email: ", "Invalid email format.");

            //Pre-check for UX
            if(!authService.isUserExists(email)){
                break;
            }
            System.out.println("This email is already registered. Please use a different one");
        }

        String password = InputUtil.getPassword(sc, "Enter password: ", "Password must be at least 8 characters, with an uppercase letter, a lowercase letter, and a special character (@#$%^&+=!-_). Spaces are not allowed.");

        try {
            User user = authService.registerUser(name, phone, email, password, UserRole.RIDER);
            System.out.println("\n  Account created successfully. Welcome, " + user.getName() + "!");
            return user;
        }
        catch (CabBookingException e) {
            System.out.println("[!] Registration failed: " + e.getMessage());
            return null;
        }
    }


    public User login() {

        System.out.print("Enter Email : ");
        String email = sc.nextLine().trim();

        System.out.print("Enter Password : ");
        String password = sc.nextLine().trim();

        if (email.isBlank() || password.isBlank()) {
            System.out.println("Email and password are required");
            return null;
        }

        try {
            User user = authService.loginUser(email, password);
            System.out.println("\nWelcome back, " + user.getName());
            return user;
        }
        catch (CabBookingException e){
            System.out.println("[!] Login failed: " + e.getMessage());
            return null;
        }
    }
}
