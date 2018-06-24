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

@Component("allegroSearcher")
public class AllegroSearcher implements Searcher {
    private Elements offers_titles, offers_thumbs;
    private final List<String> img_sources = new ArrayList<>();
    private final List<String> links = new ArrayList<>();

    @Override
    public List<Item> search(String query) {
        List<Item> searchResult = new ArrayList<>();

        try {
            Document document = Jsoup.connect(query).get();

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

    @Override
    public List<Item> search(String query, Sorter sorter, int page) {
        page++;
        String urlFormat = "https://allegro.pl/listing?string=%s&order=%s&p=%d";
        List<Item> result = new ArrayList<>();

        switch(sorter) {
            case NONE:
                return search(String.format(urlFormat, query, "", page));
            case PRICE_ASC:
                result = search(String.format(urlFormat, query, "p", page));
                break;
            case PRICE_DESC:
                result = search(String.format(urlFormat, query, "pd", page));
                break;
            case NEWEST:
                return search(String.format(urlFormat, query, "n", page));
            case OLDEST:
                return search(String.format(urlFormat, query, "t", page));
            case ACC:
                return search(String.format(urlFormat, query, "m", page));
        }
        return result;
    }

    @Override
    public List<Item> search(String query, Sorter sorter) {
        String urlFormat = "https://allegro.pl/listing?string=%s&order=m";
        List<Item> result = new ArrayList<>();

        switch(sorter) {
            case NONE:
                return search(String.format(urlFormat, query, ""));
            case PRICE_ASC:
                result = search(String.format(urlFormat, query, "p"));
                break;
            case PRICE_DESC:
                result = search(String.format(urlFormat, query, "pd"));
                break;
            case NEWEST:
                return search(String.format(urlFormat, query, "n"));
            case OLDEST:
                return search(String.format(urlFormat, query, "t"));
            case ACC:
                return search(String.format(urlFormat, query, "m"));
        }
        return result;
    }


    private void getImageSources() {
        img_sources.clear();

        for (Element elt : offers_thumbs) {
            img_sources.add(elt.attr("src"));
        }
    }

    private void getOffersLinks() {
        links.clear();

        for (Element item : offers_titles) {
            links.add(item.attr("href"));
        }
    }
}
