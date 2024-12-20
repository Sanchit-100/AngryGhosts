package angry.birds.game;

import Main_menu_screen.main_menu;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Angry_ghosts extends Game {

    public SpriteBatch batch;
    private Viewport viewport;

    @Override
    public void create() {
        batch = new SpriteBatch();

        // Set the game to windowed mode with specific dimensions (e.g., 800x600)
        Gdx.graphics.setWindowedMode(2000, 1200);

        // Create a viewport that fits the window size
        viewport = new FitViewport(2000, 1200);

        this.setScreen(new main_menu(this));
    }

    @Override
    public void resize(int width, int height) {
        // Update the viewport to fit the new screen dimensions
        viewport.update(width, height, true);

        // Update the screen to match the new viewport
        this.getScreen().resize(width, height);
    }

    @Override
    public void render() {
        // Set the viewport for the batch
        batch.setProjectionMatrix(viewport.getCamera().combined);
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
