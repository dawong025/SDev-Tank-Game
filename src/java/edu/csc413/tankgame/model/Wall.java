package edu.csc413.tankgame.model;

import edu.csc413.tankgame.Constants;

public class Wall extends Entity{
    public Wall (String id, double x, double y, double angle, int lives){
        super(id, x, y, 0.0, lives);
    }
    /*
    private static int nextId = 0;
    private static String generateId(){
        String id = "wall-" + nextId;
        nextId++;
        return id;
    }
    */
    @Override
    public void move(GameWorld gameWorld){
        //no movement LOL
    }
    @Override
    public void checkBounds(GameWorld gameWorld){
        //no checks needed since no movement
    }

    @Override
    public double getXBound(){
        return getX() + Constants.WALL_WIDTH;
    }
    @Override
    public double getYBound(){
        return getY() + Constants.WALL_HEIGHT;
    }
}
