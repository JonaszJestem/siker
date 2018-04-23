package com.jonaszwiacek.siker.SpringSiker.Controllers;

import com.jonaszwiacek.siker.Siker.Searchers.Item;
import com.jonaszwiacek.siker.Siker.Searchers.SprzedajemySearcher;
import com.jonaszwiacek.siker.Siker.Siker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@CrossOrigin
public class SprzedajemyController {

    private final Siker siker;

    @Autowired
    public SprzedajemyController(Siker siker, SprzedajemySearcher sprzedajemySearcher) {
        this.siker = siker;
        siker.addSearcher(sprzedajemySearcher);
    }


    @RequestMapping("/sprzedajemy")
    String searchQuery(@RequestParam(value = "query") String query) {
        ArrayList<Item> items = new ArrayList<>(siker.search(query));

        return siker.toJson(items);
    }



}
