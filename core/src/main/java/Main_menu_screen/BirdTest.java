package Main_menu_screen;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.graphics.Texture;

public class BirdTest {
    private Bird bird;
    private World world;

    @Before
    public void setUp() {
        world = new World(new Vector2(0, -10), true);
        bird = new Bird(world, new Texture("bird.png"), new Vector2(0, 0), 0.5f);
    }

    @Test
    public void testLaunch() {
        Vector2 launchVector = new Vector2(1, 1);
        bird.launch(launchVector, 10f);
        assertTrue(bird.isLaunched());
        assertNotEquals(new Vector2(0, 0), bird.getVelocity());
    }

    @Test
    public void testReset() {
        bird.launch(new Vector2(1, 1), 10f);
        bird.reset();
        assertFalse(bird.isLaunched());
        assertEquals(new Vector2(0, 0), bird.getVelocity());
    }

    @Test
    public void testIsOutOfBounds() {
        bird.getBody().setTransform(new Vector2(1000, 1000), 0);
        assertTrue(bird.isOutOfBounds(800, 600));
    }
}
