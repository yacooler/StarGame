package com.vyazankin.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.vyazankin.game.base.BaseButton;
import com.vyazankin.game.base.BaseScreen;
import com.vyazankin.game.base.BaseSprite;
import com.vyazankin.game.math.Rect;
import com.vyazankin.game.sprite.Background;
import com.vyazankin.game.sprite.Star;

import java.util.ArrayList;
import java.util.List;

public class MenuScreen extends BaseScreen {

    private Background background;

    private BaseButton playButton;
    private BaseButton closeButton;
    private List<BaseSprite> stars;

    private TextureAtlas menuAtlas;
    private final int STARS_COUNT = 1256;


    public MenuScreen(Game game) {
        super(game);

    }

    @Override
    public void show() {
        super.show();

        menuAtlas = new TextureAtlas("resources/textures/menuAtlas.tpack");

        background = new Background(new TextureRegion(new Texture("resources/textures/background.jpg")));


        closeButton = new BaseButton(new TextureRegion(menuAtlas.findRegion("btExit"))) {
            @Override
            public void action() {
                Gdx.app.exit();
            };
        };


        playButton = new BaseButton(new TextureRegion(menuAtlas.findRegion("btPlay"))) {
            @Override
            public void action() {
                dispose();
                getGame().setScreen(new GameScreen(getGame()));
            };
        };

        //Спрайты отрисовываются в последовательности добавления!
        addSpriteToDefaultPool(background, true);

        stars = new ArrayList<>(STARS_COUNT);
        for (int i = 0; i < STARS_COUNT; i++) {
            Star star;
            star = new Star(new TextureRegion(menuAtlas.findRegion("star")));
            stars.add(star);
            addSpriteToDefaultPool(star, true);
        }

        addInputListener(closeButton);
        addInputListener(playButton);


        //Отрисовываются в последовательности добавления!
        addSpriteToDefaultPool(closeButton, true);
        addSpriteToDefaultPool(playButton, true);

    }


    @Override
    public void worldResize(Rect bounds) {
        super.worldResize(bounds);

        closeButton.setHeightProportion(0.2f);
        closeButton.setRight(bounds.getRight() - 0.02f);
        closeButton.setBottom(bounds.getBottom() + 0.02f);

        playButton.setHeightProportion(0.26f);
        playButton.setLeft(bounds.getLeft() + 0.02f);
        playButton.setBottom(bounds.getBottom() + 0.02f);
    }

    @Override
    public void dispose() {
        menuAtlas.dispose();
        super.dispose();
    }

}
