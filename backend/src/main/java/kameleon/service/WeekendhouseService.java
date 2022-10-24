package kameleon.service;

import kameleon.dao.WeekendhouseRepository;
import kameleon.model.apartman.Property;
import kameleon.model.apartman.Weekendhouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        house.getProperties().forEach(p -> p.setWeekendhouse(house));
        return repository.save(house);
    }

    public void setupWh() {
        Weekendhouse wh = new Weekendhouse();
        wh.setAddress("Semmelweis utca, Balatonszemes");
        String htmlcode = "<div style=\"\">Reggeli madárfütty-koncert, hűs falak, árnyékos teraszok, hosszú pancsolások\"+ és esti sütögetések. Mi ezek miatt szeretünk annyira a balatonszemesi nyaralónkban lenni.</div><div style=\"\"><br></div><div style=\"\"><div>A mi kis nyaralónk  a Balaton déli partjának közepén várja a pihenésre vágyókat.  A nyaraló egy csendes környéken fekszik,&nbsp; jobbra-balra tőle szabadstrandok, illetve homokos öböl található játszótérrel, ami&nbsp; kisgyerekeseknek is ideális. Körülbelül 1 km- re található egy fizetős medencés élményfürdő, ahol a nagy balatoni strandrész szintén ingyenes. </div><div>A nyaralóban 3 apartman található és bérelhető. Ezeket többségében a családunk használja, ezért nagyon jól felszereltek. A vendégek rendelezésére áll például egy&nbsp;mosógép, vasaló, hajszárító, kerékpár stb.</div></div><div style=\"\"><font face=\"Roboto Light\"><br></font></div><div><b>Idén nyáron is várunk Titeket sok szeretettel a Kaméleon Nyaralóházban, csak 2 perc sétára a strandtól!</b></div>";
        wh.setDescription(htmlcode);
        wh.setEmail("mail.dorner.eva@gmail.com");
        wh.setFacebook("https://www.facebook.com/kameleonnyaralohaz");
        wh.setPhone_number("+36302460637");
        Property p1 = new Property();
        p1.setName("2 perc sétára a strandtól");
        p1.setIcon_name("fa-umbrella-beach");
        List<Property> properties = new ArrayList<Property>();
        properties.add(p1);
        wh.setProperties(properties);
        repository.save(wh);
        return;
    }
}
