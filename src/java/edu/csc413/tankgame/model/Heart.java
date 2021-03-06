package edu.csc413.tankgame.model;

import edu.csc413.tankgame.Constants;

public class Heart extends PowerUp{
    public Heart(String id, double x, double y, double angle, int lives){
        super(id, x, y, angle, lives);
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
