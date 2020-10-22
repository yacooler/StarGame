package com.vyazankin.game.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class BaseButton extends BaseSprite implements TouchListener{

    public BaseButton(TextureRegion regionUntouched, TextureRegion regionTouched) {
        super(new TextureRegion[] {regionUntouched, regionTouched});
    }

    @Override
    public void touchDown(Vector2 worldTouchPosition, int pointer, int button) {
        currentFrame = 1;
    }

    @Override
    public void touchUp(Vector2 worldTouchPosition, int pointer, int button) {
        currentFrame = 0;
    }

    //Для тачдауна проверяем, кликнули ли мы внутрь кнопки
    @Override
    public boolean isTouchDownInBounds(Vector2 checkedPosition) {
        return isVectorInside(checkedPosition);
    }

}
