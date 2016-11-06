package com.hard.core.parkour.desktop;


import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.hard.core.parkour.Game;
import static com.hard.core.parkour.handlers.B2DVars.DESKTOP_SIZE_MULTIPLYER;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = Game.TITLE;
		config.width = Game.V_WIDTH * DESKTOP_SIZE_MULTIPLYER;
		config.height= Game.V_HEIGHT * DESKTOP_SIZE_MULTIPLYER;

		new LwjglApplication(new Game(), config);
	}
}
