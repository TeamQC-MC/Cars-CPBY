package qc.aeonis.cars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Settings {
    private static Preferences prefs;
    
    public static float guiScale = 1.0f;
    public static boolean soundEnabled = true;
    public static boolean musicEnabled = true;
    public static String language = "en";

    public static void load() {
        prefs = Gdx.app.getPreferences("qc.aeonis.cars.settings");
        guiScale = prefs.getFloat("guiScale", 1.0f);
        soundEnabled = prefs.getBoolean("soundEnabled", true);
        musicEnabled = prefs.getBoolean("musicEnabled", true);
        language = prefs.getString("language", "en");
    }

    public static void save() {
        prefs.putFloat("guiScale", guiScale);
        prefs.putBoolean("soundEnabled", soundEnabled);
        prefs.putBoolean("musicEnabled", musicEnabled);
        prefs.putString("language", language);
        prefs.flush();
    }
}
