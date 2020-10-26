package com.vyazankin.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.vyazankin.game.base.BaseSprite;
import com.vyazankin.game.math.RandomBounds;
import com.vyazankin.game.math.Rect;

public class Star extends BaseSprite {


    private Vector2 velocity;
    private float size;
    private Rect worldBounds;

    public Star(TextureRegion region) {
        super(region);
    }

    @Override
    public void worldResize(Rect bounds) {
        super.worldResize(bounds);
        worldBounds = bounds;
        setCenterPosition(RandomBounds.getRandom(bounds));
        size = RandomBounds.getRandom(0.005f, 0.01f);
        velocity = new Vector2(RandomBounds.getRandom(-0.001f, 0.001f), -0.01f).scl(size * 30f);
        setHeightProportion(size);
    }

    @Override
    public void recalc(float deltaTime) {
        super.recalc(deltaTime);
        setCenterPosition(getCenterPosition().add(velocity));

        if (getRight() < worldBounds.getLeft()){
            setLeft(worldBounds.getRight());
        }

        if (getLeft() > worldBounds.getRight()){
            setRight(worldBounds.getLeft());
        }

        if (getTop() < worldBounds.getBottom()){
            setBottom(worldBounds.getTop());
        }
    }
}
