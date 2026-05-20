package qc.aeonis.cars;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameRenderer {
    private final CarsGame game;
    private TextureRegion[][] tileset;
    private static final int TILE_WIDTH = 10;
    private static final int TILE_HEIGHT = 7;

    public GameRenderer(CarsGame game) {
        this.game = game;
        loadTileset();
    }

    private void loadTileset() {
        Texture tex = Assets.get("9.png", Texture.class);
        if (tex != null) {
            // 9.png is likely a grid of tiles. 
            // The J2ME code uses 'var2 % 16' and 'var2 / 16' in method_141,
            // implying a 16-column grid.
            tileset = TextureRegion.split(tex, TILE_WIDTH, TILE_HEIGHT);
        }
    }

    public void render(TrackData track, float camX, float camY, Viewport viewport) {
        if (track == null || tileset == null) return;

        SpriteBatch batch = game.batch;
        
        // Calculate visible tiles
        int startX = (int)(camX / TILE_WIDTH) - 1;
        int startY = (int)(camY / TILE_HEIGHT) - 1;
        int endX = startX + (int)(viewport.getWorldWidth() / TILE_WIDTH) + 2;
        int endY = startY + (int)(viewport.getWorldHeight() / TILE_HEIGHT) + 2;

        for (int y = startY; y < endY; y++) {
            for (int x = startX; x < endX; x++) {
                int tileId = getTile(track, x, y);
                int tx = tileId % 16;
                int ty = tileId / 16;
                
                if (ty < tileset.length && tx < tileset[ty].length) {
                    batch.draw(tileset[ty][tx], x * TILE_WIDTH, y * TILE_HEIGHT);
                }
            }
        }
    }
public void renderSprite(int frameId, float x, float y, int transform) {
    byte[] data = SpriteManager.getFrameData(frameId);
    if (data == null) return;

    int srcX = data[0] & 0xFF;
    int srcY = data[1] & 0xFF;
    int width = data[2] & 0xFF;
    int height = data[3] & 0xFF;
    int offsetX = data[4];
    int offsetY = data[5];

    Texture tex = SpriteManager.getTexture(SpriteManager.getTextureId(frameId));
    if (tex == null) return;

    boolean flipX = (transform & 2) != 0;
    boolean flipY = (transform & 1) != 0;

    float drawX = x - (flipX ? 0 : offsetX);
    float drawY = y - (flipY ? 0 : offsetY);

    game.batch.draw(tex, drawX, drawY, width, height, srcX, srcY, width, height, flipX, flipY);
}

private int getTile(TrackData track, int x, int y) {
    if (x < 0 || x >= track.width || y < 0 || y >= track.height) {
        return 3; // Default background tile
    }
    return track.mapData[y * track.width + x] & 0xFF;
}
}

