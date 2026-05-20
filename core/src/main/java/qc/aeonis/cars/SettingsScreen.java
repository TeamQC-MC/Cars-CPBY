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

public class SettingsScreen extends ScreenAdapter {
    private final CarsGame game;
    private Stage stage;

    public SettingsScreen(CarsGame game) {
        this.game = game;
        // Apply GUI Scale to viewport
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

        table.add(new Label("SETTINGS", UISkin.get(), "title")).padBottom(50).row();

        // GUI Scale Option
        Table scaleTable = new Table();
        scaleTable.add(new Label("GUI SCALE: ", UISkin.get())).padRight(20);
        
        final Label scaleVal = new Label(String.format("%.1fx", Settings.guiScale), UISkin.get());
        scaleTable.add(scaleVal).width(100);
        
        TextButton plus = new TextButton("+", UISkin.get());
        plus.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Settings.guiScale = Math.min(2.0f, Settings.guiScale + 0.1f);
                scaleVal.setText(String.format("%.1fx", Settings.guiScale));
                Settings.save();
                game.setScreen(new SettingsScreen(game)); // Refresh viewport
            }
        });
        
        TextButton minus = new TextButton("-", UISkin.get());
        minus.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Settings.guiScale = Math.max(0.5f, Settings.guiScale - 0.1f);
                scaleVal.setText(String.format("%.1fx", Settings.guiScale));
                Settings.save();
                game.setScreen(new SettingsScreen(game)); // Refresh viewport
            }
        });

        scaleTable.add(minus).width(60).height(60).padRight(10);
        scaleTable.add(plus).width(60).height(60);
        table.add(scaleTable).padBottom(20).row();

        // Sound Toggle
        final TextButton soundBtn = new TextButton("SOUND: " + (Settings.soundEnabled ? "ON" : "OFF"), UISkin.get());
        soundBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Settings.soundEnabled = !Settings.soundEnabled;
                soundBtn.setText("SOUND: " + (Settings.soundEnabled ? "ON" : "OFF"));
                Settings.save();
            }
        });
        table.add(soundBtn).width(400).height(60).padBottom(20).row();

        // Language Toggle
        final TextButton langBtn = new TextButton("LANGUAGE: " + Settings.language.toUpperCase(), UISkin.get());
        langBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (Settings.language.equals("en")) Settings.language = "es";
                else Settings.language = "en";
                langBtn.setText("LANGUAGE: " + Settings.language.toUpperCase());
                Settings.save();
                // In a real port, reload assets or text here
            }
        });
        table.add(langBtn).width(400).height(60).padBottom(40).row();

        // Back Button
        TextButton backBtn = new TextButton("BACK TO MENU", UISkin.get());
        backBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MenuScreen(game));
            }
        });
        table.add(backBtn).width(400).height(80);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.05f, 0.15f, 0.05f, 1);
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
