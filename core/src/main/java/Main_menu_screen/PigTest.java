package Main_menu_screen;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.graphics.Texture;

public class PigTest {
    private Pig pig;
    private World world;

    @Before
    public void setUp() {
        world = new World(new Vector2(0, -10), true);
        pig = new Pig_1(world, 0, 0);
    }

    @Test
    public void testIncrementHits() {
        pig.incrementHits();
        assertEquals(1, pig.getHits());
    }

    @Test
    public void testIsDestroyed() {
        for (int i = 0; i < pig.MAX_HITS; i++) {
            pig.incrementHits();
        }
        assertTrue(pig.isDestroyed());
    }
}
