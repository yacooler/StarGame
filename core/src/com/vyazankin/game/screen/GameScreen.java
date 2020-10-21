package com.vyazankin.game.screen;

import com.vyazankin.game.base.BaseScreen;

public class GameScreen extends BaseScreen {


    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        batch.begin();

        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return super.touchDown(screenX, screenY, pointer, button);
    }


}
