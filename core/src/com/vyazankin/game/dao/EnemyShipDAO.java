package com.vyazankin.game.dao;


public class EnemyShipDAO {

    //Параметры корабля
    public float ship_size;
    public float ship_max_velocity;
    public int ship_health;
    public float ship_rate_of_fire;
    public float shootSoundVolume;

    public String shipRegionName;
    public String bulletRegionName;

    //Параметры стрельбы
    public float bullet_velocity;
    public float bullet_size;
    public int   bullet_damage;

    public EnemyShipDAO(float ship_size, float ship_max_velocity, int ship_health, float ship_rate_of_fire, float shootSoundVolume, String shipRegionName, String bulletRegionName, float bullet_velocity, float bullet_size, int bullet_damage) {
        this.ship_size = ship_size;
        this.ship_max_velocity = ship_max_velocity;
        this.ship_health = ship_health;
        this.ship_rate_of_fire = ship_rate_of_fire;
        this.shootSoundVolume = shootSoundVolume;
        this.shipRegionName = shipRegionName;
        this.bulletRegionName = bulletRegionName;
        this.bullet_velocity = bullet_velocity;
        this.bullet_size = bullet_size;
        this.bullet_damage = bullet_damage;
    }
}
