package com.lee.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.lee.game.Basket;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//config.height=Basket.HEIGHT;
		//config.width=Basket.WIDTH;
		config.width = 800;
		config.height = 1200;
		new LwjglApplication(new Basket(), config);
	}
}
