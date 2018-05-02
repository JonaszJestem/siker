import com.jonaszwiacek.siker.Siker.Searchers.Item;
import com.jonaszwiacek.siker.Siker.Searchers.OlxSearcher;
import org.junit.Test;
import com.jonaszwiacek.siker.Siker.*;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class OlxSearcherTest {
    @Test
    public void shouldReturnResultSetFromGivenSearch() {
        Siker siker = new Siker();
        siker.addSearcher(new OlxSearcher());

        ArrayList<String> searchPhrases = new ArrayList<>(Arrays.asList("komputer","czesci", "telefon", "samochod", "telewizor", "ksiazka"));

        long avg_time = 0;
        for(int i = 0; i < 100; i++) {
            long start = System.currentTimeMillis();
            ArrayList<Item> items = new ArrayList<>(siker.search(searchPhrases.get(i%searchPhrases.size())));
            long end = System.currentTimeMillis();
            System.out.println("DEBUG: Logic A took " + (end - start) + " MilliSeconds");
            avg_time+= end - start;
            assertFalse(items.isEmpty());
        }
        System.out.println("AVG: " + avg_time/100);
    }
}
