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

    public Server(int port, int listeningInterval, IServerStrategy serverStrategy) {
        this.port = port;
        this.listeningInterval = listeningInterval;
        this.serverStrategy = serverStrategy;
    }

    public void start() {
        new Thread(this::runServer).start();
    }


    private void runServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningInterval);
            executor = Executors.newFixedThreadPool(Configurations.getThreadPoolSize("ThreadPoolSize"));
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

    private void handleClient(Socket clientSocket) {
        try {
            //LOG.info(String.format("Handling client with socket: %s", clientSocket.toString()));
            serverStrategy.serverStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
            clientSocket.close();
        } catch (IOException e) {
            //LOG.error("IOException", e);
        }
    }

    public void stop() {
        //LOG.info("Stopping server..");
        stop = true;
    }

    public static class Configurations{
        private static enum GeneratorType{Empty, Simple ,My}; //TODO: maze generator factory
        private static enum SolverType{Best, BFS, DFS}; //TODO: maze generator factory

        private static Properties properties;

        private Configurations(){
        }

        private static void initializeProperties(){
            try(InputStream inputStream = new FileInputStream(".\\resources\\config.properties")){
                properties = new Properties();
                properties.load(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public static String getProperties(String key) {
            if (properties != null){
                return properties.getProperty(key);
            }
            initializeProperties();
            return properties.getProperty(key);
        }

        public static ASearchingAlgorithm getSolver(String key){
            if (properties == null) {
                initializeProperties();
            }
            SolverType solverType = SolverType.valueOf(properties.getProperty(key));
            if (solverType.equals("Best")){
                return new BestFirstSearch();
            }else if (solverType.equals("BFS")){
                return new BreadthFirstSearch();
            }else {
                return new DepthFirstSearch();
            }
        }

        public static int getThreadPoolSize(String key) {
            if (properties == null) {
                initializeProperties();
            }
            return Integer.parseInt(properties.getProperty(key));
        }


        public static AMazeGenerator getGenerator(String key){
            if (properties == null) {
                initializeProperties();
            }
            GeneratorType generatorType = GeneratorType.valueOf(properties.getProperty(key));
            if (generatorType.equals("My")){
                return new MyMazeGenerator();
            }else if (generatorType.equals("Simple")){
                return new SimpleMazeGenerator();
            }else {
                return new EmptyMazeGenerator();
            }
        }

    }
}
