package com.vyazankin.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.vyazankin.game.base.BaseSprite;

public class GameOver extends BaseSprite {
    private float recalcTimer;
    public GameOver(TextureRegion region) {
        super(region);
    }

    @Override
    protected void recalc(float deltaTime) {
        super.recalc(deltaTime);
        recalcTimer += deltaTime;
        setCenterPosition(0, (float) Math.sin(recalcTimer / 1000f) / 5f);
    }
}
