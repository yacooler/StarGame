package com.vyazankin.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.vyazankin.game.base.BaseSprite;
import com.vyazankin.game.base.TouchListener;
import com.vyazankin.game.math.Rect;

public class Background extends BaseSprite implements TouchListener {

    public Background(TextureRegion region) {
        super(region);
    }

    @Override
    public void worldResize(Rect bounds) {
        setHeightProportion(bounds.getFullHeight());
        setCenterPosition(bounds.getCenterPosition());
    }

    @Override
    public void dispose() {

    }

    @Override
    public void touchDown(Vector2 worldTouchPosition, int pointer, int button) {
        System.out.println("Тачдаун " + worldTouchPosition);
    }

    @Override
    public void touchUp(Vector2 worldTouchPosition, int pointer, int button) {
        System.out.println("Тачап " + worldTouchPosition);
    }

    @Override
    public void drag(Vector2 worldTouchPosition, int pointer) {
        System.out.println("Драг " + worldTouchPosition);
    }

    @Override
    public boolean isInBounds(Vector2 checkedPosition) {
        return isVectorInside(checkedPosition);
    }
}
