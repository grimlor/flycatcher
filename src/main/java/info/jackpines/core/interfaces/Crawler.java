package info.jackpines.core.interfaces;

import java.net.URI;
import java.util.HashMap;

public interface Crawler {
    public HashMap<String, Integer> getWordCounts(URI uri);
}
