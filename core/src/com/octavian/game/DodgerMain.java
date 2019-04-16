package com.octavian.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.octavian.game.config.Assets;
import com.octavian.game.windows.MainMenuScreen;

public class DodgerMain extends Game {

	private SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
		Assets.load();
		//settings = new Settings();

		//settings.load();
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
