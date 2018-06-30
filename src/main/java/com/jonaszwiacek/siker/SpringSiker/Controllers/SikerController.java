package com.jonaszwiacek.siker.SpringSiker.Controllers;

import com.jonaszwiacek.siker.Siker.Searchers.*;
import com.jonaszwiacek.siker.Siker.Sorter;
import com.jonaszwiacek.siker.SpringSiker.Converters.SortingConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jonaszwiacek.siker.Siker.Siker;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public String searchQuery(
            @RequestParam String services,
            @RequestParam String query,
            @RequestParam(defaultValue = "NONE") String sorting,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "-1") String category) {

        List<String> searchers = new ArrayList<>(Arrays.asList(services.split(",")));

        siker.clearSearchers();

        for(String s: searchers) {
            siker.addSearcher(
                    SearchersFactory.createSearcher(SearchersEnum.valueOf(s.toUpperCase()))
            );
        }

        return siker.toJson(siker.search(
                query,
                new SortingConverter().convert(sorting),
                page
        ));
    }
}
