package kameleon.service;

import exception.CustomMessageException;
import kameleon.dao.ApartmentRepository;
import kameleon.model.apartman.Apartment;
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

    public void setupApartments() {
        Apartment a1 = new Apartment();
        a1.setName("apartman egy");
        a1.setDescription("todo");
        a1.setPrice(1);
        repository.save(a1);

        Apartment a2 = new Apartment();
        a2.setName("apartman ketto");
        a2.setDescription("todo");
        a2.setPrice(1);
        repository.save(a2);

        Apartment a3 = new Apartment();
        a3.setName("apartman harom");
        a3.setDescription("todo");
        a3.setPrice(1);
        repository.save(a3);

        return;
    }
}
