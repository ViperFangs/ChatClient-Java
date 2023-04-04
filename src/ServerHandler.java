import java.io.BufferedReader;
import java.io.IOException;

public class ServerHandler implements Runnable {
    private BufferedReader serverReader;
    public ServerHandler(BufferedReader serverReader) {
        this.serverReader = serverReader;
    }

    @Override
    public void run() {
        System.out.println("Waiting for a message from the server ... ");
        String message;

        try {
            while ((message = this.serverReader.readLine()) != null) {
                System.out.println("Received: " + message);
            }
        } catch (IOException error) {
            error.printStackTrace();
        }
    }
}
