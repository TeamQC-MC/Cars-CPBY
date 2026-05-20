package qc.aeonis.cars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class CameraController {
    private float shakeAmount = 0;
    private float shakeTime = 0;
    private Vector2 shakeOffset = new Vector2();
    private float worldWidth, worldHeight;

    public CameraController(float worldWidth, float worldHeight) {
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
    }

    public void update(float delta) {
        if (shakeTime > 0) {
            shakeTime -= delta;
            shakeOffset.set(MathUtils.random(-shakeAmount, shakeAmount), MathUtils.random(-shakeAmount, shakeAmount));
        } else {
            shakeOffset.set(0, 0);
        }
    }

    public void shake(float amount, float time) {
        this.shakeAmount = amount;
        this.shakeTime = time;
    }

    public void apply(com.badlogic.gdx.graphics.OrthographicCamera camera, float targetX, float targetY, com.badlogic.gdx.utils.viewport.Viewport viewport) {
        float viewW = viewport.getWorldWidth();
        float viewH = viewport.getWorldHeight();

        // Strict Clamping to borders
        float x = MathUtils.clamp(targetX, viewW / 2f, worldWidth - viewW / 2f);
        float y = MathUtils.clamp(targetY, viewH / 2f, worldHeight - viewH / 2f);

        // World might be smaller than viewport
        if (worldWidth < viewW) x = worldWidth / 2f;
        if (worldHeight < viewH) y = worldHeight / 2f;

        camera.position.set(x + shakeOffset.x, y + shakeOffset.y, 0);
        camera.update();
    }
}
