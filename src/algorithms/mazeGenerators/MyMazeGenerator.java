package algorithms.mazeGenerators;

import java.util.ArrayList;

public class MyMazeGenerator extends AMazeGenerator{

    @Override
    public Maze generate(int row, int column) {
        Maze maze=super.generateEmptyMaze(row , column);
        maze = RandomizePrim(maze);
        selectGoalPosition(maze);
        return maze;
    }

    /**
     * Receives an empty maze, generate a maze using Randomize Prim algorithm and returns that maze
     * @param maze - Maze
     * @return - Maze - Randomize Prim generated maze
     */
    private Maze RandomizePrim(Maze maze)
    {
        maze= fillMaze(maze);
        if(maze == null)
        {
            return null;
        }
        /*randomly select start position*/
        maze.setStartPosition(new Position((int)Math.floor(Math.random() * Math.floor(maze.getRowLength())),(int)Math.floor(Math.random() * Math.floor(maze.getColumnLength()))));
        /*update the start position to be a part of the maze*/
        maze.setValue(maze.getStartPosition(),0);
        ArrayList<Position> frontier = new ArrayList<>();
        addAdjacent(maze , frontier, maze.getStartPosition());

        while (!frontier.isEmpty()){
            int randomFrontierIndex = (int)Math.floor(Math.random() * Math.floor(frontier.size()));
            Position currentFrontier = frontier.get(randomFrontierIndex);

            Position partOfPathPosition = isDividingRow(maze, currentFrontier);
            if (needToBeAdded(maze, frontier, currentFrontier, partOfPathPosition)){
                //do nothing
            } else {
                partOfPathPosition = isDividingColumn(maze, currentFrontier);
                needToBeAdded(maze, frontier, currentFrontier, partOfPathPosition);
            }
            frontier.remove(randomFrontierIndex);
        }
        return maze;
    }

    /**
     * Creates a path through currentFrontier in the maze. Adds the partOfPathPosition to the maze and adds it's adjacent to the frontier.
     * @param maze - Maze
     * @param frontier - ArrayList<Position>
     * @param currentFrontier - Position
     * @param partOfPathPosition - Position
     * @return - boolean
     */
    private boolean needToBeAdded(Maze maze, ArrayList<Position> frontier, Position currentFrontier, Position partOfPathPosition) {
        if (partOfPathPosition != null){
            maze.setValue(partOfPathPosition, 0);
            maze.setValue(currentFrontier, 0);
            addAdjacent(maze, frontier, partOfPathPosition);
            return true;
        }
        return false;
    }

    /**
     * Adds all the adjacent positions to the given position that need to be added to the frontier
     * @param maze - Maze
     * @param frontier - ArrayList<Position>
     * @param currentPosition - Position
     */
    private void addAdjacent(Maze maze, ArrayList<Position> frontier, Position currentPosition) {

        /*up Adjacent*/
        if(currentPosition.getRowIndex()-1 >0 && maze.getValue(currentPosition.getRowIndex()-1,currentPosition.getColumnIndex()) != 0)
        {
            Position adjacent = new Position(currentPosition.getRowIndex()-1,currentPosition.getColumnIndex());
            if(!frontier.contains(adjacent));
            {
                frontier.add(adjacent);
            }
        }
        /*down Adjacent*/
        if(currentPosition.getRowIndex()+1 < maze.getRowLength() && maze.getValue(currentPosition.getRowIndex()+1,currentPosition.getColumnIndex()) != 0)
        {
            Position adjacent = new Position(currentPosition.getRowIndex()+1,currentPosition.getColumnIndex());
            if(!frontier.contains(adjacent));
            {
                frontier.add(adjacent);
            }
        }
        /*left Adjacent*/
        if(currentPosition.getColumnIndex()-1 >0 && maze.getValue(currentPosition.getRowIndex(),currentPosition.getColumnIndex()-1) != 0)
        {
            Position adjacent = new Position(currentPosition.getRowIndex(),currentPosition.getColumnIndex()-1);
            if(!frontier.contains(adjacent));
            {
                frontier.add(adjacent);
            }
        }
        /*right Adjacent*/
        if(currentPosition.getColumnIndex()+1 < maze.getColumnLength() && maze.getValue(currentPosition.getRowIndex(),currentPosition.getColumnIndex()+1) != 0)
        {
            Position adjacent = new Position(currentPosition.getRowIndex(),currentPosition.getColumnIndex()+1);
            if(!frontier.contains(adjacent));
            {
                frontier.add(adjacent);
            }
        }
    }

