package com.jonaszwiacek.siker.Siker.Searchers;

import com.jonaszwiacek.siker.Siker.Sorter;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component("olxSearcher")
public class OlxSearcher implements Searcher {
    private Elements offers_titles, offers_thumbs, offers_prices;
    private Document document;
    private final List<String> img_sources = new ArrayList<>();
    private final List<String> links = new ArrayList<>();

    @Override
    public List<Item> search(String query) {
        List<Item> searchResult = new ArrayList<>();
        try {
            document = Jsoup.connect(query).get();

            Elements offers = document.select("#body-container > div:nth-child(3) > div > div.rel.listHandler > table > tbody > tr > td > table > tbody > tr:nth-child(1)");

            // Remove advertisements as they don't usually have price.
            offers.removeIf(element -> {
                String price = element.select("td.wwnormal.tright.td-price").text();
                return price.isEmpty();
            });


            offers_titles = offers.select("td:nth-child(2) > div > h3 > a");
            offers_thumbs = offers.select("tr:nth-child(1) > td:nth-child(1) > a.thumb");
            offers_prices = offers.select("td.wwnormal.tright.td-price");

            getOffersLinks();
            getImageSources();

            for (int i = 0; i < offers_titles.size(); i++) {
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

    @Override
    public List<Item> search(String query, Sorter sorter) {
        String urlFormat = "https://www.olx.pl/oferty/q-%s/?search[order]=%s";
        List<Item> result = new ArrayList<>();
        switch(sorter) {
            case NONE:
            case ACC:
                return search(String.format(urlFormat, query, ""));
            case PRICE_ASC:
                result = search(String.format(urlFormat, query, "filter_float_price,asc"));
                result.sort(Comparator.comparing(i -> Integer.parseInt(
                        i.getPrice()
                                .replaceAll("[^\\d]", "")
                )));
                break;
            case PRICE_DESC:
                result = search(String.format(urlFormat, query, "filter_float_price,desc"));
                result.sort(Comparator.comparing(i -> Integer.parseInt(
                        i.getPrice()
                                .replaceAll("[^\\d.]", "")
                ), Comparator.reverseOrder()));
                break;
            case NEWEST:
                return search(String.format(urlFormat, query, "created_at,desc"));
            case OLDEST:
                return search(String.format(urlFormat, query, "created_at,asc"));
        }
        return result;
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

        for(Element item: offers_titles) {
            String href = item.attr("href");
            if(href.equalsIgnoreCase("#"))
                continue;
            links.add(href);
        }
    }
}
