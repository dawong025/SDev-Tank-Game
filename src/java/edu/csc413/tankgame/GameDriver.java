package edu.csc413.tankgame;

import edu.csc413.tankgame.model.*;
import edu.csc413.tankgame.view.*;

import java.util.*;
import java.awt.event.ActionEvent;

public class GameDriver {
    private final MainView mainView;
    private final RunGameView runGameView;
    private final GameWorld gameWorld;

    private boolean wonGame = false;
    private boolean lostGame = false;

    public GameDriver() {
        mainView = new MainView(this::startMenuActionPerformed);
        runGameView = mainView.getRunGameView();
        gameWorld = new GameWorld();
    }

    public void start() {
        mainView.setScreen(MainView.Screen.START_GAME_SCREEN);
    }

    private void startMenuActionPerformed(ActionEvent actionEvent) {
        switch (actionEvent.getActionCommand()) {
            case StartMenuView.START_BUTTON_ACTION_COMMAND -> runGame();
            case StartMenuView.EXIT_BUTTON_ACTION_COMMAND -> mainView.closeGame();
            default -> throw new RuntimeException("Unexpected action command: " + actionEvent.getActionCommand());
        }
    }

    private void runGame() {
        mainView.setScreen(MainView.Screen.RUN_GAME_SCREEN);
        Runnable gameRunner = () -> {
            setUpGame();
            while (updateGame()) {
                runGameView.repaint();
                try {
                    Thread.sleep(10L);
                } catch (InterruptedException exception) {
                    throw new RuntimeException(exception);
                }
                if(gameWorld.escapeKeyPressed()){ //break out of loop if escape is pressed
                    break;
                }
                else if(lostGame){
                    System.out.println("The player has been eliminated.");
                    break;
                }
                else if(wonGame){
                    System.out.println("The player has won");
                }
            }
            mainView.setScreen(MainView.Screen.END_MENU_SCREEN);
            resetGame();
        };
        new Thread(gameRunner).start();
    }

    /**
     * setUpGame is called once at the beginning when the game is started. Entities that are present from the start
     * should be initialized here, with their corresponding sprites added to the RunGameView.
     */
    private void setUpGame() {
        // TODO: Implement.
        Tank playerTank = new playerTank(
                Constants.PLAYER_TANK_ID,
                Constants.PLAYER_TANK_INITIAL_X,
                Constants.PLAYER_TANK_INITIAL_Y,
                Constants.PLAYER_TANK_INITIAL_ANGLE,
                3
        );
        Tank aiTank = new aiTank (
                Constants.AI_TANK_1_ID,
                Constants.AI_TANK_1_INITIAL_X,
                Constants.AI_TANK_1_INITIAL_Y,
                Constants.AI_TANK_1_INITIAL_ANGLE,
                3
        );

        int id = 0;
        List<WallInformation> wallInfos = WallInformation.readWalls();
        for(WallInformation wallInfo: wallInfos){
            //Create a Wall Entity, add it to gameWorld
            String updatedId = "wall-"+id++;
            Wall wall = new Wall (updatedId, wallInfo.getX(), wallInfo.getY(), 0.0, 3);
            gameWorld.addEntity(wall);
            runGameView.addSprite(
                    updatedId, wallInfo.getImageFile(), wallInfo.getX(), wallInfo.getY(), 0.0);
        }

        gameWorld.addEntity(playerTank);
        gameWorld.addEntity(aiTank);
        //for all wallInformation
        gameWorld.moveEntitiesToAdd();

        runGameView.addSprite(playerTank.getId(),
                RunGameView.PLAYER_TANK_IMAGE_FILE,
                playerTank.getX(),
                playerTank.getY(),
                playerTank.getAngle());
        runGameView.addSprite(aiTank.getId(),
                RunGameView.AI_TANK_IMAGE_FILE,
                aiTank.getX(),
                aiTank.getY(),
                aiTank.getAngle());

    }

