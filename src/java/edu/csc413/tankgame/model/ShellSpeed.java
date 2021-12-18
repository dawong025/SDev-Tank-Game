package edu.csc413.tankgame.model;

import edu.csc413.tankgame.Constants;

public class ShellSpeed extends PowerUp{
    public ShellSpeed(String id, double x, double y, double angle, int lives){
        super(id, x, y, angle, lives);
    }
    @Override
    public double getXBound(){
        return getX() + Constants.POWERUP_WIDTH;
    }
    @Override
    public double getYBound(){
        return getY() + Constants.POWERUP_HEIGHT;
    }
}
