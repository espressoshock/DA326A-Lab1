package se.espressoshock.preparation.task3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class MulticastEchoServerRemote {
    private ServerSocket serverSocket;
    private int port;

    public MulticastEchoServerRemote(int port) {
        this.port = port;
    }

    public MulticastEchoServerRemote() {
        this.port = 1234;
    }


    public void setup() throws UnknownHostException {
        System.out.println("DA326A - Lab1 | Multicast echo server");
        System.out.println("--------------------------------------");
        System.out.println("Please utilize the details below to connect to the server\n");
        System.out.println("**************************************");
        System.out.println("IP Address: "+ InetAddress.getLocalHost());
        System.out.println("Porn Number: #"+this.port);
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
        while (true){
            try {
                System.out.println("->Client connection requested");
                new MultiCastEchoClientHandler(serverSocket.accept()).start();
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
        MulticastEchoServerRemote multicastEchoServer = new MulticastEchoServerRemote();
        //multicastEchoServer.init();
        multicastEchoServer.setup();
    }

    private static class MultiCastEchoClientHandler extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;

        public MultiCastEchoClientHandler(Socket clientSocket){
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            super.run();
            //////////////// PRINTWRITER/BUFFEREDREADER | MESSAGE LISTENING ////////////////
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }


            try {
                String requestMessage;
                boolean exitFlag = false;
                while ((requestMessage = in.readLine()) != null && !exitFlag) {
                    if (requestMessage.equals("bye")) {
                        System.out.println("-> Client disconnection requested");
                        exitFlag = true;
                    }
                    out.println("echo: " + requestMessage); //-> send input message
                }
            } catch (IOException ioe) {
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
