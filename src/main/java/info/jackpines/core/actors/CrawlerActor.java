package info.jackpines.core.actors;

import akka.actor.AbstractActor;
import akka.actor.Props;
import info.jackpines.core.interfaces.MessageQueue;
import info.jackpines.core.interfaces.WordCounter;
import info.jackpines.core.models.SiteWordCounts;
import info.jackpines.impl.WordCountCrawler;

import java.net.URL;
import java.util.Map;

public class CrawlerActor extends AbstractActor {

    private final MessageQueue queue;

    static public Props props(final MessageQueue queue) {
        return Props.create(CrawlerActor.class, queue);
    }

    CrawlerActor(final MessageQueue queue) {
        this.queue = queue;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(URL.class, url -> {
                    WordCounter crawler = new WordCountCrawler();
                    Map<String, Integer> wordCounts = crawler.getWordCounts(url);

                    queue.add(new SiteWordCounts(url, wordCounts));
                })
                .build();
    }
}
