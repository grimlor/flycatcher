package info.jackpines;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import info.jackpines.core.actors.Supervisor;
import info.jackpines.core.interfaces.MessageQueue;
import info.jackpines.impl.SimpleMessageQueue;
import org.apache.commons.validator.ValidatorException;
import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.LinkedBlockingQueue;

import static java.lang.Thread.sleep;

public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    private static ActorSystem system;

    private static MessageQueue queue;

    public static void setMessageQueue(final MessageQueue mQueue) {
        queue = mQueue;
    }

    public static void main(String[] args) {
        try {
            validate(args);
        } catch (ValidatorException e) {
            logger.error(e.getMessage());
            System.exit(64);
        }

        if (queue == null) {
            queue = new SimpleMessageQueue(new LinkedBlockingQueue<>());
        }

        // TODO: This code would be replaced by some mechanism for inserting into the queue,
        // eg. RESTful API
        for (String urlString : args) {
            try {
                queue.add(new URL(urlString));
            } catch (MalformedURLException e) {
                logger.error("{} is not a valid URL.", urlString);
            }
        }

        system = ActorSystem.create("flycatcher");
        final Props supervisorProps = Supervisor.props(queue);
        final ActorRef supervisor = system.actorOf(supervisorProps, "supervisor");

        try {
            while (!queue.isEmpty()) {
                supervisor.tell(queue.take(), supervisor);
                sleep(600);
            }
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        } finally {
            system.terminate();
        }
    }

    private static void validate(String[] args) throws ValidatorException {
        if (args == null || args.length < 1) {
            throw new ValidatorException("Please provide one or more URLs");
        }

        for (String arg : args) {
            try {
                validate(arg);
            } catch (MalformedURLException e) {
                logger.error("{} is not a valid URL.", arg);
            }
        }
    }

    private static void validate(String urlString) throws MalformedURLException {
        final String[] schemes = {"http","https"}; // DEFAULT schemes = "http", "https", "ftp"
        final UrlValidator urlValidator = new UrlValidator(schemes);
        if (!urlValidator.isValid(urlString)) {
            throw new MalformedURLException(urlString);
        }
    }
}
