package com.mygdx.game.Back.MovementStrategy;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Back.Object.Object;
import com.mygdx.game.Back.World.Map;

public interface MovementStrategy {
    Vector2 getNextPosition(Object object, Map map);
}


