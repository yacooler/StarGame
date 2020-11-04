package com.vyazankin.game.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.vyazankin.game.base.BaseShip;
import com.vyazankin.game.base.InputListener;
import com.vyazankin.game.math.Rect;
import com.vyazankin.game.utils.TextureUtils;

public class PlayerSpaceShip extends BaseShip implements InputListener {

    private boolean isLeftPressed;
    private boolean isRightPressed;

    private final int UNKNOWN_POINTER = -1;
    private int isLeftTouched = UNKNOWN_POINTER;
    private int isRightTouched = UNKNOWN_POINTER;

    //Корабль
    private static final float SHIP_SIZE = 0.15f;
    private static final float MAX_SHIP_VELOCITY = 0.5f;
    private static final int SHIP_HEALTH = 100;
    private static final float SHOOT_SOUND_VOLUME = 0.3f;
    //Выстрелы
    private static final float SHIP_RATE_OF_FIRE = 10f;
    private static final float BULLET_VELOCITY = -0.1f;
    private static final float BULLET_SIZE = 0.01f;
    private static final int   BULLET_DAMAGE = 1;


    Sound shootSound;


    public PlayerSpaceShip(TextureAtlas mainAtlas, BulletSpritePool bulletSpritePool, Sound shootSound) {

        super(  TextureUtils.split(mainAtlas.findRegion("main_ship"),1,2,2),
                mainAtlas.findRegion("bulletMainShip"),
                bulletSpritePool,
                shootSound,

                SHIP_SIZE,
                MAX_SHIP_VELOCITY,
                SHIP_HEALTH,
                SHOOT_SOUND_VOLUME,
                SHIP_RATE_OF_FIRE,

                BULLET_VELOCITY,
                BULLET_SIZE,
                BULLET_DAMAGE);

        this.bulletSpritePool = bulletSpritePool;
        this.shootSound = shootSound;
    }

    @Override
    public void worldResize(Rect bounds) {
        super.worldResize(bounds);
        setBottom(bounds.getBottom() + SHIP_SIZE / 4);
    }

    @Override
    public void recalc(float deltaTime) {
        super.recalc(deltaTime);

        centerPosition.mulAdd(velocity_vector, deltaTime);
        if (getLeft() < getActualSpriteWorldBound().getLeft()){
            setLeft(getActualSpriteWorldBound().getLeft());
            stop();
        } else if(getRight() > getActualSpriteWorldBound().getRight()){
            setRight(getActualSpriteWorldBound().getRight());
            stop();
        }

        temporary.set(0f, 1f);
        float shipAngle = temporary.angle(velocity_vector) /3f;
        setAngle(shipAngle);


    }

    @Override
    public void touchDown(Vector2 worldTouchPosition, int pointer, int button) {
        if (worldTouchPosition.x < getActualSpriteWorldBound().getCenterPosition().x){
            isLeftTouched = pointer;
            moveLeft();
        } else {
            isRightTouched = pointer;
            moveRight();
        }
    }

    @Override
    public void touchUp(Vector2 worldTouchPosition, int pointer, int button) {
        if (isLeftTouched == pointer){
            isLeftTouched = UNKNOWN_POINTER;
            if (isRightTouched != UNKNOWN_POINTER){
                moveRight();
            } else {
                stop();
            }
        } else if (isRightTouched == pointer){
            isRightTouched = UNKNOWN_POINTER;
            if (isLeftTouched != UNKNOWN_POINTER){
                moveLeft();
            } else {
                stop();
            }
        }
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
        velocity_vector.x = -MAX_SHIP_VELOCITY;
    }

    public void moveRight(){
        velocity_vector.x = MAX_SHIP_VELOCITY;
    }

    public void stop(){
        velocity_vector.setZero();
    }



    @Override
    public void dispose() {
        shootSound.dispose();
        super.dispose();
    }
}
