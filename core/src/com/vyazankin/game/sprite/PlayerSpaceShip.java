package com.vyazankin.game.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.vyazankin.game.base.BaseSprite;
import com.vyazankin.game.base.InputListener;
import com.vyazankin.game.math.Rect;

public class PlayerSpaceShip extends BaseSprite implements InputListener {

    private Rect worldBounds;
    private final float SHIP_SIZE = 0.1f;
    private Vector2 velocity;
    private Vector2 temporary;
    private float velocityConst = 0.01f;

    private boolean isLeftPressed;
    private boolean isRightPressed;

    private final int UNKNOWN_POINTER = -1;
    private int isLeftTouched = UNKNOWN_POINTER;
    private int isRightTouched = UNKNOWN_POINTER;


    public PlayerSpaceShip(TextureRegion region) {
        super(region);
        temporary = new Vector2(0,0);
        velocity = new Vector2(0,0);
    }



    @Override
    public void worldResize(Rect bounds) {
        super.worldResize(bounds);
        worldBounds = bounds;
        setHeightProportion(SHIP_SIZE);
        setBottom(bounds.getBottom() + SHIP_SIZE / 3);
    }

    @Override
    public void touchDown(Vector2 worldTouchPosition, int pointer, int button) {

    }

    @Override
    public void touchUp(Vector2 worldTouchPosition, int pointer, int button) {
        worldTouchPosition.y = getCenterPosition().y;
        temporary.set(worldTouchPosition);
        temporary.sub(getCenterPosition()).nor();
        velocity.set(temporary.scl(velocityConst));
    }

    @Override
    public void recalc(float deltaTime) {
        super.recalc(deltaTime);
        centerPosition.add(velocity);
    }


    @Override
    public boolean keyDown(int keycode) {
        switch (keycode){
            case Input.Keys.A:
            case Input.Keys.LEFT:
                isLeftPressed = true;
                moveLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                isRightPressed = true;
                moveRight();
                break;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode){

            case Input.Keys.A:
            case Input.Keys.LEFT:
                isLeftPressed = false;
                if (isRightPressed){
                    moveRight();
                } else {
                    stop();
                }
                break;

            case Input.Keys.D:
            case Input.Keys.RIGHT:
                isRightPressed = false;
                if (isLeftPressed){
                    moveLeft();
                } else {
                    stop();
                }
                break;
        }
        return false;
    }

    public void moveLeft(){
        velocity.x = -velocityConst;
    }

    public void moveRight(){
        velocity.x = velocityConst;
    }

    public void stop(){
        velocity.setZero();
    }

}
