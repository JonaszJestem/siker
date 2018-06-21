package com.jonaszwiacek.siker.Siker.Searchers;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component("allegroSearcher")
public class AllegroSearcher implements Searcher {
    private Elements offers_titles, offers_thumbs, offers_prices;
    private Document document;
    private final List<String> img_sources = new ArrayList<>();
    private final List<String> links = new ArrayList<>();

    @Override
    public List<Item> search(String query) {
        List<Item> searchResult = new ArrayList<>();
        String urlFormat = "https://allegro.pl/listing?string=%s&p=0";

        try {
                Document document = Jsoup.connect(String.format(urlFormat, query)).get();

                offers_titles = document.select("section article h2._342830a a");
                offers_thumbs = document.select("section img");

                getOffersLinks();
                getImageSources();

                for (int i = 0; i < offers_titles.size(); i++) {
                    searchResult.add(new Item(
                            StringEscapeUtils.escapeJava(offers_titles.get(i).text()),
                            img_sources.get(i),
                            "",
                            links.get(i)
                    ));
                }

            return searchResult;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return searchResult;
    }


    private void getImageSources() {
        img_sources.clear();

        for(Element elt: offers_thumbs) {
            img_sources.add(elt.attr("src"));
        }
    }

    private void getOffersLinks() {
        links.clear();

        for(Element item: offers_titles) {
            links.add(item.attr("href"));
        }
    }
}
