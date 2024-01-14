package com.mygdx.game.Back.MovementStrategy;

import com.badlogic.gdx.math.Vector2;

import com.mygdx.game.Back.Object.Object;
import com.mygdx.game.Back.World.Map;

import java.util.Random;

public class WanderMovementStrategy implements MovementStrategy {

    private Random random;

    public WanderMovementStrategy() {
        this.random = new Random();
    }

    @Override
    public Vector2 getNextPosition(Object object, Map map) {
        float speed = 7; // Adjust speed as needed
        float X = object.getX();
        float Y = object.getY();

        // Generate random movements
        int randomX = random.nextInt(3) - 1; // Random movement in x direction (-1, 0, 1)
        int randomY = random.nextInt(3) - 1; // Random movement in y direction (-1, 0, 1)

        // Get New Position
        float newX = X + (randomX * map.getTilewidth() / speed);
        float newY = Y + (randomY * map.getTilewidth() / speed);

        // Update character's new position if valid
        if (object.isValidPosition((int) newX / map.getTilewidth(), (int) newY / map.getTilewidth(), map)) {
            return new Vector2(newX, newY);
        }

        // Return the current position if the new position is not valid
        return new Vector2(X, Y);
    }
}
