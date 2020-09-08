package se.espressoshock.preparation.task1;

import java.net.*;
import java.io.*;

public class EchoServer {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    /**
     * ESTABLISH METHOD ALTERNATIVE
     *
     * @param port | server port
     */
    public void init(int port) throws IOException{
        //////////////// SERVERSOCKET ////////////////
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException ioe) {
            System.out.println("ERROR: couldn't listen on port: " + port);
            System.exit(-1);
        }
        //////////////// CLIENTSOCKET ////////////////
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException ioe) {
            System.out.println("ERROR: failed to accept on port: " + port);
            System.exit(-1);
        }

        //////////////// PRINTWRITER/BUFFEREDREADER | MESSAGE LISTENING ////////////////
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }


        try{
            String requestMessage;
            boolean exitFlag = false;
            while((requestMessage = in.readLine()) != null && !exitFlag){
                if(requestMessage.equals("bye")){
                    System.out.println("-> Client disconnection requested");
                    exitFlag = true;
                }
                out.println("echo: "+requestMessage); //-> send input message
            }
        } catch (IOException ioe){
            System.out.println("Failed to read client request");
            System.exit(-1); //-> exit
        }



    }

    public void stop() throws IOException{
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }

    public static void main(String[] args) throws IOException{
       EchoServer echoServer = new EchoServer();
       echoServer.init(1234);
    }
}
