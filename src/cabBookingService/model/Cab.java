package cabBookingService.model;

import cabBookingService.util.IdGenerator;

import java.util.Objects;

//cab details
//all fields are final
public class Cab {
    private final String cabId;
    private final String model;
    private final CabType cabType;
    private final String registrationNumber;

    public Cab(String registrationNumber, String model, CabType cabType) {
        if(registrationNumber == null || registrationNumber.isBlank()) {
            throw new IllegalArgumentException("Registration number cannot be null or blank");
        }

        if(model == null || model.isBlank()) {
            throw new IllegalArgumentException("Model cannot be null or blank");
        }

        if(cabType == null) {
            throw new IllegalArgumentException("Cab type cannot be null");
        }
        this.cabId = IdGenerator.generateCabId();
        this.registrationNumber = registrationNumber.trim();
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
}