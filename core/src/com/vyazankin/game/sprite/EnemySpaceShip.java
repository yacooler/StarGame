package com.vyazankin.game.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.vyazankin.game.base.BaseShip;
import com.vyazankin.game.dao.EnemyShipDAO;
import com.vyazankin.game.math.RandomBounds;
import com.vyazankin.game.math.Rect;
import com.vyazankin.game.utils.TextureUtils;

public class EnemySpaceShip extends BaseShip {

    private TextureAtlas mainAtlas;
    private boolean firstShoot;

    //стартовый множитель скорости, пока корабля не видно
    private float velocityScaler = 10f;

    //Первый выстрел на позиции, когда видна 1/5 корпуса корабля
    private float firstShootPosition = 1f;


    public EnemySpaceShip(TextureAtlas mainAtlas, BulletSpritePool bulletSpritePool, Sound shootSound) {
        super();
        this.mainAtlas = mainAtlas;
        this.bulletSpritePool = bulletSpritePool;
        this.shootSound = shootSound;
        initialized = false;

    }

    public void set(EnemyShipDAO shipDAO){
        set(
                TextureUtils.split(mainAtlas.findRegion(shipDAO.shipRegionName),1,2,2),
                mainAtlas.findRegion(shipDAO.bulletRegionName),
                super.bulletSpritePool,
                super.shootSound,
                shipDAO.ship_size,
                shipDAO.ship_max_velocity,
                shipDAO.ship_health,
                shipDAO.ship_rate_of_fire,
                shipDAO.bullet_velocity,
                shipDAO.bullet_size,
                shipDAO.bullet_damage);

        velocity_vector.set(0, shipDAO.ship_max_velocity);
        setBottom(getActualSpriteWorldBound().getTop());
        setLeft(RandomBounds.getRandom(getActualSpriteWorldBound().getLeft(), getActualSpriteWorldBound().getRight() - getFullWidth()));
        initialized = true;
    }

    @Override
    protected void worldResize(Rect bounds) {
        super.worldResize(bounds);
        if (bounds == null) {
            System.out.println("Wrong WR!");
        }
        setBottom(bounds.getTop());
    }

    @Override
    public void recalc(float deltaTime) {
        super.recalc(deltaTime);


        /*Вычисляем коэффициент наличия корабля на экране - 0 корабля полностью нет, 1 - корабль полностью виден*/
        float shipFrameAtTheScreenProportion = (1f + (getBottom() - actualSpriteWorldBound.getTop()) / getFullHeight());
        if (shipFrameAtTheScreenProportion > 1f) shipFrameAtTheScreenProportion = 1f;

        /*Скорость корабля скалирована от его позиции на экране. Если корабль на экране не показан - его скорость увеличена,
        конда корабль на экране полностью - его скорость стандартна
        */
        if (getTop() >= actualSpriteWorldBound.getTop()) {
            temporary.set(velocity_vector).scl(1f + velocityScaler * shipFrameAtTheScreenProportion);
            setCenterPosition(getCenterPosition().mulAdd(temporary, deltaTime));
        } else {
            setCenterPosition(getCenterPosition().mulAdd(velocity_vector, deltaTime));
        }

        /*Если корабль вышел за границы экрана - выводим его в инактив*/
        if (getTop() < actualSpriteWorldBound.getBottom()) {
            setActive(false);
        }

        /*Первый выстрел - зависит от положения корабля на экране firstShootPosition и возможен только
        если до этого корабль не стрелял по таймеру. Таймер при этом выстреле сбросится в ноль.
         */
        if (!firstShoot && (1 - firstShootPosition) >= shipFrameAtTheScreenProportion && shoots == 0){
            fire_delay_timer = Float.MAX_VALUE;
            firstShoot = true;
        }



    }
}
