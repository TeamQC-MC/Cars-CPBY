package qc.aeonis.cars;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;

public class GameScreen extends ScreenAdapter {
    private final CarsGame game;
    private Viewport viewport;
    private OrthographicCamera camera;
    private TrackData currentTrack;
    private GameRenderer renderer;
    private CarEntity player;
    private CameraController cameraController;
    private PauseOverlay pauseOverlay;
    private Vector3 mousePos = new Vector3();

    public GameScreen(CarsGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false); // Native Y-UP
        viewport = new ExtendViewport(400, 240, camera);
        
        currentTrack = new TrackData(0);
        player = new CarEntity(0, (currentTrack.width * 10) / 2f, (currentTrack.height * 7) / 2f);
        renderer = new GameRenderer(game);
        cameraController = new CameraController(currentTrack.width * 10, currentTrack.height * 7);
        pauseOverlay = new PauseOverlay(game);
        
        SpriteManager.init();
        Gdx.input.setInputProcessor(new InputHandler());
    }

    @Override
    public void render(float delta) {
        if (pauseOverlay.isVisible()) {
            pauseOverlay.render(delta);
            return;
        }

        if (InputHandler.menu) {
            pauseOverlay.show();
            return;
        }

        // Handle Input
        if (Gdx.input.isTouched()) {
            mousePos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            viewport.unproject(mousePos);
            InputHandler.worldX = mousePos.x;
            InputHandler.worldY = mousePos.y;
        }

        player.update(delta);
        cameraController.update(delta);
        cameraController.apply(camera, player.x, player.y, viewport);

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        renderer.render(currentTrack, camera.position.x - viewport.getWorldWidth()/2, camera.position.y - viewport.getWorldHeight()/2, viewport);
        renderer.renderCar(player);
        
        // HUD
        BitmapFont font = FontManager.getFont();
        float hudX = camera.position.x - viewport.getWorldWidth()/2 + 10;
        float hudY = camera.position.y + viewport.getWorldHeight()/2 - 10;
        
        font.draw(game.batch, "POS: " + (int)player.x + ", " + (int)player.y, hudX, hudY);
        font.draw(game.batch, "SPD: " + (int)player.speed, hudX, hudY - 30);
        font.draw(game.batch, "ESC TO PAUSE", hudX, hudY - 60);
        
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        pauseOverlay.resize(width, height);
    }

    @Override
    public void hide() {
        // When resuming or switching, restore input
        Gdx.input.setInputProcessor(new InputHandler());
    }
}
