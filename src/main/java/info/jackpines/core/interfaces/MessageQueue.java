package info.jackpines.core.interfaces;

public interface MessageQueue {

    void add(final Object message);

    Object take() throws InterruptedException;

    boolean isEmpty();
}
