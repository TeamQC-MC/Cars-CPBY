package qc.aeonis.cars;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class UISkin {
    private static Skin skin;

    public static Skin get() {
        if (skin == null) {
            skin = new Skin();
            
            // Create a basic background texture
            Pixmap pixmap = new Pixmap(100, 100, Pixmap.Format.RGBA8888);
            pixmap.setColor(new Color(0.2f, 0.2f, 0.8f, 0.8f));
            pixmap.fill();
            Texture bg = new Texture(pixmap);
            skin.add("bg", bg);
            
            pixmap.setColor(new Color(0.1f, 0.1f, 0.5f, 1.0f));
            pixmap.fill();
            Texture down = new Texture(pixmap);
            skin.add("down", down);

            pixmap.dispose();

            TextButton.TextButtonStyle btnStyle = new TextButton.TextButtonStyle();
            btnStyle.font = FontManager.getFont();
            btnStyle.up = skin.newDrawable("bg", Color.CYAN);
            btnStyle.down = skin.newDrawable("down", Color.WHITE);
            btnStyle.over = skin.newDrawable("bg", Color.WHITE);
            skin.add("default", btnStyle);

            Label.LabelStyle lblStyle = new Label.LabelStyle();
            lblStyle.font = FontManager.getFont();
            lblStyle.fontColor = Color.YELLOW;
            skin.add("default", lblStyle);
            
            Label.LabelStyle titleStyle = new Label.LabelStyle();
            titleStyle.font = FontManager.getFont();
            titleStyle.fontColor = Color.ORANGE;
            skin.add("title", titleStyle);
        }
        return skin;
    }
}
