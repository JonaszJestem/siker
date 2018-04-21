import Siker.Searchers.Item;
import Siker.Searchers.OlxSearcher;
import Siker.Searchers.SprzedajemySearcher;
import Siker.Siker;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertFalse;

public class SikerTest {
    @Test
    public void shouldReturnResultSetFromGivenSearch() {
        ArrayList<String> searchPhrases = new ArrayList<>(Arrays.asList("komputer","czesci", "telefon", "samochod", "telewizor", "ksiazka"));
        Siker siker = new Siker();
        siker.addSearcher(new OlxSearcher());
        siker.addSearcher(new SprzedajemySearcher());

        for(int i = 0; i < 1; i++) {
            ArrayList<Item> items = new ArrayList<>(siker.search(searchPhrases.get(i%searchPhrases.size())));

            for (Item item: items
                    ) {
                System.out.println(item.getImageSource());
            }

            assertFalse(items.isEmpty());
        }
    }
}
