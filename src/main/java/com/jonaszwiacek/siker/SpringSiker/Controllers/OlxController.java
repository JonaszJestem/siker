package com.jonaszwiacek.siker.SpringSiker.Controllers;

import com.jonaszwiacek.siker.Siker.Searchers.Item;
import com.jonaszwiacek.siker.Siker.Searchers.OlxSearcher;
import com.jonaszwiacek.siker.Siker.Siker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@CrossOrigin
public class OlxController {

    private final Siker siker;

    @Autowired
    public OlxController(Siker siker, OlxSearcher olxSearcher) {
        this.siker = siker;
        OlxSearcher olxSearcher1 = olxSearcher;
        siker.addSearcher(olxSearcher1);
    }


    @RequestMapping("/olx")
    String searchQuery(@RequestParam(value = "query") String query) {
        ArrayList<Item> items = new ArrayList<>(siker.search(query));

        return siker.toJson(items);
    }
}
