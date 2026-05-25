package cabBookingService.controller;

import cabBookingService.model.User;
import cabBookingService.service.AuthService;

import java.util.Scanner;

public class AuthController {

    private final Scanner sc;
    private final AuthService authService;

    public AuthController(AuthService authService, Scanner sc){
        this.authService = authService;
        this.sc = sc;
    }

//    public User registerRider(){
//
//    }


    public User login(){

        System.out.print("Enter Email : ");
        String email = sc.nextLine().trim();

        System.out.print("Enter Password : ");
        String password = sc.nextLine().trim();

        if(email.isBlank() || password.isBlank() ){
            System.out.println("Email and password are required");
            return null;
        }

        User user = authService.loginUser(email, password);

        if(user == null){
            System.out.println("Login failed. Invalid email or password.");
            return null;
        }

        System.out.println("\nWelcome back, " + user.getName() );
        return user;
    }
}
