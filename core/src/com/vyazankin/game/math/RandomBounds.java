package com.vyazankin.game.math;

import com.badlogic.gdx.math.Vector2;

public class RandomBounds {

    public static float getRandom(float minBound, float maxBound){

        return (float) ((maxBound - minBound) * Math.random() + minBound);
    }

    public static Vector2 getRandom(Rect bounds){

        Vector2 randomCoordinates = new Vector2();

        randomCoordinates.set(
                getRandom(bounds.getLeft(), bounds.getRight()),
                getRandom(bounds.getBottom(), bounds.getTop()));

        return randomCoordinates;
    }

}
