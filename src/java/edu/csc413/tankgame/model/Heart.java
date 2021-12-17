package edu.csc413.tankgame.model;

import edu.csc413.tankgame.Constants;

public class Heart extends Entity{
    /*
    A pseudo-power up, differs slightly based on the getXBound and getYBound
     */
    public Heart(String id, double x, double y, double angle, int lives){
        super(id, x, y, angle, lives);
    }
    @Override
    public void move(GameWorld gameWorld){
        //No movement
    }
    @Override
    public void checkBounds(GameWorld gameWorld){
        //No movement, no checks needed
    }
    @Override
    public double getXBound(){
        return getX() + Constants.HEART_WIDTH;
    }
    @Override
    public double getYBound(){
        return getY() + Constants.HEART_HEIGHT;
    }
}
