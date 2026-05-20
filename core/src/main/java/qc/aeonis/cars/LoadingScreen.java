package qc.aeonis.cars;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class LoadingScreen extends ScreenAdapter {
    private final CarsGame game;

    public LoadingScreen(CarsGame game) {
        this.game = game;
        Assets.load();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Assets.update()) {
            game.setScreen(new MenuScreen(game));
        }
        
        // Render loading bar or logo here
    }
}
