package edu.csc413.tankgame.model;

import edu.csc413.tankgame.KeyboardReader;

import java.util.*;

/**
 * GameWorld holds all of the model objects present in the game. GameWorld tracks all moving entities like tanks and
 * shells, and provides access to this information for any code that needs it (such as GameDriver or entity classes).
 */
public class GameWorld {
    // TODO: Implement. There's a lot of information the GameState will need to store to provide contextual information.
    //       Add whatever instance variables, constructors, and methods are needed.
    private ArrayList<Entity> entities;
    private ArrayList<Entity> entitiesToAdd;

    KeyboardReader keyboardReader = KeyboardReader.instance();

    public GameWorld() {
        // TODO: Implement.
        entities = new ArrayList<>();
        entitiesToAdd = new ArrayList<>();
    }

    /** Returns a list of all entities in the game. */
    public List<Entity> getEntities() {
        // TODO: Implement. [DONE]
        return entities;
    }

    public List<Entity> getEntitiesToAdd(){
        return entitiesToAdd;
    }

    public void moveEntitiesToAdd(){
        entities.addAll(entitiesToAdd);
        entitiesToAdd.clear();
    }

    /** Adds a new entity to the game. */
    public void addEntity(Entity entity) {
        // TODO: Implement. [DONE]
        entitiesToAdd.add(entity);
    }

    /** Returns the Entity with the specified ID. */
    public Entity getEntity(String id) {
        // TODO: Implement. [DONE]
        Entity tempEntity = null;
        for(Entity entity: entities) {
            if (entity.getId().equals(id)) {
                tempEntity = entity;
            }
        }
        return tempEntity;
    }

    /** Removes the entity with the specified ID from the game. */
    public void removeEntity(String id) {
        // TODO: Implement. [DONE]
        for(Entity entity: entities) {
            if (entity.getId().equals(id)) {
                entities.remove(entity);
            }
        }
    }
    //Keyboard reader -> GameWorld
    public boolean upKeyPressed (){
        return keyboardReader.upPressed();
    }
    public boolean downKeyPressed(){
        return keyboardReader.downPressed();
    }
    public boolean leftKeyPressed(){
        return keyboardReader.leftPressed();
    }
    public boolean rightKeyPressed(){
        return keyboardReader.rightPressed();
    }
    public boolean spaceBarPressed(){
        return keyboardReader.spacePressed();
    }
}
