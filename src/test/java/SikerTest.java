import com.jonaszwiacek.siker.Siker.Searchers.*;
import com.jonaszwiacek.siker.Siker.Siker;
import com.jonaszwiacek.siker.Siker.Sorter;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SikerTest {
    @Test
    public void shouldReturnResultSetFromGivenSearch() {
        ArrayList<String> searchPhrases = new ArrayList<>(Arrays.asList("komputer", "czesci", "telefon", "samochod", "telewizor", "ksiazka"));
        Siker siker = new Siker();
        siker.addSearcher(new OlxSearcher());
        siker.addSearcher(new AllegroSearcher());
        siker.addSearcher(new SprzedajemySearcher());

        for (String searchPhrase : searchPhrases) {
            ArrayList<Item> items = new ArrayList<>(siker.search(searchPhrase, Sorter.NONE));

            for (Item item : items) {
                assertTrue(item.getLink().contains("olx") || item.getLink().contains("allegro") || item.getLink().contains("sprzedajemy"));
            }

            assertFalse(items.isEmpty());
        }
    }
    @Test
    public void shouldSortResultSetByPriceAscending() {
        Siker siker = new Siker();
        siker.addSearcher(new OlxSearcher());
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
        siker.addSearcher(new OlxSearcher());
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
}
