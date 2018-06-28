package com.jonaszwiacek.siker.Siker.Searchers;

public class SearchersFactory {
    public static Searcher createSearcher(SearchersEnum s) {
        switch(s) {
            case ALLEGRO:
                return new AllegroSearcher();
            case OLX:
                return new OlxSearcher();
            case SPRZEDAJEMY:
                return new SprzedajemySearcher();
        }
        return null;
    }
}