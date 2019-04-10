package com.octavian.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.octavian.game.windows.MainMenuScreen;

public class DodgerMain extends Game {

	private SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
		//settings = new Settings();
		//assets = new Assets();

		//settings.load();
		//assets.load();
		setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	public SpriteBatch getBatch() {
		return this.batch;
	}

}
