package se.espressoshock.exercises.task2;

import se.espressoshock.preparation.task4.Message;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class MulticastUUIDClient {
    private Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

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
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException ioe) {
            System.out.println("ERROR: I/O Exception");
            System.exit(-1);
        }
    }
    public UUIDResponse request(UUIDRequest uuidRequest) {
        try {
            out.writeObject(uuidRequest);
            UUIDResponse response = (UUIDResponse)in.readObject();
            return response;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    return null;
    }

    public void disconnect() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    public static void main(String[] args) {
        MulticastUUIDClient multicastUUIDClient = new MulticastUUIDClient();
        multicastUUIDClient.connect("192.168.0.10", 1234);

        System.out.println(multicastUUIDClient.request(new UUIDRequest(UUIDRequest.TYPE.UUID)).getUuid());

    }
}
