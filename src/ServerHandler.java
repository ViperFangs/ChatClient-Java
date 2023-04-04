import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;

public class ServerHandler implements Runnable {
    private BufferedReader serverReader;
    private JTextArea textDisplay;
    public ServerHandler(BufferedReader serverReader, JTextArea messageDisplay) {
        this.serverReader = serverReader;
        this.textDisplay = messageDisplay;
    }

    @Override
    public void run() {
        System.out.println("Waiting for a message from the server ... ");
        String message;

        try {
            while ((message = this.serverReader.readLine()) != null) {
                System.out.println("Received: " + message);
                this.textDisplay.append(message + "\n");
            }
        } catch (IOException error) {
            error.printStackTrace();
        }
    }
}
