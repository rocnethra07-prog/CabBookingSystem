package cabBookingService.repository;

import cabBookingService.exception.CabBookingException;
import cabBookingService.model.Cab;

public class CabRepo extends BaseRepository<Cab>{

    private final static CabRepo INSTANCE = new CabRepo() ;

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