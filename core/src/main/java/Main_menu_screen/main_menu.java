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
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class main_menu implements Screen {

    // Button dimensions (now in percentage of screen height)
    private static final float OPTION_BUTTON_WIDTH_PERCENT = 0.2f;
    private static final float OPTION_BUTTON_HEIGHT_PERCENT = 0.1f;
    private static final float PLAY_BUTTON_WIDTH_PERCENT = 0.233f;
    private static final float PLAY_BUTTON_HEIGHT_PERCENT = 0.1f;
    private static final float CONTINUE_BUTTON_WIDTH_PERCENT = 0.2f;
    private static final float CONTINUE_BUTTON_HEIGHT_PERCENT = 0.1f;
    private static final float EXIT_BUTTON_WIDTH_PERCENT = 0.2f;
    private static final float EXIT_BUTTON_HEIGHT_PERCENT = 0.1f;


    // Textures
    private Texture optionButton;
    private Texture playButton;
    private Texture continueButton;
    private Texture exitButton;
    private Texture backgroundMenu;
    private Texture high_score;
    private Texture logo1;
    private BitmapFont scoreFont;
    private GlyphLayout scoreLayout;
    private String highScoreText = "0";
    private float scoreX, scoreY;
    private Texture profile_pic;
    private Texture deadh;

    // Core variables
    private Angry_ghosts ag;
    private OrthographicCamera camera;
    private Viewport viewport;

    // Button positions and sizes
    private float xPlay, xOption, xExit, xContinue, xHighS;
    private float buttonWidth, buttonHeight;

    public main_menu(Angry_ghosts ag) {
        this.ag = ag;

        // Load textures
        playButton = new Texture("download-removebg-preview.png");
        optionButton = new Texture("download_1_-removebg-preview.png");
        continueButton = new Texture("download_2_-removebg-preview.png");
        exitButton = new Texture("download_3_-removebg-preview.png");
        backgroundMenu = new Texture("main menu screen.jpg");
        high_score = new Texture("highs1.png");
        profile_pic = new Texture("profile.png");
        logo1 = new Texture("Angry_Birds_logos.jpg");
        deadh = new Texture("deadhour.png");
        // Create the camera and viewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        camera.position.set(viewport.getWorldWidth() / 2f, viewport.getWorldHeight() / 2f, 0);

        scoreFont = new BitmapFont(); // Use default font for now, can change to a custom .fnt file
        scoreFont.setColor(1, 0, 0, 1); // Set initial color to red
        scoreLayout = new GlyphLayout();
    }

    @Override
    public void show() {
        updateButtonPositions();
        updateScorePosition();
    }
    private void updateScorePosition() {
        float worldHeight = viewport.getWorldHeight();
        float worldWidth = viewport.getWorldWidth();

        scoreLayout.setText(scoreFont, highScoreText);
        scoreX = xHighS + 200 - scoreLayout.width / 2; // Center the text below the high score
        scoreY = worldHeight * 0.75f - 20 - scoreLayout.height; // Adjust Y to be below the high score image
    }


    private void updateButtonPositions() {
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        buttonWidth = worldHeight * PLAY_BUTTON_WIDTH_PERCENT;
        buttonHeight = worldHeight * PLAY_BUTTON_HEIGHT_PERCENT;

        xPlay = worldWidth / 2f - buttonWidth / 2f;
        xOption = worldWidth / 2f - buttonWidth / 2f;
        xExit = worldWidth / 2f - buttonWidth / 2f;
        xContinue = worldWidth / 2f - buttonWidth / 2f;
        xHighS = 10;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        camera.update();
        ag.batch.setProjectionMatrix(camera.combined);

        ag.batch.begin();
        float worldWidth = viewport.getWorldWidth();
        //float worldHeight = viewport.getWorldHeight();

        // Draw the background
        ag.batch.draw(backgroundMenu, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());

        // Draw the buttons
        float worldHeight = viewport.getWorldHeight();
        ag.batch.draw(playButton, xPlay, worldHeight * 0.5f, buttonWidth, buttonHeight);
        ag.batch.draw(optionButton, xOption, worldHeight * 0.35f, buttonWidth, buttonHeight);
        ag.batch.draw(continueButton, xContinue, worldHeight * 0.65f, buttonWidth, buttonHeight);
        ag.batch.draw(exitButton, xExit, worldHeight * 0.2f, buttonWidth, buttonHeight);
        ag.batch.draw(high_score, xHighS, worldHeight * 0.75f, 400, 250);
        ag.batch.draw(profile_pic, worldWidth -400f,worldHeight*0.75f,450,270);
        ag.batch.draw(logo1,worldWidth/2f - 350f,worldHeight * 0.75f, 700,200);
        ag.batch.draw(deadh,worldWidth/2f - 450f,worldHeight*0.005f,900,270);

        //scoreFont.setColor(1, 0, 0, 1); // Set color to red
        //scoreFont.draw(ag.batch, highScoreText, scoreX, scoreY);

        ag.batch.end();

        // Handle input
        if (Gdx.input.justTouched()) {
            Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            viewport.unproject(touch);

            if (isButtonClicked(touch, xPlay, worldHeight * 0.5f)) {
                this.dispose();
                ag.setScreen(new Level_selection(ag));
            } else if (isButtonClicked(touch, xOption, worldHeight * 0.35f)) {
                this.dispose();
                // ag.setScreen(new setting_menu());
            } else if (isButtonClicked(touch, xContinue, worldHeight * 0.65f)) {
                this.dispose();
                // ag.setScreen(new Game_screen());
            } else if (isButtonClicked(touch, xExit, worldHeight * 0.2f)) {
                Gdx.app.exit();
            }
            else if(isButtonClicked(touch, worldWidth -400f,worldHeight*0.75f)){
                this.dispose();
                ag.setScreen(new profile_menu(ag));
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
        playButton.dispose();
        optionButton.dispose();
        continueButton.dispose();
        exitButton.dispose();
        backgroundMenu.dispose();
        high_score.dispose();
        scoreFont.dispose();
        profile_pic.dispose();
        logo1.dispose();
        deadh.dispose();
    }
}
