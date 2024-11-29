package Main_menu_screen;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class GameContactListenerTest {
    private GameWorld gameWorld;
    private GameLevel level;
    private GameContactListener listener;

    @Before
    public void setUp() {
        gameWorld = new GameWorld();
//        level = new GameLevel();
        listener = new GameContactListener(level);
    }

    @Test
    public void testBeginContact_PigCollision() {
        // Create fixtures and contact for test
        Pig pig = new Pig_1(gameWorld.getWorld(), 0, 0);
        Bird bird = new Bird(gameWorld.getWorld(), new Texture("bird.png"), new Vector2(0, 0), 0.5f);
        Fixture pigFixture = pig.getBody().getFixtureList().first();
        Fixture birdFixture = bird.getBody().getFixtureList().first();

        // Use Mockito to create a mock Contact object
        Contact contact = Mockito.mock(Contact.class);
        Mockito.when(contact.getFixtureA()).thenReturn(pigFixture);
        Mockito.when(contact.getFixtureB()).thenReturn(birdFixture);

        // Simulate beginContact
        listener.beginContact(contact);

        // Check if the pig's hit count is incremented
        assertEquals(1, pig.getHits());
    }
}
