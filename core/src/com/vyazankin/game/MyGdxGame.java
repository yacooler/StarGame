package com.vyazankin.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.vyazankin.game.screen.MenuScreen;

public class MyGdxGame extends Game {

	Music music;

	@Override
	public void create() {
		setScreen(new MenuScreen(this));
		music = Gdx.audio.newMusic(Gdx.files.internal("resources/sounds/music.mp3"));
		music.play();
	}

	@Override
	public void dispose() {
		music.dispose();
		super.dispose();
	}
}
