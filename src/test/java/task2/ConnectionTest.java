package task2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import se.espressoshock.preparation.task1.EchoClient;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class ConnectionTest {
    private final int listeningPort = 1234;

    //*******************************
    // REMEMBER TO RUN SERVER FIRST
    //*******************************


    @Test
    public void MultiCastConnectionTest_Client1() throws IOException {
        EchoClient echoClient1 = new EchoClient();
        echoClient1.connect("127.0.0.1", listeningPort);

        String responce1 = echoClient1.sendMessage("this");
        String responce2 = echoClient1.sendMessage("is");
        String responce3 = echoClient1.sendMessage("client1!");
        String responce4 = echoClient1.sendMessage("bye");

        assertEquals("echo: this", responce1);
        assertEquals("echo: is", responce2);
        assertEquals("echo: client1!", responce3);
        assertEquals("echo: bye", responce4);
    }

    @Test
    public void MultiCastConnectionTest_Client2() throws IOException {
        EchoClient echoClient2 = new EchoClient();
        echoClient2.connect("127.0.0.1", listeningPort);

        String responce1 = echoClient2.sendMessage("this");
        String responce2 = echoClient2.sendMessage("is");
        String responce3 = echoClient2.sendMessage("client2!");
        String responce4 = echoClient2.sendMessage("bye");

        assertEquals("echo: this", responce1);
        assertEquals("echo: is", responce2);
        assertEquals("echo: client2!", responce3);
        assertEquals("echo: bye", responce4);
    }

}
