package Server;

import algorithms.mazeGenerators.*;
import algorithms.search.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private int port;
    private int listeningInterval;
    private IServerStrategy serverStrategy;
    private volatile boolean stop;
    private ExecutorService executor;

    /**
     * Constructor
     * @param port - int - the server's listening port
     * @param listeningInterval - int
     * @param serverStrategy - IServerStrategy - serverStrategy
     */
    public Server(int port, int listeningInterval, IServerStrategy serverStrategy) {
        this.port = port;
        this.listeningInterval = listeningInterval;
        this.serverStrategy = serverStrategy;
    }

    /**
     * Starts a new thread which runs sunServer
     */
    public void start() {
        new Thread(this::runServer).start();
    }

    /**
     * Starting the server
     */
    private void runServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningInterval);
            int threadPoolSize = Configurations.getThreadPoolSize("ThreadPoolSize");
            executor = Executors.newFixedThreadPool(threadPoolSize);
            //LOG.info(String.format("Server starter at %s!", serverSocket));
            //LOG.info(String.format("Server's Strategy: %s", serverStrategy.getClass().getSimpleName()));
            //LOG.info("Server is waiting for clients...");
            while (!stop) {
                try {
                    Socket clientSocket = serverSocket.accept(); // blocking call
                    //LOG.info(String.format("Client excepted: %s", clientSocket));
                    executor.execute(new Thread(() -> handleClient(clientSocket)));
                } catch (SocketTimeoutException e) {
                    //LOG.debug("Socket Timeout - No clients pending!");
                }
            }
            serverSocket.close();
            executor.shutdown();
        } catch (IOException e) {
            //LOG.error("IOException", e);
        }
    }

    /**
     * Activates the server strategy using the given client socket.
     * @param clientSocket - Socket
     */
    private void handleClient(Socket clientSocket) {
        try {
            //LOG.info(String.format("Handling client with socket: %s", clientSocket.toString()));
            serverStrategy.serverStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
            clientSocket.close();
        } catch (IOException e) {
            //LOG.error("IOException", e);
        }
    }

    /**
     * Stops the server and shuts it down
     */
    public void stop() {
        //LOG.info("Stopping server..");
        stop = true;
    }
}
