package qc.aeonis.cars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class PauseOverlay {
    private Stage stage;
    private boolean visible = false;
    private CarsGame game;

    public PauseOverlay(CarsGame game) {
        this.game = game;
        this.stage = new Stage(new ExtendViewport(1280, 720));
        
        Table table = new Table();
        table.setFillParent(true);
        table.setBackground(UISkin.get().newDrawable("bg", new Color(0, 0, 0, 0.6f)));
        stage.addActor(table);

        table.add(new Label("PAUSED", UISkin.get(), "title")).padBottom(40).row();

        TextButton resumeBtn = new TextButton("RESUME", UISkin.get());
        resumeBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                hide();
            }
        });
        table.add(resumeBtn).padBottom(20).width(300).height(60).row();

        TextButton menuBtn = new TextButton("QUIT TO MENU", UISkin.get());
        menuBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MenuScreen(game));
            }
        });
        table.add(menuBtn).width(300).height(60);
    }

    public void show() { 
        visible = true;
        Gdx.input.setInputProcessor(stage);
    }
    
    public void hide() { 
        visible = false; 
        // Caller must restore input processor
    }

    public boolean isVisible() { return visible; }

    public void render(float delta) {
        if (!visible) return;
        stage.act(delta);
        stage.draw();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void dispose() {
        stage.dispose();
    }
}
