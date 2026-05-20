package qc.aeonis.cars;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class MenuScreen extends ScreenAdapter {
    private final CarsGame game;
    private Stage stage;

    public MenuScreen(CarsGame game) {
        this.game = game;
        // Apply GUI Scale
        float baseWidth = 1280 / Settings.guiScale;
        float baseHeight = 720 / Settings.guiScale;
        this.stage = new Stage(new ExtendViewport(baseWidth, baseHeight));
        Gdx.input.setInputProcessor(stage);
        createUI();
    }

    private void createUI() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label title = new Label("CARS REMASTERED", UISkin.get(), "title");
        title.setFontScale(2.0f);
        table.add(title).padBottom(60).row();

        TextButton playButton = new TextButton("START RACE", UISkin.get());
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new GameScreen(game));
            }
        });
        table.add(playButton).padBottom(20).width(400).height(80).row();

        TextButton settingsButton = new TextButton("SETTINGS", UISkin.get());
        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new SettingsScreen(game));
            }
        });
        table.add(settingsButton).padBottom(20).width(400).height(80).row();

        TextButton exitButton = new TextButton("EXIT GAME", UISkin.get());
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
        table.add(exitButton).width(400).height(80);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.05f, 0.05f, 0.15f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
