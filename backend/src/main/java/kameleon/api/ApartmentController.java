package kameleon.api;

import exception.CustomMessageException;
import kameleon.dto.UploadFileResponse;
import kameleon.model.Apartment;
import kameleon.model.Weekendhouse;
import kameleon.service.ApartmentService;
import kameleon.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public Apartment addApartmentPictures(@RequestParam("apartmentId") Long apartmentId, @RequestParam("files") MultipartFile[] files) {
        Apartment apartment = apartmentService.getApartmentById(apartmentId);

        List<String> fileNames = Arrays.asList(files)
                .stream()
                .map(file -> {
                    String fileName = fileStorageService.storeFile(file, "apartments");
                    if (fileName == null) {
                        throw new CustomMessageException("One or more of the uploaded files have invalid type");
                    }
                    return fileName;
                })
                .collect(Collectors.toList());

        Apartment a = apartmentService.addApartmentPictures(apartment, fileNames);
        return a;
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping(path = "/deletepic/{apartmentId}/{filename}")
    public Apartment deleteApartmentPicture(@PathVariable("apartmentId") Long apartmentId, @PathVariable("filename") String filename ) {
        fileStorageService.deleteFile(filename, "apartments");
        Apartment a = apartmentService.deletePicture(apartmentId, filename);
        return a;
    }
}
