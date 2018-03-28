package Siker;

import java.util.ArrayList;
import java.util.List;

public class Siker {
    List<Searcher> searcherList = new ArrayList<>();
    List<Item> items = new ArrayList<>();

    public void addSearcher(Searcher searcher) {
        searcherList.add(searcher);
    }

    public List<Item> search(String query) {
        items.clear();

        for(Searcher searcher: searcherList) {
            items.addAll(searcher.search(query));
        }

        return items;
    }
}
