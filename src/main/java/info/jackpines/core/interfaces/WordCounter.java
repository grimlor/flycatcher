package info.jackpines.core.interfaces;

import java.net.URL;
import java.util.Map;

public interface WordCounter {

    Map<String, Integer> getWordCounts(URL url);
}
