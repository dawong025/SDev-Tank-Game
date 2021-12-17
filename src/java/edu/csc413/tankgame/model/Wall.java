package edu.csc413.tankgame.model;

import edu.csc413.tankgame.Constants;

public class Wall extends Entity{
    public Wall (String id, double x, double y, double angle, int lives){
        super(id, x, y, 0.0, lives);
    }

    @Override
    public void move(GameWorld gameWorld){
        //No movement
    }
    @Override
    public void checkBounds(GameWorld gameWorld){
        //No movement, no checkes needed
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
