package kameleon.service;

import exception.CustomMessageException;
import kameleon.dao.ApartmentRepository;
import kameleon.model.Apartment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        return repository.findById(id).orElseThrow(() ->new CustomMessageException("No such apartment exists"));
    }

    public Apartment updateApartment(Apartment apartment)
    {
        apartment.getProperties().forEach(p -> p.setApartment(apartment));
        return repository.save(apartment);
    }

    public Apartment addApartmentPicture(Apartment apartment, String fileName) {
        //Apartment apartment = repository.findById(apartmentId).orElseThrow(() ->new CustomMessageException("No such apartment exists"));
        apartment.addPicture(fileName);
        return repository.save(apartment);
    }

    public Apartment addApartmentPictures(Apartment apartment, List<String> fileNames) {
        apartment.addPictures(fileNames);
        return repository.save(apartment);
    }

    public Apartment deletePicture(Long apartmentId, String filename) {
        Apartment apartment = this.getApartmentById(apartmentId);
        apartment.deletePicture(filename);
        return repository.save(apartment);
    }
}
