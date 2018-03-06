package info.jackpines.core.models;

import java.net.URL;
import java.util.Map;
import java.util.UUID;

public class SiteWordCounts extends Entity {

    private static final String TABLE_NAME = "SiteWordCounts";

    private URL site;

    private Map<String, Integer> wordCounts;

    public SiteWordCounts(URL site, Map<String, Integer> wordCounts) {
        this(null, site, wordCounts);
    }

    public SiteWordCounts(UUID id, URL site, Map<String, Integer> wordCounts) {
        super(id);

        this.site = site;
        this.wordCounts = wordCounts;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public URL getSite() {
        return site;
    }

    public Map<String, Integer> getWordCounts() {
        return wordCounts;
    }
}
