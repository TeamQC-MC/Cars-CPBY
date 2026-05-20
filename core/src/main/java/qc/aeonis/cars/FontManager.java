package qc.aeonis.cars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import java.io.File;

public class FontManager {
    public enum FontType { BITMAP, MODERN_BOLD }
    
    private static BitmapFont bitmapFont;
    private static BitmapFont modernBoldFont;
    private static FontType activeType = FontType.MODERN_BOLD;

    public static void init() {
        // LibGDX default font is Y-down (0,0 top left) by default in constructor?
        // Actually, BitmapFont(boolean flip)
        // If flip is false, (0,0) is bottom left.
        bitmapFont = new BitmapFont(false); 
        bitmapFont.getData().setScale(1.0f);
        
        String[] possiblePaths = {
            "modern.ttf",
            "/usr/share/fonts/truetype/dejavu/DejaVuSans-Bold.ttf",
            "/usr/share/fonts/TTF/DejaVuSans-Bold.ttf",
            "/usr/share/fonts/liberation/LiberationSans-Bold.ttf",
            "/usr/share/fonts/adwaita-mono-fonts/AdwaitaMono-Bold.ttf"
        };

        for (String path : possiblePaths) {
            try {
                com.badlogic.gdx.files.FileHandle fontFile;
                if (new File(path).isAbsolute()) {
                    fontFile = Gdx.files.absolute(path);
                } else {
                    fontFile = Gdx.files.internal(path);
                }

                if (fontFile.exists()) {
                    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
                    FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
                    parameter.size = 32; // Crisp HD size
                    parameter.flip = false; // Y-UP
                    parameter.borderWidth = 2f;
                    parameter.borderColor = Color.BLACK;
                    parameter.color = Color.WHITE;
                    // Use Nearest filtering for the font texture if needed, but linear is often fine for TTF
                    modernBoldFont = generator.generateFont(parameter);
                    generator.dispose();
                    Gdx.app.log("FontManager", "Loaded HD font: " + path);
                    break;
                }
            } catch (Exception e) { }
        }

        if (modernBoldFont == null) {
            modernBoldFont = new BitmapFont(false); 
            modernBoldFont.getData().setScale(1.5f);
        }
    }

    public static void setFontType(FontType type) {
        activeType = type;
    }

    public static BitmapFont getFont() {
        return (activeType == FontType.BITMAP) ? bitmapFont : modernBoldFont;
    }

    public static BitmapFont getFont(FontType type) {
        return (type == FontType.BITMAP) ? bitmapFont : modernBoldFont;
    }

    public static void dispose() {
        if (bitmapFont != null) bitmapFont.dispose();
        if (modernBoldFont != null) modernBoldFont.dispose();
    }
}
