package se.espressoshock.preparation.task1;

import java.net.*;
import java.io.*;

public class EchoClient {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void connect(String ip, int port) {
        //////////////// CONNECTION SETUP ////////////////
        try {
            clientSocket = new Socket(ip, port);
        } catch (UnknownHostException e) {
            System.err.println("ERROR: Unknown host");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("ERROR: I/O Exception");
            System.exit(1);
        }

        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException ioe) {
            System.out.println("ERROR: I/O Exception");
            System.exit(-1);
        }
    }

    public String sendMessage(String msg) throws IOException {
        out.println(msg);
        return in.readLine();
    }

    public void disconnect() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }


    public static void main(String[] args) throws IOException {
        System.out.println("Client");
        EchoClient echoClient = new EchoClient();
        echoClient.connect("127.0.0.1", 1234);

        String responce1 = echoClient.sendMessage("hello");
        String responce2 = echoClient.sendMessage("world!");
        String responce3 = echoClient.sendMessage("bye");

        System.out.println(responce1);
        System.out.println(responce2);
        System.out.println(responce3);
        echoClient.disconnect();
    }
}
