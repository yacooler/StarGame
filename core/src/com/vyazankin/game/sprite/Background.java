package com.vyazankin.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.vyazankin.game.base.BaseSprite;
import com.vyazankin.game.base.TouchListener;
import com.vyazankin.game.math.Rect;

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
