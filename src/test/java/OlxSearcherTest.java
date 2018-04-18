import Siker.Searchers.Item;
import Siker.Searchers.OlxSearcher;
import org.junit.Test;
import Siker.*;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class OlxSearcherTest {
    @Test
    public void shouldReturnResultSetFromGivenSearch() {
        Siker siker = new Siker();
        siker.addSearcher(new OlxSearcher());

        ArrayList<String> searchPhrases = new ArrayList<>(Arrays.asList("komputer","czesci", "telefon", "samochod", "telewizor", "ksiazka"));

        for(int i = 0; i < 100; i++) {
            ArrayList<Item> items = new ArrayList<>(siker.search(searchPhrases.get(i%searchPhrases.size())));
            assertFalse(items.isEmpty());

        }
    }
}
