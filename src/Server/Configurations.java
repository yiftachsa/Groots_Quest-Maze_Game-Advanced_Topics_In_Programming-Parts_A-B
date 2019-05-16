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

/**
 * Server configuration class, based on Singleton design pattern
 */
public class Configurations{
    private static enum GeneratorType{Empty, Simple ,My}; //TODO: maze generator factory
    private static enum SolverType{Best, BFS, DFS}; //TODO: maze generator factory

    private static Properties properties;

    private Configurations(){
    }

    /**
     * Initializes this.properties from an existing properties file
     */
    private static void initializeProperties(){
        try(InputStream inputStream = new FileInputStream(".\\resources\\config.properties")){
            properties = new Properties();
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the value that matches to the given key
     * @param key - String - the property
     * @return - String - the value of the property
     */
    public static String getProperties(String key) {
        if (properties != null){
            return properties.getProperty(key);
        }
        initializeProperties();
        return properties.getProperty(key);
    }

    /**
     * Getter and Factory combination.
     * Retries a property from the configuration file and returns the appropriate object that match the property.
     * @param key - String
     * @return - ASearchingAlgorithm
     */
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

    /**
     * Getter
     * Retries a property from the configuration file and returns the appropriate value that match the property.
     * @param key - String
     * @return - int
     */
    public static int getThreadPoolSize(String key) {
        if (properties == null) {
            initializeProperties();
        }
        return Integer.parseInt(properties.getProperty(key));
    }

    /**
     * Getter and Factory combination.
     * Retries a property from the configuration file and returns the appropriate object that match the property.
     * @param key - String
     * @return - AMazeGenerator
     */
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


