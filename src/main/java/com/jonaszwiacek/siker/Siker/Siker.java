package com.jonaszwiacek.siker.Siker;

import com.jonaszwiacek.siker.Siker.Searchers.Item;
import com.jonaszwiacek.siker.Siker.Searchers.Searcher;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("siker")
public class Siker {
    private final List<Searcher> searcherList = new ArrayList<>();
    private final List<Item> items = new ArrayList<>();

    public void addSearcher(Searcher searcher) {
        searcherList.add(searcher);
    }

    public List<Item> search(String query, Sorter sorter) {
        this.items.clear();
        for(Searcher searcher: searcherList) {
            items.addAll(searcher.search(query, sorter));
        }
        if(sorter == Sorter.PRICE_ASC) {
            items.sort(Comparator.comparing(i -> Integer.parseInt(
                    i.getPrice()
                            .replaceAll("[^\\d.]", "")
            )));
        }
        else if(sorter == Sorter.PRICE_DESC) {
            items.sort(Comparator.comparing(i -> Integer.parseInt(
                    i.getPrice()
                            .replaceAll("[^\\d.]", "")
            ), Comparator.reverseOrder()));
        }
        return items;
    }

    public List<Item> search(String query, Sorter sorter, int page) {
        this.items.clear();
        for(Searcher searcher: searcherList) {
            items.addAll(searcher.search(query, sorter, page));
        }
        if(sorter == Sorter.PRICE_ASC) {
            items.sort(Comparator.comparing(i -> Integer.parseInt(
                    i.getPrice()
                            .replaceAll("[^\\d.]", "")
            )));
        }
        else if(sorter == Sorter.PRICE_DESC) {
            items.sort(Comparator.comparing(i -> Integer.parseInt(
                    i.getPrice()
                            .replaceAll("[^\\d.]", "")
            ), Comparator.reverseOrder()));
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

    public void addAll(Searcher[] searchers) {
        searcherList.addAll(Arrays.asList(searchers));
    }
}
