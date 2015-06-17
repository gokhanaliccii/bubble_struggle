package com.galici.bubblegame;

import screen.GameScreen;
import screen.ScreenManager;
import utility.AssetManager;
import utility.ResourcePath;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame implements ApplicationListener {

	private SpriteBatch spriteBatch;
	private ScreenManager screenManager;

	Texture backgroundImage;

	@Override
	public void create() {

		screenManager = ScreenManager.getInstance();
		screenManager.setCurrentScreen(new GameScreen());
		screenManager.getCurrentScreen().create();

		spriteBatch = new SpriteBatch();
		backgroundImage = AssetManager.getInstance().getTexture(
				ResourcePath.BACKGROUND_IMAGE);
	}

	@Override
	public void render() {

		Gdx.graphics.getGL20().glClearColor(140 / 255f, 181 / 255f, 203 / 255f,
				0.3f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		spriteBatch.begin();
		spriteBatch.draw(backgroundImage, 0, 0, screenManager.getScreenWidth(),
				screenManager.getScreenHeight());
		spriteBatch.end();

		screenManager.getCurrentScreen().update();
		screenManager.getCurrentScreen().render(spriteBatch);

	}

	@Override
	public void resize(int width, int height) {

		screenManager.resize(width, height);
		screenManager.getCurrentScreen().resize(width, height);
	}

	@Override
	public void pause() {
		screenManager.getCurrentScreen().pause();
	}

	@Override
	public void resume() {
		screenManager.getCurrentScreen().resume();
	}

	@Override
	public void dispose() {

		backgroundImage.dispose();
		screenManager.getCurrentScreen().dispose();
	}
}
