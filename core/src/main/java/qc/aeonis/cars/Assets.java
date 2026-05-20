package qc.aeonis.cars;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;

public class Assets {
    private static AssetManager manager = new AssetManager();

    public static void load() {
        String[] textures = {
            "0.png", "1.png", "11.png", "12.png", "2.png", "21.png", 
            "23.png", "27.png", "28.png", "3.png", "30.png", "31.png", 
            "32.png", "33__en.png", "34.png", "8.png", "9.png", 
            "capy.png", "font1.png", "font2.png", "i.png", "splash.png"
        };
        
        for (String tex : textures) {
            manager.load(tex, Texture.class);
        }
    }

    public static float getProgress() {
        return manager.getProgress();
    }

    public static boolean update() {
        boolean loaded = manager.update();
        if (loaded) {
            // Force Nearest filtering for all textures once loaded to fix blurriness
            com.badlogic.gdx.utils.Array<String> assetNames = manager.getAssetNames();
            for (String name : assetNames) {
                if (manager.getAssetType(name) == Texture.class) {
                    Texture tex = manager.get(name, Texture.class);
                    tex.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
                }
            }
        }
        return loaded;
    }

    public static <T> T get(String name, Class<T> type) {
        if (manager.isLoaded(name, type)) {
            return manager.get(name, type);
        }
        return null;
    }

    public static void dispose() {
        manager.dispose();
    }
}
