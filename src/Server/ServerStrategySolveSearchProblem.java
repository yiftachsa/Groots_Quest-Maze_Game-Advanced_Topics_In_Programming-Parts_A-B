package Server;

import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import algorithms.mazeGenerators.Maze;
import algorithms.search.ASearchingAlgorithm;
import algorithms.search.BestFirstSearch;
import algorithms.search.SearchableMaze;
import algorithms.search.Solution;

import java.io.*;

public class ServerStrategySolveSearchProblem implements IServerStrategy {
    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            toClient.flush();

            //Maze clientMaze = (Maze) fromClient.readObject(); //###SHOLUD Decompress
            //recive a byte[]
            //the next line maybe should be in MyDecompressorInputStream read()
            //byte[] compressedMaze = (byte[])fromClient.readObject();


            MyDecompressorInputStream decompressor = new MyDecompressorInputStream(fromClient);
            byte[] decompressedMaze = new byte[0];
            decompressor.read(decompressedMaze);

           Solution previousSolution = RetriveExistingSolutionFromFile(decompressedMaze);

            if (previousSolution!=null){
                toClient.writeObject(previousSolution); //if previousSolution is Solution TODO:Check in the lecture, maybe Solution should be serializable
            }else{
                //generate Maze
                Maze clientMaze = new Maze(decompressedMaze);
                if (clientMaze == null || !(clientMaze instanceof Maze)){
                    return;
                }

                //Generate new solution
                SearchableMaze searchableClientMaze = new SearchableMaze(clientMaze);
                ASearchingAlgorithm searchingAlgorithm = new BestFirstSearch();
                Solution newSolution = searchingAlgorithm.solve(searchableClientMaze);
                toClient.writeObject(newSolution);

                //TODO:Save the maze and the solution in file
                String mazeFileName = "savedMaze.maze"; //

                try {
                    // save maze to a file
                    OutputStream out = new MyCompressorOutputStream(new FileOutputStream(mazeFileName));
                    out.write(decompressedMaze);
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Every solution should be in a separate file.
                //Maybe put the maze and the solution in the same file. when searching for a solution,
                //the maze will be read and if the maze matches then the solution will be read from the file and returned,
                //if not then we will continue to read files from the storage.
                //if no identical maze was found then the server will generate a new solution.
            }

        } catch (
                IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Solution RetriveExistingSolutionFromFile(byte[] decompressedMaze) {
        //when searching for a solution,
        //the maze will be read and if the maze matches then the solution will be read from the file and returned,
        //if not then we will continue to read files from the storage.
        //if no identical maze was found then the server will generate a new solution.
        return null;
    }
}

