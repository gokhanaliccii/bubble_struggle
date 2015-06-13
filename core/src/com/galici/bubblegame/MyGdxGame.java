package com.galici.bubblegame;

import screen.GameScreen;
import screen.ScreenManager;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MyGdxGame implements ApplicationListener {

	SpriteBatch spriteBatch;
	ScreenManager screenManager;

	OrthographicCamera camera;

	@Override
	public void create() {

		screenManager = ScreenManager.getInstance();
		screenManager.setCurrentScreen(new GameScreen());
		screenManager.getCurrentScreen().create();

		spriteBatch = new SpriteBatch();
		camera = new OrthographicCamera();
	}

	@Override
	public void render() {

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		screenManager.getCurrentScreen().update();
		screenManager.getCurrentScreen().render(spriteBatch);

	}

	@Override
	public void resize(int width, int height) {
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
		screenManager.getCurrentScreen().dispose();
	}
}
