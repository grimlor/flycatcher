package info.jackpines.core.models;

import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class SiteWordCountsTests {

    @Test
    public void EntityFieldsCorrect() {
        final String expected = "SiteWordCounts";
        final UUID id = UUID.randomUUID();
        final Entity siteWordCounts = new SiteWordCounts(id, null, null);

        assertEquals(id, siteWordCounts.getId());
        assertEquals(expected, siteWordCounts.getTableName());
    }

    @Test
    public void NewSiteWordCountsInitializesCorrectly() throws MalformedURLException {
        final URL url = new URL("https://www.google.com");
        final Map<String, Integer> wordCounts = new HashMap<>();
        final SiteWordCounts siteWordCounts = new SiteWordCounts(url, wordCounts);

        assertEquals(null, siteWordCounts.getId());
        assertEquals(url, siteWordCounts.getSite());
        assertEquals(wordCounts, siteWordCounts.getWordCounts());
    }

    @Test
    public void ExistingSiteWordCountsInitializesCorrectly() throws MalformedURLException {
        final UUID id = UUID.randomUUID();
        final URL url = new URL("https://www.google.com");
        final Map<String, Integer> wordCounts = new HashMap<>();
        final SiteWordCounts siteWordCounts = new SiteWordCounts(id, url, wordCounts);

        assertEquals(id, siteWordCounts.getId());
        assertEquals(url, siteWordCounts.getSite());
        assertEquals(wordCounts, siteWordCounts.getWordCounts());
    }
}
