package com.yerimen.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.yerimen.YerimenGame;

import java.awt.*;

public class DesktopLauncher {
	public static void main (String[] arg) {
		new LwjglApplication(new YerimenGame(), gameConfiguration());
	}

	private static LwjglApplicationConfiguration gameConfiguration() {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.useGL30 = true;
        config.fullscreen = false;
        config.vSyncEnabled = true;
        config.title = "Yerimen";
        config.addIcon("../../desktop/images/icon.png", Files.FileType.Internal);
        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        config.width = (int) screenDimension.getWidth();
        config.height = (int) screenDimension.getHeight();
        return config;
    }
}
