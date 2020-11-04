package com.vyazankin.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.vyazankin.game.base.BaseScreen;
import com.vyazankin.game.base.BaseSprite;
import com.vyazankin.game.sprite.Background;
import com.vyazankin.game.sprite.BulletSpritePool;
import com.vyazankin.game.sprite.EnemyShipPooler;
import com.vyazankin.game.sprite.EnemySpaceShip;
import com.vyazankin.game.sprite.PlayerSpaceShip;
import com.vyazankin.game.sprite.Star;

import java.util.ArrayList;
import java.util.List;

public class GameScreen extends BaseScreen {

    private Background background;

    private List<BaseSprite> stars;

    private TextureAtlas menuAtlas;
    private TextureAtlas mainAtlas;

    private final int STARS_COUNT = 64;

    private PlayerSpaceShip spaceShip;

    private BulletSpritePool bulletSpritePool;

    public GameScreen(Game game) {
        super(game);
    }

    private EnemyShipPooler enemyShipPooler;

    private int MAX_SHIPS = 15;

    Sound shootSound;
    Sound enemyShootSound;
    @Override
    public void show() {
        super.show();

        shootSound =  Gdx.audio.newSound(Gdx.files.internal("resources/sounds/laser.wav"));
        enemyShootSound = Gdx.audio.newSound(Gdx.files.internal("resources/sounds/bullet.wav"));

        //Пул спрайтов - пуль
        bulletSpritePool = new BulletSpritePool();

        //Добавляем пул в список пулов. По ним происходит автоматическое итерирование и вызов необходимых методов
        addSpritePool(bulletSpritePool);



        menuAtlas = new TextureAtlas("resources/textures/menuAtlas.tpack");
        mainAtlas = new TextureAtlas("resources/textures/mainAtlas.tpack");

        background = new Background(new TextureRegion(new Texture("resources/textures/background.jpg")));

        //Пул по умолчанию
        addSpriteToDefaultPool(background, true);

        //Пуллер кораблей - класс, отвечающий за создание вражеских кораблей. Наследуется от BaseSpritePool
        enemyShipPooler = new EnemyShipPooler(mainAtlas, bulletSpritePool, enemyShootSound);
        addSpritePool(enemyShipPooler);


        //Пролетающие на заднем фоне звёзды находятся в пуле по умолчанию
        stars = new ArrayList<>(STARS_COUNT);
        for (int i = 0; i < STARS_COUNT; i++) {
            Star star;
            star = new Star(new TextureRegion(menuAtlas.findRegion("star")));
            stars.add(star);
            addSpriteToDefaultPool(star, true);
        }


        //Корабль игрока, находится в пуле по умолчанию
        spaceShip = new PlayerSpaceShip(mainAtlas, bulletSpritePool, shootSound);

        addSpriteToDefaultPool(spaceShip, true);
        addInputListener(spaceShip);

    }


    @Override
    protected void recalc(float deltaTime) {
        super.recalc(deltaTime);
        int ships;

        ships = enemyShipPooler.getActiveSpritesList().size();

        //Если кораблей достаточно - больше не плодим
        if (ships >= MAX_SHIPS) return;

        //Вероятность появления корабля в секунду тем выше, чем меньше кораблей на экране
        if (Math.random() <= 1/60d && Math.random() <= (double) (MAX_SHIPS - ships) / (double) MAX_SHIPS){
            //Получаем случайный корабль
            float f = (float) Math.random();
            EnemySpaceShip ship;
            if (f <= 0.5f){
                ship = enemyShipPooler.poolShip(0);
            } else if (f<= 0.85f){
                ship = enemyShipPooler.poolShip(1);
            } else {
                ship = enemyShipPooler.poolShip(2);
            }
            ship.setActive(true);
            enemyShipPooler.addSpriteIntoActive(ship);
        }

    }

    @Override
    public void dispose() {
        super.dispose();
        shootSound.dispose();
        enemyShootSound.dispose();
    }






}
