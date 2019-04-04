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

    private void selectGoalPosition(Maze maze) {
        Position tempGoalPosition = new Position((int)Math.floor(Math.random() * Math.floor(maze.getRowLength())),(int)Math.floor(Math.random() * Math.floor(maze.getColumnLength())));
        while (maze.getValue(tempGoalPosition) == 1 || tempGoalPosition.equals(maze.getStartPosition())){
            tempGoalPosition = new Position((int)Math.floor(Math.random() * Math.floor(maze.getRowLength())),(int)Math.floor(Math.random() * Math.floor(maze.getColumnLength())));
        }
        maze.setGoalPosition(tempGoalPosition);
    }

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

    private boolean needToBeAdded(Maze maze, ArrayList<Position> frontier, Position currentFrontier, Position partOfPathPosition) {
        if (partOfPathPosition != null){
            maze.setValue(partOfPathPosition, 0);
            maze.setValue(currentFrontier, 0);
            addAdjacent(maze, frontier, partOfPathPosition);
            return true;
        }
        return false;
    }


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

}


