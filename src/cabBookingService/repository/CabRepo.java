package cabBookingService.repository;

import cabBookingService.exception.CabBookingException;
import cabBookingService.model.Cab;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CabRepo extends BaseRepository<Cab>{

    private final static CabRepo INSTANCE = new CabRepo() ;

    private CabRepo(){}

    public static CabRepo getInstance(){
        return INSTANCE;
    }

    public void save(Cab cab) {
        super.save(cab.getCabId(), cab);
    }

    public boolean existsByRegNumber(String regNumber) {
        if(regNumber == null){
            throw new CabBookingException("Registration number cannot be null");
        }

        for(Cab cab : storage.values()){
            if(cab.getRegistrationNumber().equalsIgnoreCase(regNumber)){
                return true;
            }
        }
        return false;
    }

}