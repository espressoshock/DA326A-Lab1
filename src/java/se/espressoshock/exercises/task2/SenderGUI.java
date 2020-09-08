package se.espressoshock.exercises.task2;

import se.espressoshock.exercises.task1.CFileManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

public class SenderGUI extends JFrame {
    private final JFrame cFrame;
    private final JLabel titleLabel;
    private static JLabel uuidLabel;
    private static JLabel statusLabel;
    private final JButton generateUUIDButton;

    private JPanel tPanel;
    public static JPanel targetPanel;
    private JPanel actionPanel;
    private static JPanel statusPanel;

    private MulticastUUIDServer multicastUUIDServer;


    public SenderGUI() {


        this.cFrame = new JFrame("Sender");
        this.cFrame.setSize(350, 160);
        this.cFrame.setLayout(new GridLayout(4,0));

        this.tPanel = new JPanel();
        targetPanel = new JPanel();
        this.actionPanel = new JPanel();
        statusPanel = new JPanel();

        /////////////SERVER SETUP///////////////
        multicastUUIDServer = new MulticastUUIDServer(targetPanel);

        this.tPanel.setLayout(new BorderLayout());
        targetPanel.setLayout(new BorderLayout());
        this.actionPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        statusPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        this.tPanel.setBorder(new EmptyBorder(6,0,0,0));
        targetPanel.setBackground(Color.BLUE);
        statusPanel.setBackground(Color.DARK_GRAY);



        this.titleLabel = new JLabel("Sender generated random UUID", SwingConstants.CENTER);
        this.titleLabel.setForeground(Color.LIGHT_GRAY);
        uuidLabel = new JLabel(MulticastUUIDServer.getcUUID(), SwingConstants.CENTER);
        uuidLabel.setForeground(Color.WHITE);
        statusLabel = new JLabel("Ready to send.");
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setFont(new Font("Sans-serif", Font.BOLD, 10));
        this.generateUUIDButton = new JButton("Renew UUID");



        this.tPanel.add(titleLabel);
        targetPanel.add(uuidLabel, BorderLayout.CENTER);
        this.actionPanel.add(generateUUIDButton, BorderLayout.CENTER);
        statusPanel.add(statusLabel);

        this.cFrame.add(statusPanel);
        this.cFrame.add(tPanel);
        this.cFrame.add(targetPanel);
        this.cFrame.add(actionPanel);
        this.cFrame.setVisible(true);
        this.cFrame.setResizable(false);

        this.setupListeners();

        /////////////SERVER INIT///////////////
        multicastUUIDServer.init();


    }
    public static String generateUUID(){
        return UUID.randomUUID().toString();
    }
    public void setupListeners(){
        ActionListener generateUUIDButtonAL = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel payload = (JPanel) MulticastUUIDServer.getPayload();
                System.out.println("generateUUIDButton-clicked");
                MulticastUUIDServer.renewUUID();
                SenderGUI.uuidLabel.setText(MulticastUUIDServer.getcUUID());
                MulticastUUIDServer.setPayload(targetPanel);
                JPanel p = (JPanel)MulticastUUIDServer.getPayload();
                //SenderGUI.statusPanel.setBackground(Color.GREEN);
                System.out.println();
            }
        };

        this.generateUUIDButton.addActionListener(generateUUIDButtonAL);
    }

    public static void main(String[] args) {
        SenderGUI sender = new SenderGUI();
    }
}
