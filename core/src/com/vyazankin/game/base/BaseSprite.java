package com.vyazankin.game.base;

import com.badlogic.gdx.graphics.g2d.Batch;

abstract public class BaseSprite {
    abstract public void draw(Batch batch);
    abstract public void dispose();
    abstract public void recalc();
}
