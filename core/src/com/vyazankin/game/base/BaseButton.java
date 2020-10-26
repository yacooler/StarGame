package com.vyazankin.game.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;


public abstract class BaseButton extends BaseSprite implements TouchListener{

    private int pointer = 0;
    private Vector2 touchedPosition;
    private boolean pressed;

    public BaseButton(TextureRegion regionUntouched, TextureRegion regionTouched) {
        super(new TextureRegion[] {regionUntouched, regionTouched});
        touchedPosition = new Vector2();
    }

    public BaseButton(TextureRegion region) {
        super(new TextureRegion[] {region, region});
        touchedPosition = new Vector2();
    }

    @Override
    public void touchDown(Vector2 worldTouchPosition, int pointer, int button) {
        touchedPosition = worldTouchPosition;
        this.pointer = pointer;
        pressed = true;
        //Если разные текстуры - меняем текстуры, иначе меняем фрейм
        if (textureRegions[0] == textureRegions[1]){
            setScale(0.9f);
        } else {
            setCurrentFrame(1);
        }
    }

    @Override
    public void touchUp(Vector2 worldTouchPosition, int pointer, int button) {
        if (!pressed) return;
        this.pointer = 0;
        pressed = false;
        //Если разные текстуры - меняем текстуры, иначе меняем фрейм
        if (textureRegions[0] == textureRegions[1]){
            setScale(1f);
        } else {
            setCurrentFrame(0);
        }

        //action() вызывается последним, т.к. после него экрана и текстур уже может не существовать
        if (this.pointer == pointer && isTouchDownInBounds(worldTouchPosition)){
            action();
        }
    }

    //Для тачдауна проверяем, кликнули ли мы внутрь кнопки
    @Override
    public boolean isTouchDownInBounds(Vector2 checkedPosition) {
        return isVectorInside(checkedPosition);
    }

    public abstract void action();

}
