package Main_menu_screen;

import static org.junit.Assert.*;

import angry.birds.game.Angry_ghosts;
import com.badlogic.gdx.graphics.Texture;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.ArrayList;
import com.badlogic.gdx.math.Vector2;

public class LevelStateTest {
    private level_1 level;
    private LevelState levelState;

    @Before
    public void setUp() {
        Angry_ghosts ag = new Angry_ghosts();
        level = new level_1(ag);
        levelState = new LevelState(level);
    }

    @Test
    public void testSaveToFile() {
        levelState.saveToFile("test_save.ser");
        LevelState loadedState = LevelState.loadFromFile("test_save.ser");
        assertNotNull(loadedState);
    }

    @Test
    public void testRestoreState() {
        Bird bird = new Bird(level.gameWorld.getWorld(), new Texture("bird.png"), new Vector2(0, 0), 0.5f);
        level.birds.add(bird);
        levelState = new LevelState(level);
        levelState.saveToFile("test_save.ser");
        LevelState loadedState = LevelState.loadFromFile("test_save.ser");

        level.birds.clear();
        loadedState.restoreState(level);
        assertEquals(1, level.birds.size());
    }
}
