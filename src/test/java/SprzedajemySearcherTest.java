import Siker.*;
import Siker.Searchers.Item;
import Siker.Searchers.SprzedajemySearcher;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertFalse;

public class SprzedajemySearcherTest {
    @Test
    public void shouldReturnResultSetFromGivenSearch() {
        Siker siker = new Siker();
        siker.addSearcher(new SprzedajemySearcher());

        ArrayList<String> searchPhrases = new ArrayList<>(Arrays.asList("komputer","czesci", "telefon", "samochod", "telewizor", "ksiazka"));

        for(int i = 0; i < 100; i++) {
            ArrayList<Item> items = new ArrayList<>(siker.search(searchPhrases.get(i%searchPhrases.size())));
            assertFalse(items.isEmpty());
        }
    }
}
