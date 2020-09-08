package se.espressoshock.exercises.task1;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReceiverGUI extends JFrame{
    private final JFrame cFrame;
    private final JLabel titleLabel;
    private static JLabel statusLabel;
    private final JButton readButton;

    private JPanel tPanel;
    public static JPanel targetPanel;
    private JPanel actionPanel;
    private JPanel statusPanel;


    public ReceiverGUI() {
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

        targetPanel.setBackground(Color.LIGHT_GRAY);
        statusPanel.setBackground(Color.DARK_GRAY);
        //actionPanel.setBackground(Color.yellow);
        //tPanel.setBackground(Color.orange);




        this.titleLabel = new JLabel("(RECEIVED) Sender's generated random UUID", SwingConstants.CENTER);
        this.titleLabel.setForeground(Color.LIGHT_GRAY);
        statusLabel = new JLabel("Waiting to read...");
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setFont(new Font("Sans-serif", Font.BOLD, 10));
        this.readButton = new JButton("Read UUID Panel");



        this.tPanel.add(titleLabel);

        //targetPanel.add(uuidLabel, BorderLayout.CENTER);

        this.actionPanel.add(readButton, BorderLayout.CENTER);
        statusPanel.add(statusLabel);

        this.cFrame.add(statusPanel);
        this.cFrame.add(tPanel);
        this.cFrame.add(actionPanel);
        this.cFrame.add(targetPanel);

        this.cFrame.setVisible(true);
        this.cFrame.setResizable(false);

        this.setupListeners();
    }
    public void setupListeners(){
        ActionListener readButtonAL = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel loadedPanel = (JPanel) CFileManager.read();
                if(loadedPanel!=null){
                    cFrame.remove(targetPanel);
                    cFrame.add(loadedPanel);
                    cFrame.validate();
                    targetPanel = loadedPanel;

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


    public static void main(String[] args) {
        ReceiverGUI receiverGUI = new ReceiverGUI();
    }
}
