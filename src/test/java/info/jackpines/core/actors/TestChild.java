package info.jackpines.core.actors;

import akka.actor.AbstractActor;

public class TestChild extends AbstractActor {

    private int state = 0;

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Exception.class, exception -> { throw exception; })
                .match(Integer.class, i -> state = i)
                .matchEquals("get", s -> getSender().tell(state, getSelf()))
                .build();    }
}
