package info.jackpines.impl;

import info.jackpines.core.interfaces.WordCounter;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WordCountCrawlerTests {

    @Test
    public void getWordCountsSucceeds() throws MalformedURLException {
        URL url = new URL("https://lipsum.com");
        WordCounter crawler = new WordCountCrawler();

        Map<String, Integer> wordCounts = crawler.getWordCounts(url);

        assertTrue(wordCounts.size() > 600);
        assertEquals(24, (int)wordCounts.getOrDefault("LOREM", 0));
        assertEquals(10, (int)wordCounts.getOrDefault("PAIN", 0));
        assertEquals(2, (int)wordCounts.getOrDefault("PAINS", 0));
        assertEquals(1, (int)wordCounts.getOrDefault("PAINFUL", 0));
        assertEquals(0, (int)wordCounts.getOrDefault("...", 0));
    }

    @Test
    public void getWordCountsHandlesDynamicPage() throws MalformedURLException {
        URL url = new URL("https://www.google.com/finance");
        WordCounter crawler = new WordCountCrawler();

        Map<String, Integer> wordCounts = crawler.getWordCounts(url);

        assertTrue(wordCounts.getOrDefault("STOCKS", 0) >= 2);
        assertTrue(wordCounts.getOrDefault("NEWS", 0) >= 2);
        assertTrue(wordCounts.getOrDefault("DOW", 0) >= 2);
        assertTrue(wordCounts.getOrDefault("TRUMP", 0) >= 1);
    }

    @Test
    public void anotherDynamicPage() throws MalformedURLException {
        URL url = new URL("http://www.starwars.com");
        WordCounter crawler = new WordCountCrawler();

        Map<String, Integer> wordCounts = crawler.getWordCounts(url);

        assertTrue(wordCounts.size() > 1000);
    }
}
