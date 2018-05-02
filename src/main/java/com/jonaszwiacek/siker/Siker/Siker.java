package com.jonaszwiacek.siker.Siker;

import com.jonaszwiacek.siker.Siker.Searchers.Item;
import com.jonaszwiacek.siker.Siker.Searchers.Searcher;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("siker")
public class Siker {
    private final List<Searcher> searcherList = new ArrayList<>();
    private final List<Item> items = new ArrayList<>();

    public void addSearcher(Searcher searcher) {
        searcherList.add(searcher);
    }

    public List<Item> search(String query) {
        this.items.clear();
        for(Searcher searcher: searcherList) {
            items.addAll(searcher.search(query));
        }
        return items;
    }

    public String toJson(List<Item> items) {
        StringBuilder sb = new StringBuilder();
        sb.append("{ \"offers\": [");
        for(int i = 0; i < items.size(); i++) {
            sb.append(items.get(i).toJSON());
            if(i < items.size() - 1) sb.append(",");
        }
        sb.append("]}");

        return sb.toString();
    }

    public void clearSearchers() {
        this.searcherList.clear();
    }
}