    /**
     * Receives a maze and fills it with walls (replaces all it's values with 1)
     * @param maze - Maze
     * @return - Maze - maze full of walls
     */
    private Maze fillMaze(Maze maze) {
        if(maze!=null) {
            for (int i = 0; i < maze.getRowLength(); i++) {
                for (int j = 0; j < maze.getColumnLength(); j++) {
                    maze.setValue(i, j, 1);
                }
            }
            return maze;
        }
        return null;
    }

    /**
     * Returns the position that positionToCheck is dividing between it an the rest of the discovered maze.
     * Checks only horizontally
     * @param maze - Maze
     * @param positionToCheck - Position - position from the frontier
     * @return - Position
     */
    private Position isDividingRow(Maze maze, Position positionToCheck) {
        if (positionToCheck.getRowIndex() <= 0 || positionToCheck.getRowIndex() >= maze.getRowLength() - 1) {
            return null;
        }
        int upValue = maze.getValue(positionToCheck.getRowIndex()-1,positionToCheck.getColumnIndex());
        int downValue = maze.getValue(positionToCheck.getRowIndex()+1,positionToCheck.getColumnIndex());
        if (upValue +downValue == 1){
            if (upValue == 1){
                return new Position(positionToCheck.getRowIndex()-1,positionToCheck.getColumnIndex());
            } else {
                return new Position(positionToCheck.getRowIndex()+1,positionToCheck.getColumnIndex());
            }
        }
        return null;
    }

    /**
     * Returns the position that positionToCheck is dividing between it an the rest of the discovered maze.
     * Checks only vertically
     * @param maze - Maze
     * @param positionToCheck - Position - position from the frontier
     * @return - Position
     */
    private Position isDividingColumn(Maze maze, Position positionToCheck) {
        if (positionToCheck.getColumnIndex() <= 0 || positionToCheck.getColumnIndex() >= maze.getColumnLength() - 1) {
            return null;
        }
        int leftValue = maze.getValue(positionToCheck.getRowIndex(),positionToCheck.getColumnIndex()-1);
        int rightValue = maze.getValue(positionToCheck.getRowIndex(),positionToCheck.getColumnIndex()+1);
        if (leftValue +rightValue == 1){
            if (leftValue == 1){
                return new Position(positionToCheck.getRowIndex(),positionToCheck.getColumnIndex()-1);
            } else {
                return new Position(positionToCheck.getRowIndex(),positionToCheck.getColumnIndex()+1);
            }
        }
        return null;
    }

    /**
     * Selects a random valid goal position for the received maze
     * @param maze - Maze - RandomizePrim maze without a final goal position
     */
    private void selectGoalPosition(Maze maze) {
        //###TODO: need to decide what to do in case of a 1-by-1 maze
        if (maze != null) {
            Position tempGoalPosition = new Position((int) Math.floor(Math.random() * Math.floor(maze.getRowLength())), (int) Math.floor(Math.random() * Math.floor(maze.getColumnLength())));
            while (maze.getValue(tempGoalPosition) == 1 || tempGoalPosition.equals(maze.getStartPosition())) {
                tempGoalPosition = new Position((int) Math.floor(Math.random() * Math.floor(maze.getRowLength())), (int) Math.floor(Math.random() * Math.floor(maze.getColumnLength())));
            }
            maze.setGoalPosition(tempGoalPosition);
        }
    }
}


