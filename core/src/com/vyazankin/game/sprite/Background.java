package com.vyazankin.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.vyazankin.game.base.BaseSprite;
import com.vyazankin.game.base.Rect;

public class Background extends BaseSprite{

    public Background(TextureRegion region) {
        super(region);
    }

    @Override
    public void worldResize(Rect bounds) {
        setHeightProportion(bounds.getFullHeight());
        setCenterPosition(bounds.getCenterPosition());
    }

}
