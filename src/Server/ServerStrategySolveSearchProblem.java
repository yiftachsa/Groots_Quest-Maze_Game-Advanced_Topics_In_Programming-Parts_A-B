package Server;

import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import algorithms.mazeGenerators.Maze;
import algorithms.search.ASearchingAlgorithm;
import algorithms.search.BestFirstSearch;
import algorithms.search.SearchableMaze;
import algorithms.search.Solution;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;

public class ServerStrategySolveSearchProblem implements IServerStrategy {

    private static int fileIndex = 0;
    //TODO: decide if we should hold a data structure in memory to save retrial time.
    //private static HashMap<byte[],Integer> previouslySolved= new HashMap<>();

    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            toClient.flush();

            MyDecompressorInputStream decompressor = new MyDecompressorInputStream(fromClient);
            byte[] decompressedMaze = new byte[0];
            decompressor.read(decompressedMaze);

            Solution previousSolution = retrieveExistingSolutionFromFile(decompressedMaze);
            //OR
            //TODO: decide if we should hold a data structure in memory to save retrial time.
            /*
            if (previouslySolved.containsKey(decompressedMaze)){
                int previousSolutionIndex = previouslySolved.get(decompressedMaze);
            }
            */
            if (previousSolution != null) {
                toClient.writeObject(previousSolution); //if previousSolution is Solution TODO:Check in the lecture, maybe Solution should be serializable
            } else {
                //generate Maze
                Maze clientMaze = new Maze(decompressedMaze);
                if (clientMaze == null || !(clientMaze instanceof Maze)) {
                    return;
                }

                //Generate new solution
                SearchableMaze searchableClientMaze = new SearchableMaze(clientMaze);
                ASearchingAlgorithm searchingAlgorithm = new BestFirstSearch();
                Solution newSolution = searchingAlgorithm.solve(searchableClientMaze);
                toClient.writeObject(newSolution);

                //TODO:Save the maze and the solution in file
                String tempDirectoryPath = System.getProperty("java.io.tmpdir");
                String mazeFileName = tempDirectoryPath + "\\Maze" + fileIndex + ".maze"; //TODO:Path
                String solutionFileName = tempDirectoryPath + "\\Solution" + fileIndex + ".sol"; //TODO:Path
                fileIndex++;

                saveMazeToFile(decompressedMaze, mazeFileName);
                saveSolutionToFile(newSolution, solutionFileName);

                //Every solution should be in a separate file.
                //Maybe put the maze and the solution in the same file. when searching for a solution,
                //the maze will be read and if the maze matches then the solution will be read from the file and returned,
                //if not then we will continue to read files from the storage.
                //if no identical maze was found then the server will generate a new solution.
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveSolutionToFile(Solution newSolution, String solutionFileName) {
        // save solution to a file
        try {
            OutputStream fileOutputStream = new FileOutputStream(solutionFileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(newSolution);
            fileOutputStream.close();
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveMazeToFile(byte[] decompressedMaze, String mazeFileName) {
        // save maze to a file
        try {
            OutputStream fileOutputStream = new FileOutputStream(mazeFileName);
            OutputStream myCompressorOutputStream = new MyCompressorOutputStream(fileOutputStream);
            myCompressorOutputStream.write(decompressedMaze);
            fileOutputStream.close();
            myCompressorOutputStream.flush();
            myCompressorOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Solution retrieveExistingSolutionFromFile(byte[] decompressedMaze) {
        String tempDirectoryPath = System.getProperty("java.io.tmpdir");
        for (int i = 0; i < fileIndex; i++) {
            String mazeFileName = tempDirectoryPath + "\\Maze" + i + ".maze"; //TODO: Check path
            byte savedMazeBytes[] = new byte[0];
            try {
                //read maze from file
                InputStream fileInputStream = new FileInputStream(mazeFileName);
                InputStream myDecompressorInputStream = new MyDecompressorInputStream(fileInputStream);
                myDecompressorInputStream.read(savedMazeBytes);

                myDecompressorInputStream.close();
                fileInputStream.close();

                if (savedMazeBytes.equals(decompressedMaze)){//
                    String solutionFileName = tempDirectoryPath + "\\Solution" + i + ".sol"; //TODO: Check path
                    InputStream fileInputStreamSolution = new FileInputStream(solutionFileName);
                    ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStreamSolution);
                    Solution savedSolution = (Solution)objectInputStream.readObject();
                    objectInputStream.close();
                    fileInputStreamSolution.close();
                    return savedSolution;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}

