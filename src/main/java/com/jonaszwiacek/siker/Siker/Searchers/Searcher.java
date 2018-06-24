package com.jonaszwiacek.siker.Siker.Searchers;
import com.jonaszwiacek.siker.Siker.Sorter;

import java.util.List;

public interface Searcher {
    List<Item> search(String query, Sorter sorter, int page);
    List<Item> search(String query, Sorter sorter);
    List<Item> search(String query);
}
