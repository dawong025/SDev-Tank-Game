package edu.csc413.tankgame.model;

/**
 * A general concept for an entity in the Tank Game. This includes everything that can move or be interacted with, such
 * as tanks, shells, walls, power ups, etc.
 */
public abstract class Entity {
    private String id;
    private double x;
    private double y;
    private double angle;
    private int lives;

    public Entity(String id, double x, double y, double angle, int lives) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.lives = lives;
    }

    public String getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getAngle() {
        return angle;
    }
    public int getLives(){
        return lives;
    }
    public void setX(double newX){
        x = newX;
    }
    public void setY(double newY){
        y = newY;
    }
    public void setAngle(double newAngle){
        angle = newAngle;
    }
    public void setLives(int newLives){
        lives = newLives;
    }
    protected void moveForward(double movementSpeed) {
        x += movementSpeed * Math.cos(angle);
        y += movementSpeed * Math.sin(angle);
    }

    protected void moveBackward(double movementSpeed) {
        x -= movementSpeed * Math.cos(angle);
        y -= movementSpeed * Math.sin(angle);
    }

    protected void turnLeft(double turnSpeed) {
        angle -= turnSpeed;
    }

    protected void turnRight(double turnSpeed) {
        angle += turnSpeed;
    }

    /** All entities have a X and Y bounds, even if the details of their bounds are based on the specific type of Entity**/
    public abstract double getXBound();
    public abstract double getYBound();

    /** All entities can move, even if the details of their move logic may vary based on the specific type of Entity. */
    public abstract void move(GameWorld gameWorld);
    
    public abstract void checkBounds(GameWorld gameWorld);
}
