package test;

import Server.*;
import Client.*;
import IO.MyDecompressorInputStream;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.search.AState;
import algorithms.search.Solution;
import sun.awt.Mutex;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class RunCommunicateWithServers {
    public static void main(String[] args) {
        //Initializing servers
        Server mazeGeneratingServer = new Server(5400, 1000, new ServerStrategyGenerateMaze());
        Server solveSearchProblemServer = new Server(5401, 1000, new ServerStrategySolveSearchProblem());
        //Server stringReverserServer = new Server(5402, 1000, new ServerStrategyStringReverser());

        //Starting servers
        solveSearchProblemServer.start();
        mazeGeneratingServer.start();
        //stringReverserServer.start();


        //Threads initialization

        Thread[] mazeGeneratingThreads = new Thread[10];
        for (int i = 0; i < mazeGeneratingThreads.length; i++) {
            mazeGeneratingThreads[i] = new ThreadMazeGenerating(i);
        }
        //Threads start
        for (int i = 0; i < mazeGeneratingThreads.length; i++) {
            System.out.println("\nMazeGenerating | Thread index: "+i+" Thread Id: "+ Thread.currentThread().getId());
            mazeGeneratingThreads[i].start();
        }
        //Wait for all the threads to join
        for (int i = 0; i < mazeGeneratingThreads.length; i++) {
            try{
                mazeGeneratingThreads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

/*
        MyMazeGenerator myMazeGenerator = new MyMazeGenerator();
        Maze maze = myMazeGenerator.generate(5,5);

        Thread[] solveSearchThreads = new Thread[10];
        for (int i = 0; i < solveSearchThreads.length; i++) {
            solveSearchThreads[i] = new ThreadSolveSearchProblem(i);
        }
        //Threads start
        for (int i = 0; i < solveSearchThreads.length; i++) {
            System.out.println("\n SolveSearchProblem | Thread index: "+ i +" Thread Id: "+ Thread.currentThread().getId());
            solveSearchThreads[i].start();
        }
        //Wait for all the threads to join
        for (int i = 0; i < solveSearchThreads.length; i++) {
            try{
                solveSearchThreads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
*/

        //Communicating with servers

        //CommunicateWithServer_MazeGenerating(100,100);
        //CommunicateWithServer_SolveSearchProblem(100, 100);
        //CommunicateWithServer_StringReverser();
        //Stopping all servers
        mazeGeneratingServer.stop();
        solveSearchProblemServer.stop();
        //stringReverserServer.stop();
    }
    private static void CommunicateWithServer_MazeGenerating(int row, int column) {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        int[] mazeDimensions = new int[]{row, column};
                        int maxToByteArray = (row*column) + (6*4)+1;
                        toServer.writeObject(mazeDimensions); //send mazedimensions to server
                        toServer.flush();
                        byte[] compressedMaze = (byte[]) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
                        InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                        byte[] decompressedMaze = new byte[maxToByteArray /*CHANGE SIZE ACCORDING TO YOU MAZE SIZE*/]; //allocating byte[] for the decompressed maze -
                        is.read(decompressedMaze); //Fill decompressedMaze with bytes
                        Maze maze = new Maze(decompressedMaze);
                        //Mutex to protect the print
                        maze.print();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private static void CommunicateWithServer_SolveSearchProblem(int row,int column) {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5401, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        MyMazeGenerator mg = new MyMazeGenerator();
                        Maze maze = mg.generate(row, column);
                        maze.print();
                        toServer.writeObject(maze); //send maze to server
                        toServer.flush();
                        Solution mazeSolution = (Solution) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
                                //Print Maze Solution retrieved from the server
                        System.out.println(String.format("Solution steps: %s", mazeSolution));
                        ArrayList<AState> mazeSolutionSteps = mazeSolution.getSolutionPath();
                        for (int i = 0; i < mazeSolutionSteps.size(); i++) {
                            System.out.println(String.format("%s. %s", i, mazeSolutionSteps.get(i).toString()));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
    private static void CommunicateWithServer_SolveSameSearchProblem(Maze maze) {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5401, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        toServer.writeObject(maze); //send maze to server
                        toServer.flush();
                        Solution mazeSolution = (Solution) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
                        //Print Maze Solution retrieved from the server
                        System.out.println(String.format("Solution steps: %s", mazeSolution));
                        ArrayList<AState> mazeSolutionSteps = mazeSolution.getSolutionPath();
                        for (int i = 0; i < mazeSolutionSteps.size(); i++) {
                            System.out.println(String.format("%s. %s", i, mazeSolutionSteps.get(i).toString()));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
    private static void CommunicateWithServer_StringReverser() {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5402, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        BufferedReader fromServer = new BufferedReader(new InputStreamReader(inFromServer));
                        PrintWriter toServer = new PrintWriter(outToServer);
                        String message = "Client Message";
                        String serverResponse;
                        toServer.write(message + "\n");
                        toServer.flush();
                        serverResponse = fromServer.readLine();
                        System.out.println(String.format("Server response: %s", serverResponse));
                        toServer.flush();
                        fromServer.close();
                        toServer.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
    private static class ThreadMazeGenerating extends Thread {
        private int threadId;

        public ThreadMazeGenerating(int threadId) {
            this.threadId = threadId;
        }

        public int getThreadId() {
            return threadId;
        }

        public void run(){
            CommunicateWithServer_MazeGenerating(50,50);
        }
    }

    private static class ThreadSolveSearchProblem extends Thread {
        private int threadId;
        private Maze maze;

        public ThreadSolveSearchProblem(int threadId, Maze maze) {
            this.threadId = threadId;
            this.maze = maze;
        }

        public ThreadSolveSearchProblem(int threadId) {
            this.threadId = threadId;
            MyMazeGenerator mazeGenerator = new MyMazeGenerator();
            this.maze = mazeGenerator.generate(50,50);
        }

        public void run(){
            CommunicateWithServer_SolveSameSearchProblem(maze);
        }
    }
}

