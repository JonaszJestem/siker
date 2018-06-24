import com.jonaszwiacek.siker.Siker.*;
import com.jonaszwiacek.siker.Siker.Searchers.Item;
import com.jonaszwiacek.siker.Siker.Searchers.SprzedajemySearcher;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SprzedajemySearcherTest {
    @Test
    public void shouldReturnResultSetFromGivenSearch() {
        Siker siker = new Siker();
        siker.addSearcher(new SprzedajemySearcher());

        ArrayList<String> searchPhrases = new ArrayList<>(Arrays.asList("komputer", "czesci", "telefon", "samochod", "telewizor", "ksiazka"));

        for (String searchPhrase : searchPhrases) {
            ArrayList<Item> items = new ArrayList<>(siker.search(searchPhrase, Sorter.NONE));

            for (Item item : items) {
                assertTrue(item.getLink().contains("sprzedajemy"));
            }

            assertFalse(items.isEmpty());
        }
    }

    @Test
    public void shouldSortResultSetByPriceAscending() {
        Siker siker = new Siker();
        siker.addSearcher(new SprzedajemySearcher());

        List<Item> items = new ArrayList<>(siker.search("komputer", Sorter.PRICE_ASC));

        assertFalse(items.isEmpty());

        int maxPrice = 0;
        for(Item i: items) {
            int itemPrice = Integer.parseInt(i.getPrice().replaceAll("[^\\d]", ""));
            assertTrue(itemPrice >= maxPrice);
            maxPrice = itemPrice;
        }
    }

    @Test
    public void shouldSortResultSetByPriceDescending() {
        Siker siker = new Siker();
        siker.addSearcher(new SprzedajemySearcher());

        List<Item> items = new ArrayList<>(siker.search("komputer", Sorter.PRICE_DESC));

        assertFalse(items.isEmpty());

        int maxPrice = Integer.parseInt(items.get(0).getPrice().replaceAll("[^\\d]", ""));
        for(Item i: items) {
            int itemPrice = Integer.parseInt(i.getPrice().replaceAll("[^\\d]", ""));
            assertTrue(itemPrice <= maxPrice);
            maxPrice = itemPrice;
        }
    }


    @Test
    public void shouldSearchThroughPages() {
        Siker siker = new Siker();
        siker.addSearcher(new SprzedajemySearcher());
        for(int i=0; i < 10; i++) {
            List<Item> items = new ArrayList<>(siker.search("komputer", Sorter.NONE, i));

            assertFalse(items.isEmpty());
        }
    }
}
