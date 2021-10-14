package kameleon.api;

import kameleon.model.Apartment;
import kameleon.model.Weekendhouse;
import kameleon.service.ApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RequestMapping("/apartment")
@RestController
public class ApartmentController {

    private final ApartmentService apartmentService;

    @Autowired
    public ApartmentController(ApartmentService apartmentService) {
        this.apartmentService = apartmentService;
    }

    @GetMapping
    public List<Apartment> getAllApartment() {
        return apartmentService.getAllApartment();
    }

    @GetMapping(path = "/{id}")
    public Apartment getApartmentById(@PathVariable("id") Long id) {
        Apartment a =apartmentService.getApartmentById(id);
        return a;
    }

    @PutMapping
    public Apartment updateApartmenthouse(@RequestBody Apartment apartment) {
        Apartment a =apartmentService.updateApartment(apartment);
        return a;
    }
}
