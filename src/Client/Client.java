package Client;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private InetAddress serverIP;
    private int serverPort;
    private IClientStrategy clientStrategy;

    /**
     * Constructor
     * @param serverIP - InetAddress - destination server IP
     * @param serverPort - int - destination server port
     * @param clientStrategy - IClientStrategy
     */
    public Client(InetAddress serverIP, int serverPort, IClientStrategy clientStrategy) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.clientStrategy = clientStrategy;
    }

    /**
     *  Activates the client using the client strategy.
     */
    public void start() {
        try {
            Socket theServer = new Socket(serverIP, serverPort);
            System.out.println(String.format("Client is connected to server (IP: %s, port: %s)", serverIP, serverPort));
            clientStrategy.clientStrategy(theServer.getInputStream(), theServer.getOutputStream());
            theServer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts communicate with the server - calls this.start
     */
    public void communicateWithServer() {
        start();
    }
}
