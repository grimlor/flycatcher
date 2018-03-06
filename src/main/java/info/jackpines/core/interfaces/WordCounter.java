package info.jackpines.core.interfaces;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

public interface WordCounter {

    Map<String, Integer> getWordCounts(URL url) throws IOException;
}
