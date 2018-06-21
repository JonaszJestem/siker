import com.jonaszwiacek.siker.Siker.Searchers.Item;
import com.jonaszwiacek.siker.Siker.Searchers.OlxSearcher;
import com.jonaszwiacek.siker.Siker.Searchers.SprzedajemySearcher;
import com.jonaszwiacek.siker.Siker.Siker;
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

        for(int i = 0; i < 100; i++) {
            ArrayList<Item> items = new ArrayList<>(siker.search(searchPhrases.get(i%searchPhrases.size())));
            assertFalse(items.isEmpty());
        }
    }
}
