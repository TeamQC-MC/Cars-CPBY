package qc.aeonis.cars;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Gdx;

public class InputHandler extends InputAdapter {
    public static boolean up, down, left, right, fire, back, menu;
    public static int mouseX, mouseY;
    public static boolean isTouched;

    @Override
    public boolean keyDown(int keycode) {
        updateKey(keycode, true);
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        updateKey(keycode, false);
        return true;
    }

    private void updateKey(int keycode, boolean isPressed) {
        switch (keycode) {
            case Keys.UP:
            case Keys.W:
                up = isPressed;
                break;
            case Keys.DOWN:
            case Keys.S:
                down = isPressed;
                break;
            case Keys.LEFT:
            case Keys.A:
                left = isPressed;
                break;
            case Keys.RIGHT:
            case Keys.D:
                right = isPressed;
                break;
            case Keys.ENTER:
            case Keys.SPACE:
                fire = isPressed;
                break;
            case Keys.ESCAPE:
                menu = isPressed;
                break;
            case Keys.BACKSPACE:
            case Keys.DEL:
                back = isPressed;
                break;
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        isTouched = true;
        mouseX = screenX;
        mouseY = screenY;
        fire = true;
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        isTouched = false;
        fire = false;
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        mouseX = screenX;
        mouseY = screenY;
        return true;
    }
}
