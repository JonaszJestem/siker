package SpringSiker.Controllers;

import Siker.Searchers.Item;
import Siker.Searchers.OlxSearcher;
import Siker.Siker;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class OlxController {
    @RequestMapping("/olx")
    String searchQuery(@RequestParam(value = "query") String query) {
        Siker siker = new Siker();
        siker.addSearcher(new OlxSearcher());
        ArrayList<Item> items = new ArrayList<>(siker.search(query));

        return siker.toJson(items);
    }
}
