package Server.ServerStrategy;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;

import java.io.*;
import java.util.ArrayList;

public class ServerStrategyGenerateMaze implements IServerStrategy{
    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            toClient.flush();

            int[] mazeSpecs = (int[])fromClient.readObject();
            Maze maze;
            if (mazeSpecs != null && mazeSpecs.length ==2){
                MyMazeGenerator mazeGenerator = new MyMazeGenerator();
                maze = mazeGenerator.generate(mazeSpecs[0], mazeSpecs[1]);
            }
            MyCompressorOutputStream compressor = new MyCompressorOutputStream(toClient);

            //TODO:this line should be at the end of MyCompressorOutputStream
            //toClient.writeObject(al);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
