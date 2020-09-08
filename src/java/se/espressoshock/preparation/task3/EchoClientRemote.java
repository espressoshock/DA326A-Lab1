package se.espressoshock.preparation.task3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class EchoClientRemote {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

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
        Scanner sc = new Scanner(System.in);
        EchoClientRemote echoClient = new EchoClientRemote();
        echoClient.setup();

        System.out.println("Type a message to send to the server | terminate connection with keyword 'bye'");
        String message="";
        while(!message.equals("bye")){
            System.out.print(">");
            message = sc.next();
            String response = echoClient.sendMessage(message);
            System.out.println(response);
        }

        /*String responce1 = echoClient.sendMessage("hello");
        String responce2 = echoClient.sendMessage("world!");
        String responce3 = echoClient.sendMessage("bye");*/

        echoClient.disconnect();
    }
}
