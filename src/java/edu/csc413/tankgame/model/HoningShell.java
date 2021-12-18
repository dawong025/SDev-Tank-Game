package edu.csc413.tankgame.model;

import edu.csc413.tankgame.Constants;

public class HoningShell extends PowerUp{
    public HoningShell(String id, double x, double y, double angle, int lives){
        super(id, x, y, angle, lives);
    }
    @Override
    public double getXBound(){
        return getX() + Constants.HONING_SHELL_WIDTH;
    }
    @Override
    public double getYBound(){
        return getY() + Constants.HONING_SHELL_HEIGHT;
    }
}
