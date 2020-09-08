package se.espressoshock.exercises.task1;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

public class SenderGUI {
    private final JFrame cFrame;
    private final JLabel titleLabel;
    private static JLabel uuidLabel;
    private static JLabel statusLabel;
    private final JButton generateUUIDButton;
    private final JButton writeButton;

    private JPanel tPanel;
    public static JPanel targetPanel;
    private JPanel actionPanel;
    private static JPanel statusPanel;


    public SenderGUI() {
        this.cFrame = new JFrame("Sender");
        this.cFrame.setSize(350, 160);
        this.cFrame.setLayout(new GridLayout(4,0));

        this.tPanel = new JPanel();
        targetPanel = new JPanel();
        this.actionPanel = new JPanel();
        statusPanel = new JPanel();

        this.tPanel.setLayout(new BorderLayout());
        targetPanel.setLayout(new BorderLayout());
        this.actionPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        statusPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        this.tPanel.setBorder(new EmptyBorder(6,0,0,0));
        //this.statusPanel.setBorder(new EmptyBorder(6,0,0,0));
        targetPanel.setBackground(Color.BLUE);
        statusPanel.setBackground(Color.DARK_GRAY);



        this.titleLabel = new JLabel("Sender generated random UUID", SwingConstants.CENTER);
        this.titleLabel.setForeground(Color.LIGHT_GRAY);
        uuidLabel = new JLabel(this.generateUUID(), SwingConstants.CENTER);
        uuidLabel.setForeground(Color.WHITE);
        statusLabel = new JLabel("Waiting to write...");
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setFont(new Font("Sans-serif", Font.BOLD, 10));
        this.generateUUIDButton = new JButton("Generate UUID");
        this.writeButton = new JButton("Write to file");



        this.tPanel.add(titleLabel);
        targetPanel.add(uuidLabel, BorderLayout.CENTER);
        this.actionPanel.add(generateUUIDButton, BorderLayout.CENTER);
        this.actionPanel.add(writeButton);
        statusPanel.add(statusLabel);

        this.cFrame.add(statusPanel);
        this.cFrame.add(tPanel);
        this.cFrame.add(targetPanel);
        this.cFrame.add(actionPanel);
        this.cFrame.setVisible(true);
        this.cFrame.setResizable(false);

        this.setupListeners();


    }
    private static String generateUUID(){
        return UUID.randomUUID().toString();
    }

    public void setupListeners(){
        ActionListener generateUUIDButtonAL = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("generateUUIDButton-clicked");
                System.out.println(SenderGUI.generateUUID());
                SenderGUI.uuidLabel.setText(SenderGUI.generateUUID());
                SenderGUI.statusPanel.setBackground(Color.GREEN);
            }
        };
        ActionListener writeButtonAL = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("writeButton-clicked");
                if(CFileManager.write(SenderGUI.targetPanel)){
                    statusLabel.setText("UUID Panel written to file correctly!");
                    SenderGUI.statusPanel.setBackground(Color.GREEN);
                } else{
                    statusLabel.setText("An Error occurred while writing to file!");
                    SenderGUI.statusPanel.setBackground(Color.RED);
                }

            }
        };
        this.generateUUIDButton.addActionListener(generateUUIDButtonAL);
        this.writeButton.addActionListener(writeButtonAL);
    }

    public static void main(String[] args) {
        SenderGUI sender = new SenderGUI();
    }
}
