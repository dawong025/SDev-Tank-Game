package edu.csc413.tankgame.model;

import edu.csc413.tankgame.Constants;

public class Shell extends Entity{
    public Shell (double x, double y, double angle){
        super(generateId(), x, y, angle);
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
}
