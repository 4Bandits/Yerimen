package com.yerimen.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.yerimen.YerimenGame;

import java.awt.*;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        config.width = (int) screenDimension.getWidth();
        config.height = (int) screenDimension.getHeight();
        config.useGL30 = true;
        config.fullscreen = false;
        config.vSyncEnabled = true;
		new LwjglApplication(new YerimenGame(), config);
	}
}
