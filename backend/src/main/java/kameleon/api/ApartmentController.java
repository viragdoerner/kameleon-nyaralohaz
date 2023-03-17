package kameleon.api;

import exception.CustomMessageException;
import kameleon.model.apartman.Apartment;
import kameleon.service.ApartmentService;
import kameleon.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("/apartment")
@RestController
public class ApartmentController {

    private final ApartmentService apartmentService;
    private final FileStorageService fileStorageService;

    @Autowired
    public ApartmentController(ApartmentService apartmentService,FileStorageService fileStorageService) {
        this.apartmentService = apartmentService;
        this.fileStorageService = fileStorageService;
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
    @Secured("ROLE_ADMIN")
    @PutMapping
    public Apartment updateApartment(@RequestBody Apartment apartment) {
        Apartment a =apartmentService.updateApartment(apartment);
        return a;
    }

    // not in use
    @Secured("ROLE_ADMIN")
    @PostMapping(path = "/addpicture")
    public Apartment addApartmentPicture(@RequestParam("apartmentId") Long apartmentId, @RequestParam("file") MultipartFile file) {
        Apartment apartment = apartmentService.getApartmentById(apartmentId);

        String fileName = fileStorageService.storeFile(file, "apartments");
        if (fileName == null) {
            throw new CustomMessageException("The file is not an image");
        }
        Apartment a =apartmentService.addApartmentPicture(apartment, fileName);
        return a;
    }

    @PostMapping(path = "/addpictures")
    @Secured("ROLE_ADMIN")
    public Apartment addApartmentPictures(@RequestParam("apartmentId") Long apartmentId, @RequestParam("imageURLs") List<String> imageURLs) {
        Apartment apartment = apartmentService.getApartmentById(apartmentId);

        Apartment a = apartmentService.addApartmentPictures(apartment, imageURLs);
        return a;
    }

    @Secured("ROLE_ADMIN")
    @PostMapping(path = "/deletepicture")
    public Apartment deleteApartmentPicture(@RequestParam("apartmentId") Long apartmentId, @RequestParam("imageURL") String imageURL ) {
        Apartment a = apartmentService.deletePicture(apartmentId, imageURL);
        return a;
    }
}
