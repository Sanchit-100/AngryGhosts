package Main_menu_screen;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class GameWorldTest {
    private GameWorld gameWorld;

    @Before
    public void setUp() {
        gameWorld = new GameWorld();
    }

    @Test
    public void testWorldInitialization() {
        assertNotNull(gameWorld.getWorld());
    }

    @Test
    public void testCreateScreenBoundaries() {
        World world = gameWorld.getWorld();
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody;
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 0.0f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 1.0f;
        EdgeShape edgeShape = new EdgeShape();
        edgeShape.set(0, 0, 100, 0);
        fixtureDef.shape = edgeShape;
        Body groundBody = world.createBody(groundBodyDef);
        groundBody.createFixture(fixtureDef);
        assertEquals(1, world.getBodyCount());
    }
}
