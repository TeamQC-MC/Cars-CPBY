package qc.aeonis.cars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import java.io.InputStream;
import java.io.IOException;
import java.util.HashMap;

public class SpriteManager {
    private static byte[] frameCoords;
    private static HashMap<Integer, Texture> textureCache = new HashMap<>();

    public static void init() {
        try (InputStream is = Gdx.files.internal("coords.bin").read()) {
            byte[][] parts = parseBinary(is);
            if (parts != null && parts.length > 0) {
                frameCoords = parts[0];
            }
        } catch (Exception e) {
            Gdx.app.error("SpriteManager", "Failed to load coords.bin", e);
        }
    }

    public static Texture getTexture(int id) {
        if (textureCache.containsKey(id)) return textureCache.get(id);
        
        String filename = id + ".png";
        Texture tex = Assets.get(filename, Texture.class);
        if (tex != null) {
            textureCache.put(id, tex);
            return tex;
        }
        
        filename = id + ".img";
        if (Gdx.files.internal(filename).exists()) {
            try (InputStream is = Gdx.files.internal(filename).read()) {
                byte[][] parts = parseBinary(is);
                if (parts != null && parts.length > 0) {
                    Pixmap pm = new Pixmap(parts[0], 0, parts[0].length);
                    tex = new Texture(pm);
                    pm.dispose();
                    tex.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
                    textureCache.put(id, tex);
                    return tex;
                }
            } catch (Exception e) {
                Gdx.app.error("SpriteManager", "Failed to load " + filename, e);
            }
        }
        return null;
    }

    public static byte[] getFrameData(int frameId) {
        if (frameCoords == null) return null;
        int offset = frameId * 6;
        if (offset + 5 >= frameCoords.length) return null;
        
        return new byte[] {
            frameCoords[offset],
            frameCoords[offset+1],
            frameCoords[offset+2],
            frameCoords[offset+3],
            frameCoords[offset+4],
            frameCoords[offset+5]
        };
    }
    
    public static int getTextureId(int frameId) {
        // User confirmed 12.png has every movement tile.
        // Let's force all frames in car range to 12.
        if (frameId >= 301 && frameId <= 356) return 12;
        
        // Ported logic from Sprite.method_307
        if (frameId < 21) return 1;
        if (frameId < 32) return 27;
        if (frameId < 58) return 11;
        if (frameId < 72) return 27;
        if (frameId < 80) return 11;
        if (frameId < 87) return 0;
        if (frameId < 264) return 11;
        if (frameId < 268) return 8;
        if (frameId < 275) return 31;
        if (frameId < 284) return 21;
        if (frameId < 292) return 22;
        if (frameId < 293) return 23;
        if (frameId < 301) return 13;
        if (frameId < 309) return 17;
        if (frameId < 325) return 12;
        if (frameId < 357) return 2;
        return frameId < 373 ? 29 : 30;
    }

    private static byte[][] parseBinary(InputStream is) throws IOException {
        int numParts = readVarInt(is);
        if (numParts <= 0) return null;
        
        byte[][] parts = new byte[numParts][];
        for (int i = 0; i < numParts; i++) {
            int len = readVarInt(is);
            parts[i] = new byte[len];
            int read = 0;
            while (read < len) {
                int r = is.read(parts[i], read, len - read);
                if (r == -1) break;
                read += r;
            }
        }
        return parts;
    }

    private static int readVarInt(InputStream is) throws IOException {
        int val = 0;
        int b = 128;
        while (b > 127) {
            b = is.read();
            if (b == -1) return val;
            val = (val << 7) | (b & 0x7F);
        }
        return val;
    }
    
    public static void dispose() {
        for (Texture tex : textureCache.values()) {
            tex.dispose();
        }
        textureCache.clear();
    }
}
