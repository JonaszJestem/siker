package com.jonaszwiacek.siker.SpringSiker.Controllers;

import com.jonaszwiacek.siker.Siker.Searchers.Item;
import com.jonaszwiacek.siker.Siker.Searchers.OlxSearcher;
import com.jonaszwiacek.siker.Siker.Searchers.SprzedajemySearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jonaszwiacek.siker.Siker.Siker;
import java.util.ArrayList;
import java.util.Arrays;

@RestController
@CrossOrigin
public class SikerController {

    private final Siker siker;

    @Autowired
    private OlxSearcher olxSearcher;
    @Autowired
    private SprzedajemySearcher sprzedajemySearcher;

    @Autowired
    public SikerController(Siker siker) {
        this.siker = siker;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping("/search")
    public String searchQuery(@RequestParam(value = "services") String services, @RequestParam(value = "query") String query) {

        ArrayList searchers = new ArrayList<>();
        if (services.contains(",")) {
            searchers.addAll(Arrays.asList(services.split(",")));
        }
        else {
            searchers.add(services);
        }
        siker.clearSearchers();

        if(searchers.contains("olx")) {
            siker.addSearcher(olxSearcher);
        }
        if(searchers.contains("sprzedajemy")) {
            siker.addSearcher(sprzedajemySearcher);
        }

        return siker.toJson(siker.search(query));
    }
}
