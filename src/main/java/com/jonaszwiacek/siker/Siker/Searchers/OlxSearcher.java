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

@Component("olxSearcher")
public class OlxSearcher implements Searcher {
    private Elements offers_titles, offers_thumbs, offers_prices, offers_links;
    private final List<String> img_sources = new ArrayList<>();
    private final List<String> links = new ArrayList<>();

    @Override
    public List<Item> search(String query) {
        List<Item> searchResult = new ArrayList<>();
        try {
            String url = "https://www.olx.pl/oferty/q-";
            Document document = Jsoup.connect(url + query).get();

            offers_titles = document.select("div.space.rel a strong");
            offers_thumbs = document.select(".thumb");
            offers_prices = document.select(".wwnormal.tright.td-price");
            offers_links = document.select("div.space.rel a");

            getOffersLinks();
            getImageSources();

            for(int i = 0; i < offers_titles.size(); i++) {
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

        for(Element img: offers_thumbs) {
            if(img.hasClass("nophoto")) {
                img_sources.add("http://www.domkata.com/no_photo.png");
            }
            else {
                img_sources.add(img.select("img").attr("src"));
            }
        }
    }

    private void getOffersLinks() {
        links.clear();

        for(Element item: offers_links) {
            String href = item.attr("href");
            if(href.equalsIgnoreCase("#"))
                continue;

            links.add(href);
        }
    }
}
