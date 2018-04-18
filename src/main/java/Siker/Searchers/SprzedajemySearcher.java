package Siker.Searchers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SprzedajemySearcher implements Searcher {
    private final String url = "https://sprzedajemy.pl/szukaj?inp_text=";
    private Elements offers_text;
    private Elements offers_thumb;
    private Elements offers_price;
    private Document document;
    private final List<String> img_sources = new ArrayList<>();

    @Override
    public List<Item> search(String query) {
        List<Item> searchResult = new ArrayList<>();
        try {
            document = Jsoup.connect(url + query).get();
            Elements offers = document.select(".element");

            // Remove the current element from the iterator and the list.
            offers.removeIf(element -> {
                String price = element.select(".price").text();
                String title = element.select(".title a").text();
                return price.isEmpty() || title.isEmpty() || element.select("a[href^=http]").size() != 0;
            });

            offers_text = offers.select(".title a");
            offers_thumb = offers.select("ul > li:first-of-type");
            offers_price = offers.select(".price");

            getImageSources();

            for(int i = 0; i < offers.size(); i++) {
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
