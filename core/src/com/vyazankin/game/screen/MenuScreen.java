package com.vyazankin.game.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.vyazankin.game.base.BaseScreen;
import com.vyazankin.game.math.Rect;
import com.vyazankin.game.sprite.Background;

public class MenuScreen extends BaseScreen {

    private Background background;

    @Override
    public void show() {
        super.show();
        background = new Background(new TextureRegion(new Texture("background.jpg")));
        addTouchListener(background);
    }

    @Override
    public void worldResize(Rect bounds) {
        System.out.println("worldResize" + bounds);
        background.worldResize(bounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        background.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return super.touchDown(screenX, screenY, pointer, button);
    }
}
