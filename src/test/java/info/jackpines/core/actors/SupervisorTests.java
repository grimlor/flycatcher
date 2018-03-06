package info.jackpines.core.actors;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.javadsl.TestKit;
import info.jackpines.core.interfaces.MessageQueue;
import info.jackpines.core.models.Entity;
import info.jackpines.core.models.SiteWordCounts;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static akka.pattern.Patterns.ask;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class SupervisorTests {

    private ActorSystem system;
    private Duration timeout = Duration.create(5, SECONDS);

    @Before
    public void setup() {
        system = ActorSystem.create();
    }

    @After
    public void teardown() {
        TestKit.shutdownActorSystem(system);
        system = null;
    }

    @Test
    public void IOExceptionResumes() throws Exception {
        final MessageQueue queue = mock(MessageQueue.class);
        final Props supervisorProps = Supervisor.props(queue);
        final ActorRef supervisor = system.actorOf(supervisorProps, "supervisor");
        final ActorRef child = (ActorRef) Await.result(
                ask(supervisor, Props.create(TestChild.class), 5000),
                timeout);

        child.tell(42, ActorRef.noSender());
        Object result = Await.result(ask(child, "get", 5000), timeout);

        assertEquals(42, result);

        child.tell(new IOException(), ActorRef.noSender());
        result = Await.result(ask(child, "get", 5000), timeout);

        assertEquals(42, result);
    }

    @Test
    public void URLMessageCreatesCrawlerActor() throws Exception {
        final MessageQueue queue = mock(MessageQueue.class);
        final Props supervisorProps = Supervisor.props(queue);
        final ActorRef supervisor = system.actorOf(supervisorProps, "supervisor");
        final URL site = new URL("https://www.google.com");

        supervisor.tell(site, supervisor);
    }

    @Test
    public void SiteWordCountsMessagePrintsResults() throws MalformedURLException {
        final MessageQueue queue = mock(MessageQueue.class);
        final Props supervisorProps = Supervisor.props(queue);
        final ActorRef supervisor = system.actorOf(supervisorProps, "supervisor");
        final URL site = new URL("https://www.google.com");
        final Map<String, Integer> wordCounts = new HashMap<>();
        wordCounts.put("test", 1);
        final SiteWordCounts siteWordCounts = new SiteWordCounts(site, wordCounts);

        supervisor.tell(siteWordCounts, supervisor);
    }

    @Test
    public void EntityMessageHandled() {
        final MessageQueue queue = mock(MessageQueue.class);
        final Props supervisorProps = Supervisor.props(queue);
        final ActorRef supervisor = system.actorOf(supervisorProps, "supervisor");
        final Entity entity = mock(Entity.class);

        supervisor.tell(entity, supervisor);
    }
}
