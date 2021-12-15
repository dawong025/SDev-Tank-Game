package edu.csc413.tankgame.model;
import edu.csc413.tankgame.Constants;

//[TODO] CHANGE BACK TO ABSTRACT
public abstract class Tank extends Entity {
    protected static int INITIAL_SHELL_COOLDOWN = 100;
    public Tank(String id, double x, double y, double angle) {
        super(id, x, y, angle);
    }

    @Override
    public void checkBounds(GameWorld gameWorld){
        //if tank's x < minimum x, do something
    }

    // You can use getShellX() and getShellY() to determine the x and y coordinates of a Shell that
    // is fired by this tank. The Shell's angle should be the same as the Tank's angle.

    protected double getShellX() {
        return getX() + Constants.TANK_WIDTH / 2 + 45.0 * Math.cos(getAngle()) - Constants.SHELL_WIDTH / 2;
    }

    protected double getShellY() {
        return getY() + Constants.TANK_HEIGHT / 2 + 45.0 * Math.sin(getAngle()) - Constants.SHELL_HEIGHT / 2;
    }
}