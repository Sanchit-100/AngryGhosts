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

public class level_1 implements Screen {
    private static final float BUTTON_WIDTH_PERCENT = 0.1f;
    private static final float BUTTON_HEIGHT_PERCENT = 0.1f;

    private Texture backgroundImage;
    private Texture slingshotImage;
    private Texture[] ghostSprites = new Texture[3];
    private Texture[] pigSprites = new Texture[3];
    private Texture glassBlock;
    private Texture woodenBlock;
    private Texture winButton;
    private Texture loseButton;
    private Texture pauseButton;

    private Angry_ghosts ag;
    private OrthographicCamera camera;
    private Viewport viewport;

    private float buttonWidth, buttonHeight;
    private float xWin, xLose;

    public level_1(Angry_ghosts ag) {
        this.ag = ag;

        // Load textures
        backgroundImage = new Texture("Assets/spooky_bg_6.jpg");
        slingshotImage = new Texture("Assets/slingshot.png");
        ghostSprites[0] = new Texture("Assets/angry1.png");
        ghostSprites[1] = new Texture("Assets/angry2.png");
        ghostSprites[2] = new Texture("Assets/angry3.png");
        pigSprites[0] = new Texture("Assets/pig1.png");
        pigSprites[1] = new Texture("Assets/pig1.png");
        pigSprites[2] = new Texture("Assets/pig1.png");
        pauseButton = new Texture("Assets/pause.png");
        glassBlock = new Texture("Assets/glass_block.png");
        woodenBlock = new Texture("Assets/wooden_block.png");
        winButton = new Texture("Assets/win_icon.png");
        loseButton = new Texture("Assets/lose_icon.png");

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
        xWin = worldWidth - buttonWidth * 2;
        xLose = worldWidth - buttonWidth;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        camera.update();
        ag.batch.setProjectionMatrix(camera.combined);
        ag.batch.begin();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        // Draw the background
        ag.batch.draw(backgroundImage, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());

        // Draw the slingshot
        ag.batch.draw(slingshotImage, 250, 150, 150, 250);


        // Draw the ghost sprites beside the slingshot
        for (int i = 0; i < ghostSprites.length; i++) {
            ag.batch.draw(ghostSprites[i], 10 + i * 100, 130, 120, 120);
        }

        // Draw the pigs on blocks
        ag.batch.draw(glassBlock, 1420, 130, 150, 90);
        ag.batch.draw(pigSprites[0], 1420, 190, 120, 120);

        ag.batch.draw(woodenBlock, 1535, 135, 150, 70);
        ag.batch.draw(pigSprites[0], 1550, 190, 120, 120);

        ag.batch.draw(glassBlock, 1420, 480, 150, 90);
        ag.batch.draw(pigSprites[0], 1420, 540, 120, 120);

        ag.batch.draw(woodenBlock, 1535, 485, 150, 70);
        ag.batch.draw(pigSprites[0], 1550, 540, 120, 120);

        ag.batch.draw(glassBlock, 1420, 880, 150, 90);
        ag.batch.draw(pigSprites[0], 1420, 940, 120, 120);

        ag.batch.draw(woodenBlock, 1535, 885, 150, 70);
        ag.batch.draw(pigSprites[0], 1550, 940, 120, 120);


//        ag.batch.draw(woodenBlock, 550, 100, 100, 150);
//        ag.batch.draw(pigSprites[1], 570, 130, 120, 120);
//
//        ag.batch.draw(glassBlock, 700, 100, 100, 150);
//        ag.batch.draw(pigSprites[2], 720, 130, 120, 120);

        // Draw the buttons at the bottom right
        ag.batch.draw(winButton, xWin, 50, buttonWidth, buttonHeight);
        ag.batch.draw(loseButton, xLose, 50, buttonWidth, buttonHeight);
        ag.batch.draw(pauseButton, worldWidth*0.05f,worldHeight*0.9f,buttonWidth,buttonHeight);

        ag.batch.end();

        // Handle input
        if (Gdx.input.justTouched()) {
            Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            viewport.unproject(touch);
            if (isButtonClicked(touch, xWin, 50)) {
                this.dispose();
                ag.setScreen(new win_screen(ag, 1000));
            } else if (isButtonClicked(touch, xLose, 50)) {
                this.dispose();
                ag.setScreen(new lose_screen(ag,10000));
            }
            else if (isButtonClicked(touch, worldWidth*0.05f,worldHeight*0.9f)) {
                this.dispose();
                ag.setScreen(new PauseScreen(ag));
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
        slingshotImage.dispose();
        for (Texture ghost : ghostSprites) {
            ghost.dispose();
        }
        for (Texture pig : pigSprites) {
            pig.dispose();
        }
        glassBlock.dispose();
        woodenBlock.dispose();
        winButton.dispose();
        loseButton.dispose();
    }
}
