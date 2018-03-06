package info.jackpines.core.actors;

import akka.actor.AbstractActor;
import akka.actor.Props;
import info.jackpines.Main;
import info.jackpines.core.interfaces.WordCounter;
import info.jackpines.core.models.SiteWordCounts;
import info.jackpines.impl.WordCountCrawler;

import java.net.URL;
import java.util.Map;

public class CrawlerActor extends AbstractActor {

    static public Props props() {
        return Props.create(CrawlerActor.class, CrawlerActor::new);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(URL.class, url -> {
                    WordCounter crawler = new WordCountCrawler();
                    Map<String, Integer> wordCounts = crawler.getWordCounts(url);
                    Main.queue.add(new SiteWordCounts(url, wordCounts));
                })
                .build();
    }
}
