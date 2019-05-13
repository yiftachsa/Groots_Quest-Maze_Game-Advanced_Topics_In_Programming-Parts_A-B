package Server;

import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.EmptyMazeGenerator;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.SimpleMazeGenerator;
import algorithms.search.ASearchingAlgorithm;
import algorithms.search.BestFirstSearch;
import algorithms.search.BreadthFirstSearch;
import algorithms.search.DepthFirstSearch;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configurations{
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
        if (solverType.equals(SolverType.Best)){
            return new BestFirstSearch();
        }else if (solverType.equals(SolverType.BFS)){
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
        if (generatorType.equals(GeneratorType.My)){
            return new MyMazeGenerator();
        }else if (generatorType.equals(GeneratorType.Simple)){
            return new SimpleMazeGenerator();
        }else {
            return new EmptyMazeGenerator();
        }
    }

}


