package com.vyazankin.game.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.vyazankin.game.base.BaseScreen;
import com.vyazankin.game.math.Rect;
import com.vyazankin.game.sprite.Background;
import com.vyazankin.game.sprite.Moon;

public class MenuScreen extends BaseScreen {

    private Background background;
    private Moon moon;
    @Override
    public void show() {
        super.show();
        background = new Background(new TextureRegion(new Texture("images/background.jpg")));
        moon = new Moon(new TextureRegion(new Texture("images/moon.png")));
        moon.setHeightProportion(0.1f);
        addTouchListener(moon);
    }

    @Override
    public void worldResize(Rect bounds) {
        System.out.println("worldResize" + bounds);
        background.worldResize(bounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        moon.recalc(delta);

        batch.begin();

        background.draw(batch);
        moon.draw(batch);

        batch.end();
    }

    @Override
    public void dispose() {
        background.dispose();

        removeTouchListener(moon);
        moon.dispose();

        super.dispose();
    }

}
