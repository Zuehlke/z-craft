import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by mako on 15.04.2016.
 */
public class TrigrammTest {

    Trigram trigramm;

    Random mockRandom = new Random(){
        @Override
        public int nextInt(int bound) {
            return 0;
        }
    };

    @Before
    public void setup() {
        trigramm = new Trigram();
    }

    @Test
    public void sanitize() {
        assertEquals("eins zwei drei", trigramm.sanitize("eins,-zwei\ndrei"));
        assertEquals("äins zweö dreü", trigramm.sanitize("äins,-zweö\ndreü"));
    }


    @Test
    public void generateSimple() {
        Map<List<String>, Set<String>> corpus = new HashMap<List<String>, Set<String>>();
        corpus.put(ImmutableList.of("eins", "zwei"), ImmutableSet.of("drei"));
        corpus.put(ImmutableList.of("zwei", "drei"), Collections.<String>emptySet());
        assertEquals("eins zwei", trigramm.generate(mockRandom, 2, corpus));
        assertEquals("eins zwei drei", trigramm.generate(mockRandom, 3, corpus));
    }

    @Test
    public void analyzeEmpty() {
        String text = "";
        assertEquals(Collections.emptyMap(), trigramm.analyze(""));
    }

    @Test
    public void analyzeMinimal() {
        String text = "eins zwei";
        assertEquals(Collections.singletonMap(
                ImmutableList.of("eins", "zwei"),
                Collections.emptySet()
        ), trigramm.analyze(text));
    }

    @Test
    public void analyzeThree() {
        String text = "eins zwei drei";
        Map<List<String>, Set<String>> expected = new HashMap<List<String>, Set<String>>();
        expected.put(ImmutableList.of("eins", "zwei"), ImmutableSet.of("drei"));
        expected.put(ImmutableList.of("zwei", "drei"), Collections.<String>emptySet());
        assertEquals(expected, trigramm.analyze(text));
    }

    @Test
    public void analyzeMany() {
        String text = "eins zwei drei eins zwei zwei drei eins sieben";
        Map<List<String>, Set<String>> expected = new HashMap<List<String>, Set<String>>();
        expected.put(ImmutableList.of("eins", "zwei"), ImmutableSet.of("drei", "zwei"));
        expected.put(ImmutableList.of("zwei", "drei"), ImmutableSet.of("eins"));
        expected.put(ImmutableList.of("drei", "eins"), ImmutableSet.of("zwei", "sieben"));
        expected.put(ImmutableList.of("zwei", "zwei"), ImmutableSet.of("drei"));
        expected.put(ImmutableList.of("eins", "sieben"), Collections.<String>emptySet());
        assertEquals(expected, trigramm.analyze(text));
    }
}
