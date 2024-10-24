package Main_menu_screen;

import angry.birds.game.Angry_ghosts;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.math.Vector3;

public class profile_menu implements Screen {
    Angry_ghosts ag;

    private Texture main_bg;
    private Texture second_bg;
    private Texture Stars;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Texture back_button;

    // Position and size for back button
    private float backButtonX, backButtonY, backButtonWidth, backButtonHeight;

    public profile_menu(Angry_ghosts ag) {
        this.ag = ag;

        // Load textures
        main_bg = new Texture("profilebg.png");
        second_bg = new Texture("main_rectangle12.png");
        Stars = new Texture("stars.jpg");
        back_button = new Texture("left-arrow.png");

        // Set up the camera and viewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        camera.position.set(viewport.getWorldWidth() / 2f, viewport.getWorldHeight() / 2f, 0);

        // Set back button position and size
        backButtonWidth = 200f;
        backButtonHeight = 200f;
        backButtonX = 10f;
        backButtonY = viewport.getWorldHeight() * 0.8f;
    }

    @Override
    public void show() {}

    @Override
    public void render(float v) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        camera.update();
        ag.batch.setProjectionMatrix(camera.combined);

        ag.batch.begin();

        // Draw the background and main elements
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        ag.batch.draw(main_bg, 0, 0, worldWidth, worldHeight);
        ag.batch.draw(second_bg, worldWidth / 2f - 500f, worldHeight * 0.15f, 1000f, 800f);
        ag.batch.draw(back_button, backButtonX, backButtonY, backButtonWidth, backButtonHeight);

        ag.batch.end();

        // Check if the back button is clicked
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            viewport.unproject(touchPos); // Convert screen coordinates to world coordinates

            if (isButtonClicked(touchPos, backButtonX, backButtonY, backButtonWidth, backButtonHeight)) {
                // Dispose current screen and switch to the previous screen (e.g., main menu)
                this.dispose();
                ag.setScreen(new main_menu(ag)); // Assuming `main_menu` is the previous screen
            }
        }
    }


    private boolean isButtonClicked(Vector3 touch, float x, float y, float width, float height) {
        return touch.x >= x && touch.x <= x + width && touch.y >= y && touch.y <= y + height;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        camera.position.set(viewport.getWorldWidth() / 2f, viewport.getWorldHeight() / 2f, 0);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        main_bg.dispose();
        second_bg.dispose();
        back_button.dispose();
    }
}
