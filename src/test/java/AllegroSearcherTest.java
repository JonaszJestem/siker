import com.jonaszwiacek.siker.Siker.*;
import com.jonaszwiacek.siker.Siker.Searchers.AllegroSearcher;
import com.jonaszwiacek.siker.Siker.Searchers.Item;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertFalse;

public class AllegroSearcherTest {
    @Test
    public void shouldReturnResultSetFromGivenSearch() {
        Siker siker = new Siker();
        siker.addSearcher(new AllegroSearcher());

        ArrayList<String> searchPhrases = new ArrayList<>(Arrays.asList("komputer","czesci", "telefon", "samochod", "telewizor", "ksiazka"));

        for(int i = 0; i < 1; i++) {
            ArrayList<Item> items = new ArrayList<>(siker.search(searchPhrases.get(i%searchPhrases.size())));
            //items.forEach(item -> System.out.println(item.getTitle()));
            System.out.println(items.size());
            assertFalse(items.isEmpty());
        }
    }
}
