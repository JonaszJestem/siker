package Siker;

import Siker.Searchers.Item;
import Siker.Searchers.Searcher;

import java.util.ArrayList;
import java.util.List;

public class Siker {
    private final List<Searcher> searcherList = new ArrayList<>();
    private final List<Item> items = new ArrayList<>();

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

    public String toJson(List<Item> items) {
        StringBuilder sb = new StringBuilder();
        sb.append("{[");
        for(Item i: items) {
            sb.append(i.toJSON());
        }
        sb.append("]}");

        return sb.toString();
    }
}
