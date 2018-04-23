package com.jonaszwiacek.siker.Siker.Searchers;

import java.util.List;

public interface Searcher {
    List<Item> search(String query);
}
