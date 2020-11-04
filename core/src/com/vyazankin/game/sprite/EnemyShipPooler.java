package com.vyazankin.game.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.vyazankin.game.base.BaseSpritePool;
import com.vyazankin.game.dao.EnemyShipDAO;

public class EnemyShipPooler extends BaseSpritePool<EnemySpaceShip> {

    private TextureAtlas mainAtlas;
    private BulletSpritePool bulletSpritePool;
    private Sound shootSound;

    EnemyShipParamGetter enemyShipParamGetter;


    public EnemyShipPooler(TextureAtlas mainAtlas, BulletSpritePool bulletSpritePool, Sound shootSound) {
        this.mainAtlas = mainAtlas;
        this.bulletSpritePool = bulletSpritePool;
        this.shootSound = shootSound;

        enemyShipParamGetter = new EnemyShipParamGetter();
    }

    @Override
    protected EnemySpaceShip obtainSprite() {
        //Корабль, созданный этим методом необходимо инициализировать методом set(...)
        return new EnemySpaceShip(mainAtlas, bulletSpritePool, shootSound);
    }

    public EnemySpaceShip poolShip(int type){

        EnemySpaceShip ship;

        EnemyShipDAO shipDAO = enemyShipParamGetter.get(type);
        ship = poolNewOrInactiveSprite();

        ship.set(shipDAO);

        return ship;
    }

}
