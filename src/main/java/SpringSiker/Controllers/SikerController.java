package SpringSiker.Controllers;

import Siker.Searchers.Item;
import Siker.Searchers.OlxSearcher;
import Siker.Searchers.SprzedajemySearcher;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Siker.Siker;
import java.util.ArrayList;
import java.util.Arrays;

@RestController
@CrossOrigin
public class SikerController {

    @RequestMapping("/search")
    public String searchQuery(@RequestParam(value = "services") String services, @RequestParam(value = "query") String query) {

        ArrayList searchers = new ArrayList<>();
        if (services.contains(",")) {
            searchers.addAll(Arrays.asList(services.split(",")));
        }
        else {
            searchers.add(services);
        }
        Siker siker = new Siker();
        if(searchers.contains("olx")) {
            siker.addSearcher(new OlxSearcher());
        }
        if(searchers.contains("sprzedajemy")) {
            siker.addSearcher(new SprzedajemySearcher());
        }

        ArrayList<Item> items = new ArrayList<>(siker.search(query));
        return siker.toJson(items);
    }
}
