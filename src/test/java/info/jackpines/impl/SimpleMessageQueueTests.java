package info.jackpines.impl;

import info.jackpines.core.interfaces.MessageQueue;
import org.junit.Test;

import java.util.concurrent.BlockingQueue;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;

@SuppressWarnings("unchecked")
public class SimpleMessageQueueTests {

    @Test
    public void addSucceeds() {
        final BlockingQueue<Object> mockQueue = mock(BlockingQueue.class);
        final MessageQueue testQueue = new SimpleMessageQueue(mockQueue);
        final String testMessage = "Test Message";

        testQueue.add(testMessage);

        verify(mockQueue, times(1)).add(testMessage);
    }

    @Test
    public void takeSucceeds() throws InterruptedException {
        final String testMessage = "Test Message";
        final BlockingQueue<Object> mockQueue = mock(BlockingQueue.class);
        when(mockQueue.take()).thenReturn(testMessage);
        final MessageQueue testQueue = new SimpleMessageQueue(mockQueue);

        Object actual = testQueue.take();

        assertEquals(testMessage, actual);
    }
}
