package edu.csc413.tankgame.model;

import edu.csc413.tankgame.Constants;

public class Shell extends Entity{
    public Shell (double x, double y, double angle, int lives){
        super(generateId(), x, y, angle, lives);
    }

    private static int nextId = 0;
    private static String generateId(){
        String id = "shell-" + nextId;
        nextId++;
        return id;
    }

    @Override
    public void move(GameWorld gameWorld){
        moveForward(Constants.SHELL_MOVEMENT_SPEED);
    }

    @Override
    public void checkBounds(GameWorld gameWorld){
        if(getX() < Constants.SHELL_X_LOWER_BOUND){
            gameWorld.removeEntity(getId());
        }
        if(getX() > Constants.SHELL_X_UPPER_BOUND){
            gameWorld.removeEntity(getId());
        }
        if(getY() < Constants.SHELL_Y_LOWER_BOUND){
            gameWorld.removeEntity(getId());
        }
        if(getY() > Constants.SHELL_Y_UPPER_BOUND){
            gameWorld.removeEntity(getId());
        }
    }

    @Override
    public double getXBound(){
        return getX() + Constants.SHELL_WIDTH;
    }
    @Override
    public double getYBound(){
        return getY() + Constants.SHELL_HEIGHT;
    }
}
