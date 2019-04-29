package com.octavian.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.octavian.game.config.Assets;
import com.octavian.game.screen.MainMenuScreen;

public class DodgerMain extends Game {

	@Override
	public void create () {
		Assets.load();
		setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
}
