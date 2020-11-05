package com.vyazankin.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.vyazankin.game.base.BaseButton;
import com.vyazankin.game.base.BaseScreen;
import com.vyazankin.game.base.BaseShip;
import com.vyazankin.game.base.BaseSprite;
import com.vyazankin.game.math.Rect;
import com.vyazankin.game.sprite.Background;
import com.vyazankin.game.sprite.Bullet;
import com.vyazankin.game.sprite.GameOver;
import com.vyazankin.game.spritepools.BulletSpritePool;
import com.vyazankin.game.spritepools.EnemyShipPooler;
import com.vyazankin.game.sprite.EnemySpaceShip;
import com.vyazankin.game.spritepools.ExplosionSpritePool;
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
    private GameOver gameOverMessage;

    private BulletSpritePool bulletSpritePool;
    private ExplosionSpritePool explosionSpritePool;

    public GameScreen(Game game) {
        super(game);
    }

    private EnemyShipPooler enemyShipPooler;

    private BaseButton newGame;

    private int MAX_SHIPS = 15;

    Sound shootSound;
    Sound enemyShootSound;
    Sound explosionSound;

    private boolean started;

    @Override
    public void show() {
        super.show();

        shootSound =  Gdx.audio.newSound(Gdx.files.internal("resources/sounds/laser.wav"));
        enemyShootSound = Gdx.audio.newSound(Gdx.files.internal("resources/sounds/bullet.wav"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("resources/sounds/explosion.wav"));
        //Пул спрайтов - пуль
        bulletSpritePool = new BulletSpritePool();

        //Добавляем пул в список пулов. По ним происходит автоматическое итерирование и вызов необходимых методов
        addSpritePool(bulletSpritePool);



        menuAtlas = new TextureAtlas("resources/textures/menuAtlas.tpack");
        mainAtlas = new TextureAtlas("resources/textures/mainAtlas.tpack");

        background = new Background(new TextureRegion(new Texture("resources/textures/background.jpg")));

        //Пул по умолчанию
        addSpriteToDefaultPool(background, true);

        //Взрывы
        explosionSpritePool = new ExplosionSpritePool(mainAtlas, explosionSound);

        //Пуллер кораблей - класс, отвечающий за создание вражеских кораблей. Наследуется от BaseSpritePool
        enemyShipPooler = new EnemyShipPooler(mainAtlas, bulletSpritePool, explosionSpritePool, enemyShootSound);
        addSpritePool(enemyShipPooler);


        //Взрывы рисуются поверх кораблей, поэтому добавляем их последними
        addSpritePool(explosionSpritePool);

        //Пролетающие на заднем фоне звёзды находятся в пуле по умолчанию
        stars = new ArrayList<>(STARS_COUNT);
        for (int i = 0; i < STARS_COUNT; i++) {
            Star star;
            star = new Star(new TextureRegion(menuAtlas.findRegion("star")));
            stars.add(star);
            addSpriteToDefaultPool(star, true);
        }


        //Корабль игрока, находится в пуле по умолчанию
        spaceShip = new PlayerSpaceShip(mainAtlas, bulletSpritePool, explosionSpritePool, shootSound);

        addSpriteToDefaultPool(spaceShip, true);

        addInputListener(spaceShip);

        //Спрайт окончания игры
        gameOverMessage = new GameOver(mainAtlas.findRegion("message_game_over"));
        gameOverMessage.setHeightProportion(0.1f);


        //Новая игра
        newGame = new BaseButton(mainAtlas.findRegion("button_new_game")) {
            @Override
            public void action() {
                System.out.println("action");
                startNewGame();
            }

            @Override
            protected void worldResize(Rect bounds) {
                super.worldResize(bounds);
                newGame.setHeightProportion(0.1f);
                newGame.setBottom(worldBounds.getBottom() + newGame.getFullHeight() *2f );
            }
        };
        addInputListener(newGame);

        gameOver();



    }



    @Override
    protected void recalc(float deltaTime) {

        super.recalc(deltaTime);

        if (!started) return;

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
        explosionSound.dispose();
    }



    @Override
    protected boolean checks() {
        boolean retValue = false;
        //Корабли противника vs наш корабль
        for (EnemySpaceShip ship: enemyShipPooler.getActiveSpritesList()) {
            if (!ship.isActive()) continue;
            if(spaceShip.getCenterPosition().dst(ship.getCenterPosition()) < (Math.min(spaceShip.getHalfHeight(), ship.getHalfHeight()))){
                spaceShip.damage(ship.getShipHealth());
                ship.damage(ship.getShipHealth());
                retValue = true;
            }
            //Попадание пуль
            for (Bullet bullet: bulletSpritePool.getActiveSpritesList()){
                if (!bullet.isActive()) continue;
                BaseShip baseShip;

                //Пули игрока или противника
                if (bullet.isPlayerIsOwner()) {
                    baseShip = ship;
                } else {
                    baseShip = spaceShip;
                }

                if (baseShip.isVectorInside(bullet.getCenterPosition())) {
                    baseShip.damage(bullet.getDamage());
                    bullet.setActive(false);
                    retValue = true;
                }

            }
        }

        //Если игрок проиграл
        if (!spaceShip.isActive()){
            gameOver();
        }

        return retValue;
    }

    private void gameOver(){
        started = false;
        bulletSpritePool.forceInactive();
        enemyShipPooler.forceInactive();
        addSpriteToDefaultPool(gameOverMessage, true);
        addSpriteToDefaultPool(newGame, true);
        spaceShip.setActive(false);
    }

    private void startNewGame(){
        spaceShip.reset();
        spaceShip.setActive(true);
        addSpriteToDefaultPool(spaceShip, true);
        gameOverMessage.setActive(false);
        //newGame.setActive(false);
        started = true;



    }
}
