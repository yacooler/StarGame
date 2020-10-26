package com.vyazankin.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.vyazankin.game.base.BaseScreen;
import com.vyazankin.game.base.BaseSprite;
import com.vyazankin.game.math.Rect;
import com.vyazankin.game.sprite.Background;
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



    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

        menuAtlas = new TextureAtlas("images/textures/menuAtlas.tpack");
        mainAtlas = new TextureAtlas("images/textures/mainAtlas.tpack");

        background = new Background(new TextureRegion(new Texture("images/textures/background.jpg")));

        addSprite(background);

        stars = new ArrayList<>(STARS_COUNT);
        for (int i = 0; i < STARS_COUNT; i++) {
            Star star;
            star = new Star(new TextureRegion(menuAtlas.findRegion("star")));
            stars.add(star);
            addSprite(star);
        }


        TextureRegion playerShipRegion = mainAtlas.findRegion("main_ship");
        playerShipRegion.setRegion(playerShipRegion.getRegionX(),playerShipRegion.getRegionY(), playerShipRegion.getRegionWidth()/2, playerShipRegion.getRegionHeight());
        spaceShip = new PlayerSpaceShip(playerShipRegion);

        addSprite(spaceShip);
        addTouchListener(spaceShip);
    }


    @Override
    public void dispose() {
        super.dispose();
    }




}
