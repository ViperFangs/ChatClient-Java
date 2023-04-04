import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient {
    private PrintWriter serverWriter;
    private Socket serverConnection;
    public ChatClient() {
        this.setUpNetwork();
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
}
