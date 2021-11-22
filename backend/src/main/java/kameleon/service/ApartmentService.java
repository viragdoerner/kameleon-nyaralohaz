package kameleon.service;

import kameleon.dao.ApartmentRepository;
import kameleon.model.Apartment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApartmentService {

    private ApartmentRepository repository;

    @Autowired
    ApartmentService(ApartmentRepository r){
        this.repository = r;
    }

    public List<Apartment> getAllApartment() {
        List<Apartment> list = repository.findAll();
        return list;
    }

    public Apartment getApartmentById(Long id) {
        return repository.getById(id);
    }

    public Apartment updateApartment(Apartment apartment)
    {
        apartment.getProperties().forEach(p -> p.setApartment(apartment));
        return repository.save(apartment);
    }
}
