package se.espressoshock.preparation.task4;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class MulticastChatServer {
    private ServerSocket serverSocket;
    private int port;

    public void setup() throws UnknownHostException {
        Scanner sc = new Scanner(System.in);

        System.out.println("DA326A - Lab1 | Multicast echo server");
        System.out.println("--------------------------------------");
        System.out.print("Please type the port: #");
        this.port = sc.nextInt();
        System.out.println("--------------------------------------");
        System.out.println("Please utilize the details below to connect to the server\n");
        System.out.println("**************************************");
        System.out.println("IP Address: " + InetAddress.getLocalHost());
        System.out.println("Porn Number: #" + this.port);
        System.out.println("**************************************\n");
        System.out.println(">Waiting for clients...");

        this.init();
    }

    public void init() {
        //////////////// SERVERSOCKET ////////////////
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException ioe) {
            System.out.println("ERROR: couldn't listen on port: " + port);
            System.exit(-1);
        }

        //////////////// CLIENTSOCKET ////////////////
        while (true) {
            try {
                //System.out.println("->Client connection requested");
                new MultiCastChatClientHandler(serverSocket.accept()).start();
            } catch (IOException ioe) {
                System.out.println("ERROR: I/O Exception");
                System.exit(-1);
            }
        }
    }

    public void stop() {
        try {
            serverSocket.close();
        } catch (IOException ioe) {
            System.out.println("ERROR: I/O Exception");
            System.exit(-1);
        }
    }

    public static void main(String[] args) throws IOException {
        MulticastChatServer multicastChatServer = new MulticastChatServer();
        multicastChatServer.setup();
    }

    private static class MultiCastChatClientHandler extends Thread {
        private Socket clientSocket;
        private ObjectOutputStream out;
        private ObjectInputStream in;

        public MultiCastChatClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            super.run();
            //////////////// PRINTWRITER/BUFFEREDREADER | MESSAGE LISTENING ////////////////
            try {

                out = new ObjectOutputStream(clientSocket.getOutputStream());
                in = new ObjectInputStream(clientSocket.getInputStream());
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }


            try {
                Message requestMessage;
                boolean exitFlag = false;
                while ((requestMessage = (Message) in.readObject()) != null && !exitFlag) {
                    if (requestMessage.getText().equals("!terminate")) {
                        System.out.println("->" + requestMessage.getUsername() + " (Client) disconnection requested");
                        exitFlag = true;
                        out.writeObject(new Message("SERVER", "Client request termination"));
                        out.flush();
                    } else {
                        out.writeObject(new Message("SERVER", "\u001B[32m(ECHO) " + requestMessage.getText() + "\u001B[0m"));
                        out.flush();
                    }

                }
            } catch (IOException | ClassNotFoundException ioe) {
                System.out.println("Failed to read client request");
                System.exit(-1); //-> exit
            }



            /////// CONNECTION TEARDOWN ///////
            try {
                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException ioe) {
                System.out.println("ERROR: I/O Exception");
                System.exit(-1);
            }
        }
    }

}
