package test;
import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.search.MazeState;

import java.io.*;
import java.util.Arrays;

public class RunCompressDecompressMaze {
    public static void main(String[] args) {
        String mazeFileName = "savedMaze.maze";
        AMazeGenerator mazeGenerator = new MyMazeGenerator();
        Maze maze = mazeGenerator.generate(100, 100); //Generate new maze
        double sizeAfterCompression=0;
        double sizeAfterInitialCompression = 0;
        try {
            // save maze to a file
            OutputStream out = new MyCompressorOutputStream(new
                    FileOutputStream(mazeFileName));
            out.write(maze.toByteArray());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte savedMazeBytes[] = new byte[0];
        sizeAfterInitialCompression =  maze.toByteArray().length;
        System.out.println("Size after initial compression: "+sizeAfterInitialCompression);
        try {
            //read maze from file
            InputStream in = new MyDecompressorInputStream(new
                    FileInputStream(mazeFileName));
            savedMazeBytes = new byte[maze.toByteArray().length];
            sizeAfterCompression = in.read(savedMazeBytes);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Maze loadedMaze = new Maze(savedMazeBytes);
        boolean areMazesEquals = Arrays.equals(loadedMaze.toByteArray(),maze.toByteArray());
        System.out.println("Size after compression: "+ sizeAfterCompression);
        double byteCompressed = sizeAfterInitialCompression-sizeAfterCompression;
        double compressionRate = sizeAfterCompression/sizeAfterInitialCompression;
        System.out.println("Compression rate(sizeAfterCompression/toByteArray.length): "+ compressionRate);
        System.out.println("Saved byte rate (1-compression rate): " + (1-compressionRate));
        System.out.println("Number of bytes saved: " + byteCompressed);

        System.out.println(String.format("Mazes equal: %s",areMazesEquals));
//maze should be equal to loadedMaze
    }
}