package Main_menu_screen;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;

public class GameWorld {
    private World world;

    public GameWorld() {
        // Initialize Box2D
        Box2D.init();

        // Create a new world with gravity
        Vector2 gravity = new Vector2(0, -10f); // Gravity pointing down
        world = new World(gravity, true);
    }

    public World getWorld() {
        return world;
    }

    // Dispose of the world when it's no longer needed
    public void dispose() {
        world.dispose();
    }
}
