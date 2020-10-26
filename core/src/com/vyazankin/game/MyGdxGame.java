package com.vyazankin.game;

import com.badlogic.gdx.Game;
import com.vyazankin.game.screen.MenuScreen;

public class MyGdxGame extends Game {

	@Override
	public void create() {
		setScreen(new MenuScreen(this));
	}
}
