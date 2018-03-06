package info.jackpines.core.interfaces;

public interface MessageQueue {

    void add(Object message);

    Object take() throws InterruptedException;
}
