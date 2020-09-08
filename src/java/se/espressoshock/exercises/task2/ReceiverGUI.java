package se.espressoshock.exercises.task2;


import se.espressoshock.exercises.task1.CFileManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Future;

public class ReceiverGUI extends JFrame{
    private final JFrame cFrame;
    private final JLabel titleLabel;
    private static JLabel uuidLabel;
    private static JLabel statusLabel;
    private final JButton readButton;

    private JPanel tPanel;
    public static JPanel targetPanel;
    private JPanel actionPanel;
    private JPanel statusPanel;

    private MulticastUUIDClient multicastUUIDClient;


    public ReceiverGUI() throws UnknownHostException {
        /////////////CLIENT SETUP///////////////
        multicastUUIDClient = new MulticastUUIDClient();


        this.cFrame = new JFrame("Receiver");
        this.cFrame.setSize(350, 160);
        this.cFrame.setLayout(new GridLayout(4,0));

        this.tPanel = new JPanel();
        targetPanel = new JPanel();
        this.actionPanel = new JPanel();
        this.statusPanel = new JPanel();


        this.tPanel.setLayout(new BorderLayout());
        targetPanel.setLayout(new BorderLayout());
        this.actionPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.statusPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        actionPanel.setBorder(new EmptyBorder(-3,0,0,0));

        targetPanel.setBackground(Color.BLUE);
        statusPanel.setBackground(Color.DARK_GRAY);
        //actionPanel.setBackground(Color.yellow);
        //tPanel.setBackground(Color.orange);




        this.titleLabel = new JLabel("(RECEIVED) Sender's generated random UUID", SwingConstants.CENTER);
        this.titleLabel.setForeground(Color.LIGHT_GRAY);
        uuidLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel = new JLabel("Connected!");
        uuidLabel.setForeground(Color.WHITE);
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setFont(new Font("Sans-serif", Font.BOLD, 10));
        this.readButton = new JButton("Read UUID Panel");



        this.tPanel.add(titleLabel);
        this.actionPanel.add(readButton, BorderLayout.CENTER);
        targetPanel.add(uuidLabel, BorderLayout.CENTER);
        statusPanel.add(statusLabel);

        this.cFrame.add(statusPanel);
        this.cFrame.add(tPanel);
        this.cFrame.add(actionPanel);
        this.cFrame.add(targetPanel);

        this.cFrame.setVisible(true);
        this.cFrame.setResizable(false);

        this.setupListeners();
        /////////////CLIENT init///////////////
        multicastUUIDClient.connect(InetAddress.getLocalHost().getHostAddress(), 1234);


    }
    public void setupListeners() {
        ActionListener readButtonAL = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UUIDResponse uuidResponse = multicastUUIDClient.request(new UUIDRequest(UUIDRequest.TYPE.UUID));

                JPanel responsePayload = (JPanel) uuidResponse.getPayload();
                System.out.println(uuidResponse.getUuid());
                if(responsePayload!=null){

                    uuidLabel.setText(uuidResponse.getUuid());
                    statusLabel.setText("UUID Panel loaded correctly!");
                    statusPanel.setBackground(Color.GREEN);
                } else{
                    statusLabel.setText("An error occurred while load UUID Panel!");
                    statusPanel.setBackground(Color.RED);
                }

            }
        };
        this.readButton.addActionListener(readButtonAL);
    }


    public static void main(String[] args) throws UnknownHostException {
        ReceiverGUI receiverGUI = new ReceiverGUI();
    }
}
