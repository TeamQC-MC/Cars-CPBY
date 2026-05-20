package qc.aeonis.cars;

public class CarEntity {
    public float x, y;
    public float rotation;
    public float speed;
    public int type;
    
    // Original physics constants (approximated from decompiled code)
    private static final float MAX_SPEED = 200f;
    private static final float ACCELERATION = 500f;
    private static final float FRICTION = 0.95f;

    public CarEntity(int type, float x, float y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public void update(float delta) {
        // Keyboard Input
        if (InputHandler.up) {
            speed += ACCELERATION * delta;
        } else if (InputHandler.down) {
            speed -= ACCELERATION * delta;
        } else if (!InputHandler.isTouched) {
            speed *= FRICTION;
        }

        if (speed > MAX_SPEED) speed = MAX_SPEED;
        if (speed < -MAX_SPEED / 2) speed = -MAX_SPEED / 2;

        if (InputHandler.isTouched) {
            steerTowardsMouse(delta);
        } else {
            if (InputHandler.left) {
                rotation += 180f * delta;
            } else if (InputHandler.right) {
                rotation -= 180f * delta;
            }
        }

        // Move based on rotation
        x += Math.cos(Math.toRadians(rotation)) * speed * delta;
        y += Math.sin(Math.toRadians(rotation)) * speed * delta;
    }

    private void steerTowardsMouse(float delta) {
        // In GameScreen, we unproject mouse coords to world coords
        float targetX = InputHandler.worldX;
        float targetY = InputHandler.worldY;

        float angleToTarget = (float) Math.toDegrees(Math.atan2(targetY - y, targetX - x));
        
        // Normalize angles
        float diff = (angleToTarget - rotation + 180) % 360 - 180;
        if (diff < -180) diff += 360;

        float steerSpeed = 200f;
        if (Math.abs(diff) > 2) {
            rotation += Math.signum(diff) * steerSpeed * delta;
        }
        
        // Auto-gas when using mouse
        speed += ACCELERATION * 0.5f * delta;
    }
}
