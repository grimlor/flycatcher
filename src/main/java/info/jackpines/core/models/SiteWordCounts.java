package info.jackpines.core.models;

import java.net.URL;
import java.util.Map;
import java.util.UUID;

public class SiteWordCounts extends Entity {

    private static final String TABLE_NAME = "SiteWordCounts";

    private final URL site;

    private final Map<String, Integer> wordCounts;

    public SiteWordCounts(final URL site, final Map<String, Integer> wordCounts) {
        this(null, site, wordCounts);
    }

    SiteWordCounts(final UUID id, final URL site, final Map<String, Integer> wordCounts) {
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
