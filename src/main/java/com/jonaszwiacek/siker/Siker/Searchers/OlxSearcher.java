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
    private Elements offers_text;
    private Elements offers_thumb;
    private Elements offers_price;
    private Document document;
    private final List<String> img_sources = new ArrayList<>();

    @Override
    public List<Item> search(String query) {
        List<Item> searchResult = new ArrayList<>();
        try {
            String url = "https://www.olx.pl/oferty/q-";
            document = Jsoup.connect(url + query).get();

            offers_text = document.select("div.space.rel a strong");
            offers_thumb = document.select(".thumb");
            offers_price = document.select(".wwnormal.tright.td-price");

            getImageSources();

            for(int i = 0; i < offers_text.size(); i++) {
                searchResult.add(new Item(
                        StringEscapeUtils.escapeJava(offers_text.get(i).text()),
                        img_sources.get(i),
                        offers_price.get(i).text()
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

        for(Element img: offers_thumb) {
            if(img.hasClass("nophoto")) {
                img_sources.add("No photo");
            }
            else {
                img_sources.add(img.select("img").attr("src"));
            }
        }

    }

}