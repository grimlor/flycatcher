package info.jackpines.impl;

import info.jackpines.core.interfaces.MessageQueue;

import java.util.concurrent.BlockingQueue;

public class SimpleMessageQueue implements MessageQueue {

    private final BlockingQueue<Object> queue;

    public SimpleMessageQueue(BlockingQueue<Object> queue) {
        this.queue = queue;
    }


    @Override
    public void add(Object message) {
        queue.add(message);
    }

    @Override
    public Object take() throws InterruptedException {
        return queue.take();
    }
}
