package cabBookingService.repository;

import cabBookingService.exception.CabBookingException;
import cabBookingService.model.Cab;
import cabBookingService.model.CabType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CabRepo extends BaseRepository<Cab>{

    private static final CabRepo INSTANCE = new CabRepo() ;

    private CabRepo(){}

    public static CabRepo getInstance(){
        return INSTANCE;
    }

    public void save(Cab cab) {
        String key = cab.getCabId().trim();
        if(existsByKey(key)){
            throw new CabBookingException("Record already exists for key : " + key);
        }
        super.save(key, cab);
    }

    public boolean existsByRegNumber(String regNumber) {
        if(regNumber == null || regNumber.isBlank()){
            throw new CabBookingException("Registration number cannot be null or blank");
        }

        for(Cab cab : storage.values()){
            if(cab.getRegistrationNumber().equalsIgnoreCase(regNumber)){
                return true;
            }
        }
        return false;
    }

    public List<Cab> findCabsByCabType(CabType cabType){
        List<Cab> cabs = new ArrayList<>();
        for(Cab cab : storage.values()){
            if(cab.getCabType() == cabType){
                cabs.add(cab);
            }
        }
        return Collections.unmodifiableList(cabs);
    }
}