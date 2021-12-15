package edu.csc413.tankgame.model;

import edu.csc413.tankgame.Constants;

public class aiTank extends Tank{
    private int shellCooldown;
    public aiTank (String id, double x, double y, double angle){
        super(id, x, y, angle);
        shellCooldown = INITIAL_SHELL_COOLDOWN;
    }
    @Override
    public void move(GameWorld gameWorld){
        moveForward(Constants.TANK_MOVEMENT_SPEED);
        if(shellCooldown > 0) {
            shellCooldown--;
        }
        fireShell(gameWorld);
    }
    protected void fireShell(GameWorld gameWorld){
        if(shellCooldown == 0) {
            Shell newShell = new Shell(getShellX(), getShellY(), getAngle());
            gameWorld.addEntity(newShell);
            shellCooldown = INITIAL_SHELL_COOLDOWN;
        }
    }
}