    /**
     * updateGame is repeatedly called in the gameplay loop. The code in this method should run a single frame of the
     * game. As long as it returns true, the game will continue running. If the game should stop for whatever reason
     * (e.g. the player tank being destroyed, escape being pressed), it should return false.
     */
    private boolean updateGame() {
        // TODO: Implement.
        for(Entity entity: gameWorld.getEntities()){
            entity.move(gameWorld);
            entity.checkBounds(gameWorld);
        }
        //A bunch of other things like collision detection, bounce checking, etc
        for(int i = 0; i < gameWorld.getEntities().size(); i++){
            for(int j = i + 1; j < gameWorld.getEntities().size(); j++){
                handleCollision(gameWorld.getEntities().get(i), gameWorld.getEntities().get(j));
                //System.out.println("Handing collision between"+gameWorld.getEntities().get(i).getId()+" "+gameWorld.getEntities().get(j).getId());
            }
        }

        for(Entity entity: gameWorld.getEntitiesToAdd()){
            runGameView.addSprite(
                    entity.getId(), RunGameView.SHELL_IMAGE_FILE,entity.getX(), entity.getY(), entity.getAngle());
        }
        gameWorld.moveEntitiesToAdd();

        for(Entity entity: gameWorld.getEntities()){
            runGameView.setSpriteLocationAndAngle(entity.getId(), entity.getX(), entity.getY(), entity.getAngle());
        }

        for(Entity entity: gameWorld.getEntitiesToRemove()){
            runGameView.removeSprite(entity.getId());
        }
        gameWorld.moveEntitiesToRemove();
        return true;
    }
    private boolean areEntitiesColliding(Entity entityA, Entity entityB){
        //return true if both entities are colliding, false otherwise
        boolean x1ToX2Bound = entityA.getX() < entityB.getXBound();
        boolean x1BoundToX2 = entityA.getXBound() > entityB.getX();
        boolean y1ToY2Bound = entityA.getY() < entityB.getYBound();
        boolean y1BoundToY2 = entityA.getYBound() > entityB.getY();

        //All conditions must be true in order for two entities to collide
        return x1ToX2Bound && x1BoundToX2 && y1ToY2Bound && y1BoundToY2;
    }
    private void handleCollision(Entity entityA, Entity entityB){
            if (entityA instanceof Tank && entityB instanceof Tank) {
                if(areEntitiesColliding(entityA, entityB)) {
                    //Four Possible Scenarios (In reference to Tank A movement)
                    double moveLeft = entityA.getXBound() - entityB.getX();
                    double moveRight = entityB.getXBound() - entityA.getX();
                    double moveUp = entityA.getYBound() - entityB.getY();
                    double moveDown = entityB.getYBound() - entityA.getY();

                    //Create a temp array to sort out values, then sort
                    double[] move = {moveLeft, moveRight, moveUp, moveDown};
                    double minimum = move[0];

                    for (int i = 0; i < move.length; i++) {
                        if (move[i] < minimum) {
                            minimum = move[i];
                        }
                    }
                    //minimum cases
                    if (minimum == moveLeft) {
                        entityA.setX(entityA.getX() - minimum / 2);
                        entityB.setX(entityB.getX() + minimum / 2);
                    } else if (minimum == moveRight) {
                        entityA.setX(entityA.getX() + minimum / 2);
                        entityB.setX(entityB.getX() - minimum / 2);
                    } else if (minimum == moveUp) {
                        entityA.setY(entityA.getY() - minimum / 2); //where - means going up
                        entityB.setY(entityB.getY() + minimum / 2);
                    } else if (minimum == moveDown) {
                        entityA.setY(entityA.getY() + minimum / 2); //where + means going down
                        entityB.setY(entityB.getY() - minimum / 2);
                    }
                }
            }
            else if (entityA instanceof Shell && entityB instanceof Shell) {
                if(areEntitiesColliding(entityA, entityB)) {
                    //both shells should be removed
                    entityA.setLives(0);
                    gameWorld.removeEntity(entityA.getId());
                    entityB.setLives(0);
                    gameWorld.removeEntity(entityB.getId());
                }
            }
            else if (entityA instanceof Tank && entityB instanceof Shell) {
                if(areEntitiesColliding(entityA, entityB)){
                    entityA.setLives(entityA.getLives() - 1);
                    if(entityA.getLives() == 0 && entityA.getId() == "player-tank") {
                        gameWorld.removeEntity(entityA.getId());
                        lostGame = true;
                    }
                    else if(entityA.getLives () == 0){
                        gameWorld.removeEntity(entityA.getId());
                    }

                    entityB.setLives(0);
                    gameWorld.removeEntity(entityB.getId()); //get rid of shell

                }
            }
            else if (entityA instanceof Tank && entityB instanceof Wall ||
                    entityA instanceof Wall && entityB instanceof Tank) {
                    //second case most likely to happen based on loop order
                if(areEntitiesColliding(entityA, entityB)) {
                    if (entityA instanceof Tank && entityB instanceof Wall) {
                        double moveLeft = entityA.getXBound() - entityB.getX();
                        double moveRight = entityB.getXBound() - entityA.getX();
                        double moveUp = entityA.getYBound() - entityB.getY();
                        double moveDown = entityB.getYBound() - entityA.getY();

                        //Create a temp array to sort out values, then sort
                        double[] move = {moveLeft, moveRight, moveUp, moveDown};
                        double minimum = move[0];

                        for (int i = 0; i < move.length; i++) {
                            if (move[i] < minimum) {
                                minimum = move[i];
                            }
                        }
                        //minimum cases
                        if (minimum == moveLeft) {
                            entityA.setX(entityA.getX() - minimum);
                        } else if (minimum == moveRight) {
                            entityA.setX(entityA.getX() + minimum);
                        } else if (minimum == moveUp) {
                            entityA.setY(entityA.getY() - minimum); //where - means going up
                        } else if (minimum == moveDown) {
                            entityA.setY(entityA.getY() + minimum); //where + means going down
                        }
                    }
                    else{
                        if (entityA instanceof Wall && entityB instanceof Tank) {
                            double moveLeft = entityB.getXBound() - entityA.getX();
                            double moveRight = entityA.getXBound() - entityB.getX();
                            double moveUp = entityB.getYBound() - entityA.getY();
                            double moveDown = entityA.getYBound() - entityB.getY();

                            //Create a temp array to sort out values, then sort
                            double[] move = {moveLeft, moveRight, moveUp, moveDown};
                            double minimum = move[0];

                            for (int i = 0; i < move.length; i++) {
                                if (move[i] < minimum) {
                                    minimum = move[i];
                                }
                            }
                            //minimum cases
                            if (minimum == moveLeft) {
                                entityB.setX(entityB.getX() - minimum);
                            } else if (minimum == moveRight) {
                                entityB.setX(entityB.getX() + minimum);
                            } else if (minimum == moveUp) {
                                entityB.setY(entityB.getY() - minimum); //where - means going up
                            } else if (minimum == moveDown) {
                                entityB.setY(entityB.getY() + minimum); //where + means going down
                            }
                        }
                    }
                }
            }
            else if (entityA instanceof Shell && entityB instanceof Wall ||
                    entityA instanceof Wall && entityB instanceof Shell) {
                //shell disappears, wall -hp
                if(areEntitiesColliding(entityA, entityB)){
                    if(entityA instanceof Shell && entityB instanceof Wall) {
                        entityA.setLives(0);
                        gameWorld.removeEntity(entityA.getId());
                        entityB.setLives(entityB.getLives() - 1);
                        if(entityB.getLives() == 0){
                            gameWorld.removeEntity(entityA.getId());
                        }
                    }
                    else if(entityA instanceof Wall && entityB instanceof Shell){ //entityA = Wall, entityB = Shell
                        entityA.setLives(entityA.getLives() - 1);
                        if(entityA.getLives() == 0){
                            gameWorld.removeEntity(entityA.getId());
                        }
                        entityB.setLives(0);
                        gameWorld.removeEntity(entityB.getId());
                    }
                }
            }

    }
    /**
     * resetGame is called at the end of the game once the gameplay loop exits. This should clear any existing data from
     * the game so that if the game is restarted, there aren't any things leftover from the previous run.
     */
    private void resetGame() {
        // TODO: Implement. reset all the data
        setUpGame();
        runGameView.reset();
    }
    public static void main(String[] args) {
        GameDriver gameDriver = new GameDriver();
        gameDriver.start();
    }

}
