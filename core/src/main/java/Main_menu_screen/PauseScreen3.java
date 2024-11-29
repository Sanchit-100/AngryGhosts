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

public class PauseScreen3 implements Screen {
    private static final float BUTTON_WIDTH_PERCENT = 0.15f;
    private static final float BUTTON_HEIGHT_PERCENT = 0.15f;

    private Texture backgroundImage;
    private Texture pauseImage;
    private Texture resumeButton;
    private Texture restartButton;
    private Texture mainMenuButton;

    private Angry_ghosts ag;
    private OrthographicCamera camera;
    private Viewport viewport;

    private float buttonWidth, buttonHeight;
    private float xResume, xRestart, xMainMenu;

    public PauseScreen3(Angry_ghosts ag) {
        this.ag = ag;

        // Load textures
        backgroundImage = new Texture("spooky_bg_6.jpg");
        pauseImage = new Texture("main menu screen.jpg");  // Image on the right side
        resumeButton = new Texture("resume.png");
        restartButton = new Texture("restart.png");
        mainMenuButton = new Texture("main_menu_icon.png");

        // Create the camera and viewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        camera.position.set(viewport.getWorldWidth() / 2f, viewport.getWorldHeight() / 2f, 0);
    }

    @Override
    public void show() {
        updateButtonPositions();
    }

    private void updateButtonPositions() {
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        buttonWidth = worldHeight * BUTTON_WIDTH_PERCENT;
        buttonHeight = worldHeight * BUTTON_HEIGHT_PERCENT;

        //Vertically align buttons on the left side
        xResume = worldWidth * 0.1f;
        xRestart = worldWidth * 0.1f;
        xMainMenu = worldWidth * 0.1f;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        camera.update();
        ag.batch.setProjectionMatrix(camera.combined);

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        ag.batch.begin();

        // Draw the background
        ag.batch.draw(backgroundImage, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());

        // Draw the pause image on the right side
        ag.batch.draw(pauseImage, worldWidth * 0.25f, worldHeight * 0.1f, worldWidth * 0.7f, worldHeight * 0.7f);

        // Draw the buttons vertically aligned on the left side
        ag.batch.draw(resumeButton, xResume, worldHeight * 0.5f, buttonWidth, buttonHeight);
        ag.batch.draw(restartButton, xRestart, worldHeight * 0.35f, buttonWidth, buttonHeight);
        ag.batch.draw(mainMenuButton, xMainMenu, worldHeight * 0.2f, buttonWidth, buttonHeight);

        ag.batch.end();

        // Handle input
        if(Gdx.input.justTouched()){
            Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            viewport.unproject(touch);
            if(isButtonClicked(touch, xResume, worldHeight * 0.5f)) {
                this.dispose();

                level_3 level3 = new level_3(ag);
                level3.loadSavedGame();

                ag.setScreen(level3);
            }

            else if(isButtonClicked(touch, xRestart, worldHeight * 0.35f)) {
                this.dispose();
                ag.setScreen(new level_3(ag));
            }

            else if(isButtonClicked(touch, xMainMenu, worldHeight * 0.2f)) {
                this.dispose();
                ag.setScreen(new main_menu(ag));
            }
        }
    }

    private boolean isButtonClicked(Vector3 touch, float x, float y) {
        return touch.x >= x && touch.x <= x + buttonWidth && touch.y >= y && touch.y <= y + buttonHeight;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        camera.position.set(viewport.getWorldWidth() / 2f, viewport.getWorldHeight() / 2f, 0);
        updateButtonPositions();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        backgroundImage.dispose();
        pauseImage.dispose();
        resumeButton.dispose();
        restartButton.dispose();
        mainMenuButton.dispose();
    }
}
