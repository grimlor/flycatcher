package info.jackpines.core.actors;

import akka.actor.*;
import akka.japi.pf.DeciderBuilder;
import info.jackpines.core.interfaces.MessageQueue;
import info.jackpines.core.models.Entity;
import info.jackpines.core.models.SiteWordCounts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.concurrent.duration.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Supervisor extends AbstractActor {

    private static Logger logger = LoggerFactory.getLogger(Supervisor.class);

    private final MessageQueue queue;

    private static SupervisorStrategy strategy =
            new OneForOneStrategy(10, Duration.create(1, TimeUnit.MINUTES),
                    // TODO: identify types of exceptions to handle gracefully
                    DeciderBuilder
                            .match(IOException.class, e -> {
                                logger.error(e.getMessage());
                                return SupervisorStrategy.resume();
                            })
                            .matchAny(o -> SupervisorStrategy.escalate())
                            .build());

    static public Props props(final MessageQueue queue) {
        return Props.create(Supervisor.class, queue);
    }

    Supervisor(final MessageQueue queue) {
        this.queue = queue;
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(URL.class, url -> {
                    final ActorRef crawler = getContext().actorOf(CrawlerActor.props(queue));
                    getSender().tell(url, crawler);
                })
                .match(SiteWordCounts.class, siteWordCounts -> {
                    logger.info("{}: {}", siteWordCounts.getSite().toString(), siteWordCounts.getWordCounts().size());
                    for (Map.Entry<String, Integer> wordCount : siteWordCounts.getWordCounts().entrySet()) {
                        logger.info("{}: {}", wordCount.getKey(), wordCount.getValue());
                    }
                })
                .match(Entity.class, entity -> {
                    // TODO: persist results in lieu of above printing out of results
                    logger.info("Entity received");
                })
                .match(Props.class, props -> getSender().tell(getContext().actorOf(props), getSelf()))
                .build();
    }
}
