package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;


public class DesktopLauncher {
   public static void main (String[] arg) {
      Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
      config.setTitle("Rogue");
      config.setWindowedMode(1200, 800);
      config.useVsync(true);
      config.setForegroundFPS(60);
      new Lwjgl3Application(new MyGame(), config);
   }
}
