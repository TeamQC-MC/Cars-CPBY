package qc.aeonis.cars;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen extends ScreenAdapter {
    private final CarsGame game;
    private Viewport viewport;
    private OrthographicCamera camera;
    private TrackData currentTrack;
    private GameRenderer renderer;
    private CarEntity player;

    public GameScreen(CarsGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(true); // Y-down
        viewport = new ExtendViewport(240, 320, camera); // Original J2ME res
        
        currentTrack = new TrackData(0);
        player = new CarEntity(0, 120, 160);
        renderer = new GameRenderer(game);
        
        SpriteManager.init();
        Gdx.input.setInputProcessor(new InputHandler());
    }

    @Override
    public void render(float delta) {
        player.update(delta);
        camera.position.set(player.x, player.y, 0);
        camera.update();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        renderer.render(currentTrack, camera.position.x - viewport.getWorldWidth()/2, camera.position.y - viewport.getWorldHeight()/2, viewport);
        
        // Draw car (frame 0 for now as test)
        renderer.renderSprite(0, player.x, player.y, 0);
        
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
}
