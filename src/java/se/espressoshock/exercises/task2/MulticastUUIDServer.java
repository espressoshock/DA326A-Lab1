package se.espressoshock.exercises.task2;


import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MulticastUUIDServer {
    private ServerSocket serverSocket;
    private static final int port = 1234;
    private static String cUUID; //-> 1 uuid x server
    private static Object payload;

    public MulticastUUIDServer(String cUUID) {
        cUUID = cUUID;
    }
    public MulticastUUIDServer(Object p) {
        payload = p;
        this.renewUUID();
    }


    public static String getcUUID() {
        return MulticastUUIDServer.cUUID;
    }

    public static Object getPayload() {
        return MulticastUUIDServer.payload;
    }

    public static void setPayload(Object p) {
        payload = p;
    }

    public static void renew(Object p){
        renewUUID();
        payload = p;
    }
    public static String renewUUID(){
        cUUID = UUIDGenerator.generateRandom();
        return cUUID;
    }

    public void init(){
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
                new MultiCastUIIDClientHandler(serverSocket.accept()).start();
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

    private static class MultiCastUIIDClientHandler extends Thread {
        private Socket clientSocket;
        private ObjectOutputStream out;
        private ObjectInputStream in;

        public MultiCastUIIDClientHandler(Socket clientSocket){
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
               UUIDRequest clientRequest = null;
               JPanel a = (JPanel)payload;
                boolean exitFlag = false;
                while ((clientRequest = (UUIDRequest) in.readObject()) != null && !exitFlag) {
                    if (clientRequest.getType() == UUIDRequest.TYPE.TERMINATION) {
                        System.out.println("-> (Client) disconnection requested");
                        exitFlag = true;
                        out.flush();
                    } else {
                        out.writeObject(new UUIDResponse(cUUID, payload));
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
