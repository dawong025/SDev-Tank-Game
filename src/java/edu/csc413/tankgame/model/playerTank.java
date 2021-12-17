package edu.csc413.tankgame.model;

import edu.csc413.tankgame.Constants;

public class playerTank extends Tank{
    protected int playerShellCooldown;
    protected int initial_shell_cooldown = 100; //non-constant for powerup manipulation
    public playerTank(String id, double x, double y, double angle, int lives){
        super(id, x, y, angle, lives);
        playerShellCooldown = 0;
    }
    @Override
    public void move(GameWorld gameWorld){
        if(gameWorld.upKeyPressed()) {
            moveForward(Constants.TANK_MOVEMENT_SPEED);
        }
        else if(gameWorld.downKeyPressed()){
            moveBackward(Constants.TANK_MOVEMENT_SPEED);
        }
        else if(gameWorld.leftKeyPressed()){
            turnLeft(Constants.TANK_TURN_SPEED);
        }
        else if(gameWorld.rightKeyPressed()){
            turnRight(Constants.TANK_TURN_SPEED);
        }

        if(playerShellCooldown > 0) {
            playerShellCooldown--;
        }
        if(gameWorld.spaceBarPressed()) {
            fireShell(gameWorld);
        }
    }
    public void fireShell(GameWorld gameWorld){
        if (playerShellCooldown == 0) {
            Shell newShell = new Shell(getShellX(), getShellY(), getAngle(),1);
            gameWorld.addEntity(newShell);
            playerShellCooldown = initial_shell_cooldown;
        }
    }
    @Override
    public void setShellCooldown (int newCooldown){
        initial_shell_cooldown = newCooldown;
    }
}
