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

@Component("sprzedajemySearcher")
public class SprzedajemySearcher implements Searcher {
    private Elements offers_titles, offers_thumbs, offers_prices;
    private final List<String> img_sources = new ArrayList<>();
    private final List<String> links = new ArrayList<>();

    @Override
    public List<Item> search(String query) {
        List<Item> searchResult = new ArrayList<>();
        try {
            String url = "https://sprzedajemy.pl/szukaj?inp_text=";
            Document document = Jsoup.connect(url + query).get();
            Elements offers = document.select(".element");

            // Remove the current element from the iterator and the list.
            offers.removeIf(element -> {
                String price = element.select(".price").text();
                String title = element.select(".title a").text();
                return price.isEmpty() || title.isEmpty() || element.select("a[href^=http]").size() != 0;
            });

            offers_titles = offers.select(".title a");
            offers_thumbs = offers.select("ul > li:first-of-type");
            offers_prices = offers.select(".price");

            getImageSources();
            getOffersLinks();

            for(int i = 0; i < offers.size(); i++) {
                searchResult.add(new Item(
                        StringEscapeUtils.escapeJava(offers_titles.get(i).text()),
                        img_sources.get(i),
                        offers_prices.get(i).text(),
                        links.get(i)
                ));
            }
            return searchResult;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void getImageSources() {
        img_sources.clear();

        for(Element elt: offers_thumbs) {
            if (elt.select("img").attr("src") != null) {
                img_sources.add(elt.select("img").attr("src"));
            } else {
                img_sources.add("http://www.domkata.com/no_photo.png");
            }
        }
    }

    private void getOffersLinks() {
        links.clear();

        for(Element item: offers_titles) {
            links.add("sprzedajemy.pl/" + item.attr("href"));
        }
    }

}
