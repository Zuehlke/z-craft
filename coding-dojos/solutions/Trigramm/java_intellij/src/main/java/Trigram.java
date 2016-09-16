import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import sun.misc.IOUtils;

import java.util.*;

/**
 * Created by mako on 15.04.2016.
 */
public class Trigram {

    public static void main(String[] args) throws Exception{
        Trigram trigram = new Trigram();
        String source = org.apache.commons.io.IOUtils.toString(Trigram.class.getClassLoader().getResourceAsStream("gpl.txt"));
        Map<List<String>, Set<String>> corpus = trigram.analyze(trigram.sanitize(source));
        System.out.println(trigram.generate(new Random(), 200, corpus));
    }

    public String generate(Random random, int wordCount, Map<List<String>, Set<String>> corpus) {
        List<String> result = new ArrayList<>();
        List<List<String>> pairs = new ArrayList<List<String>>(corpus.keySet());
        result.addAll(pairs.get(random.nextInt(pairs.size())));
        for(int i=2; i<wordCount; i++){
            Set<String> currentPair = corpus.get(Arrays.asList(result.get(i - 2).trim(), result.get(i - 1).trim()));
            List<String> candidates = new ArrayList<>(currentPair);
            String tmp = candidates.get(random.nextInt(candidates.size()));
            if(result.size() % 15 == 14){
                tmp += "\n";
            }
            result.add(tmp);
        }
        return Joiner.on(" ").join(result);
    }

    public String sanitize(String input){
        input = input.replaceAll("[^a-zA-ZäöüÄÖÜß]", " ");
        input = input.replaceAll(" +", " ");
        return input;
    }

    public Map<List<String>, Set<String>> analyze(String input) {
        HashMap<List<String>, Set<String>> result = new HashMap<>();
        String[] words = input.split(" ");
        for(int i=0; i<words.length-1; i++) {
            List<String> pair = ImmutableList.of(words[i], words[i+1]);

            Set<String> successors = result.get(pair);
            if(successors == null) {
                successors = new HashSet<String>();
                result.put(pair, successors);
            }
            if(i < words.length-2) {
                successors.add(words[i + 2]);
            }
        }

        return result;
    }
}
