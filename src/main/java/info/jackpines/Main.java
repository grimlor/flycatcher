package info.jackpines;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import info.jackpines.core.actors.Supervisor;
import info.jackpines.core.interfaces.MessageQueue;
import info.jackpines.impl.SimpleMessageQueue;
import org.apache.commons.validator.ValidatorException;
import org.apache.commons.validator.routines.UrlValidator;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {

    public static MessageQueue queue;

    private static ActorSystem system;

    public static void main(String[] args) {
        try {
            validate(args);
        } catch (ValidatorException e) {
            System.err.println(e.getMessage());
            System.exit(64);
        }

        queue = new SimpleMessageQueue(new LinkedBlockingQueue<>());

        system = ActorSystem.create("flycatcher");
        Props supervisorProps = Props.create(Supervisor.class);
        ActorRef supervisor = system.actorOf(supervisorProps, "supervisor");

        try {
            // TODO: This code would be replaced by some mechanism for inserting into the queue,
            // eg. RESTful API
            for (String urlString : args) {
                try {
                    queue.add(new URL(urlString));
                } catch (MalformedURLException e) {
                    System.err.println(urlString + " is not a valid URL.");
                }
            }

            while (true) {
                Object message = queue.take();
                supervisor.tell(message, supervisor);
            }

        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
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
                System.err.println(arg + " is not a valid URL.");
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
