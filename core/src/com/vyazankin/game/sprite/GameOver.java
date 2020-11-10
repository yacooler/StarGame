package com.vyazankin.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.vyazankin.game.base.BaseSprite;
import com.vyazankin.game.base.Rect;

public class GameOver extends BaseSprite {
    private float recalcTimer;
    public GameOver(TextureRegion region) {
        super(region);
    }

    @Override
    protected void recalc(float deltaTime) {
        super.recalc(deltaTime);
        recalcTimer += deltaTime;
        setCenterPosition(0, (float) Math.sin(recalcTimer) / 5f);
    }

    @Override
    protected void worldResize(Rect bounds) {
        super.worldResize(bounds);
        setHeightProportion(0.08f);
    }
}
