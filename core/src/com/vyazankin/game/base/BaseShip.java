package com.vyazankin.game.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.vyazankin.game.sprite.Bullet;
import com.vyazankin.game.spritepools.BulletSpritePool;
import com.vyazankin.game.sprite.Explosion;
import com.vyazankin.game.spritepools.ExplosionSpritePool;

public abstract class BaseShip extends BaseSprite {


    //Параметры корабля
    private float shipSize;
    private float shipMaxVelocity;
    private int shipHealth;
    private float shipRateOfFire;


    //Параметры стрельбы
    //Выстрелов в секунду
    private float bulletVelocity;
    private float bulletSize;
    private int bulletDamage;
    protected float fireDelayTimer;
    protected float damageTimer = 10f;
    private float shootSoundVolume;

    //Общие пулы и ресурсы (не удаляем вместе с кораблем)
    protected BulletSpritePool bulletSpritePool;
    protected ExplosionSpritePool explosionSpritePool;
    protected Sound shootSound;
    protected TextureRegion bulletTextureRegion;


    //Скорость и вспомогательные вектора
    protected Vector2 velocity_vector = new Vector2();

    protected Vector2 temporary = new Vector2();
    protected Vector2 temporary2 = new Vector2();


    protected int shoots;

    protected boolean playerShip = false;

    public BaseShip(boolean playerShip){
        this.playerShip = playerShip;
    }

    public BaseShip(
            TextureRegion[] textureRegions,
            TextureRegion bulletTextureRegion,
            BulletSpritePool bulletSpritePool,
            ExplosionSpritePool explosionSpritePool,
            Sound shootSound,

            float shipSize,
            float shipMaxVelocity,
            int shipHealth,
            float shootSoundVolume,

            float shipRateOfFire,
            float bulletVelocity,
            float bulletSize,
            int bulletDamage){

        super(textureRegions);
        this.bulletTextureRegion = bulletTextureRegion;
        this.explosionSpritePool = explosionSpritePool;
        this.bulletSpritePool = bulletSpritePool;
        this.shootSound = shootSound;
        this.shootSoundVolume = shootSoundVolume;

        this.shipSize = shipSize;
        this.shipMaxVelocity = shipMaxVelocity;
        this.shipHealth = shipHealth;

        this.shipRateOfFire = shipRateOfFire;
        this.bulletVelocity = bulletVelocity;
        this.bulletSize = bulletSize;
        this.bulletDamage = bulletDamage;

        setHeightProportion(shipSize);
    }

    public void set(
            TextureRegion[] textureRegions,
            TextureRegion bulletTextureRegion,
            BulletSpritePool bulletSpritePool,
            Sound shootSound,

            float shipSize,
            float shipMaxVelocity,
            int shipHealth,
            float shootSoundVolume,

            float shipRateOfFire,
            float bulletVelocity,
            float bulletSize,
            int   bulletDamage) {

        this.textureRegions = textureRegions;
        this.bulletTextureRegion = bulletTextureRegion;
        this.bulletSpritePool = bulletSpritePool;
        this.shootSound = shootSound;
        this.shootSoundVolume = shootSoundVolume;

        this.shipSize = shipSize;
        this.shipMaxVelocity = shipMaxVelocity;
        this.shipHealth = shipHealth;

        this.shipRateOfFire = shipRateOfFire;
        this.bulletVelocity = bulletVelocity;
        this.bulletSize = bulletSize;
        this.bulletDamage = bulletDamage;

        this.currentFrame = 0;
        this.initialized = true;
        shoots = 0;

        this.fireDelayTimer = 0;
        this.damageTimer = 10f;

        setHeightProportion(shipSize);

    }

    @Override
    public void recalc(float deltaTime) {
        super.recalc(deltaTime);

        fireDelayTimer += deltaTime;
        //Делаем проверку на возможность выстрела и непосредственно выстрел
        if (fireDelayTimer >= 1f/ shipRateOfFire){
            fireDelayTimer = 0f;
            temporary.set(0, 1);
            makeShoot(temporary.angle(velocity_vector));
        }

        damageTimer += deltaTime;
        //проверка на урон
        if (damageTimer > 0.1f){
            currentFrame = 0;
            damageTimer = 10f;
        } else {
            currentFrame = 1;
        }

    }

    /**
     * Сделать выстрел
     */
    protected void makeShoot(float bulletAngle){
        shoots++;
        //Взяли пулю в неактивных или новых
        Bullet bullet = bulletSpritePool.poolNewOrInactiveSprite();

        //Начальная позиция пули
        temporary.set(getCenterPosition()).add(0f, getHalfHeight()).rotateAround(getCenterPosition(), bulletAngle);
        //Скорость и направление пули
        temporary2.set(0f, bulletVelocity).setAngle(bulletAngle + 90f);

        bullet.set(bulletTextureRegion,
                0,
                temporary,
                bulletSize,
                temporary2,
                bulletDamage,

                playerShip);

        bullet.setActive(true);
        //Положили в активные, оттуда она исчезнет по своему внутреннему условию
        bulletSpritePool.addSpriteIntoActive(bullet);

        //Звук выстрела
        long soundId = shootSound.play();
        shootSound.setVolume(soundId, shootSoundVolume);
    }


    /**
     * Получение урона damage кораблем. В случае, когда здоровье меньше либо равно 0 - вызывает destroy()
     * @param damage
     */
    public void damage(int damage){
        damageTimer = 0f;
        shipHealth -= damage;
        if (shipHealth <= 0){
            destroy();
        }
    }

    public void destroy(){
        Explosion explosion = explosionSpritePool.poolNewOrInactiveSprite();
        explosion.set(getCenterPosition(), shipSize);
        explosion.setActive(true);
        explosionSpritePool.addSpriteIntoActive(explosion);
        setActive(false);
    }

    public int getShipHealth(){
        return shipHealth;
    }


}
