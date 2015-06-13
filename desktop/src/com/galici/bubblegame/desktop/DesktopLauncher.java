package com.galici.bubblegame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.galici.bubblegame.MyGdxGame;

public class DesktopLauncher {
	
	public static int WIDTH = 800;
	public static int HEIGHT = 480;
	public static String TITLE ="Bubble Struggle";
	
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width=WIDTH;
		config.height=HEIGHT;
		config.title=TITLE;
		
		new LwjglApplication(new MyGdxGame(), config);
	}
}
