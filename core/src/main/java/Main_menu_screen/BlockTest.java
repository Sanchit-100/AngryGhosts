package Main_menu_screen;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.graphics.Texture;

public class BlockTest {
    private Block block;
    private World world;
    private Texture texture;

    @Before
    public void setUp() {
        texture = new Texture("angry3.png");
        world = new World(new Vector2(0, -10), true);
        block = new Block(world,texture, new Vector2(0, 0), 1f, 1f);
    }

    @Test
    public void testBlockInitialization() {
        assertNotNull(block.getBody());
    }

    @Test
    public void testBlockCollisionProperties() {
        Fixture fixture = block.getBody().getFixtureList().first();
        assertEquals(0.5f, fixture.getDensity(), 0.01f);
        assertEquals(1f, fixture.getFriction(), 0.01f);
        assertEquals(0f, fixture.getRestitution(), 0.01f);
    }
}
