package com.jonaszwiacek.siker.Siker.Searchers;

import org.jsoup.nodes.Element;

import java.util.List;

public interface Searcher {
    List<Item> search(String query);
}
