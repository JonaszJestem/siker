package com.jonaszwiacek.siker.Siker;

import com.jonaszwiacek.siker.Siker.Searchers.Item;
import com.jonaszwiacek.siker.Siker.Searchers.Searcher;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.logging.Logger;

@Component("siker")
public class Siker {
    private final List<Searcher> searcherList = new ArrayList<>();
    private final List<Item> items = new ArrayList<>();

    public void addSearcher(Searcher searcher) {
        searcherList.add(searcher);
    }

    public List<Item> search(String query, Sorter sorter) {
        System.out.println("Searching query: " + query + " Using sorter: " + sorter);
        this.items.clear();
        for(Searcher searcher: searcherList) {
            System.out.println("Searching using " + searcher);
            items.addAll(searcher.search(query, sorter));
            System.out.println("Searching complete");
        }
        if(sorter == Sorter.PRICE_ASC) {
            items.sort(Comparator.comparing(i -> Integer.parseInt(
                    i.getPrice()
                            .replaceAll("[^\\d]", "")
            )));
        }
        else if(sorter == Sorter.PRICE_DESC) {
            items.sort(Comparator.comparing(i -> Integer.parseInt(
                    i.getPrice()
                            .replaceAll("[^\\d]", "")
            ), Comparator.reverseOrder()));
        }
        return items;
    }

    public List<Item> search(String query, Sorter sorter, int page) {
        System.out.println("Searching query: " + query + " Using sorter: " + sorter);
        this.items.clear();
        for(Searcher searcher: searcherList) {
            System.out.println("Searching using " + searcher);
            items.addAll(searcher.search(query, sorter, page));
            System.out.println("Searching complete");
        }
        if(sorter == Sorter.PRICE_ASC) {
            items.sort(Comparator.comparing(i -> Integer.parseInt(
                    i.getPrice()
                            .replaceAll("[^\\d]", "")
            )));
        }
        else if(sorter == Sorter.PRICE_DESC) {
            items.sort(Comparator.comparing(i -> Integer.parseInt(
                    i.getPrice()
                            .replaceAll("[^\\d]", "")
            ), Comparator.reverseOrder()));
        }
        System.out.println("Returning results of total " + items.size() + " items.");
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
