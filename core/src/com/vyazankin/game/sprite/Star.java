package com.vyazankin.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.vyazankin.game.base.BaseSprite;
import com.vyazankin.game.utils.RandomBounds;
import com.vyazankin.game.base.Rect;

public class Star extends BaseSprite {


    private Vector2 velocity;
    private float size;


    public Star(TextureRegion region) {
        super(region);
    }

    @Override
    public void worldResize(Rect bounds) {
        super.worldResize(bounds);

        setCenterPosition(RandomBounds.getRandom(bounds));
        size = RandomBounds.getRandom(0.001f, 0.01f);
        velocity = new Vector2(RandomBounds.getRandom(-0.0001f, 0.0001f), -0.01f).scl(size * 3000f);
        setHeightProportion(size);
    }

    @Override
    public void recalc(float deltaTime) {

        super.recalc(deltaTime);
        setCenterPosition(getCenterPosition().mulAdd(velocity, deltaTime));

        if (getRight() < getActualSpriteWorldBound().getLeft()){
            setLeft(getActualSpriteWorldBound().getRight());
        }

        if (getLeft() > getActualSpriteWorldBound().getRight()){
            setRight(getActualSpriteWorldBound().getLeft());
        }

        if (getTop() < getActualSpriteWorldBound().getBottom()){
            setBottom(getActualSpriteWorldBound().getTop());
        }
    }
}
