package com.vyazankin.game.sprite;

import com.vyazankin.game.dao.EnemyShipDAO;

public class EnemyShipParamGetter {

    private EnemyShipDAO[] ships = new EnemyShipDAO[3];
    public EnemyShipParamGetter() {
        ships[0] = new EnemyShipDAO(0.1f, -0.1f, 5, 1f, "enemy0", "bulletEnemy", -0.3f, 0.01f, 1);
        ships[1] = new EnemyShipDAO(0.2f, -0.05f, 5, 0.3f, "enemy1", "bulletEnemy", -0.1f, 0.02f, 5);
        ships[2] = new EnemyShipDAO(0.25f, -0.01f, 5, 0.1f, "enemy2", "bulletEnemy", -0.1f, 0.04f, 10);
    }

    public EnemyShipDAO get(int type){
        System.out.println("new ship " +type );
        return ships[type];
    }


}
