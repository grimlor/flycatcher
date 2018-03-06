package info.jackpines;

import info.jackpines.core.interfaces.MessageQueue;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MainTests {

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @Test
    public void FunctionalTest() {
        String[] testSites = { "https://www.google.com/finance", "https://lipsum.com" };

        Main.main(testSites);
    }

    @Test
    public void MalformedUrlContinuesProcessing() {
        String[] testSites = { "badUrl", "https://www.google.com/finance", "https://lipsum.com" };

        Main.main(testSites);
    }

    @Test
    public void NoArgsExits() {
        exit.expectSystemExitWithStatus(64);
        String[] noSites = {};
        Main.main(noSites);
    }

    @Test
    public void InterruptedExceptionHandled() throws InterruptedException {
        MessageQueue queue = mock(MessageQueue.class);
        when(queue.take()).thenThrow(InterruptedException.class);
        when(queue.isEmpty()).thenReturn(false);
        Main.setMessageQueue(queue);
        String[] testSites = { "https://www.google.com/finance", "https://lipsum.com" };

        Main.main(testSites);
    }
}