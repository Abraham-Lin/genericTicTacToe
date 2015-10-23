package me.squaretictactoe.genericTicTacToe.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import me.squaretictactoe.genericTicTacToe.menuScreen;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new menuScreen(), config);
		//new LwjglApplication(new appEntry(), config);
	}
}
