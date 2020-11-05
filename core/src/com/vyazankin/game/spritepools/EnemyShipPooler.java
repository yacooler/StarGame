package com.vyazankin.game.spritepools;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.vyazankin.game.base.BaseSpritePool;
import com.vyazankin.game.dao.EnemyShipDAO;
import com.vyazankin.game.sprite.EnemyShipParamGetter;
import com.vyazankin.game.sprite.EnemySpaceShip;
import com.vyazankin.game.spritepools.BulletSpritePool;
import com.vyazankin.game.spritepools.ExplosionSpritePool;

public class EnemyShipPooler extends BaseSpritePool<EnemySpaceShip> {

    private TextureAtlas mainAtlas;
    private com.vyazankin.game.spritepools.BulletSpritePool bulletSpritePool;
    private com.vyazankin.game.spritepools.ExplosionSpritePool explosionSpritePool;
    private Sound shootSound;

    EnemyShipParamGetter enemyShipParamGetter;


    public EnemyShipPooler(TextureAtlas mainAtlas, BulletSpritePool bulletSpritePool, ExplosionSpritePool explosionSpritePool, Sound shootSound) {
        this.mainAtlas = mainAtlas;
        this.bulletSpritePool = bulletSpritePool;
        this.explosionSpritePool = explosionSpritePool;
        this.shootSound = shootSound;

        enemyShipParamGetter = new EnemyShipParamGetter();
    }

    @Override
    protected EnemySpaceShip obtainSprite() {
        //Корабль, созданный этим методом необходимо инициализировать методом set(...)
        return new EnemySpaceShip(mainAtlas, bulletSpritePool, explosionSpritePool, shootSound);
    }

    public EnemySpaceShip poolShip(int type){

        EnemySpaceShip ship;

        EnemyShipDAO shipDAO = enemyShipParamGetter.get(type);
        ship = poolNewOrInactiveSprite();

        ship.set(shipDAO);

        return ship;
    }

}
