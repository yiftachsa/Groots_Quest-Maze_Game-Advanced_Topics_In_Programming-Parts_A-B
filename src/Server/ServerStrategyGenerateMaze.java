package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;

import java.io.*;

public class ServerStrategyGenerateMaze implements IServerStrategy {

    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            toClient.flush();

            int[] mazeSpecs = (int[])fromClient.readObject();
            Maze maze;
            if (mazeSpecs != null && mazeSpecs.length ==2){
                AMazeGenerator mazeGenerator = new MyMazeGenerator();
                maze = mazeGenerator.generate(mazeSpecs[0], mazeSpecs[1]);
                byte[] mazeByteArray = maze.toByteArray();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                OutputStream compressor = new MyCompressorOutputStream(byteArrayOutputStream);
                compressor.write(mazeByteArray);
                toClient.writeObject(byteArrayOutputStream.toByteArray());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}