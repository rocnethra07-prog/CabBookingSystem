package cabBookingService.repository;

import cabBookingService.model.Cab;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CabRepo {

    private static CabRepo cabRepo ;

    //key: id
    private final Map<String, Cab> cabsById = new HashMap<>();

    //to check uniqueness
    private final Set<String> registrationNumberSet = new HashSet<>();
    private CabRepo(){}

    public static CabRepo getInstance(){
        if(cabRepo == null) {
            cabRepo = new CabRepo();
        }
        return cabRepo;
    }

    public void save(Cab cab) {
        cabsById.put(cab.getCabId(), cab);
        registrationNumberSet.add(cab.getRegistrationNumber());
    }

    public boolean existsByRegNumber(String regNumber) {
        if(regNumber == null){
            return false;
        }
        return registrationNumberSet.contains(regNumber.trim().toUpperCase());
    }

    public Cab findById(String id){
        return cabsById.get(id);
    }

}