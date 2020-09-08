package se.espressoshock.exercises.task3;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ReceiverGUI extends JFrame {
    private final JFrame cFrame;
    private final JLabel titleLabel;
    private static JLabel uuidLabel;
    private static JLabel statusLabel;
    private final JButton readButton;
    private static JTextField ipAddressTF;
    private static JTextField portTF;
    private final JButton connectButton;

    private JPanel tPanel;
    public static JPanel targetPanel;
    private JPanel actionPanel;
    private JPanel statusPanel;
    private JPanel connectionPanel;


    private MulticastUUIDClient multicastUUIDClient;


    public ReceiverGUI() throws UnknownHostException {
        /////////////CLIENT SETUP///////////////
        multicastUUIDClient = new MulticastUUIDClient();


        this.cFrame = new JFrame("Receiver");
        this.cFrame.setSize(350, 250);
        this.cFrame.setLayout(new GridLayout(5, 0));

        this.tPanel = new JPanel();
        targetPanel = new JPanel();
        this.actionPanel = new JPanel();
        this.statusPanel = new JPanel();
        this.connectionPanel = new JPanel();


        JLabel ipLabel = new JLabel("Endpoint IP: ");
        JLabel portLabel = new JLabel(":");
        this.ipAddressTF = new JTextField(8);
        this.portTF = new JTextField(4);
        this.connectButton = new JButton("Connect");


        this.tPanel.setLayout(new BorderLayout());
        targetPanel.setLayout(new BorderLayout());
        this.actionPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.statusPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        actionPanel.setBorder(new EmptyBorder(-3, 0, 0, 0));
        statusPanel.setBorder(new EmptyBorder(6, 0, 0, 0));

        targetPanel.setBackground(Color.BLUE);
        statusPanel.setBackground(Color.DARK_GRAY);
        //actionPanel.setBackground(Color.yellow);
        //tPanel.setBackground(Color.orange);


        this.titleLabel = new JLabel("(RECEIVED) Sender's generated random UUID", SwingConstants.CENTER);
        this.titleLabel.setForeground(Color.LIGHT_GRAY);
        uuidLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel = new JLabel("Wait to connect...");
        uuidLabel.setForeground(Color.WHITE);
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setFont(new Font("Sans-serif", Font.BOLD, 10));
        this.readButton = new JButton("Read UUID Panel");
        readButton.setEnabled(false);


        this.connectionPanel.add(ipLabel);
        this.connectionPanel.add(ipAddressTF);
        this.connectionPanel.add(portLabel);
        this.connectionPanel.add(portTF);
        this.connectionPanel.add(connectButton);
        this.tPanel.add(titleLabel);
        this.actionPanel.add(readButton, BorderLayout.CENTER);
        targetPanel.add(uuidLabel, BorderLayout.CENTER);
        statusPanel.add(statusLabel);

        this.cFrame.add(statusPanel);
        this.cFrame.add(connectionPanel);
        this.cFrame.add(tPanel);
        this.cFrame.add(actionPanel);
        this.cFrame.add(targetPanel);

        this.cFrame.setVisible(true);
        this.cFrame.setResizable(false);

        this.setupListeners();


    }

    public void setupListeners() {
        ActionListener readButtonAL = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UUIDResponse uuidResponse = multicastUUIDClient.request(new UUIDRequest(UUIDRequest.TYPE.UUID));

                JPanel responsePayload = (JPanel) uuidResponse.getPayload();
                System.out.println(uuidResponse.getUuid());
                if (responsePayload != null) {

                    uuidLabel.setText(uuidResponse.getUuid());
                    statusLabel.setText("UUID Panel loaded correctly!");
                    statusPanel.setBackground(Color.GREEN);
                } else {
                    statusLabel.setText("An error occurred while load UUID Panel!");
                    statusPanel.setBackground(Color.RED);
                }

            }
        };
        ActionListener connectAL = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(ipAddressTF.getText().isEmpty()) && !(portTF.getText().isEmpty())) {
                    /////////////CLIENT init///////////////
                    if (multicastUUIDClient.connect(ipAddressTF.getText(), Integer.parseInt(portTF.getText())) >= 0) {
                        statusLabel.setText("Connected successfully!");
                        statusPanel.setBackground(Color.GREEN);
                        connectButton.setEnabled(false);
                        readButton.setEnabled(true);
                        ipAddressTF.setEnabled(false);
                        portTF.setEnabled(false);
                   /* cFrame.remove(connectionPanel);
                    cFrame.validate();
                    cFrame.repaint();*/
                    } else {
                        statusLabel.setText("An error occurred during connection or host unknown host!");
                        statusPanel.setBackground(Color.RED);
                    }

                } else {
                    statusLabel.setText("Please fill all the field.");
                    statusPanel.setBackground(Color.RED);
                }

            }
        };
        this.connectButton.addActionListener(connectAL);
        this.readButton.addActionListener(readButtonAL);
    }


    public static void main(String[] args) throws UnknownHostException {
        ReceiverGUI receiverGUI = new ReceiverGUI();
    }
}
