package se.espressoshock.preparation.task4;


import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class MulticastChatClient {
    private Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String username;


    public void setup(){
        String endpointIP;
        int endpointPort;
        Scanner scanner = new Scanner(System.in);

        System.out.println("DA326A - Lab1 | Multicast echo server");
        System.out.println("--------------------------------------");
        System.out.println("Please fill the details below to connect to the server\n");
        System.out.println("**************************************");
        System.out.print("IP Address: ");
        endpointIP = scanner.next();

        System.out.print("Porn Number: #");
        endpointPort = scanner.nextInt();

        System.out.print("Username: ");
        this.username = scanner.next();

        System.out.println("**************************************\n");


        this.connect(endpointIP, endpointPort);
    }

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

    public Message sendMessage(String msg) throws IOException, ClassNotFoundException {
        Message message = new Message(username, msg);
        out.writeObject(message);
        out.flush();
        return ((Message)in.readObject());

    }

    public void disconnect() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException{
        Scanner sc = new Scanner(System.in);
        MulticastChatClient multicastChatClient = new MulticastChatClient();
        multicastChatClient.setup();

        System.out.println("Type a message to send to the server | terminate connection with keyword '!terminate'");
        String messageContent="";
        while(!messageContent.equals("!terminate")){
            System.out.print(">");
            messageContent = sc.nextLine();
            System.out.println(multicastChatClient.sendMessage(messageContent).toString());
        }

        multicastChatClient.disconnect();
    }


}
