package qc.aeonis.cars;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.setTitle("Cars Remaster");
        config.setWindowedMode(800, 600); // Larger default window for landscape
        new Lwjgl3Application(new CarsGame(), config);
    }
}
