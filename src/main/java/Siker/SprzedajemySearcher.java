package Siker;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SprzedajemySearcher implements Searcher {
    String url = "https://sprzedajemy.pl/szukaj?inp_text=";
    Elements offers_text, offers_thumb, offers_price;
    Document document;
    List<String> img_sources = new ArrayList<>();

    @Override
    public List<Item> search(String query) {
        List<Item> searchResult = new ArrayList<>();
        try {
            document = Jsoup.connect(url + query).get();

            offers_text = document.select(".element .title");
            offers_thumb = document.select(".element > ul > li:first-of-type");
            offers_price = document.select(".element .price");

            getImageSources();

            for(int i = 0; i < offers_text.size(); i++) {
                searchResult.add(new Item(
                        offers_text.get(i).text(),
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

        for(Element elt: offers_thumb) {
            if(elt.select("img").attr("src") != null) {
                img_sources.add(elt.select("img").attr("src"));
            }
            else {
                img_sources.add("No photo");
            }
        }

    }

}
