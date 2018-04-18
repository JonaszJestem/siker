package SpringSiker.Controllers;

import Siker.Searchers.Item;
import Siker.Searchers.SprzedajemySearcher;
import Siker.Siker;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class SprzedajemyController {
    @RequestMapping("/sprzedajemy")
    String searchQuery(@RequestParam(value = "query") String query) {
        Siker siker = new Siker();
        siker.addSearcher(new SprzedajemySearcher());
        ArrayList<Item> items = new ArrayList<>(siker.search(query));

        return siker.toJson(items);
    }



}
