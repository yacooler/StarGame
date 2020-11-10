package com.vyazankin.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.vyazankin.game.base.BaseButton;
import com.vyazankin.game.base.Rect;

public abstract class NewGame extends BaseButton {
    public NewGame(TextureRegion region) {
        super(region);
    }

    @Override
    protected void worldResize(Rect bounds) {
        super.worldResize(bounds);
        setHeightProportion(0.05f);
        setBottom(getActualSpriteWorldBound().getBottom() + this.getFullHeight());
    }
}
