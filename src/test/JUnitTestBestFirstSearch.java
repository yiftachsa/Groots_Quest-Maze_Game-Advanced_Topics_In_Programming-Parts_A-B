package test;

import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.search.BestFirstSearch;
import algorithms.search.BreadthFirstSearch;
import algorithms.search.SearchableMaze;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JUnitTestBestFirstSearch {

    private IMazeGenerator mazeGenerator;
    private Maze maze;
    private SearchableMaze searchableMaze;
    BestFirstSearch best;

    @BeforeEach
    void setUp() {
        this.mazeGenerator = new MyMazeGenerator();
        this.maze = mazeGenerator.generate(1000, 1000);
        this.searchableMaze = new SearchableMaze(maze);
        this.best = new BestFirstSearch();
    }

    @AfterEach
    void tearDown() {
        this.mazeGenerator = null;
        this.maze = null;
        this.searchableMaze = null;
        this.best = null;
    }

    @Test
    void testTimeConstraint() {
        long startTime;
        long endTime;
        startTime = System.currentTimeMillis();
        best.solve(searchableMaze);
        endTime=System.currentTimeMillis();
        if (endTime-startTime > 60000){
            System.out.println("Test Failed: \"testTimeConstraint\", Running Time: " + (endTime -startTime));
        } else {
            System.out.println("Test Succeeded: \"testTimeConstraint\", Running Time: " + (endTime -startTime));
        }
    }

    @Test
    void testAlgorithmName() {
        if (!best.getName().equals("BestFirstSearch")){
            System.out.println("Test Failed: \"testAlgorithmName\", Name: " + best.getName());
        } else {
            System.out.println("Test Succeeded: \"testAlgorithmName\", Name: " + best.getName());
        }
    }

    @org.junit.jupiter.api.Test
    void initializeDataStructures() {
    }
}