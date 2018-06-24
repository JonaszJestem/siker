package com.jonaszwiacek.siker.SpringSiker.Controllers;

import com.jonaszwiacek.siker.Siker.Searchers.*;
import com.jonaszwiacek.siker.Siker.Sorter;
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

    private final Searcher olxSearcher, allegroSearcher, sprzedajemySearcher;

    @Autowired
    public SikerController(Siker siker, OlxSearcher olxSearcher, Searcher allegroSearcher, Searcher sprzedajemySearcher) {
        this.siker = siker;
        this.olxSearcher = olxSearcher;
        this.allegroSearcher = allegroSearcher;
        this.sprzedajemySearcher = sprzedajemySearcher;
    }

    @RequestMapping("/search")
    public String searchQuery(@RequestParam(value = "services") String services, @RequestParam(value = "query") String query) {

        ArrayList searchers = new ArrayList<>(Arrays.asList(services.split(",")));

        siker.clearSearchers();

        if(searchers.contains("olx")) {
            siker.addSearcher(olxSearcher);
        }
        if(searchers.contains("sprzedajemy")) {
            siker.addSearcher(sprzedajemySearcher);
        }
        if(searchers.contains("allegro")) {
            siker.addSearcher(allegroSearcher);
        }

        return siker.toJson(siker.search(query, Sorter.NONE));
    }
}
