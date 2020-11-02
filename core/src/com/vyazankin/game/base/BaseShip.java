package com.vyazankin.game.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.vyazankin.game.sprite.Bullet;
import com.vyazankin.game.sprite.BulletSpritePool;

public abstract class BaseShip extends BaseSprite {


    //Параметры корабля
    private float ship_size;
    private float ship_max_velocity;
    private int ship_health;
    private float ship_rate_of_fire;


    //Параметры стрельбы
    //Выстрелов в секунду
    private float bullet_velocity;
    private float bullet_size;
    private int   bullet_damage;
    protected float fire_delay_timer;


    //Общие пулы и ресурсы (не удаляем вместе с кораблем)
    protected BulletSpritePool bulletSpritePool;
    protected Sound shootSound;
    protected TextureRegion bulletTextureRegion;


    //Скорость и вспомогательные вектора
    protected Vector2 velocity_vector = new Vector2();

    protected Vector2 temporary = new Vector2();
    protected Vector2 temporary2 = new Vector2();

    protected int shoots;

    public BaseShip(){
    }

    public BaseShip(
            TextureRegion[] textureRegions,
            TextureRegion bulletTextureRegion,
            BulletSpritePool bulletSpritePool,
            Sound shootSound,

            float ship_size,
            float ship_max_velocity,
            int ship_health,

            float ship_rate_of_fire,
            float bullet_velocity,
            float bullet_size,
            int   bullet_damage){

        super(textureRegions);
        this.bulletTextureRegion = bulletTextureRegion;
        this.bulletSpritePool = bulletSpritePool;
        this.shootSound = shootSound;

        this.ship_size = ship_size;
        this.ship_max_velocity = ship_max_velocity;
        this.ship_health = ship_health;

        this.ship_rate_of_fire = ship_rate_of_fire;
        this.bullet_velocity = bullet_velocity;
        this.bullet_size = bullet_size;
        this.bullet_damage = bullet_damage;

        setHeightProportion(ship_size);
    }

    public void set(
            TextureRegion[] textureRegions,
            TextureRegion bulletTextureRegion,
            BulletSpritePool bulletSpritePool,
            Sound shootSound,

            float ship_size,
            float ship_max_velocity,
            int ship_health,

            float ship_rate_of_fire,
            float bullet_velocity,
            float bullet_size,
            int   bullet_damage) {

        this.textureRegions = textureRegions;
        this.bulletTextureRegion = bulletTextureRegion;
        this.bulletSpritePool = bulletSpritePool;
        this.shootSound = shootSound;

        this.ship_size = ship_size;
        this.ship_max_velocity = ship_max_velocity;
        this.ship_health = ship_health;

        this.ship_rate_of_fire = ship_rate_of_fire;
        this.bullet_velocity = bullet_velocity;
        this.bullet_size = bullet_size;
        this.bullet_damage = bullet_damage;

        this.currentFrame = 0;
        this.initialized = true;

        setHeightProportion(ship_size);

    }

    @Override
    public void recalc(float deltaTime) {
        super.recalc(deltaTime);

        //Делаем проверку на возможность выстрела и непосредственно выстрел
        if (fire_delay_timer >= 1f/ship_rate_of_fire){
            fire_delay_timer = 0f;
            temporary.set(0, 1);
            make_shoot(temporary.angle(velocity_vector));
        }
        fire_delay_timer += deltaTime;
    }

    /**
     * Сделать выстрел
     */
    protected void make_shoot(float bulletAngle){
        shoots++;
        //Взяли пулю в неактивных или новых
        Bullet bullet = bulletSpritePool.poolNewOrInactiveSprite();

        //Начальная позиция пули
        temporary.set(getCenterPosition()).add(0f, getHalfHeight()).rotateAround(getCenterPosition(), bulletAngle);
        //Скорость и направление пули
        temporary2.set(0f, bullet_velocity).setAngle(bulletAngle + 90f);

        bullet.set(bulletTextureRegion,
                0,
                temporary,
                bullet_size,
                temporary2,
                bullet_damage);

        bullet.setActive(true);
        //Положили в активные, оттуда она исчезнет по своему внутреннему условию
        bulletSpritePool.addSpriteIntoActive(bullet);

        //Звук выстрела
        long soundId = shootSound.play();
        shootSound.setVolume(soundId, 0.04f);
    }


}
