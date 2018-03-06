package info.jackpines.impl;

import info.jackpines.core.interfaces.MessageQueue;

import java.util.concurrent.BlockingQueue;

public class SimpleMessageQueue implements MessageQueue {

    private final BlockingQueue<Object> queue;

    public SimpleMessageQueue(final BlockingQueue<Object> queue) {
        this.queue = queue;
    }


    @Override
    public void add(final Object message) {
        queue.add(message);
    }

    @Override
    public Object take() throws InterruptedException {
        return queue.take();
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
