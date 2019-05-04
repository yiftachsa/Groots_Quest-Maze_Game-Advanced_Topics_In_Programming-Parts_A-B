package Server.ServerStrategy;

import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
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

            Maze clientMaze = (Maze) fromClient.readObject(); //###SHOLUD Decompress
            //recive a byte[]
            //the next line maybe should be in MyDecompressorInputStream read()
            //byte[] compressedMaze = (byte[])fromClient.readObject();

            MyDecompressorInputStream decompressor = new MyDecompressorInputStream(fromClient);
            decompressor.read();


            if (clientMaze == null && !(clientMaze instanceof Maze)){
                return;
            }


            Solution exsitingSolution = RetriveExistingSolutionFromFile(clientMaze);

            if (exsitingSolution!=null){
                //Option 1
                byte[] solutionByteArray = exsitingSolution.toByteArray(); //like in maze we wil need to write another constructor.
                MyCompressorOutputStream compressor = new MyCompressorOutputStream(toClient);
                compressor.write(solutionByteArray);
                //option 2 -- better?
                toClient.writeObject(exsitingSolution); //TODO:Check in the lecture, maybe Solution should be serializable
            }else{
                //Generate new solution
                SearchableMaze searchableClientMaze = new SearchableMaze(clientMaze);
                ASearchingAlgorithm searchingAlgorithm = new BestFirstSearch();
                Solution solution = searchingAlgorithm.solve(searchableClientMaze);

                //TODO:this line should be at the end of MyCompressorOutputStream write method
                toClient.writeObject(solution); //TODO:Check in the lecture, maybe Solution should be serializable

                //TODO:Save the maze and the solution in file
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

    private Solution RetriveExistingSolutionFromFile(Maze clientMaze) {
        //when searching for a solution,
        //the maze will be read and if the maze matches then the solution will be read from the file and returned,
        //if not then we will continue to read files from the storage.
        //if no identical maze was found then the server will generate a new solution.
        return null;
    }
}

