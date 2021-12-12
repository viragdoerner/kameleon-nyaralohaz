package kameleon.api;

import kameleon.model.Weekendhouse;
import kameleon.service.ApartmentService;
import kameleon.service.WeekendhouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("/weekendhouse")
@RestController
public class WeekendhouseController {

    private final WeekendhouseService weekendhouseService;

    @Autowired
    public WeekendhouseController(WeekendhouseService weekendhouseService) {
        this.weekendhouseService = weekendhouseService;
    }

    @GetMapping
    public Weekendhouse getWeekendhouse() {
        Weekendhouse w = weekendhouseService.getWeekendhouse();
        return w;
    }

    @Secured("ROLE_ADMIN")
    @PutMapping
    public Weekendhouse updateWeekendhouse( @RequestBody Weekendhouse house) {
        Weekendhouse w = weekendhouseService.updateWeekendhouse(house);
        return w;
    }
}
