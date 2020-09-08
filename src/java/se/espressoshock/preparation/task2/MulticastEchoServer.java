package se.espressoshock.preparation.task2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MulticastEchoServer {
    private ServerSocket serverSocket;

    public void init(int port) {
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

    public static void main(String[] args) {
        MulticastEchoServer multicastEchoServer = new MulticastEchoServer();
        multicastEchoServer.init(1234);
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
