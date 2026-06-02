package cabBookingService.model;

import cabBookingService.exception.CabBookingException;
import cabBookingService.util.IdGenerator;

import java.util.Objects;

//cab details
//all fields are final
public class Cab {
    private final String cabId;
    private final String model;
    private final CabType cabType;
    private final String registrationNumber;

//    for now, commented it because there is no driver lookup from cab (if needed could be added)
//    private final String driverId; //reference of the driver who owns this cab

    public Cab(String registrationNumber, String model, CabType cabType) {

        if (registrationNumber == null || registrationNumber.isBlank()) {
            throw new CabBookingException("Registration number cannot be null or blank.");
        }

        if (model == null || model.isBlank()) {
            throw new CabBookingException("Car model cannot be null or blank.");
        }

        if (cabType == null) {
            throw new CabBookingException("Cab type cannot be null.");
        }

        this.cabId = IdGenerator.generateCabId();
        this.registrationNumber = registrationNumber.trim().toUpperCase();
        this.model = model.trim();
        this.cabType = cabType;
    }

    public String getCabId() {
        return cabId;
    }

    public String getModel() {
        return model;
    }

    public CabType getCabType() {
        return cabType;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    @Override
    public boolean equals(Object object) {
        if(this == object) {
            return true;
        }
        if (!(object instanceof Cab cab)) return false;
        return Objects.equals(registrationNumber, cab.registrationNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(registrationNumber);
    }

    public String toString(){
        return cabId +
                "\nModel               : " + model +
                "\nCab Type            : " + cabType +
                "\nRegistration Number : "+ registrationNumber;
    }
}