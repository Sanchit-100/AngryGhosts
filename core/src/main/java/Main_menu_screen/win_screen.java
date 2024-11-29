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
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class win_screen implements Screen {
    private static final float BUTTON_WIDTH_PERCENT = 0.15f;
    private static final float BUTTON_HEIGHT_PERCENT = 0.1f;

    private Texture backgroundImage;
    private Texture subBackgroundImage;  // Add sub-background image texture
    private Texture victoryImage;
    private Texture mainMenuButton;
    private Texture restartButton;
    private Texture settingsButton;
    private Texture pointsImage;  // Add points image texture

    private Angry_ghosts ag;
    private OrthographicCamera camera;
    private Viewport viewport;

    private float buttonWidth, buttonHeight;
    private float xMainMenu, xRestart, xSettings;

    public win_screen(Angry_ghosts ag, int points){
        this.ag = ag;

        // Load textures
        backgroundImage = new Texture("win_bg.jpg");
        subBackgroundImage = new Texture("sub_bg.png");  // Load sub-background image texture
        victoryImage = new Texture("victory_symbol.png");
        mainMenuButton = new Texture("home.png");
        restartButton = new Texture("restart.png");
        settingsButton = new Texture("main_menu_icon.png");
        pointsImage = new Texture("points_new.png");  // Load points image texture

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
        float totalButtonWidth = 3 * buttonWidth + 2 * (buttonWidth * 0.5f); // Three buttons with space in between
        float startX = worldWidth / 2f - totalButtonWidth / 2;

        xMainMenu = startX;
        xRestart = startX + buttonWidth * 1.5f;
        xSettings = startX + buttonWidth * 3f;
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

        // Draw the sub-background image
        // ag.batch.draw(subBackgroundImage, worldWidth*0.15f, worldHeight*0.1f,(viewport.getWorldWidth())*0.7f , viewport.getWorldHeight()*0.7f);  // Adjust position and size as needed

        // Draw the victory image
        ag.batch.draw(victoryImage, worldWidth / 2f - 350f, worldHeight * 0.3f, 800, 500);

        // Draw the points image
        ag.batch.draw(pointsImage, worldWidth * 0.17f, worldHeight * 0.65f, 300, 150);  // Adjust position and size as needed

        BitmapFont font = new BitmapFont();
        font.getData().setScale(3);
        String victoryText = "VICTORY";
        float textWidth = font.getRegion().getRegionWidth();
        float xVictoryText = worldWidth / 2f - textWidth / 2f;
        float yVictoryText = worldHeight * 0.3f - 50;
        font.draw(ag.batch, victoryText, xVictoryText, yVictoryText);
        // Draw the buttons horizontally
        ag.batch.draw(mainMenuButton, xMainMenu, 100, buttonWidth, buttonHeight);
        ag.batch.draw(restartButton, xRestart, 100, buttonWidth, buttonHeight);
        ag.batch.draw(settingsButton, xSettings, 100, buttonWidth, buttonHeight);

        ag.batch.end();

        // Handle input
        if (Gdx.input.justTouched()) {
            Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            viewport.unproject(touch);
            if (isButtonClicked(touch, xMainMenu, 100)) {
                this.dispose();
                ag.setScreen(new main_menu(ag));
            } else if (isButtonClicked(touch, xRestart, 100)) {
                this.dispose();
                ag.setScreen(new level_1(ag)); // You can restart the level here
            } else if (isButtonClicked(touch, xSettings, 100)) {
                this.dispose();
                ag.setScreen(new Level_selection(ag));
            }
        }
    }

    private boolean isButtonClicked(Vector3 touch, float x, float y){
        return touch.x >= x && touch.x <= x + buttonWidth && touch.y >= y && touch.y <= y + buttonHeight;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        camera.position.set(viewport.getWorldWidth() / 2f, viewport.getWorldHeight() / 2f, 0);
        updateButtonPositions();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume(){
    }

    @Override
    public void hide(){
    }

    @Override
    public void dispose(){
        backgroundImage.dispose();
        subBackgroundImage.dispose();
        victoryImage.dispose();
        mainMenuButton.dispose();
        restartButton.dispose();
        settingsButton.dispose();
        pointsImage.dispose();
    }
}
