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
            tileset = TextureRegion.split(tex, TILE_WIDTH, TILE_HEIGHT);
        }
    }

    public void render(TrackData track, float camX, float camY, Viewport viewport) {
        if (track == null || tileset == null) return;

        SpriteBatch batch = game.batch;
        
        int startX = (int)(camX / TILE_WIDTH) - 1;
        int startY = (int)(camY / TILE_HEIGHT) - 1;
        int endX = startX + (int)(viewport.getWorldWidth() / TILE_WIDTH) + 2;
        int endY = startY + (int)(viewport.getWorldHeight() / TILE_HEIGHT) + 2;

        for (int y = startY; y < endY; y++) {
            if (y < 0 || y >= track.height) continue;
            int mapRow = track.height - 1 - y;
            for (int x = startX; x < endX; x++) {
                if (x < 0 || x >= track.width) continue;
                int tileId = track.mapData[mapRow * track.width + x] & 0xFF;
                int tx = tileId % 16;
                int ty = tileId / 16;
                if (ty < tileset.length && tx < tileset[ty].length) {
                    batch.draw(tileset[ty][tx], x * TILE_WIDTH, y * TILE_HEIGHT);
                }
            }
        }
    }

    public void renderCar(CarEntity car) {
        // Map rotation (0-360, 0 is Right, CCW) to 16 frames (0-15, 0 is Right, CW)
        float angle = (car.rotation % 360 + 360) % 360;
        
        // Convert CCW to CW
        float cwAngle = (360 - angle) % 360;
        
        int frame = (int)(cwAngle / (360f / 16f));
        
        // car.type 0 uses base frame 309 (16 frames)
        renderSprite(309 + frame, car.x, car.y, 0);
    }

    public void renderSprite(int frameId, float x, float y, int transform) {
        byte[] data = SpriteManager.getFrameData(frameId);
        if (data == null) return;

        int srcX = data[0] & 0xFF;
        int srcY = data[1] & 0xFF;
        int width = data[2] & 0xFF;
        int height = data[3] & 0xFF;
        int offsetX = data[4]; // signed byte
        int offsetY = data[5]; // signed byte

        Texture tex = SpriteManager.getTexture(SpriteManager.getTextureId(frameId));
        if (tex == null) return;

        boolean flipX = (transform & 2) != 0;
        boolean flipY = (transform & 1) != 0;

        // Correct offset math for Y-UP
        // J2ME: top_left = anchor - offset
        // y_top_left = y - offsetY (in Y-DOWN)
        // y_bottom_left = y_top_left + height = y - offsetY + height (in Y-DOWN)
        
        // In Y-UP:
        // top_left_y is anchor_y + offsetY? 
        // Let's re-verify: if offsetY is 10, and anchor is at 100, top is at 110.
        // bottom is at 110 - height.
        float drawX = x - offsetX;
        float drawY = y + offsetY - height;

        game.batch.draw(tex, drawX, drawY, width, height, srcX, srcY, width, height, flipX, flipY);
    }
}
