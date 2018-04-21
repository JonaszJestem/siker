import Siker.*;
import Siker.Searchers.AllegroSearcher;
import Siker.Searchers.Item;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertFalse;

public class AllegroSearcherTest {
    @Test
    @Ignore
    public void shouldReturnResultSetFromGivenSearch() {
        Siker siker = new Siker();
        siker.addSearcher(new AllegroSearcher());

        ArrayList<String> searchPhrases = new ArrayList<>(Arrays.asList("komputer","czesci", "telefon", "samochod", "telewizor", "ksiazka"));

        for(int i = 0; i < 100; i++) {
            ArrayList<Item> items = new ArrayList<>(siker.search(searchPhrases.get(i%searchPhrases.size())));
            assertFalse(items.isEmpty());

        }
    }
}
