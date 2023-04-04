import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient {
    private PrintWriter serverWriter;
    private Socket serverConnection;
    private JTextField txtOutgoing;
    private JTextArea messageDisplay;
    public ChatClient() {
        this.setUpGUI();
        this.setUpNetwork();
        this.setUpThread();
    }

    private void setUpThread() {
        BufferedReader serverReader = null;

        try {
            serverReader = new BufferedReader(new InputStreamReader(this.serverConnection.getInputStream()));
        } catch (IOException error) {
            error.printStackTrace();
        }

        Runnable serverListener = new ServerHandler(serverReader, messageDisplay);
        Thread serverListenerThread = new Thread(serverListener);
        serverListenerThread.start();
    }

    private void setUpNetwork() {
        System.out.println("Connecting to Server ... ");

        try {
            this.serverConnection = new Socket("127.0.0.1", 8080);
        } catch (UnknownHostException error) {
            error.printStackTrace();
        } catch (IOException error) {
            error.printStackTrace();
        }

        try {
            this.serverWriter = new PrintWriter(this.serverConnection.getOutputStream());
        } catch (IOException error) {
            error.printStackTrace();
        }

        System.out.println("Connected to Server ...");
    }

    private void setUpGUI() {
        JFrame frame = new JFrame("Chat Client");
        JPanel mainPanel = new JPanel();

        this.messageDisplay = new JTextArea(15, 50);
        this.messageDisplay.setLineWrap(true);
        this.messageDisplay.setWrapStyleWord(true);
        this.messageDisplay.setEditable(false);

        JScrollPane scrollBar = new JScrollPane(this.messageDisplay);
        scrollBar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollBar.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        this.txtOutgoing = new JTextField(20);

        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new SendButtonListener());

        mainPanel.add(scrollBar);
        mainPanel.add(txtOutgoing);
        mainPanel.add(sendButton);

        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);

        frame.setSize(640, 480);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private class SendButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            serverWriter.println(txtOutgoing.getText());
            serverWriter.flush();

            txtOutgoing.setText("");
            txtOutgoing.requestFocus();
        }
    }
}
