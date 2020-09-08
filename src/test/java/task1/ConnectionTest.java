package task1;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import se.espressoshock.preparation.task1.EchoClient;
import se.espressoshock.preparation.task1.EchoServer;

import static org.junit.Assert.*;

import java.io.IOException;


@RunWith(JUnit4.class)
public class ConnectionTest {
    private EchoClient echoClient;
    private EchoServer echoServer;
    private final int listeningPort = 1234;

    //*******************************
    // REMEMBER TO RUN SERVER FIRST
    //*******************************

    @Before
    public void setup() throws IOException {
        echoClient = new EchoClient();
        echoClient.connect("127.0.0.1", listeningPort);
    }

    @After
    public void tearDown() throws IOException {
        echoClient.disconnect();
    }

    @Test
    public void UnicastConnectionTest() throws IOException {
        String responce1 = echoClient.sendMessage("hello");
        String responce2 = echoClient.sendMessage("world!");
        String responce3 = echoClient.sendMessage("bye");

        assertEquals("echo: hello", responce1);
        assertEquals("echo: world!", responce2);
        assertEquals("echo: bye", responce3);
    }
}
