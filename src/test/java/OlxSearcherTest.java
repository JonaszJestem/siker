import com.jonaszwiacek.siker.Siker.Searchers.Item;
import com.jonaszwiacek.siker.Siker.Searchers.OlxSearcher;
import org.junit.Test;
import com.jonaszwiacek.siker.Siker.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class OlxSearcherTest {
    @Test
    public void shouldReturnResultSetFromGivenSearch() {
        Siker siker = new Siker();
        siker.addSearcher(new OlxSearcher());

        ArrayList<String> searchPhrases = new ArrayList<>(Arrays.asList("komputer", "czesci", "telefon", "samochod", "telewizor", "ksiazka"));

        for (String searchPhrase : searchPhrases) {
            ArrayList<Item> items = new ArrayList<>(siker.search(searchPhrase, Sorter.NONE));

            for (Item item : items) {
                assertTrue(item.getLink().contains("olx"));
            }

            assertFalse(items.isEmpty());
        }
    }

    @Test
    public void shouldSortResultSetByPriceAscending() {
        Siker siker = new Siker();
        siker.addSearcher(new OlxSearcher());

        List<Item> items = new ArrayList<>(siker.search("komputer", Sorter.PRICE_ASC));

        assertFalse(items.isEmpty());

        int maxPrice = 0;
        for(Item i: items) {
            int itemPrice = Integer.parseInt(i.getPrice().replaceAll("[^\\d.]", ""));
            assertTrue(itemPrice >= maxPrice);
            maxPrice = itemPrice;
        }
    }

    @Test
    public void shouldSortResultSetByPriceDescending() {
        Siker siker = new Siker();
        siker.addSearcher(new OlxSearcher());

        List<Item> items = new ArrayList<>(siker.search("komputer", Sorter.PRICE_DESC));

        assertFalse(items.isEmpty());

        int maxPrice = Integer.parseInt(items.get(0).getPrice().replaceAll("[^\\d.]", ""));
        for(Item i: items) {
            int itemPrice = Integer.parseInt(i.getPrice().replaceAll("[^\\d.]", ""));
            assertTrue(itemPrice <= maxPrice);
            maxPrice = itemPrice;
        }
    }
}
