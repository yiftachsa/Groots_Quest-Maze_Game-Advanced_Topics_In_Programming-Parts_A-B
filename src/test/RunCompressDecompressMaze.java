package test;

import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import java.io.*;
import java.util.Arrays;

public class RunCompressDecompressMaze {
    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {


            String mazeFileName = "savedMaze.maze";
            AMazeGenerator mazeGenerator = new MyMazeGenerator();
            Maze maze = mazeGenerator.generate(1000, 1000); //Generate new maze
            // System.out.println("\nBefore:\n" + maze.getRowLength() + ", " + maze.getColumnLength());
            // maze.print();
        /*
        maze.print();
        byte[] mazeByteArray = maze.toByteArray();
        Maze reconstructedMaze = new Maze(mazeByteArray);

        reconstructedMaze.print();
*/

            try {
                // save maze to a file
                OutputStream out = new MyCompressorOutputStream(new
                        FileOutputStream(mazeFileName));
                byte[] bytes = maze.toByteArray();
                out.write(bytes);
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte savedMazeBytes[] = new byte[0];
            try {
                //read maze from file
                InputStream in = new MyDecompressorInputStream(new
                        FileInputStream(mazeFileName));
                savedMazeBytes = new byte[maze.getRowLength()*maze.getColumnLength()+6*4];
                in.read(savedMazeBytes);
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Maze loadedMaze = new Maze(savedMazeBytes);
            boolean areMazesEquals =
                    Arrays.equals(loadedMaze.toByteArray(),maze.toByteArray());

            //  System.out.println("\nAfter:\n " + loadedMaze.getRowLength() + ", " + loadedMaze.getColumnLength());
            //  loadedMaze.print();
            System.out.println(String.format("\n Mazes equal: %s",areMazesEquals));
            //maze should be equal to loadedMaze
        }
    }
}
