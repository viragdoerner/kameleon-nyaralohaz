package kameleon.service;

import kameleon.dao.WeekendhouseRepository;
import kameleon.model.Weekendhouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeekendhouseService {

    private WeekendhouseRepository repository;

    @Autowired
    WeekendhouseService(WeekendhouseRepository r){
        this.repository = r;
    }

    public Weekendhouse getWeekendhouse() {
        List<Weekendhouse> list = repository.findAll();
        if(list == null || list.size() == 0){
            return null;
        }
        return list.get(0);
    }

    public Weekendhouse updateWeekendhouse(Weekendhouse house){
        return repository.save(house);
    }
}
