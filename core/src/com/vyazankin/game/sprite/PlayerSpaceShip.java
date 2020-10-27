package com.vyazankin.game.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
    private final float SHIP_VELOCITY = 0.5f;

    private boolean isLeftPressed;
    private boolean isRightPressed;

    private final int UNKNOWN_POINTER = -1;
    private int isLeftTouched = UNKNOWN_POINTER;
    private int isRightTouched = UNKNOWN_POINTER;

    //Выстрелов в секунду
    private final float RATE_OF_FIRE = 30f;
    private final float BULLET_VELOCITY = -1f;
    private final float BULLET_SIZE = 0.01f;
    private final int   BULLET_DAMAGE = 1;
    private float fire_delay_time;

    private BulletSpritePool bulletSpritePool;

    private TextureAtlas mainAtlas;


    public PlayerSpaceShip(TextureRegion region, BulletSpritePool bulletSpritePool) {
        super(region);
        temporary = new Vector2(0,1);
        velocity = new Vector2(0,0);
        this.bulletSpritePool = bulletSpritePool;

        mainAtlas = new TextureAtlas("images/textures/mainAtlas.tpack");
    }



    @Override
    public void worldResize(Rect bounds) {
        super.worldResize(bounds);
        worldBounds = bounds;
        setHeightProportion(SHIP_SIZE);
        setBottom(bounds.getBottom() + SHIP_SIZE / 3);
    }

    @Override
    public void recalc(float deltaTime) {
        super.recalc(deltaTime);
        centerPosition.mulAdd(velocity, deltaTime);
        if (getLeft() < getActualWorldBound().getLeft()){
            setLeft(getActualWorldBound().getLeft());
            stop();
        } else if(getRight() > getActualWorldBound().getRight()){
            setRight(getActualWorldBound().getRight());
            stop();
        }

        temporary.set(0f, 1f);
        float shipAngle = temporary.angle(velocity) /3f;

        setAngle(shipAngle);

        //Делаем проверку на выстрел и выстрел
        fire_delay_time += deltaTime;
        if (1f/RATE_OF_FIRE < fire_delay_time){
            fire_delay_time = 0f;
            fire(shipAngle);
        }


    }

    @Override
    public void touchDown(Vector2 worldTouchPosition, int pointer, int button) {
        if (worldTouchPosition.x < getActualWorldBound().getCenterPosition().x){
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
        velocity.x = -SHIP_VELOCITY;
    }

    public void moveRight(){
        velocity.x = SHIP_VELOCITY;
    }

    public void stop(){
        velocity.setZero();
    }

    public void fire(float buletAngle) {
        //Взяли спрайт в неактивных или новых
        Bullet bullet = bulletSpritePool.poolNewOrInactiveSprite();

        //Начальная позиция пули

        temporary.set(getCenterPosition()).add(0f, getHalfHeight()).rotateAround(getCenterPosition(), buletAngle);
        bullet.set(
                mainAtlas.findRegion("bulletMainShip"),
                0,
                temporary,
                BULLET_SIZE);
        bullet.setDamage(BULLET_DAMAGE);

        //Скорость и направление пули
        temporary.set(0f, BULLET_VELOCITY).setAngle(buletAngle + 90f);

        bullet.setVelocity(temporary);
        bullet.setActive(true);
        //Положили в активные, оттуда она исчезнет по своему внутреннему условию
        bulletSpritePool.addSpriteIntoActive(bullet);
    }


}
