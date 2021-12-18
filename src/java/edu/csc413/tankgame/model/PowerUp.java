package edu.csc413.tankgame.model;

import edu.csc413.tankgame.Constants;

public abstract class PowerUp extends Entity{
    public PowerUp(String id, double x, double y, double angle, int lives){
        super(id, x, y, angle, lives);
    }
    @Override
    public void move(GameWorld gameWorld){
        //no movement
    }
    @Override
    public void checkBounds(GameWorld gameWorld){
        //No movement, no checkes needed
    }

}
