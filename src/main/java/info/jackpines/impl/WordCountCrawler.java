package info.jackpines.impl;

import info.jackpines.core.interfaces.WordCounter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class WordCountCrawler implements WordCounter {

    private static final int TIMEOUT = 30000; // TODO: implement configuration feature

    @Override
    public Map<String, Integer> getWordCounts(final URL url) throws IOException {

        final Document doc = Jsoup.parse(url, TIMEOUT);
        String docText = doc.text();

        if (docText == null || docText.isEmpty()) { return new HashMap<>(); }

        docText = docText
                .replaceAll("['.,!:?|&+/;\\-><=—–]+\\s+|…|\\.\"|\\?\"|!\"|[{(\"“”)}]|\\.\\.+|\\s+[']",
                        " ")
                .replaceAll("[‘’]", "'");
        final String[] words = docText.split("\\s+");
        final Map<String, Integer> wordCounts = new HashMap<>();
        for (String word : words) {
            word = word.toUpperCase();
            int count = wordCounts.getOrDefault(word, 0);
            wordCounts.put(word, ++count);
        }

        return wordCounts;
    }
}
