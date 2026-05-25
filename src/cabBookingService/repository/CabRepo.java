package cabBookingService.repository;

import cabBookingService.model.Cab;

import java.util.HashMap;
import java.util.Map;

public class CabRepo {

    private static CabRepo cabRepo ;

    //key: id
    private final Map<String, Cab> cabsById = new HashMap<>();

    //key: registration number
    private final Map<String, Cab> cabsByRegNumber = new HashMap<>();

    private CabRepo(){}

    public static CabRepo getInstance(){
        if(cabRepo == null) {
            cabRepo = new CabRepo();
        }
        return cabRepo;
    }

    public void save(Cab cab) {
        cabsById.put(cab.getCabId(), cab);
        cabsByRegNumber.put(cab.getRegistrationNumber(), cab);
    }

    public boolean existsByRegNumber(String regNumber) {
        if(regNumber == null){
            return false;
        }
        return cabsByRegNumber.containsKey(regNumber.trim().toUpperCase());
    }

    public Cab findById(String id){
        return cabsById.get(id);
    }

}