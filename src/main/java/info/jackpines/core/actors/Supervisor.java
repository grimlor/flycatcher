package info.jackpines.core.actors;

import akka.actor.*;
import akka.japi.pf.DeciderBuilder;
import info.jackpines.core.models.Entity;
import info.jackpines.core.models.SiteWordCounts;
import scala.concurrent.duration.Duration;

import java.net.URL;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Supervisor extends AbstractActor {

    private static SupervisorStrategy strategy =
            new OneForOneStrategy(10, Duration.create(1, TimeUnit.MINUTES),
                    // TODO: identify types of exceptions to handle gracefully
                    DeciderBuilder
                        .matchAny(o -> SupervisorStrategy.escalate())
                        .build());

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(URL.class, url -> {
                    System.out.print(url.toString() + ": ");
                    ActorRef crawler = getContext().actorOf(CrawlerActor.props());
                    getSender().tell(url, crawler);
                })
                .match(SiteWordCounts.class, siteWordCounts -> {
                    System.out.println(siteWordCounts.getSite());
                    for (Map.Entry<String, Integer> wordCount : siteWordCounts.getWordCounts().entrySet()) {
                        System.out.println(wordCount.getKey() + ": " + wordCount.getValue());
                    }
                    System.out.println();
                })
                .match(Entity.class, entity -> {
                    // TODO: persist results in lieu of above printing out of results
                })
                .build();
    }
}
