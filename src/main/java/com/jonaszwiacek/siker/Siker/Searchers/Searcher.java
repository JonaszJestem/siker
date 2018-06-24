package com.jonaszwiacek.siker.Siker.Searchers;
import com.jonaszwiacek.siker.Siker.Sorter;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface Searcher {
    List<Item> search(String query);

    List<Item> search(String query, Sorter sorter);
}
