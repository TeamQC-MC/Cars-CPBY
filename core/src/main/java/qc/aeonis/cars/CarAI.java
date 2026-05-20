package qc.aeonis.cars;

import com.badlogic.gdx.math.Vector2;

public class CarAI {
    private CarEntity car;
    private TrackData track;
    private int currentWaypoint = 0;

    public CarAI(CarEntity car, TrackData track) {
        this.car = car;
        this.track = track;
    }

    public void update(float delta) {
        // Simple AI: move towards next waypoint
        // In the original, waypoint data is in Track.field_253
        
        // Placeholder AI logic
        car.speed = 100f; // Constant speed for AI
        car.rotation += 10f * delta; // Constant circle for testing
    }
}
