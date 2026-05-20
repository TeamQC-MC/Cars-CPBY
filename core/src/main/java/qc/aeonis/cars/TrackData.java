package qc.aeonis.cars;

import com.badlogic.gdx.Gdx;
import java.io.InputStream;
import java.io.IOException;

public class TrackData {
    public byte[] mapData;
    public int width;
    public int height;
    public byte[] waypointData;
    public int id;

    public TrackData(int id) {
        this.id = id;
        load(id + ".m2");
    }

    private void load(String filename) {
        Gdx.app.log("TrackData", "Loading " + filename);
        try (InputStream is = Gdx.files.internal(filename).read()) {
            byte[][] parts = parseBinary(is);
            if (parts != null && parts.length >= 3) {
                this.width = parts[0][0] & 0xFF;
                if (this.width == 0) {
                    Gdx.app.error("TrackData", "Width is 0 in " + filename);
                    return;
                }
                this.mapData = parts[1];
                this.height = mapData.length / width;
                this.waypointData = parts[2];
                Gdx.app.log("TrackData", "Loaded " + filename + ": " + width + "x" + height);
                
                // Adjust map data as per original logic if needed
                if (id != 3) {
                    applyMapFixes();
                }
            } else {
                Gdx.app.error("TrackData", "Invalid parts in " + filename + " (count: " + (parts != null ? parts.length : "null") + ")");
            }
        } catch (Exception e) {
            Gdx.app.error("TrackData", "Failed to load " + filename, e);
        }
    }

    private void applyMapFixes() {
        int i = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int tile = mapData[i] & 0xFF;
                if (tile == 40 || tile == 76 || tile == 102) {
                    mapData[i] = (byte)(tile | (x % 2) | ((y % 2) << 4));
                }
                i++;
            }
        }
    }

    // Re-implementation of the custom binary format parser
    private byte[][] parseBinary(InputStream is) throws IOException {
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

    private int readVarInt(InputStream is) throws IOException {
        int val = 0;
        int b = 128;
        while (b > 127) {
            b = is.read();
            if (b == -1) return val;
            val = (val << 7) | (b & 0x7F);
        }
        return val;
    }
}
