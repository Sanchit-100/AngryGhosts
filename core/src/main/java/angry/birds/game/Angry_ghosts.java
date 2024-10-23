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

        // Get the display mode of the primary monitor
        DisplayMode displayMode = Gdx.graphics.getDisplayMode();

        // Set the game to fullscreen
        Gdx.graphics.setFullscreenMode(displayMode);

        // Create a viewport that fits the screen size
        viewport = new FitViewport(displayMode.width, displayMode.height);

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
