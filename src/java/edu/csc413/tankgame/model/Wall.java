package edu.csc413.tankgame.model;

import edu.csc413.tankgame.Constants;

public class Wall extends Entity{
    public Wall (double x, double y, double angle){
        super(generateId(), x, y, 0.0);
    }

    private static int nextId = 0;
    private static String generateId(){
        String id = "wall-" + nextId;
        nextId++;
        return id;
    }

    @Override
    public void move(GameWorld gameWorld){
        //no movement LOL
    }
    @Override
    public void checkBounds(GameWorld gameWorld){

    }
}
